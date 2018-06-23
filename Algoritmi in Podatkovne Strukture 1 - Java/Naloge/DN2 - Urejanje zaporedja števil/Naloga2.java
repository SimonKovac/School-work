import java.util.Arrays;
import java.util.Scanner;

class resizableArray {
	int len;
	int[] array;
	String argument;
	String sort;
	int ascDesc;
	int longest;
	int mvCnt, cmpCnt;
	int min, max;
	public resizableArray(int[] values, String arg, String sort, int ascDesc){
		this.len = values.length;
		this.array = values;
		this.argument = arg;
		this.ascDesc = ascDesc;
		this.findLongest();
		this.sort = sort;
		if(this.argument.equals("trace")){
			this.printArray(0, 0, this.len);
			this.runSort();
		}else if(this.argument.equals("count")){
			String tempString = "";
			for(int i = 0; i < 3; i++){
				this.mvCnt = 0;
				this.cmpCnt = 0;
				if(i == 2) this.ascDesc *= -1;
				this.runSort();
				tempString += Integer.toString(this.mvCnt) + " " + Integer.toString(this.cmpCnt) + " ";
			}
			System.out.println(tempString);
		}
	}
	public void runSort(){
		if(this.sort.equals("bubble")) this.bubbleSort();
		else if(this.sort.equals("insert")) this.insertionSort();
		else if(this.sort.equals("select")) this.selectionSort();
		else if(this.sort.equals("radix")) this.radixSort(0);
		else if(this.sort.equals("heap")) this.heapSort();
		else if(this.sort.equals("quick")) this.quickSort(0, this.len);
		else if(this.sort.equals("bucket")) this.bucketSort();
		else if(this.sort.equals("merge")) this.mergeSort();
	}
	public void swap(int indexA, int indexB){
		int temp = this.array[indexA];
		this.array[indexA] = this.array[indexB];
		this.array[indexB] = temp;
		this.mvCnt += 3;
	}
	public boolean compare(int indexA, int indexB){
		this.cmpCnt++;
		return (this.array[indexA] - this.array[indexB]) * this.ascDesc < 0;
	}
	public void printArray(int barPos, int from, int to){
		String temp = "";
		for(int i = from; i < to; i++){
			if((barPos > 0) && (barPos == i)) temp += "| ";
			temp += Integer.toString(this.array[i]) + " ";
		}
		if(barPos == to) temp+= "|";
		System.out.println(temp);
	}
	public void printArray(int[] barPos, int from, int to){
		String temp = "";
		for(int i = from; i < to; i++){
			if(barPos.length > 0){
				for(int j = 0; j < barPos.length; j++){
					if(i == barPos[j]) temp += "| ";
				}
			}
			temp += Integer.toString(this.array[i]) + " ";
		}
		for(int i = 0; i < barPos.length; i++){
			if(barPos[i] == to) temp += "|";
		}
		System.out.println(temp);
	}
	public void insertionSort(){
		for(int i = 1; i < this.len; i++){
			int j = i;
			while((j > 0) && (this.compare(j, j-1))){
				this.swap(j, j-1);
				j--;
			}
			if(this.argument.equals("trace")) this.printArray(i+1, 0, this.len);
		}
	}
	public void selectionSort(){
		for (int j = 0; j < this.len-1; j++){
		    int min = j;
		    for (int i = j+1; i < this.len; i++) {
		        if (this.compare(i, min)) min = i;
		    }
		    if(min != j) this.swap(j, min);
		    if(this.argument.equals("trace")) this.printArray(j+1, 0, this.len);
		}
	}
	public void bubbleSort(){
		int zadnjaZamenjava = 0;
		boolean swapped = false;
		do{
			swapped = false;
			int razlika = 1;
			for(int i = this.len - 1; i > zadnjaZamenjava; i--){
				razlika = 1;
				if(this.compare(i, i-1)){
					this.swap(i, i-1);
					swapped = true;
					razlika = 1;
				}else if(swapped){
					razlika += 1;
				}
			}
			zadnjaZamenjava += razlika;
			if((this.argument.equals("trace"))&&(swapped)) this.printArray(zadnjaZamenjava, 0, this.len);
		}while(swapped);
	}
	public int partition(int from, int to){
		int pivot = from;
		int i = from;
		int j = to;
		while(true){
			do{
				i++;
			}while((i<this.len)&&(this.compare(i, pivot))&&(i < to));
			do{
				j--;
			}while(this.compare(pivot, j));
			if( i >= j) break;
			this.swap(i, j);
		}
		this.swap(from, j);
		return j;
	}
	public void quickSort(int from, int to){
		if(from < to){
			int p = this.partition(from, to);
			if((this.argument.equals("trace")) && (to-from > 2)) this.printArray(new int[]{p, p+1}, from, to);
			this.quickSort(from, p);
			this.quickSort(p+1, to);
		}
		if(((to - from) == this.len)&&(this.argument.equals("trace"))) this.printArray(0, from, to);
	}
	public void findLongest(){
		int meja = 1;
		for(int i = 0; i < this.len; i++){
			while(this.array[i] > Math.pow(10, meja)) meja++;
		}
		this.longest = meja;
	}
	public void radixSort(int which){
		int[] c = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		for(int i = 0; i < this.len; i++){
			int iskana = (int) ((this.array[i] % Math.pow(10, which+1))/Math.pow(10, which));
			c[iskana]++;
		}
		int[] p = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		if(this.ascDesc == 1){
			int skupno = 0;
			for(int i = 0; i < c.length; i++){
				skupno += c[i];
				p[i] += skupno;
			}
		}else{
			int skupno = this.len;
			for(int i = 0; i < c.length; i++){
				p[i] += skupno;
				skupno -= c[i];
			}
		}
		int[] temp = new int[this.len];
		for(int i = this.len-1; i >= 0; i--){
			int iskana = (int) ((this.array[i] % Math.pow(10, which+1))/Math.pow(10, which));
			temp[--p[iskana]] = this.array[i];
		}
		this.array = temp;
		this.printArray(0, 0, this.len);
		if(which < this.longest-1) this.radixSort(which+1);
		this.cmpCnt += 10;
		this.mvCnt += 10;
	}
	public int getParent(int i){
		return (i-1)/2;
	}
	public int getLeftNode(int i){
		return 2 * i + 1;
	}
	public void siftDown(int start, int end){
		int root = start;
		while(this.getLeftNode(root) <= end){
			int child = this.getLeftNode(root);
			int swap = root;
			if(this.compare(swap, child)) swap = child;
			if((child+1 <= end) && (this.compare(swap, child+1))) swap = child + 1;
			if(swap == root){
				return;
			}else{
				this.swap(root, swap);
				root = swap;
			}
		}
	}
	public void makeHeap(){
		int start = this.getParent(this.len-1);
		while(start >= 0){
			this.siftDown(start--, this.len-1);
		}
	}
	public void heapSort(){
		this.makeHeap();
		if(this.argument.equals("trace")) this.printArray(this.len, 0, this.len);
		int end = this.len - 1;
		while(end > 0){
			this.swap(end, 0);
			if(this.argument.equals("trace")) this.printArray(end, 0, this.len);
			end--;
			this.siftDown(0, end);
		}
	}
	public void findMinMax(){
		int min = this.array[0];
		int max = this.array[0];
		for(int i = 1; i < this.len; i++){
			if(this.array[i] > max) max = this.array[i];
			if(this.array[i] < min) min = this.array[i];
		}
		this.max = max;
		this.min = min;
	}
	public void bucketSort(){
		this.findMinMax();
		int bckCnt = this.len/2;
		double v = (this.max - this.min + 1)/(double)bckCnt;
		int[] c = new int[bckCnt];
		for(int i = 0; i < this.len; i++){
			int ind =(int) Math.floor((this.array[i]-this.min)/v);
			c[ind]++;
		}
		int[] p = new int[bckCnt];
		int[] bars = new int[bckCnt-1];
		if(this.ascDesc == 1){
			int skupno = 0;
			for(int i = 0; i < bckCnt; i++){
				skupno += c[i];
				p[i] = skupno;
				if(i < bckCnt-1){
					bars[i] = skupno + 1;
				}
			}
		}else{
			int skupno = this.len;
			for(int i = 0; i < bckCnt; i++){
				p[i] = skupno;
				if(i > 0){
					bars[i-1] = skupno;
				}
				skupno -= c[i];
			}
		}
		int[] temp = new int[this.len];
		for(int i = this.len-1; i >= 0; i--){
			int ind =(int) Math.floor((this.array[i]-this.min)/v);
			temp[--p[ind]] = this.array[i];
		}
		this.mvCnt += 2 * this.len;
		this.cmpCnt += 2 * this.len;
		this.array = temp;
		this.printArray(bars, 0, this.len);
		this.insertionSort();
	}
	public void split(int[] temp, int from, int to){
		if(to - from < 2){
			this.cmpCnt++;
			return;
		}
		int mid = (int)Math.ceil((from + to) / (double)2);
		if(this.argument.equals("trace")) this.printArray(mid, from, to);
		this.split(temp, from, mid);
		this.split(temp, mid, to);
		this.merge(temp, from, mid, to);
		if(this.argument.equals("trace")) this.printArray(0, from, to);
	}
	public void merge(int[] temp, int from, int mid, int to){
		int i = from, j = mid;
		for(int k = from; k < to; k++){
			if((i < mid) && ((j >= to) || (temp[i] <= temp[j]))){
				this.array[k] = temp[i];
				i++;
			}else{
				this.array[k] = temp[j];
				j++;
			}
			this.mvCnt += 2;
		}
	}
	public void mergeSort(){
		int[] temp = new int[this.len];
		System.arraycopy(this.array, 0, temp, 0, this.len);
		this.split(temp, 0, this.len);
	}
}

public class Naloga2 {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		String[] split = input.split(" ");
		int[] temp = new int[split.length];
		int tempad = 1;
		for(int i = 0; i < split.length; i++){
			temp[i] = Integer.parseInt(split[i]);
		}

		if(args.length == 3){
			if(args[2].equals("up")) tempad = 1; else if (args[2].equals("down")) tempad = -1;
			resizableArray test = new resizableArray(temp, args[0], args[1], tempad);
		}
		sc.close();
	}
}  