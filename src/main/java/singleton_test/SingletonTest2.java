package singleton_test;

import java.io.IOException;
import java.util.Properties;

public class SingletonTest2 {
	// 静态常量 类一加载直接创建，外部可以以 SingletonTest.instance 访问
	public static final SingletonTest2 instance;
	static {
		Properties pro= new Properties();
		try {
			pro.load(SingletonTest2.class.getClassLoader().getResourceAsStream("singleton.properties"));
		} catch (IOException e) {
			// TODO 用类加载器加载配置文件
			e.printStackTrace();
		}
		String s= pro.getProperty("info");
		
		instance = new SingletonTest2();
	}
	
	private SingletonTest2() {
		
	};
}
