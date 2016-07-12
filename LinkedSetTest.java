import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class LinkedSetTest {


   /** Fixture initialization (common initialization
    *  for all tests). **/
   @Before public void setUp() {
   }


  
   @Test public void sizeTest() {
   LinkedSet<Integer> ls = new LinkedSet<Integer>();
   ls.add(1); ls.add(2); ls.add(3);
   Assert.assertEquals(3, ls.size);
         }
}
