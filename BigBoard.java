import java.util.ArrayList;

public class BigBoard {
    //Disposition des SmallBoard
    /*
    0 | 1 | 2
    -----------
    3 | 4 | 5
    -----------
    6 | 7 | 8
     */

    private SmallBoard[][] boardArray;
    private int boardToPlayIndex = -1;

    public static final int NBLIGNES = 3;
    public static final int NBCOLONNES = 3;

    public BigBoard() {
        boardArray = new SmallBoard[NBLIGNES][NBCOLONNES];

        for (int i = 0; i < NBLIGNES; i++){
            for (int j = 0; j < NBCOLONNES; j++){
                SmallBoard smallBoard = new SmallBoard();
                boardArray[i][j] = smallBoard;
            }
        }
    }

    // Place la pièce 'nodeMark' sur le plateau, à la
    // position spécifiée dans Move
    public void play(Move m, Mark mark){


        int boardToPlayRow = m.getBoardIndex() / 3;
        int boardToPlayCol = m.getBoardIndex() % 3;



        boardArray[boardToPlayRow][boardToPlayCol].play(m, mark);

        int value = boardArray[boardToPlayRow][boardToPlayCol].evaluate(mark);
        if (value == 100){
            boardArray[boardToPlayRow][boardToPlayCol].setResult(mark);
        } else if (value == -100) {
            boardArray[boardToPlayRow][boardToPlayCol].setResult(mark.enemy());
        } else if (value == 0) {
            boardArray[boardToPlayRow][boardToPlayCol].setResult(Mark.EMPTY);
        }

        this.boardToPlayIndex = (m.getRow() * 3) + m.getCol();
        if (boardArray[m.getRow()][m.getCol()].getResult() != Mark.NULL){
            this.boardToPlayIndex = -1;
        }
    }


    // retourne  100 pour une victoire
    //          -100 pour une défaite
    //           0   pour un match nul
    // Ne pas changer la signature de cette méthode
    public int evaluate(Mark mark){
        //HORIZONTALE
        for (int i = 0; i < NBLIGNES; i++){
            if (boardArray[i][0].getResult() == boardArray[i][1].getResult() && boardArray[i][1].getResult() == boardArray[i][2].getResult()){
                if (boardArray[i][0].getResult() == mark){
                    return 100;
                }else if (boardArray[i][0].getResult() == mark.enemy()){
                    return -100;
                }
            }
        }

        //VERTICALE
        for (int i = 0; i < NBCOLONNES; i++){
            if (boardArray[0][i] == boardArray[1][i] && boardArray[1][i] == boardArray[2][i]){
                if (boardArray[0][i].getResult() == mark){
                    return 100;
                } else if (boardArray[0][i].getResult() == mark.enemy()) {
                    return -100;
                }
            }
        }

        //DIAGONALE
        if (boardArray[0][0].getResult() == mark && boardArray[1][1].getResult() == mark && boardArray[2][2].getResult() == mark){
            return 100;
        } else if (boardArray[0][0].getResult() == mark.enemy() && boardArray[1][1].getResult() == mark.enemy() && boardArray[2][2].getResult() == mark.enemy()) {
            return -100;
        }

        //ANTI-DIAGONALE
        if (boardArray[0][2].getResult() == mark && boardArray[1][1].getResult() == mark && boardArray[2][0].getResult() == mark){
            return 100;
        } else if (boardArray[0][2].getResult() == mark.enemy() && boardArray[1][1].getResult() == mark.enemy() && boardArray[2][0].getResult() == mark.enemy()) {
            return -100;
        }

        //missing moves pour un smallBoard
        if (missingMoves() == 0){
            return 0;           //match nul
        }else{
            return -200;        //match non fini et pas de victoire
        }

    }
    //retourne 1 s'il reste des cases disponibles 0 sinon
    public int missingMoves(){
        for (int i = 0; i < NBLIGNES; i++){
            for (int j = 0; j < NBCOLONNES; j++){
                if (boardArray[i][j].getResult() == Mark.NULL){
                    return 1;
                }
            }
        }
        return 0;
    }

    public ArrayList<Move> getPossibleMoves(){
        ArrayList<Move> moveArrayList = new ArrayList<>();

        if (boardToPlayIndex == -1){
            int index = 0;
            for (int i = 0; i < NBLIGNES; i++){
                for (int j = 0; j < NBCOLONNES; j++){
                    if (boardArray[i][j].getResult() == Mark.NULL){  //on ne prend que les board qui ne sont pas résolus
                        moveArrayList.addAll(boardArray[i][j].getPossibleMoves(index));
                    }
                    index++;
                }
            }
        }else{
            int boardToPlayRow = boardToPlayIndex / 3;
            int boardToPlayCol = boardToPlayIndex % 3;
            moveArrayList = boardArray[boardToPlayRow][boardToPlayCol].getPossibleMoves(boardToPlayIndex);  //ecq smallboard devrait connaitre son index ?
        }

        return moveArrayList;
    }

    public BigBoard cloneBoard(){
        BigBoard newboard = new BigBoard();
        newboard.boardToPlayIndex = this.boardToPlayIndex;

        for (int i = 0; i < NBLIGNES; i++){
            for (int j = 0; j < NBCOLONNES; j++){
                SmallBoard b = boardArray[i][j].cloneBoard();
                newboard.boardArray[i][j] = b;
            }
        }
        return newboard;
    }

    public void setBoardToPlayIndex(int boardToPlayIndex) {
        this.boardToPlayIndex = boardToPlayIndex;
    }

    public SmallBoard[][] getBoardArray() {
        return boardArray;
    }
}

