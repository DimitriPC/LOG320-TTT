import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes
// de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait 
// être le cas)
public class Board
{
    private Mark[][] board;

    public static final int NBLIGNES = 3;
    public static final int NBCOLONNES = 3;

    // Ne pas changer la signature de cette méthode
    public Board() {
        board = new Mark[3][3];
        for (int i = 0; i < NBLIGNES; i++){
            for (int j = 0; j < NBCOLONNES; j++){
                Move m = new Move(i, j);
                play(m, Mark.EMPTY);
            }
        }
    }

    // Place la pièce 'nodeMark' sur le plateau, à la
    // position spécifiée dans Move
    //
    // Ne pas changer la signature de cette méthode
    public void play(Move m, Mark mark){
        board[m.getRow()][m.getCol()] = mark;
    }


    // retourne  100 pour une victoire
    //          -100 pour une défaite
    //           0   pour un match nul
    // Ne pas changer la signature de cette méthode
    public int evaluate(Mark mark){
        //HORIZONTALE
        for (int i = 0; i < NBLIGNES; i++){
           if (board[i][0] == board[i][1] && board[i][1] == board[i][2]){
               if (board[i][0] == mark){
                   return 100;
               }else if (board[i][0] != Mark.EMPTY){
                   return -100;
               }
           }
        }

        //VERTICALE
        for (int i = 0; i < NBCOLONNES; i++){
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]){
                if (board[0][i] == mark){
                    return 100;
                } else if (board[0][i] != Mark.EMPTY) {
                    return -100;
                }
            }
        }

        //DIAGONALE
        if (board[0][0] == mark && board[1][1] == mark && board[2][2] == mark){
            return 100;
        } else if (board[0][0] == mark.enemy() && board[1][1] == mark.enemy() && board[2][2] == mark.enemy()) {
            return -100;
        }

        //ANTI-DIAGONALE
        if (board[0][2] == mark && board[1][1] == mark && board[2][0] == mark){
            return 100;
        } else if (board[0][2] == mark.enemy() && board[1][1] == mark.enemy() && board[2][0] == mark.enemy()) {
            return -100;
        }

        if (missingMoves() == 0){
            return 0;           //match nul
        }else{
            return -200;        //match non fini et pas de victoire
        }

    }
    public int missingMoves(){
        for (int i = 0;i < NBLIGNES; i++){
            for (int j = 0; j < NBCOLONNES; j++){
                if (board[i][j] == Mark.EMPTY){
                    return 1;
                }
            }
        }
        return 0;
    }

    public ArrayList<Move> getPossibleMoves(){
        ArrayList<Move> moveArrayList = new ArrayList<>();
        for (int i = 0; i < NBLIGNES; i++){
            for (int j = 0; j < NBCOLONNES; j++){
                if (board[i][j] == Mark.EMPTY){
                    Move m = new Move(i, j);
                    moveArrayList.add(m);
                }
            }
        }
        return moveArrayList;
    }

    public Board cloneBoard(){
        Board newboard = new Board();
        for (int i = 0; i < NBLIGNES; i++){
            for (int j = 0; j < NBCOLONNES; j++){
                if (board[i][j] == Mark.O){
                    Move m = new Move(i, j);
                    newboard.play(m, Mark.O);
                } else if (board[i][j] == Mark.X) {
                    Move m = new Move(i, j);
                    newboard.play(m, Mark.X);
                } else if (board[i][j] == Mark.EMPTY) {
                    Move m = new Move(i, j);
                    newboard.play(m, Mark.EMPTY);
                }
            }
        }
        return newboard;
    }

    public Mark[][] getBoard() {
        return board;
    }
}
