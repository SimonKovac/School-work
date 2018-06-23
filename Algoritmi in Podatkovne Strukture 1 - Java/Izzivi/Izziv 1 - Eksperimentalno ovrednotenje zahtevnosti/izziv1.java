package vaje1;

import java.util.Random;

public class izziv1 {
	public static int[] generateTable(int n)
	{
		int[] tabela = new int[n+1];
		for(int i = 1; i <= n; i++)
		{
			tabela[i] = i;
		}
		return tabela;
	}
	
    static int findLinear(int[] a, int v)
    {
    	for(int i = 0; i < a.length; i++){
    		if(a[i] == v){
    			return i;
    		}
    	}
    	return -1;
    }
    //rekurzivna
    static int findBinary(int[] a, int l, int r, int v)
    {
    	if(l > r){
    		return -1;
    	}
    	
    	int m = (int)Math.floor((double)(l + r)/2);
    	if(a[m] < v){
    		return findBinary(a, m+1, r, v);
    	}else if(a[m] > v){
    		return findBinary(a, l, m-1, v);
    	}else if (a[m] == v){
    		return m;
    	}
    	return -1;
    }
    /*//ne-rekurzivna
    static int findBinary(int[] a, int v){
    	int l = 0;
    	int r = a.length-1;
    	if(l>r){
    		return 0;
    	}
    	int m = (int)Math.floor((double)(l+r)/2);
    	while(a[m]!=v){
    		m = (int)Math.floor((double)(l+r)/2);
    		if(a[m] < v){
    			l = m + 1;
    		}else if(a[m] > v){
    			r = m-1;
    		}else if(a[m] == v){
    			return v;
    		}
    	}
    	return 0;
    }
    */
    static long timeLinear(int n)
    {
    	Random rng = new Random();
    	int[] a = generateTable(n);
    	long startTime = System.nanoTime();
    	for(int i = 0; i < 1000; i++){
    		int v = rng.nextInt(n-1) + 1;
        	findLinear(a, v);
    	}
    	long executionTime = System.nanoTime() - startTime;
    	return executionTime/1000;
    }
    
    static long timeBinary(int n)
    {
    	Random rng = new Random();
    	int[] a = generateTable(n);
    	long startTime = System.nanoTime();
    	for(int i = 0; i < 1000; i++){
    		int v = rng.nextInt(n-1) + 1;
        	findBinary(a, 0, a.length-1, v);
    	}
    	long executionTime = System.nanoTime() - startTime;
    	return executionTime/1000;
    }

    public static void main(String args[]){
    	System.out.format("%10s|%10s|%10s\n", "n", "linearno", "dvojisko");
    	System.out.format("--------------------------------\n");
    	for(int i = 1000; i < 100000; i+=1000){
    		System.out.format("%10d|%10d|%10d\n", i, timeLinear(i), timeBinary(i));
    	}
    }

}
