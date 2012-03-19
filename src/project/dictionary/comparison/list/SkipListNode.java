package project.dictionary.comparison.list;

public class SkipListNode {
	private SkipListNode[] next;
	private final int key;
	
	public SkipListNode(int key, int levels) {
		this.key = key;
		
		if (key == -1) {
			next = null;
		} else {
			this.next = new SkipListNode[levels];
		}
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
	
	public boolean isTail() {
		if (next == null && key == -1) {
			return true;
		} else {
			return false;
		}
	}
}
