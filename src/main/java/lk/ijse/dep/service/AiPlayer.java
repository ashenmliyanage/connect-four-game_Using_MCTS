package lk.ijse.dep.service;

import java.util.*;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class AiPlayer extends Player{
    public AiPlayer(Board board){
        super(board);
    }

//    @Override
//    public void movePiece(int col) {
//        boolean flag = false;
//        do {
//            col = (int)(Math.random()*(6-0)+0);
//        }while (!board.isLegalMove(col));
//
//        board.updateMove(col,Piece.GREEN);
//        board.getBoardUI().update(col,flag);
//
//        Winner winner =board.findWinner();
//
//        if (board.findWinner().getWinningPiece() == Piece.GREEN) {
//            board.getBoardUI().notifyWinner(winner);
//        } else{
//            if (!board.exitsLegalMove()){
//                board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
//            }
//        }

    //---------------------------------------------------Move----------------------------------------------------

    @Override
    public void movePiece(int col) {
        MCTS mcts=new MCTS(board.getBoardImpl());

        col=mcts.startMCTS();

        board.updateMove(col,Piece.GREEN);
        board.getBoardUI().update(col,false);
        Winner winner=board.findWinner();
        if (winner.getWinningPiece()!=Piece.EMPTY){
            board.getBoardUI().notifyWinner(winner);
        }
        else if (!board.exitsLegalMove()){
            board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
        }
    }










        /*
    boolean flag = true;
    Min_max(2,flag);

    board.updateMove(col,Piece.GREEN);
    board.getBoardUI().update(col,false);

    Winner winner = board.findWinner();

        if (board.findWinner().getWinningPiece() == Piece.GREEN) {
            board.getBoardUI().notifyWinner(winner);
        } else{
            if (!board.exitsLegalMove()){
                board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
            }
        }


    }
    int Min_max(int depth, boolean maxPlayer){
        Piece[][] piece = board.getPiece();

        if (depth == 0 || !board.exitsLegalMove()){
            board.updateMove(0,Piece.GREEN);
            board.getBoardUI().update(0,false);

            Winner winner =board.findWinner();

            if (board.findWinner().getWinningPiece() == Piece.GREEN){
                board.getBoardUI().notifyWinner(winner);
            } else if (!board.exitsLegalMove()) {
                board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
            }
        }
        if (maxPlayer){
                int max = -100000;
                int hu;

            for (int i = 0; i < piece.length; i++) {
                if (piece[i][board.findNextAvailableSpot(i)] == Piece.EMPTY){
                    hu = Min_max(depth - 1, false);
                    max = max(max,hu);
                }
            }
            return max;
        }
        else{
                int min = +100000;
                int hu;

            for (int i = 0; i < piece.length; i++) {
                if (piece[i][board.findNextAvailableSpot(i)] == Piece.EMPTY) {
                    hu = Min_max(depth - 1, true);
                    min = min(min, hu);
                }
            }
            return min;
        }*/

    }

//    static class Node{
//        // catch the bord;
//        public BoardImpl boardImpl;
//        public int visit;
//        public int score;
//
//        List<Node> child = new ArrayList<>();
//
//        Node parent = null;
//
//        public Node(BoardImpl board){
//            this.boardImpl = board;
//        }
//        Node getChildinMaxScore(){
//            Node result = child.get(0);
//            for (int i = 0; i < child.size(); i++) {
//                if (child.get(i).score > result.score) {
//                    result = child.get(i);
//                }
//            }
//            return result;
//        }
//        void addChlid(Node node){
//            child.add(node);
//        }
//
//        Random r = new Random();
//        private Node expandNodeandReturnRandom(Node node){
//            //check next current move
//            Node result = node;
//            BoardImpl board1 = node.boardImpl;
//            List<BoardImpl> legalMoves = board1.getAllLegalNextMoves();
//
//            for (int i = 0; i < legalMoves.size(); i++) {
//                BoardImpl move = legalMoves.get(i);
//                Node node1 = new Node(move);
//                node1.parent = node;
//                node.addChlid(node1);
//
//                result = node1;
//            }
//
//            int random = r.nextInt(node.child.size());
//
//            return node.child.get(random);
//        }
//
//         static Piece simulateLightPlayout(Node promisingNode) {
//            //make a new move and what the end
//
//            Node node=new Node(promisingNode.boardImpl);
//            node.parent=promisingNode.parent;
//
//            Winner winner=node.boardImpl.findWinner();
//
//            if (winner.getWinningPiece()==Piece.BLUE){
//                node.parent.score=Integer.MIN_VALUE;
//
//                return node.boardImpl.findWinner().getWinningPiece();
//            }
//
//
//            while (node.boardImpl.getStatus()){
//                BoardImpl nextMove=node.boardImpl.getRandomLeagalNextMove();
//                Node node1 = new Node(nextMove);
//                node1.parent=node;
//                node.addChlid(node1);
//                node=node1;
//
//                //   System.out.println(node.board.findWinner().getWinningPiece());
//
////            if (node.board== null) {
////                System.out.println("yy");
////                // Handle the null case, such as returning a default value or throwing an exception.
////                // For example, you can throw an IllegalArgumentException:
////                throw new IllegalArgumentException("promisingNode cannot be null");
////            }
//
//            }
//
//            return node.boardImpl.findWinner().getWinningPiece();
//        }


    class MCTS{
        private BoardImpl board;

        private Piece AiSId = Piece.GREEN;
        private Piece HumanSId = Piece.BLUE;

        public MCTS(BoardImpl board){
            this.board = board;
        }

        public int startMCTS(){
            //get bord and stand
            System.out.println("MCTS working.");

            int count = 0;

            Node node = new Node(board);

            while (count<1000){
                count++;

                System.out.println(count);
                //Select Node
                System.out.println("node ekata yanna yako");
                Node promisingNode = selectPromis(node);
                //Expand Node
                Node select = promisingNode;

                if (select.boardImpl.getStatus()){
                    select.expandNodeandReturnRandom(promisingNode);
                }

                //Simulate
                //Simulate
                Piece resultPiece= Node.simulateLightPlayout(select);

                //Propagate
                backPropagation(resultPiece,select);

                System.out.println(count+"end");


            }
            Node best= node.getChildinMaxScore();

            System.out.println("Best move scored " + best.score + " and was visited " + best.visit + " times");

            return best.boardImpl.col;
        }
        private void backPropagation(Piece resultPiece, Node selected) {
            //visit and count
            Node node=selected;

            while (node!=null){
                node.visit++;

                if (node.boardImpl.piece==resultPiece){
                    node.score++;
                }
                node = node.parent;

            }



        }

        private Node selectPromis(Node tree){

            System.out.println("selete ekata awaaa");
            //UCD > and get a next move
            Node node1 = tree;
            System.out.println("wile 12 go");
            int i = 0;
            while (node1.child.size() != 0){
                System.out.println("while  "+i);
                node1 = UCT.findBestNode(node1);
            }
            System.out.println("wile 12 end");
            return node1;
        }

        static class UCT{
            //calculate the best bord
            public static  double Value(int totl, double nWinScore, int nVisit){
                if (nVisit == 0){
                    return Integer.MAX_VALUE;
                }
                return ((double) nWinScore / (double) nVisit) + 1.41 * Math.sqrt(Math.log(totl) / (double) nVisit);
            }

            public static Node findBestNode(Node node){
                int parentVisit = node.visit;
                return Collections.max(node.child, Comparator.comparing(c -> Value(parentVisit,c.score,c.visit)));
            }
        }



        //-----------------------------------------------Node-------------------------------------------------------------------------

        static class Node{
            // catch the bord;

            public BoardImpl boardImpl;
            public int visit;
            public int score;

            List<Node> child = new ArrayList<>();

            Node parent = null;

            public Node(BoardImpl board){
                this.boardImpl = board;
            }
            Node getChildinMaxScore(){
                System.out.println("getChildinMaxScore()");
                Node result = child.get(0);
                for (int i = 0; i < child.size(); i++) {
                    if (child.get(i).score > result.score) {
                        result = child.get(i);
                    }
                }
                System.out.println("getChildinMaxScore() end" );
                return result;

            }
            void addChlid(Node node){
                child.add(node);
            }

            Random r = new Random();
            private Node expandNodeandReturnRandom(Node node){
                System.out.println("Node");
                //check next current move
                Node result = node;
                BoardImpl board1 = node.boardImpl;
                List<BoardImpl> legalMoves = board1.getAllLegalNextMoves();

                for (int i = 0; i < legalMoves.size(); i++) {
                    BoardImpl move = legalMoves.get(i);
                    Node node1 = new Node(move);
                    node1.parent = node;
                    node.addChlid(node1);

                    result = node1;
                }

                int random = r.nextInt(node.child.size());
                System.out.println("close node");
                return node.child.get(random);

            }

            static Piece simulateLightPlayout(Node promisingNode) {
                System.out.println("simulate");
                //make a new move and what the end

                Node node=new Node(promisingNode.boardImpl);
                node.parent=promisingNode.parent;

                Winner winner=node.boardImpl.findWinner();

                if (winner.getWinningPiece()==Piece.BLUE){
                    node.parent.score=Integer.MIN_VALUE;

                    return node.boardImpl.findWinner().getWinningPiece();
                }
                System.out.println("wile awaa");
                while (node.boardImpl.getStatus()){

                    BoardImpl nextMove=node.boardImpl.getRandomLeagalNextMove();
                    Node node1 = new Node(nextMove);
                    node1.parent=node;
                    node.addChlid(node1);
                    node=node1;

                    //   System.out.println(node.board.findWinner().getWinningPiece());

//            if (node.board== null) {
//                System.out.println("yy");
//                // Handle the null case, such as returning a default value or throwing an exception.
//                // For example, you can throw an IllegalArgumentException:
//                throw new IllegalArgumentException("promisingNode cannot be null");
//            }

                }
                System.out.println("while end");
                System.out.println("simu end");
                return node.boardImpl.findWinner().getWinningPiece();
            }




    }
}
