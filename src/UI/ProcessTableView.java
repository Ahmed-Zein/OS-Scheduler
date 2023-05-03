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
    private boolean showPriority;
    
    ProcessTableView(Queue<MyProcess> queue, boolean showPriority) {
        this.queue = queue;
        this.showPriority = showPriority;
        init();
    }

    public boolean isShowPriority() {
		return showPriority;
	}

	public void setShowPriority(boolean showPriority) {
		this.showPriority = showPriority;
	}

	void init() {
        table = new TableView<>();
        TableColumn<MyProcess, String> pidColumn = new TableColumn<>("Process ID");
        TableColumn<MyProcess, Long> arrivalColumn = new TableColumn<>("Arrival Time");
        TableColumn<MyProcess, Long> remainingColumn = new TableColumn<>("Remaining Time");
        TableColumn<MyProcess, Integer> burstColumn = new TableColumn<>("Burst Time");
        
        table.getColumns().addAll(pidColumn, arrivalColumn, burstColumn, remainingColumn);
        
        if(showPriority) {
            TableColumn<MyProcess, Integer> priorityColumn = new TableColumn<>("Priority");
            priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
            table.getColumns().addAll(priorityColumn);
        }
        
        TableColumn<MyProcess, ProcessState> stateColumn = new TableColumn<>("State");

        table.getColumns().addAll(stateColumn);

        table.setMaxHeight(200);
        pidColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arriveTime"));
        remainingColumn.setCellValueFactory(new PropertyValueFactory<>("remainingTime"));
        burstColumn.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
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
