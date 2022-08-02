import SeaBattle.Box;
import SeaBattle.Coordinate;
import SeaBattle.Game;
import SeaBattle.Ranges;
import SeaBattle.GameStatus;
import SeaBattle.Computer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SeaBattle extends JFrame {
    private Game game;
    private JPanel mainPanel = new JPanel();
    private JPanel computerPanel;
    private JPanel playerPanel;
    private JLabel label;
    private final int IMAGE_SIZE = 50;

    public static void main(String[] args) {
        new SeaBattle();
    }

    private SeaBattle() {
        game = new Game();
        game.start();
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java Sea Battle");
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setIconImage(getImage("logo"));
    }

    private void initPanel() {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        computerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coordinate coordinate : Ranges.getAllCoordinates()) {
                    g.drawImage((Image) game.getComputerBox(coordinate).image,
                            coordinate.x * IMAGE_SIZE,
                            coordinate.y * IMAGE_SIZE, this);
                }
            }
        };
        playerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coordinate coordinate : Ranges.getAllCoordinates()) {
                    g.drawImage((Image) game.getOwnBox(coordinate).image,
                            coordinate.x * IMAGE_SIZE,
                            coordinate.y * IMAGE_SIZE, this);
                }
            }
        };

        playerPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coordinate coordinate = new Coordinate(x, y);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    game.pressLeftButtonOnOwnField(coordinate);
                }
                label.setText(getMessage());
                mainPanel.repaint();
            }
        });

        computerPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coordinate coordinate = new Coordinate(x, y);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    game.pressLeftButtonOnComputersField(coordinate);
                }
                label.setText(getMessage());
                mainPanel.repaint();
            }
        });

        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON2) {
                    game.start();
                }
                label.setText(getMessage());
                mainPanel.repaint();
            }
        });
        computerPanel.setPreferredSize(setDimension());
        playerPanel.setPreferredSize(setDimension());

        mainPanel.add(playerPanel);
        mainPanel.add(new JPanel());
        mainPanel.add(computerPanel);

        add(mainPanel);
    }

    private Dimension setDimension() {
        return new Dimension(
                (Ranges.getSize().x) * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE);
    }

    private String getMessage() {
        switch (game.getStatus()) {
            case HIT:
                return "Hit";
            case OCCUPIED:
                return "Cells are occupied!";
            case COMPUTERSTURN:
                return "Computer's turn";
            case NOTVALIDSHIP:
                return "Ship is not valid!";
            case PLACING4:
                return "Place 4-deck ship";
            case PLACING3:
                return "Place 3-deck ship";
            case PLACING2:
                return "Place 2-deck ship";
            case PLACING1:
                return "Place 1-deck ship";
            case PLAYED:
                return "Please, make the turn";
            case LOOSED:
                return "YOU LOSE!";
            case WINNER:
                return "Player win!";
            default:
                return "Welcome!";
        }
    }

    private void initLabel() {
        label = new JLabel("Welcome!");
        add(label, BorderLayout.SOUTH);
    }

    private void setImages() {
        for (Box box : Box.values()) {
            box.image = getImage(box.name().toLowerCase());
        }
    }

    private Image getImage(String name) {
        String filename = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }
}
