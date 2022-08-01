package SeaBattle;

public class Test {
//     private JFrame frame;
//    private Game game;
//    private JPanel panel;
//    private JLabel label;
//    private final int IMAGE_SIZE = 50;
//
//    public static void main(String[] args) {
//        new SeaBattle();
//    }
//
//    private SeaBattle() {
//        game = new Game();
//        game.start();
//        setImage();
//        initPanel();
//        initFrame();
//        initLabel();
//    }
//
//    private void initLabel() {
//        label = new JLabel("Welcome");
//        panel.add(label, BorderLayout.SOUTH);
//    }
//
//    private String getMessage() {
//        switch (game.getStatus()) {
//            case HIT:
//                return "Hit";
//            case OCCUPIED:
//                return "Cells are occupied!";
//            case COMPUTERSTURN:
//                return "Computer's turn";
//            case NOTVALIDSHIP:
//                return "Ship is not valid!";
//            case PLACING4:
//                return "Place 4-deck ship";
//            case PLACING3:
//                return "Place 3-deck ship";
//            case PLACING2:
//                return "Place 2-deck ship";
//            case PLACING1:
//                return "Place 1-deck ship";
//            case PLAYED:
//                return "Please, make the turn";
//            case LOOSED:
//                return "YOU LOSE!";
//            case WINNER:
//                return "Player win!";
//            default:
//                return "Welcome!";
//        }
//    }
//
//    private void initPanel() {
//        Container mainContainer = frame.getContentPane();
//        mainContainer.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//
//
//        JPanel playersFild = new JPanel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                for (Coordinate coordinate : Ranges.getAllCoordinates()) {
//                    g.drawImage((Image) game.getBox(coordinate).image,
//                            coordinate.x * IMAGE_SIZE, coordinate.y * IMAGE_SIZE, this);
//                }
//            }
//        };
//        JPanel computersFild = new JPanel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                for (Coordinate coordinate : Ranges.getAllCoordinates()) {
//                    g.drawImage((Image) game.getBox(coordinate).image,
//                            coordinate.x * IMAGE_SIZE, coordinate.y * IMAGE_SIZE, this);
//                }
//            }
//        };
//
//        panel = new JPanel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                for (Coordinate coordinate : Ranges.getAllCoordinates()) {
//                    g.drawImage((Image) game.getBox(coordinate).image,
//                            coordinate.x * IMAGE_SIZE,
//                            coordinate.y * IMAGE_SIZE, this);
//                }
//            }
//        };
//        computersFild.setPreferredSize(new Dimension(
//                Ranges.getSize().x * IMAGE_SIZE,
//                Ranges.getSize().y * IMAGE_SIZE));
//        playersFild.setPreferredSize(new Dimension(
//                Ranges.getSize().x * IMAGE_SIZE,
//                Ranges.getSize().y * IMAGE_SIZE));
//        //add(mainContainer);
//    }
//    private void initFrame() {
//        frame = new JFrame();
//        frame.setSize(1000, 1000);
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setTitle("Sea Battle");
//        frame.setResizable(false);
//        frame.setVisible(true);
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setIconImage(getImage("logo"));
//    }
//
//    private void setImage() {
//        for (Box box : Box.values()) {
//            box.image = getImage(box.name().toLowerCase());
//        }
//    }
//
//    private Image getImage(String name) {
//        String filename = "img/" + name + ".png";
//        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
//        return icon.getImage();
//    }
}
