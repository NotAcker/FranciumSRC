package org.apache.core.g;

import org.apache.core.Client;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

import static net.minecraft.text.Text.of;

public class GS extends Screen {

    private final CG gui;

    public GS() {
        super(of("gui"));
        gui = Client.INSTANCE.clickGui();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices, 0);
        super.render(matrices, mouseX, mouseY, delta);
        gui.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void renderBackground(MatrixStack matrices, int vOffset) {
        if (this.client.world != null) {
            this.fillGradient(matrices, 0, 0, this.width, this.height, new Color(0, 0, 0, 80).getRGB(), new Color(0, 0, 0, 100).getRGB());
        } else {
            this.renderBackgroundTexture(vOffset);
        }
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        gui.handleMouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        gui.handleMouseClicked(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        gui.handleMouseReleased(mouseX, mouseY, button);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        gui.handleMouseScrolled(mouseX, mouseY, amount);
        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        gui.handleMouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }
}
