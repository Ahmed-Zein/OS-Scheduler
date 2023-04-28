package UI;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import oserver.Observer;
import process.MyProcess;
import process.ProcessState;

import java.util.Queue;

public class ProcessTableView implements Observer {
    private TableView<MyProcess> table;
    private Queue<MyProcess> queue;

    ProcessTableView(Queue<MyProcess> queue) {
        this.queue = queue;
        init();
    }

    void init() {
        table = new TableView<>();
        TableColumn<MyProcess, String> pidColumn = new TableColumn<>("Process ID");
        TableColumn<MyProcess, Long> arrivalColumn = new TableColumn<>("Arrival Time");
        TableColumn<MyProcess, Integer> burstColumn = new TableColumn<>("Burst Time");
        TableColumn<MyProcess, Integer> priorityColumn = new TableColumn<>("Priority");
        TableColumn<MyProcess, ProcessState> stateColumn = new TableColumn<>("State");

        table.getColumns().addAll(pidColumn, arrivalColumn, burstColumn, priorityColumn, stateColumn);

        pidColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arriveTime"));
        burstColumn.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        updateData();
    }

    TableView build() {
        return table;
    }

    public void updateData() {
        table.getItems().clear();
        table.getItems().addAll(queue);
    }

    @Override
    public void update() {
        updateData();
    }
}
