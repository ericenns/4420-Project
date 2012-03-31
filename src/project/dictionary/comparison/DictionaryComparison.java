package project.dictionary.comparison;

import java.util.Random;

import project.dictionary.comparison.bitwisetrie.BitwiseTrie;
import project.dictionary.comparison.list.SkipList;
import project.dictionary.comparison.xfasttrie.XFastTrie;

public class DictionaryComparison 
{
	public static final int LIST=0;
	public static final int BITWISETRIE=1;
	public static final int XFASTTRIE=2;
	public static final int u=1073741824;
	
	public static void main(String[] argv) 
	{
		int[] insertSequence;
		int[] searchSequence;
		int[] predecessorSequence;
		int[] deleteSequence;
		Object[] structure = new Object[3];
		int[] n = {25000, 50000, 75000, 100000};
		String[][] results = new String[4][4];
		String[] operations = {"Insert", "Search", "Predecessor", "Delete"};
		
		for(int j=0; j<4; j++) {
			insertSequence = generateSequence(n[j], u);
			searchSequence = generateSequence(n[j]/2, u);
			predecessorSequence = generateSequence(n[j]/2, u);
			deleteSequence = generateSequence(n[j]/2, u);
			
			results[0][j] = "" + n[j];
			for (int i=0; i<3; i++) {
				switch (i) {
				case LIST:
					structure[i] = new SkipList(insertSequence.length);
					break;
				case BITWISETRIE:
					structure[i] = new BitwiseTrie(u-1);
					break;
				case XFASTTRIE:
					structure[i] = new XFastTrie(u-1);
					break;
				default:
					break;
				}
				results[0][j] += "\t" + testInsert(i, structure[i], insertSequence);
			}
			results[1][j] = "" + n[j];
			for (int i=0; i<3; i++) {
				results[1][j] += "\t" + testSearch(i, structure[i], searchSequence);
			}
			results[2][j] = "" + n[j];
			for (int i=0; i<3; i++) {
				results[2][j] += "\t" + testPredecessor(i, structure[i], predecessorSequence);
			}
			results[3][j] = "" + n[j];
			for (int i=0; i<3; i++) {
				results[3][j] += "\t" + testDelete(i, structure[i], deleteSequence);
			}
		}
		
		for (int i=0; i<4; i++) {
			System.out.println(operations[i]);
			for (int j=0; j<4; j++) {
				System.out.println(results[i][j]);
			}
		}

		System.out.println("End of Processing...");
	}
	
	private static int testInsert(int dictionary, Object structure, int[] sequence) {
		int comparisons = 0;
		
		for (int i=0; i<sequence.length; i++) {
			switch (dictionary) {
			case LIST:
				comparisons += ((SkipList) structure).insert(sequence[i]);
				break;
			case BITWISETRIE:
				comparisons += ((BitwiseTrie) structure).insert(sequence[i]);
				break;
			case XFASTTRIE:
				comparisons += ((XFastTrie) structure).insert(sequence[i]);
				break;
			default:
				break;
			}
		}
		
		return (int)Math.ceil((double)comparisons/(double)sequence.length);
	}
	
	public static int testSearch(int dictionary, Object structure, int[] sequence) {
		int comparisons = 0;
		
		for (int i=0; i<sequence.length; i++) {
			switch (dictionary) {
			case LIST:
				comparisons += ((SkipList) structure).search(sequence[i]);
				break;
			case BITWISETRIE:
				comparisons += ((BitwiseTrie) structure).search(sequence[i]);
				break;
			case XFASTTRIE:
				comparisons += ((XFastTrie) structure).search(sequence[i]);
				break;
			default:
				break;
			}
		}
		
		return (int)Math.ceil((double)comparisons/(double)sequence.length);
	}
	
	public static int testDelete(int dictionary, Object structure, int[] sequence) {
		int comparisons = 0;
		
		for (int i=0; i<sequence.length; i++) {
			switch (dictionary) {
			case LIST:
				comparisons += ((SkipList) structure).delete(sequence[i]);
				break;
			case BITWISETRIE:
				comparisons += ((BitwiseTrie) structure).delete(sequence[i]);
				break;
			case XFASTTRIE:
				comparisons += ((XFastTrie) structure).delete(sequence[i]);
				break;
			default:
				break;
			}
		}
		
		return (int)Math.ceil((double)comparisons/(double)sequence.length);
	}
	
	public static int testPredecessor(int dictionary, Object structure, int[] sequence) {
		int comparisons = 0;
		
		for (int i=0; i<sequence.length; i++) {
			switch (dictionary) {
			case LIST:
				comparisons += ((SkipList) structure).predecessor(sequence[i]);
				break;
			case BITWISETRIE:
				comparisons += ((BitwiseTrie) structure).predecessor(sequence[i]);
				break;
			case XFASTTRIE:
				comparisons += ((XFastTrie) structure).predecessor(sequence[i]);
				break;
			default:
				break;
			}
		}
		
		return (int)Math.ceil((double)comparisons/(double)sequence.length);
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
