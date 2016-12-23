import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

import java.util.Random;

public class Game15 extends Application {

    private GridPane layout = new GridPane();
    Stage gameWindow;
    private static Random generator = new Random();
    private int[][] numbers = new int[4][4];
    private MenuBar menu;



    @Override
    public void start(Stage primaryStage) throws Exception {

        gameWindow = primaryStage;

        gameWindow.setTitle("15 puzzle");
        gameWindow.setResizable(false);
        createMenu();
        layout.setPadding(new Insets(0,0,0,0));
        VBox vBox = new VBox();
        Scene scene = new Scene(vBox, 390, 415);
        vBox.getChildren().addAll(createMenu(), layout);

        gameWindow.setScene(scene);
        gameWindow.show();



    }

    private MenuBar createMenu(){
        menu = new MenuBar();
        Menu gameMenu = new Menu("Game");

        MenuItem newGame = new MenuItem("New Game");


        MenuItem exitGame = new MenuItem("Exit");
        exitGame.setOnAction(e -> gameWindow.close());

        MenuBar gameMenuBar = new MenuBar();
        gameMenuBar.getMenus().addAll(gameMenu);

        gameMenu.getItems().addAll(newGame, exitGame);
        menu.getMenus().add(gameMenu);
        return menu;
    }

    public static void main(String[] args) {

        launch(args);
    }

}
