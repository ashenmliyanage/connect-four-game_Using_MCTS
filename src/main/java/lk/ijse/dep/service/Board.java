package lk.ijse.dep.service;

public interface Board {
    int NUM_OF_ROWS = 5;
    int NUM_OF_COLS =6;

    BoardUI getBoardUI();
    public int findNextAvailableSpot(int col);
    public boolean isLegalMove(int col);
    public boolean exitsLegalMove();
    public void updateMove(int col, Piece move);
    public Winner findWinner();

    public void updateMove(int col, int row , Piece move);

    Piece[][] getPiece();

    BoardImpl getBoardImpl();
}
