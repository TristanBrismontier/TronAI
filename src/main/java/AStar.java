import java.util.*;

/**
 * Created by Tristan on 27/08/2015.
 */
public class AStar {

    private Map<Node,Node> camFrom =  new HashMap<>();
    private Map<Node,Integer> costSoFar =  new HashMap<>();
    private Node start;
    private Node target;

    public AStar(Graph graph, Node start, Node target){
        this.start = start;
        this.target = target;
        PriorityQueue<Node> frontier = new PriorityQueue<>(graph.nodesSize,new NodeCompartor());
        start.setPriority(0);
        frontier.offer(start);

        camFrom.put(start, start);
        costSoFar.put(start, 0);

        while(frontier.size() > 0){
            Node current = frontier.poll();
            if(current.equals(target)){
                break;
            }
            for (Node neigh : graph.getNeighbours(current)){
                int newCost = costSoFar.getOrDefault(neigh,0) + graph.Cost(current, neigh);
                if (!costSoFar.containsKey(neigh)|| newCost < costSoFar.get(neigh)){
                    costSoFar.put(neigh, newCost);
                    int priority = newCost + Heuristic(neigh,target);
                    neigh.setPriority(priority);
                    frontier.offer(neigh);
                    camFrom.put(neigh,current);
                }
            }
        }
    }

    public List<Node> getPath(){
        Node current = target;
        List<Node> path = new ArrayList<>();
        if(!camFrom.containsKey(target)){
            return path;
        }
        path.add(current);
        while(current != start){
            current = camFrom.get(current);
            path.add(current);
        }
        Collections.reverse(path);
        return path;
    }

    static public int Heuristic(Node a, Node b){
        return Math.abs(a.getX()-b.getX()) + Math.abs(a.getY()-b.getY());
    }

    public static class NodeCompartor implements Comparator<Node>
    {
        @Override
        public int compare(Node x, Node y)
        {
            if (x.getPriority() < y.getPriority())
            {
                return -1;
            }
            if (x.getPriority() > y.getPriority())
            {
                return 1;
            }
            return 0;
        }
    }

}
