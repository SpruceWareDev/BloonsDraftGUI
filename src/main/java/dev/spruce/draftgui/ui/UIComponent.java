package dev.spruce.draftgui.ui;

public abstract class UIComponent {

    private float x;
    private float y;
    private final float width;
    private final float height;

public UIComponent(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void render();
    public abstract void update();

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    protected void setX(float x) {
        this.x = x;
    }

    protected void setY(float y) {
        this.y = y;
    }
}
