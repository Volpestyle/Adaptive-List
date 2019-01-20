package edu.iastate.cs228.proj3;

public class main {

	public static void main(String[] args) {
		AdaptiveList<String> seq = new AdaptiveList<String>();
		seq.add("B");
		seq.add("A");
		seq.add("C");
		System.out.println("After the three seq.add() operations:");
		System.out.println("linkedUTD: " + seq.getlinkedUTD());
		System.out.println("arrayUTD: " + seq.getarrayUTD());
		System.out.println(seq.toString());
		System.out.println( seq.get(1) );
		System.out.println("After the seq.get(1) operation:");
		System.out.println("linkedUTD: " + seq.getlinkedUTD());
		System.out.println("arrayUTD: " + seq.getarrayUTD());
		System.out.println(seq.toString());
		System.out.println( seq.set(1, "D") );
		System.out.println("After the seq.set(1, 'D') operation:");
		System.out.println("linkedUTD: " + seq.getlinkedUTD());
		System.out.println("arrayUTD: " + seq.getarrayUTD());
		System.out.println(seq.toString());
		seq.add("E");
		System.out.println("After the seq.add('E') operation:");
		System.out.println("linkedUTD: " + seq.getlinkedUTD());
		System.out.println("arrayUTD: " + seq.getarrayUTD());
		System.out.println(seq.toString());
	}

}
