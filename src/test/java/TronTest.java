import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Tristan on 25/08/2015.
 */
public class TronTest {

    /**
     * Basic Test
     */
    @Test
    public void tronTest() {
        Graph graph = new Graph();
        assertEquals(600, graph.nodesSize);
    }

    /**
     * Check Neighbours count
     */
    @Test
    public void tronNeighbours() {
        Graph graph = new Graph();

        assertEquals(4, graph.getNeighbours(new Node(15, 15)).size());
        assertEquals(3, graph.getNeighbours(new Node(0, 15)).size());

        assertEquals(2, graph.getNeighbours(new Node(0, 0)).size());
        assertEquals(2, graph.getNeighbours(new Node(0, 19)).size());
        assertEquals(2, graph.getNeighbours(new Node(29, 0)).size());
        assertEquals(2, graph.getNeighbours(new Node(29, 19)).size());

        assertTrue(graph.getNeighbours(new Node(30, 20)).isEmpty());
    }


    /**
     * Restore Path after opponent death
     */
    @Test
    public void restoreEdge() {
        Graph graph = new Graph();
        List<Node> oppos = new ArrayList<>();
        int startVisited = countVisited(graph);
        Node oldopponent = null;
        Node opponent = new Node(15, 10);
        oppos.add(opponent);
        for (int i = 0; i < 20; i++) {
            assertMovement(oldopponent, opponent);
            oldopponent = opponent;
            graph.addOpponent(opponent);
            graph.addPlayer(opponent);
            opponent = graph.getDirection();
            oppos.add(opponent);
            if (oldopponent != null) {
                graph.addOpponent(oldopponent);
            }
        }
        assertTrue(startVisited < countVisited(graph));
        //Restore Edges of dead opponent
        graph.restorePath(oppos);
        assertEquals(startVisited, countVisited(graph));
    }

    private int countVisited(final Graph graph) {
        int count = 0;
        for (int i = 0; i < graph.getxLength(); i++) {
            for (int j = 0; j < graph.getyLength(); j++) {
                if (graph.getGraph()[i][j] == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    private void assertMovement(Node oldopponent, Node opponent) {
        if (oldopponent == null) return;
        assertFalse(oldopponent.equals(opponent));
        double dx = oldopponent.getX() - opponent.getX();
        double dy = oldopponent.getY() - opponent.getY();
        assertTrue(dx != dy);
        double heuristic = Math.pow(dx, 2D) + Math.pow(dy, 2D);
        assertTrue(heuristic <= 2 && heuristic >= 1);
    }
}
