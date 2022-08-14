package SeaBattle;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Player {
    private String name;
    private GameField ownField;
    private GameField enemyField;
    private boolean shotResult;
    private List<Ship> ships;
    private Ship hitShip;

    public Player() {
        name = "Andrei";
        ships = new ArrayList<>();
        hitShip = new Ship(new ArrayList<>());
        shotResult = true;
        ownField = new GameField(Box.CELL);
        enemyField = new GameField(Box.CLOSED);

    }

    public void setShotResult() {
        this.shotResult = true;
    }

    public boolean isShotResult() {
        return shotResult;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public GameField getOwnField() {
        return ownField;
    }

    public Box getEnemyFieldBox(Coordinate coordinate) {
        return enemyField.get(coordinate);
    }

    public Box getOwnFieldBox(Coordinate coordinate) {
        return ownField.get(coordinate);
    }

    public void setShipBox(Coordinate coordinate) {
        ownField.set(coordinate, Box.OPENED);
    }

    public void attack(Computer computer, Coordinate coordinate) {
        enemyField.set(coordinate, Box.OPENED);
        switch (computer.getOwnFieldBox(coordinate)) {
            case SHIP:
                successfulShot(computer, coordinate);
                shotResult = true;
                break;
            default:
                enemyField.set(coordinate, Box.MISS);
                shotResult = false;
        }
    }

    private void successfulShot(Computer computer, Coordinate coordinate) {
        for (Ship ship : computer.getShips()) {
            if (ship.getCoordinates().contains(coordinate)) {
                ship.getCoordinates().remove(coordinate);
                hitShip.getCoordinates().add(coordinate);
                if (ship.isShipAlive()) {
                    enemyField.set(coordinate, Box.HIT);
                    break;
                } else {
                    enemyField.addAureole(hitShip, Box.AUREOLE);
                    for (Coordinate coord : hitShip.getCoordinates()) {
                        enemyField.set(coord, Box.SUNK);
                    }
                    computer.getShips().remove(ship);
                    hitShip = new Ship(new ArrayList<>());
                }
                shotResult = true;
                break;
            }
        }
    }

    public void placeShips(Ship ship) {
        this.ships.add(ship);
        this.ownField.addAureole(ship, Box.SHIELD);
        for (Coordinate c : ship.getCoordinates()) {
            ownField.set(c, Box.SHIP);
        }
    }


}

