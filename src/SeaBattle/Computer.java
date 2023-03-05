package SeaBattle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Computer extends Player {
     private final List<Coordinate> shotCollection;

    public Computer() {
        name = "Computer";
        shotCollection = new ArrayList<>();
        getCoordinateFromFile();
    }

    private File getFile() {
        int random = (int) (1 + Math.random() * 4);
        String filename = "res/coordinates/" + random + ".txt";
        return new File(filename);
    }

    private void getCoordinateFromFile() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(getFile());
        } catch (FileNotFoundException e) {
            System.out.println("FIle not found");
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] coordinates = line.split(("[,;]"));
            Ship ship = new Ship(Coordinate.coordinatesParseInt(coordinates));
            this.ships.add(ship);
            placeShips(ship);
        }
    }

    public void placeShips(Ship ship) {
        for (Coordinate coordinate : ship.getCoordinates()) {
            this.ownField.set(coordinate, Box.SHIP);
        }
    }

    public void attack() {
        Coordinate coordinate;
        if (hitShip.isHitShip()) {
            coordinate = chooseCoordinateToShot();
        } else {
            coordinate = Ranges.getRandomCoordinate();
        }
        if (isShotDuplicated(coordinate)) {
            attack();
        } else {
            shotCollection.add(coordinate);
            if (opponent.getOwnFieldBox(coordinate) == Box.SHIP) {
                successfulShot(coordinate);
            } else {
                opponent.ownField.set(coordinate, Box.MISS);
                shotResult = false;
            }
        }
    }

    private Coordinate verticalShipKillStrategy() {
        int lastElement = hitShip.getCoordinates().size() - 1;
        Coordinate variant1 = new Coordinate(hitShip.getCoordinates().get(lastElement).x + 1, hitShip.getCoordinates().get(0).y);
        Coordinate variant2 = new Coordinate(hitShip.getCoordinates().get(0).x - 1, hitShip.getCoordinates().get(0).y);
        if (isCoordinateCanBeUsed(variant1)) {
            return variant1;
        } else if (isCoordinateCanBeUsed(variant2)) {
            return variant2;
        }
        return horizontalShipKillStrategy();
    }

    private Coordinate horizontalShipKillStrategy() {
        int lastElement = hitShip.getCoordinates().size() - 1;
        Coordinate variant1 = new Coordinate(hitShip.getCoordinates().get(0).x, hitShip.getCoordinates().get(lastElement).y + 1);
        Coordinate variant2 = new Coordinate(hitShip.getCoordinates().get(0).x, hitShip.getCoordinates().get(0).y - 1);

        if (isCoordinateCanBeUsed(variant1)) {
            return variant1;
        } else if (isCoordinateCanBeUsed(variant2)) {
            return variant2;
        }
        return verticalShipKillStrategy();
    }


    private Coordinate chooseCoordinateToShot() {
        if (hitShip.isShipCanBeDefined()) {
            hitShip.defineTypeOfShip();
            hitShip.getCoordinates().sort((o1, o2) -> {
                if (hitShip.getTypeOfShip().equals("vertical")) {
                    return Integer.compare(o1.x, o2.x);
                }
                return Integer.compare(o1.y, o2.y);
            });
            if (hitShip.getTypeOfShip().equals("vertical")) {
                return verticalShipKillStrategy();
            }
            return horizontalShipKillStrategy();
        }
        return chooseCoordinateAround();
    }

    private Coordinate chooseCoordinateAround() {
        int random = (int) (0 + Math.random() * 2);
        if (random == 0) {
            return verticalShipKillStrategy();
        } else {
            return horizontalShipKillStrategy();
        }
    }

    private boolean isCoordinateCanBeUsed(Coordinate coordinate) {
        return Ranges.inRange(coordinate) && !isShotDuplicated(coordinate);
    }

    private boolean isShotDuplicated(Coordinate coordinate) {
        return shotCollection.contains(coordinate);
    }

    public Box getOwnFieldBox(Coordinate coordinate) {
        return ownField.get(coordinate);
    }
}

