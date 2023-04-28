package UI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import process.MyProcess;
import servers.Server;

public class SayMyName {
    // Create new stage
    private Stage stage;
    private Server server;
    private Thread thread;

    private SayMyName() {
    }

    public SayMyName(Server server) {
        this.server = server;
        thread = new Thread(server);
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
        stage = new Stage();
        // Create root node for the scene
        VBox root = new VBox();

        // upper half of the scene
        HBox upperContainer = new HBox();
        Label noOfProcess = new Label("current number of processes: " + server.getScheduler().size());
        Button addProcessBtn = new Button("Add Process");
        LabeledTxtField burstTime = new LabeledTxtField("Enter Burst Time");
        LabeledTxtField priority = new LabeledTxtField("Set priority");

        // second half
        VBox lowerContainer = new VBox();
        HBox schedulerControllers = new HBox();
        Button startBtn = new Button("Start");
        Button pauseBtn = new Button("Pause");  // ??????????
        HBox processCreatorPanel = new HBox();
        GrantChart chart = GrantChart.instance();

        processCreatorPanel.getChildren().addAll(burstTime.build(), priority.build());

        upperContainer.getChildren().addAll(processCreatorPanel, noOfProcess, addProcessBtn);

        schedulerControllers.getChildren().addAll(startBtn, pauseBtn);
        lowerContainer.getChildren().addAll(schedulerControllers, chart.build());
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
            //todo start the server
            if (!thread.isAlive())
                thread.start();
            thread = new Thread(server);
        });

        pauseBtn.setOnAction(e -> {
            //todo may be un necessary
//            chart.changeColor(p.);
        });

        root.getChildren().addAll(upperContainer, schedulerControllers, lowerContainer);
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
