package ui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene; 
import javafx.scene.control.*;
import javafx.stage.Stage;
import logic.Player;
import logic.Tetris;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    private Tetris tetris;
    private Player player;


    public Controller() {
        this.tetris = Tetris.getInstance();
        tetris.load();
        player = new Player(0, "player");
    }

    /**********************  Main.fxml **********************/
    public void NewGameBtnClick(ActionEvent actionEvent) throws Exception {
        final Node source = (Node) actionEvent.getSource();
        final Stage primaryStage = (Stage) source.getScene().getWindow();
        Parent newName;
        newName = FXMLLoader.load(getClass().getResource("NewName.fxml"));
        primaryStage.setTitle("Name");
        Scene scene = new Scene(newName, 400, 400);
        primaryStage.setScene(scene);
        ((TextField) newName.lookup("#NameText")).setText(player.getName());
        primaryStage.show();
    }

    public void ScoresTableBtnClick(ActionEvent actionEvent) throws IOException {
        Stage scoresStage = new Stage();
        Parent scores;
        scores = FXMLLoader.load(getClass().getResource("ScoresTable.fxml"));
        scoresStage.setTitle("ScoresTable");
        Scene scene = new Scene(scores, 440, 330);
        scoresStage.setScene(scene);
        TableView ScoreTable = (TableView) scores.lookup("#ScoreTable");
        ScoreTable.setPlaceholder(new Label("No Score to display"));
        ScoreTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ScoreTable.getItems().addAll(tetris.getScoreManager().getPlayers());

        scoresStage.show();

    }

    public void ExitBtnClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exit Dialog");
        alert.setHeaderText("");
        alert.setContentText("Are you sure?");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(ButtonType.YES);
        alert.getButtonTypes().add(ButtonType.NO);
        alert.showAndWait();
        tetris.save();
        if (alert.getResult() == ButtonType.YES) {
            Platform.exit();
        }
    }

    public void SettingBtnClick(ActionEvent actionEvent) throws IOException {
        Stage settingStage = new Stage();
        Parent settings;
        settings = FXMLLoader.load(getClass().getResource("SettingStage.fxml"));
        settingStage.setTitle("Setting");
        Scene scene = new Scene(settings, 440, 330);
        settingStage.setScene(scene);

        //load current settings
        TextField scorePerRow = (TextField) settings.lookup("#SetScoresPerRowText");
        TextField startLevel = (TextField) settings.lookup("#SetStartText");
        ColorPicker color = (ColorPicker) settings.lookup("#colorPicker");
        scorePerRow.setText(String.valueOf(tetris.getSettingManger().getScorePerRow()));
        startLevel.setText(String.valueOf(tetris.getSettingManger().getStartLevel()));
        color.setValue(tetris.getSettingManger().getBackgroundColor());

        //listeners
        scorePerRow.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    try {
                        int value = Integer.parseInt(scorePerRow.getText());
                        tetris.getSettingManger().setScorePerRow(value);
                        tetris.save();
                    } catch (Exception e) {
                        alert();
                    }
                }
            }
        });

        startLevel.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    try {
                        int value = Integer.parseInt(startLevel.getText());
                        if (value < 1 || value > 10) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Information Dialog");
                            alert.setHeaderText("Look!!!    an Information Dialog");
                            alert.setContentText("Please Enter a number Between 1 and 10");
                            alert.showAndWait();
                            return;
                        }
                        tetris.getSettingManger().setStartLevel(value);
                        tetris.save();
                    } catch (Exception e) {
                        alert();
                    }
                }
            }
        });

        color.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    try {
                        tetris.getSettingManger().setBackgroundColor(color.getValue());
                        tetris.save();
                    } catch (Exception e) {
                        alert();
                    }
                }
            }
        });

        settingStage.show();

    }

    void alert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Look!!!    an Information Dialog");
        alert.setContentText("This number is not correct");
        alert.showAndWait();
    }

    /*************************** NewName.fxml ***************************/
    @FXML
    private TextField NameText;
    public void NextNameBtnClick(ActionEvent actionEvent) throws IOException {
        player.setName(NameText.getText());
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        Parent newGame;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewGame.fxml"));
        newGame = loader.load();
        stage.setTitle("New Game");
        Scene scene = new Scene(newGame, 500, 420);
        stage.setScene(scene);
        GameController gameController = loader.getController();
        tetris.newgame();
        gameController.setPlayer(player);
        stage.show();
    }

    /*************************** SettingStage.fxml ***************************/
    public void ClearScoresBtnClick(ActionEvent actionEvent) {
        tetris.getScoreManager().setPlayers(new ArrayList<>());
        tetris.save();
    }


}

