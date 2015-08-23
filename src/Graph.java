import java.util.*;
import java.util.stream.Collectors;

/**
 * Tron
 * Created by Tristan Brismontier on 23/08/2015.
 */
public class Graph {
    private final Map<Node,Node> nodes = new HashMap<>();
    private final Set<Node> opponents = new HashSet<>();

    public void addEdge(Node nodeA,Node nodeB){
        final Edge edge = new Edge(nodeA, nodeB);
        nodeA.getEdges().add(edge);
        nodeB.getEdges().add(edge);
    }

    public void newTurn(){
        opponents.clear();
    }

    public void addOpponent(final Node opponent){
        Node oppo = nodes.get(opponent);
        if(oppo!=null){
            oppo.setVisited(true);
        }
        opponents.add(nodes.get(opponent));
    }

    public void removeEdge(final Node vec1,final Node vec2, boolean supr){
        final Node a = nodes.get(vec1);
        final Node b = nodes.get(vec2);
        final Edge edge = new Edge(b,a);
        try {
            removeEdge(edge);
            // if(supr){
            //     nodes.remove(vec1);
            // }
            // nodes.remove(vec2);

        } catch (NullPointerException e){
            System.err.println("null");
        }
    }
    private void removeEdge(final Edge toRemove){
        nodes.get(toRemove.getNodeA()).getEdges().remove(toRemove);
        nodes.get(toRemove.getNodeB()).getEdges().remove(toRemove);
    }

    public ArrayList<Node> getNeighbours(final Node node){
        return (ArrayList<Node>) node.getEdges().stream().map(edge -> edge.getOther(node)).collect(Collectors.toList());
    }

    public Node getOrAddNode(Node node) {
        if (nodes.get(node)==null){
            nodes.put(node,node);
        }
        return nodes.get(node);
    }

    public Node getDirection(Node player) {
        System.err.println("Player : " + player);
        Node graphPlayer = nodes.get(player);

        ArrayList<Node> neighbours = (ArrayList<Node>)getNeighbours(graphPlayer).stream().filter(node -> !node.isVisited()).collect(Collectors.toList());

        int nextEdge = 0;
        Map<Node,Integer> possible = new HashMap<>();
        int index = 0;
        for (Node other : neighbours){
            index++;
            System.err.println("Other : " + other);
            if(other.isVisited()){
                continue;
            }
            if(possible.isEmpty() || nextEdge <= other.getEdges().size()) {
                nextEdge = other.getEdges().size();
                int x = other.getX() - player.getX();
                int y = other.getY() - player.getY();
                Node nes = nodes.get(new Node(other.getX() +x,other.getY()+y));

                if(nes == null || nes.isVisited()){
                    System.err.println("nes : " + nes + " V=> 1");
                    possible.put(other, 10);
                }else{
                    System.err.println("nes : " + nes + " V=> " + (nes.getEdges().size()+index));
                    possible.put(other,nes.getEdges().size()+index);
                }
            }
        }

        graphPlayer.setVisited(true);

        Node nex = possible.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .findFirst().get();
        System.err.println("choose"+nex);
        return nex;
    }

    public String nodesToDirection(Node origin, Node destination) {
        if (origin.getX() == destination.getX()){
            return (origin.getY()<destination.getY())?"DOWN":"UP";
        }else{
            return (origin.getX()<destination.getX())?"RIGHT":"LEFT";
        }
    }

    public Map<Node, Node> getNodes() {
        return nodes;
    }
}
