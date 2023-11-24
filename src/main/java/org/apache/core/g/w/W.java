package org.apache.core.g.w;


import org.apache.core.m.ms.c.CG;
import org.apache.core.t.IFont;
import org.apache.core.u.RU;
import org.apache.core.Client;
import com.mojang.blaze3d.systems.RenderSystem;
import org.apache.core.g.c.C;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

import static org.apache.core.Client.MC;

public class W {
    public final org.apache.core.g.CG parent;
    private double x, y;
    private double width, length;
    private double scrollAmount = 0;
    protected boolean minimized = false;
    protected ArrayList<C> components = new ArrayList<>();
    private String title = "";
    private boolean isDraggable = true;
    private boolean draggable = true;
    public boolean closable = false;
    public boolean minimizable = true;
    private boolean resizable = true;
    private boolean pinnable = true;

    public W(org.apache.core.g.CG parent, double x, double y, double width, int length) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.width = width;
        this.length = length;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addComponent(C component) {
        components.add(component);
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        float r = (float) CG.class.cast(Client.INSTANCE.moduleManager().getModule(CG.class)).getHudColorRed();
        float g = (float) CG.class.cast(Client.INSTANCE.moduleManager().getModule(CG.class)).getHudColorGreen();
        float b = (float) CG.class.cast(Client.INSTANCE.moduleManager().getModule(CG.class)).getHudColorBlue();

        int r1 = (int) CG.class.cast(Client.INSTANCE.moduleManager().getModule(CG.class)).getRed();
        int g1 = (int) CG.class.cast(Client.INSTANCE.moduleManager().getModule(CG.class)).getGreen();
        int b1 = (int) CG.class.cast(Client.INSTANCE.moduleManager().getModule(CG.class)).getBlue();

        TextRenderer textRenderer = MC.textRenderer;
        if(length==20){
            return;
        }
        if (!minimized) {
            RenderSystem.setShader(GameRenderer::getPositionShader);

            if (!closable) {
                this.length = Client.INSTANCE.moduleManager().getSizeOfModulesByCategory(title) * 16d + 19d;
            }

            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glEnable(GL11.GL_BLEND);
            Client.INSTANCE.renderUtils().fillGradient(matrices, (int) x, (int) y, (int) (x + width),  (int) (y + length), new Color(r1, g1, b1, 50).getRGB(), new Color(r1, g1, b1, 100).getRGB());

            for (C component : components) {
                GL11.glDisable(GL11.GL_CULL_FACE);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                RenderSystem.lineWidth(1);
                component.render(matrices, mouseX, mouseY, delta);
                GL11.glEnable(GL11.GL_CULL_FACE);
                GL11.glDisable(GL11.GL_BLEND);
            }
        }
        if (draggable) {
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glEnable(GL11.GL_BLEND);
            RenderSystem.setShader(GameRenderer::getPositionShader);
            RenderSystem.setShaderColor((float) r, (float) g, (float) b, 1.0f);
            RU.drawQuad(x, y, x + width, y + 1, matrices);
            RU.drawQuad(x, y + 15, x + width, y + 16, matrices);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
        }
        if (closable) {
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glEnable(GL11.GL_BLEND);
            RenderSystem.setShader(GameRenderer::getPositionShader);
            RenderSystem.setShaderColor(1.0f, 0.2f, 0.2f, 1.0f);
            double x = getX() + width - 12;
            double y = getY() + 2;

            if (CG.class.cast(Client.INSTANCE.moduleManager().getModule(CG.class)).customFont.get()) {
                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                IFont.COMFORTAA.drawString(matrices, "x", (float) (x + 1f),  (float) (y), new Color(255, 255, 255).getRGB(), false);
            } else {
                textRenderer.draw(matrices, "x", (float) (x + 2.5f), (float) y, 0xFFFFFFFF);
            }

            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);


        }
        if (minimizable) {
            double x = getX() + width - 12;
            double y = getY() + 2;

            textRenderer.draw(matrices, minimized ? "-" : "+", (float) (x + 1), (float) (y + 2), 0xFFFFFFFF);
        }


        if (!minimized) {
            RenderSystem.setShader(GameRenderer::getPositionShader);
            RenderSystem.setShaderColor((float) r, (float) g, (float) b, 1.0f);
            RU.drawQuad(x, y, x + 1, y + length, matrices);
            RU.drawQuad(x, y + length, x + width, y + length + 1, matrices);
            RU.drawQuad(x + width - 1, y, x + width, y + length, matrices);
        }

        double textX = x;
        double textY = y + (width / 2) - 57;

        if (closable) {
            textY = y + 3.5f;
        } else if (!closable) {
            textY = textY + 5f;
        }

        if (minimized) {
            RenderSystem.setShader(GameRenderer::getPositionShader);
            RenderSystem.setShaderColor(r, g, b, 1.0f);
            RU.drawQuad(x, y, x + 1, y + 15, matrices);
            RU.drawQuad(x + width - 1, y, x + width, y + 15, matrices);
            Client.INSTANCE.renderUtils().fillGradient(matrices, (int) x, (int) y, (int) (x + width),  (int) (y + 15), new Color(r1, g1, b1, 50).getRGB(), new Color(r1, g1, b1, 100).getRGB());
        }

        if (CG.class.cast(Client.INSTANCE.moduleManager().getModule(CG.class)).customFont.get()) {
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glEnable(GL11.GL_BLEND);
            RenderSystem.setShaderColor((float) 1.0f, (float) 1.0f, (float) 1.0f, 1.0f);
            IFont.COMFORTAA.drawCenteredString(matrices, title, (double) (textX + getWidth() / 2), (double) textY, new Color(255, 255, 255).getRGB());
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
        } else {
            textY = y + 4;
            RU.typeCentered(matrices, title, (int) (textX + getWidth() / 2), (int) textY, 0xFFFFFF);
        }

    }

    public void onMouseMoved(double mouseX, double mouseY) {
        for (C component : components) {
            component.onMouseMoved(mouseX, mouseY);
        }
    }

    public void onMouseClicked(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
            if (closable) {
                double x = getX() + width - 10;
                double y = getY();
                if (RU.isHoveringOver(mouseX, mouseY, x, y, x + 10, y + 10))
                    parent.close(this);
            }
            if (minimizable) {
                double x = getX() + width - 10;
                double y = getY();
                if (RU.isHoveringOver(mouseX, mouseY, x, y, x + 10, y + 10))
                    minimized = !minimized;
            }
        }
        if (!minimized && !canDrag(mouseX, mouseY)) {
            if (RU.isHoveringOver(mouseX, mouseY, x, draggable ? y + 10 : y, x + width, y + length)) {
                for (C component : components) {
                    component.onMouseClicked(mouseX, mouseY, button);
                }
            }
        }
    }

    public void onMouseScrolled(double mouseX, double mouseY, double amount) {
        if (minimized)
            return;
        if (!RU.isHoveringOver(mouseX, mouseY, x, y, x + width, y + length))
            return;
        for (C component : components) {
            if (component.onMouseScrolled(mouseX, mouseY, amount))
                return;
        }

        scrollAmount += amount * 2;
        if (scrollAmount > 0)
            scrollAmount = 0;
        else {
            for (C component : components) {
                component.setY(component.getY() + amount * 2);
            }
        }
    }

    public boolean onMouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        for (C component : components) {
            if (component.onMouseDragged(mouseX, mouseY, button, deltaX, deltaY))
                return true;
        }
        return false;
    }

    public void onClose() {

    }

    public boolean canDrag(double mouseX, double mouseY) {
        if (!draggable)
            return false;
        return RU.isHoveringOver(mouseX, mouseY, x, y, x + width, y + 10);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public boolean isDraggable() {
        return isDraggable;
    }

    public void setIsDraggable(boolean isDraggable) {
        this.isDraggable = isDraggable;
    }

    public boolean isHoveringOver(double mouseX, double mouseY) {
        return minimized ? canDrag(mouseX, mouseY) : RU.isHoveringOver(mouseX, mouseY, x, y, x + width, y + length);
    }
}
