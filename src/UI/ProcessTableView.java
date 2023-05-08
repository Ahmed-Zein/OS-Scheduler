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
    private final Queue<MyProcess> queue;
    private final boolean showPriority;

    ProcessTableView(Queue<MyProcess> queue, boolean showPriority) {
        this.queue = queue;
        this.showPriority = showPriority;
        init();
    }

    void init() {
        table = new TableView<>();
        TableColumn<MyProcess, String> pidColumn = new TableColumn<>("Process Name");
        TableColumn<MyProcess, Long> arrivalColumn = new TableColumn<>("Arrival Time");
        TableColumn<MyProcess, Long> remainingColumn = new TableColumn<>("Remaining Time");
        TableColumn<MyProcess, Integer> burstColumn = new TableColumn<>("Burst Time");

        table.getColumns().addAll(pidColumn, arrivalColumn, burstColumn, remainingColumn);

        if (showPriority) {
            TableColumn<MyProcess, Integer> priorityColumn = new TableColumn<>("Priority");
            priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
            table.getColumns().addAll(priorityColumn);
        }

        TableColumn<MyProcess, ProcessState> stateColumn = new TableColumn<>("State");

        table.getColumns().addAll(stateColumn);

        table.setMaxHeight(200);
        pidColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arriveTime"));
        remainingColumn.setCellValueFactory(new PropertyValueFactory<>("remainingTime"));
        burstColumn.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        updateData();
    }

    public TableView<MyProcess> build() {
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
