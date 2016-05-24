/**
 * Crono = mede um tempo entre start() e stop()
 * O tempo e medido em nanosegundos e convertido para
 * um double que representa os segs na sua parte inteira.
 *
 * @author FMM
 * @version 1-2009
 *
 * Uso:
 * Crono.start();
 * Crono.stop();
 * Crono.print();
 */
import static java.lang.System.nanoTime;
public class Crono {

  private static long inicio = 0L;
  private static long fim = 0L;

  public static void start() {
      fim = 0L; inicio = nanoTime();
  }

  public static double stop() {
      fim = nanoTime();
      long elapsedTime = fim - inicio;
      // segundos
      return elapsedTime / 1.0E09;
  }

  public static String print() {
      return "" + stop();
  }

}