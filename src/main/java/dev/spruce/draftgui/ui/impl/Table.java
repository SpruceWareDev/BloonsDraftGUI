package dev.spruce.draftgui.ui.impl;

import com.raylib.Colors;
import com.raylib.Raylib;
import dev.spruce.draftgui.ui.UIComponent;
import dev.spruce.draftgui.utils.Colours;
import dev.spruce.draftgui.utils.RenderUtils;

import java.util.ArrayList;
import java.util.List;

public class Table extends UIComponent {

    private final int CELL_FONT_SIZE = 22;
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
        Raylib.DrawRectangle((int) this.getX(), (int) this.getY(), (int) this.getWidth(), (int) this.rowHeight, Colours.DARKEST);
        int headingCounter = 0;
        for (String heading : this.headings) {
            float cellWidth = this.getWidth() / this.headings.size();
            RenderUtils.DrawTextAShadow(heading, (int) (this.getX() + (cellWidth * headingCounter)) + 4,
                    (int) (this.getY() + (this.rowHeight / 2)) - 11, 22, 4, Colors.WHITE
            );
            headingCounter++;
        }

        int rowCounter = 1;
        for (Row row : this.rows) {
            final float cellWidth = this.getWidth() / this.headings.size();

            Raylib.DrawRectangle((int) this.getX(), (int) (this.getY() + (this.rowHeight * rowCounter)),
                    (int) this.getWidth(), (int) this.rowHeight, Colours.DARKER);

            headingCounter = 0;
            for (String cell : row.getCells()) {
                float cellX = this.getX() + (cellWidth * headingCounter);
                float cellY = this.getY() + (this.rowHeight * rowCounter);

                RenderUtils.DrawTextAShadow(
                        cell,
                        (int) cellX + 4,
                        (int) (cellY + (this.rowHeight / 2)) - CELL_FONT_SIZE / 2,
                        CELL_FONT_SIZE, 4, row.getCellColor(cell)
                );
                headingCounter++;
            }
            rowCounter++;
        }
    }

    @Override
    public void update() {

    }

    public void addRow(List<String> cells) {
        this.rows.add(new Row(cells));
    }

    public void addRow(Row row) {
        this.rows.add(row);
    }

    public static class Row {
        private final List<String> cells;
        private final List<Raylib.Color> cellColors;

        public Row(List<String> cells) {
            this.cells = cells;
            this.cellColors = List.of(Colors.WHITE, Colors.WHITE, Colors.WHITE, Colors.WHITE, Colors.WHITE);
        }

        public Row(List<String> cells, List<Raylib.Color> cellColors) {
            this.cells = cells;
            this.cellColors = cellColors;
        }

        public List<String> getCells() {
            return cells;
        }

        public List<Raylib.Color> getCellColors() {
            return cellColors;
        }

        public Raylib.Color getCellColor(String cellValue) {
            return cellColors.get(cells.indexOf(cellValue));
        }
    }
}
