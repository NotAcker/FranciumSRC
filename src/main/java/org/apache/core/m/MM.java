package org.apache.core.m;

import org.apache.core.m.ms.c.*;
import org.apache.core.m.ms.co.*;
import org.apache.core.m.ms.m.*;
import org.apache.core.m.ms.r.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class MM
{
	private final HashMap<Class<? extends M>, M> modulesByClass = new HashMap<>();
	private final HashMap<String, M> modulesByName = new HashMap<>();
	private final HashSet<M> modules = new HashSet<>();

	private Class[] moduleClasses = {};

	public MM()
	{
		addModules();
	}

	public ArrayList<M> getModules()
	{
		ArrayList<M> arrayList = new ArrayList<>(modules);
		arrayList.sort(Comparator.comparing(M::getName));
		return arrayList;
	}

	public int getSizeOfModulesByCategory(C category) {
		ArrayList<M> arrayList = getModules();
		int size = 0;

		for (int i = 0; i < arrayList.size(); i++) {
			if (arrayList.get(i).getCategory() == category)
				size++;
		}
		return size;
	}

	public int getSizeOfModulesByCategory(String categoryName) {
		return getSizeOfModulesByCategory(getCategoryByName(categoryName));
	}

	public C getCategoryByName(String categoryName) {

		C[] categories = { C.CLIENT, C.COMBAT, C.RENDER, C.MISC};

		for (C category : categories) {
			if (category.toString().equals(categoryName)) {
				return category;
			}
		}

		return null;

	}

	public M getModule(Class<? extends M> clazz)
	{
		return modulesByClass.get(clazz);
	}

	public M getModuleByName(String name)
	{
		return modulesByName.get(name);
	}

	private void addModules() {

		addModule(AC.class);
		addModule(ADH.class);
		addModule(AR.class);
		addModule(CC.class);
		addModule(AL.class);
		addModule(LR.class);
		addModule(TB.class);
		addModule(ART.class);
		addModule(AD.class);
		addModule(AHC.class);
		addModule(CG.class);
		addModule(FC.class);
		addModule(MCr.class);
		addModule(AM.class);
		addModule(AX.class);
		addModule(MCP.class);
		addModule(DC.class);
		addModule(SD.class);
		addModule(NHC.class);
		addModule(AP.class);
		addModule(VM.class);
		addModule(H.class);
		addModule(AMC.class);
		addModule(CO.class);
        addModule(AW.class);
        addModule(BM.class);
        addModule(LLY.class);
		addModule(MCPe.class);
		addModule(WACR.class);
	}

	private void addModule(Class<? extends M> clazz) {
		try {
			M module = clazz.getConstructor().newInstance();
			modulesByClass.put(clazz, module);
			modulesByName.put(module.getName(), module);
			modules.add(module);
		} catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
				 InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public void removeModules() {
		for (M module : this.getModules()) {
			module.cleanup();
			removeModule(module.getClass());
		}
	}

	private void removeModule(Class<? extends M> clazz) {
		modulesByClass.remove(clazz);
		modulesByName.remove(clazz);
		modules.remove(clazz);
	}
}
