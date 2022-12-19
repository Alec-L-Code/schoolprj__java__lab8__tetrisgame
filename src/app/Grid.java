package app;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Grid 
{
    static public void InitGameGrid(GridPane gameScreen, Block[][] grid)  // Fills the grid with white squares so it will take shape
    {
      gameScreen.getChildren().clear();
      for(int i = 0; i < 24; i++)
      {
        for(int j = 0; j < 24; j++)
        {
          Block def = new Block(Color.WHITE, j, i);
          grid[i][j] = def;
          gameScreen.add(def, j, i);
        }
      }
    }
    static public void CheckRows(GridPane gameScreen, Text scoreText, Block[][] grid) // Checks if a row is full so it can be deleted and turned into a score
    {
      for(int i = 24 - 1; i >= 0; i--) // Row index Starting from Bottom Row Y
      {
        int nodeCount = 0;
        for(int j = 0; j < 24; j++) // Collumn index per row. X
        {
          if(getBlockFromColumnRow(gameScreen, j, i, grid) == null) { continue; }
          Color c = (Color)getBlockFromColumnRow(gameScreen, j, i, grid).getFill();
          if(c != Color.WHITE)
          {
            nodeCount++;
          }
        }
        if(nodeCount == 24)
        {
          DeleteRow(gameScreen, i, scoreText, grid);
        }
      } 
    }
    static public void AddBlockToGrid(GridPane gameScreen, Block[][] grid, Block... blockArr) // Replaces the current node in a grid with a new one. GridPane does not inherently support the replacement of nodes when placed in the same index.
    {
        try
        {
            for(Block b : blockArr)
            {
              int rowIndex = b.getRow();
              int colIndex = b.getCol();
              RemoveBlockFromGrid(gameScreen, grid, b);
              grid[rowIndex][colIndex] = b;
              gameScreen.add(b, colIndex, rowIndex);
            }
        } catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Index out of grid bounds when trying to add a node");
        }
    }
    static public void RemoveBlockFromGrid(GridPane gameScreen, Block[][] grid, Block... blockArr)
    {
      try
      {
        for(Block b : blockArr)
        {
          int rowIndex = b.getRow();
          int colIndex = b.getCol();
          gameScreen.getChildren().remove(grid[rowIndex][colIndex]);
          grid[rowIndex][colIndex] = new Block(Color.WHITE, b.getCol(), b.getRow());
          gameScreen.add(grid[rowIndex][colIndex], colIndex, rowIndex);
          gameScreen.setGridLinesVisible(false);
          gameScreen.setGridLinesVisible(true);
        }
      } catch(ArrayIndexOutOfBoundsException e)
      {
        System.out.println("Index out of grid bounds when trying to remove a node");
      }
    }
    static public void DeleteRow(GridPane gameScreen, int rowIndex, Text scoreText, Block[][] grid) // Deletes a whole row of a given row index.
    {
        for(int i = 0; i < 24; i++)
        {
          RemoveBlockFromGrid(gameScreen, grid, getBlockFromColumnRow(gameScreen, i, rowIndex, grid));
        }
        scoreText.setText(Integer.parseInt(scoreText.getText()) + 10 + "");
    }
    static public Block getBlockFromColumnRow(GridPane gameScreen, int column, int row, Block[][] gridArr)
    {
      return gridArr[row][column];
    }
}