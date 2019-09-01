import java.io.IOException;
import java.util.List;

import logic.Board;
import logic.Constants;
import generate.ObjectsGenerator;

public class GameApp {

    public static void main(String[] args) throws IOException, InterruptedException {
        List<Object> objectsInGame = ObjectsGenerator.createObjectsInGame(Constants.DIMENSION_X, Constants.DIMENSION_Y);

        Board board = new Board(Constants.DIMENSION_X, Constants.DIMENSION_Y);
        board.setObjectsInGame(objectsInGame);


        for (int i = 0; i < 1000; i++) {
//            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            System.out.print("\033[H\033[2J");
            System.out.println(board.getBoardToDisplay());
            board.generateAndMoveObjectsOnScreen();
            board.getStatus().forEach(System.out::println);
            board.getEvents().forEach(System.out::println);
            Thread.sleep(1000);


        }
    }


}

