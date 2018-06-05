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
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class PhotoSorter {

	private ObjectProperty<Path> destinationPath = new SimpleObjectProperty<Path>();
	private ObservableList<SourceItem> sourcePaths = FXCollections.observableArrayList();
	private ObservableList<String> outputText = FXCollections.observableArrayList();

	public ObjectProperty<Path> getDestinationPath() {
		return destinationPath;
	}

	public void setDestinationPath(Path newDestinationPath) {
		this.destinationPath.set(newDestinationPath);
	}

	public void addSourcePath(SourceItem pNewSourcePath) {
		System.out.println("add:" + pNewSourcePath);
		sourcePaths.add(pNewSourcePath);
	}

	public ObservableList<SourceItem> getSourcePaths() {
		return sourcePaths;
	}

	public ObservableList<String> getOutputText() {
		return outputText;
	}

	private void print(String s) {
		System.out.println(s);
		outputText.add(s + "\n");
	}

	public void sort() {
		final Service<Void> service = new Service<Void>() {

			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						process();
						return null;
					}
				};
			}
		};
		service.start();
	}

	private void process() {
		if (destinationPath.isNotNull().get() && !sourcePaths.isEmpty()) {
			String sources = sourcePaths.stream().map(lSourceItem -> "\"" + lSourceItem.getTextualPath().get() + "\"")
					.collect(Collectors.joining(" "));
			String cmd = "python ..\\fs_photo_sort.py " + "\"" + destinationPath.get().normalize().toString() + "\" "
					+ sources;
			print(cmd);
			System.out.println(cmd);

			try {
				Process p = Runtime.getRuntime().exec(cmd);
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

				BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

				// read the output from the command
				String s;
				while ((s = stdInput.readLine()) != null) {
					print(s);
				}
				// read any errors from the attempted command
				while ((s = stdError.readLine()) != null) {
					print(s);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			System.err.println("missing args");
		}
	}

}
