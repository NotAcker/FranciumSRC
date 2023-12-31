package org.apache.core.g.c;

import com.mojang.blaze3d.systems.RenderSystem;
import org.apache.core.m.ms.c.CG;
import org.apache.core.t.IFont;
import org.apache.core.u.RU;
import org.apache.core.Client;
import org.apache.core.g.w.W;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;

import java.util.function.Supplier;

import static org.apache.core.Client.MC;

public class BC extends C {

    private final Runnable action;
    private final Supplier<String> display;

    public BC(W parent, double x, double y, double length, String name, Runnable action, Supplier<String> display) {
        super(parent, x, y, length, name);
        this.action = action;
        this.display = display;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        double parentX = parent.getX();
        double parentY = parent.getY();
        double parentWidth = parent.getWidth();
        double parentLength = parent.getLength();
        double parentX2 = parent.getX() + parentWidth;
        double parentY2 = parent.getY() + parentLength;
        double x = getX() + parentX;
        double y = Math.max(getY() + parentY, parentY);
        double x2 = Math.min(x + getLength(), parentX2);
        double y2 = Math.min(y + 10, parentY2);
        if (getY() + 10 <= 0)
            return;
        if (parentY2 - (getY() + parentY) <= 0)
            return;
        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(0.1f, 0.1f, 0.1f, 0.4f);
        if (RU.isHoveringOver(mouseX, mouseY, x, y, x2, y2))
            RenderSystem.setShaderColor(0.15f, 0.15f, 0.15f, 1.0f);
        RU.drawQuad(x, y, x2, y2, matrices);

        if (CG.class.cast(Client.INSTANCE.moduleManager().getModule(CG.class)).customFont.get()) {
            RenderSystem.setShaderColor((float) 1.0f, (float) 1.0f, (float) 1.0f, 1.0f);
            IFont.COMFORTAA.drawString(matrices, display.get(), (float) (x + 1.5f), (float) (y + 1), 0xffffff, false);
        } else {
            MC.textRenderer.draw(matrices, display.get(), (float) x+1.5f, (float) y+1, 0xffffff);
        }
    }

    @Override
    public void onMouseClicked(double mouseX, double mouseY, int button) {
        double parentX = parent.getX();
        double parentY = parent.getY();
        double parentWidth = parent.getWidth();
        double parentLength = parent.getLength();
        double parentX2 = parent.getX() + parentWidth;
        double parentY2 = parent.getY() + parentLength;
        double x = getX() + parentX;
        double y = Math.max(getY() + parentY, parentY);
        double x2 = Math.min(x + getLength(), parentX2);
        double y2 = Math.min(y + 10, parentY2);
        if (getY() + 10 <= 0)
            return;
        if (parentY2 - (getY() + parentY) <= 0)
            return;
        if (RU.isHoveringOver(mouseX, mouseY, x, y, x2, y2)) {
            if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
                action.run();
            }
        }
    }
}
