package edu.iastate.cs228.proj3;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author James Volpe
 * i love u
 */

public class TestAdaptiveList
{
	private static AdaptiveList<String> list;
	private static Collection<String> collection;
	private static Collection<String> collection2;
	private ListIterator<String> i;

	@Test
	public void constructorTest()
	{
		list = new AdaptiveList<>();
		assertEquals(0, list.size());
		assertFalse(list.getarrayUTD());
		assertTrue(list.getlinkedUTD());
	}
	
	@Test
	public void constructorWithCollectionTest() //this tests for addAll
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		assertEquals(4, list.size());
		assertEquals(collection, list);
	}
	
	@Test
	public void addAllExceptions()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		boolean thrown = false;
		try {
			list.addAll(null);
		} catch (NullPointerException exception) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public void addTest()
	{
		list = new AdaptiveList<>();
		list.add("luigi");
		assertFalse(list.getarrayUTD());
		assertTrue(list.getlinkedUTD());
		assertTrue(list.contains("luigi"));
		assertEquals("luigi", list.head.next.data);
	}
	
	@Test
	public void addAtPosition()
	{
		list = new AdaptiveList<>();
		list.add("wario");
		list.add(0, "luigi");
		assertFalse(list.getarrayUTD());
		assertTrue(list.getlinkedUTD());
		assertTrue(list.contains("luigi"));
		assertEquals("luigi", list.head.next.data);
		assertEquals("wario", list.head.next.next.data);
	}
	
	@Test
	public void addAtPositionExceptions()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		boolean thrown = false;
		boolean thrown2 = false;
		try {
			list.add(-1, "waluigi");
		} catch (IndexOutOfBoundsException exception) {
			thrown = true;
		}
		assertTrue(thrown);
		try {
			list.add(5, "waluigi"); // you can add at the very end, ex: pos 4.
		} catch (IndexOutOfBoundsException exception) {
			thrown2 = true;
		}
		assertTrue(thrown2);
	}
	
	@Test
	public void addAllAtPositionTest()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>();
		list.add("luigi");
		list.add("wario");
		list.addAll(0, collection); //collection added before "luigi" and "wario"
		assertEquals(6, list.size());
		assertFalse(list.getarrayUTD());
		assertTrue(list.getlinkedUTD());
		assertEquals("wario", list.tail.prev.data); //wario should be last
	}
	
	@Test
	public void addAllAtPositionExceptions()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		collection2 = Arrays.asList("wee", "doop", "doopp", "da");
		list = new AdaptiveList<>(collection);
		boolean thrown = false;
		boolean thrown2 = false;
		try {
			list.addAll(-1, collection2);
		} catch (IndexOutOfBoundsException exception) {
			thrown = true;
		}
		assertTrue(thrown);
		try {
			list.addAll(5, collection2); // you can add at the very end, ex: pos 4.
		} catch (IndexOutOfBoundsException exception) {
			thrown2 = true;
		}
		assertTrue(thrown2);
	}
	
	@Test
	public void removeObjTest()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		boolean result;
		result = list.remove("shrek");
		assertTrue(result);
		assertEquals(3, list.size());
		assertEquals("ogres", list.tail.prev.data);
		assertFalse(list.getarrayUTD());
		assertTrue(list.getlinkedUTD());
	}
	
	@Test
	public void removeIntTest()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		String result = list.remove(2);
		assertEquals(result, "shrek");
		assertEquals(3, list.size());
		assertEquals("ogres", list.tail.prev.data);
		assertFalse(list.getarrayUTD());
		assertTrue(list.getlinkedUTD());
	}
	
	@Test
	public void getTest()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);;
		assertEquals(list.get(0), "urmum");
		assertEquals(list.get(2), "shrek");
	}
	
	@Test
	public void getExceptions()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		boolean thrown = false;
		boolean thrown2 = false;
		try {
			list.get(-1);
		} catch (IndexOutOfBoundsException exception) {
			thrown = true;
		}
		assertTrue(thrown);
		try {
			list.get(5); 
		} catch (IndexOutOfBoundsException exception) {
			thrown2 = true;
		}
		assertTrue(thrown2);
	}
	
	@Test
	public void setTest()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		list.set(0, "memum");
		assertEquals("memum", list.get(0));
		assertTrue(list.getarrayUTD());
		assertFalse(list.getlinkedUTD());
	}
	
	@Test
	public void setExceptions()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		boolean thrown = false;
		boolean thrown2 = false;
		try {
			list.set(-1, "doop");
		} catch (IndexOutOfBoundsException exception) {
			thrown = true;
		}
		assertTrue(thrown);
		try {
			list.set(5, "doop"); 
		} catch (IndexOutOfBoundsException exception) {
			thrown2 = true;
		}
		assertTrue(thrown2);
	}
	
	@Test
	public void reverseTest()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		assertTrue(list.reverse());
		assertEquals("ogres", list.get(0));
		assertEquals("shrek", list.get(1));
	}
	
	@Test
	public void reorderOddEvenTest()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		assertTrue(list.reorderOddEven());
		assertEquals("420", list.get(0));
		assertEquals("urmum", list.get(1));
		assertEquals("ogres", list.get(2));
	}
	
	@Test
	public void containsTest()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		assertTrue(list.contains("urmum"));
		assertFalse(list.contains("fiona"));
	}
	
	@Test
	public void containsAllTest()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		collection2 = Arrays.asList("urmum", "420", "shrek", "doop");
		list = new AdaptiveList<>(collection);
		assertTrue(list.containsAll(collection));
		assertFalse(list.containsAll(collection2));
	}
	
	@Test
	public void indexOfTest()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres", "shrek");
		list = new AdaptiveList<>(collection);
		assertEquals(-1, list.indexOf("memum"));
		assertEquals(2, list.indexOf("shrek"));
	}
	
	@Test
	public void lastIndexOfTest()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres", "shrek");
		list = new AdaptiveList<>(collection);
		assertEquals(-1, list.lastIndexOf("memum"));
		assertEquals(4, list.lastIndexOf("shrek"));
	}
	
	@Test
	public void removeAllTest1()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		collection2 = Arrays.asList("urmum", "420", "shrek");
		list = new AdaptiveList<>(collection);
		list.removeAll(collection2);
		assertEquals(1, list.size());
		assertEquals("ogres", list.head.next.data);
		assertTrue(list.getlinkedUTD());
		assertFalse(list.getarrayUTD());
	}
	
	@Test
	public void removeAllTest2()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		collection2 = Arrays.asList("420", "shrek", "doop");
		list = new AdaptiveList<>(collection);
		list.removeAll(collection2);
		assertEquals(2, list.size());
		assertEquals("urmum", list.head.next.data);
		assertTrue(list.getlinkedUTD());
		assertFalse(list.getarrayUTD());
	}
	
	@Test
	public void retainAllTest()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		collection2 = Arrays.asList("420", "shrek");
		list = new AdaptiveList<>(collection);
		list.retainAll(collection2);
		assertEquals(2, list.size());
		assertEquals("420", list.head.next.data);
		assertEquals("shrek", list.head.next.next.data);
		assertTrue(list.getlinkedUTD());
		assertFalse(list.getarrayUTD());
	}
	
	@Test
	public void retainAllExceptions()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		boolean thrown = false;
		try {
			list.retainAll(null);
		} catch (NullPointerException exception) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	/**
	 * PIZZA TIME
	 * 
	 * 
	 */
	
	@Test
	public void ALI_nextTest()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		i = list.listIterator();
		assertEquals("urmum", i.next());
		assertEquals("420", i.next());
		i.next();
		assertEquals("ogres", i.next());
	}
	
	@Test
	public void ALI_hasNextTest()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		i = list.listIterator();
		assertTrue(i.hasNext());
		i.next();i.next();i.next();i.next();
		assertFalse(i.hasNext());
	}
	
	@Test
	public void ALI_nextIndexTest()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		i = list.listIterator();
		assertEquals(0, i.nextIndex());
		i.next();
		assertEquals(1, i.nextIndex());
		i.previous();
		assertEquals(0, i.nextIndex());
	}
	
	@Test
	public void ALI_previousIndexTest()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		i = list.listIterator();
		assertEquals(-1, i.previousIndex());
		i.next();
		assertEquals(0, i.previousIndex());
		i.previous();
		assertEquals(-1, i.previousIndex());
	}
	
	@Test
	public void ALI_nextExceptions()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		i = list.listIterator();
		i.next();i.next();i.next();i.next(); //move it to end
		boolean thrown = false;
		try {
			i.next();
		} catch (NoSuchElementException exception) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public void ALI_hasPreviousTest()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		i = list.listIterator();
		assertFalse(i.hasPrevious());
		i.next();
		assertTrue(i.hasPrevious());
	}
	
	@Test
	public void ALI_previousTest()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		i = list.listIterator();
		i.next();
		i.next();
		assertEquals("420", i.previous());
	}
	
	public void ALI_previousExceptions()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		i = list.listIterator();
		boolean thrown = false;
		try {
			i.previous();
		} catch (NoSuchElementException exception) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public void ALI_addTest()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		i = list.listIterator();
		i.add("luigi");
		assertEquals("luigi", list.head.next.data);
		i.next();
		i.add("mario");
		assertEquals("mario", list.head.next.next.next.data);
		assertEquals(6, list.size());
	}
	
	@Test
	public void ALI_removeTest()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		i = list.listIterator();
		i.next();
		i.remove();
		assertEquals("420", list.head.next.data);
		assertEquals(3, list.size());
	}
	
	@Test
	public void ALI_removeTest2()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		i = list.listIterator();
		i.next();
		i.next();
		i.previous();
		i.remove();
		assertEquals("shrek", list.head.next.next.data);
		assertEquals(3, list.size());
	}
	
	@Test
	public void ALI_removeExceptions()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		i = list.listIterator();
		boolean thrown = false;
		boolean thrown2 = false;
		try
		{
			i.remove();
		} catch (IllegalStateException exception)
		{
			thrown = true;
		}
		assertTrue(thrown);
		i.next();
		i.next();
		i.remove();
		try
		{
			i.remove();
		} catch (IllegalStateException exception)
		{
			thrown2 = true;
		}
		assertTrue(thrown2);
	}
	
	@Test
	public void ALI_setTest()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		i = list.listIterator();
		i.next();
		i.set("memum");
		assertEquals("memum", list.head.next.data);
	}
	
	@Test
	public void ALI_setTest2()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		i = list.listIterator();
		i.next();
		i.next();
		i.previous();
		i.set("430");
		assertEquals("430", list.head.next.next.data);
	}
	
	@Test
	public void ALI_setExceptions()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		i = list.listIterator();
		boolean thrown = false;
		try {
			i.set("luigi");
		} catch (IllegalStateException exception)
		{
			thrown = true;
		}
		assertTrue(thrown);
	}

	@Test
	public void ALI_positionsConstructorTest()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		i = list.listIterator(2);
		assertEquals("shrek", i.next());
	}
	
	@Test
	public void ALI_positionsConstructorTest2()
	{
		collection = Arrays.asList("urmum", "420", "shrek", "ogres");
		list = new AdaptiveList<>(collection);
		i = list.listIterator(4);
		assertEquals("ogres", i.previous());
	}

	

}
