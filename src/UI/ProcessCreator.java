package UI;

import Schedulers.PriorityScheduler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import process.MyProcess;
import servers.Server;

public class ProcessCreator {
    VBox root;

    private final Server server;

    ProcessCreator(Server server) {
        root = new VBox();
        this.server = server;
        init();
    }

    void init() {
        Button addProcessBtn = new Button("Add Process");
        HBox hContainer = new HBox();

        LabeledTxtField burstTime = new LabeledTxtField("Enter Burst Time");
        LabeledTxtField priority = new LabeledTxtField("Set priority");

        hContainer.getChildren().add(burstTime.build());

        if (server.getScheduler() instanceof PriorityScheduler) {
            hContainer.getChildren().add(priority.build());
        }
        hContainer.getChildren().add(addProcessBtn);
        root.getChildren().addAll(hContainer);

        addProcessBtn.setOnAction(e -> {
            if (burstTime.data() > 0 && (priority.data() > 0 || !(server.getScheduler() instanceof PriorityScheduler))) {
                MyProcess p = new MyProcess();
                p.setBurstTime(burstTime.data());
                p.setPriority(priority.data());

                if (!server.isRunning()) {
                    p.setArriveTime(0);
                } else {
                    p.setArriveTime((System.currentTimeMillis() - server.getServerStartTime()) / 1000);
                }
                server.push(p);
                burstTime.clear();
                priority.clear();
            }
        });
        hContainer.setStyle("-fx-background-color: #f4f4f4; -fx-spacing: 10px;");
        root.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 10px; -fx-spacing: 10px;");
        HBox.setMargin(addProcessBtn, new Insets(15, 0, 0, 0));
    }

    Pane build() {
        return root;
    }
}
