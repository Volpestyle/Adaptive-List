package edu.iastate.cs228.proj3;
/*
 *  @author James Volpe
 *
 *
 *  An implementation of List<E> based on a doubly-linked list 
 *  with an array for indexed reads/writes
 *
 */

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import edu.iastate.cs228.proj3.AdaptiveList.ListNode;

public class AdaptiveList<E> implements List<E>
{
  public class ListNode 
  {                     
    public E data;        
		public ListNode next;
		public ListNode prev; 
    
    public ListNode(E item)
    {
      data = item;
      next = prev = null;
    }
  }
  
  public ListNode head;  // dummy node made public for testing.
  public ListNode tail;  // dummy node made public for testing.
  private int numItems;  // number of data items
  private boolean linkedUTD; // true if the linked list is up-to-date.

  public E[] theArray;  // the array for storing elements
  private boolean arrayUTD; // true if the array is up-to-date.

  public AdaptiveList()
  {  
	  clear(); 
  }

  @Override
  public void clear()
  {
	head = new ListNode(null);
	tail = new ListNode(null);  
    head.next = tail;
    tail.prev = head;
    linkedUTD = true;
    arrayUTD = false;
    numItems = 0;
    theArray = null;
  }

  public boolean getlinkedUTD()
  {
    return linkedUTD;
  }

  public boolean getarrayUTD()
  {
    return arrayUTD;
  }

  public AdaptiveList(Collection<? extends E> c)
  {
	clear();
	addAll(c);  
  }

  // Removes the node from the linked list.
  // This method should be used to remove a node 
  // from the linked list.
  private void unlink(ListNode toRemove)
  {
    if ( toRemove == head || toRemove == tail )
      throw new RuntimeException("An attempt to remove head or tail");
    toRemove.prev.next = toRemove.next;
    toRemove.next.prev = toRemove.prev;
  }

  // Inserts new node toAdd right after old node current.
  // This method should be used to add a node to the linked list.
  private void link(ListNode current, ListNode toAdd)
  {
    if ( current == tail )
      throw new RuntimeException("An attempt to chain after tail");
    if ( toAdd == head || toAdd == tail )
      throw new RuntimeException("An attempt to add head/tail as a new node");
    toAdd.next = current.next;
    toAdd.next.prev = toAdd;
    toAdd.prev = current;
    current.next = toAdd;
  }

  private void updateArray() // makes theArray up-to-date.
  {
    if ( numItems < 0 )
      throw new RuntimeException("numItems is negative: " + numItems);
    if ( ! linkedUTD )
      throw new RuntimeException("linkedUTD is false");

 	theArray = (E[]) new Object[numItems];
 	ListNode currentNode = head.next; //Head is the predeccesor of the first node
 	int i = 0;
 	while (currentNode.next != null) {
 		theArray[i] = currentNode.data;
 		currentNode = currentNode.next;
 		i++;
 	}
 	arrayUTD = true;
 }
  

  private void updateLinked() // makes the linked list up-to-date.
  {
    if ( numItems < 0 )
      throw new RuntimeException("numItems is negative: " + numItems);
    if (!arrayUTD)
      throw new RuntimeException("arrayUTD is false");
    
    if ( theArray == null || theArray.length < numItems )
      throw new RuntimeException("theArray is null or shorter");
    	
    head.next = tail; // Reset to empty list
	tail.prev = head; 
    	for (int j = 0; j < theArray.length; j++) {
		ListNode newNode = new ListNode(theArray[j]);
		link(tail.prev, newNode);
	}
	linkedUTD = true;
  }

  @Override
  public int size()
  {
	  return numItems;
  }

  @Override
  public boolean isEmpty()
  {
	  return numItems == 0;
  }

  @Override
  public boolean add(E obj)
  {
	 if (!linkedUTD) updateLinked();
	 ListNode newNode = new ListNode(obj);
	 link(tail.prev, newNode);
	 /*newNode.next = tail; //tail is successor of last data node
	 newNode.prev = tail.prev; 
	 newNode.prev.next = newNode;
	 tail.prev = newNode;*/
	 numItems++;
	 arrayUTD = false;
	 return true; 
  }

  @Override
  public boolean addAll(Collection< ? extends E> c)
  {
	  Iterator<? extends E> i = c.iterator();
	  while (i.hasNext()) {
		  add(i.next());
	  }
	  if (c.size() == 0) {
		  return false;
	  }
	  return true;
  }

  @Override
  public boolean remove(Object obj)
  {
	  if (!contains(obj)) return false;
	  
	  AdaptiveListIterator i = new AdaptiveListIterator();
	  while (i.next() != obj)
	  {
		  //wait
	  }
	  i.remove();
	  return true;
  }

  private void checkIndex(int pos) // a helper method
  {
    if ( pos >= numItems || pos < 0 )
     throw new IndexOutOfBoundsException(
       "Index: " + pos + ", Size: " + numItems);
  }

  private void checkIndex2(int pos) // a helper method
  {
    if ( pos > numItems || pos < 0 )
     throw new IndexOutOfBoundsException(
       "Index: " + pos + ", Size: " + numItems);
  }

  private void checkNode(ListNode cur) // a helper method
  {
    if ( cur == null || cur == tail )
     throw new RuntimeException(
      "numItems: " + numItems + " is too large");
  }

  private ListNode findNode(int pos)   // a helper method
  {
    ListNode cur = head;
    for ( int i = 0; i < pos; i++ )
    {
      checkNode(cur);
      cur = cur.next;
    }
    checkNode(cur);
    return cur;
  }

  @Override
  public void add(int pos, E obj)
  {
	  if (pos > numItems || pos < 0) throw new IndexOutOfBoundsException();
	  if (!linkedUTD) updateLinked();

	  AdaptiveListIterator ali = new AdaptiveListIterator(pos);
	  ali.add(obj);
	  arrayUTD = false;
  }

  @Override
  public boolean addAll(int pos, Collection< ? extends E> c)
  {
	  if (c == null || c.isEmpty()) return false;
	  if (pos < 0 || pos > numItems) throw new IndexOutOfBoundsException();
	  if (!linkedUTD) updateLinked();
	  
	  Iterator<? extends E> i = c.iterator();
	  AdaptiveListIterator ali = new AdaptiveListIterator(pos);
	  while (i.hasNext()) {
		  ali.add(i.next());
	  }
	  return true;
  }

  @Override
  public E remove(int pos)
  {
	  if (pos < 0 || pos > numItems) throw new IndexOutOfBoundsException();
	  AdaptiveListIterator ali = new AdaptiveListIterator(pos);
	  E result = ali.next();
	  ali.remove();	
	  return result; 
  }

  @Override
  public E get(int pos) //array
  {
	  if (pos > size() || pos < 0) throw new IndexOutOfBoundsException();
	  if (!arrayUTD) updateArray();
	  return theArray[pos]; 
  }

  @Override
  public E set(int pos, E obj) //array
  {
	  if (pos > size() || pos < 0)
			throw new IndexOutOfBoundsException();
		if (!arrayUTD) updateArray();
		E result = get(pos);
		theArray[pos] = obj;
		linkedUTD = false;
		return result; 
  } 

  /**
   *  If the number of elements is at most 1, 
   *  the method returns false. Otherwise, it 
   *  reverses the order of the elements in the 
   *  array without using any additional array, 
   *  and returns true. Note that if the array 
   *  is modified, then linkedUTD needs to be set 
   *  to false.
   */
  public boolean reverse() //array
  {
	  if (numItems <= 1)
			return false;
	  if (!arrayUTD)
			updateArray();

	  for (int i = 0; i < numItems / 2; i++) {// up to halfway
	  		E temp = theArray[i];
	  		theArray[i] = theArray[theArray.length - 1 - i];
	  		theArray[theArray.length - 1 -i] = temp;
  		}
		linkedUTD = false;
		return true;
  }

  
  /** 
   *  If the number of elements is at most 1, 
   *  the method returns false. Otherwise, it 
   *  swaps the items positioned at even index 
   *  with the subsequent one in odd index without 
   *  using any additional array, and returns true.
   *  Note that if the array is modified, then 
   *  linkedUTD needs to be set to false. 
   */
  public boolean reorderOddEven() //array
  {
	  if (numItems <= 1) return false;
	  if (!arrayUTD) updateArray();
	  for (int i = 0; i < numItems - 1; i+=2) {
			E temp = theArray[i];
		  	theArray[i] = theArray[i + 1];
		  	theArray[i + 1] = temp;	
		}
	  linkedUTD = false;
	  return true;
  }
  
  @Override
  public boolean contains(Object obj)
  {
	  AdaptiveListIterator i = new AdaptiveListIterator();
	  while (i.hasNext()) {
		  if (i.next() == obj) {
				return true;
		  }
	  }
	  return false;
  }

  @Override
  public boolean containsAll(Collection< ? > c)
  {
	  Iterator<?> i = c.iterator();
	  while (i.hasNext()) {
		  if (!contains(i.next())) {
			  return false;
		  }
	  }
	  return true;
  }


  @Override
  public int indexOf(Object obj)
  {
	  int index = -1;
	  if (contains(obj)) {
		AdaptiveListIterator i = new AdaptiveListIterator();
		while (i.hasNext()) {
			if (i.next().equals(obj)) {
				index = (i.index - 1);
				return index;
			}
		}
	  }
	  return index; 
  }

  @Override
  public int lastIndexOf(Object obj)
  {
	  int theIndex = -1;
	  int index = 0;
	  if (contains(obj)) {
		  AdaptiveListIterator i = new AdaptiveListIterator();
		  while (i.hasNext()) {
			  if (i.next().equals(obj)) {
				  theIndex = index;
			  }
			  index++;
		  }
		}
	  return theIndex;
	}
  
  @Override
  public boolean removeAll(Collection<?> c)
  {
	  if (c == null) throw new NullPointerException();
	  
	  boolean removed = false;
	  AdaptiveListIterator i = new AdaptiveListIterator();
	  while (i.hasNext()) {
		  if (c.contains(i.next())) {
			  i.remove();
			  removed = true;
		  }
	  }
	  return removed; 
  }

  @Override
  public boolean retainAll(Collection<?> c)
  {
	  if (c == null) throw new NullPointerException();
	  if (!arrayUTD) updateArray();
	  
	  boolean removed = false;
	  AdaptiveListIterator i = new AdaptiveListIterator();
	  while (i.hasNext()) {
		  if (!c.contains(i.next())) {
			  i.remove();
			  removed = true;
		  }
	  }
	  return removed; 
  }

  @Override
  public Object[] toArray()
  {
	  if (!arrayUTD) updateArray(); 
	  Object[] result = new Object[numItems];
	  for (int i = 0; i < numItems; i++) {
		  result[i] = theArray[i];
	  }
	  return result; 
  }
  
  
  /**
   * In here you are allowed to use only 
   * java.util.Arrays.copyOf method.
   */
  @Override
  public <T> T[] toArray(T[] arr)
  {
	  if (!arrayUTD) updateArray(); 
	  return (T[]) java.util.Arrays.copyOf(theArray, numItems); 
  }

  @Override
  public List<E> subList(int fromPos, int toPos)
  {
    throw new UnsupportedOperationException();
  }

  private class AdaptiveListIterator implements ListIterator<E>
  {
    private int    index;  // index of next node;
    private ListNode cur;  // node at index - 1
    private ListNode last; // node last visited by next() or previous()

    public AdaptiveListIterator()
    {
      if (!linkedUTD) 
    	  	updateLinked();
      cur = head;
      last = null;
      index = 0;
    }
    public AdaptiveListIterator(int pos)
    {
      if ( ! linkedUTD ) 
    	  	updateLinked();
      cur = head;
      index = 0;
      for (int i = 0; i < pos; i++) {
    	  	next(); 
      }
      last = null;
    }

    @Override
    public boolean hasNext()
    {
    		return cur.next != tail; 
    }

    @Override
    public E next()
    {
	    	if (!hasNext()) 
	    		throw new NoSuchElementException();
		cur = cur.next;
		last = cur;
		index++;
		return last.data; 
    } 

    @Override
    public boolean hasPrevious()
    {
    		return cur != head; //head is predeccessor to first element
    }

    @Override
    public E previous()
    {
    		if (!hasPrevious())
			throw new NoSuchElementException();
    		last = cur;
    		cur = cur.prev;
		index--;
		return last.data;
    }
    
    @Override
    public int nextIndex()
    {
      return index;
    }

    @Override
    public int previousIndex()
    {
    		return index - 1;
    }

    @Override
    public void remove()
    {
    		if (last == null) throw new IllegalStateException();
		if (!linkedUTD) updateLinked();
    		if (cur == last) {
    			cur = cur.prev;
    		}
		AdaptiveList.this.unlink(last);
		index--;
		numItems--;
		last = null;
		arrayUTD = false;
    }

    @Override
    public void add(E obj)
    { 
    		if (!linkedUTD) updateLinked();
		ListNode newNode = new ListNode(obj);
		AdaptiveList.this.link(cur, newNode);
		/*newNode.next = cur.next;
	    newNode.next.prev = newNode;
	    newNode.prev = cur;
	    cur.next = newNode;*/
		cur = cur.next;
		index++;
		numItems++;
		last = null;
    } 

    @Override
    public void set(E obj)
    {
    		if (last == null) throw new IllegalStateException();
		last.data = obj;
		arrayUTD = false;
    }
  } // AdaptiveListIterator
  
  @Override
  public boolean equals(Object obj)
  {
    if ( ! linkedUTD ) updateLinked();
    if ( (obj == null) || ! ( obj instanceof List<?> ) )
      return false;
    List<?> list = (List<?>) obj;
    if ( list.size() != numItems ) return false;
    Iterator<?> iter = list.iterator();
    for ( ListNode tmp = head.next; tmp != tail; tmp = tmp.next )
    {
      if ( ! iter.hasNext() ) return false;
      Object t = iter.next();
      if ( ! (t == tmp.data || t != null && t.equals(tmp.data) ) )
         return false;
    }
    if ( iter.hasNext() ) return false;
    return true;
  }

  @Override
  public Iterator<E> iterator()
  {
    return new AdaptiveListIterator();
  }

  @Override
  public ListIterator<E> listIterator()
  {
    return new AdaptiveListIterator();
  }

  @Override
  public ListIterator<E> listIterator(int pos)
  {
    checkIndex2(pos);
    return new AdaptiveListIterator(pos);
  }

  // Adopted from the List<E> interface.
  @Override
  public int hashCode()
  {
    if ( ! linkedUTD ) updateLinked();
    int hashCode = 1;
    for ( E e : this )
       hashCode = 31 * hashCode + ( e == null ? 0 : e.hashCode() );
    return hashCode;
  }

  // You should use the toString*() methods to see if your code works as expected.
  @Override
  public String toString()
  {
   // Other options System.lineSeparator or
   // String.format with %n token...
   // Not making data field.
   String eol = System.getProperty("line.separator");
   return toStringArray() + eol + toStringLinked();
  }

  public String toStringArray()
  {
    String eol = System.getProperty("line.separator");
    StringBuilder strb = new StringBuilder();
    strb.append("A sequence of items from the most recent array:" + eol );
    strb.append('[');
    if ( theArray != null )
      for ( int j = 0; j < theArray.length; )
      {
        if ( theArray[j] != null )
           strb.append( theArray[j].toString() );
        else
           strb.append("-");
        j++;
        if ( j < theArray.length )
           strb.append(", ");
      }
    strb.append(']');
    return strb.toString();
  }

  public String toStringLinked()
  {
    return toStringLinked(null);
  }

  // iter can be null.
  public String toStringLinked(ListIterator<E> iter)
  {
    int cnt = 0;
    int loc = iter == null? -1 : iter.nextIndex();

    String eol = System.getProperty("line.separator");
    StringBuilder strb = new StringBuilder();
    strb.append("A sequence of items from the most recent linked list:" + eol );
    strb.append('(');
    for ( ListNode cur = head.next; cur != tail; )
    {
      if ( cur.data != null )
      {
        if ( loc == cnt )
        {
          strb.append("| ");
          loc = -1;
        }
        strb.append(cur.data.toString());
        cnt++;

        if ( loc == numItems && cnt == numItems )
        {
          strb.append(" |");
          loc = -1;
        }
      }
      else
         strb.append("-");
      
      cur = cur.next;
      if ( cur != tail )
         strb.append(", ");
    }
    strb.append(')');
    return strb.toString();
  }
}
