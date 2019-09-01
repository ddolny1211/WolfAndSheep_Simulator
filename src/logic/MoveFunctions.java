package logic;

import static logic.Constants.*;

import java.util.Random;
import objects.Position;
import objects.Sheep;
import objects.Wolf;

public class MoveFunctions {
    private static final String MOVE_UP = "MOVE_UP";
    private static final String MOVE_DOWN = "MOVE_DOWN";
    private static final String MOVE_RIGHT = "MOVE_RIGHT";
    private static final String MOVE_LEFT = "MOVE_LEFT";
    private static final String EMPTY = "";
    Random generator = new Random();

    //POZYCJA WILKA I OWCY

    public void moveWolf(Wolf wolf) {
        String movement = generateMovement();
        moveAnimal(movement, wolf, wolf.getMoveSpeed());

    }

    public void moveSheep(Sheep sheep) {
        String movement = generateMovement();
        moveAnimal(movement, sheep, sheep.getMoveSpeed());

    }

    private void moveAnimal(String movement, Position animalPostion, int step) {
        switch (movement) {
            case MOVE_UP:
                moveUp(animalPostion, step);
                break;
            case MOVE_DOWN:
                moveDown(animalPostion, step);
                break;
            case MOVE_LEFT:
                moveLeft(animalPostion, step);
                break;
            case MOVE_RIGHT:
                moveRight(animalPostion, step);
                break;
            default:
                throw new IllegalArgumentException("Zły ruch!");
        }

    }

    private void moveRight(Position animalPostion, int step) {
        if (animalPostion.getPositionX() + step * 2 > DIMENSION_X) {
            animalPostion.setPositionX(animalPostion.getPositionX() - step * 2);
        } else {
            animalPostion.setPositionX(animalPostion.getPositionX() + step * 2);
        }
    }

    private void moveLeft(Position animalPostion, int step) {
        if (animalPostion.getPositionX() - step * 2 < 0) {
            animalPostion.setPositionX(animalPostion.getPositionX() + step * 2);
        } else {
            animalPostion.setPositionX(animalPostion.getPositionX() - step * 2);
        }
    }

    private void moveDown(Position animalPostion, int step) {
        if (animalPostion.getPositionY() + step > DIMENSION_Y) {
            animalPostion.setPositionY(animalPostion.getPositionY() - step);
        } else {
            animalPostion.setPositionY(animalPostion.getPositionY() + step);
        }
    }

    private void moveUp(Position animalPostion, int step) {
        if (animalPostion.getPositionY() - step < 0) {
            animalPostion.setPositionY(animalPostion.getPositionY() + step);
        } else {
            animalPostion.setPositionY(animalPostion.getPositionY() - step);
        }
    }

    private String generateMovement() {
        int number = generator.nextInt(4);
        if (number < 1) {
            return MOVE_UP;
        } else if (number >= 1 && number < 2) {
            return MOVE_DOWN;
        } else if (number >= 2 && number < 3) {
            return MOVE_RIGHT;
        } else if (number >= 3) {
            return MOVE_LEFT;
        }
        return EMPTY;
    }
}
