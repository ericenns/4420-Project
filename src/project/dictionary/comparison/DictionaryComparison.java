package project.dictionary.comparison;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;

import project.dictionary.comparison.bitwisetrie.BitwiseTrie;
import project.dictionary.comparison.list.SkipList;
import project.dictionary.comparison.tree.Node;
import project.dictionary.comparison.xfasttrie.XFastNode;
import project.dictionary.comparison.xfasttrie.XFastTrie;

public class DictionaryComparison 
{
	public static final int LIST=0;
	public static final int BITWISETRIE=1;
	public static final int XFASTTRIE=2;
	public static final int u=262144;
	
	public static void main(String[] argv) 
	{
		XFastTrie trie = new XFastTrie(7);

		trie.insert(1);
		trie.insert(0);
		trie.insert(4);

	    //trie.delete(1);
		//trie.delete(0);
		
		HashMap map = trie.getHashTable();
		XFastNode currNode = trie.getRoot();
		
		if( map.get("00001") == null )
			System.out.println("blah");
		else
			System.out.println("TEST");
		
		System.out.println(trie.predecessor(3));
		//System.out.println( ((XFastNode) map.get("1")).getDescendant().getKey());
		
		//currNode = (XFastNode)map.get("11110");
		//System.out.println(currNode.getKey());
		//System.out.println(((XFastNode)currNode.getChild(true)).getKey());
		//System.out.println(((XFastNode)currNode.getChild(true).getChild(true)).getKey());
		//System.out.println(((XFastNode)currNode.getChild(true).getChild(true).getChild(true)).getKey());
		
		
		
		/*
		int[] insertSequence;
		int[] searchSequence;
		int[] predecessorSequence;
		int[] deleteSequence;
		Object[] structure = new Object[3];
		long startTime;
		long endTime;
		int[] n = {25000, 50000, 75000, 100000};
		
		for(int j=0; j<4; j++) {
			insertSequence = generateSequence(n[j], u);
			searchSequence = generateSequence(n[j]/2, u);
			predecessorSequence = generateSequence(n[j]/2, u);
			deleteSequence = generateSequence(n[j]/2, u);
			
			System.out.println(n[j] + " insert operations:");
			for (int i=0; i<3; i++) {
				startTime = System.nanoTime();
				structure[i] = testInsert(i, insertSequence);
				endTime = System.nanoTime();
				System.out.println(endTime - startTime);
			}
	
			System.out.println(n[j] + " search operations:");
			for (int i=0; i<3; i++) {
				startTime = System.nanoTime();
				testSearch(i, structure[i], searchSequence);
				endTime = System.nanoTime();
				System.out.println(endTime - startTime);
			}
	
			System.out.println(n[j] + " predecessor operations:");
			for (int i=0; i<3; i++) {
				startTime = System.nanoTime();
				testPredecessor(i, structure[i], predecessorSequence);
				endTime = System.nanoTime();
				System.out.println(endTime - startTime);
			}
	
			System.out.println(n[j] + " delete operations:");
			for (int i=0; i<3; i++) {
				startTime = System.nanoTime();
				testDelete(i, structure[i], deleteSequence);
				endTime = System.nanoTime();
				System.out.println(endTime - startTime);
			}
		}
		*/
		System.out.println("End of Processing...");
	}
	
	private static Object testInsert(int dictionary, int[] sequence) {
		Object structure;
		
		switch (dictionary) {
		case LIST:
			structure = new SkipList(sequence.length);
			break;
		case BITWISETRIE:
			structure = new BitwiseTrie(u-1);
			break;
		case XFASTTRIE:
			structure = new XFastTrie(u-1);
			break;
		default:
			return null;
		}
		
		for (int i=0; i<sequence.length; i++) {
			switch (dictionary) {
			case LIST:
				((SkipList) structure).insert(sequence[i]);
				break;
			case BITWISETRIE:
				((BitwiseTrie) structure).insert(sequence[i]);
				break;
			case XFASTTRIE:
				((XFastTrie) structure).insert(sequence[i]);
				break;
			default:
				break;
			}
		}
		
		return structure;
	}
	
	public static void testSearch(int dictionary, Object structure, int[] sequence) {
		for (int i=0; i<sequence.length; i++) {
			switch (dictionary) {
			case LIST:
				((SkipList) structure).search(sequence[i]);
				break;
			case BITWISETRIE:
				((BitwiseTrie) structure).search(sequence[i]);
				break;
			case XFASTTRIE:
				((XFastTrie) structure).search(sequence[i]);
				break;
			default:
				break;
			}
		}
	}
	
	public static void testDelete(int dictionary, Object structure, int[] sequence) {
		for (int i=0; i<sequence.length; i++) {
			switch (dictionary) {
			case LIST:
				((SkipList) structure).delete(sequence[i]);
				break;
			case BITWISETRIE:
				((BitwiseTrie) structure).delete(sequence[i]);
				break;
			case XFASTTRIE:
				((XFastTrie) structure).delete(sequence[i]);
				break;
			default:
				break;
			}
		}
	}
	
	public static void testPredecessor(int dictionary, Object structure, int[] sequence) {
		for (int i=0; i<sequence.length; i++) {
			switch (dictionary) {
			case LIST:
				((SkipList) structure).predecessor(sequence[i]);
				break;
			case BITWISETRIE:
				((BitwiseTrie) structure).predecessor(sequence[i]);
				break;
			case XFASTTRIE:
				((XFastTrie) structure).predecessor(sequence[i]);
				break;
			default:
				break;
			}
		}
	}
	
	/*
	 * Returns an integer array of size n where each element is unique.
	 */
	private static int[] generateSequence(int n, int max) {
		int[] sequence = new int[n];
		boolean inserted = false;
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
				sequence[i] = toInsert;
				i++;
			}
		}
		
		return sequence;
	}
}
