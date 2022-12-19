package app;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/**
 * Box class for the boxes that make up shapes
 */
public class Block extends Rectangle
{
    private int rowIndex = 0;
    private int colIndex = 0;

    public Block(Color color, int col, int row)
    {
        super();
        // Setting up the general block characteristics
        this.setWidth(25);
        this.setHeight(25);
        this.setStroke(Color.WHITE);
        this.setFill(color);
        this.setArcHeight(5);
        this.setArcWidth(5);
        this.rowIndex = row;
        this.colIndex = col;
        //
    }
    public void SetColor(Color color)
    {
        this.setFill(color);
    }
    public void setRow(int row) { this.rowIndex = row; }
    public void setCol(int col) { this.colIndex = col; }
    public int getRow() { return this.rowIndex; }
    public int getCol() { return this.colIndex; }
}