package org.apache.core.m.s;

import org.apache.core.g.c.C;
import org.apache.core.g.c.SC;
import org.apache.core.g.w.W;
import org.apache.core.m.M;

import java.util.function.Supplier;

public class IS extends S<Integer>
{

	private int value;
	private final int min;
	private final int max;
	private final Supplier<Boolean> availability;

	private IS(Builder builder)
	{
		super(builder.name, builder.description, builder.module);
		this.value = builder.value;
		this.min = builder.min;
		this.max = builder.max;
		this.availability = builder.availability;
	}

	@Override
	public Integer get()
	{
		return value;
	}

	@Override
	public void set(Integer value)
	{
		this.value = value;
	}

	@Override
	public C makeComponent(W parent)
	{
		return new SC(parent, 0, 0, 60, value, min, max, 1, SC.DisplayType.INTEGER, v -> value = v.intValue(), availability, getName());
	}

	public int getMin()
	{
		return min;
	}

	public int getMax()
	{
		return max;
	}

	public static class Builder
	{
		private String name;
		private String description;
		private M module;
		private int value;
		private int min;
		private int max;
		private Supplier<Boolean> availability = () -> true;

		public static Builder newInstance()
		{
			return new Builder();
		}

		public IS build()
		{
			return new IS(this);
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

		public Builder setValue(int value)
		{
			this.value = value;
			return this;
		}

		public Builder setMin(int min)
		{
			this.min = min;
			return this;
		}

		public Builder setMax(int max)
		{
			this.max = max;
			return this;
		}

		public Builder setAvailability(Supplier<Boolean> availability)
		{
			this.availability = availability;
			return this;
		}
	}
}
