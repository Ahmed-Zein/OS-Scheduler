package UI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import process.MyProcess;
import servers.Server;

public class SayMyName {
    // Create new stage
    private Stage stage;
    private Server server;

    private SayMyName() {
    }

    public SayMyName(Server server) {
        this.server = server;
        init();
    }

    /*
     * -----------------------
     * |                 |   |
     * |   ##            |   |
     * -----------------------
     * |   ## ##             |
     * |                     |
     * |                     |
     * -----------------------
     */
    private void init() {
        stage = new Stage();
        // Create root node for new scene
        VBox root = new VBox();
        HBox upperContainer = new HBox();
        HBox grantChart = new HBox();
        HBox schedulerControllers = new HBox();
        Button startBtn = new Button("Start");
        Button pauseBtn = new Button("Pause");  // ??????????
        Button addProcessBtn = new Button("Add Process");
        HBox processCreatorPanel = new HBox();
        Label noOfProcess = new Label("current number of processes: " + server.getScheduler().size());
        LabeledTxtField burstTime = new LabeledTxtField("Enter Burst Time");
        LabeledTxtField priority = new LabeledTxtField("Set priority");
        TimeLine timeLine = new TimeLine(Color.AZURE);

        processCreatorPanel.getChildren().addAll(burstTime.build(), priority.build());

        upperContainer.getChildren().addAll(processCreatorPanel, noOfProcess, addProcessBtn);

        schedulerControllers.getChildren().addAll(startBtn, pauseBtn);
        grantChart.getChildren().addAll(timeLine.build());
        addProcessBtn.setOnAction(e -> {
            MyProcess p = new MyProcess();

            if (burstTime.data() > 0 && priority.data() > 0) {
                p.setBurstTime(burstTime.data());
                p.setPriority(priority.data());
                server.getScheduler().addProcess(p);
                // update the no of processes displayed
                noOfProcess.setText("current number of processes: " + server.getScheduler().size());
                burstTime.clear();
                priority.clear();
            }
        });
        startBtn.setOnAction(e -> {
            timeLine.startTimeLine();
        });
        pauseBtn.setOnAction(e -> {
            //todo
            timeLine.changeColor();
        });

        root.getChildren().addAll(upperContainer, schedulerControllers, grantChart);
        // Create new scene with root node
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
        // Set title for new stage
        stage.setTitle("New Stage");

        stage.show();
    }

    public Stage getStage() {
        return stage;
    }

}
