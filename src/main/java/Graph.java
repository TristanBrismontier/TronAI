import java.util.*;
import java.util.stream.Collectors;

/**
 * Tron
 * Created by Tristan Brismontier on 23/08/2015.
 */
public class Graph {
    private final Map<Node,Node> nodes = new HashMap<>();
    private final Set<Node> opponents = new HashSet<>();
    private String lastMove;

    public void addEdge(Node nodeA,Node nodeB){
        final Edge edge = new Edge(nodeA, nodeB);
        nodeA.getEdges().add(edge);
        nodeB.getEdges().add(edge);
    }

    public Graph() {
        this.lastMove = "nop";
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
            vec2.getEdges().forEach(e -> e.getOther(vec2).getEdges().remove(e));
            vec2.getEdges().clear();
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
        Node graphPlayer = nodes.get(player);

        ArrayList<Node> neighbours = (ArrayList<Node>)getNeighbours(graphPlayer).stream().filter(node -> !node.isVisited()).collect(Collectors.toList());

        int nextEdge = 0;
        Map<Node,Integer> possible = new HashMap<>();

        for (Node other : neighbours){

            if(other.isVisited()){
                continue;
            }
            if(possible.isEmpty() || nextEdge <= Math.min(other.getEdges().size(),3)) {
                nextEdge = Math.min(other.getEdges().size(), 3);
                int x = other.getX() - player.getX();
                int y = other.getY() - player.getY();
                Node nes = nodes.get(new Node(other.getX() +x,other.getY()+y));
                if(nes == null || nes.isVisited()){
                    possible.put(other, 0);
                }else{
                    possible.put(other,( Math.min(nes.getEdges().size(), 3)+((lastMove.equals(computeDirection(player, other))&& nes.getEdges().size()>1)?1:0))*-1);
                }
            }
        }
        graphPlayer.setVisited(true);

        possible.entrySet().stream().forEach(System.err::println);
        System.err.println("");
        Node nex = possible.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .findFirst().get();
        nodesToDirection(player, nex);
        return nex;
    }

    public String computeDirection(Node origin, Node destination) {
        if (origin.getX() == destination.getX()){
            return (origin.getY()<destination.getY())?"DOWN":"UP";
        }else{
            return  (origin.getX()<destination.getX())?"RIGHT":"LEFT";
        }
    }

    public String nodesToDirection(Node origin, Node destination) {
        if (origin.getX() == destination.getX()){
            this.lastMove =  (origin.getY()<destination.getY())?"DOWN":"UP";
        }else{
            this.lastMove =  (origin.getX()<destination.getX())?"RIGHT":"LEFT";
        }
        return lastMove;
    }

    public Map<Node, Node> getNodes() {
        return nodes;
    }

    public void restorePath(List<Node> oppos) {
        if(oppos.size()>=2){
            Node last = nodes.get(oppos.get(0));
            last.setVisited(false);
            for (int j = 1; j < oppos.size() ; j++) {
                Node tmp = nodes.get(oppos.get(j));
                tmp.setVisited(false);
                addEdge(last,tmp);
                restoreAllLink(last);
                last = tmp;
            }
            restoreAllLink(last);
        }else{
            if(!oppos.isEmpty()){
                restoreAllLink(oppos.get(0));
            }
        }
        oppos.clear();
    }

    private void restoreAllLink(Node toRestore) {
        Node north = nodes.get(new Node(toRestore.getX(),toRestore.getY()-1));
        Node south = nodes.get(new Node(toRestore.getX(),toRestore.getY()+1));
        Node east = nodes.get(new Node(toRestore.getX()+1,toRestore.getY()));
        Node west = nodes.get(new Node(toRestore.getX()-1,toRestore.getY()));
        restoreEdge(toRestore, north);
        restoreEdge(toRestore, south);
        restoreEdge(toRestore, east);
        restoreEdge(toRestore, west);
    }

    private void restoreEdge(Node toRestore, Node cardiNode) {
        if(cardiNode!=null && !cardiNode.isVisited()){
            Edge e = new Edge(toRestore,cardiNode);
            if(!cardiNode.getEdges().contains(e)){
                cardiNode.getEdges().add(e);
            }
            if(!toRestore.getEdges().contains(e)){
                toRestore.getEdges().add(e);
            }
        }
    }

    public int getEdgeSize() {
        int size = 0;
        for (Map.Entry<Node,Node> node:nodes.entrySet()) {
            size += node.getValue().getEdges().size();
        }
        return size;
    }
}
