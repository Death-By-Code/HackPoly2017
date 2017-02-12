package deathbycode.hackpoly2017;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public final class Bootstrap extends Application {

    private Desktop desktop = Desktop.getDesktop();
    private ToneSynth toneSynth = new ToneSynth();

    @Override
    public void start(final Stage stage) {
    	stage.setTitle("Harmonibites");
        final FileChooser fileChooser = new FileChooser();
        final ComboBox<Integer> beatSpeed = new ComboBox<>();
        final Button openButton = new Button("Browse");

        Label hashText = new Label("waiting...");
        String str = new String();
        StringProperty fileHex = new SimpleStringProperty();
        fileHex.setValue(str);

        openButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null) {
                       SHA256Hash hash = new SHA256Hash(file.getPath());
                    	try {
							fileHex.setValue(hash.hashFileHex());
							toneSynth.playNotes(300 - (int)beatSpeed.getValue(), hash.hashFile());
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                    	hashText.textProperty().bind(fileHex);
                    }
                }
            });


        final GridPane inputGridPane = new GridPane();

        Text scenetitle = new Text("Choose a file to play sound for");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        inputGridPane.add(scenetitle, 0, 0, 2, 1);

        Label fileButton = new Label("Select file: ");
        inputGridPane.add(fileButton, 0, 1);

        Label hashout = new Label("Hash: ");
        inputGridPane.add(hashout, 0, 2);
        inputGridPane.add(hashText, 1, 2);

        // select BPM
        Label bpm = new Label("BPM: ");
        inputGridPane.add(bpm, 0, 2);
        beatSpeed.getItems().addAll(
        		50,
        		100,
        		150,
        		200,
        		250
        );
        inputGridPane.add(beatSpeed, 1, 2);

        GridPane.setConstraints(openButton, 1, 1);
        inputGridPane.setAlignment(Pos.CENTER);
        inputGridPane.setHgap(100);
        inputGridPane.setVgap(20);
        inputGridPane.getChildren().addAll(openButton);

        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(25, 25, 25, 25));

        stage.setScene(new Scene(rootGroup));
        stage.show();
    }

    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                Bootstrap.class.getName()).log(
                    Level.SEVERE, null, ex
                );
        }
    }
}