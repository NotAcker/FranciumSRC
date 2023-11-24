package org.apache.core.m.ms.co;


import net.minecraft.entity.decoration.EndCrystalEntity;
import org.apache.core.m.s.BS;
import org.apache.core.m.s.DS;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.apache.core.e.e.PlayerTickListener;
import org.apache.core.m.C;
import org.apache.core.m.M;
import org.apache.core.m.s.IS;
import org.apache.core.mx.MinecraftClientAccessor;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static org.apache.core.Client.MC;

public class TB extends M implements PlayerTickListener
{
	private final IS delay;
	private final BS randomDelay;
	private final DS cooldown;
	private final BS attackMobs;
	private final BS activateOnLeftClick;
	private final BS attackInAir;
	private final BS attackOnJump;

	enum TriggerStatus {
		IDLE,
		ATTACKING
	}

	private TriggerStatus triggerStatus;
	private int delayClock;
	private int randomDelayInt;

	public TB() {
		super(" Trigger Bot", "automatically attacks players for you", false, C.COMBAT);
		this.delay = IS.Builder.newInstance().setName("Delay").setDescription("").setModule(this).setAvailability(() -> true).setValue(0).setMin(0).setMax(5).build();
		this.randomDelay = BS.Builder.newInstance().setName("Random Delay").setDescription("").setModule(this).setValue(true).setAvailability(() -> true).build();
		this.cooldown = DS.Builder.newInstance().setName("Cooldown").setDescription("the cooldown threshold").setModule(this).setValue(1.0).setMin(0.0).setMax(1.0).setStep(0.1).setAvailability(() -> true).build();
		this.attackMobs = BS.Builder.newInstance().setName("Attack Mobs").setDescription("").setModule(this).setValue(false).setAvailability(() -> true).build();
		this.activateOnLeftClick = BS.Builder.newInstance().setName("Use Left Click").setDescription("will only activate on right click when enabled").setModule(this).setValue(false).setAvailability(() -> true).build();
		this.attackInAir = BS.Builder.newInstance().setName("Attack In Air").setDescription("whether or not to attack in mid air").setModule(this).setValue(true).setAvailability(() -> true).build();
		final BS.Builder setValue = BS.Builder.newInstance().setName("Attack On Jump").setDescription("whether or not to attack when going upwards").setModule(this).setValue(true);
		final BS attackInAir = this.attackInAir;
		Objects.requireNonNull(attackInAir);
		this.attackOnJump = setValue.setAvailability(attackInAir::get).build();
	}

	@Override
	public void onEnable() {
		super.onEnable();
		eventManager.add(PlayerTickListener.class, this);

		triggerStatus = TriggerStatus.IDLE;
		delayClock = 0;
		randomDelayInt = 0;
	}

	@Override
	public void onDisable() {
		super.onDisable();
		eventManager.remove(PlayerTickListener.class, this);

		triggerStatus = TriggerStatus.IDLE;
		delayClock = 0;
		randomDelayInt = 0;
	}

	private int getDelay() {
		if (!randomDelay.get())
			return delay.get();

		if (randomDelayInt == 0)
			randomDelayInt = ThreadLocalRandom.current().nextInt(1, 3);

		return randomDelayInt;
	}

	@Override
	public void onPlayerTick() {

		if (MC.currentScreen != null)
			return;

		if (MC.player.isUsingItem())
			return;

		if (this.activateOnLeftClick.get() && GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), 0) != 1)
			return;

		if (!(MC.player.getMainHandStack().getItem() instanceof SwordItem))
			return;

		HitResult hit = MC.crosshairTarget;

		if (hit.getType() != HitResult.Type.ENTITY) {
			triggerStatus = TriggerStatus.IDLE;
			return;
		}

		if (MC.player.getAttackCooldownProgress(0.0f) < this.cooldown.get())
			return;

		final Entity target = ((EntityHitResult)hit).getEntity();

		if (target instanceof EndCrystalEntity)
			return;

		if (!target.isAlive())
			return;

		if (!(target instanceof PlayerEntity) && !attackMobs.get())
			return;

		if (triggerStatus == TriggerStatus.IDLE) {
			if (delayClock <= getDelay()) {
				delayClock++;
				return;
			}

			delayClock = 0;
		}

		if (!target.isOnGround() && !this.attackInAir.get())
			return;

		if (attackOnJump.get() && MC.player.getVelocity().y > -0.08)
			return;

		((MinecraftClientAccessor) MC).leftClick();
		triggerStatus = TriggerStatus.ATTACKING;
		randomDelayInt = 0;

	}
}
