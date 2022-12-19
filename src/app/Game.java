package app;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Random;

enum ShapeType
{
 LSHAPE,
 LSHAPEINVERTED,
 SSHAPE,
 SSHAPEINVERTED,
 TSHAPE,
 LINE,
 SQUARE;

 public static ShapeType getRandomShape()
 {
    Random random = new Random();
    return values()[random.nextInt(values().length)];
 }
}
enum MoveType
{
    LEFT,
    RIGHT,
    DOWN
}
public class Game 
{
    private boolean shapeActive = false;
    private Stopwatch sw;
    private GridPane gameScreen;
    private Block[][] gridArr;
    private Text scoreText;
    public Block[] curShape;
    public ShapeType curShapeType;
    private GameOver gameOver;
    private Text gameOverText;
    private int currentSecond = 0;
    public Game(){}

    public void Init(Stopwatch sw, GridPane gs, Block[][] gridArr, Text scoreText, Scene mainScene, GameOver gameOver, Text gameOverText)
    {
        this.sw = sw;
        this.gameScreen = gs;
        this.gridArr = gridArr;
        this.scoreText = scoreText;
        this.gameOver = gameOver;
        this.gameOverText = gameOverText;
    }
    public void Start()
    {
        if(!shapeActive)
        {
            Grid.AddBlockToGrid(gameScreen, gridArr, generateRandShape());
        }
        currentSecond = sw.parseSecs();
        sw.getText().textProperty().addListener((observable, oldValue, newValue) -> {
            if(sw.parseSecs() >= currentSecond + 1) { Fall(); Grid.CheckRows(gameScreen, scoreText, gridArr); }
            if(sw.parseSecs() <= 2 && currentSecond == 59 || currentSecond == 60 || currentSecond == 61)
            {
                currentSecond = 0;
            }
        });
    }
    public void Fall()
    {
        shapeActive = true;
        if(sw.parseSecs() >= currentSecond + 1)
        {
            boolean bottom = moveShape(curShape, curShapeType, MoveType.DOWN);
            if(!bottom)
            {
                if(getTopOfShape().get(0).getRow() == 0)
                {
                    gameOver.gameIsOver = true;
                    gameOverText.setVisible(true);
                    sw.Reset();
                    Grid.InitGameGrid(gameScreen, gridArr);
                    shapeActive = false;
                }
                else
                {
                    Grid.CheckRows(gameScreen, scoreText, gridArr);
                    shapeActive = false;
                    Start();
                }
            }
            currentSecond = sw.parseSecs();
        }
    }
    public Block[] generateRandShape()
    {
        Random random = new Random();
        int colIndex = 24/2 - 1; // Half of the width of the grid. (middle position) X position
        int rowIndex = 0; // Starting at the top of the screen Y position
        ShapeType rand = ShapeType.getRandomShape();
        curShapeType = rand;
        Color randomColor = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1);
        switch(rand)
        {
            case LSHAPE:
                Block[] a = new Block[] {
                    new Block(randomColor, colIndex, rowIndex),
                    new Block(randomColor, colIndex, rowIndex + 1),
                    new Block(randomColor, colIndex, rowIndex + 2),
                    new Block(randomColor, colIndex + 1, rowIndex + 2)
                };
                curShape = a;
                return a;
            case LSHAPEINVERTED:
                Block[] b = new Block[] {
                    new Block(randomColor, colIndex, rowIndex),
                    new Block(randomColor, colIndex, rowIndex + 1),
                    new Block(randomColor, colIndex, rowIndex + 2),
                    new Block(randomColor, colIndex - 1, rowIndex + 2)
                };
                curShape = b;
                return b;
            case SSHAPE:
                Block[] c = new Block[] {
                    new Block(randomColor, colIndex, rowIndex),
                    new Block(randomColor, colIndex, rowIndex + 1),
                    new Block(randomColor, colIndex + 1, rowIndex + 1),
                    new Block(randomColor, colIndex + 1, rowIndex + 2)
                };
                curShape = c;
                return c;
            case SSHAPEINVERTED:
                Block[] d = new Block[] {
                    new Block(randomColor, colIndex, rowIndex),
                    new Block(randomColor, colIndex, rowIndex + 1),
                    new Block(randomColor, colIndex - 1, rowIndex + 1),
                    new Block(randomColor, colIndex - 1, rowIndex + 2)
                };
                curShape = d;
                return d;
            case TSHAPE:
                Block[] e = new Block[] {
                    new Block(randomColor, colIndex, rowIndex),
                    new Block(randomColor, colIndex - 1, rowIndex),
                    new Block(randomColor, colIndex + 1, rowIndex),
                    new Block(randomColor, colIndex, rowIndex + 1)
                };
                curShape = e;
                return e;
            case LINE:
                Block[] f = new Block[] {
                    new Block(randomColor, colIndex, rowIndex),
                    new Block(randomColor, colIndex, rowIndex + 1),
                    new Block(randomColor, colIndex, rowIndex + 2),
                    new Block(randomColor, colIndex, rowIndex + 3)
                };
                curShape = f;
                return f;
            case SQUARE:
                Block[] g = new Block[] {
                    new Block(randomColor, colIndex, rowIndex),
                    new Block(randomColor, colIndex + 1, rowIndex),
                    new Block(randomColor, colIndex, rowIndex + 1),
                    new Block(randomColor, colIndex + 1, rowIndex + 1)
                };
                curShape = g;
                return g;
            default:
                return null;
        }
    }
    public boolean moveShape(Block[] shape, ShapeType st, MoveType mt) // Returns false if shape was not moved due to a barrier block
    {
        switch(mt)
        {
            case DOWN:
                int bottom = getBottomOfShape().get(0).getRow();
                if(bottom < 23)
                {
                    for(Block b : shape)
                    {
                        if(gridArr[b.getRow() + 1][b.getCol()].getFill() != Color.WHITE && gridArr[b.getRow() + 1][b.getCol()].getFill() != b.getFill())
                        {
                            //Another shape is underneath
                            return false;
                        }
                    }
                    Grid.RemoveBlockFromGrid(gameScreen, gridArr, shape);
                    for(Block b : shape)
                    {
                        b.setRow(b.getRow() + 1);
                    }
                    Grid.AddBlockToGrid(gameScreen, gridArr, shape);
                    return true;
                }
                else
                {
                    return false;
                }
            case RIGHT:
                int right = getRightOfShape().get(0).getCol();
                if(right < 23)
                {
                    for(Block b : shape)
                    {
                        if(gridArr[b.getRow()][b.getCol() + 1].getFill() != Color.WHITE && gridArr[b.getRow()][b.getCol() + 1].getFill() != b.getFill())
                        {
                            //Another shape is to the right
                            return false;
                        }
                    }
                    Grid.RemoveBlockFromGrid(gameScreen, gridArr, shape);
                    for(Block b : shape)
                    {
                        b.setCol(b.getCol() + 1);
                    }
                    Grid.AddBlockToGrid(gameScreen, gridArr, shape);
                    return true;
                }
                else
                {
                    return false;
                }
            case LEFT:
                int left = getLeftOfShape().get(0).getCol();
                if(left > 0)
                {
                    for(Block b : shape)
                    {
                        if(gridArr[b.getRow()][b.getCol() - 1].getFill() != Color.WHITE && gridArr[b.getRow()][b.getCol() - 1].getFill() != b.getFill())
                        {
                            //Another shape is to the left
                            return false;
                        }
                    }
                    Grid.RemoveBlockFromGrid(gameScreen, gridArr, shape);
                    for(Block b : shape)
                    {
                        b.setCol(b.getCol() - 1);
                    }
                    Grid.AddBlockToGrid(gameScreen, gridArr, shape);
                    return true;
                }
                else
                {
                    return false;
                }
            default:
                return true;
        }
        
    }
    // public boolean rotateShape(Block[] shape, ShapeType st) // Rotates the shape. Returns false if shape cannot be rotated
    // {
    //     Grid.RemoveBlockFromGrid(gameScreen, gridArr, shape);
        
        
    //     Grid.AddBlockToGrid(gameScreen, gridArr, shape);
    //     return false;
    // }
    // These methods return the block that corresponds to each side of a given shape.
    public ArrayList<Block> getTopOfShape()
    {
        ArrayList<Block> keep = new ArrayList<>();
        int topY = curShape[0].getRow();

        for(Block b : curShape)
        {
            if(b.getRow() == topY)
            {
                keep.add(b);
                topY = b.getRow();
            }
            else if(b.getRow() <= topY)
            {
                keep.clear();
                topY = b.getRow();
                keep.add(b);
            }
        }
        return keep;
    }
    public ArrayList<Block> getBottomOfShape()
    {
        ArrayList<Block> keep = new ArrayList<>();
        int botY = curShape[0].getRow();

        for(Block b : curShape)
        {
            if(b.getRow() == botY)
            {
                keep.add(b);
                botY = b.getRow();
            }
            else if(b.getRow() >= botY)
            {
                keep.clear();
                botY = b.getRow();
                keep.add(b);
            }
        }
        return keep;
    }
    public ArrayList<Block> getLeftOfShape()
    {
        ArrayList<Block> keep = new ArrayList<>();
        int leftX = curShape[0].getCol();

        for(Block b : curShape)
        {
            if(b.getCol() == leftX)
            {
                keep.add(b);
                leftX = b.getCol();
            }
            else if(b.getCol() <= leftX)
            {
                keep.clear();
                leftX = b.getCol();
                keep.add(b);
            }
        }
        return keep;
    }
    public ArrayList<Block> getRightOfShape()
    {
        ArrayList<Block> keep = new ArrayList<>();
        int rightX = curShape[0].getCol();

        for(Block b : curShape)
        {
            if(b.getCol() == rightX)
            {
                keep.add(b);
                rightX = b.getCol();
            }
            else if(b.getCol() >= rightX)
            {
                keep.clear();
                rightX = b.getCol();
                keep.add(b);
            }
        }
        return keep;
    }
}