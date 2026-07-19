package dev.spruce.draftgui.utils;

import com.raylib.Colors;
import com.raylib.Raylib;

public class RenderUtils {
    
    public static double larp(double start, double end, double t) {
        return start + (end - start) * t;
    }

    public static void DrawTextAShadow(String text, int posX, int posY, int fontSize, Raylib.Color tint) {
        Raylib.DrawText(text, posX + 1, posY + 1, fontSize, Colors.BLACK);
        Raylib.DrawText(text, posX, posY, fontSize, tint);
    }

    public static void DrawTextAShadow(String text, int posX, int posY, int fontSize, int iterations, Raylib.Color tint) {
        for (int i = 0; i < iterations; i++) {
            Raylib.DrawText(text, posX + (i + 1), posY + (i + 1), fontSize, Colors.BLACK);
        }
        Raylib.DrawText(text, posX, posY, fontSize, tint);
    }

    /**
     * Draws a texture on the screen.
     *
     * @param texture  The texture to draw.
     * @param x        The x-coordinate of the texture's top-left corner.
     * @param y        The y-coordinate of the texture's top-left corner.
     * @param width    The width of the texture.
     * @param height   The height of the texture.
     * @param rotation The rotation of the texture in degrees.
     * @param tint     The tint colour to apply to the texture.
     */
    public static void drawTexture(Raylib.Texture texture, float x, float y, float width, float height, float rotation, Raylib.Color tint) {
        Raylib.Rectangle sourceRect = new Raylib.Rectangle().x(0).y(0).width(texture.width()).height(texture.height());
        Raylib.Rectangle destRect = new Raylib.Rectangle().x(x).y(y).width(width).height(height);
        Raylib.Vector2 origin = new Raylib.Vector2().x(0).y(0);
        Raylib.DrawTexturePro(texture, sourceRect, destRect, origin, rotation, tint);
        sourceRect.deallocate();
        destRect.deallocate();
        origin.deallocate();
    }
}
