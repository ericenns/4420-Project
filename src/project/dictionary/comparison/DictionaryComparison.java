package project.dictionary.comparison;

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
	
	public static void main(String[] argv) 
	{
		int sequence[];
		long startTime;
		long endTime;
		
		sequence = generateSequence(25000, Integer.MAX_VALUE);
		
		for (int i=0; i<3; i++) {
			startTime = System.nanoTime();
			testInsert(i, sequence);
			endTime = System.nanoTime();
			System.out.println(endTime - startTime);
		}

		System.out.println("End of Processing...");
	}
	
	private static void testInsert(int dictionary, int[] sequence) {
		Object structure;
		
		switch (dictionary) {
		case LIST:
			structure = new SkipList(sequence.length);
			break;
		case BITWISETRIE:
			structure = new BitwiseTrie(Integer.MAX_VALUE-1);
			break;
		case XFASTTRIE:
			structure = new XFastTrie(Integer.MAX_VALUE-1);
			break;
		default:
			return;
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
