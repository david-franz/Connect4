import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game extends JFrame {

    private Board board;

    public static final int TOOLBAR_HEGIHT = 22;

    public Game() {
        board = new Board();

        setTitle("Connect 4");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.setBackground(Color.BLACK);

        setPreferredSize(new Dimension(482,504));
        setResizable(true);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setSize(new Dimension(getHeight()-TOOLBAR_HEGIHT, getHeight()));
                super.componentResized(e);
            }
        });

        board.setBounds(0,0,1000,1000);

        pack();
        setLocationRelativeTo(null); //makes game come in center
    }

    public void setup(Piece.Colour startingColour) {

        setLayout(null);

        add(board);

        board.addMouseMotionListener(new MouseAdapter(){

            @Override
            public void mouseMoved(MouseEvent e) {
                int col = (int) ((e.getX()) / board.getSquareSize());

                if (0 <= col && col < Board.NUM_COLS){
                    board.highlightColumn(col);
                    board.findMoveInCol(col);
                    board.repaint();
                }

            }
        });

        board.addMouseMotionListener(new MouseAdapter(){

            private int prevX = UNDEFINED;
            private int prevY = UNDEFINED;

            public static final int UNDEFINED = -1;

            @Override
            public void mouseDragged(MouseEvent e) {
                int col = (int) ((e.getX()) / board.getSquareSize());

                int x = e.getX(), y = e.getY();
                if(movedMouse(x, y)) {
                    System.out.println(true);
                    if (0 <= col && col < Board.NUM_COLS) {
                        board.findMoveInCol(col);
                        board.repaint();
                    }
                    prevX = x;
                    prevY = y;
                }
            }

            public boolean movedMouse(int x, int y) {
                if(prevX == UNDEFINED || prevY == UNDEFINED) return false;
                return x != prevX && y != prevY;
            }
        });

        board.addMouseListener((new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                board.doMove();
            }
        }));
    }

    public Board getBoard() {
        return board;
    }
}
