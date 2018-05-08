package fsphotosort.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.stream.Collectors;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PhotoSorter {

	private ObjectProperty<Path> destinationPath = new SimpleObjectProperty<Path>();
	private ObservableList<SourceItem> sourcePaths = FXCollections.observableArrayList();
	private ObservableList<String> outputText = FXCollections.observableArrayList();

	public ObjectProperty<Path> getDestinationPath() {
		return destinationPath;
	}

	public void setDestinationPath(Path newDestinationPath) {
		this.destinationPath.set(newDestinationPath);
		;
	}

	public void addSourcePath(SourceItem pNewSourcePath) {
		sourcePaths.add(pNewSourcePath);
	}

	public ObservableList<SourceItem> getSourcePaths() {
		return sourcePaths;
	}

	public ObservableList<String> getOutputText() {
		return outputText;
	}

	private void print(String s) {
		outputText.add(s + "\n");
	}

	public void sort() {
		String sources = sourcePaths.stream().map(lSourceItem -> lSourceItem.getTextualPath().get()).collect(Collectors.joining(" "));
		String cmd = "python ..\\fs_photo_sort.py " + sources;
		Process p;
		String s;
		try {

			p = Runtime.getRuntime().exec(cmd);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			// read the output from the command
			while ((s = stdInput.readLine()) != null) {
				print(s);
			}

			// read any errors from the attempted command
			while ((s = stdError.readLine()) != null) {
				print(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
