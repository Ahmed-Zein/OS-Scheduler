package UI;

import Schedulers.FirstComeFirstServed;
import Schedulers.PriorityScheduler;
import Schedulers.RoundRobin;
import Schedulers.ShortestJobFirstScheduler;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import servers.NonPreemptiveServer;
import servers.PreemptiveServer;
import servers.RoundRobinServer;
import servers.Server;

public class Main extends Application {

    private Server server;
    private boolean showPriority;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Create a VBox layout to hold the radio buttons
        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        // Create a toggle group for the radio buttons
        ToggleGroup toggleGroup = new ToggleGroup();

        Label selectionLabel = new Label("chose your agent");
        Label warningLabel = new Label(null);


        RadioButton option1 = new RadioButton("First Come First Served");
        RadioButton option2 = new RadioButton("Priority Preemptive");
        RadioButton option3 = new RadioButton("Priority Nob-Preemptive");
        RadioButton option4 = new RadioButton("Shortest Job First Nob-Preemptive");
        RadioButton option5 = new RadioButton("Shortest Job First Preemptive");
        RadioButton option6 = new RadioButton("Round robin");

        Button createBtn = new Button("Create");

        option1.setToggleGroup(toggleGroup);
        option2.setToggleGroup(toggleGroup);
        option3.setToggleGroup(toggleGroup);
        option4.setToggleGroup(toggleGroup);
        option5.setToggleGroup(toggleGroup);
        option6.setToggleGroup(toggleGroup);

        root.getChildren().addAll(selectionLabel, option1, option2, option3, option4, option5, option6, warningLabel, createBtn);

        // Create button to display selected option
        createBtn.setOnAction(event -> {
            if (setFactory(toggleGroup)) {
                new SayMyName(server, showPriority);
                stage.hide();
            } else {
                warningLabel.setText("choose ur agent (* ￣︿￣)");
            }

        });
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Radio Button Example");
        stage.show();
    }

    boolean setFactory(ToggleGroup toggleGroup) {
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        if (selectedRadioButton != null) {
            switch (selectedRadioButton.getText()) {

                case "First Come First Served":
                    server = new NonPreemptiveServer(new FirstComeFirstServed());
                    showPriority = false;
                    break;
                case "Priority Preemptive":
                    server = new PreemptiveServer(new PriorityScheduler());
                    showPriority = true;
                    break;
                case "Priority Nob-Preemptive":
                    server = new NonPreemptiveServer(new PriorityScheduler());
                    showPriority = true;
                    break;
                case "Shortest Job First Nob-Preemptive":
                    server = new NonPreemptiveServer(new ShortestJobFirstScheduler());
                    showPriority = false;
                    break;
                case "Shortest Job First Preemptive":
                    server = new PreemptiveServer(new ShortestJobFirstScheduler());
                    showPriority = false;
                    break;
                case "Round robin":
                    server = new NonPreemptiveServer(new RoundRobin());
                    showPriority = false;
                    break;
            }
            return true;
        }
        return false;
    }

}
