
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public final class GUI extends Application {

    private Desktop desktop = Desktop.getDesktop();

    @Override
    public void start(final Stage stage) {
    	stage.setTitle("Harmonibites");
        final FileChooser fileChooser = new FileChooser();

        final Button openButton = new Button("Browse");

        openButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null) {
                        SHA256Hash hash = new SHA256Hash(file.getPath());
                    }
                }
            });


        final GridPane inputGridPane = new GridPane();

        Text scenetitle = new Text("Choose a file to play sound for");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        inputGridPane.add(scenetitle, 0, 0, 2, 1);

        Label fileButton = new Label("Select file: ");
        inputGridPane.add(fileButton, 0, 1);

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

    public static void main(String[] args) {
        Application.launch(args);
    }

    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                Main.class.getName()).log(
                    Level.SEVERE, null, ex
                );
        }
    }
}