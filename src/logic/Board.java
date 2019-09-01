package logic;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import objects.GraphicImage;
import objects.Grass;
import objects.Position;
import objects.Trap;
import objects.Sheep;
import objects.Wolf;

import static logic.Constants.MALE;

public class Board {
    private int dimensionX;
    private int dimensionY;
    Random random = new Random();
    private List<Object> objectsInGame;
    private MoveFunctions moveFunctions;

    private String boardToDisplay = "";

    List<String> events = new ArrayList<>();
    List<String> statistic = new ArrayList<>();

    public Board(int dimensionX, int dimensionY) {
        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
        moveFunctions = new MoveFunctions();
    }

    public synchronized void setObjectsInGame(List<Object> objectsInGame) {
        this.objectsInGame = objectsInGame;
    }

    public void generateAndMoveObjectsOnScreen() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                checkLiveTime();
                detectCollistions();
                generateObjects();
                moveWolfsAndSheeps();
            }

            private void checkLiveTime(){
                List<Wolf> wolves = new ArrayList<>();
                List<Sheep> sheeps = new ArrayList<>();
                List<Trap> traps = new ArrayList<>();
                List<Grass> grasses = new ArrayList<>();

                for (Object object : objectsInGame) {
                    if (object instanceof Wolf) {
                        wolves.add((Wolf) object);
                    } else if (object instanceof Sheep) {
                        sheeps.add((Sheep) object);
                    } else if (object instanceof Trap) {
                        traps.add((Trap) object);
                    } else if (object instanceof Grass) {
                        grasses.add((Grass) object);
                    }
                }
                List<Wolf> wolvesLeft = getWolvesLeft(wolves);
                List<Sheep> sheepsLeft = getSheepsLeft(sheeps);

                List<Object> newObjects = new ArrayList<>();
                newObjects.addAll(wolvesLeft);
                newObjects.addAll(sheepsLeft);
                newObjects.addAll(grasses);
                newObjects.addAll(traps);

                setObjectsInGame(newObjects);

            }
            private List<Wolf> getWolvesLeft(List<Wolf> wolves){
                List<Wolf> wolvesLeft = new ArrayList<>(wolves);
                for(Wolf wolf : wolves){
                    wolf.setLiveTime(wolf.getLiveTime()-1);
                    if(wolf.getLiveTime()==0){
                        wolvesLeft.remove(wolf);
                        addEvent("Wilk umarł z głodu! ");
                    }
                }
                return wolvesLeft;
            }
            private List<Sheep> getSheepsLeft(List<Sheep> sheeps){
                List<Sheep> sheepsLeft = new ArrayList<>(sheeps);
                for(Sheep sheep : sheeps){
                    sheep.setLiveTime(sheep.getLiveTime()-1);
                    if(sheep.getLiveTime()==0){
                        sheepsLeft.remove(sheep);
                        addEvent("Owca umarła z głodu! ");
                    }
                }
                return sheepsLeft;
            }

            private void detectCollistions() {
                List<Wolf> wolves = new ArrayList<>();
                List<Sheep> sheeps = new ArrayList<>();
                List<Trap> traps = new ArrayList<>();
                List<Grass> grasses = new ArrayList<>();

                for (Object object : objectsInGame) {
                    if (object instanceof Wolf) {
                        wolves.add((Wolf) object);
                    } else if (object instanceof Sheep) {
                        sheeps.add((Sheep) object);
                    } else if (object instanceof Trap) {
                        traps.add((Trap) object);
                    } else if (object instanceof Grass) {
                        grasses.add((Grass) object);
                    }
                }

                List<Sheep> aliveSheeps = getAliveSheeps(wolves, sheeps);
                List<Wolf> aliveWolfs = getAliveWolfs(wolves, traps);
                List<Grass> grassLeft = getGrassLeft(sheeps, grasses);
                List<Wolf> newBornWolves = getNewWolves(aliveWolfs);
                List<Sheep> newBornSheeps = getNewSheeps(aliveSheeps);

                List<Object> newObjects = new ArrayList<>();
                newObjects.addAll(aliveSheeps);
                newObjects.addAll(aliveWolfs);
                newObjects.addAll(newBornWolves);
                newObjects.addAll(newBornSheeps);
                newObjects.addAll(traps);
                newObjects.addAll(grassLeft);

                setObjectsInGame(newObjects);

                addStatus("Liczba wilkow: "+aliveWolfs.size());
                addStatus("Liczba owiec: "+aliveSheeps.size());

            }

            private List<Sheep> getNewSheeps(List<Sheep> sheeps){
                List<Sheep> newBornSheeps = new ArrayList<>();
                for (Sheep sheep1 : sheeps) {
                    for (Sheep sheep2 : sheeps) {
                        if(!sheep1.equals(sheep2)) {
                            if (sheep1.getPositionX() == sheep2.getPositionX() && sheep1.getPositionY() == sheep2.getPositionY()) {
                                if (!sheep1.getSex().equals(sheep2.getSex())) {
                                    newBornSheeps.add(new Sheep(MALE, random.nextInt(dimensionX), random.nextInt(dimensionY)));
                                    addEvent("Narodziła się nowa Owca!");
                                }
                            }
                        }
                    }
                }
                return newBornSheeps;

            }

            private List<Wolf> getNewWolves(List<Wolf> wolves){
                List<Wolf> newBornWolves = new ArrayList<>();
                for (Wolf wolf1 : wolves) {
                    for (Wolf wolf2 : wolves) {
                        if(!wolf1.equals(wolf2)) {
                            if (wolf1.getPositionX() == wolf2.getPositionX() && wolf1.getPositionY() == wolf2.getPositionY()) {
                                if (!wolf1.getSex().equals(wolf2.getSex())) {
                                    newBornWolves.add(new Wolf(MALE, random.nextInt(dimensionX), random.nextInt(dimensionY)));
                                    addEvent("Narodził się nowy Wilk!");
                                }
                            }
                        }
                    }
                }
                return newBornWolves;

            }

            private List<Grass> getGrassLeft(List<Sheep> sheeps, List<Grass> grasses){
                List<Grass> grassLeft = new ArrayList<>(grasses);
                for (Sheep sheep : sheeps) {
                    for (Grass grass : grasses) {
                        if (sheep.getPositionX() == grass.getPositionX() && sheep.getPositionY() == grass.getPositionY()) {
                            grassLeft.remove(grass);
                            addEvent("Owca zjadła trawę! ");
                            sheep.setLiveTime(sheep.getLiveTime()+30);
                        }
                    }
                }
                return grassLeft;

            }

            private List<Sheep> getAliveSheeps(List<Wolf> wolves, List<Sheep> sheeps) {
                List<Sheep> aliveSheeps = new ArrayList<>(sheeps);
                for (Wolf wolf : wolves) {
                    for (Sheep sheep : sheeps) {
                        if (wolf.getPositionX() == sheep.getPositionX() && wolf.getPositionY() == sheep.getPositionY()) {
                            aliveSheeps.remove(sheep);
                            addEvent("Wilk zjadł owcę!");
                            wolf.setLiveTime(wolf.getLiveTime()+40);
                        }
                    }
                }
                return aliveSheeps;
            }
            private List<Wolf> getAliveWolfs(List<Wolf> wolves, List<Trap> traps) {
                List<Wolf> aliveWolfs = new ArrayList<>(wolves);
                for (Wolf wolf : wolves) {
                    for (Trap trap : traps) {
                        if (wolf.getPositionX() == trap.getPositionX() && wolf.getPositionY() == trap.getPositionY()) {
                            aliveWolfs.remove(wolf);
                            addEvent("Wilk wpadł w pułapkę!");
                        }
                    }
                }
                return aliveWolfs;
            }

            private void moveWolfsAndSheeps() {
                for (Object object : objectsInGame) {
                    if (object instanceof Wolf) {
                        moveFunctions.moveWolf((Wolf) object);
                    } else if (object instanceof Sheep) {
                        moveFunctions.moveSheep((Sheep) object);
                    }
                }
            }

            private void generateObjects() {
                StringBuilder sb = new StringBuilder();
                for (int y = 0; y < dimensionY; y++) {
                    for (int x = 0; x < dimensionX; x++) {
                        boolean found = false;
                        for (Object object : objectsInGame) {
                            Position position = convertToPostionObject(object);
                            GraphicImage graphicImage = convertToImageObject(object);
                            int positionX = position.getPositionX();
                            int positionY = position.getPositionY();

                            if (positionX == x && positionY == y) {
                                sb.append(graphicImage.getGraphicImage());
                                found = true;
                            }
                        }

                        if (!found) {
                            sb.append(" ");
                        }
                    }
                    sb.append(System.lineSeparator());
                }
                setBoardToDisplay(sb.toString());
            }
        });

        thread.setDaemon(true);
        thread.start();

    }
    private synchronized  void addStatus(String status){
        statistic.add(status);
    }

    public synchronized List<String> getStatus(){
        List<String> tempStatus = new ArrayList<>(statistic);
        statistic.clear();
        return tempStatus;
    }

    private synchronized void addEvent(String event) {
        events.add(event);
    }

    public synchronized List<String> getEvents() {
        List<String> tempEvents = new ArrayList<>(events);
        events.clear();
        return tempEvents;
    }

    public synchronized void setBoardToDisplay(String boardToDisplay) {
        this.boardToDisplay = boardToDisplay;
    }

    public synchronized String getBoardToDisplay() {
        return boardToDisplay;
    }

    private Position convertToPostionObject(Object object) {
        if (object instanceof Grass) {
            return (Grass) object;
        } else if (object instanceof Trap) {
            return (Trap) object;
        } else if (object instanceof Wolf) {
            return (Wolf) object;
        } else if (object instanceof Sheep) {
            return (Sheep) object;
        }
        return null;
    }

    private GraphicImage convertToImageObject(Object object) {
        if (object instanceof Grass) {
            return (Grass) object;
        } else if (object instanceof Trap) {
            return (Trap) object;
        } else if (object instanceof Wolf) {
            return (Wolf) object;
        } else if (object instanceof Sheep) {
            return (Sheep) object;
        }
        return null;
    }

    public int getDimensionX() {
        return dimensionX;
    }

    public int getDimensionY() {
        return dimensionY;
    }
}
