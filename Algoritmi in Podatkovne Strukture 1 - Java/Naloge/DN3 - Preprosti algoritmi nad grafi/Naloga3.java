import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class par{
	public int i, j;
	public par(int i, int j){
		this.i = i;
		this.j = j;
	}
}

class graf{
	int V;
	par[] E;
	boolean directed;
	int[] mark;
	int time;
	int[] out;
	int outtime;
	public graf(int V, par[] E, boolean dir){
		this.V = V;
		this.E = E;
		this.directed = dir;
		this.mark = new int[V];
		this.out = new int[V];
		this.time = 0;
		this.outtime = 0;
	}
	public boolean adjacent(int x, int y){
		boolean isAdjacent = false;
		for(int i = 0; i < this.E.length; i++){
			if(((this.E[i].i == x) && (this.E[i].j == y)) || ((this.E[i].i == y) && (this.E[i].j == x) && !this.directed)){
				isAdjacent = true;
				break;
			}
		}
		return isAdjacent;
	}
	public int[] unmarkedNeighbours(int x){
		int[] out = new int[this.neighbours(x).length];
		int index = 0;
		for(int point : this.neighbours(x)){
			if(this.mark[point] == -1) out[index++] = point;
		}
		return Arrays.copyOfRange(out, 0, index);
	}
	public int[] neighbours(int x){
		int[] n = new int[this.V];
		int index = 0;
		for(int i = 0; i < this.E.length; i++){
			if(this.E[i].i == x) n[index++] = this.E[i].j;
			if((this.E[i].j == x) && !this.directed) n[index++] = this.E[i].i;
		}
		return Arrays.copyOfRange(n, 0, index);
	}
	public static int clique(int V, boolean directed){
		int sum = 0;
		for(int i = V; i > 0; i--){
			sum += V;
			if(!directed) sum -= V - i;
		}
		return sum;
	}
	public void info(){
		String out = this.V + " " + this.E.length + " " + (graf.clique(this.V, this.directed) - this.E.length);
		System.out.println(out);
		for(int i = 0; i < this.V; i++){
			String line = i + " ";
			int vhod = 0,
				izhod = 0;
			for(int j = 0; j < this.E.length; j++){
				if(this.E[j].i == i) izhod++;
				if(this.E[j].j == i) vhod++;
			}
			line += this.directed ? vhod + " " + izhod : (vhod+izhod);
			System.out.println(line);
		}
	}
	public int walk(int start, int stop, int length){
		if(length == 1){
			if(this.adjacent(start, stop)) return 1;
			return 0;
		}else{
			int sum = 0;
			for(int point : this.neighbours(start)){
				sum += walk(point, stop, length-1);
			}
			return sum;
		}
	}
	public void walks(int k){
		for(int i = 0; i < this.V; i++){
			String vrstica = "";
			for(int j = 0; j < this.V; j++){
				vrstica += walk(i, j, k) + " ";
			}
			System.out.println(vrstica);
		}
	}
	public void fsInit(){
		for(int i = 0; i < this.V; i++){
			this.mark[i] = -1;
			this.out[i] = -1;
		}
		this.time = 0;
	}
	public void dfs(int v){
		if(this.mark[v] == -1) this.mark[v] = this.time++;
		for(int point : this.unmarkedNeighbours(v)){
			if(this.mark[point] == -1) {
				this.dfs(point);
			}
		}
		if(this.out[v] == -1) this.out[v] = this.time;
	}
	public void dfsStart(){
		this.fsInit();
		for(int i = 0; i < this.V; i++){
			if(this.mark[i] == -1) dfs(i);
		}
		String vrstica = "";
		for(int i = 0; i < this.V; i++){
			vrstica += this.mark[i] + " ";
		}
		System.out.println(vrstica);
		vrstica = "";
		for(int i = 0; i < this.V; i++){
			vrstica += this.out[i] + " ";
		}
		System.out.println(vrstica);
	}
	public void bfs(int v){
		Queue<Integer> q = new LinkedList<Integer>();
		if(this.mark[v] == -1) this.mark[v] = this.time++;
		q.add(v);
		while(!q.isEmpty()){
			v = q.poll();
			for(int point : this.unmarkedNeighbours(v)){
				this.mark[point] = time++;
				q.add(point);
			}
		}
	}
	public void bfsStart(){
		this.fsInit();
		for(int i = 0; i < this.V; i++){
			if(mark[i] == -1) bfs(i);
		}
		String vrstica = "";
		for(int i = 0; i < this.V; i++){
			vrstica += this.mark[i] + " ";
		}
		System.out.println(vrstica);
	}
	public void len(int v){
		Queue<Integer> q = new LinkedList<Integer>();
		if(this.mark[v] == -1) this.mark[v] = this.time++;
		q.add(v);
		while(!q.isEmpty()){
			v = q.poll();
			for(int point : this.unmarkedNeighbours(v)){
				this.mark[point] = time;
				q.add(point);
			}
			time++;
		}
	}
	public void sp(int i){
		this.fsInit();
		this.len(i);
		for(int j = 0; j < this.V; j++){
			System.out.println(j + " " + this.mark[j]);
		}
	}
	public int[] unmarked(){
		int[] out = new int[this.V];
		int index = 0;
		for(int i = 0; i < this.V; i++){
			if(this.mark[i] == -1){
				out[index++] = i;
			}
		}
		return Arrays.copyOfRange(out, 0, index);
	}
	public void comp(){
		this.fsInit();
		if(this.directed){
			
		}else{
			Queue<Integer> q = new LinkedList<Integer>();
			q.add(0);
			int[] toDo = this.unmarked();
			while(toDo.length >= 1){
				String out = "";
				while(!q.isEmpty()){
					int i = (int) q.poll();
					out += i + " ";
					this.mark[i] = 1;
					for(int point: this.unmarkedNeighbours(i)){
						q.add(point);
						this.mark[point] = 1;
					}
				}
				System.out.println(out);
				toDo = this.unmarked();
				if(toDo.length>0) q.add(toDo[0]);
			}
		}
	}
}

public class Naloga3 {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int stVoz = Integer.parseInt(sc.nextLine());
		boolean jePovezan = args[0].equals("directed");
		par[] vozlisca = new par[graf.clique(stVoz, jePovezan)];
		int index = 0;
		while(sc.hasNext()){
			String vrstica = sc.nextLine();
			int I = Integer.parseInt(vrstica.split(" ")[0]),
				J = Integer.parseInt(vrstica.split(" ")[1]);
			boolean jeRaz = true;
			for(int j = 0; j < index; j++){
				jeRaz = !(((I == vozlisca[j].i) && (J == vozlisca[j].j)) || ((I == vozlisca[j].j) && (J == vozlisca[j].i) && !jePovezan));
				if (!jeRaz) break;
			}
			if(jeRaz) vozlisca[index++] = new par(I, J);
		}
		sc.close();
		
		graf test = new graf(stVoz, Arrays.copyOfRange(vozlisca, 0, index), jePovezan);
		String u = args[1];
		if(u.equals("info")) test.info();
		else if(u.equals("walks")) test.walks(Integer.parseInt(args[2]));
		else if(u.equals("dfs")) test.dfsStart();
		else if(u.equals("bfs")) test.bfsStart();
		else if(u.equals("sp")) test.sp(Integer.parseInt(args[2]));
		else if(u.equals("comp")) test.comp();
	}
}
