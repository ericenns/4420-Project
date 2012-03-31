package project.dictionary.comparison.list;

import java.util.Random;

public class SkipList {
	private SkipListNode head;
	private SkipListNode tail;
	private final int levels;
	private Random generator;
	
	public SkipList(int levels) {
		this.levels = levels;
		this.head = new SkipListNode(-1, levels);
		this.tail = new SkipListNode(-1, levels);
		this.generator = new Random();
		
		for (int i=0; i<levels; i++) {
			head.setNext(tail, i);
		}
	}
	
	public int search(int key) {
		int comparisons = 0;
		SkipListNode curr = head;
		boolean found = false;
		boolean done;
		int compare;
		
		for (int i=levels-1; i>=0 && !found; i--) {
			done = false;
			while (!done) {
				if (curr.getNext(i).getKey() > -1) {
					compare = curr.getNext(i).compareKeys(key);
					comparisons++;
					if (compare >= 0) {
						done = true;
						if (compare == 0) {
							found = true;
						}
					} else {
						curr = curr.getNext(i);
					}
				} else {
					done = true;
				}
			}
		}
		
		return comparisons;
	}
	
	public int insert(int key) {
		int comparisons = 0;
		SkipListNode[] prev = new SkipListNode[levels];
		SkipListNode curr;
		SkipListNode newNode;
		int newNodeLevels = 1;
		boolean done;
		
		while (generator.nextInt(2) == 1 && newNodeLevels < levels) {
			newNodeLevels++;
		}
		
		newNode = new SkipListNode(key, newNodeLevels);
		
		if (isEmpty()) {
			for (int i=0; i<newNodeLevels; i++) {
				newNode.setNext(head.getNext(i), i);
				head.setNext(newNode, i);
			}
		} else {
			for(int i=0; i<levels; i++) {
				prev[i] = head;
			}
			
			curr = head;
			
			// set previous nodes for each level
			for (int i=newNodeLevels-1; i>=0; i--) {
				done = false;
				while (!done) {
					if (curr.getNext(i).getKey() > -1) {
						if (curr.getNext(i).compareKeys(key) >= 0) {
							done = true;
						} else {
							for (int j=0; j<curr.getHeight(); j++) {
								prev[j] = curr;
							}
							curr = curr.getNext(i);
						}
						comparisons++;
					} else {
						done = true;
					}
				}
			}
			
			// insert the newNode
			for (int i=0; i<newNodeLevels; i++) {
				if (i < curr.getHeight()) {
					newNode.setNext(curr.getNext(i), i);
					curr.setNext(newNode, i);
				} else {
					newNode.setNext(prev[i].getNext(i), i);
					prev[i].setNext(newNode, i);
				}
			}
		}
		
		return comparisons;
	}
	
	public int delete(int key) {
		int comparisons = 0;
		SkipListNode[] prev = new SkipListNode[levels];
		SkipListNode curr = head;
		SkipListNode toDelete = null;
		boolean done;
		int compare;
		
		for(int i=0; i<levels; i++) {
			prev[i] = head;
		}
		
		for (int i=levels-1; i>=0; i--) {
			done = false;
			while (!done) {
				if (curr.getNext(i).getKey() > -1) {
					compare = curr.getNext(i).compareKeys(key);
					comparisons++;
					if (compare >= 0) {
						done = true;
						if (compare == 0) {
							toDelete = curr.getNext(i);
						}
					} else {
						for (int j=0; j<curr.getHeight(); j++) {
							prev[j] = curr;
						}
						curr = curr.getNext(i);
					}
				} else {
					done = true;
				}
			}
		}
		
		if (toDelete != null) {
			for (int i=0; i<toDelete.getHeight(); i++) {
				prev[i].setNext(toDelete.getNext(i), i);
			}
		}
		
		return comparisons;
	}
	
	public int predecessor(int key) {
		int comparisons = 0;
		SkipListNode[] prev = new SkipListNode[levels];
		SkipListNode curr = head;
		SkipListNode pred = null;
		boolean done;
		int compare;
		
		for(int i=0; i<levels; i++) {
			prev[i] = head;
		}
		
		for (int i=levels-1; i>=0; i--) {
			done = false;
			while (!done) {
				if (curr.getNext(i).getKey() > -1) {
					compare = curr.getNext(i).compareKeys(key);
					comparisons++;
					if (compare >= 0) {
						done = true;
						if (compare == 0) {
							pred = curr.getNext(i);
						}
					} else {
						for (int j=0; j<curr.getHeight(); j++) {
							prev[j] = curr;
						}
						curr = curr.getNext(i);
					}
				} else {
					done = true;
				}
			}
		}
		
//		if (pred != null) {
//			return pred.getKey();
//		} else {
//			return prev[0].getKey();
//		}
//		
		return comparisons;
	}
	
	public boolean isEmpty() {
		if (head.getNext(0).isTail()) {
			return true;
		} else {
			return false;
		}
	}
}