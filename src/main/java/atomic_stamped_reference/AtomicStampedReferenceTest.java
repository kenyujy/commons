package atomic_stamped_reference;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceTest {
	
	static AtomicReference<Integer> atomicReference= new AtomicReference<Integer>(100);
	
	static AtomicStampedReference<Integer> atomicStampedReference= new AtomicStampedReference<Integer>(100, 1);  
	//数值，版本号
	public static void main(String[] args) {
		
		new Thread(()-> {
			atomicReference.compareAndSet(100, 101);
			atomicReference.compareAndSet(101, 100);
		}, "t1").start();
		
		
		new Thread(()-> {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			boolean b= atomicReference.compareAndSet(100, 2019);
			System.out.println(b+"\t"+atomicReference.get());
		}, "t2").start();
		
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		new Thread(()-> {
			int stamp= atomicStampedReference.getStamp();
			System.out.println(Thread.currentThread().getName()+"\t"+"stamp "+stamp);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp()+1);
			atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp()+1);
			// 期望值， 如果期望值正确的 修改值， 期望版本值， 修改后的版本值
		}, "t3").start();
		
		
		new Thread(()-> {
			int stamp= atomicStampedReference.getStamp();
			System.out.println(Thread.currentThread().getName()+"\t"+"stamp "+stamp);
			try {
				TimeUnit.SECONDS.sleep(4);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			boolean result= atomicStampedReference.compareAndSet(100, 2019, stamp, stamp+1);
			// 如果版本号被其他Thread 改过就不能修改
			System.out.println(result);
		}, "t4").start();
	}

}
