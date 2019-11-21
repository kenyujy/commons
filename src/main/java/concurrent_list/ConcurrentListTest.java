package concurrent_list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ConcurrentListTest {
	
	public static void main(String[] args) {
		List<String> list= new ArrayList<String>();
		
		list.add("hello");
		list.add("world");
		list.add("www");
		System.out.println(list);
		Collections.sort(list);
		System.out.println(list);
		list.parallelStream().forEach(e->System.out.println(e));//打印的不是按顺序的
		
		for (int i=0; i<5; i++) {
			new Thread(()-> {
				synchronized(ConcurrentListTest.class) {
					list.add(UUID.randomUUID().toString().substring(0, 8));
					System.out.println(list);
				}
			},"thread i").start();
		}
	}
		

}
