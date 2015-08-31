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
        TronUniverse tron = new TronUniverse();
        assertEquals(600, tron.getGraph().nodesSize);

    }

    /**
     * Check Neighbours count
     */
    @Test
    public void tronNeighbours() {
        TronUniverse tron = new TronUniverse();

        assertEquals(4, tron.getGraph().getNeighbours(new Node(15, 15)).size());
        assertEquals(3, tron.getGraph().getNeighbours(new Node(0, 15)).size());

        assertEquals(2, tron.getGraph().getNeighbours(new Node(0, 0)).size());
        assertEquals(2, tron.getGraph().getNeighbours(new Node(0, 19)).size());
        assertEquals(2, tron.getGraph().getNeighbours(new Node(29, 0)).size());
        assertEquals(2, tron.getGraph().getNeighbours(new Node(29, 19)).size());

        assertTrue(tron.getGraph().getNeighbours(new Node(30, 20)).isEmpty());
    }


    /**
     * Restore Path after opponent death
     */
    @Test
    public void restoreEdge() {
        TronUniverse tron = new TronUniverse();
        List<Node> oppos = new ArrayList<>();
        int startVisited = countVisited(tron);
        Node oldopponent = null;
        Node opponent = new Node(15, 10);
        oppos.add(opponent);
        for (int i = 0; i < 20; i++) {
            assertMovement(oldopponent, opponent);
            oldopponent = opponent;
            tron.getGraph().addOpponent(opponent);
            tron.getGraph().addPlayer(opponent);
            opponent = tron.getGraph().getDirection();
            oppos.add(opponent);
            if (oldopponent != null) {
                tron.getGraph().addOpponent(oldopponent);
            }
        }
        assertTrue(startVisited < countVisited(tron));
        //Restore Edges of dead opponent
        tron.getGraph().restorePath(oppos);
        assertEquals(startVisited, countVisited(tron));
    }

    private int countVisited(final TronUniverse tron) {
        int count = 0;
        for (int i = 0; i < tron.getGraph().getxLength(); i++) {
            for (int j = 0; j < tron.getGraph().getyLength(); j++) {
                if (tron.getGraph().getGraph()[i][j] == 1) {
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
