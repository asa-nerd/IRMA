package irmaANALYSIS;

import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class VisualizerList {
	Group g;
	TableView table;
	TableColumn <Long, Subject> tableColumnId;
	TableColumn <String, Subject> tableColumnRecordName;
	TableColumn <Double, Subject> tableColumnAge;
	TableColumn <String, Subject> tableColumnEducation;
	
	VisualizerList(){
		g = new Group();
		table = new TableView();
		table.setPlaceholder(new Label("Please load sample data."));
		table.setEditable(true);
		tableColumnId = new TableColumn<>("ID");
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnRecordName = new TableColumn<>("Record Name");
		tableColumnRecordName.setCellValueFactory(new PropertyValueFactory<>("recordName"));
		tableColumnAge = new TableColumn<>("Age");
		tableColumnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
		tableColumnEducation =  new TableColumn<>("Education");
		tableColumnEducation.setCellValueFactory(new PropertyValueFactory<>("education"));
		// tableColumnLength = new TableColumn<>("Length");
		tableColumnId.setSortable(false);
		tableColumnRecordName.setSortable(false);
		tableColumnId.setPrefWidth(40);
		tableColumnRecordName.setPrefWidth(140);
		tableColumnAge.setPrefWidth(40);
		tableColumnEducation.setPrefWidth(180);
		table.getColumns().addAll(tableColumnId, tableColumnRecordName, tableColumnAge, tableColumnEducation);
		
		g.getChildren().add(table);
	}
	
	public Group getVisualizerListContainer() {
		return g;
	}
	
	public void clearTable() {
		table.getItems().clear();
	}
	
	public void addSubject(Subject _s) {
		table.getItems().add(_s);
	}	
}
