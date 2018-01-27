

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
public class motoristaTest
{
    /**
     * Default constructor for test class motoristaTest
     */
    public motoristaTest()
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
    public void testarPrecoViagem() {
        Carro v1 = new Carro();
        v1.setPrecoBase(3.0);

        Motorista m1 = new Motorista("tonio@fast.taxi","toni","amordemae","Cucujaes","32-13-2028",v1);

        System.out.println(m1.precoViagem(-20.0));
        assertEquals(m1.getVeiculo().getPrecoBase()*20, m1.precoViagem(-20.0),0);
    }
    
    @Test
    public void testarAtualizaDados() {
        Carro v1 = new Carro();
        Motorista m1 = new Motorista("tonio@fast.taxi","toni","amordemae","Cucujaes","32-13-2028",v1);
        double distPerc = m1.getKmsTotais();
        m1.atualizaDados(new Coordenada(),-20,0.5,0.5);
        // Após o registo de uma nova viagem, a distância percorrida deverá aumentar
        assertTrue(distPerc < m1.getKmsTotais());
    }
    
    public void testarTotalFaturado() {
        Coordenada i = new Coordenada(20,30);
        Carro c1 = new Carro(70,4.5,50,"99-ZZ-99",i,false);
        
        Motorista m1 = new Motorista("tonio@fast.taxi","toni","amordemae","Cucujaes","32-13-2028",c1);
        
        Coordenada n = new Coordenada(30,20);
        Viagem v1 = new Viagem(i,n,m1.tempoViagem(i.distancia(n)),m1.getMail(),new GregorianCalendar(),m1.precoViagem(i.distancia(n)),0);
        
        m1.registaViagem(v1);

        assertEquals(90.0,m1.totalFaturado());
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
