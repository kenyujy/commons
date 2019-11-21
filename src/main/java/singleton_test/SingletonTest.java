package singleton_test;

public class SingletonTest {
	// 静态常量 类一加载直接创建，外部可以以 SingletonTest.instance 访问
	public static final SingletonTest instance= new SingletonTest();
	
	private SingletonTest() {
		// 构造器为私有，外部不能访问
	}

}
