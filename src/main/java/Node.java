import java.util.ArrayList;
import java.util.List;


/**
 * Tron
 * Created by Tristan Brismontier on 23/08/2015.
 */
public class Node {
    private final int x;
    private final int y;
    private boolean visited;
    private float priority;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        priority = 0;
        visited = false;
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

    public float getPriority() {
        return priority;
    }

    public void setPriority(float priority) {
        this.priority = priority;
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
                '}' + visited;
    }

    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
