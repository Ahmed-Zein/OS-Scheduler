package UI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import servers.Server;
import Schedulers.PriorityScheduler;

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
        GrantChart chart = GrantChart.instance();

        server.getObservers().addObserver(processTable);

        startBtn.setOnAction(e -> {
            if (!server.isRunning())
                thread.start();
            thread = new Thread(server);
        });

        root.getChildren().addAll(processCreator.build(), processTable.build(), btnBox, chart.build());

        root.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 10px; -fx-spacing: 10px;");

        buildScene();
    }

    void buildScene() {
        Scene newScene = new Scene(this.root);
        stage.setScene(newScene);
        stage.setTitle("New Stage");
        stage.show();
    }


}
