package SeaBattle;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    String name;
    GameField ownField;
    GameField enemyField;
    boolean shotResult;
    List<Ship> ships;
    Ship hitShip;

    Player opponent;

    public Player() {
        name = null;
        ships = new ArrayList<>();
        hitShip = new Ship(new ArrayList<>());
        shotResult = true;
        ownField = new GameField(Box.CELL);
        enemyField = new GameField(Box.CLOSED);
    }

    public void successfulShot(Coordinate coordinate) {
        shotResult = true;
        for (Ship ship : opponent.getShips()) {
            if (ship.getCoordinates().contains(coordinate)) {
                ship.getCoordinates().remove(coordinate);
                hitShip.getCoordinates().add(coordinate);

                if (ship.isShipAlive()) {
                    opponent.ownField.set(coordinate, Box.HIT);
                    break;
                } else {
                    opponent.ownField.addAureole(hitShip, Box.AUREOLE);
                    enemyField.addAureole(hitShip, Box.OPENED);
                    for (Coordinate coord : hitShip.getCoordinates()) {
                        opponent.ownField.set(coord, Box.SUNK);
                    }
                    opponent.getShips().remove(ship);
                    hitShip = new Ship(new ArrayList<>());
                }
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

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

}
