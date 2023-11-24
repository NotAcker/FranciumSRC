package org.apache.core.m.s;

import org.apache.core.g.c.CC;
import org.apache.core.g.c.C;
import org.apache.core.g.w.W;
import org.apache.core.m.M;

import java.util.function.Supplier;

public class BS extends S<Boolean>
{

	private boolean value;
	private final Supplier<Boolean> availability;

	private BS(Builder builder)
	{
		super(builder.name, builder.description, builder.module);
		value = builder.value;
		availability = builder.availability;
	}

	@Override
	public Boolean get()
	{
		return value;
	}

	@Override
	public void set(Boolean value)
	{
		this.value = value;
	}

	@Override
	public C makeComponent(W parent)
	{
		return new CC(parent, 0, 0, value, v -> value = v, availability, getName());
	}

	public static class Builder
	{
		private String name;
		private String description;
		private M module;
		private boolean value;
		private Supplier<Boolean> availability = () -> true;

		public static Builder newInstance()
		{
			return new Builder();
		}

		public BS build()
		{
			return new BS(this);
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

		public Builder setValue(boolean value)
		{
			this.value = value;
			return this;
		}

		public Builder setAvailability(Supplier<Boolean> availability)
		{
			this.availability = availability;
			return this;
		}
	}
}
