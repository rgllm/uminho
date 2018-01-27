

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class coordeanadaTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class coordeanadaTest
{
    /**
     * Default constructor for test class coordeanadaTest
     */
    public coordeanadaTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }
    
    @Test
    public void testarEquals(){
        Coordenada c1 = new Coordenada();
        Coordenada c2 = new Coordenada();
        assertTrue(c1.equals(c2));
        Coordenada c3 = new Coordenada(20,30);
        assertFalse(c1.equals(c3));
        Coordenada c4 = new Coordenada(30,20);
        assertFalse(c3.equals(c4));
        assertTrue(c3.equals(c3.clone()));
        assertFalse(c1.equals(null));
    }
    
    @Test
    public void testarDistancia(){
        Coordenada c1 = new Coordenada();
        Coordenada c2 = new Coordenada();
        assertEquals(0,c1.distancia(c2));
        Coordenada c3 = new Coordenada(20,30);
        Coordenada c4 = new Coordenada(30,20);
        assertEquals(c3.distancia(c4),c4.distancia(c3)); 
        assertEquals(c1.distancia(c3),c3.distancia(c1));
        
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
}
