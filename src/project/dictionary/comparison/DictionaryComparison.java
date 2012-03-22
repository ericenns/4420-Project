package project.dictionary.comparison;

import project.dictionary.comparison.tree.BitwiseTrie;

public class DictionaryComparison 
{
	public static void main(String[] argv) 
	{
		BitwiseTrie trie = new BitwiseTrie(255);
		
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
}
