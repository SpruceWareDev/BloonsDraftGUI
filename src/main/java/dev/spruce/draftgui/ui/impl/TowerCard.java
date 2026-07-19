package dev.spruce.draftgui.ui.impl;

import com.raylib.Colors;
import com.raylib.Raylib;
import dev.spruce.draftgui.game.Tower;
import dev.spruce.draftgui.ui.UIComponent;
import dev.spruce.draftgui.utils.Colours;
import dev.spruce.draftgui.utils.Rectangle;
import dev.spruce.draftgui.utils.RenderUtils;

public class TowerCard extends UIComponent {

    private final Tower tower;
    private final float targetX, targetY;
    private final Runnable onClick;

    public TowerCard(Tower tower, float targetX, float targetY, float width, float height, Runnable onClick) {
        super(Raylib.GetRenderWidth() / 2f, Raylib.GetRenderHeight(), width, height);
        this.targetX = targetX;
        this.targetY = targetY;
        this.tower = tower;
        this.onClick = onClick;
    }

    @Override
    public void render() {
        setX((float) RenderUtils.larp(getX(), targetX, 0.1f));
        setY((float) RenderUtils.larp(getY(), targetY, 0.1f));


        Raylib.Rectangle rect = new Rectangle(getX(), getY(), getWidth(), getHeight()).toRaylibRectangle();
        Raylib.DrawRectangleRounded(rect, 0.1f, 4, Colours.DARKER);
        rect.deallocate();

        RenderUtils.DrawTextAShadow(
                this.tower.getName(),
                (int) (getX() + getWidth() / 2f - Raylib.MeasureText(this.tower.getName(), 20) / 2f),
                (int) (getY() + getHeight() - 50),
                20,
                Colors.WHITE);

        RenderUtils.drawTexture(
                this.tower.getTexture(),
                (int) (getX() + getWidth() / 2f - (tower.getTexture().width() / 2)),
                (int) (getY() + getHeight() / 2f - (tower.getTexture().height() / 2)),
                tower.getTexture().width(), tower.getTexture().height(), 0, Colors.WHITE
        );
    }

    @Override
    public void update() {
        boolean isMouseOver = new Rectangle(getX(), getY(), getWidth(), getHeight()).contains(Raylib.GetMousePosition().x(), Raylib.GetMousePosition().y());
        if (Raylib.IsMouseButtonPressed(0) && isMouseOver) {
            this.onClick.run();
        }
    }

    public void unloadTowerTexture() {
        this.tower.unloadTexture();
    }
}
