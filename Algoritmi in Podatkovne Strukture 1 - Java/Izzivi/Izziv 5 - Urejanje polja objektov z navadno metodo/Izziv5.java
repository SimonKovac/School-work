import java.util.Random;
import java.util.Scanner;

class oseba implements Comparable{
    String priimek;
    String ime;
    int letoR;
    static int smer = 1;
    static int atr = 0;
    static final String[] priimki = {"Kovac", "Drnovsek", "Jansa", "Putin", "Trump", "Kucan", "Stalin", "Zizek", "Zedong"};
    static final String[] imena = {"Simon", "Janez", "Vladimir", "Donald", "Josef", "Milan", "Mao", "Slavoj", "Marjan"};
    static final int najstarejsi = 1899;
    public oseba(){
        Random r = new Random();
        this.priimek = oseba.priimki[r.nextInt(oseba.priimki.length)];
        this.ime = oseba.imena[r.nextInt(oseba.imena.length)];
        this.letoR = r.nextInt(2016 - oseba.najstarejsi + 1) + oseba.najstarejsi;
    }
    static void setAtr(int newAtr){
        oseba.atr = newAtr;
    }
    static int getAtr(){
        return oseba.atr;
    }
    static void setSmer(int newSmer){
    	oseba.smer = newSmer;
    }
    public String toString(){
        switch (oseba.atr){
            case 0: return this.ime;
            case 1: return this.priimek;
            case 2: return Integer.toString(this.letoR);
        }
        return "";
    }
    @Override
    public int compareTo(Object arg0) {
        if(arg0 instanceof oseba){
            switch (oseba.atr) {
                case 0: return this.ime.compareTo(((oseba) arg0).ime);
                case 1: return this.priimek.compareTo(((oseba) arg0).priimek);
                case 2:    return this.letoR - ((oseba)arg0).letoR;
            }
        }
        return 0;
    }
}
public class izziv5 {
    public static Comparable[] straightInsertion(Comparable[] a){
        for(int i = 1; i < a.length-1; i++){
            Comparable k = a[i];
            int j = i;
            while((j > 0) && (a[j-1].compareTo(k)*oseba.smer > 0)){
                a[j] = a[j-1];
                j = j - 1;
            }
            a[j] = k;
        }
        return a;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.printf("Vnesi velikost n: ");
        int n = sc.nextInt();
        oseba[] tt = new oseba[n];
        oseba[] t = new oseba[n];
        for(int i = 0; i < n; i++){
            tt[i] = new oseba();
        }
        boolean ponovitev = true;
        while(ponovitev){
        	t = tt;
        	for(int i = 0; i < t.length; i++){
        		System.out.printf("%d ", t[i]);
        	}
        	System.out.printf("\nVnesi atr: ");
        	oseba.setAtr(sc.nextInt());
        	System.out.printf("\nVnesi smer: ");
        	oseba.setSmer(sc.nextInt());
        	straightInsertion(t);
        	System.out.printf("\nPonovitev? Y/N");
        	ponovitev = sc.nextLine().equals("Y");
        }
    }
}