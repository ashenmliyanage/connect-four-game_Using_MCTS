package lk.ijse.dep.service;

import lk.ijse.dep.controller.BoardController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoardImpl implements Board{
    private Piece[][] pieces;
    private BoardUI boardUI;

    private Piece player;
    public int col;


    public BoardImpl(BoardUI boardUI) {

        pieces = new Piece[NUM_OF_COLS][NUM_OF_ROWS];


        //Child class object is assign to parent class variable.
        this.boardUI = boardUI;

        //Initialize all pieces in array as EMPTY.
        for (int i = 0; i < NUM_OF_COLS; i++) {
            for (int j = 0; j < NUM_OF_ROWS; j++) {
                pieces[i][j] = Piece.EMPTY;
            }
        }
    }

    public BoardImpl(Piece[][] pieces, BoardUI boardUI) {
        this.pieces=new Piece[6][5];
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                this.pieces[i][j]=pieces[i][j];
            }
        }
        this.boardUI = boardUI;
    }

    @Override
    public BoardUI getBoardUI() {
        return boardUI;
    }

    @Override
    public int findNextAvailableSpot(int col) {
        //Check if there is any spot as EMPTY in provide column.
        for (int i = 0; i < NUM_OF_ROWS; i++) {
            if (pieces[col][i] == Piece.EMPTY) {
                return i; //Return row number if there is any.
            }
        }
        return -1; //Return -1 if there is non.
    }

    @Override
    public boolean isLegalMove(int col) {
        int rowNo = findNextAvailableSpot(col);
        //Check if the returned num is -1 or not.
        if (rowNo > -1) {
            return true; //Return true if rowNo is not -1.
        }
        return false; //Return false if rowNo is -1.
    }

    @Override
    public boolean exitsLegalMoves() {
        //Check whole board to find there is any EMPTY spots.
        for (int i = 0; i < NUM_OF_COLS; i++) {
            for (int j = 0; j < NUM_OF_ROWS; j++) {
                if (pieces[i][j] == Piece.EMPTY) {
                    return true; //Return true if there is.
                }
            }
        }
        return false; //Return false if there is not.
    }

    public Piece getPlayer() {
        return player;
    }

    @Override
    public void updateMove(int col, Piece move) {

        this.col = col;
        this.player = move;

        //Find the first EMPTY spot in provide colum and change the value from EMPTY to value of move.
        for (int i = 0; i < 5; i++) {
            if (pieces[col][i] == Piece.EMPTY) {
                pieces[col][i] = move;
                break; //Break the for loop after find and initialize the first EMPTY spot.
            }
        }

    }

//    @Override
//    public Winner findWinner() {
//        Piece win = Piece.EMPTY;
//        int col1 = 0;
//        int row1 = 0;
//        int col2 = 0;
//        int row2 = 0;
//
//        for (int i = 0; i < pieces.length; i++) {
//            if (findNextAvailableSpot(i) == 4 || findNextAvailableSpot(i) == -1) {
//                if (pieces[i][0] == pieces[i][1]) {
//                    if (pieces[i][1] == pieces[i][2]) {
//                        if (pieces[i][2] == pieces[i][3]) {
//                            win = pieces[i][0];
//                            col1 = i;
//                            col2 = i;
//                            row1 = 0;
//                            row2 = 3;
//                        }
//                    }
//                } else if (pieces[i][1] == pieces[i][2]){
//                    if (pieces[i][2] == pieces[i][3]){
//                        if (pieces[i][3] == pieces[i][4]){
//                            win = pieces[i][1];
//                            col1 = i;
//                            col2 = i;
//                            row1 = 1;
//                            row2 = 4;
//                        }
//                    }
//                }
//            }
//        }
//
//        for (int i = 0; i < pieces[i].length; i++) {
//            if (findNextAvailableSpot(i) == 4 || findNextAvailableSpot(i) == 5 || findNextAvailableSpot(i) == -1) {
//                if (pieces[0][i] == pieces[1][i]){
//                    if (pieces[1][i] == pieces[2][i]) {
//                        if (pieces[2][i] == pieces[3][i]) {
//                            win = pieces[0][i];
//                            col1 = 0;
//                            col2 = 3;
//                            row1 = i;
//                            row2 = i;
//                        }
//                    }
//                } else if (pieces[1][i] == pieces[2][i]) {
//                    if (pieces[2][i] == pieces[3][i]) {
//                        if (pieces[3][i] == pieces[4][i]) {
//                            win =pieces[1][i];
//                            col1 = 1;
//                            col2 = 4;
//                            row1 = i;
//                            row2 = i;
//                        }
//                    }
//                } else if (pieces[2][i] == pieces[3][i]) {
//                    if (pieces[3][i] == pieces[4][i]) {
//                        if (pieces[4][i] == pieces[5][i]) {
//                            win =pieces[2][i];
//                            col1=2;
//                            col2=5;
//                            row1=i;
//                            row2=i;
//                        }
//                    }
//                }
//            }
//        }
//        Winner winner;
//        if (win == Piece.EMPTY) {
//            winner = new Winner(win);
//        }
//        else {
//            winner = new Winner(win,col1,row1,col2,row2);
//        }
//        return winner;
//    }



    @Override
    public Winner findWinner() {
        //Check if there is any winner.
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[0].length; j++) {
                Piece currentPiece = pieces[i][j]; //Take the first piece to check.

                //Ensure that currentPiece is not EMPTY.
                if (currentPiece != Piece.EMPTY) {
                    //Vertical check.
                    if(j + 3 < pieces[0].length){
                        if (currentPiece == pieces[i][j + 1]){
                            if (currentPiece == pieces[i][j + 2]){
                                if(currentPiece == pieces[i][j + 3]){
                                    return new Winner(currentPiece, i, j, i, (j+3));
                                }
                            }
                        }
                    }

                    //Horizontal check.
                    if(i + 3 < pieces.length){
                        if (currentPiece == pieces[i + 1][j]){
                            if (currentPiece == pieces[i + 2][j]){
                                if(currentPiece == pieces[i + 3][j]){
                                    return new Winner(currentPiece, i, j, (i + 3), j);
                                }
                            }
                        }
                    }
                }
            }
        }
        //If there is no winner.
        return new Winner(Piece.EMPTY);
    }



    @Override
    public void updateMove(int col, int row, Piece move) {
        pieces[col][row] = move;
    }

    @Override
    public BoardImpl getBoardImpl() {
        return this;
    }
    public Piece[][] getPieces() {
        return pieces;
    }
    public boolean getStatus(){
        if (!exitsLegalMoves()){
            return false;
        }

        Winner winner=findWinner();
        if (winner.getWinningPiece() != Piece.EMPTY){

            return false;
        }
        return true;
    }
    public BoardImpl getRandomLeagalNextMove() {
        final List<BoardImpl> legalMoves = getAllLegalNextMoves();

        if (legalMoves.isEmpty()) {
            return null;
        }

        final int random= new Random().nextInt(legalMoves.size());
        return legalMoves.get(random);

    }

    public List<BoardImpl> getAllLegalNextMoves() {

        Piece nextPiece = player == Piece.BLUE?Piece.GREEN:Piece.BLUE;

        List<BoardImpl> nextMoves = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            int raw=findNextAvailableSpot(i);
            if (raw!=-1){
                BoardImpl legalMove=new BoardImpl(this.pieces,this.boardUI);
                legalMove.updateMove(i,nextPiece);
                nextMoves.add(legalMove);
            }
        }
        return  nextMoves;
    }
}
