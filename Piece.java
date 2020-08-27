import java.awt.*;

public class Piece {

    enum Colour{RED, YELLOW}

    private Colour pieceColour;

    public Piece(Colour pieceColour) {
        this.pieceColour = pieceColour;
    }

    public Colour getPieceColour() {
        return pieceColour;
    }
}
