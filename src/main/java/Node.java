import java.util.ArrayList;
import java.util.List;


/**
 * Tron
 * Created by Tristan Brismontier on 23/08/2015.
 */
public class Node {
    private final int x;
    private final int y;
    private final float r;
    private final float g;
    private final float b;
    private boolean visited;
    private final List<Edge> edges = new ArrayList<>();

    public  List<Edge> getEdges(){
        return edges;
    }

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.r = (float)Math.random()*85;
        this.g =(float)Math.random()*255;
        this.b =(float)Math.random()*255;
        visited = false;
    }

    public float getR() {
        return r;
    }

    public float getG() {
        return g;
    }

    public float getB() {
        return b;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return x == node.x && y == node.y;
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
