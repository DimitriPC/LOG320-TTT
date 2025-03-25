import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes
// de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait 
// être le cas)
public class SmallBoard
{
    private static final int VALUE_FOR_CENTER = 5;
    private static final int VALUE_FOR_CORNER = 2;
    private static final int VALUE_FOR_EDGES = 1;
    private Mark[][] board;

    private Mark result = Mark.NULL;


    public static final int NBLIGNES = 3;
    public static final int NBCOLONNES = 3;

    // Ne pas changer la signature de cette méthode
    public SmallBoard() {
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
                   result = mark;
                   return 100;
               }else if (board[i][0] != Mark.EMPTY){
                   result = mark.enemy();
                   return -100;
               }
           }
        }

        //VERTICALE
        for (int i = 0; i < NBCOLONNES; i++){
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]){
                if (board[0][i] == mark){
                    result = mark;
                    return 100;
                } else if (board[0][i] != Mark.EMPTY) {
                    result = mark.enemy();
                    return -100;
                }
            }
        }

        //DIAGONALE
        if (board[0][0] == mark && board[1][1] == mark && board[2][2] == mark){
            result = mark;
            return 100;
        } else if (board[0][0] == mark.enemy() && board[1][1] == mark.enemy() && board[2][2] == mark.enemy()) {
            result = mark.enemy();
            return -100;
        }

        //ANTI-DIAGONALE
        if (board[0][2] == mark && board[1][1] == mark && board[2][0] == mark){
            result = mark;
            return 100;
        } else if (board[0][2] == mark.enemy() && board[1][1] == mark.enemy() && board[2][0] == mark.enemy()) {
            result = mark.enemy();
            return -100;
        }

        if (missingMoves() == 0){
            result = Mark.EMPTY;
            return 0;           //match nul
        }else{
            result = Mark.NULL;
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

    public ArrayList<Move> getPossibleMoves(int boardIndex){
        ArrayList<Move> moveArrayList = new ArrayList<>();
        for (int i = 0; i < NBLIGNES; i++){
            for (int j = 0; j < NBCOLONNES; j++){
                if (board[i][j] == Mark.EMPTY){
                    Move m = new Move(i, j, boardIndex);
                    moveArrayList.add(m);
                }
            }
        }
        return moveArrayList;
    }

    public SmallBoard cloneBoard(){
        SmallBoard newboard = new SmallBoard();
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
        newboard.result = this.result;
        return newboard;
    }

    public Mark[][] getBoard() {
        return board;
    }

    public Mark getResult() {
        return result;
    }

    public void setResult(Mark result) {
        this.result = result;
    }

    public int giveHeuristicValue(Mark mark){
        int[] cornerRows = {0, 0, 2, 2};
        int[] cornerCols = {0, 2, 0, 2};

        int[] edgeRows = {0, 1, 1, 2};
        int[] edgeCols = {1, 0, 2, 1};

        int value = 0;
        if (board[1][1] == mark){
            value += VALUE_FOR_CENTER;
        } else if (board[1][1] == mark.enemy()) {
            value -= VALUE_FOR_CENTER;
        }
        for (int i = 0; i < cornerRows.length; i++){
            if (board[cornerRows[i]][cornerCols[i]] == mark){
                value += VALUE_FOR_CORNER;
            } else if (board[cornerRows[i]][cornerCols[i]] == mark.enemy()) {
                value -= VALUE_FOR_CORNER;
            }
        }

        for (int i = 0; i < edgeRows.length; i++){
            if (board[edgeRows[i]][edgeCols[i]] == mark){
                value += VALUE_FOR_EDGES;
            } else if (board[edgeRows[i]][edgeCols[i]] == mark.enemy()) {
                value -= VALUE_FOR_EDGES;
            }
        }

        return value;
    }
}
