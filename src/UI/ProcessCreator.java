package UI;

import Schedulers.PriorityScheduler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import process.MyProcess;
import servers.Server;

public class ProcessCreator {
    HBox root;

    private Server server;

    ProcessCreator(Server server) {
        root = new HBox();
        this.server = server;
        init();
    }

    void init() {
        Button addProcessBtn = new Button("Add Process");
        HBox hbox = new HBox(addProcessBtn);

        LabeledTxtField burstTime = new LabeledTxtField("Enter Burst Time");
        LabeledTxtField priority = new LabeledTxtField("Set priority");

        root.getChildren().add(burstTime.build());
        
        if(server.getScheduler() instanceof PriorityScheduler) {
        	root.getChildren().add(priority.build());
        }
        
        root.getChildren().add(hbox);

        addProcessBtn.setOnAction(e -> {
            MyProcess p = new MyProcess();
            if (burstTime.data() > 0 && (priority.data() > 0 || !(server.getScheduler() instanceof PriorityScheduler))) {
                p.setBurstTime(burstTime.data());
                p.setPriority(priority.data());
                if(server.getServerStartTime() == -1) {
                	p.setArriveTime(0);
                }else {
                	p.setArriveTime((p.getCreationTime()- server.getServerStartTime())/1000);
                }
                server.push(p);
                burstTime.clear();
                priority.clear();

            }
        });
        root.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 10px; -fx-spacing: 10px;");
        HBox.setMargin(addProcessBtn, new Insets(15, 0, 0, 0));
    }

    Pane build() {
        return root;
    }
}
