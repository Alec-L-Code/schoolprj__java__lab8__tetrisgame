package app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;


/**
 * A class for a javafx stopwatch for keeping track of points and time elapsed.
 * Special Thanks to SatyaSnehith on GitHub. All Credit Goes to him for this class.
 * SatayaSnehith / Stopwatch.java
 * https://gist.github.com/SatyaSnehith/167779aac353b4e79f8dfae4ed23cb85
 */
public class Stopwatch 
{
    private Text time = new Text("00:00:000");
    private Timeline timeline;
    public Stopwatch()
    { 
        Start();
        time.setFont(Font.font("Sans-serif", 20));
        time.setFill(Color.RED);
    }
    private int mins = 0, secs = 0, millis = 0;

    public void Start() // Initiates and starts the timer. // Do not call twice use Reset/Unpause Instead
    {
        time = new Text("00:00:000");
        timeline = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event)
            {
                SetText(time);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
    }
    public void Pause() // Pauses the timer
    {
        timeline.pause();
    }
    public void UnPause() // Unpauses the timer
    {
        timeline.play();
    }
    public void Reset() // Resets the timer back to zero.
    {
        mins = 0;
        secs = 0;
        millis = 0;
        timeline.pause();
        time.setText("00:00:000");
    }
    private void SetText(Text text)
    {
        if(millis == 1000)
        {
            secs++;
            millis = 0;
        }
        if(secs == 60)
        {
            mins++;
            secs = 0;
        }
        text.setText((((mins/10) == 0) ? "0" : "") + mins + ":"
		 + (((secs/10) == 0) ? "0" : "") + secs + ":" 
			+ (((millis/10) == 0) ? "00" : (((millis/100) == 0) ? "0" : "")) + millis++);
    }
    public Text getText() { return time; }
    // Parsing methods for millis, seconds, and minutes elsapsed. All methods will parse as integers (I created these)
    public int parseMillis()
    {
        String[] parsedStrArr = time.textProperty().get().split(":");   
        return Integer.parseInt(parsedStrArr[0]);
    }
    public int parseSecs()
    {
        String[] parsedStrArr = time.textProperty().get().split(":");   
        return Integer.parseInt(parsedStrArr[1]);
    }
    public int parseMins()
    {
        String[] parsedStrArr = time.textProperty().get().split(":");   
        return Integer.parseInt(parsedStrArr[2]);
    }
}