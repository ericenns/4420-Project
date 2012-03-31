package project.dictionary.comparison.xfasttrie;

import java.util.HashMap;

import project.dictionary.comparison.tree.Node;
import project.dictionary.comparison.tree.Tree;

public class XFastTrie extends Tree
{
	XFastNode root;
	int numLevels;
	XFastNode leftMostLeaf;
	XFastNode rightMostLeaf;
	HashMap hashTable;
	
	public HashMap getHashTable()
	{
		return hashTable;
	}
	
	public XFastNode getRoot()
	{
		return root;
	}
	
	public XFastTrie( int u )
	{
		root = new XFastNode("-1", null, null, null, null);
		numLevels = (int)Math.ceil(Math.log(u)/Math.log(2));
		leftMostLeaf = new XFastNode("-2", null, null, null, null);
		rightMostLeaf = new XFastNode("-3", null, null, null, null);
		leftMostLeaf.setChild(rightMostLeaf, false);
		rightMostLeaf.setChild(leftMostLeaf, true);
		
		hashTable = new HashMap();
	}
	
	public int search( int key )
	{
		return predecessor( key );
	}
	
	public int predecessor( int key )
	{
		String keyString = calcKeyBitString(key);
		int numOperations = 0;
		
		int low = 0;
		int high = numLevels;
		int mid = 0;
		XFastNode tempNode = root;
		
		// binary search through levels of the trie
		while( high - low > 1 )
		{
			mid = (int) Math.floor((high + low)/2);

			if( hashTable.get(keyString.substring(0,mid+1)) != null )
			{
				low = mid;
				tempNode = (XFastNode) hashTable.get(keyString.substring(0,mid+1));
			}
			else
			{
				high = mid;
				mid = mid-1;
			}
			
			numOperations++;
		}
		
		// if tempNode is still unchanged, attempt to change it using the current mid val
		if( tempNode == root )
		{
			if( (XFastNode) hashTable.get(keyString.substring(0,mid+1)) != null )
				tempNode =(XFastNode) hashTable.get(keyString.substring(0,mid+1));
			numOperations++;
		}
			
		String result;
		
		if( tempNode.getKey().equals(keyString) )
		{
			result = keyString;
			numOperations++;
		}
		else if( tempNode.getChild(true) == null )
		{
			result = tempNode.getDescendant().getChild(true).getKey();
			numOperations++;
		}
		else if( tempNode.getChild(false) == null )
		{
			result = tempNode.getDescendant().getKey();
			numOperations++;
		}

		return numOperations;
	}
	
	public int insert( int key )
	{
		String keyString = calcKeyBitString(key);
		int numOperations = 0;

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
					numOperations++;
				}

				XFastNode childNode = new XFastNode(keyString.substring(0,i + 1), currNode, null, null, null);
				currNode.setChild(childNode, keyString.charAt(i) == '0');
				numOperations++;
				
				hashTable.put(keyString.substring(0,i+1), childNode);
				numOperations++;
			}
			
			currNode = (XFastNode) currNode.getChild(keyString.charAt(i) == '0');
			numOperations++;
		}
		
		if(predecessor == null)
			predecessor = leftMostLeaf;
		
		// link in the new leaf node amongst the doubly linked leaves
		currNode.setChild(predecessor, true);
		currNode.setChild(predecessor.getChild(false), false);
		currNode.getChild(true).setChild(currNode, false);
		currNode.getChild(false).setChild(currNode, true);
		numOperations = numOperations + 4;
		
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
				numOperations++;
			}
			
			currNode = (XFastNode) currNode.getParent();
			numOperations++;
		}
		
		return numOperations;
	}
	
	public int delete( int key )
	{
		String keyString = calcKeyBitString(key);
		int numOperations = 0;
		
		// Find the node to be deleted, if it exists
		XFastNode nodeToDelete = (XFastNode) hashTable.get(keyString);
		numOperations++;

		// do work that is needed to fully delete the node from the trie
		if( nodeToDelete != null )
		{
			// Remove the node to be deleted from the leaf linked list
			nodeToDelete.getChild(true).setChild(nodeToDelete.getChild(false),false);
			nodeToDelete.getChild(false).setChild(nodeToDelete.getChild(true),true);
			
			XFastNode prevNode = (XFastNode) nodeToDelete;
			XFastNode currNode = (XFastNode) nodeToDelete.getParent();
			
			hashTable.remove(nodeToDelete.getKey());
			numOperations++;

			// work your way up the tree deleting the appropriate nodes
			while(currNode.getNumChildren() == 1 && currNode != root)
			{
				// remove the nodes that are going to deleted from the hash table
				hashTable.remove(currNode.getKey());
				numOperations++;

				if( currNode.getKey().charAt(currNode.getKey().length()-1) == '0' )
					((XFastNode)currNode.getParent()).setDescendant((XFastNode) nodeToDelete.getChild(false));
				else
					((XFastNode)currNode.getParent()).setDescendant((XFastNode) nodeToDelete.getChild(true));
				numOperations++; 

				prevNode = (XFastNode) currNode;
				currNode = (XFastNode) currNode.getParent();
				numOperations++;
			}
			
			currNode.setChild(null, prevNode.getKey().charAt(prevNode.getKey().length()-1) == '0');
			numOperations++;
			
			currNode = (XFastNode) nodeToDelete.getParent();
				
			// work your way up the tree updating descendant pointers
			while( currNode != root)
			{
				if( currNode.getDescendant() == nodeToDelete )
				{ 
					if( currNode.getChild(true) == null )
						currNode.setDescendant((XFastNode) nodeToDelete.getChild(false));
					else if( currNode.getChild(false) == null )
						currNode.setDescendant((XFastNode) nodeToDelete.getChild(true));
					
					numOperations++;
				}
				
				currNode = (XFastNode) currNode.getParent();
				numOperations++;
			}
		}
		return numOperations;
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
