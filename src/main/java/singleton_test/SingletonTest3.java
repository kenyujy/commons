package singleton_test;

public class SingletonTest3 {
	
	private static SingletonTest3 instance;
	//构造函数为私有
	private SingletonTest3() {
		
	}
	//静态函数块创建实例对象, 外部通过 SingletonTest3.getInstance() 访问
	//线程不安全, 静态内部类不会随着外部类的加载而初始化
	public static SingletonTest3 getInstance() {
		synchronized(SingletonTest3.class) {
			if(null==instance) {
				instance= new SingletonTest3();
			}	
		}
		return instance;
	}

}
