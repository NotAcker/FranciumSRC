package org.apache.core;

import org.apache.core.e.EM;
import org.apache.core.g.CG;
import org.apache.core.k.KM;
import org.apache.core.m.MM;
import org.apache.core.t.IFont;
import org.apache.core.u.CU;
import org.apache.core.u.HwU;
import org.apache.core.u.RU;
import org.apache.core.c.CDT;
import org.apache.core.c.PAS;
import org.apache.core.c.Ro;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public enum Client
{

	INSTANCE;

	public static MinecraftClient MC;

    private RU renderUtils;
	private EM eventManager;
	private MM moduleManager;
	private KM keybindManager;
	private CG gui;
	private Boolean guiInitialized = false;
	private Boolean beta = true;
	private CDT crystalDataTracker;
	private PAS playerActionScheduler;
	private Ro rotator;

    public void load() {
		Client instance = INSTANCE;
		init();
    }

	public void init() {
        renderUtils = new RU();
		MC = MinecraftClient.getInstance();
		eventManager = new EM();
		moduleManager = new MM();
		keybindManager = new KM();
		gui = new CG();
		crystalDataTracker = new CDT();
		playerActionScheduler = new PAS();
		rotator = new Ro();
	}


	public void panic() {
		guiInitialized = null;
		beta = null;
		MC = null;
		eventManager = null;
		moduleManager = null;
		keybindManager = null;
		gui = null;
		crystalDataTracker = null;
		playerActionScheduler = null;
		rotator = null;
		IFont.fontPath = null;
		CU.cleanString();
	}

	public MinecraftClient MC() {
		return MinecraftClient.getInstance();
	}

    public RU renderUtils() { return renderUtils; }

	public EM eventManager() { return eventManager; }

	public MM moduleManager()
	{
		return moduleManager;
	}

	public KM keybindManager()
	{
		return keybindManager;
	}

	public CG clickGui() {
		if (!guiInitialized) {
			gui.init();
			guiInitialized = true;
		}

		return gui;
	}

	public CDT crystalDataTracker() {
		return crystalDataTracker;
	}

	public PAS playerActionScheduler() {
		return playerActionScheduler;
	}

	public Ro rotator() {
		return rotator;
	}

}
