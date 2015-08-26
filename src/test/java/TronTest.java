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
     *  Basic Test
     */
    @Test
    public void tronTest(){
        TronUniverse tron = new TronUniverse();
        assertEquals(600, tron.getGraph().getNodes().size());
        assertEquals(2300,tron.getGraph().getEdgeSize());
    }

    @Test
    public void restoreEdge(){
        TronUniverse tron = new TronUniverse();
        int startEdges = tron.getGraph().getEdgeSize();
        List<Node> oppos = new ArrayList<>();
        Node oldopponent=null;
        Node opponent = tron.getGraph().getNodes().get(new Node(15, 10));
        oppos.add(opponent);
        for (int i = 0; i <20 ; i++) {
            assertMovement(oldopponent,opponent);
            oldopponent = opponent;
            tron.getGraph().addOpponent(opponent);
            opponent = tron.getGraph().getDirection(opponent);
            oppos.add(opponent);
            if (oldopponent != null) {
                tron.getGraph().removeEdge(opponent, oldopponent, false);
            }
        }
        assertTrue(startEdges>tron.getGraph().getEdgeSize());
        //Restore Edges of dead opponent
        tron.getGraph().restorePath(oppos);
        assertEquals(startEdges,tron.getGraph().getEdgeSize());
    }

    private void assertMovement(Node oldopponent, Node opponent) {
        if(oldopponent == null)return;
        assertFalse(oldopponent.equals(opponent));
        double dx = oldopponent.getX() - opponent.getX();
        double dy = oldopponent.getY() - opponent.getY();
        assertTrue(dx!=dy);
        double hypotenuse = Math.pow(dx,2D)+Math.pow(dy,2D);
        assertTrue(hypotenuse<=2 && hypotenuse >=1);
    }


}
