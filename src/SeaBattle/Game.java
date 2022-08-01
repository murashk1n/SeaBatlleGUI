package SeaBattle;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Computer computer;
    private Player player;
    private GameStatus status;
    private List<Coordinate> list;
    private int counter;

    public Game() {
        Ranges.setSize();
        computer = new Computer();
        player = new Player();
    }

    public GameStatus getStatus() {
        return status;
    }

    public void start() {
        computer.start();
        player.start();
        status = GameStatus.PLACING4;
        list = new ArrayList<>();
        counter = 0;

    }

    public GameStatus changeStatus() {
        if (counter == 0)
            return status = GameStatus.PLACING4;
        if (counter == 1 || counter == 2)
            return status = GameStatus.PLACING3;
        if (counter == 3 || counter == 4 || counter == 5)
            return status = GameStatus.PLACING2;
        if (counter == 6 || counter == 7 || counter == 8 || counter == 9)
            return status = GameStatus.PLACING1;
        if (counter == 10)
            return status = GameStatus.PLAYED;
        return null;
    }

    private boolean isNumberOfDeckRight() {
        if (counter == 0) {
            return list.size() == 4;
        }
        if (counter == 1 || counter == 2) {
            return list.size() == 3;
        }
        if (counter == 3 || counter == 4 || counter == 5) {
            return list.size() == 2;
        }
        if (counter == 6 || counter == 7 || counter == 8 || counter == 9) {
            return list.size() == 1;
        }
        return false;
    }

    public void computersAction() {
        if (status == GameStatus.COMPUTERSTURN) {
            while (computer.isShotResult()) {
                if (!isComputerWin()) {
                    computer.attack(player);
                } else {
                    status = GameStatus.LOOSED;
                    checkWhoWin();
                }
            }
            status = GameStatus.PLAYED;
        }
    }

    public Box getComputerBox(Coordinate coordinate) {
        if (player.getEnemyFieldBox(coordinate) == Box.OPENED) {
            return computer.getOwnFieldBox(coordinate);
        } else {
            return player.getEnemyFieldBox(coordinate);
        }
    }

    public Box getOwnBox(Coordinate coordinate) {
        return player.getOwnFieldBox(coordinate);
    }

    public void pressLeftButtonOnComputersField(Coordinate coordinate) {
        if (status == GameStatus.PLAYED) {
            leftButtonActionOnComputersField(coordinate);
        }
    }

    private void leftButtonOnOwnFieldAction(Coordinate coordinate) {
        list.add(coordinate);
        if (isNumberOfDeckRight()) {
            Ship ship = new Ship(list);
            if (ship.isShipValid()) {
                player.placeShips(ship);
                list = new ArrayList<>();
                counter++;
            } else {
                status = GameStatus.NOTVALIDSHIP;
                notValidShipAction();
                list.clear();
            }
        }
    }

    public void pressLeftButtonOnOwnField(Coordinate coordinate) {
        if (player.getOwnField().isCellEmpty(coordinate)) {
            changeStatus();
            if (counter < 10) {
                player.setShipBox(coordinate);
                leftButtonOnOwnFieldAction(coordinate);
            }
        } else {
            status = GameStatus.OCCUPIED;
        }
    }

    private void notValidShipAction() {
        for (Coordinate coordinate : list) {
            player.getOwnField().set(coordinate, Box.CELL);
        }
    }

    private void leftButtonActionOnComputersField(Coordinate coordinate) {
        player.attack(computer, coordinate);
        if (player.isShotResult()) {
            if (isHumanWin()) {
                status = GameStatus.WINNER;
                checkWhoWin();
            }
        } else {
            status = GameStatus.COMPUTERSTURN;
            computersAction();
        }
        resetShotResult();
    }


    private boolean isHumanWin() {
        return computer.getShips().size() == 0;
    }

    private boolean isComputerWin() {
        return player.getShips().size() == 0;
    }

    private void checkWhoWin() {
        if (status == GameStatus.PLAYED) {
            if (isHumanWin()) {
                status = GameStatus.WINNER;
            }
            if (isComputerWin()) {
                status = GameStatus.LOOSED;
            }
        }
    }

    private void resetShotResult() {
        player.setShotResult();
        computer.setShotResult();
    }
}
