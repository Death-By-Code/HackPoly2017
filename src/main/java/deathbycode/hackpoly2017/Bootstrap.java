package deathbycode.hackpoly2017;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.guigarage.flatterfx.FlatterFX;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public final class Bootstrap extends Application {

    private Desktop desktop = Desktop.getDesktop();
    private ToneSynth toneSynth = new ToneSynth();
    private File file;
    private PlayControl playControl;
    private boolean hasFile;
    private Alert alert = new Alert(Alert.AlertType.WARNING);

    @Override
    public void start(final Stage stage) {
    	stage.setTitle("Harmonibites");
        alert.setContentText("You must select a file before you can use this button");
        alert.setWidth(480);
        alert.setHeaderText(null);
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
                    hasFile = false;
                    fileHex.setValue("...");
                    filestr.setValue("...");
                    stage.setWidth(720);
                    file = fileChooser.showOpenDialog(stage);
                    if (file != null) {
                        stage.setWidth(1280);
                        hasFile = true;
                       SHA256Hash hash = new SHA256Hash(file.getPath());
                    	try {
							fileHex.setValue(hash.hashFileHex());
							filestr.setValue(file.getName());
						} catch (Exception e1) {
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
                    if (!hasFile) {
                        alert.showAndWait();
                        return;
                    }
                   if (file != null) {
                       SHA256Hash hash = new SHA256Hash( file.getPath() );
                       hasFile = true;
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

						} catch (Exception e1) {
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
                        if (!hasFile) {
                            alert.showAndWait();
                            return;
                        }
                        if (playControl != null) {
                            playControl.stopMusic();
                        }
                    }
                });

        final GridPane inputGridPane = new GridPane();
        inputGridPane.setPrefSize(500, 300);

        Label scenetitle = new Label("Choose a file to play sound for");
        scenetitle.setFont(new Font(40));
        inputGridPane.add(scenetitle, 0, 0, 2, 1);

        // file selection
        Label fileButton = new Label("Select file: ");
        inputGridPane.add(fileButton, 0, 1);

        // file name
        Label fileTitle = new Label("File name: ");
        inputGridPane.add(fileTitle, 0, 2);
        inputGridPane.add(fileName, 1, 2);

        // hash output
        Label hashout = new Label("Hash: ");
        inputGridPane.add(hashout, 0, 3);
        inputGridPane.add(hashText, 1, 3);


        // BPM slider
        Label bpm = new Label("BPM: ");
        inputGridPane.add(bpm, 0, 4);
        beatSpeed.setMin(150);
        beatSpeed.setMax(250);
        beatSpeed.setValue(200);
        beatSpeed.setShowTickLabels(true);
        beatSpeed.setShowTickMarks(true);
        beatSpeed.setMajorTickUnit(50);
        beatSpeed.setMinorTickCount(5);
        beatSpeed.setBlockIncrement(10);
        inputGridPane.setConstraints(beatSpeed, 1, 4);
        inputGridPane.getChildren().add(beatSpeed);

        // play stop buttons
        GridPane buttonsPane = new GridPane();
        buttonsPane.add( playButton, 1, 0 );
        buttonsPane.add( stopButton, 3, 0 );
        GridPane.setMargin(playButton, new Insets(5, 5, 5, 5));
        GridPane.setMargin(stopButton, new Insets(5, 5, 5, 5));
        inputGridPane.add(buttonsPane, 1, 5);

        GridPane.setConstraints(openButton, 1, 1);
        playButton.setMinHeight(65);
        stopButton.setMinHeight(65);
        openButton.setMinHeight(65);
        playButton.setMinWidth(150);
        stopButton.setMinWidth(150);
        openButton.setMinWidth(150);
        inputGridPane.setAlignment(Pos.BASELINE_CENTER);
        inputGridPane.setHgap(5);
        inputGridPane.setVgap(20);
        inputGridPane.getChildren().addAll(openButton);

        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(20, 20, 20, 20));

        Scene scene = new Scene(rootGroup);
        stage.setScene(scene);
        stage.setHeight(500);
        stage.setWidth(720);
        stage.setResizable(false);
        stage.show();
        FlatterFX.style();
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
