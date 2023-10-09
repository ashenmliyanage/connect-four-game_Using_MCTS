package lk.ijse.dep.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class  BoardImpl implements Board{
    private Piece[][] pieces;
    private BoardUI boardUI;

    public int col;

    public BoardImpl(BoardUI boardUI) {
        this.boardUI = boardUI;
        pieces = new Piece[NUM_OF_COLS][NUM_OF_ROWS];
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0 ; j < pieces[i].length; j++) {
                pieces[i][j] = Piece.EMPTY;
            }
        }
    }

    @Override
    public BoardUI getBoardUI() {
        return boardUI;
    }

    @Override
    public int findNextAvailableSpot(int col) {
        for (int j = 0; j < NUM_OF_ROWS; j++) {
            if (pieces[col][j] == Piece.EMPTY) {
                return j;
            }
        }
        return -1;
    }
    @Override
    public boolean isLegalMove(int col) {
        boolean flag = false;
        int getLegal = findNextAvailableSpot(col);
        if (getLegal != -1){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean exitsLegalMove() {
        boolean flag = false;
        for (int i = 0; i < NUM_OF_COLS; i++) {
            for (int j = 0; j < NUM_OF_ROWS; j++) {
                if (pieces[i][j] == Piece.EMPTY) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    @Override
    public void updateMove(int col, Piece move) {
        int legalRow = findNextAvailableSpot(col);
        pieces[col][legalRow] = move;
    }

    @Override
    public Winner findWinner() {
        Piece win = Piece.EMPTY;
        int col1 = 0;
        int row1 = 0;
        int col2 = 0;
        int row2 = 0;

        for (int i = 0; i < pieces.length; i++) {
            if (findNextAvailableSpot(i) == 4 || findNextAvailableSpot(i) == -1) {
                if (pieces[i][0] == pieces[i][1]) {
                    if (pieces[i][1] == pieces[i][2]) {
                        if (pieces[i][2] == pieces[i][3]) {
                            win = pieces[i][0];
                            col1 = i;
                            col2 = i;
                            row1 = 0;
                            row2 = 3;
                        }
                    }
                } else if (pieces[i][1] == pieces[i][2]){
                    if (pieces[i][2] == pieces[i][3]){
                        if (pieces[i][3] == pieces[i][4]){
                            win = pieces[i][1];
                            col1 = i;
                            col2 = i;
                            row1 = 1;
                            row2 = 4;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < pieces[i].length; i++) {
            if (findNextAvailableSpot(i) == 4 || findNextAvailableSpot(i) == 5 || findNextAvailableSpot(i) == -1) {
                if (pieces[0][i] == pieces[1][i]){
                    if (pieces[1][i] == pieces[2][i]) {
                        if (pieces[2][i] == pieces[3][i]) {
                            win = pieces[0][i];
                            col1 = 0;
                            col2 = 3;
                            row1 = i;
                            row2 = i;
                        }
                    }
                } else if (pieces[1][i] == pieces[2][i]) {
                    if (pieces[2][i] == pieces[3][i]) {
                        if (pieces[3][i] == pieces[4][i]) {
                                win =pieces[1][i];
                                col1 = 1;
                                col2 = 4;
                                row1 = i;
                                row2 = i;
                        }
                    }
                } else if (pieces[2][i] == pieces[3][i]) {
                    if (pieces[3][i] == pieces[4][i]) {
                        if (pieces[4][i] == pieces[5][i]) {
                                win =pieces[2][i];
                                col1=2;
                                col2=5;
                                row1=i;
                                row2=i;
                        }
                    }
                }
            }
        }
        Winner winner;
        if (win == Piece.EMPTY) {
            winner = new Winner(win);
        }
        else {
            winner = new Winner(win,col1,row1,col2,row2);
        }
        return winner;
    }

    @Override
    public void updateMove(int col, int row, Piece move) {

    }

    @Override
    public Piece[][] getPiece() {
        return new Piece[0][];
    }

    public boolean getStatus(){
        if (!exitsLegalMove()) {
            return false;
        }
        Winner winner = findWinner();
        if (winner.getWinningPiece() != Piece.EMPTY) {
            return false;
        }
        return true;
    }
    public Piece piece = Piece.BLUE;
    public List<BoardImpl> getAllLegalNextMoves(){
        Piece nextPiece = Piece.BLUE;
        if (piece == Piece.BLUE){
            nextPiece = Piece.GREEN;
        }

        List<BoardImpl> nextMoves = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            int raw = findNextAvailableSpot(i);
            if (raw != -1){
                BoardImpl board = new BoardImpl(this.pieces,this.boardUI);
                board.updateMove(i,nextPiece);
                nextMoves.add(board);
            }
        }
        return nextMoves;
    }

    public BoardImpl(Piece[][] pieces, BoardUI boardUI) {
        this.pieces = pieces;
        this.boardUI = boardUI;
    }

    Random random = new Random();

    public BoardImpl getRandomLeagalNextMove() {
        final List<BoardImpl> legalMoves=getAllLegalNextMoves();

        if (legalMoves.isEmpty()) {
            return null;
        }

        final int random= this.random.nextInt(legalMoves.size());
        return legalMoves.get(random);

    }
    @Override
    public BoardImpl getBoardImpl() {
        return this;
    }
}
