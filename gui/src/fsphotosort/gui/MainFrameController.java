package fsphotosort.gui;

import java.io.File;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.text.TextFlow;
import javafx.stage.DirectoryChooser;

public class MainFrameController {

	private static final ResourceBundle GUI_BUNDLE =ResourceBundle.getBundle("GuiBundle");
	
    @FXML
    private TableView<SourceItem> sourceTable;
	
    @FXML
    private TableColumn<SourceItem, String> sourcePathColumn;
    
    @FXML
    private TextArea destinationPathProperty;
    
    @FXML
    private TextFlow terminal;
    
    private final PhotoSorter photoSorter;
    
    public MainFrameController() {
    	photoSorter = new PhotoSorter();
    }
    
    @FXML
    public void initialize() {
    	sourcePathColumn.setCellValueFactory(sourceItem -> sourceItem.getValue().getTextualPath());
    	photoSorter.getDestinationPath().addListener((observable, oldValue, newValue) -> destinationPathProperty.setText(newValue.toString()));
    }

    @FXML
    void addSource(ActionEvent event) {
    	DirectoryChooser chooser = new DirectoryChooser();
    	chooser.setTitle(GUI_BUNDLE.getString("source.chooser.title"));
    	File selectedDirectory = chooser.showDialog(sourceTable.getScene().getWindow());
    	if (selectedDirectory != null) {
        	sourceTable.getItems().add(new SourceItem(selectedDirectory.toPath()));
    	}
    }

    @FXML
    void proceed(ActionEvent event) {

    }

    @FXML
    void selectDestination(ActionEvent event) {
    	DirectoryChooser chooser = new DirectoryChooser();
    	chooser.setTitle(GUI_BUNDLE.getString("source.chooser.title"));
    	File selectedDirectory = chooser.showDialog(sourceTable.getScene().getWindow());
    	if (selectedDirectory != null) {
    	photoSorter.setDestinationPath(selectedDirectory.toPath());
    	}
    }
}
