package fsphotosort.gui;

import java.nio.file.Path;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class SourceItem {

	private final SimpleObjectProperty<Path> path;
	private final SimpleStringProperty textualPath;

	public SourceItem(Path directoryPath) {
		super();
		path = new SimpleObjectProperty<Path>(directoryPath);
		textualPath = new SimpleStringProperty(directoryPath.toString());
		path.addListener((obs, newPath, oldPath) -> textualPath.set(newPath.toString()));
	}

	public SimpleObjectProperty<Path> getPath() {
		return path;
	}

	public SimpleStringProperty getTextualPath() {
		return textualPath;
	}
}
