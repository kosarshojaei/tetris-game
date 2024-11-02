package ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.Direction;
import logic.Player;
import logic.State;
import logic.Tetris;
import logic.bricks.Brick;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GameController {

    @FXML
    public GridPane brickPanel;
    @FXML
    public BorderPane gameBoard;
    @FXML
    public GridPane gamePanel;
    @FXML
    public GridPane nextBrick;
    @FXML
    public Text levelValue;
    @FXML
    public Text scoreValue;
    @FXML
    public Text heartValue;
    @FXML
    public ToggleButton pauseButton;

    private Rectangle[][] displayMatrix;
    private Tetris tetris;
    private Player player;
    private Rectangle[][] rectangles;
    private Timeline timeLine;

    public void initialize() {
        this.tetris = Tetris.getInstance();
        this.tetris.setGameController(this);
        startGame();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private void startGame() {
        gamePanel.setBackground(new Background(new BackgroundFill(tetris.getBackgroundColor(), CornerRadii.EMPTY, Insets.EMPTY)));
        //add listeners
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (tetris.getGameBoard().getState() == State.START && tetris.getHeart() != 0) {
                    if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                        tetris.move(Direction.LEFT);
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                        tetris.move(Direction.RIGHT);
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                        tetris.rotate();
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                        tetris.moveDown();
                        keyEvent.consume();
                    }
                }
            }
        });

        // draw Background
        int row = tetris.getGameBoard().getROW();
        int col = tetris.getGameBoard().getCOL();
        int cellSize = tetris.getGameBoard().getCellSize();
        displayMatrix = new Rectangle[col][row];
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                Rectangle rectangle = new Rectangle(cellSize, cellSize);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, i, j);
            }
        }

        //add current brick
        repaintBrick();
        //add panel
        repaintPanel();

        timeLine = new Timeline(new KeyFrame(
                Duration.millis(400 / tetris.getLevel()),
                ae -> tetris.moveDown()
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
        tetris.getGameBoard().start();
    }

    public void pauseGame(ActionEvent actionEvent) {
        if (tetris.getGameBoard().getState() == State.START) {
            tetris.stop();
            pauseButton.setText("Resume");
        } else {
            tetris.start();
            pauseButton.setText("Pause");
        }
    }

    public void gameOver() {
        tetris.getGameBoard().stop();
        timeLine.stop();
        player.setScore(tetris.getScore());

        Stage scoresStage = new Stage();
        Parent gameOver = null;
        try {
            gameOver = FXMLLoader.load(getClass().getResource("GameOver.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scoresStage.setTitle("ScoresTable");
        Scene scene = new Scene(gameOver, 440, 330);
        scoresStage.setScene(scene);
        Text result = (Text) gameOver.lookup("#result");

        if (tetris.getScoreManager().addPlayer(player)) {
            result.setText("Congrats!! You set a new record\n" + tetris.getScore());
            tetris.save();
        }
        scoresStage.show();
        Stage primaryStage = (Stage) gamePanel.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Tetris Game");
        primaryStage.setScene(new Scene(root, 300 + 100, 400));
        ImageView tetrisAx = (ImageView) root.lookup("#image");
        try {
            tetrisAx.setImage(new Image(new FileInputStream("tetris.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void repaint() {
        repaintBackground();
        repaintBrick();
        repaintPanel();
        gamePanel.requestFocus();
    }

    private void repaintPanel() {
        scoreValue.setText(String.valueOf(tetris.getScore()));
        heartValue.setText(String.valueOf(tetris.getHeart()));
        levelValue.setText(String.valueOf(tetris.getLevel()));

        Brick brick = tetris.getGameBoard().getNextBrick();
        boolean[][] nextBrickShape = brick.getShape();
        nextBrick.getChildren().clear();
        int cellSize = tetris.getGameBoard().getCellSize();
        for (int i = 0; i < brick.getWIDTH(); i++) {
            for (int j = 0; j < brick.getHEIGHT(); j++) {
                Rectangle rectangle = new Rectangle(cellSize, cellSize);
                rectangle.setFill(tetris.getGameBoard().getNextBrick().getColor());
                rectangle.setArcHeight(9);
                rectangle.setArcWidth(9);
                if (nextBrickShape[i][j]) {
                    nextBrick.add(rectangle, i, j);
                }
            }
        }

    }

    private void repaintBrick() {
        int cellSize = tetris.getGameBoard().getCellSize();
        Brick brick = tetris.getGameBoard().getCurrentBrick();

        rectangles = new Rectangle[3][3];
        brickPanel.getChildren().clear();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Rectangle rectangle = new Rectangle(cellSize, cellSize);
                Color color = brick.getShape()[i][j] ? brick.getColor() : Color.TRANSPARENT;
                rectangle.setFill(color);
                rectangle.setArcHeight(9);
                rectangle.setArcWidth(9);
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, i, j);
            }
        }
        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.x * brickPanel.getVgap() + brick.x * cellSize);
        brickPanel.setLayoutY(gamePanel.getLayoutY() + brick.y * brickPanel.getHgap() + brick.y * cellSize);
    }

    private void repaintBackground() {
        boolean[][] board = tetris.getGameBoard().getBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Color color = board[i][j] ? Color.GRAY : Color.TRANSPARENT;
                displayMatrix[i][j].setFill(color);
                displayMatrix[i][j].setArcHeight(9);
                displayMatrix[i][j].setArcWidth(9);
            }
        }
    }

    public void levelup() {
        timeLine.stop();
        timeLine = new Timeline(new KeyFrame(
                Duration.millis(400 / tetris.getLevel()),
                ae -> tetris.moveDown()
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }
}
