public class Main {

    public static Game game;

    public static void main(String[] args) {
        game = new Game();
        game.setup(Piece.Colour.RED);
        game.setVisible(true);
    }
}
