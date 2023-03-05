package SeaBattle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Game implements ActionListener {
    Timer timer = new Timer(300, this);
    private Computer computer;
    private Human human;
    private GameStatus status;
    private List<Coordinate> list;
    private int counter;

    public Game() {
        Ranges.setSize();
    }

    public GameStatus getStatus() {
        return status;
    }

    public void start() {
        timer.start();
        this.human = new Human();
        this.computer = new Computer();
        human.setOpponent(computer);
        computer.setOpponent(human);
        status = GameStatus.PLACING4;
        list = new ArrayList<>();
        counter = 0;
    }

    public void changeStatus() {
        if (counter == 0) {
            status = GameStatus.PLACING4;
            return;
        }
        if (counter == 1 || counter == 2) {
            status = GameStatus.PLACING3;
            return;
        }
        if (counter == 3 || counter == 4 || counter == 5) {
            status = GameStatus.PLACING2;
            return;
        }
        if (counter == 6 || counter == 7 || counter == 8 || counter == 9) {
            status = GameStatus.PLACING1;
            return;
        }
        if (counter == 10) {
            status = GameStatus.PLAYED;
        }
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
                    computer.attack();
                } else {
                    status = GameStatus.LOOSED;
                }
            }
            status = GameStatus.PLAYED;
        }
    }

    public Box getComputerBox(Coordinate coordinate) {
        if (human.getEnemyFieldBox(coordinate) == Box.OPENED) {
            return computer.getOwnFieldBox(coordinate);
        } else {
            return human.getEnemyFieldBox(coordinate);
        }
    }

    public Box getOwnBox(Coordinate coordinate) {
        return human.getOwnFieldBox(coordinate);
    }

    public void pressLeftButtonOnComputersField(Coordinate coordinate) {
        checkWhoWin();
        if (status == GameStatus.PLAYED) {
            if (human.getEnemyFieldBox(coordinate) == Box.CLOSED) {
                leftButtonActionOnComputersField(coordinate);
            }
        }
    }

    private void leftButtonOnOwnFieldAction(Coordinate coordinate) {
        list.add(coordinate);
        if (isNumberOfDeckRight()) {
            Ship ship = new Ship(list);
            if (ship.isShipValid()) {
                human.placeShips(ship);
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
        if (human.getOwnFieldBox(coordinate) == Box.CELL) {
            changeStatus();
            if (counter < 10) {
                human.setShipBox(coordinate);
                leftButtonOnOwnFieldAction(coordinate);
            }
        } else {
            status = GameStatus.OCCUPIED;
        }
    }

    private void notValidShipAction() {
        for (Coordinate coordinate : list) {
            human.getOwnField().set(coordinate, Box.CELL);
        }
    }

    private void leftButtonActionOnComputersField(Coordinate coordinate) {
        human.attack(coordinate);
        if (human.isShotResult()) {
            if (isHumanWin()) {
                status = GameStatus.WINNER;
            }
        } else {
            status = GameStatus.COMPUTERSTURN;
            // computersAction();
        }
        resetShotResult();
    }


    private boolean isHumanWin() {
        return computer.getShips().size() == 0;
    }

    private boolean isComputerWin() {
        return human.getShips().size() == 0;
    }

    private void checkWhoWin() {
        if (isHumanWin()) {
            status = GameStatus.WINNER;
        }
        if (isComputerWin()) {
            status = GameStatus.LOOSED;
        }
    }

    private void resetShotResult() {
        human.setShotResult();
        computer.setShotResult();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        computersAction();
    }
}
