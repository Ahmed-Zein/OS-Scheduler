package UI;

import Schedulers.FirstComeFirstServed;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import process.MyProcess;
import servers.Server;

public class ProcessCreator {
    HBox root;

    private Server server;
    private boolean showPriority;
    
    ProcessCreator(Server server, boolean showPriority) {
        root = new HBox();
        this.server = server;
        this.showPriority = showPriority;
        init();
    }

    void init() {
        Button addProcessBtn = new Button("Add Process");
        HBox hbox = new HBox(addProcessBtn);

        LabeledTxtField burstTime = new LabeledTxtField("Enter Burst Time");
        LabeledTxtField priority = new LabeledTxtField("Set priority");
        
        System.out.println(this.server);

        root.getChildren().add(burstTime.build());
        
        if(this.showPriority) {
        	root.getChildren().add(priority.build());
        }
        
        root.getChildren().add(hbox);

        addProcessBtn.setOnAction(e -> {
            MyProcess p = new MyProcess();
            if (burstTime.data() > 0 && (priority.data() > 0 || !showPriority)) {
                p.setBurstTime(burstTime.data());
                if(showPriority) {
                    p.setPriority(priority.data());
                }
                else {
                	p.setPriority(1);
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
