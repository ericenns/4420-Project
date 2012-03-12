package project.dictionary.comparison.tree;

public class Node {
	protected Node leftChild;
	protected Node rightChild;
	protected Node parent;
	protected int key;
	
	public Node(int key) {
		this.key = key;
		leftChild = null;
		rightChild = null;
		parent = null;
	}
	
	public Node(int key, Node parent, Node leftChild, Node rightChild) {
		this.key = key;
		this.parent = parent;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
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
	
	public Node getChild(Boolean left) {
		if (left)
			return leftChild;
		else
			return rightChild;
	}
	
	public Node getParent()
	{
		return parent;
	}
	
	public int getNumChildren()
	{
		int num = 0;
		if( leftChild != null )
			num++;
		if( rightChild != null )
			num++;
		
		return num;
	}
}
