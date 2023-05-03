package UI;

import Schedulers.PriorityScheduler;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import servers.Server;

public class SayMyName {
    private Stage stage;
    private Server server;
    private Thread thread;
    private VBox root;

    private SayMyName() {
    }

    public SayMyName(Server server) {
        this.server = server;
        thread = new Thread(server);
        stage = new Stage();
        root = new VBox();
        init();
    }

    /*
     * -----------------------
     * |                 |   |
     * |   ##            |   |
     * -----------------------
     * |   ## ##             |
     * -----------------------
     * |                     |
     * |       chart         |
     * |                     |
     * -----------------------
     */
    private void init() {

        ProcessCreator processCreator = new ProcessCreator(server);
        boolean showPriority = this.server.getScheduler() instanceof PriorityScheduler;
        ProcessTableView processTable = new ProcessTableView(server.getScheduler().getProcesses(), showPriority);

        Button startBtn = new Button("Start");
        HBox btnBox = new HBox(startBtn);
        VBox container = new VBox();
        HBox comboBox =   this.buildComboBox();
        GrantChart chart = GrantChart.instance();

        btnBox.getChildren().addAll(ShowReport.getInstance().build());

        container.getChildren().addAll(btnBox, comboBox);
        server.getObservers().addObserver(processTable);

        startBtn.setOnAction(e -> {
            if (!server.isRunning())
                thread.start();
            thread = new Thread(server);
        });

        root.getChildren().addAll(processCreator.build(), processTable.build(), container, chart.build());

        root.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 10px; -fx-spacing: 10px;");
        container.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 10px; -fx-spacing: 5px;");
        btnBox.setStyle("-fx-background-color: #f4f4f4; -fx-spacing: 30px;");

        buildScene();
    }

    void buildScene() {
        Scene newScene = new Scene(this.root);
        stage.setScene(newScene);
        stage.setTitle("New Stage");
        stage.show();
    }

    HBox buildComboBox() {
        HBox pane = new HBox();
        Label lbl = new Label("select speed");
        String[] options = {"Ultra Fast", "Normal", "Slow Motion"};

        // Create a ComboBox with the list of options
        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(options));

        // Set the default value for the ComboBox
        comboBox.setValue("Normal");

        // Add an event handler to the ComboBox
        comboBox.setOnAction(event -> {
            // Get the selected item from the ComboBox
            String selectedOption = comboBox.getValue();

            // Perform an action based on the selected item
            if (selectedOption.equals("Ultra Fast")) {
                GrantChart.instance().setSpeed(10);
            } else if (selectedOption.equals("Normal")) {
                GrantChart.instance().setSpeed(1000);
            } else if (selectedOption.equals("Slow Motion")) {
                GrantChart.instance().setSpeed(2000);
            }
        });
        pane.getChildren().addAll(lbl, comboBox);
        pane.setStyle("-fx-background-color: #f4f4f4; -fx-spacing: 10px;");

        return pane;
    }

}
