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

        if (dataPoints == null || dataPoints.size() < 2) {
            return;
        }

        // Find the min and max values in the dataPoints to normalize them
        float minValue = Float.MAX_VALUE;
        float maxValue = Float.MIN_VALUE;
        for (float value : dataPoints) {
            if (value < minValue) minValue = value;
            if (value > maxValue) maxValue = value;
        }

        // Avoid division by zero if all data points are the same
        float range = maxValue - minValue;
        if (range == 0) {
            range = 1;
        }

        // Draw the lines
        for (int i = 0; i < dataPoints.size() - 1; i++) {
            float normalizedY1 = (dataPoints.get(i) - minValue) / range;
            float normalizedY2 = (dataPoints.get(i + 1) - minValue) / range;

            float x1 = x + (i * (width / (dataPoints.size() - 1)));
            float y1 = y + height - (normalizedY1 * height);
            float x2 = x + ((i + 1) * (width / (dataPoints.size() - 1)));
            float y2 = y + height - (normalizedY2 * height);

            Raylib.DrawLine((int) x1, (int) y1, (int) x2, (int) y2, Colors.BLUE);
        }

        // Draw proportional height values on the side
        Raylib.DrawText(String.format("%.2f", maxValue), (int) x - 40, (int) y - 10, 20, Colors.BLACK);
        Raylib.DrawText(String.format("%.2f", minValue), (int) x - 40, (int) (y + height - 10), 20, Colors.BLACK);
    }

    @Override
    public void update() {

    }
}
