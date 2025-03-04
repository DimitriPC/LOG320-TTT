import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// IMPORTANT: Il ne faut pas changer la signature des méthodes
// de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait 
// être le cas)
public class CPUPlayer
{
    private final Mark markCPU;
    private final int depthMax = 3;

    // Contient le nombre de noeuds visités (le nombre
    // d'appel à la fonction MinMax ou Alpha Beta)
    // Normalement, la variable devrait être incrémentée
    // au début de votre MinMax ou Alpha Beta.
    private int numExploredNodes;

    // Le constructeur reçoit en paramètre le
    // joueur MAX (X ou O)
    public CPUPlayer(Mark cpu){
        markCPU = cpu;
    }

    // Ne pas changer cette méthode
    public int  getNumOfExploredNodes(){
        return numExploredNodes;
    }

    

    // Retourne la liste des coups possibles.  Cette liste contient
    // plusieurs coups possibles si et seuleument si plusieurs coups
    // ont le même score.
    public ArrayList<Move> getNextMoveAB(BigBoard bboard){
        numExploredNodes = 0;
        ArrayList<Move> moveArrayList = new ArrayList<>();

        int bestValue = Integer.MIN_VALUE;
        for (Move m : bboard.getPossibleMoves()){
            BigBoard b = bboard.cloneBoard();
            b.play(m, markCPU);
            int value = alphaBeta(b, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);

            if (value > bestValue){
                moveArrayList.clear();
                moveArrayList.add(m);
                bestValue = value;
            } else if (value == bestValue) {
                moveArrayList.add(m);
            }

        }
        return moveArrayList;
    }

    public int alphaBeta(BigBoard board, int depth, boolean maximizing, int alpha, int beta){
        numExploredNodes++;
        int evaluation = board.evaluate(markCPU);
        if (depth == depthMax){
            return 0;           //changer pour fonction evaluation
        }
        if (evaluation == 0){
            return 0;
        } else if (evaluation == 100 || evaluation == -100) {
            return evaluation;
        }

        if (maximizing){
            int maxEvaluation = Integer.MIN_VALUE;
            for (int i = 0; i < board.getPossibleMoves().size(); i++){
                BigBoard b = board.cloneBoard();
                b.play(board.getPossibleMoves().get(i), markCPU);
                int value = alphaBeta(b, depth+1,false, alpha, beta);
                maxEvaluation = Math.max(maxEvaluation, value);
                alpha = Math.max(alpha, maxEvaluation);

                if (alpha >= beta) break;

            }
            return maxEvaluation;
        }else{
            int minEvaluation = Integer.MAX_VALUE;
            for (int i = 0; i < board.getPossibleMoves().size(); i++) {
                BigBoard b = board.cloneBoard();
                b.play(board.getPossibleMoves().get(i), markCPU.enemy());
                int value = alphaBeta(b, depth + 1, true, alpha, beta);
                minEvaluation = Math.min(minEvaluation, value);
                beta = Math.min(beta, minEvaluation);

                if (alpha >= beta) break;
            }
            return minEvaluation;
        }
    }


    public Move monteCarlo(SmallBoard board){
        Node root = new Node(board, null, markCPU);

        int ITERATIONS = 10000;
        for (int i = 0; i < ITERATIONS; i++) {
            Node node = root;

            // Selection
            while (node.state.evaluate(markCPU) == -200 && !node.children.isEmpty()) {  //tant qu'on n'atteint pas une feuille (match fini ou plus d'enfants)
                node = node.selectChild();
            }

            // Expansion
            if (node.state.evaluate(markCPU) == -200) {
                node = node.expand();
            }

            // Simulation
            double result = node.simulate(markCPU);

            // Backpropagation
            node.backpropagate(result);
        }

        Node bestChild = Collections.max(root.children, Comparator.comparing(c -> c.visits));   //
        SmallBoard bestState = bestChild.state;

//        printChildrenStats(root);

        // Find the move that leads to the best state
        Move move = null;
        for (int i = 0; i < SmallBoard.NBLIGNES; i++) {
            for (int j = 0; j < SmallBoard.NBCOLONNES; j++) {
                if (board.getBoard()[i][j] != bestState.getBoard()[i][j]) {
                    move = new Move(i, j);
                    return move;
                }
            }
        }

        return null;
    }

    public void printChildrenStats(Node root){
        for (Node child : root.children){
            System.out.println("UCT value " + child.uctValue() + " First parameter: " + (child.winScore / child.visits) + " Visit number: " + child.visits);
        }
    }

    public Mark getMarkCPU() {
        return markCPU;
    }
}
