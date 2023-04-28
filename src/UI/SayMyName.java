package UI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
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
        ProcessTableView processTable = new ProcessTableView(server.getScheduler().getProcesses());
        Button startBtn = new Button("Start");
        HBox startBox = new HBox(startBtn);
        GrantChart chart = GrantChart.instance();

        server.getObservers().addObserver(processTable);

        startBtn.setOnAction(e -> {
            processTable.updateData();
            //todo start the server
            if (!thread.isAlive())
                thread.start();
            thread = new Thread(server);
        });

        root.getChildren().addAll(processCreator.build(), processTable.build(), startBox, chart.build());

        root.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 10px; -fx-spacing: 10px;");
        HBox.setMargin(startBox, new Insets(15, 0, 0, 0));

        buildScene();
    }

    void buildScene() {
        Scene newScene = new Scene(this.root);
        stage.setScene(newScene);
        stage.setTitle("New Stage");
        stage.show();
    }


}
