package project.dictionary.comparison.tree;

public class BitwiseTrie extends Tree 
{
	Node root;
	int numLevels; // number of levels in the trie
	
	public BitwiseTrie( int u )
	{
		root = new Node(-1, null, null, null);
		
		numLevels = (int)Math.ceil(Math.log(u)/Math.log(2));
		System.out.println(numLevels);
	}

	public boolean search( int key ) 
	{
		int[] keyArray = calcKeyBitString(key);
		
		boolean found = false;
		Node currNode = root;
		
		for(int i = 0; i < keyArray.length && currNode != null; i++)
		{
			currNode = currNode.getChild(keyArray[i] == 0);
		}
		
		if( currNode != null )
		{
			found = true;
		}
		
		return found;
	}

	public void insert( int key ) 
	{
		int[] keyArray = calcKeyBitString(key);
		
		Node currNode = root;
		
		for(int i = 0; i < keyArray.length; i++)
		{
			if( currNode.getChild(keyArray[i] == 0) == null )
			{	
				Node childNode;
				if( i == keyArray.length - 1 )
					childNode = new LeafNode(keyArray[i], key, currNode, null, null);
				else
					childNode = new Node(keyArray[i], currNode, null, null);
				currNode.setChild(childNode, keyArray[i] == 0);
			}
			currNode = currNode.getChild(keyArray[i] == 0);
		}
	}
	
	private int[] calcKeyBitString( int key )
	{
		char[] tempArray;
		tempArray = Integer.toBinaryString(key).toCharArray();

		int[] keyArray = new int[numLevels];
		
		for( int i = 0; i  < keyArray.length - tempArray.length; i++ )
		{
			keyArray[i] = 0;
		}
		
		for( int i = 0; i < tempArray.length; i++ )
		{
			keyArray[keyArray.length - tempArray.length + i] = Integer.parseInt(""+tempArray[i]);
		}
		
		return keyArray;
	}
	
	
	public void delete( int key ) 
	{
		int[] keyArray = calcKeyBitString(key);
		
		Node currNode = root;
		
		for(int i = 0; i < keyArray.length && currNode != null; i++)
			currNode = currNode.getChild(keyArray[i] == 0);
		
		if( currNode != null )
		{
			while(currNode.parent.getNumChildren() == 1 && currNode.getParent() != root)
				currNode = currNode.getParent();

			currNode.parent.setChild(null, currNode.getKey() == 0);
		}
	}
	
	public int predecessor( int key ) 
	{
		int[] keyArray = calcKeyBitString(key);
		int returnVal;
		
		boolean found = false;
		Node currNode = root;
		
		for(int i = 0; (i < keyArray.length) && (currNode.getChild(keyArray[i] == 0) != null); i++)
		{
			currNode = currNode.getChild(keyArray[i] == 0);
		}
		
		if( currNode instanceof LeafNode && ((LeafNode)currNode).getLeafKey() == key )
		{			
			returnVal = key;
		}
		else
		{
			if( currNode.getChild(true) == null )
			{
				//find a parent node that has a left child in order to find the predecessor
				if(currNode != root ) 
				{
					// find a node that is the right child of it's parent
					while( currNode.getKey() != 1 && currNode.getKey()!= -1  )
					{
						currNode = currNode.getParent();
					}
					
					currNode = currNode.getParent();
					
					boolean finished = false;
					while(!finished)
					{
						if( currNode == root)
						{
							finished = true;
						}
						else
						{
							if( currNode.getChild(true) == null )
							{
								currNode = currNode.getParent();
								if( currNode.getChild(true) == null )
								{
									while( currNode.getKey() != 1 )
									{
										currNode = currNode.getParent();
									}
									currNode = currNode.getParent();
								}
							}
							else
								finished = true;
						}
					}
				}
			}
			
			if( currNode == root && currNode.getChild(true) == null )
			{
				returnVal = -1;
			}
			else
			{
				currNode = currNode.getChild(true);
				
				while(currNode.getNumChildren() != 0)
				{
					if( currNode.getChild(false) != null )
						currNode = currNode.getChild(false);
					else
						currNode = currNode.getChild(true);
				}
				
				returnVal = ((LeafNode)currNode).getLeafKey();
			}
		}
		
		return returnVal;
	}
}
