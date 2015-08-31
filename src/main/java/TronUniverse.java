import java.util.*;


/**
 * Tron
 * Created by Tristan Brismontier on 23/08/2015.
 */
public class TronUniverse {
    final Graph graph = new Graph();


    public TronUniverse() {
    }

    public Graph getGraph() {
        return graph;
    }

    public void toRenameRefactoYop(){
        Scanner in = new Scanner(System.in);
        final Graph graph = new Graph();
        Map<Integer, List<Node>> history = new HashMap<>();

        for (int i = 0; i < 4; i++) {
            history.put(i, new ArrayList<>());
        }

        // game loop
        while (true) {
            graph.newTurn();
            int N = in.nextInt(); // total number of players (2 to 4).
            int P = in.nextInt(); // your player number (0 to 3).
            Node player = new Node(0, 0);
            Node opponent = new Node(0, 0);
            for (int i = 0; i < N; i++) {
                int X0 = in.nextInt(); // starting X coordinate of lightcycle (or -1)
                int Y0 = in.nextInt(); // starting Y coordinate of lightcycle (or -1)
                int X1 = in.nextInt(); // starting X coordinate of lightcycle (can be the same as X0 if you play before this player)
                int Y1 = in.nextInt(); // starting Y coordinate of lightcycle (can be the same as Y0 if you play before this player)
                if (i != P) {
                    if (X0 == -1) {
                        if (history.get(i) != null) {
                            graph.restorePath(history.get(i));
                            history.remove(i);
                        }
                        continue;
                    }
                    Node antagonist = new Node(X1, Y1);
                    graph.addOpponent(antagonist);
                    history.get(i).add(antagonist);
                } else {
                    player = new Node(X1, Y1);
                    graph.addPlayer(player);
                }
            }
            System.out.println(graph.getDirection()); // A single line with UP, DOWN, LEFT or RIGHT
        }
    }
}
