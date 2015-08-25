
/**
 * Tron
 * Created by Tristan Brismontier on 23/08/2015.
 */
public class Edge {

    private final Node nodeA;
    private final Node nodeB;

    public Edge(Node nodeA, Node nodeB) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
    }

    public boolean hasNode(final Node node){
        return (node.equals(nodeA) || node.equals(nodeB));
    }

    public Node getOther(final Node node){
        return (node.equals(nodeA))?nodeB:nodeA;
    }

    public Node getNodeA() {
        return nodeA;
    }

    public Node getNodeB() {
        return nodeB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return !(edge.nodeA == null || edge.nodeB == null) && (edge.nodeA.equals(this.nodeA) && edge.nodeB.equals(this.nodeB) || edge.nodeB.equals(this.nodeA) && edge.nodeA.equals(this.nodeB));
    }

    @Override
    public String toString() {
        return "Edge{" +
                "nodeA=" + nodeA +
                ", nodeB=" + nodeB +
                '}';
    }
}
