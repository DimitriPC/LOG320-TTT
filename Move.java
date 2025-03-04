

public class Move
{
    private int row;
    private int col;
    private int boardIndex;

    public Move(){
        row = -1;
        col = -1;
        boardIndex = -1;
    }

    public Move(int r, int c){
        row = r;
        col = c;
    }

    public Move(int r, int c, int boardIndex){
        row = r;
        col = c;
        this.boardIndex = boardIndex;
    }
    public Move(String move){

        int row = 9 - Character.getNumericValue(move.charAt(1));
        int col = move.charAt(0) - 'A';
        int index = (row / 3) * 3 + (col / 3);
        this.row = row;
        this.col = col;
        this.boardIndex = index;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public int getBoardIndex() {
        return boardIndex;
    }

    public void setRow(int r){
        row = r;
    }

    public void setCol(int c){
        col = c;
    }

    public void setBoardIndex(int boardIndex) {
        this.boardIndex = boardIndex;
    }

    @Override
    public String toString(){
        return boardIndex + ". (" + row + "," + col + ")";
    }

    public String sendMove(){
        int[] startingRow = {7, 7, 7, 4, 4, 4, 1, 1, 1};
        char[] startingCol = {'a', 'd', 'g', 'a', 'd', 'g', 'a', 'd', 'g'};

        int endRow = startingRow[boardIndex] + 2 - row;
        String endRowString = Integer.toString(endRow);
        int endCharInt = (int) startingCol[boardIndex] + col;
        char endChar = (char) endCharInt;
        String endCharString = Character.toString(endChar).toUpperCase();
        return endCharString + endRowString;
    }

}
