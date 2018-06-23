class CollectionException extends Exception {
    public CollectionException(String msg) {
         super(msg);
    }
}

interface Collection {
    static final String ERR_MSG_EMPTY = "Collection is empty.";
    static final String ERR_MSG_FULL = "Collection is full.";

    boolean isEmpty();
    boolean isFull();
    int count();
    String toString();
}

interface Stack<T> extends Collection {
    T top() throws CollectionException;
    void push(T x) throws CollectionException;
    T pop() throws CollectionException;
}

interface Deque<T> extends Collection {
    T front() throws CollectionException;
    T back() throws CollectionException;
    void enqueue(T x) throws CollectionException;
    void enqueueFront(T x) throws CollectionException;
    T dequeue() throws CollectionException;
    T dequeueBack() throws CollectionException;
}

class Node<T>{
	Node<T> prev, next;
	T value;
	public Node(Node<T> p, Node<T> n, T v){
		this.prev = p;
		this.next = n;
		this.value = v;
	}
	public void setNext(Node<T> newNext){
		this.next = newNext;
	}
	public void setPrev(Node<T> newPrev){
		this.prev = newPrev;
	}
}

class ArrayDeque<T> implements Deque<T>, Stack<T> {
    private static final int DEFAULT_CAPACITY = 64;
    Node<T> start, end;
    
    public ArrayDeque(){
    	this.start = null;
    	this.end = null;
    }

	@Override
	public boolean isEmpty() {
		return ((this.start == null) && (this.end == null));
	}

	@Override
	public boolean isFull() {
		return this.count() == DEFAULT_CAPACITY;
	}

	@Override
	public int count() {
		if(this.isEmpty()) return 0;
		else if(this.start == this.end) return 1;
		else{
			int count = 1;
			Node<T> current = this.start;
			do{
				current = current.next;
				count++;
			}while(!(current.equals(this.end)));
			return count;
		}
	}

	@Override
	public T top() throws CollectionException {
		if(this.isEmpty()) throw new CollectionException(ERR_MSG_EMPTY);
		else return this.start.value;
	}

	@Override
	public void push(T x) throws CollectionException {
		if(this.isFull()) throw new CollectionException(ERR_MSG_FULL);
		else if(this.isEmpty()){
			Node<T> temp = new Node<T>(null, null, x);
			this.start = temp;
			this.end = temp;
		}
		else{
			
			Node<T> temp = new Node<T>(this.end, null, x);
			this.end.setNext(temp);
			this.end = temp;
		}		
	}

	@Override
	public T pop() throws CollectionException {
		if(this.isEmpty()) throw new CollectionException(ERR_MSG_EMPTY);
		else if(this.count() == 1){
			T value = this.start.value;
			this.start = null;
			this.end = null;
			return value;
		}else{
			T value = this.end.value;
			this.end = this.end.prev;
			this.end.setNext(null);
			return value;
		}
	}

	@Override
	public T front() throws CollectionException {
		if(this.isEmpty()) throw new CollectionException(ERR_MSG_EMPTY);
		else return this.start.value;
	}

	@Override
	public T back() throws CollectionException {
		if(this.isEmpty()) throw new CollectionException(ERR_MSG_EMPTY);
		else return this.end.value;
	}

	@Override
	public void enqueueFront(T x) throws CollectionException {
		this.push(x);
	}

	@Override
	public void enqueue(T x) throws CollectionException {
		if(this.isFull()) throw new CollectionException(ERR_MSG_FULL);
		else if(this.isEmpty()){
			this.push(x);
		}else{
			Node<T> temp = new Node<T>(null, this.start, x);
			this.start.setPrev(temp);
			this.start = temp;
		}
	}

	@Override
	public T dequeue() throws CollectionException {
		return this.pop();
	}

	@Override
	public T dequeueBack() throws CollectionException {
		if(this.isEmpty()) throw new CollectionException(ERR_MSG_FULL);
		else if(this.count() == 1){
			return this.pop();
		}else{
			T value = this.start.value;
			this.start = this.start.next;
			this.start.setPrev(null);
			return value;
		}
	}
}
public class Izziv3 {
	public static void main(String[] args) throws CollectionException{
		ArrayDeque<String> test = new ArrayDeque<String>();
		String testString = "An ban pet podgan";
		System.out.println("Test push/pop");
		for(String word : testString.split(" ")){
			test.push(word);
		}
		while(!(test.isEmpty())){
			System.out.println(test.pop());
		}
		System.out.println("test enqueue/dequeue");
		for(String word : testString.split(" ")){
			test.enqueue(word);
		}
		while(!(test.isEmpty())){
			System.out.println(test.dequeue());
		}
		System.out.println("test enqueueFront/dequeueBack");
		for(String word : testString.split(" ")){
			test.enqueueFront(word);
		}
		while(!(test.isEmpty())){
			System.out.println(test.dequeueBack());
		}
	}
}
