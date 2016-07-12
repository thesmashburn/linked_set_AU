import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Provides an implementation of the Set interface.
 * A doubly-linked list is used as the underlying data structure.
 * Although not required by the interface, this linked list is
 * maintained in ascending natural order. In those methods that
 * take a LinkedSet as a parameter, this order is used to increase
 * efficiency.
 *
 * @author Spencer Mashburn
 * @version 2016-03-13
 *
 */
public class LinkedSet<T extends Comparable<? super T>> implements Set<T> {

    //////////////////////////////////////////////////////////
    // Do not change the following three fields in any way. //
    //////////////////////////////////////////////////////////

    /** References to the first and last node of the list. */
   Node front;
   Node rear;

    /** The number of nodes in the list. */
   int size;

    /////////////////////////////////////////////////////////
    // Do not change the following constructor in any way. //
    /////////////////////////////////////////////////////////

    /**
     * Instantiates an empty LinkedSet.
     */
   public LinkedSet() {
      front = null;
      rear = null;
      size = 0;
   }


    //////////////////////////////////////////////////
    // Public interface and class-specific methods. //
    //////////////////////////////////////////////////

    ///////////////////////////////////////
    // DO NOT CHANGE THE TOSTRING METHOD //
    ///////////////////////////////////////
    /**
     * Return a string representation of this LinkedSet.
     *
     * @return a string representation of this LinkedSet
     */
   @Override
    public String toString() {
      if (isEmpty()) {
         return "[]";
      }
      StringBuilder result = new StringBuilder();
      result.append("[");
      for (T element : this) {
         result.append(element + ", ");
      }
      result.delete(result.length() - 2, result.length());
      result.append("]");
      return result.toString();
   }


    ///////////////////////////////////
    // DO NOT CHANGE THE SIZE METHOD //
    ///////////////////////////////////
    /**
     * Returns the current size of this collection.
     *
     * @return  the number of elements in this collection.
     */
   public int size() {
      return size;
   }

    //////////////////////////////////////
    // DO NOT CHANGE THE ISEMPTY METHOD //
    //////////////////////////////////////
    /**
     * Tests to see if this collection is empty.
     *
     * @return  true if this collection contains no elements, false otherwise.
     */
   public boolean isEmpty() {
      return (size == 0);
   }






    /**
     * Ensures the collection contains the specified element. Neither duplicate
     * nor null values are allowed. This method ensures that the elements in the
     * linked list are maintained in ascending natural order.
     *
     * @param  element  The element whose presence is to be ensured.
     * @return true if collection is changed, false otherwise.
     */
   @SuppressWarnings("unchecked")
   public boolean add(T element) {
      if (element != null) {

        //If the linked list is completely empty.
         if (isEmpty()) {
            front = new Node(element);
            size++;
            return true;
         }

         //If there is only a front node in the linked list.
         else if (size == 1) {
            if (front.element.compareTo(element) < 0) {
               rear = new Node(element);
               front.next = rear;
               rear.prev = front;
               size++;
               return true;
            }

            else if (front.element.compareTo(element) > 0) {
               rear = front;
               front = new Node(element);
               front.next = rear;
               rear.prev = front;
               size++;
               return true;
            }

            return false;
         }

        //If the size is greater than two, normal iteration occurs.
         Node p = front;
         int counter = 1;
         while (p != null) {

            //If the element is a duplicate, it is not added and false is returned.
            if (p.element.compareTo(element) == 0)
               return false;

            //If the value is greater than the element, we have found where to add.
            else if (p.element.compareTo(element) > 0) {

               //Make sure the value is not at the front, otherwise front must be reassigned to
               //the new Node.
               if (counter != 1) {
                  Node temp = new Node(element);
                  temp.prev = p.prev;
                  temp.next = p;
                  p.prev.next = temp;
                  p.prev = temp;
                  size++;
                  return true;
               }

               Node temp = new Node(element);
               front.prev = temp;
               temp.next = front;
               front = temp;
               size++;
               return true;
            }


            //If the element is greater than all current values and is at the final Node in
            //the linked list, rear must be reassigned. If it is just greater, the counter is
            //incremented to reflect position in linked list, and p is set to p.next.
            else if (p.element.compareTo(element) < 0 && counter == size) {
               Node temp = new Node(element);
               p.next = temp;
               temp.prev = p;
               rear = temp;
               size++;
               return true;
            }

            counter++;
            p = p.next;
         }
      }

      return false;
   }

    /**
     * Ensures the collection does not contain the specified element.
     * If the specified element is present, this method removes it
     * from the collection. This method, consistent with add, ensures
     * that the elements in the linked lists are maintained in ascending
     * natural order.
     *
     * @param   element  The element to be removed.
     * @return  true if collection is changed, false otherwise.
     */
   @SuppressWarnings("unchecked")
   public boolean remove(T element) {
      if (element != null) {

         Node p = front;
         int counter = 1;
         while (p != null) {

            //Check if the element to remove is equal to the current element. If so,
            //checks to see if it is at the beginning or end of the linked list so that
            //it may chang ethe respective front and rear.
            if (p.element.compareTo(element) == 0) {

               if (counter == 1) {
                  p.next.prev = null;
                  front = p.next;
                  size--;
                  return true;
               }

               else if (counter == size) {
                  p.prev.next = null;
                  rear = p.prev;
                  size--;
                  return true;
               }


               p.prev.next = p.next;
               p.next.prev = p.prev;
               size--;
               return true;
            }

            p = p.next;
            counter++;
         }
      }
      return false;
   }


    /**
     * Searches for specified element in this collection.
     *
     * @param   element  The element whose presence in this collection is to be tested.
     * @return  true if this collection contains the specified element, false otherwise.
     */
   @SuppressWarnings("unchecked")
   public boolean contains(T element) {
      if (element != null) {
         Node p = front;
         while (p != null) {
            if (p.element.compareTo(element) == 0) {
               return true;
            }
            p = p.next;
         }
         return false;
      }
      return false;
   }


    /**
     * Tests for equality between this set and the parameter set.
     * Returns true if this set contains exactly the same elements
     * as the parameter set, regardless of order.
     *
     * @return  true if this set contains exactly the same elements as
     *               the parameter set, false otherwise
     */
   @SuppressWarnings("unchecked")
   public boolean equals(Set<T> s) {
      if (s.size() == this.size) {
         for (T object : s) {
            if (object != null) {
               if (!this.contains(object))
                  return false;
            }
         }
         return true;
      }
      return false;
   }


    /**
     * Tests for equality between this set and the parameter set.
     * Returns true if this set contains exactly the same elements
     * as the parameter set, regardless of order.
     *
     * @return  true if this set contains exactly the same elements as
     *               the parameter set, false otherwise
     */
   @SuppressWarnings("unchecked")
   public boolean equals(LinkedSet<T> s) {
      if (s.size == this.size) {
         Node sLoop = s.front;
         Node thisLoop = this.front;

         while (sLoop != null) {
            if (sLoop.element.compareTo(thisLoop.element) != 0) {
               return false;
            }
            sLoop = sLoop.next;
            thisLoop = thisLoop.next;
         }
         return true;
      }
      return false;
   }


    /**
     * Returns a set that is the union of this set and the parameter set.
     *
     * @return  a set that contains all the elements of this set and the parameter set
     */
   @SuppressWarnings("unchecked")
   public Set<T> union(Set<T> s){
      if (s.isEmpty())
         return this;
      else if (this.isEmpty())
         return s;

      LinkedSet unionSet = this;
      for (T object : s) {
         unionSet.add(object);
      }
      return unionSet;
   }


    /**
     * Returns a set that is the union of this set and the parameter set.
     *
     * @return  a set that contains all the elements of this set and the parameter set
     */
   @SuppressWarnings("unchecked")
   public Set<T> union(LinkedSet<T> s){
      if (s.isEmpty())
         return this;
      else if (this.isEmpty())
         return s;

   //Variable and Object Declarations
      int maxSize = s.size + this.size;
      Node sLoop = s.front;
      Node thisLoop = this.front;
      LinkedSet unionSet = new LinkedSet();
      Node placeHolder = null;

   //While loop for the haters, just to make sure you get that OG time complexity. Ya feel?
      while (unionSet.size < maxSize) {
         if (sLoop == null && thisLoop == null) break;
         else if (sLoop == null || thisLoop == null) {
         Node listNode = null;
         boolean thisTrue =true;

         if (sLoop == null) {listNode = thisLoop; thisTrue = true;}
         else if (thisLoop == null) {listNode = sLoop; thisTrue = false;}

            placeHolder.next = new Node(listNode.element);
            placeHolder.next.prev = placeHolder;
            placeHolder = placeHolder.next;
            unionSet.size++;

          if (thisTrue) thisLoop = thisLoop.next;
          else if (!thisTrue) sLoop = sLoop.next;
            }

         //Checks to see if there are no items in the union set.
         else if (unionSet.size == 0) {
         Node listNode = null;
         int determine = 0;

         if (sLoop.element.compareTo(thisLoop.element) < 0) {listNode = sLoop; determine = 1;}
         else if (sLoop.element.compareTo(thisLoop.element) > 0) {listNode = thisLoop; determine = 2;}
         else {listNode = thisLoop; determine = 3;}

         placeHolder = new Node(listNode.element);
         unionSet.front = placeHolder;
         unionSet.size++;

         if (determine == 1) sLoop = sLoop.next;
         else if (determine == 2) thisLoop = thisLoop.next;
         else  {sLoop = sLoop.next;  thisLoop = thisLoop.next;}

         }



         else {
         Node listNode = null;
         int determine = 0;

         if (sLoop.element.compareTo(thisLoop.element) < 0) {listNode = sLoop; determine = 1;}
         else if (sLoop.element.compareTo(thisLoop.element) > 0) {listNode = thisLoop; determine = 2;}
         else {listNode = thisLoop; determine = 3;}

         placeHolder.next = new Node(listNode.element);
         placeHolder.next.prev = placeHolder;
         placeHolder = placeHolder.next;
         unionSet.size++;

         if (determine == 1) sLoop = sLoop.next;
         else if (determine == 2) thisLoop = thisLoop.next;
         else {sLoop = sLoop.next;  thisLoop = thisLoop.next;}

         }

      }
      unionSet.rear = placeHolder;
      return unionSet;
   }


    /**
     * Returns a set that is the intersection of this set and the parameter set.
     *
     * @return  a set that contains elements that are in both this set and the parameter set
     */
   @SuppressWarnings("unchecked")
   public Set<T> intersection(Set<T> s) {

   //If either Set is empty/null, this method returns null.
      if (s.isEmpty() || this.isEmpty()) {
         LinkedSet nullSet = new LinkedSet();
         return nullSet;
      }

         LinkedSet intersectionSet = new LinkedSet();

      //Iterate through the Set and check to see if the given object is
      //in this as well. If it is, it is then added to intersectionSet.
      for (T object : s) {
      Node temp = this.front;
         while (temp != null) {
            if (temp.element.compareTo(object) == 0) {
               intersectionSet.add(object);
            }
            temp = temp.next;

         }
      }

      return intersectionSet;
   }

    /**
     * Returns a set that is the intersection of this set and
     * the parameter set.
     *
     * @return  a set that contains elements that are in both
     *            this set and the parameter set
     */
   @SuppressWarnings("unchecked")
   public Set<T> intersection(LinkedSet<T> s) {

   //If either Set is empty/null, this method returns null.
      if (s.isEmpty() || this.isEmpty()) {
         LinkedSet nullSet = new LinkedSet();
         return nullSet;
      }

      Node sLoop = s.front;
      Node thisLoop = this.front;
      LinkedSet intersectionLinkedSet = new LinkedSet();
      Node placeHolder = null;
      int maxSize;

      if (s.size > this.size) maxSize = s.size;
      else   maxSize = this.size;


      //Iterate through both LinkedSets as long as neither one is empty
      while (intersectionLinkedSet.size < maxSize) {
         if (sLoop == null || thisLoop == null) {
            break; }

         //If they are not equal, the lesser value is incremeneted.
         else if (sLoop.element.compareTo(thisLoop.element) > 0) {
            thisLoop = thisLoop.next;
         }

         else if (sLoop.element.compareTo(thisLoop.element) < 0) {
            sLoop = sLoop.next;
         }

         else if (sLoop.element.compareTo(thisLoop.element) == 0) {

            if (intersectionLinkedSet.size == 0) {
               Node temp = new Node(sLoop.element);
               intersectionLinkedSet.front = temp;
               placeHolder = intersectionLinkedSet.front;
            }

            else {
               Node temp = new Node(sLoop.element);
               placeHolder.next = temp;
               temp.prev = placeHolder;
               placeHolder = temp;
            }

            intersectionLinkedSet.size++;
            sLoop = sLoop.next;
            thisLoop = thisLoop.next;
         }

      }

      if (!intersectionLinkedSet.isEmpty())
         intersectionLinkedSet.rear = placeHolder;


      return intersectionLinkedSet;


   }


    /**
     * Returns a set that is the complement of this set and the parameter set.
     *
     * @return  a set that contains elements that are in this set but not the parameter set
     */
   @SuppressWarnings("unchecked")
   public Set<T> complement(Set<T> s) {
      if (s.isEmpty())
         return this;
      else if (this.isEmpty())
         return this;

      Node iterate = front;
      LinkedSet result = new LinkedSet();
      Node resultNode = null;



      while (iterate != null) {
         if (!s.contains(iterate.element)) {
            if (result.size == 0) {
               result.front = new Node(iterate.element);
               resultNode = result.front;
            }

            else {
               resultNode.next = new Node(iterate.element);
               resultNode.next.prev = resultNode;
               resultNode = resultNode.next;
            }
            result.size++;
         }

         iterate = iterate.next;

      }

      result.rear = resultNode;
      return result;
   }


    /**
     * Returns a set that is the complement of this set and
     * the parameter set.
     *
     * @return  a set that contains elements that are in this
     *            set but not the parameter set
     */
   @SuppressWarnings("unchecked")
   public Set<T> complement(LinkedSet<T> s) {
      if (s .isEmpty())
         return this;
      else if (this.isEmpty())
         return this;

      LinkedSet result = new LinkedSet();
      Node placeHolder = null;
      Node thisLoop = this.front;
      Node sLoop = s.front;
      while (thisLoop != null) {

         if (sLoop == null || thisLoop.element.compareTo(sLoop.element) < 0 ) {
            if (result.size == 0) {
               placeHolder = new Node(thisLoop.element);
               result.front = placeHolder;
            }
            else {
               placeHolder.next = new Node(thisLoop.element);
               placeHolder.next.prev = placeHolder;
               placeHolder = placeHolder.next;
            }
            thisLoop = thisLoop.next;
            result.size++;
         }

         else if (thisLoop.element.compareTo(sLoop.element) > 0) sLoop = sLoop.next;
         else if (thisLoop.element.compareTo(sLoop.element) == 0) {
            sLoop = sLoop.next;
            thisLoop = thisLoop.next;
         }
      }

      result.rear = placeHolder;
      return result;




   }


    /**
     * Returns an iterator over the elements in this LinkedSet.
     * Elements are returned in ascending natural order.
     *
     * @return  an iterator over the elements in this LinkedSet
     */
   public Iterator<T> iterator() {
      return new defaultIterator(this);

   }


    /**
     * Returns an iterator over the elements in this LinkedSet.
     * Elements are returned in descending natural order.
     *
     * @return  an iterator over the elements in this LinkedSet
     */
   public Iterator<T> descendingIterator() {
      return new descendingIterator(this);

   }


    /**
     * Returns an iterator over the members of the power set
     * of this LinkedSet. No specific order can be assumed.
     *
     * @return  an iterator over members of the power set
     */
   @SuppressWarnings("unchecked")
   public Iterator<Set<T>> powerSetIterator() {
      return new powerTigerIterator(this);
   }



    //////////////////////////////
    // Private utility methods. //
    //////////////////////////////

    // Feel free to add as many private methods as you need.

    ////////////////////
    // Nested classes //
    ////////////////////

    //////////////////////////////////////////////
    // DO NOT CHANGE THE NODE CLASS IN ANY WAY. //
    //////////////////////////////////////////////

    /**
     * Defines a node class for a doubly-linked list.
     */
   @SuppressWarnings("unchecked")
   class Node {
        /** the value stored in this node. */
      T element;
        /** a reference to the node after this node. */
      Node next;
        /** a reference to the node before this node. */
      Node prev;

        /**
         * Instantiate an empty node.
         */
      public Node() {
         element = null;
         next = null;
         prev = null;
      }

        /**
         * Instantiate a node that containts element
         * and with no node before or after it.
         */
      public Node(T e) {
         element = e;
         next = null;
         prev = null;
      }
   }

   @SuppressWarnings("unchecked")
   private class defaultIterator implements Iterator<T> {
      private int counter;
      private int size;
      private LinkedSet paramSet;
      private Node iterateSet;


      public defaultIterator(LinkedSet theSet) {
         paramSet = theSet;
         iterateSet = paramSet.front;
         size = paramSet.size;
         counter = 1;
      }

      @Override
      public boolean hasNext() {
         return (counter <= size);
      }

      @SuppressWarnings("unchecked")
      @Override
      public T next() {
         if (hasNext()) {
            if (counter != size) {
               counter++;
               iterateSet = iterateSet.next;
               return iterateSet.prev.element;
            }
            else {
               counter++;
               return iterateSet.element;
            }
         }
         throw new NoSuchElementException();
      }

      @Override
      public void remove() {}

   }

   @SuppressWarnings("unchecked")
   private class descendingIterator implements Iterator<T> {
      private int counter;
      private int size;
      private LinkedSet paramSet;
      private Node iterateSet;


      public descendingIterator(LinkedSet theSet) {
         paramSet = theSet;
         iterateSet = paramSet.rear;
         size = paramSet.size;
         counter = 1;
      }

      @Override
      public boolean hasNext() {
         return (counter <= size);
      }

      @SuppressWarnings("unchecked")
      @Override
      public T next() {
         if (hasNext()) {
            if (counter != size) {
               counter++;
               iterateSet = iterateSet.prev;
               return iterateSet.next.element;
            }
            else {
               counter++;
               return iterateSet.element;
            }
         }
         throw new NoSuchElementException();
      }

      @Override
      public void remove() {}

   }


   @SuppressWarnings("unchecked")
   private class powerTigerIterator implements Iterator<Set<T>> {
      private int size;
      private int subset;
      private int M;
      private Node iterateSet;


      public powerTigerIterator(LinkedSet theSet) {
         iterateSet = theSet.front;
         size = theSet.size;
         M = (int)Math.pow(2, size);
         subset = 0;

      }

      @Override
      public boolean hasNext() {
         return (subset < M);
      }

      @SuppressWarnings("unchecked")
      @Override
      public Set<T> next() {
         if (hasNext()) {
            LinkedSet crouchingTiger = new LinkedSet();
            Node tigerBait = null;
            Node tigerSaint = iterateSet;

            int counter = 1;
            int mask = 1;

            while (tigerSaint != null) {

               if ((subset & mask) != 0) {
                  if (counter == 1) {
                     tigerBait = new Node(tigerSaint.element);
                     crouchingTiger.front = tigerBait;
                     crouchingTiger.size++;
                  }

                  else {
                     tigerBait.next = new Node(tigerSaint.element);
                     tigerBait.next.prev = tigerBait;
                     tigerBait = tigerBait.next;


                     crouchingTiger.size++;
                  }
                  counter++;

               }
               mask <<= 1;
               tigerSaint = tigerSaint.next;
            }
            subset++;
            crouchingTiger.rear = tigerBait;
            return crouchingTiger;

         }
         throw new NoSuchElementException();
      }

      @Override
      public void remove() {}

   }







}
