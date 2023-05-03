package UI;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ShowReport {
    private static volatile ShowReport instance;
    private Label lbl;
    private Label lbl2;
    private VBox root;

    private ShowReport() {
        init();
    }

    public static ShowReport getInstance() {
        if (instance == null) {
            synchronized (GrantChart.class) {
                if (instance == null) {
                    instance = new ShowReport();
                }
            }
        }
        return instance;
    }

    private void init() {
        lbl = new Label("The Average waiting time: ");
        lbl2 = new Label("The Average turn around time: ");
        root = new VBox();
        root.getChildren().addAll(lbl, lbl2);
    }

    public VBox build() {
        return root;
    }

    public void setWaitingTime(double x) {
        this.lbl.setText("The Average waiting time: " + x);
    }

    public void setTurnAround(double x) {
        this.lbl2.setText("The Average turn around time: " + x);
    }

}
