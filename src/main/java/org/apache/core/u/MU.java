package org.apache.core.u;

public enum MU
{
	;
	public static double roundToStep(double value, double step)
	{
		return step * Math.round(value / step);
	}
}
