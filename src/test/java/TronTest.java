import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
}
