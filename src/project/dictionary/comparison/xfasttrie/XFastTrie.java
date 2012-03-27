package project.dictionary.comparison.xfasttrie;

import project.dictionary.comparison.tree.Node;
import project.dictionary.comparison.tree.Tree;

public class XFastTrie extends Tree
{
	XFastNode root;
	int numLevels;
	Node[][] hashTable;
	XFastNode leftMostLeaf;
	XFastNode rightMostLeaf;
	
	public Node[][] getHashTable()
	{
		return hashTable;
	}
	
	public XFastTrie( int u )
	{
		root = new XFastNode("-1", null, null, null, null);
		numLevels = (int)Math.ceil(Math.log(u)/Math.log(2));
		System.out.println("NumLevels: " + numLevels);
		leftMostLeaf = new XFastNode("-2", null, null, null, null);
		rightMostLeaf = new XFastNode("-3", null, null, null, null);
		leftMostLeaf.setChild(rightMostLeaf, false);
		rightMostLeaf.setChild(leftMostLeaf, true);
		
		hashTable = new Node[numLevels][u+1];
		//System.out.println(numLevels + " x " + (u+1));
	}
	
	public XFastNode getRoot()
	{
		return root;
	}
	
	public boolean search( int key )
	{
		return Integer.parseInt(predecessor(key),2) == key;
	}
	
	public String predecessor( int key )
	{
		String keyString = calcKeyBitString(key);
		int low = 0;
		int high = numLevels;
		int mid = 0;
		XFastNode tempNode = root;
		
		while( high - low > 1 )
		{
			mid = (int) Math.floor((high + low)/2);
			if( hashTable[mid][Integer.parseInt(keyString.substring(0,mid+1),2)] != null )
			{
				low = mid;
				tempNode = (XFastNode) hashTable[mid][Integer.parseInt(keyString.substring(0,mid+1),2)];	
			}
			else
			{
				high = mid;
				mid = mid-1;
			}
		}

		if( hashTable[mid][Integer.parseInt(keyString.substring(0,mid+1),2)].getKey().equals(keyString) )
		{
			return keyString;
		}
		
		if( hashTable[mid][Integer.parseInt(keyString.substring(0,mid+1),2)].getChild(true) == null )
		{
			return ((XFastNode)hashTable[mid][Integer.parseInt(keyString.substring(0,mid+1),2)]).getDescendant().getChild(true).getKey();
		}
		
		if(hashTable[mid][Integer.parseInt(keyString.substring(0,mid+1),2)].getChild(false) == null )
		{
			return ((XFastNode)hashTable[mid][Integer.parseInt(keyString.substring(0,mid+1),2)]).getDescendant().getKey();
		}
		
		return "";
	}
	
	public void insert( int key )
	{
		String keyString = calcKeyBitString(key);

		XFastNode currNode = root;
		XFastNode predecessor = null;
		
		// insert the new node
		for( int i = 0; i < keyString.length(); i++ )
		{
			if( currNode.getChild(keyString.charAt(i) == '0') == null )
			{	
				if( currNode.getDescendant() != null )
				{
					if( keyString.charAt(i) == '1' )
						predecessor = currNode.getDescendant();
					else
						predecessor = (XFastNode) currNode.getDescendant().getChild(true);
				}

				XFastNode childNode = new XFastNode(keyString.substring(0,i + 1), currNode, null, null, null);
				currNode.setChild(childNode, keyString.charAt(i) == '0');
				//System.out.println("Teehee" + Integer.parseInt(keyString.substring(0,i+1),2));
				hashTable[i][Integer.parseInt(keyString.substring(0,i+1),2)] = childNode;
			}
			currNode = (XFastNode) currNode.getChild(keyString.charAt(i) == '0');
		}
		
		if(predecessor == null)
			predecessor = leftMostLeaf;
		
		// link in the new leaf node amongst the doubly linked leaves
		currNode.setChild(predecessor, true);
		currNode.setChild(predecessor.getChild(false), false);
		currNode.getChild(true).setChild(currNode, false);
		currNode.getChild(false).setChild(currNode, true);
		
		// update descendant pointers appropriately
		XFastNode newNode = currNode;
		
		currNode = (XFastNode) currNode.getParent();
		while( currNode != null )
		{
			XFastNode descendant = currNode.getDescendant();
			if( (currNode.getChild(true) == null && (descendant == null || 
					Integer.parseInt(descendant.getKey(),2) > Integer.parseInt(newNode.getKey(),2)))
				||
				(currNode.getChild(false) == null && (descendant == null || 
						Integer.parseInt(descendant.getKey(),2) < Integer.parseInt(newNode.getKey(),2))) )
			{
				currNode.setDescendant(newNode);
			}
			
			currNode = (XFastNode) currNode.getParent();
		}
	}
	
	public void delete( int key )
	{
		String keyString = calcKeyBitString(key);
		
		Node currNode = root;
		
		// Find the node to be deleted, if it exists
		for(int i = 0; i < keyString.length() && currNode != null; i++)
			currNode = currNode.getChild(keyString.charAt(i) == '0');

		// delete nodes on the path to the node to be deleted
		if( currNode != null )
		{
			// Remove the node to be deleted from the leaf linked list
			currNode.getChild(true).setChild(currNode.getChild(false),false);
			currNode.getChild(false).setChild(currNode.getChild(true),true);
			
			XFastNode nodeToDelete = (XFastNode) currNode;
			
			XFastNode prevNode = (XFastNode) currNode;
			currNode = currNode.getParent();
			hashTable[prevNode.getKey().length()-1][Integer.parseInt(currNode.getKey(),2)] = null;
			
			// work your way up the tree deleting the appropriate nodes
			while(currNode.getNumChildren() == 1 && currNode.getParent() != root)
			{
				// remove the nodes that are going to deleted from the hash table
				hashTable[currNode.getKey().length()-1][Integer.parseInt(currNode.getKey(),2)] = null;

				if( currNode.getKey().charAt(currNode.getKey().length()-1) == '0' )
					((XFastNode)currNode.getParent()).setDescendant((XFastNode) nodeToDelete.getChild(false));
				else
					((XFastNode)currNode.getParent()).setDescendant((XFastNode) nodeToDelete.getChild(true));

				prevNode = (XFastNode) currNode;
				currNode = currNode.getParent();

			}
			currNode.setChild(null, prevNode.getKey().charAt(prevNode.getKey().length()-1) == '0');
		}
	}

	private String calcKeyBitString( int key )
	{
		String keyString = Integer.toBinaryString(key);

		String temp = "";
		for(int i = 0; i < numLevels - keyString.length(); i++)
		{
			temp = temp + "0";
		}

		keyString = temp + keyString;
		
		return keyString;
	}
}
