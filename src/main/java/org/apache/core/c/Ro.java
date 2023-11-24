package org.apache.core.c;

import org.apache.core.Client;
import org.apache.core.e.e.PlayerTickListener;
import org.apache.core.u.RoU;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class Ro implements PlayerTickListener
{

	public Ro()
	{
		Client.INSTANCE.eventManager().add(PlayerTickListener.class, this);
	}

	private final ArrayList<R> rotations = new ArrayList<>();
	private Runnable callback;

	@Override
	public void onPlayerTick()
	{
		if (rotations.size() != 0)
		{
			RoU.setRotation(rotations.get(rotations.size() - 1));
			rotations.remove(rotations.size() - 1);
			if (rotations.size() == 0)
				callback.run();
		}
	}

	public void stepToward(Vec3d pos, int steps, Runnable callback)
	{
		stepToward(RoU.getNeededRotations(pos), steps, callback);
	}

	public void stepToward(R rotation, int steps, Runnable callback)
	{
		rotations.clear();
		float yaw = rotation.getYaw();
		float pitch = rotation.getPitch();
		float stepYaw = (yaw - Client.MC.player.getYaw()) / (float) steps;
		float stepPitch = (pitch - Client.MC.player.getPitch()) / (float) steps;
		for (int i = 0; i < steps; i++)
		{
			rotations.add(new R(yaw, rotation.isIgnoreYaw(), pitch, rotation.isIgnorePitch()));
			yaw -= stepYaw;
			pitch -= stepPitch;
		}
		this.callback = callback;
	}
}
