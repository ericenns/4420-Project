package project.dictionary.comparison.tree;

public class Node {
	protected Node leftChild;
	protected Node rightChild;
	protected int key;
	
	public Node(int key) {
		this.key = key;
		leftChild = null;
		rightChild = null;
	}
	
	public Node(int key, Node left, Node right) {
		this.key = key;
		leftChild = left;
		rightChild = right;
	}
	
	public int getKey() {
		return key;
	}
	
	public void setChild(Node node, Boolean left) {
		if (left)
			leftChild = node;
		else
			rightChild = node;
	}
	
	public Node getChild(Node node, Boolean left) {
		if (left)
			return leftChild;
		else
			return rightChild;
	}
}
