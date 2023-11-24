package org.apache.core.g.c;

import com.mojang.blaze3d.systems.RenderSystem;
import org.apache.core.g.w.W;
import org.apache.core.m.ms.c.CG;
import org.apache.core.t.IFont;
import org.apache.core.Client;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.awt.*;

import static org.apache.core.Client.MC;

public abstract class C {

    public final W parent;
    private double x, y;
    private final double length;
    private final String name;

    public C(W parent, double x, double y, double length, String name) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.length = length;
        this.name = name;
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
    {
        double parentX = parent.getX();
        double parentY = parent.getY();
        double parentWidth = parent.getWidth();
        double parentLength = parent.getLength();
        double parentX2 = parent.getX() + parentWidth;
        double parentY2 = parent.getY() + parentLength;
        double x = getX() + parentX;
        double y = getY() + parentY - 10;
        if (getY() + parentY - parentY - 10 <= 0)
            return;
        if (parentY2 - (getY() + parentY) <= 0)
            return;

        if (CG.class.cast(Client.INSTANCE.moduleManager().getModule(CG.class)).customFont.get()) {
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            IFont.COMFORTAA.drawString(matrices, name, (float) x, (float) y, new Color(255, 255, 255).getRGB(), false);
        } else {
            MC.textRenderer.drawTrimmed(Text.literal(name), (int) x, (int) y, (int) (parentX2 - x), 0xFFFFFF);
        }

    }

    public void onMouseMoved(double mouseX, double mouseY) {

    }

    public void onMouseClicked(double mouseX, double mouseY, int button) {

    }

    public boolean onMouseScrolled(double mouseX, double mouseY, double amount) {
        return false;
    }

    public boolean onMouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return false;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
