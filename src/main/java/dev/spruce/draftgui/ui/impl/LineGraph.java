package dev.spruce.draftgui.ui.impl;

import com.raylib.Colors;
import com.raylib.Raylib;
import dev.spruce.draftgui.ui.UIComponent;

import java.util.List;

public class LineGraph extends UIComponent {

    private List<Float> dataPoints;

    public LineGraph(float x, float y, float width, float height, List<Float> dataPoints) {
        super(x, y, width, height);
        this.dataPoints = dataPoints;
    }

    @Override
    public void render() {
        Raylib.DrawRectangle((int) x, (int) y, (int) width, (int) height, Colors.LIGHTGRAY);
        if (dataPoints.size() < 2) {
            return;
        }
        for (int i = 0; i < dataPoints.size() - 1; i++) {
            float x1 = x + (i * (width / (dataPoints.size() - 1)));
            float y1 = y + height - (dataPoints.get(i) * height);
            float x2 = x + ((i + 1) * (width / (dataPoints.size() - 1)));
            float y2 = y + height - (dataPoints.get(i + 1) * height);
            Raylib.DrawLine((int) x1, (int) y1, (int) x2, (int) y2, Colors.BLUE);
        }
    }

    @Override
    public void update() {

    }
}
