/*package col106.assignment4.WeakAVLMap;*/
import java.util.Vector;
import java.util.Queue;
import java.util.LinkedList;

public class WeakAVLMap<K extends Comparable,V>/* implements WeakAVLMapInterface<K,V>*/{

	public WeakAVLMap(){

		// write your code here
	}
	public int rotate=0;
	public int height=0;
	public int check=0;
	public class Node{
		K key;
		V value;
		int rank;
		Node left,right,parent;
		Node(K key, V value){
			this.key=key;
			this.value=value;
			left=right=parent=null;
			rank=0;
		}
	}
	public Node root;
	public int getBalance(Node n){
		if(n!=null){
			return (n.rank-n.left.rank);
		}
		return 0;
	}
	
	public V put(K key, V value){
		// write your code her 
		V v=null;
		Node n;
		
		
		if(root==null){
			
			root=new Node(key,value);			
			root.parent=null;
			
		}
		else if(get(key)!=null){
			
			v=searchkey(root, key).value;
			
			searchkey(root, key).value=value;
		}
		else{
			
				
			
			if(root.key.compareTo(key)>0){
				n=leftend(root);
				Node t=root;
				while( t!=n){if(t.key.compareTo(key)<0&&t!=root){break;}
				else{
					t=t.left;
				}

				}
				
				n=t;
				if(n.key.compareTo(key)>0){
					check=-1;
					n.left=new Node(key,value);
					n.left.parent=n;
					n.rank++;
					balanceCheck(n);
				}
				else{
					n=rightend(n);
					if(n.key.compareTo(key)<0){
						check=1;
					n.right=new Node(key,value);
					n.rank++;
					n.right.parent=n;	
					
					balanceCheck(n);}
					else{
						check=-1;
					n.left=new Node(key,value);
					n.left.parent=n;
					n.rank++;
					balanceCheck(n);

					}
				}
			}
			else if(root.key.compareTo(key)<0  ){
				
				n=rightend(root);
				Node t=root;
				while( t!=n){
					if(t.key.compareTo(key)>0 && t!=root){
						break;}
				else{
					t=t.right;
					
				}

				}
				
				n=t;
				
				if(n.key.compareTo(key)<0){
					
					check=1;
					n.right=new Node(key,value);
					n.rank++;
					
					
					n.right.parent=n;				
					balanceCheck(n);
				}
				else{
					n=leftend(n);
					if(n.key.compareTo(key)>0){
					check=-1;
					n.left=new Node(key,value);
					n.left.parent=n;
					n.rank++;
					
					
					balanceCheck(n);}
					else{
						check=1;
					n.right=new Node(key,value);
					n.rank++;
					
					
					n.right.parent=n;				
					balanceCheck(n);

					}
				}

				
			}

		}
		return v;	
	}
	public Node leftend(Node n){
		while(n.left!=null){
			n=n.left;
		}		
		return n;
	}
	public Node rightend(Node n){
		while(n.right!=null){
			n=n.right;
		}		
		return n;
	}
	private void balanceCheck(Node x) {
		
		for (Node parent = x.parent; 
			parent != null && x.rank + 1 != parent.rank; x.rank++) {
			if (check==-1) { // new node was added on the left
				
			if (rotateright(parent)) {
				
				if (x.left == null || x.rank >= x.left.rank + 2) {
					
				leftRotate(x);
				}
				
				
				rightRotate(parent);
				break;
			}else if(rotateleft(parent)){
				
				rightRotate(x);
				leftRotate(parent);
				break;
			}
			
			
			} else if(check==1){
				
			if (rotateleft(parent)) {
				
				if (x.right == null || x.rank >= x.right.rank + 2) {
					
				rightRotate(x);
				}
				
				leftRotate(parent);
				break;
			}else if(rotateright(parent)){
				leftRotate(x);
				
				rightRotate(parent);
				break;
				
			}
			/*else if(x.right!=null){
				
				rightRotate(parent);
			}*/
			}
			x = parent;
			parent = x.parent;
		}
	}
		
    private boolean rotateleft(Node p) {
		
	if (p.left==null) {
	    if (p.rank == 1)
		{return true;}
	    return false;
	} else if (p.rank >= p.left.rank + 2)
	    return true;
	return false;
    }

    
    private boolean rotateright(Node p) {
	if (p.right == null) { 
	    if (p.rank == 1)
		return true;
	    return false;
	} else if (p.rank >= p.right.rank + 2)
	    return true;
	return false;
    }
	
	
	public Node searchkey(Node x,K key){	
			Node y=null;
		
		  if(x.key.compareTo(key)==0){
			
			return x;
			
		}
		else if(x.key.compareTo(key)>0){
			if(x.left!=null){
				
				y=searchkey(x.left,key);}
				else{
					
					return null;
				}
		}
		else if(x.key.compareTo(key)<0){
			
			if(x.right!=null){
			y=searchkey(x.right,key);}
			else{
				
				return null;
			}
		}
		return y;		

	}
	

	public Node insertCheck(Node n,K key,V value){
		if(n==null){
			n=new Node(key,value);
		}
		else if(n.key.compareTo(key)>0){
			n.left=insertCheck(n.left, key, value);
		}
		else if(n.key.compareTo(key)<0){
			n.right=insertCheck(n.right,key,value);
		}
		return n;

	}
	public int max(int a, int b) { 
        return (a > b) ? a : b; 
    } 
	public void rightRotate(Node x) {
		
		
		Node y = x.left;
		x.left = y.right;
		if(y.right != null){
			y.right.parent = x;
		}
		y.parent = x.parent;
		if(x.parent == null){
			this.root = y;
		}
		else if(x == x.parent.right){
			x.parent.right = y;
		}
		else{
			x.parent.left = y;
		}
		y.right = x;
		x.parent = y;
		y.rank=x.rank;
		y.right.rank--;
		if(y.right.left==null&&y.right.right==null){
			if(y.right.rank==2){
				y.right.rank--;
			}
		}
		rotate++;
  
	}
	public void leftRotate(Node x) {
		
		
		Node y=x.right;
		x.right=y.left;
		if(y.left!=null){
			y.left.parent=x;
		}
		y.parent=x.parent;
		if(x.parent==null){
			root=y;
		}
		else if(x==x.parent.left){
			x.parent.left=y;
		}
		else{
			x.parent.right=y;
		}
		y.left=x;
		x.parent=y;
		y.rank=x.rank;
		y.left.rank--;
		if(y.left.left==null&&y.left.right==null){
			
			if(y.left.rank==2){
				y.left.rank--;
			}
		}
		


		rotate++;
		
	}

	public V remove(K key){
		// write your code her 
		V v=null;
		Node n=searchkey(root, key);
		if(n!=null)
		{v=n.value;}
		root =deletehelp(root, key);
		
		return v;
		
		
	}
	public Node deletehelp(Node n,K key){
		
		if (n == null)  
            return n;  
  
         
		if (key.compareTo(n.key)<0)  
		{	
			;check=-1;
            n.left = deletehelp(n.left, key);  }
  
       
		else if (key.compareTo(n.key)>0)  
			{check=1;
            n.right = deletehelp(n.right, key); } 
  
        
        else
        {  
  
            
            if ((n.left == null) || (n.right == null))  
            {  
                Node temp = null;  
                if (temp == n.left)  
                   { temp = n.right;  }
                else
                    {temp = n.left;  }
  
                 
                if (temp == null)  
                {  
                    temp = n;  
                    n = null;  
                }  
                else  
                    n = temp; 
            }  
            else
            {  
  
                 
				Node temp = leftend(n.right);
				Node t=n.right;
				/*while( t!=n){if(t.key.compareTo()<0&&t!=root){break;}
				else{
					t=t.left;
				}

				}
				temp=t;*/  
				
                  
                n.key = temp.key;  
				n.value=temp.value;
				
				
				n.right = deletehelp(n.right, temp.key); 
				if(n!=root) 
				deletebalance(n); 
			} 
			
        }  
  
        
        if (n == null)  
			{return n;}
			return n;
	}
	private void deletebalance( Node n) {
		int balance;
		Node sibling=null;
		if(check==-1){
			sibling=n.parent.left;
		}
		else if(check==1){
			sibling=n.parent.right;
		}
		if (sibling == null) 
			balance = -1 - n.rank;
		else
			balance = sibling.rank - n.rank;
		
		while (balance != 1) { 
			if (balance == 0) {
			n.parent.rank--;
			} else if (n.parent.left == sibling) {
			n.parent.rank -= 2;
			int siblingBalance = sibling.right.rank - sibling.left.rank;
			if (siblingBalance == 0) {
				sibling.rank++;
				n.parent.rank++;
				rightRotate(n.parent);
				break;
			} else if (siblingBalance > 0) {
				sibling.right.rank++;
				sibling.rank--;
				leftRotate(sibling);
			}
			rightRotate(n.parent);
			n.parent = n.parent.parent;
			} else { // delete on left
			n.parent.rank -= 2;
			int siblingBalance =sibling.right.rank - sibling.left.rank;
			if (siblingBalance == 0) {
				sibling.rank++;
				n.parent.rank++;
				leftRotate(n.parent);
				break;
			} else if (siblingBalance < 0) {
				sibling.left.rank++;
				sibling.rank--;
				rightRotate(sibling);
			}
			leftRotate(n.parent);
			n.parent = n.parent.parent;
			}
	
			if (n.parent.parent == null)
			return;
			n = n.parent;
			n.parent = n.parent.parent;
			sibling = (n.parent.left == n) ? n.parent.right : n.parent.left;
			balance = sibling.rank - n.rank;
		}
		}
	

	public V get(K key){
		// write your code her 
		

		Node n=searchkey(root, key);

		
		if(n!=null && n.value!=null){
			return n.value;
		}
		else{
			return null;
		}
	}

	public Vector<V> searchRange(K key1, K key2){
		// write your code her 
		Vector v=new Vector();
		
		inorder(root,v,key1,key2);
		
		return v;
	}
	public void inorder(Node node,Vector V, K key1, K key2) 
    { 
        if (node == null) 
           { return;} 
  
       
        inorder(node.left,V,key1,key2); 
		if(node.key.compareTo(key1)>=0 && node.key.compareTo(key2)<=0)
       
       { V.add(node.value);}
  
        
        inorder(node.right,V,key1,key2); 
    } 

	public int rotateCount(){
		// write your code her 
		return rotate;
	}
	public int maxDepth(Node node)  
    { 
        if (node == null) 
            return 0; 
        else 
        { 
           
            int l = maxDepth(node.left); 
            int r = maxDepth(node.right); 
   
           
            if (l > r) 
                return (l + 1); 
             else 
                return (r + 1); 
        } 
    } 

	public int getHeight(){
		// write your code her 
		int h=maxDepth(root);
		return h;
	}

	public Vector<K> BFS(){
		// write your code her 
		Queue<Node> q = new LinkedList<Node>();
		Vector v= new Vector();
		if (root == null)
			{return v;}
		q.add(root);
		while (!q.isEmpty()) {
			Node n = (Node) q.remove();
			v.add(n.key);
			if (n.left != null){
				q.add(n.left);
			}
			if (n.right != null){
				q.add(n.right);
			}
		}
		return v;
	}

}
