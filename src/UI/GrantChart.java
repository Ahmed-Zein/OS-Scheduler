package UI;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class GrantChart {

    private Pane root;
    private Timeline timeline;
    private int count = 0;
    private ScrollPane scrollPane;
    private Color color;
    private static GrantChart chart;

    private GrantChart(Color color) {
        this.color = color;
        init();
    }

    public static GrantChart instance() {
        if (chart == null) {
            synchronized (GrantChart.class) {
                if (chart == null) {
                    chart = new GrantChart();
                }
            }
        }
        return chart;
    }

    private GrantChart() {
        this.color = Color.ROSYBROWN;
        init();
    }

    public void init() {
        root = new Pane();

        // Create a timeline with a keyframe every second
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> addRectangle()));
        timeline.setCycleCount(Timeline.INDEFINITE);

        scrollPane = new ScrollPane(root);
        scrollPane.setPrefSize(400, 100);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    }

    private void addRectangle() {
        // Create a new rectangle with a random color
        Rectangle rectangle = new Rectangle(20, 80, color);
        // Add the rectangle to the pane
        root.getChildren().add(rectangle);

        // Animate the rectangle's position with a keyframe
        KeyValue keyValue = new KeyValue(rectangle.xProperty(), count * 20);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), keyValue);
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();

        // Increment the count variable
        count++;
    }

    public void addRectangleManually() {
        addRectangle();
    }

    public void startTimeLine() {
        timeline.play();
    }

    public void pauseTimeLine() {
        timeline.pause();
    }

    public void changeColor(Color color) {
        this.color = color;
    }

    ScrollPane build() {
        return scrollPane;
    }

}
