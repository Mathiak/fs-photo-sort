package fsphotosort.gui;

import java.nio.file.Path;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PhotoSorter {

	private ObjectProperty<Path> destinationPath = new SimpleObjectProperty<Path>();

	public ObjectProperty<Path> getDestinationPath() {
		return destinationPath;
	}

	public void setDestinationPath(Path newDestinationPath) {
		this.destinationPath.set(newDestinationPath);;
	}
	
}
