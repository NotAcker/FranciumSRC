package org.apache.core.m.s;

import org.apache.core.g.c.C;
import org.apache.core.g.c.SC;
import org.apache.core.g.w.W;
import org.apache.core.m.M;

import java.util.function.Supplier;

public class DS extends S<Double>
{

	private double value;
	private final double min;
	private final double max;
	private final double step;
	private final Supplier<Boolean> availability;

	private DS(Builder builder)
	{
		super(builder.name, builder.description, builder.module);
		this.value = builder.value;
		this.min = builder.min;
		this.max = builder.max;
		this.step = builder.step;
		this.availability = builder.availability;
	}

	@Override
	public Double get()
	{
		return value;
	}

	@Override
	public void set(Double value)
	{
		this.value = value;
	}

	@Override
	public C makeComponent(W parent)
	{
		return new SC(parent, 0, 0, 60, value, min, max, step, SC.DisplayType.DECIMAL, v -> value = v, availability, getName());
	}

	public static class Builder
	{
		private String name;
		private String description;
		private M module;
		private double value;
		private double min;
		private double max;
		private double step = 0.1;
		private Supplier<Boolean> availability = () -> true;

		public static Builder newInstance()
		{
			return new Builder();
		}

		public DS build()
		{
			return new DS(this);
		}

		public Builder setName(String name)
		{
			this.name = name;
			return this;
		}

		public Builder setDescription(String description)
		{
			this.description = description;
			return this;
		}

		public Builder setModule(M module)
		{
			this.module = module;
			return this;
		}

		public Builder setValue(double value)
		{
			this.value = value;
			return this;
		}

		public Builder setMin(double min)
		{
			this.min = min;
			return this;
		}

		public Builder setMax(double max)
		{
			this.max = max;
			return this;
		}

		public Builder setStep(double step)
		{
			this.step = step;
			return this;
		}

		public Builder setAvailability(Supplier<Boolean> availability)
		{
			this.availability = availability;
			return this;
		}
	}
}
