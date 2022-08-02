package SeaBattle;

public class GameField {
    private Box[][] gamefield;

    public GameField(Box defaultBox) {
        gamefield = new Box[Ranges.getSize().x][Ranges.getSize().y];
        for (Coordinate coordinate : Ranges.getAllCoordinates()) {
            gamefield[coordinate.x][coordinate.y] = defaultBox;
        }
    }

    public Box get(Coordinate coordinate) {
        if (Ranges.inRange(coordinate)) {
            return gamefield[coordinate.x][coordinate.y];
        }
        return null;
    }

    public void set(Coordinate coordinate, Box box) {
        if (Ranges.inRange(coordinate)) {
            gamefield[coordinate.x][coordinate.y] = box;
        }
    }

    public void addAureole(Ship ship, Box box) {
        for (Coordinate coordinate : ship.getCoordinates()) {
            for (Coordinate around : Ranges.getCoordinatesAround(coordinate)) {
                    gamefield[around.x][around.y] = box;
            }
        }
    }

    public boolean isCellEmpty(Coordinate coordinate) {
        return !gamefield[coordinate.x][coordinate.y].equals(Box.SHIP) && !gamefield[coordinate.x][coordinate.y].equals(Box.AUREOLE);
    }
}
