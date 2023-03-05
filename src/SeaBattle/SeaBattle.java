package SeaBattle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class SeaBattle extends JFrame implements ActionListener {
    Timer timer = new Timer(1, this);
    private final Game game;
    private static final JPanel mainPanel = new JPanel();
    private static JPanel computerPanel;

    private static JPanel playerPanel;
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
        timer.start();
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
            }
        });

        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON2) {
                    game.start();
                }
                label.setText(getMessage());
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
        return switch (game.getStatus()) {
            case HIT -> "Hit";
            case OCCUPIED -> "Cells are occupied!";
            case COMPUTERSTURN -> "Computer's turn";
            case NOTVALIDSHIP -> "Ship is not valid!";
            case PLACING4 -> "Place 4-deck ship";
            case PLACING3 -> "Place 3-deck ship";
            case PLACING2 -> "Place 2-deck ship";
            case PLACING1 -> "Place 1-deck ship";
            case PLAYED -> "Please, make the turn";
            case LOOSED -> "YOU LOSE!";
            case WINNER -> "Player win!";
        };
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
        String filename = "../img/" + name + ".png";
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(filename)));
        return icon.getImage();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainPanel.repaint();
        computerPanel.repaint();
        playerPanel.repaint();
    }
}
