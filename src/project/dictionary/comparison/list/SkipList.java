package project.dictionary.comparison.list;

public class SkipList {
	private SkipListNode head;
	
	public SkipList(int levels) {
		
	}
	
	public boolean search(int key) {
		return true;
	}
	
	public void insert(int key) {
		
	}
	
	public void delete(int key) {
		
	}
	
	public int predecessor(int key) {
		return 1;
	}
}

class SkipListNode {
	private SkipListNode[] next;
	private final int key;
	
	public SkipListNode(int key, int levels) {
		this.key = key;
		this.next = new SkipListNode[levels];
	}

	public int getKey() {
		return this.key;
	}
	
	public void setNext(SkipListNode next, int level) {
		this.next[level] = next;
	}
	
	public int getHeight() {
		return next.length;
	}
	
	public SkipListNode getNext(int level) {
		return next[level];
	}
	
	public int compareKeys(int key) {
		return this.key - key;
	}
}