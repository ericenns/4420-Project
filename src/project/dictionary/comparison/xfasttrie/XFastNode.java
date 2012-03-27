package project.dictionary.comparison.xfasttrie;

import project.dictionary.comparison.tree.Node;

public class XFastNode extends Node 
{
	XFastNode descendant;
	
	public XFastNode(String key) {
		super(key);
	}
	
	public XFastNode(String key, Node parent, Node leftChild, Node rightChild, Node descendant) {
		super(key, parent, leftChild, rightChild);
		this.descendant = (XFastNode) descendant;
	}
	
	public void setDescendant( XFastNode descendant )
	{
		this.descendant = descendant;	
	}
	
	public XFastNode getDescendant()
	{
		return descendant;
	}
}
