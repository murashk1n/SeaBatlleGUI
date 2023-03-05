package SeaBattle;

public class GameField {
    private final Box[][] gameField;

    public GameField(Box defaultBox) {
        gameField = new Box[Ranges.getSize().x][Ranges.getSize().y];
        for (Coordinate coordinate : Ranges.getAllCoordinates()) {
            gameField[coordinate.x][coordinate.y] = defaultBox;
        }
    }

    public Box get(Coordinate coordinate) {
        if (Ranges.inRange(coordinate)) {
            return gameField[coordinate.x][coordinate.y];
        }
        return null;
    }

    public void set(Coordinate coordinate, Box box) {
        if (Ranges.inRange(coordinate)) {
            gameField[coordinate.x][coordinate.y] = box;
        }
    }

    public void addAureole(Ship ship, Box box) {
        for (Coordinate coordinate : ship.getCoordinates()) {
            for (Coordinate around : Ranges.getCoordinatesAround(coordinate)) {
                    gameField[around.x][around.y] = box;
            }
        }
    }
}
