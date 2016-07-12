import java.util.Iterator;
import java.util.NoSuchElementException;


public class LinkedSetTesting<T extends Comparable<? super T>> {

   public static void main(String[] args) {
   
   LinkedSet test = new LinkedSet();
   test.add(1);
   test.add(2);
   test.add(3);
   
   
   LinkedSet test2 = new LinkedSet();
   test2.add(2);
   test2.add(3);
   test2.add(4);
    test2.add(8);
   test2.add(10);

  
    LinkedSet test4 = new LinkedSet();
    test4.add(1);
    test4.add(2);
    test4.add(3);
    
    Set test5 = test.union((Set) test2);
   System.out.println(test5);
   
     

   
   Set test3 = test.intersection((Set) test2);  
    System.out.println(test3);
  




   
   
   }
   }
