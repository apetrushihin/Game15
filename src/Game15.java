import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Game15 extends Application {

    BorderPane layout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("15 puzzle");


        Menu gameMenu = new Menu("Game");

        MenuItem newGame = new MenuItem("New Game");
        gameMenu.getItems().add(newGame);


        MenuItem exitGame = new MenuItem("Exit");
        gameMenu.getItems().add(exitGame);
        exitGame.setOnAction(e -> primaryStage.close());

        MenuBar gameMenuBar = new MenuBar();
        gameMenuBar.getMenus().addAll(gameMenu);

        layout = new BorderPane();
        layout.setTop(gameMenuBar);

        Scene scene = new Scene(layout, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
