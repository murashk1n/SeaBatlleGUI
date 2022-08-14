package SeaBattle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Computer {
    private String name;
    private GameField ownField;
    private GameField enemyField;
    private boolean shotResult;
    private List<Ship> ships;
    private Ship hitShip;
    private List<Coordinate> shotCollection;

    public Computer() {
        name = "Computer";
        ships = new ArrayList<>();
        hitShip = new Ship(new ArrayList<>());
        shotCollection = new ArrayList<>();
        shotResult = true;
        ownField = new GameField(Box.CELL);
        enemyField = new GameField(Box.CELL);
        placeShips();
    }

    public boolean isShotResult() {
        return shotResult;
    }

    public void setShotResult() {
        this.shotResult = true;
    }

    public List<Ship> getShips() {
        return ships;
    }

    private File getFile(int number) {
        String filename = "res/coordinates/" + number + ".txt";
        return new File(filename);
    }

    private void placeShips() {
        int random = (int) (1 + Math.random() * 4);
        Scanner scanner = null;
        try {
            scanner = new Scanner(getFile(random));
        } catch (FileNotFoundException e) {
            System.out.println("FIle not found");
        }

        while (true) {
            assert scanner != null;
            if (!scanner.hasNextLine()) break;

            String line = scanner.nextLine();
            String[] coordinates = line.split(("[.,:;()?!\"\\s–]+"));

            Ship ship = new Ship(Coordinate.coordinatesParseInt(coordinates));
            for (Coordinate coordinate : ship.getCoordinates()) {
                this.ownField.set(coordinate, Box.SHIP);
            }
            this.ships.add(ship);
        }
    }

    public void attack(Player player) {
        Coordinate coordinate;
        if (hitShip.isHitShip()) {
            coordinate = chooseCoordinateToShot();
        } else {
            coordinate = Ranges.getRandomCoordinate();
        }
        if (isShotDuplicated(coordinate)) {
            attack(player);
        } else {
            shotCollection.add(coordinate);
            switch (player.getOwnFieldBox(coordinate)) {
                case SHIP:
                    successfulShot(player, coordinate);
                    break;
                default:
                    player.getOwnField().set(coordinate, Box.MISS);
                    shotResult = false;
            }
        }
    }

    private void successfulShot(Player player, Coordinate coordinate) {
        for (Ship ship : player.getShips()) {
            if (ship.getCoordinates().contains(coordinate)) {
                ship.getCoordinates().remove(coordinate);
                hitShip.getCoordinates().add(coordinate);
                if (ship.isShipAlive()) {
                    player.getOwnField().set(coordinate, Box.HIT);
                } else {
                    enemyField.addAureole(hitShip, Box.AUREOLE);
                    for (Coordinate coord : hitShip.getCoordinates()) {
                        player.getOwnField().set(coord, Box.SUNK);
                    }
                    player.getShips().remove(ship);
                    hitShip = new Ship(new ArrayList<>());
                }
                shotResult = true;
                break;
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

    private boolean isCoordinateCanBeUsed(Coordinate coordinate) {
        return Ranges.inRange(coordinate) && !isShotDuplicated(coordinate);
    }

    private boolean isShotDuplicated(Coordinate coordinate) {
        return shotCollection.contains(coordinate);
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
            } else {
                return horizontalShipKillStrategy();
            }
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

    public Box getOwnFieldBox(Coordinate coordinate) {
        return ownField.get(coordinate);
    }
}

