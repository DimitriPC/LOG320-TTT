import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final Move m1 = new Move(0, 0);
    private static final Move m2 = new Move(0, 1);
    private static final Move m3 = new Move(0, 2);
    private static final Move m4 = new Move(1, 0);
    private static final Move m5 = new Move(1, 1);
    private static final Move m6 = new Move(1, 2);
    private static final Move m7 = new Move(2, 0);
    private static final Move m8 = new Move(2, 1);
    private static final Move m9 = new Move(2, 2);



    public static void main(String[] args){
        Board board = new Board();
        CPUPlayer cpu = new CPUPlayer(Mark.X);

//        board.play(m1, Mark.O);
//
//        board.play(m2, Mark.O);
//        board.play(m3, Mark.O);
//        board.play(m4, Mark.O);
//        board.play(m5, Mark.O);
//        board.play(m6, Mark.O);
//        board.play(m7, Mark.O);
//        board.play(m8, Mark.O);
//        board.play(m9, Mark.O);
//
//        board.play(m1, Mark.X);
//        board.play(m2, Mark.X);
//        board.play(m3, Mark.X);
//        board.play(m4, Mark.X);
//        board.play(m5, Mark.X);
//        board.play(m6, Mark.X);
//        board.play(m7, Mark.X);
//        board.play(m8, Mark.X);
//        board.play(m9, Mark.X);
//
//
//        ArrayList<Move> moveArrayList = cpu.getNextMoveMinMax(board);
//        ArrayList<Move> moveArrayList = cpu.getNextMoveAB(board);
//        for (Move m : moveArrayList){
//            System.out.println(m);
//        }

//        Move m = cpu.monteCarlo(board);
//        System.out.print(m);

        playCPU(board);
//        System.out.print(cpu.getNumOfExploredNodes());
    }
    public static void playCPU(Board board){
        Mark mark;
        CPUPlayer cpu;

        Random rand = new Random();
        Scanner clavier = new Scanner(System.in);

        System.out.print("Entrez 1 pour jouer en premier ou 2 pour jouer en deuxième: ");
        String choixJoueur = clavier.nextLine();

        if (choixJoueur.equals("2")){
            mark = Mark.X;
            cpu = new CPUPlayer(mark);
//            ArrayList<Move> moveArrayList = cpu.getNextMoveAB(board);
//            int moveIndex = rand.nextInt(moveArrayList.size());
//            Move m = moveArrayList.get(moveIndex);
            Move m = cpu.monteCarlo(board);
            board.play(m, mark);
            System.out.println(m);
            printBoard(board);
        }else{
            mark = Mark.O;
            cpu = new CPUPlayer(mark);
        }

        while (true){
            System.out.println("Entrez le numero de la case: ");
            String input = clavier.nextLine();
            switch (input) {
                case "1":
                    board.play(m1, mark.enemy());
                    break;
                case "2":
                    board.play(m2, mark.enemy());
                    break;
                case "3":
                    board.play(m3, mark.enemy());
                    break;
                case "4":
                    board.play(m4, mark.enemy());
                    break;
                case "5":
                    board.play(m5, mark.enemy());
                    break;
                case "6":
                    board.play(m6, mark.enemy());
                    break;
                case "7":
                    board.play(m7, mark.enemy());
                    break;
                case "8":
                    board.play(m8, mark.enemy());
                    break;
                case "9":
                    board.play(m9, mark.enemy());
                    break;
                default:
                    System.out.println("Invalid selection. Please enter a number between 1 and 9.");
                    break;
            }

            if (board.evaluate(Mark.O) == 0 || board.evaluate(Mark.O) == 100 || board.evaluate(Mark.O) == -100){
                System.out.print("Fin de la partie");
                break;
            }
//            ArrayList<Move> moveArrayList = cpu.getNextMoveAB(board);
//            int moveIndex = rand.nextInt(moveArrayList.size());
//            Move m = moveArrayList.get(moveIndex);
            Move m = cpu.monteCarlo(board);
            board.play(m, mark);
            System.out.println(m);
            printBoard(board);
            if (board.evaluate(Mark.O) == 0 || board.evaluate(Mark.O) == 100 || board.evaluate(Mark.O) == -100){
                System.out.print("Fin de la partie");
                break;
            }
        }


    }

    public static void printBoard(Board board){
        for (int i = 0; i < Board.NBCOLONNES; i++){
            for (int j = 0; j < Board.NBLIGNES; j++){
                if (board.getBoard()[i][j] == Mark.EMPTY){
                    System.out.print("* ");
                } else if (board.getBoard()[i][j] == Mark.X) {
                    System.out.print("X ");
                }else{
                    System.out.print("O ");
                }
            }
            System.out.println();
        }
    }
}
