

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.GregorianCalendar;

/**
 * The test class motoristaTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class atorTeste
{
    /**
     * Default constructor for test class motoristaTest
     */
    public atorTeste()
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
    public void testarRegistaViagem() {
        Cliente a1 = new Cliente();
        Viagem v1 = new Viagem();
        a1.registaViagem(v1);
        assertTrue(a1.getHistorico().contains(v1));
    }

    @Test(expected = NenhumaViagemException.class)
    public void testarMaiorDesvio(){
       Cliente a1 = new Cliente();
       try{
           a1.maiorDesvio();
        }
        catch(Exception e){
        }
    }


    @Test
    public void testarEquals(){
        Cliente a1 = new Cliente();
        Cliente a2 = a1.clone();
        assertTrue(a1.equals(a2));
        Viagem v1 = new Viagem ();
        a1.registaViagem(v1);
        assertFalse(a1.equals(a2));
        assertFalse(a1==a2);
        assertFalse(a2.equals(null));
    }

    @Test
    public void testarViagemEntreDatas(){
        Cliente a1 = new Cliente();
        Viagem v1 = new Viagem(new Coordenada(),new Coordenada(),20.0,"lala",new GregorianCalendar(),20.0,10.0);
        a1.registaViagem(v1);
        try{
            assertEquals(1,a1.viagensEntreDatas(new GregorianCalendar(), new GregorianCalendar()).size());
            assertTrue(a1.viagensEntreDatas(new GregorianCalendar(), new GregorianCalendar()).contains(v1));
        }
        catch(Exception e){
        }
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
