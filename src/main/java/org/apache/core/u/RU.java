package org.apache.core.u;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

import static org.apache.core.Client.MC;
import static org.lwjgl.glfw.GLFW.*;

public class RU extends DrawableHelper {

	public static void fillBox(BufferBuilder bufferBuilder, Box bb, MatrixStack matrixStack)
	{
		Matrix4f matrix = matrixStack.peek().getPositionMatrix();
		bufferBuilder
				.vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.minZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.minZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.maxZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.maxZ)
				.next();

		bufferBuilder
				.vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.minZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.maxZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.maxZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.minZ)
				.next();

		bufferBuilder
				.vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.minZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.minZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.minZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.minZ)
				.next();

		bufferBuilder
				.vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.minZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.minZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.maxZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.maxZ)
				.next();

		bufferBuilder
				.vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.maxZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.maxZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.maxZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.maxZ)
				.next();

		bufferBuilder
				.vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.minZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.maxZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.maxZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.minZ)
				.next();
	}

	public static void drawSolidBox(Box bb, MatrixStack matrixStack)
	{
		BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
		RenderSystem.setShader(GameRenderer::getPositionShader);

		bufferBuilder.begin(VertexFormat.DrawMode.QUADS,
				VertexFormats.POSITION);
		fillBox(bufferBuilder, bb, matrixStack);
		BufferRenderer.drawWithShader(bufferBuilder.end());
	}

    public static void drawItem(ItemStack itemStack, int x, int y, double scale, boolean overlay) {
        RenderSystem.disableDepthTest();

        MatrixStack matrices = RenderSystem.getModelViewStack();

        matrices.push();
        matrices.scale((float) scale, (float) scale, 1);

        MC.getItemRenderer().renderGuiItemIcon(itemStack, (int) (x / scale), (int) (y / scale));
        if (overlay)
            MC.getItemRenderer().renderGuiItemOverlay(MC.textRenderer, itemStack, (int) (x / scale), (int) (y / scale), null);

        matrices.pop();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.enableDepthTest();
    }

	public static void fillOutlinedBox(BufferBuilder bufferBuilder, Box bb, MatrixStack matrixStack)
	{
		Matrix4f matrix = matrixStack.peek().getPositionMatrix();
		bufferBuilder
				.vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.minZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.minZ)
				.next();

		bufferBuilder
				.vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.minZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.maxZ)
				.next();

		bufferBuilder
				.vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.maxZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.maxZ)
				.next();

		bufferBuilder
				.vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.maxZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.minZ)
				.next();

		bufferBuilder
				.vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.minZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.minZ)
				.next();

		bufferBuilder
				.vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.minZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.minZ)
				.next();

		bufferBuilder
				.vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.maxZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.maxZ)
				.next();

		bufferBuilder
				.vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.maxZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.maxZ)
				.next();

		bufferBuilder
				.vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.minZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.minZ)
				.next();

		bufferBuilder
				.vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.minZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.maxZ)
				.next();

		bufferBuilder
				.vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.maxZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.maxZ)
				.next();

		bufferBuilder
				.vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.maxZ)
				.next();
		bufferBuilder
				.vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.minZ)
				.next();
	}

	public static void drawOutlinedBox(Box bb, MatrixStack matrixStack)
	{
		BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
		RenderSystem.setShader(GameRenderer::getPositionShader);

		bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES,
				VertexFormats.POSITION);
		fillOutlinedBox(bufferBuilder, bb, matrixStack);
		BufferRenderer.drawWithShader(bufferBuilder.end());
	}

	public static void drawQuad(float x1, float y1, float x2, float y2, MatrixStack matrixStack)
	{
		Matrix4f matrix = matrixStack.peek().getPositionMatrix();
		BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
		RenderSystem.setShader(GameRenderer::getPositionShader);
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);

		bufferBuilder.vertex(matrix, x1, y2, 0).next();
		bufferBuilder.vertex(matrix, x2, y2, 0).next();
		bufferBuilder.vertex(matrix, x2, y1, 0).next();
		bufferBuilder.vertex(matrix, x1, y1, 0).next();

		BufferRenderer.drawWithShader(bufferBuilder.end());
	}

	public static void drawQuad(double x1, double y1, double x2, double y2, MatrixStack matrixStack)
	{
		drawQuad((float) x1, (float) y1, (float) x2, (float) y2, matrixStack);
	}

	public static void drawOutlinedQuad(float x1, float y1, float x2, float y2, MatrixStack matrixStack)
	{
		Matrix4f matrix = matrixStack.peek().getPositionMatrix();
		BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
		RenderSystem.setShader(GameRenderer::getPositionShader);
		bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION);

		bufferBuilder.vertex(matrix, x1, y2, 0).next();
		bufferBuilder.vertex(matrix, x2, y2, 0).next();
		bufferBuilder.vertex(matrix, x2, y1, 0).next();
		bufferBuilder.vertex(matrix, x2, y2, 0).next();
		bufferBuilder.vertex(matrix, x1, y1, 0).next();
		bufferBuilder.vertex(matrix, x1, y2, 0).next();
		bufferBuilder.vertex(matrix, x1, y1, 0).next();
		bufferBuilder.vertex(matrix, x2, y1, 0).next();

		BufferRenderer.drawWithShader(bufferBuilder.end());
	}

	public static void drawOutlinedQuad(double x1, double y1, double x2, double y2, MatrixStack matrixStack)
	{
		drawOutlinedQuad((float) x1, (float) y1, (float) x2, (float) y2, matrixStack);
	}

	public static boolean isHoveringOver(double mouseX, double mouseY, double x1, double y1, double x2, double y2)
	{
		return mouseX > Math.min(x1, x2) && mouseX < Math.max(x1, x2) && mouseY > Math.min(y1, y2) && mouseY < Math.max(y1, y2);
	}

	public static Vec3d getCameraPos()
	{
		return MC.getBlockEntityRenderDispatcher().camera.getPos();
	}

	public static BlockPos getCameraBlockPos()
	{
		return MC.getBlockEntityRenderDispatcher().camera.getBlockPos();
	}

	public static void applyRegionalRenderOffset(MatrixStack matrixStack)
	{
		Vec3d camPos = getCameraPos();
		BlockPos blockPos = getCameraBlockPos();

		int regionX = (blockPos.getX() >> 9) * 512;
		int regionZ = (blockPos.getZ() >> 9) * 512;

		matrixStack.translate(regionX - camPos.x, -camPos.y, regionZ - camPos.z);
	}

	public static void typeCentered(MatrixStack stack, String text, int centerX, int y, int color) {
		MC.textRenderer.drawWithShadow(stack, text, centerX - MC.textRenderer.getWidth(text) / 2, y, color);
	}

	public static void typeCenteredTrimmed(MatrixStack stack, String text, float centerX, float y, int maxWidth, int color) {
        MC.textRenderer.drawTrimmed(Text.literal(text), (int) centerX - MC.textRenderer.getWidth(text) / 2, (int) y, maxWidth, color);
	}

	public static String getKeyName(int keyCode) {

		String keyName = String.valueOf(GLFW.glfwGetKeyName(keyCode, 0));

		if (keyName != "null")
			return keyName.toUpperCase();

		Integer[] keyCodes = {GLFW_KEY_ESCAPE, GLFW_KEY_ENTER, GLFW_KEY_TAB, GLFW_KEY_BACKSPACE, GLFW_KEY_INSERT,
				GLFW_KEY_DELETE, GLFW_KEY_RIGHT, GLFW_KEY_LEFT, GLFW_KEY_DOWN, GLFW_KEY_UP, GLFW_KEY_PAGE_UP,
				GLFW_KEY_PAGE_DOWN, GLFW_KEY_HOME, GLFW_KEY_END, GLFW_KEY_CAPS_LOCK, GLFW_KEY_SCROLL_LOCK,
				GLFW_KEY_NUM_LOCK, GLFW_KEY_PRINT_SCREEN, GLFW_KEY_PAUSE, GLFW_KEY_F1, GLFW_KEY_F2, GLFW_KEY_F3,
				GLFW_KEY_F4, GLFW_KEY_F5, GLFW_KEY_F6, GLFW_KEY_F7, GLFW_KEY_F8, GLFW_KEY_F9, GLFW_KEY_F10,
				GLFW_KEY_F11, GLFW_KEY_F12, GLFW_KEY_KP_0, GLFW_KEY_KP_1, GLFW_KEY_KP_2, GLFW_KEY_KP_3,
				GLFW_KEY_KP_4, GLFW_KEY_KP_5, GLFW_KEY_KP_6, GLFW_KEY_KP_7, GLFW_KEY_KP_8, GLFW_KEY_KP_9,
				GLFW_KEY_KP_DECIMAL, GLFW_KEY_KP_DIVIDE, GLFW_KEY_KP_MULTIPLY, GLFW_KEY_KP_SUBTRACT, GLFW_KEY_KP_ADD,
				GLFW_KEY_KP_ENTER, GLFW_KEY_KP_EQUAL, GLFW_KEY_LEFT_SHIFT, GLFW_KEY_LEFT_CONTROL, GLFW_KEY_LEFT_ALT,
				GLFW_KEY_LEFT_SUPER, GLFW_KEY_RIGHT_SHIFT, GLFW_KEY_RIGHT_CONTROL, GLFW_KEY_RIGHT_ALT,
				GLFW_KEY_RIGHT_SUPER, GLFW_KEY_MENU};
		String[] keyNames = {"ESC", "ENT", "TAB", "BSPC", "INS", "DEL", "RIGHT", "LEFT", "DOWN", "UP", "KP UP",
				"PD", "HOME", "END", "CAPS", "SL", "NL", "PS", "PAUSE", "F1", "F2", "F3", "F4", "F5", "F6", "F7",
				"F8", "F9", "F10", "F11", "F12", "KP 0", "KP 1", "KP 2", "KP 3", "KP 4", "KP 5", "KP 6", "KP 7", "KP 8",
				"KP 9", "KP DEC", "KP DIV", "KP MUL", "KP SUB", "KP ADD", "KP ENT", "KP EQ", "LSHFT", "LCTRL", "LALT",
				"LSPR", "RSHFT", "RCTRL", "RALT", "RSPR", "MENU"};

		for (int i = 0; i < keyCodes.length; i++) {
			if (keyCode == keyCodes[i]) {
				return keyNames[i];
			}
		}

		return "NUL";
	}

    public static Vector3d set(Vector3d vec, Vec3d v) {
        vec.x = v.x;
        vec.y = v.y;
        vec.z = v.z;

        return vec;
    }

    public void fillGradient(MatrixStack matrices, int startX, int startY, int endX, int endY, int colorStart, int colorEnd) {
        super.fillGradient(matrices, startX, startY, endX, endY, colorStart, colorEnd, 0);
    }

    public void drawHorizontalLine(MatrixStack matrices, int x1, int x2, int y, int color) {
        super.drawHorizontalLine(matrices, x1, x2, y, color);
    }

    public void drawVerticalLine(MatrixStack matrices, int x, int y1, int y2, int color) {
        super.drawVerticalLine(matrices, x, y1, y2, color);
    }

}
