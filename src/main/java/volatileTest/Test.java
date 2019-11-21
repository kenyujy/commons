package volatileTest;

import java.util.Arrays;

public class Test {
	
	volatile int a= 1;
	boolean flag= false;
	
	public synchronized void method01(){
		a= 1;
		flag= true;
		if(flag) {
			a+=5;
			System.out.print(a+" ");
		}
	}

	public static void main(String[] args) {
		Test tr= new Test();
		
		Thread[] ths= new Thread[100];
		for(int j=0; j<100; j++) {
			ths[j]= new Thread(()-> {
				tr.method01();	
			});
		}
		
		Arrays.asList(ths).forEach(th->th.start());
	}	
}