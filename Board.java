import javax.swing.*;
import java.awt.*;
import java.util.Optional;
import java.util.Random;

public class Board extends JPanel {

    private Piece[][] board = new Piece[NUM_ROWS][NUM_COLS];

    public boolean[] highlightedCol = new boolean[NUM_COLS];

    private boolean player1Move = true;

    private static final Move possibleMove = new Move();

    private int squareSize = 30;

    public static final int NUM_ROWS = 6;
    public static final int NUM_COLS = 7;

    public static final Color BOARD_COLOUR = new Color(0x2500A1);
    public static final Color PIECE_RED = new Color(0xea2e2e);
    public static final Color PIECE_YELLOW = new Color(0xFFF400);
    public static final Color HIGHLIGHT_COLOUR = new Color(100, 200, 255, 50);

    public static int UNDEFINED = -1;

    public Board() {}

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g.create();

        squareSize = Main.game.getContentPane().getSize().height / NUM_COLS;
        int circleOffset = squareSize / 5;

        Random random = new Random();

        // Draw board
        for(int row = 0; row < NUM_ROWS; row++) {
            for(int col = 0; col < NUM_COLS; col++) {
                g2d.setColor(BOARD_COLOUR);
                g2d.fillRect(col * squareSize, row * squareSize + squareSize, squareSize, squareSize);

                if(board[row][col] == null) g2d.setColor(Color.BLACK);
                else g2d.setColor(board[row][col].getPieceColour() == Piece.Colour.RED? PIECE_RED : PIECE_YELLOW);

                g2d.fillOval(col * squareSize + (circleOffset/2), row * squareSize + squareSize + (circleOffset/2),
                        squareSize-circleOffset, squareSize-circleOffset);
            }
        }
        // Highlight column
        g2d.setColor(HIGHLIGHT_COLOUR);
        Optional<Integer> col = getHighlightedCol();
        if(!col.isEmpty()) g2d.fillRect(col.get() * squareSize, 0, squareSize, squareSize * NUM_COLS);

        // Show possible move
        if(possibleMove.getRow() != UNDEFINED && possibleMove.getCol() != UNDEFINED) {
            g2d.setColor(player1Move? PIECE_RED : PIECE_YELLOW); //change this so players get to choose?
            g2d.fillOval(possibleMove.getCol() * squareSize + (circleOffset),
                         possibleMove.getRow() * squareSize + squareSize + (circleOffset),
                    squareSize - 2 * circleOffset, squareSize - 2 * circleOffset);
        }
    }

    public void highlightColumn(int col) {
        for(int i = 0; i < NUM_COLS; i++) highlightedCol[i] = false;
        highlightedCol[col] = true;
    }

    public Optional<Integer> getHighlightedCol() {
        for(int i = 0; i < NUM_COLS; i++) { if(highlightedCol[i]) return Optional.of(i); }
        return Optional.empty();
    }

    public void findMoveInCol(int col) {
        boolean found;
        for(int row = 0; row < NUM_ROWS; row++){

            if(board[row][col] == null){
                if(row + 1 == NUM_ROWS || (row + 1 < NUM_ROWS && board[row+1][col]!= null)) {
                    possibleMove.setRow(row);
                    possibleMove.setCol(col);
                    return;
                }
            }
        }

        possibleMove.setRow(Move.UNDEFINED);
        possibleMove.setCol(Move.UNDEFINED);
    }

    public void doMove() {
        int row = possibleMove.getRow();
        int col = possibleMove.getCol();
        if(row == UNDEFINED || col == UNDEFINED) return;
        board[row][col] = new Piece(player1Move? Piece.Colour.RED : Piece.Colour.YELLOW);
        changePlayerMove();
    }

    public void changePlayerMove() {
        player1Move = !player1Move;
    }

    public int getSquareSize() {
        return squareSize;
    }

}
