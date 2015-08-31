import java.util.*;
import java.util.stream.Collectors;

/**
 * Tron
 * Created by Tristan Brismontier on 23/08/2015.
 */
public class Graph {
    public static int xLength = 30;
    public static int yLength = 20;
    private int[][] graph = new int[xLength][yLength];
    public static int nodesSize = xLength * yLength;
    private final Set<Node> opponents = new HashSet<>();
    private Node firstOpponent;
    private Node player;
    private String lastMove;

    public Graph() {
        this.lastMove = "nop";
        for (int i = 0; i < xLength; i++) {
            for (int j = 0; j < yLength; j++) {
                graph[i][j] = 0;
            }
        }
    }

    public List<Node> displayGraph() {
        List<Node> neighbours = new ArrayList<>();
        for (int i = 0; i < xLength; i++) {
            for (int j = 0; j < yLength; j++) {
                Node node = new Node(i,j);
                node.setVisited(graph[i][j] == 1);
                neighbours.add(node);
            }
        }
        return neighbours;
    }


    public static int getxLength() {
        return xLength;
    }

    public static int getyLength() {
        return yLength;
    }

    public static int getNodesSize() {
        return nodesSize;
    }

    public int[][] getGraph() {
        return graph;
    }

    private void setNode(final Node node, final int visible) {
        node.setVisited(visible==1);
        graph[node.getX()][node.getY()] = visible;
    }

    private Node getState(int x, int y) {
        try {

            final Node node = new Node(x, y);
            node.setVisited(graph[x][y] == 1);
            return node;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println(x + " ERROR " + y);
            throw e;
        }
    }

    public void newTurn() {
        opponents.clear();
    }

    public void addOpponent(final Node opponent) {
        firstOpponent = opponent;
        firstOpponent.setVisited(true);
        setNode(firstOpponent, 1);
        opponents.add(firstOpponent);
    }

    public void addPlayer(final Node player) {
        this.player = player;
    }


    public List<Node> getNeighbours(final Node node) {
        List<Node> neighbours = new ArrayList<>();
        int x = node.getX();
        int y = node.getY();
        if (x >= 30 || x < 0 || y < 0 || y >= 20) {
            return neighbours;
        }
        if ((x + 1) < 30) {
            neighbours.add(getState(x + 1, y));
        }
        if ((x - 1) > 1) {
            neighbours.add(getState(x - 1, y));
        }
        if ((y + 1) < 20) {
            neighbours.add(getState(x, y + 1));
        }
        if ((y - 1) > 1) {
            neighbours.add(getState(x, y - 1));
        }
        return neighbours.stream().filter(n -> !n.isVisited()).collect(Collectors.toList());
    }


    public Node getDirection() {

        System.err.println("Heuristic "+ AStar.Heuristic(player, firstOpponent) );
        if (AStar.Heuristic(player, firstOpponent) > 3) {
            setNode(firstOpponent, 0);
            System.err.println(firstOpponent + " Graph => "+graph[firstOpponent.getX()][firstOpponent.getY()]);
            System.err.println(player + " Player Graph => "+graph[player.getX()][player.getY()]);
            List<Node> path = new AStar(this, player, firstOpponent).getPath();
            System.err.println("PATH : " + path.size());
            path.forEach(System.err::println);
            if (path.size() > 1) {
                Node nex = path.get(1);
                System.err.println(nex+" TRERsdq");
                setNode(firstOpponent,1);
                setNode(player, 1);
                return  nex;
            }
            setNode(firstOpponent,1);
        }
        List<Node> neighbours = getNeighbours(player);

        int nextEdge = 0;
        Map<Node, Integer> possible = new HashMap<>();

        for (Node other : neighbours) {

            if (other.isVisited()) {
                continue;
            }
            if (possible.isEmpty() || nextEdge <= Math.min(getNeighbours(other).size(), 3)) {
                nextEdge = Math.min(getNeighbours(other).size(), 3);
                int x = other.getX() - player.getX();
                int y = other.getY() - player.getY();
                Node nes = new Node(other.getX() + x, other.getY() + y);
                if (nes == null || nes.isVisited()) {
                    possible.put(other, 0);
                } else {
                    possible.put(other, (Math.min(getNeighbours(nes).size(), 3) + ((lastMove.equals(computeDirection(player, other)) && getNeighbours(nes).size() > 1) ? 1 : 0)) * -1);
                }
            }
        }
        setNode(player, 1);
        Node nex = possible.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .findFirst().get();
        return  nex;
    }

    public String computeDirection(Node origin, Node destination) {
        if (origin.getX() == destination.getX()) {
            return (origin.getY() < destination.getY()) ? "DOWN" : "UP";
        } else {
            return (origin.getX() < destination.getX()) ? "RIGHT" : "LEFT";
        }
    }

    public String nodesToDirection(Node origin, Node destination) {
        if (origin.getX() == destination.getX()) {
            this.lastMove = (origin.getY() < destination.getY()) ? "DOWN" : "UP";
        } else {
            this.lastMove = (origin.getX() < destination.getX()) ? "RIGHT" : "LEFT";
        }
        return lastMove;
    }

    public void restorePath(List<Node> oppos) {
        oppos.forEach(node -> graph[node.getX()][node.getY()] = 0);
    }

    public Integer Cost(Node current, Node neigh) {
        return 1;
    }
}
