package generate;

import static logic.Constants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import objects.Grass;
import objects.Trap;
import objects.Sheep;
import objects.Wolf;

public class ObjectsGenerator {


    private ObjectsGenerator() {
    }

    public static List<Object> createObjectsInGame(int dimensionX, int dimensionY) {
        List<Wolf> wolves = new ArrayList<>();
        List<Sheep> sheeps = new ArrayList<>();
        List<Grass> grass = new ArrayList<>();
        List<Trap> traps = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < WOLF_COUPLES; i++) {
            wolves.add(new Wolf(MALE, random.nextInt(dimensionX), random.nextInt(dimensionY)));
            wolves.add(new Wolf(FEMALE, random.nextInt(dimensionX), random.nextInt(dimensionY)));
        }
        for (int i = 0; i < SHEEP_COUPLES; i++) {
            sheeps.add(new Sheep(MALE, random.nextInt(dimensionX), random.nextInt(dimensionY)));
            sheeps.add(new Sheep(FEMALE, random.nextInt(dimensionX), random.nextInt(dimensionY)));
        }
        for (int i = 0; i < GRASS_QUANTITY; i++) {
            grass.add(new Grass(random.nextInt(dimensionX), random.nextInt(dimensionY)));
        }
        for (int i = 0; i < TRAPS_QUANTITY; i++) {
            traps.add(new Trap(random.nextInt(dimensionX), random.nextInt(dimensionY)));
        }

        List<Object> objects = new ArrayList<>();
        objects.addAll(wolves);
        objects.addAll(sheeps);
        objects.addAll(grass);
        objects.addAll(traps);
        return objects;
    }
}
