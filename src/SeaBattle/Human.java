package SeaBattle;

public class Human extends Player{
    public Human() {
        name = "Andrei";
    }

    public void attack(Coordinate coordinate) {
        enemyField.set(coordinate, Box.OPENED);
        if (opponent.getOwnFieldBox(coordinate) == Box.SHIP) {
            successfulShot(coordinate);
        } else {
            enemyField.set(coordinate, Box.MISS);
            shotResult = false;
        }
    }
}

