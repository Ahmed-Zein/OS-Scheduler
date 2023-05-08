package UI;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class GrantChart {

    private Pane root;
    private Timeline timeline;
    private int count = 0;
    private ScrollPane scrollPane;
    private Color color;
    private int speed;
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
        speed = 1000;
        this.color = Color.ROSYBROWN;
        init();
    }

    public void init() {
        root = new Pane();

        // Create a timeline with a keyframe every second
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> addRectangle("")));
        timeline.setCycleCount(Timeline.INDEFINITE);

        scrollPane = new ScrollPane(root);
        scrollPane.setPrefSize(400, 100);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    }

    private void addRectangle2() {
        // Create a new rectangle with a random color
        Rectangle rectangle = new Rectangle(20, 80, color);
        // Add the rectangle to the pane
        root.getChildren().add(rectangle);

        // Animate the rectangle's position with a keyframe
        KeyValue keyValue = new KeyValue(rectangle.xProperty(), count * 20);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(this.speed), keyValue);
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();

        // Increment the count variable
        count++;
    }
    private void addRectangle(String p_name) {
        // Create a new rectangle with a random color and text
        Rectangle rectangle = new Rectangle(20, 80, color);
        Text text = new Text(p_name);
        text.setFill(Color.WHITE);
        text.setFont(Font.font(10));
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rectangle, text);

        // Add the stack pane to the pane
        root.getChildren().add(stackPane);

        // Animate the rectangle's position with a keyframe
        KeyValue keyValue = new KeyValue(stackPane.translateXProperty(), count * 20);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(this.speed), keyValue);
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();

        // Increment the count variable
        count++;
    }


    public void clearTimeline() {
        timeline.stop();
        count = 0;
        root.getChildren().clear();
    }

    public Button clearBtn() {
        Button b = new Button("clear");
        b.setOnAction((event) -> {
            clearTimeline();
        });
        return b;
    }

    public synchronized void addRectangleManually(String name) {
        addRectangle(name);
    }

    public void changeColor(Color color) {
        this.color = color;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    ScrollPane build() {
        return scrollPane;
    }

}
