package org.apache.core.m.s;

import org.apache.core.g.c.C;
import org.apache.core.g.w.W;
import org.apache.core.m.M;

import java.io.Serializable;

public abstract class S<T> implements Serializable
{

	private String name;
	private String description;

	protected S(String name, String description, M module)
	{
		this.name = name;
		this.description = description;
		module.addSetting(this);
	}

	public abstract T get();
	public abstract void set(T value);

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}

	public abstract C makeComponent(W parent);

}
