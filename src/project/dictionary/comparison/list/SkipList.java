package project.dictionary.comparison.list;

import java.util.Random;

public class SkipList {
	private SkipListNode head;
	private final int levels;
	private Random generator;
	
	public SkipList(int levels) {
		SkipListNode tail = new SkipListNode(-1, levels);
		
		this.levels = levels;
		this.head = new SkipListNode(-1, levels);
		this.generator = new Random();
		
		for (int i=0; i<levels; i++) {
			head.setNext(tail, i);
		}
	}
	
	public boolean search(int key) {
		return true;
	}
	
	public void insert(int key) {
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
	}
	
	public void delete(int key) {
		
	}
	
	public int predecessor(int key) {
		return 1;
	}
	
	public boolean isEmpty() {
		if (head.getNext(0).isTail()) {
			return true;
		} else {
			return false;
		}
	}
}