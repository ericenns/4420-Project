package project.dictionary.comparison;

import project.dictionary.comparison.tree.BitwiseTrie;

public class DictionaryComparison 
{
	public static void main(String[] argv) 
	{
		BitwiseTrie trie = new BitwiseTrie(7);
		
		trie.insert(3);
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
	}
}
