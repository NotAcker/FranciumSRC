package org.apache.core.c;

public class R
{
	private final float yaw;
	private final float pitch;

	private final boolean ignoreYaw;
	private final boolean ignorePitch;

	public R(float yaw, float pitch)
	{
		this.yaw = yaw;
		this.pitch = pitch;
		ignoreYaw = false;
		ignorePitch = false;
	}

	public R(float yaw, boolean ignoreYaw, float pitch, boolean ignorePitch)
	{
		this.yaw = yaw;
		this.ignoreYaw = ignoreYaw;
		this.pitch = pitch;
		this.ignorePitch = ignorePitch;
	}

	public float getYaw()
	{
		return yaw;
	}
	public float getPitch()
	{
		return pitch;
	}
	public boolean isIgnoreYaw()
	{
		return ignoreYaw;
	}
	public boolean isIgnorePitch()
	{
		return ignorePitch;
	}
}