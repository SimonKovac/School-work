import java.util.Random;
import java.util.Scanner;

class Node<T>{
	Node<T> left, right;
	T value;
	public Node(T v){
		this.value = v;
	}	

	public boolean isNotFull(){
		return((this.left == null) || (this.right == null));
	}
}

class celovitoDvojiskoDrevo{
	Node<String> root;
	
	public celovitoDvojiskoDrevo(int stElem){
		this.root = new Node<String>("0");
		for(int i = 1; i < stElem; i++){
			Random rnd = new Random();
			this.napolni(this.root, new Node<String>(Integer.toString(i)), i);
		}
		StdDraw.setCanvasSize(512, 512);
		StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLUE);
		StdDraw.setXscale(0, stElem);
		StdDraw.setYscale(0, (int)Math.floor(Math.log(stElem)/Math.log(2)));
		this.izrisi(root, stElem/2, (int)Math.floor(Math.log(stElem)/Math.log(2)));
	}
	public void izrisi(Node<String> which, double x, double y){
		StdDraw.circle(x, y, 0.01);
		StdDraw.text(x, y, which.value);
		if(which.left != null){
			StdDraw.line(x, y, x-1, y-1);
			this.izrisi(which.left, x-1, y-1);
		}
		if(which.right != null){
			StdDraw.line(x, y, x+1, y-1);
			this.izrisi(which.right, x+1, y-1);
		}
	}
	
	public void napolni(Node<String> root, Node<String> item, int node){
		int parent = (node -1)/2;
		if(parent == 0){
			if(root.left == null){
				root.left = item;
			}else{
				root.right = item;
			}
		}else{
			Node<String> child = ((parent%2) == 1) ? root.left : root.right;
			this.napolni(child, item, parent);
		}
	}
	public void inOrder(Node<String> focus){
		if(focus != null){
			this.inOrder(focus.left);
			System.out.print(focus.value+" ");
			this.inOrder(focus.right);
		}
	}
	public void preOrder(Node<String> focus){
		if(focus != null){
			System.out.print(focus.value+" ");
			this.preOrder(focus.left);
			this.preOrder(focus.right);
		}
	}
	public void postOrder(Node<String> focus){
		if(focus != null){
			this.postOrder(focus.left);
			this.postOrder(focus.right);
			System.out.print(focus.value+" ");
		}
	}
}

public class Izziv4 {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		celovitoDvojiskoDrevo test = new celovitoDvojiskoDrevo(n);
		System.out.println("Inorder sprehod");
		test.inOrder(test.root);
		System.out.println("");
		System.out.println("Preorder sprehod");
		test.preOrder(test.root);
		System.out.println("");
		System.out.println("Postorder sprehod");
		test.postOrder(test.root);
		sc.close();
	}
}
