package org.apache.core.g.c;

import org.apache.core.m.ms.c.CG;
import org.apache.core.t.IFont;
import org.apache.core.u.RU;
import org.apache.core.Client;
import com.mojang.blaze3d.systems.RenderSystem;
import org.apache.core.g.w.W;
import org.apache.core.m.M;
import net.minecraft.client.util.math.MatrixStack;
import org.apache.core.g.w.MSW;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.apache.core.Client.MC;

public class MBC extends C {

    private final M module;
    private boolean settingWindowOpened = false;
    private MSW moduleSettingWindow;

    public MBC(W parent, M module, double x, double y) {
        super(parent, x, y, 10, module.getName());
        this.module = module;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        float r = (float) CG.class.cast(Client.INSTANCE.moduleManager().getModule(CG.class)).getHudColorRed();
        int r1 = CG.class.cast(Client.INSTANCE.moduleManager().getModule(CG.class)).getRed();
        float g = (float) CG.class.cast(Client.INSTANCE.moduleManager().getModule(CG.class)).getHudColorGreen();
        int g1 = CG.class.cast(Client.INSTANCE.moduleManager().getModule(CG.class)).getGreen();
        float b = (float) CG.class.cast(Client.INSTANCE.moduleManager().getModule(CG.class)).getHudColorBlue();
        int b1 = CG.class.cast(Client.INSTANCE.moduleManager().getModule(CG.class)).getBlue();
        double parentX = parent.getX();
        double parentY = parent.getY();
        double parentWidth = parent.getWidth();
        double parentLength = parent.getLength();
        double parentX2 = parent.getX() + parentWidth;
        double parentY2 = parent.getY() + parentLength;
        double x = getX() + parentX;
        double y = Math.max(getY() + parentY, parentY);
        double x2 = parentX2 - getX();
        double y2 = Math.min(getY() + parentY + 16, parentY2);
        if (getY() + 16 <= 0)
            return;
        if (parentY2 - (getY() + parentY) <= 0)
            return;

//        RenderSystem.setShader(GameRenderer::getPositionShader);
//        if (RenderUtils.isHoveringOver(mouseX, mouseY, x, y, x2, y2)) {
//            if (module.isEnabled())
//                RenderSystem.setShaderColor((float) r, (float) g, (float) b, 0.6f);
//            else
//                RenderSystem.setShaderColor((float) r, (float) g, (float) b, 0.2f);
//        } else {
//            if (module.isEnabled())
//                RenderSystem.setShaderColor((float) r, (float) g, (float) b, 0.4f);
//            else
//                RenderSystem.setShaderColor(0.05f, 0.05f, 0.05f, 0.3f);
//        }
//        RenderUtils.drawQuad(x + 3, y, x2 - 3, y2, matrices);


//        if (module.isEnabled()) {
//            RenderSystem.setShader(GameRenderer::getPositionShader);
//            RenderSystem.setShaderColor((float) r, (float) g, (float) b, 0.8f);
//            RenderUtils.drawQuad(x2 - 5, y, x2 - 3, y2, matrices);
//        }

        double textX = x;
        double textY = y + ((y2 - y) / 2) - 5;
        int color1 = new Color(255, 255, 255).getRGB();

        if (CG.class.cast(Client.INSTANCE.moduleManager().getModule(CG.class)).customFont.get()) {
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glEnable(GL11.GL_BLEND);

            if (module.isEnabled())
                RenderSystem.setShaderColor(r + 0.2f, g + 0.2f, b + 0.2f, 1.0f);
            else
                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

            IFont.COMFORTAA.drawCenteredStringWidthShadow(matrices, module.getName(), (double) (textX + parentWidth / 2), (double) textY, color1);

            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_CULL_FACE);
        } else {
            if (module.isEnabled())
                color1 = new Color(r1, g1, b1).getRGB();

            textY = y + MC.textRenderer.getWrappedLinesHeight(module.getName(), MC.textRenderer.getWidth(module.getName())) / 2;
            RU.typeCenteredTrimmed(matrices, module.getName(), (float) (textX + parentWidth / 2), (float) textY, (int) (x2 - textX), color1);
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
        double y = getY() + parentY;
        double x2 = parentX2 - getX();
        double y2 = Math.min(y + 16, parentY2);
        if (getY() + 16 <= 0)
            return;
        if (parentY2 - (getY() + parentY) <= 0)
            return;
        if (RU.isHoveringOver(mouseX, mouseY, x, y, x2, y2)) {
            if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
                module.toggle();
            } else {
                if (!settingWindowOpened && module.getSettings().size()!=0) {
                    org.apache.core.g.CG gui = parent.parent;
                    moduleSettingWindow = new MSW(gui, mouseX, mouseY, module, this);
                    gui.add(moduleSettingWindow);
                    settingWindowOpened = true;
                } else if(module.getSettings().size()!=0) {
                    parent.parent.moveToTop(moduleSettingWindow);
                }
            }
        }
    }

    public void settingWindowClosed() {
        settingWindowOpened = false;
        moduleSettingWindow = null;
    }
}
