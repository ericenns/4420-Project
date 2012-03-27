package project.dictionary.comparison;

import java.util.Hashtable;
import java.util.Random;

import project.dictionary.comparison.bitwisetrie.BitwiseTrie;
import project.dictionary.comparison.tree.Node;
import project.dictionary.comparison.xfasttrie.XFastNode;
import project.dictionary.comparison.xfasttrie.XFastTrie;

public class DictionaryComparison 
{
	public static void main(String[] argv) 
	{
		int sequence[] = generateSequence(1000000, Integer.MAX_VALUE);
		XFastTrie trie = new XFastTrie(31);
		
		trie.insert(3);
		trie.insert(26);
		trie.insert(30);
		trie.insert(5);
		trie.insert(1);
		trie.insert(16);
		trie.insert(21);
		trie.insert(11);
		
		boolean result = trie.search(21);
		
		if(result)
			System.out.println("Search key found!");
		else
			System.out.println("Search key not found...");
		
		//Node[][] hashTable = trie.getHashTable();
		
		//System.out.println("Predecessor: " +  trie.predecessor(1) );
		/*
		XFastNode root = trie.getRoot();
		XFastNode currNode = (XFastNode) root.getChild(false);
		currNode = (XFastNode) currNode.getChild(true);
		currNode = (XFastNode) currNode.getChild(true);
		currNode = (XFastNode) currNode.getChild(true);
		currNode = (XFastNode) currNode.getChild(true);
		System.out.println(currNode.getKey());
		*/
		//System.out.println("BLAH: " + hashTable[1][2].getKey());
		
		//currNode = (XFastNode) currNode.getDescendant().getChild(false);
		//currNode = (XFastNode) currNode.getChild(false);
		
		//System.out.println(currNode.getKey());
		
		//trie.insert(6);
		//
		
		//System.out.println(hashTable[2][4].getKey());
		
		//trie.delete(4);
		//System.out.println(hashTable[0][1].getKey());
		System.out.println("End of Processing...");
		
		
		
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
