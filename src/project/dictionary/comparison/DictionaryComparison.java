package project.dictionary.comparison;

import project.dictionary.comparison.bitwisetrie.BitwiseTrie;
import project.dictionary.comparison.tree.Node;
import project.dictionary.comparison.xfasttrie.XFastNode;
import project.dictionary.comparison.xfasttrie.XFastTrie;

public class DictionaryComparison 
{
	public static void main(String[] argv) 
	{
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
}
