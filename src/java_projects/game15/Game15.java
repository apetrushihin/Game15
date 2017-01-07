package java_projects.game15;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class Game15 extends Application {

    private Stage game = new Stage();
    private GridPane layout = new GridPane();
    private int[][] numbers = new int[4][4];
    private MenuBar menu;
    private VBox vbox = new VBox();
    private Scene mainScene = new Scene(vbox, 390, 415);


    @Override
    public void start(Stage primaryStage) throws Exception { //calling for draw method
        drawLevel(game);
    }

    public void drawLevel (Stage gameWindow) throws Exception{ //setting parameters for game field
        gameWindow.setTitle("15 puzzle");
        gameWindow.setResizable(false);
        createMenu();
        layout.setPadding(new Insets(0,0,0,0));
        vbox.getChildren().addAll(createMenu(), layout);
        randomizeLevel();
        repaintField();
        gameWindow.setScene(mainScene);
        gameWindow.show();
    }

    public void randomizeLevel(){ //randomizing tiles positions
        Random random = new Random();
        int[] invariants = new int[16];

        do{
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                numbers[i][j] = 0;
                invariants[i*4 + j] = 0; //define which tile will be blank
            }
        }

        for (int i = 1; i < 16; i++) {
            int k;
            int l;
            do {
                k = random.nextInt(4);
                l = random.nextInt(4);
            }
            while (numbers[k][l] != 0); //until numbers not equal 0
            numbers[k][l] = i; //equals i from 1 to 15
            invariants[k * 4 + l] = i; //define positions of all tiles, except blank tile
            }
        }
        while (!gameHasSolution(invariants)); //generate field until game has solution!
    }

    public static boolean gameHasSolution(int[] state){
        //if result is false then generate again, true = start playing

        //prepare the mapping from each tile to its position
        int[] positions = new int[16];
        for(int i = 0; i < state.length; i++){
            positions[state[i]] = (i+1)%16;
        }

        //check whether this is an even or odd state
        int row = (positions[0]-1)/4;
        int col = (positions[0]-1)-row*4;
        boolean isEvenState = positions[0] == 0 || row % 2 == col %2;

        //count the even cycles
        int evenCount = 0;
        boolean[] visited = new boolean[16];
        for(int i = 0; i < positions.length; i++){
            if(visited[i])
                continue;
            //a new cycle starts at i. Count its length
            int cycleLength = 0;
            int nextTile = i;
            while(!visited[nextTile]){
                cycleLength++;
                visited[nextTile] = true;
                nextTile = positions[nextTile];
            }
            if(cycleLength % 2 == 0)
                evenCount++;
        }
        System.out.println(evenCount % 2 == 0);
        return isEvenState == (evenCount % 2 == 0);
    }

    public void repaintField() { //drawing buttons on the gaming field
        layout.getChildren().clear();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Button button = new Button(Integer.toString(numbers[i][j]));
                button.setMaxWidth(100);
                button.setMaxHeight(100);
                button.setMinHeight(100);
                button.setMinWidth(100);
                button.setFocusTraversable(false);
                layout.add(button, i, j);
                if (numbers[i][j] == 0) { //hiding "0" blank button
                    button.setVisible(false);
                } else
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            Button button = (Button) event.getSource();
                            button.setVisible(false);
                            String name = button.getText();
                            change(Integer.parseInt(name));
                        }
                    });
            }
        }

    }


    public boolean checkWin() { //checks if game finished, true=end game, false=lets play some more
        int a = 1;
        boolean status = true;
        for (int i = 0; i <4; i++)
            for (int j = 0; j<4; j++)
            {
                if (i==3&&j==3){a=0;}
                if (numbers[j][i]!=a)
                {
                    status = false;
                    break;
                }
                a++;
            }
        return status;
    }

    private MenuBar createMenu(){
        menu = new MenuBar();
        Menu gameMenu = new Menu("Game");

        MenuItem newGame = new MenuItem("New Game");
        newGame.setOnAction(ActionEvent -> {
                randomizeLevel();
                repaintField();
                game.setScene(mainScene);

        });

        MenuItem exitGame = new MenuItem("Exit");
        exitGame.setOnAction(e -> System.exit(0));

        MenuBar gameMenuBar = new MenuBar();
        gameMenuBar.getMenus().addAll(gameMenu);

        gameMenu.getItems().addAll(newGame, exitGame);
        menu.getMenus().add(gameMenu);
        return menu;
    }

    public void change(int num) { //sliding buttons up, bottom, left, right
        int i = 0, j = 0;
        //searching for blank tile to make a move
        for (int k = 0; k < 4; k++) {
            for (int l = 0; l < 4; l++) {
                if (numbers[k][l] == num) {
                    i = k;
                    j = l;
                }
            }
        }

        //sliding the tiles in the grid
        //sliding up (row)
        if (i > 0) { //checks if we can slide
            if (numbers[i - 1][j] == 0) { //comparing the coordinates of the tile with button "0"
                numbers[i - 1][j] = num; //num = coordinates of the tile
                numbers[i][j] = 0; //swapping buttons
            }
        }
        //sliding down (row)
        if (i < 3) {
            if (numbers[i + 1][j] == 0) {
                numbers[i + 1][j] = num;
                numbers[i][j] = 0;
            }
        }
        //sliding left(column)
        if (j > 0) {
            if (numbers[i][j - 1] == 0) {
                numbers[i][j - 1] = num;
                numbers[i][j] = 0;
            }
        }
        //sliding right(column)
        if (j < 3) {
            if (numbers[i][j + 1] == 0) {
                numbers[i][j + 1] = num;
                numbers[i][j] = 0;
            }
        }
        repaintField();

        if (checkWin()) {

            System.out.println("end game");

            Image winImage = new Image("file:F:\\java projects\\Game15\\src\\java_projects\\Images\\win.jpg");
            ImageView winGameImg = new ImageView();
            winGameImg.setImage(winImage);
            createMenu();
            VBox vbox2 = new VBox();
            vbox2.getChildren().addAll(createMenu(),winGameImg);
            Scene endCredits = new Scene(vbox2,390, 415);
            game.setScene(endCredits);
            }
    }

    public static void main(String[] args) {

        launch(args);
    }

}
