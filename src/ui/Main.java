package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        primaryStage.setTitle("Tetris Game");
        primaryStage.setScene(new Scene(root, 300 + 100, 400));
        ImageView tetrisAx = (ImageView) root.lookup("#image");
        tetrisAx.setImage(new Image(new FileInputStream("tetris.png")));
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
