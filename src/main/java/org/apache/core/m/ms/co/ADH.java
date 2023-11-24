package org.apache.core.m.ms.co;

import org.apache.core.u.*;
import org.apache.core.Client;
import org.apache.core.e.e.PlayerTickListener;
import org.apache.core.m.C;
import org.apache.core.m.M;
import org.apache.core.m.s.BS;
import org.apache.core.m.s.DS;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ADH extends M implements PlayerTickListener
{
	private final BS dhandafterpop;
	private final BS dhandAtHealth;
	private final DS dHandHealth;
	private final BS checkPlayersAround;
	private final DS distance;
	private final BS predictCrystals;
	private final BS checkEnemiesAim;
	private final BS checkHoldingItems;
	private final DS activatesAbove;
	private boolean BelowHearts;
	private boolean noOffhandTotem;

	public ADH() {
		super("Auto Double Hand", "Automatically double hand when you appear to be in a predicament", true, C.COMBAT);
		this.dhandafterpop = BS.Builder.newInstance().setName("dHand after Pop").setDescription("Automatically dHands afer a pop").setModule(this).setValue(false).setAvailability(() -> true).build();
		this.dhandAtHealth = BS.Builder.newInstance().setName("dHand at Health").setDescription("when enabled, it will dhand at a sertain health").setModule(this).setValue(false).setAvailability(() -> true).build();
		final DS.Builder setStep = DS.Builder.newInstance().setName("dHand Health").setDescription("What Health to automatically doublehand on").setModule(this).setValue(2.0).setMin(1.0).setMax(20.0).setStep(1.0);
		final BS dhandAtHealth = this.dhandAtHealth;
		Objects.requireNonNull(dhandAtHealth);
		this.dHandHealth = setStep.setAvailability(dhandAtHealth::get).build();
		this.checkPlayersAround = BS.Builder.newInstance().setName("Check Around Players").setDescription("if on, ADH will only activate when players are around").setModule(this).setValue(true).setAvailability(() -> true).build();
		final DS.Builder setStep2 = DS.Builder.newInstance().setName("Distance").setDescription("the distance for your enemy to activate").setModule(this).setValue(5.0).setMin(1.0).setMax(10.0).setStep(0.1);
		final BS checkPlayersAround = this.checkPlayersAround;
		Objects.requireNonNull(checkPlayersAround);
		this.distance = setStep2.setAvailability(checkPlayersAround::get).build();
		this.predictCrystals = BS.Builder.newInstance().setName("Predict Crystals").setDescription("whether or not to predict crystal placements").setModule(this).setValue(false).setAvailability(() -> true).build();
		final BS.Builder setValue = BS.Builder.newInstance().setName("Check Aim").setDescription("when enabled, crystal prediction will only activate when someone is pointing at an obsidian").setModule(this).setValue(false);
		final BS predictCrystals = this.predictCrystals;
		Objects.requireNonNull(predictCrystals);
		this.checkEnemiesAim = setValue.setAvailability(predictCrystals::get).build();
		this.checkHoldingItems = BS.Builder.newInstance().setName("Check Items").setDescription("when enabled, crystal prediction will only activate when someone is pointing at an obsidian with crystals out").setModule(this).setValue(false).setAvailability(() -> this.predictCrystals.get() && this.checkEnemiesAim.get()).build();
		this.activatesAbove = DS.Builder.newInstance().setName("Activation Hight").setDescription("ADH will only activate when you are above this height, set to 0 to disable").setModule(this).setValue(0.2).setMin(0.0).setMax(4.0).setStep(0.1).setAvailability(() -> true).build();
		this.BelowHearts = false;
		this.noOffhandTotem = false;
	}

	@Override
	public void onEnable() {
		super.onEnable();
		ADH.eventManager.add(PlayerTickListener.class, this);
	}

	@Override
	public void onDisable() {
		super.onDisable();
		ADH.eventManager.remove(PlayerTickListener.class, this);
	}

	private List<EndCrystalEntity> getNearByCrystals() {
		final Vec3d pos = Client.MC.player.getPos();
		return (List<EndCrystalEntity>) Client.MC.world.getEntitiesByClass((Class)EndCrystalEntity.class, new Box(pos.add(-6.0, -6.0, -6.0), pos.add(6.0, 6.0, 6.0)), a -> true);
	}

	@Override
	public void onPlayerTick() {
		final double distanceSq = this.distance.get() * this.distance.get();
		final PlayerInventory inv = ADH.mc.player.getInventory();
		if (((ItemStack)inv.offHand.get(0)).getItem() != Items.TOTEM_OF_UNDYING && this.dhandafterpop.get() && !this.noOffhandTotem) {
			this.noOffhandTotem = true;
			IU.selectItemFromHotbar(Items.TOTEM_OF_UNDYING);
		}
		if (((ItemStack)inv.offHand.get(0)).getItem() == Items.TOTEM_OF_UNDYING) {
			this.noOffhandTotem = false;
		}
		if (Client.MC.player.getHealth() <= this.dHandHealth.get() && this.dhandAtHealth.get() && !this.BelowHearts) {
			this.BelowHearts = true;
			IU.selectItemFromHotbar(Items.TOTEM_OF_UNDYING);
		}
		if (Client.MC.player.getHealth() > this.dHandHealth.get()) {
			this.BelowHearts = false;
		}
		if (Client.MC.player.getHealth() > 19.0f) {
			return;
		}
		if (this.checkPlayersAround.get() && Client.MC.world.getPlayers().parallelStream().filter(e -> e != Client.MC.player).noneMatch(player -> Client.MC.player.squaredDistanceTo(player) <= distanceSq)) {
			return;
		}
		final double activatesAboveV = this.activatesAbove.get();
		for (int f = (int)Math.floor(activatesAboveV), i = 1; i <= f; ++i) {
			if (BU.hasBlock(Client.MC.player.getBlockPos().add(0, -i, 0))) {
				return;
			}
		}
		if (BU.hasBlock(new BlockPos(Client.MC.player.getPos().add(0.0, -activatesAboveV, 0.0)))) {
			return;
		}
		final List<EndCrystalEntity> crystals = this.getNearByCrystals();
		final ArrayList<Vec3d> crystalsPos = new ArrayList<Vec3d>();
		crystals.forEach(e -> crystalsPos.add(e.getPos()));
		if (this.predictCrystals.get()) {
			Stream<BlockPos> stream = BU.getAllInBoxStream(Client.MC.player.getBlockPos().add(-6, -8, -6), Client.MC.player.getBlockPos().add(6, 2, 6)).filter(e -> BU.isBlock(Blocks.OBSIDIAN, e) || BU.isBlock(Blocks.BEDROCK, e)).filter(CrU::canPlaceCrystalClient);
			if (this.checkEnemiesAim.get()) {
				if (this.checkHoldingItems.get()) {
					stream = stream.filter(this::arePeopleAimingAtBlockAndHoldingCrystals);
				}
				else {
					stream = stream.filter(this::arePeopleAimingAtBlock);
				}
			}
			stream.forEachOrdered(e -> crystalsPos.add(Vec3d.ofBottomCenter(e).add(0.0, 1.0, 0.0)));
		}
		for (final Vec3d pos : crystalsPos) {
			final double damage = DU.crystalDamage((PlayerEntity) Client.MC.player, pos, true, null, false);
			if (damage >= Client.MC.player.getHealth() + Client.MC.player.getAbsorptionAmount()) {
				IU.selectItemFromHotbar(Items.TOTEM_OF_UNDYING);
				break;
			}
		}
	}

	private boolean arePeopleAimingAtBlock(final BlockPos block) {
		final Vec3d[] eyesPos = new Vec3d[1];
		final BlockHitResult[] hitResult = new BlockHitResult[1];
		return Client.MC.world.getPlayers().parallelStream().filter(e -> e != Client.MC.player).anyMatch(e -> {
			eyesPos[0] = RoU.getEyesPos(e);
			hitResult[0] = Client.MC.world.raycast(new RaycastContext(eyesPos[0], eyesPos[0].add(RoU.getPlayerLookVec(e).multiply(4.5)), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, (Entity) e));
			return hitResult[0] != null && hitResult[0].getBlockPos().equals((Object)block);
		});
	}

	private boolean arePeopleAimingAtBlockAndHoldingCrystals(final BlockPos block) {
		final Vec3d[] eyesPos = new Vec3d[1];
		final BlockHitResult[] hitResult = new BlockHitResult[1];
		return Client.MC.world.getPlayers().parallelStream().filter(e -> e != Client.MC.player).filter(e -> e.isHolding(Items.END_CRYSTAL)).anyMatch(e -> {
			eyesPos[0] = RoU.getEyesPos(e);
			hitResult[0] = Client.MC.world.raycast(new RaycastContext(eyesPos[0], eyesPos[0].add(RoU.getPlayerLookVec(e).multiply(4.5)), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, (Entity) e));
			return hitResult[0] != null && hitResult[0].getBlockPos().equals((Object)block);
		});
	}
}
