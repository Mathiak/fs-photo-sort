package fsphotosort.gui;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;

public class MainFrameController {

	private static final ResourceBundle GUI_BUNDLE = ResourceBundle.getBundle("GuiBundle");

	@FXML
	private TableView<SourceItem> sourceTable;

	@FXML
	private TableColumn<SourceItem, String> sourcePathColumn;

	@FXML
	private TextArea destinationPathProperty;

	@FXML
    private TitledPane titledTerminal;

	private Console terminal;

	private final PhotoSorter photoSorter;

	public MainFrameController() {
		photoSorter = new PhotoSorter();
	}

	@FXML
	public void initialize() {
		terminal = new Console();
		titledTerminal.setContent(terminal);
		sourcePathColumn.setCellValueFactory(sourceItem -> sourceItem.getValue().getTextualPath());
		photoSorter.getDestinationPath()
				.addListener((observable, oldValue, newValue) -> destinationPathProperty.setText(newValue.toString()));
		sourceTable.setItems(photoSorter.getSourcePaths());
		photoSorter.getOutputText().addListener((ListChangeListener<? super String>) (change) -> {
			while (change.next()) {
				if (change.wasAdded()) {
					change.getAddedSubList().forEach(lAddedText -> terminal.print(lAddedText));
				}
			}
		});
	}

	@FXML
	void addSource(ActionEvent event) {
		addSource();
	}

	@FXML
	void addSourceFromView(MouseEvent event) {
		addSource();
	}

	private void addSource() {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle(GUI_BUNDLE.getString("source.chooser.title"));
		File selectedDirectory = chooser.showDialog(sourceTable.getScene().getWindow());
		File[] lSubdirectories = selectedDirectory.listFiles(new FilenameFilter() {
			  @Override
			  public boolean accept(File current, String name) {
			    return new File(current, name).isDirectory();
			  }
			});
		Arrays.stream(lSubdirectories).map(File::toPath).forEach(path -> photoSorter.addSourcePath(new SourceItem(path)));
		if (selectedDirectory != null) {
			photoSorter.addSourcePath(new SourceItem(selectedDirectory.toPath()));
		}
	}

	@FXML
	void proceed(ActionEvent event) {
		photoSorter.sort();
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
