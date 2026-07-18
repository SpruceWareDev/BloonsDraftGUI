package dev.spruce.draftgui.ui.impl;

import com.raylib.Colors;
import com.raylib.Raylib;
import dev.spruce.draftgui.ui.UIComponent;
import dev.spruce.draftgui.utils.Colours;
import dev.spruce.draftgui.utils.RenderUtils;

import java.util.ArrayList;
import java.util.List;

public class Table extends UIComponent {

    private final float rowHeight;
    private final List<String> headings;
    private final List<Row> rows;

    public Table(List<String> headings, float x, float y, float width, float rowHeight) {
        super(x, y, width, rowHeight);
        this.headings = headings;
        this.rowHeight = rowHeight;
        this.rows = new ArrayList<>();
    }

    @Override
    public void render() {
        int rowCounter = 0;

        Raylib.DrawRectangle((int) this.getX(), (int) this.getY(), (int) this.getWidth(), (int) this.rowHeight, Colours.DARKEST);
        for (String heading : this.headings) {
            float cellWidth = this.getWidth() / this.headings.size();
            RenderUtils.DrawTextAShadow(heading, (int) (this.getX() + (cellWidth * rowCounter)) + 4,
                    (int) (this.getY() + (this.rowHeight / 2)) - 11, 22, 4, Colors.WHITE
            );
            rowCounter++;
        }

        for (Row row : this.rows) {
            rowCounter = 0;
            float cellWidth = this.getWidth() / this.headings.size();
            for (String cell : row.getCells()) {
                Raylib.DrawRectangle((int) (this.getX() + (cellWidth * rowCounter)), (int) (this.getY() + (this.rowHeight * (this.rows.indexOf(row) + 1))),
                        (int) cellWidth, (int) this.rowHeight, Colours.DARKER);
                RenderUtils.DrawTextAShadow(cell, (int) (this.getX() + (cellWidth * rowCounter)) + 4,
                        (int) (this.getY() + (this.rowHeight * (this.rows.indexOf(row) + 1)) + (this.rowHeight / 2)) - 11, 22, 3, Colors.WHITE
                );
                rowCounter++;
            }
        }
    }

    @Override
    public void update() {

    }

    public void addRow(List<String> cells) {
        this.rows.add(new Row(cells));
    }

    private class Row {
        private final List<String> cells;

        public Row(List<String> cells) {
            this.cells = cells;
        }

        public List<String> getCells() {
            return cells;
        }
    }
}
