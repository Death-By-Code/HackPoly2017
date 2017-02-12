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
import javafx.scene.control.Slider;
import javafx.scene.layout.ColumnConstraints;
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
    private File file;
    private PlayControl playControl;

    @Override
    public void start(final Stage stage) {
    	stage.setTitle("Harmonibites");
        final FileChooser fileChooser = new FileChooser();
        final Slider beatSpeed = new Slider();
        final Button openButton = new Button("Browse");
        final Button playButton = new Button("Play");
        final Button stopButton = new Button("Stop");


        Label hashText = new Label("...");
        Label fileName = new Label("...");
        StringProperty fileHex = new SimpleStringProperty();
        StringProperty filestr = new SimpleStringProperty();

        openButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    file = fileChooser.showOpenDialog(stage);
                    if (file != null) {
                       SHA256Hash hash = new SHA256Hash(file.getPath());
                    	try {
							fileHex.setValue(hash.hashFileHex());
							filestr.setValue(file.getName());
							//toneSynth.playNotes(300 - (int)beatSpeed.getValue(), hash.hashFile());
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                    	hashText.textProperty().bind(fileHex);
                    	fileName.textProperty().bind(filestr);
                    }
                }
            });

        playButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle( final ActionEvent e ) {
                    SHA256Hash hash = new SHA256Hash( file.getPath() );
                   if (file != null) {
                       SHA256Hash mash = new SHA256Hash(file.getPath());
                    	try {
							fileHex.setValue(mash.hashFileHex());
                            char[] hexString = hash.hashFileHex().toCharArray();
                            int[] hashAsciiValues = new int[hexString.length];
                            for(int i = 0; i < hexString.length; i++) {
                                hashAsciiValues[i] = (int)hexString[i];
                            }
                            for(int i = 0; i < hashAsciiValues.length; i++){
                                System.out.println(hashAsciiValues[i]);
                            }
                            playControl = new PlayControl((int)beatSpeed.getValue(), toneSynth);
                            playControl.playQuarterNote(hashAsciiValues);

//							toneSynth.playNotes(300 - (int)beatSpeed.getValue(), String.getBytes(hash.hashFileHex()));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                    	//hashText.textProperty().bind(fileHex);
                    }
                }
            });

            stopButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle( final ActionEvent e ) {
                        playControl.stopMusic();
                    }
                });

        final GridPane inputGridPane = new GridPane();
        inputGridPane.setPrefSize(500, 300);

        Text scenetitle = new Text("Choose a file to play sound for");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        inputGridPane.add(scenetitle, 0, 0, 2, 1);

        // file selection
        Label fileButton = new Label("Select file: ");
        inputGridPane.add(fileButton, 0, 1);

        // file name
        Label fileTitle = new Label("File name: ");
        inputGridPane.add(fileTitle, 0, 2);
        inputGridPane.add(fileName, 1, 2);

        // BPM slider
        Label bpm = new Label("BPM: ");
        inputGridPane.add(bpm, 0, 3);
        beatSpeed.setMin(150);
        beatSpeed.setMax(250);
        beatSpeed.setValue(200);
        beatSpeed.setShowTickLabels(true);
        beatSpeed.setShowTickMarks(true);
        beatSpeed.setMajorTickUnit(50);
        beatSpeed.setMinorTickCount(5);
        beatSpeed.setBlockIncrement(10);
        inputGridPane.setConstraints(beatSpeed, 1, 3);
        inputGridPane.getChildren().add(beatSpeed);

        // hash output
        Label hashout = new Label("Hash: ");
        inputGridPane.add(hashout, 0, 4);
        inputGridPane.add(hashText, 1, 4);

        // play button
        inputGridPane.add( playButton, 1, 5 );

        // stop button
        inputGridPane.add( stopButton, 1, 6 );

        GridPane.setConstraints(openButton, 1, 1);
        inputGridPane.setAlignment(Pos.CENTER_LEFT);
        inputGridPane.setHgap(5);
        inputGridPane.setVgap(20);
        inputGridPane.getChildren().addAll(openButton);

        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(20, 20, 20, 20));

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
