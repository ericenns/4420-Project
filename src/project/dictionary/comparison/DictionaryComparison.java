package project.dictionary.comparison;

import java.util.Hashtable;
import java.util.Random;

import project.dictionary.comparison.tree.BitwiseTrie;

public class DictionaryComparison 
{
	public static void main(String[] argv) 
	{
		BitwiseTrie trie = new BitwiseTrie(255);
		
		int sequence[] = generateSequence(1000000, Integer.MAX_VALUE);
		
//		for (int i=0; i<10; i++) {
//			trie.insert(sequence[i]);
//		}
		
		trie.insert(3);
		trie.insert(202);
		trie.insert(227);
		trie.insert(145);
		trie.insert(14);
		trie.insert(111);
		trie.insert(13);
		trie.insert(185);
		trie.insert(76);
		trie.insert(98);
	/*
		boolean result = trie.search(3);
		if( result )
			System.out.println("Search key found");
		else
			System.out.println("Search key not found!");
		
		trie.delete(3);
		result = trie.search(3);
		if( result )
			System.out.println("Search key found");
		else
			System.out.println("Search key not found!");
		*/
		
		//trie.print();
		
		
		String r;
		r = trie.predecessor(110);
		System.out.println("Predecessor: " + r + " : " + Integer.parseInt(r, 2));
		
		System.out.println("end of processing.");
		
		
	}
	
	/*
	 * Returns an integer array of size n where each element is unique.
	 */
	private static int[] generateSequence(int n, int max) {
		int[] sequence = new int[n];
		boolean inserted = false;
//		Hashtable inserted = new Hashtable();
		int i = 0;
		int toInsert;
		Random generator = new Random();
		
		while (i<n) {
			toInsert = generator.nextInt(max);
			
			// check if array contains value already
			inserted = false;
			for (int j=0; j<i && inserted==false; j++) {
				if (sequence[i] == toInsert) {
					inserted = true;
				}
			}
			if (!inserted) {
//			if (!inserted.containsKey(toInsert)) {
				sequence[i] = toInsert;
				i++;
			}
		}
		
		return sequence;
	}
}
