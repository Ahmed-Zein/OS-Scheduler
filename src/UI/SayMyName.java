package UI;

import Schedulers.Server;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import process.MyProcess;

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
        Button addProcessBtn = new Button("Add Process");
        HBox processCreatorPanel = new HBox();

        Label noOfProcess = new Label("current number of processes: " + server.getScheduler().size());
        LabeledTxtField burstTime = new LabeledTxtField("Enter Burst Time");
        LabeledTxtField priority = new LabeledTxtField("Set priority");

        processCreatorPanel.getChildren().addAll(burstTime.build(), priority.build());

        upperContainer.getChildren().addAll(processCreatorPanel, noOfProcess, addProcessBtn);

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

        root.getChildren().addAll(upperContainer, grantChart);
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

class LabeledTxtField {
    Label lbl;
    TextField textField;

    LabeledTxtField(String label) {
        lbl = new Label(label);
        textField = new TextField();

        // Create a TextFormatter that only allows integers
        StringConverter<Integer> converter = new IntegerStringConverter();
        TextFormatter<Integer> formatter = new TextFormatter<>(converter, 0, c ->
                c.getControlNewText().matches("\\d*") ? c : null);

        // Set the TextFormatter on the TextField
        textField.setTextFormatter(formatter);
    }

    VBox build() {
        VBox vBox = new VBox();
        vBox.getChildren().addAll(lbl, textField);
        return vBox;
    }

    int data() {
        return Integer.parseInt(textField.getText());
    }

    void clear() {
        textField.setText("0");
    }

}