import java.util.ArrayList;
import java.util.List;

public class Node {
    SmallBoard state;
    Node parent;
    List<Node> children;
    double visits;
    double winScore;
    Mark nodeMark;      //correspond au nodeMark de la personne devant jouer dans node

    private final double  C = 10;
    private final double WIN_VALUE = 1;
    private final double LOSS_VALUE = -2;
    private final double TIE_VALUE = 0;

    public Node(SmallBoard state, Node parent, Mark nodeMark) {
        this.state = state;
        this.parent = parent;
        this.nodeMark = nodeMark;
        this.children = new ArrayList<>();
        this.visits = 0;
        this.winScore = 0;
    }

    public Node selectChild() {
        //return Collections.max(children, Comparator.comparing(this::uctValue));
        Node childMaxUCT = null;
        double maxUCT = Integer.MIN_VALUE;
        for (Node child : children){
            if (child.uctValue() > maxUCT){
                childMaxUCT = child;
                maxUCT = child.uctValue();
            }
        }
        return childMaxUCT;
    }

    public double uctValue() {
        if (this.visits == 0) return Double.MAX_VALUE;

        return (this.winScore / this.visits) + (C * Math.sqrt(Math.log(this.parent.visits) / this.visits));
    }


    public Node expand() {
        List<Move> availableMoves = state.getPossibleMoves(2);   //a changer
        for (Move move : availableMoves) {
            SmallBoard newState = state.cloneBoard();
            newState.play(move, nodeMark);
            Node child = new Node(newState, this, nodeMark.enemy());
            children.add(child);
        }
        return children.getFirst();
    }

    public double simulate(Mark cpuMark) {
        SmallBoard simulationState = state.cloneBoard();
        int i = 0;
        while (simulationState.evaluate(nodeMark) == -200) {    //match non fini
            List<Move> moves = simulationState.getPossibleMoves(2); // a changer
            Move randomMove = moves.get((int) (Math.random() * moves.size()));
            if (i % 2 == 0){
                simulationState.play(randomMove, nodeMark);
            }else{
                simulationState.play(randomMove, nodeMark.enemy());
            }
            i++;
        }
        if (simulationState.evaluate(cpuMark) == 100) return WIN_VALUE;
        if (simulationState.evaluate(cpuMark) == -100) return LOSS_VALUE;
        return TIE_VALUE;
    }

    public void backpropagate(double result) {
        visits++;
        winScore += result;
        if (parent != null) parent.backpropagate(result);
    }
}
