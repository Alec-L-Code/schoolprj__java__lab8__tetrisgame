package app;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * Alec Lund
 * 
 * Description:
 * This game makes use of many javafx features to replicate the iconic game Tetris.
 */
public class App extends Application 
{
  private HBox mainScreen = new HBox(); // The Main screen layout. It will hold the score board info and the game itself.
  private BorderPane scoreScreen = new BorderPane(); // The Score Box that will hold the score along with other information.
  private GridPane gameScreen = new GridPane(); // Will hold the game screen
  private Block[][] gridArr = new Block[24][24]; // The block array for the grid // (row, col)
  private Stopwatch stopwatch = new Stopwatch(); // Timer class for time elapsed and calculating time.
  private Text scoreText = new Text();
  private Game game;
  private Scene mainScene;
  private GameOver gameOver = new GameOver();

  @Override 
  public void start(Stage primaryStage) 
  {
    //Setting up the grid for the blocks // Grid Initialization
    gameScreen.setPrefSize(600, 600);
    Grid.InitGameGrid(gameScreen, gridArr);
    gameScreen.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(3)))); // Border around the game
    gameScreen.setGridLinesVisible(true);
    ///////// Set up the time elapsed



    ///////
    VBox infoHolder = new VBox(); // Holds all the information/nodes on the scoreScreen
    scoreScreen.getChildren().add(infoHolder);
    ///////
    infoHolder.setSpacing(30);
    // Setup the time elapsed nodes
    HBox timeHolder = new HBox();
    Text timeElapsedStr = new Text("Time Elapsed: ");
    Text time = stopwatch.getText();
    timeElapsedStr.setFont(time.getFont());
    timeHolder.getChildren().addAll(timeElapsedStr, time);
    // Setup the score text
    HBox scoreHolder = new HBox();
    Text scoreStr = new Text("Score: ");
    scoreText.setText("0");
    scoreStr.setFont(timeElapsedStr.getFont());
    scoreText.setFont(timeElapsedStr.getFont());
    scoreHolder.getChildren().addAll(scoreStr, scoreText);
    // Set up Game Hints
    VBox hintsHolder = new VBox();
    Text pauseHint = new Text("Press ESC to Pause\nand Unpause");
    pauseHint.setFont(timeElapsedStr.getFont());
    Text keysHint = new Text("Use the arrows keys to move");
    keysHint.setFont(timeElapsedStr.getFont());
    hintsHolder.getChildren().addAll(pauseHint, keysHint);
    // Game Over Text
    HBox gameOverHolder = new HBox();
    Text gameOverText = new Text("Game Over");
    gameOverText.setFont(timeElapsedStr.getFont());
    gameOverText.setFill(Color.RED);
    gameOverText.setVisible(false);
    gameOverHolder.getChildren().add(gameOverText);
    ////////////////////////////
    // MAIN GAME FUNCTIONS
    ////////////////////////////
    game = new Game();
    game.Init(stopwatch, gameScreen, gridArr, scoreText, mainScene, gameOver, gameOverText);
    mainScene = new Scene(mainScreen);
    mainScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
      public void handle(KeyEvent e)
      {
        if(e.getCode() == KeyCode.ESCAPE) // Start the game
        {
          if(gameOver.gameIsOver)
          {
            stopwatch.UnPause();
            game.Start();
            gameOver.gameIsOver = false;
            gameOverText.setVisible(false);
          }
        }
        if(e.getCode() == KeyCode.SPACE && gameOver.gameIsOver == false) // Rotates a shape (couldnt get it to work)
        {
          //game.rotateShape(game.curShape, game.curShapeType);
        }
        if(e.getCode() == KeyCode.LEFT && gameOver.gameIsOver == false)
        {
          game.moveShape(game.curShape, game.curShapeType, MoveType.LEFT);
        }
        if(e.getCode() == KeyCode.RIGHT && gameOver.gameIsOver == false)
        {
          game.moveShape(game.curShape, game.curShapeType, MoveType.RIGHT);
        }
        if(e.getCode() == KeyCode.DOWN && gameOver.gameIsOver == false)
        {
          game.moveShape(game.curShape, game.curShapeType, MoveType.DOWN);
        }
      }
    });
    
    ////////////////////////////
    //
    ////////////
    infoHolder.getChildren().addAll(timeHolder, scoreHolder, hintsHolder, gameOverHolder); // Add all the nodes to the information section
    gameScreen.setPrefSize(600, 600); // Sizing of the game screen
    scoreScreen.setPrefSize(300, 600); ////
    mainScreen.setPrefSize(900, 600); //////
    mainScreen.getChildren().addAll(scoreScreen, gameScreen);
    primaryStage.setScene(mainScene); //////
    primaryStage.setTitle("Tetris Remake");///////
    primaryStage.show();///////
    ////////////
  }
  public static void main(String[] args) 
  {
    launch(args);
  }
}
