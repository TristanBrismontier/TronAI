import java.util.*;


/**
 * Tron
 * Created by Tristan Brismontier on 23/08/2015.
 */
public class TronUniverse {
    final Graph graph = new Graph();


    public TronUniverse() {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 20; j++) {
                Node posA = graph.getOrAddNode(new Node(i,j));
                if(i<29){
                    Node posRight = graph.getOrAddNode(new Node(i+1,j));
                    graph.addEdge(posA,posRight);
                }
                if(j<19){
                    Node posDown = graph.getOrAddNode(new Node(i,j+1));
                    graph.addEdge(posA,posDown);
                }
            }
        }
    }

    public Graph getGraph() {
        return graph;
    }

    public void toRenameRefactoYop(){
        Scanner in = new Scanner(System.in);
        Map<Integer,List<Node>> history = new HashMap<>();
        boolean first = true;

        // game loop
        while (true) {
            graph.newTurn();
            int N = in.nextInt(); // total number of players (2 to 4).
            int P = in.nextInt(); // your player number (0 to 3).
            Node player = new Node(0,0);
            for (int i = 0; i < N; i++) {
                int X0 = in.nextInt(); // starting X coordinate of lightcycle (or -1)
                int Y0 = in.nextInt(); // starting Y coordinate of lightcycle (or -1)
                int X1 = in.nextInt(); // starting X coordinate of lightcycle (can be the same as X0 if you play before this player)
                int Y1 = in.nextInt(); // starting Y coordinate of lightcycle (can be the same as Y0 if you play before this player)
                if(X0 == -1){
                    if(history.get(i)!=null){
                        if(history.get(i).size()>2){
                            Node last = graph.getNodes().get(history.get(i).get(0));
                            last.setVisited(false);
                            for (int j = 1; j < history.size() ; j++) {
                                Node tmp = graph.getNodes().get(history.get(i).get(j));
                                tmp.setVisited(false);
                                graph.addEdge(last,tmp);
                                last = tmp;
                            }
                        }
                        history.remove(i);
                    }
                    continue;
                }
                boolean supr = true;
                Node node1 = new Node(X1,Y1);
                if(first)graph.addOpponent(new Node(X0,Y0));
                if(i!=P){
                    graph.addOpponent(node1);
                }else{
                    player = node1;
                    supr = false;
                }
                if (history.get(i) != null) {
                    graph.removeEdge(node1, history.get(i).stream().reduce((a, b) -> b).get(),supr);
                }
                if(history.get(i) == null) {
                    history.put(i, new ArrayList());
                }
                history.get(i).add(node1);
            }
            first = false;
            System.out.println(graph.getDirection(player,player )); // A single line with UP, DOWN, LEFT or RIGHT
        }
    }
}
