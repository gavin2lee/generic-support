package com.lachesis.support.auth.demo.core;

public class ClassLoaderDemo {

	public static void main(String[] args) throws ClassNotFoundException {
		String clazzName = "com.lachesis.support.objects.vo.auth.AuthenticationRequestVO";
		DemoClassLoader dcl = new DemoClassLoader();
		Class<?> dclVoClazz = dcl.loadClass(clazzName);
		Class<?> mainVoClazz = Class.forName(clazzName);
		
		if(dclVoClazz == mainVoClazz){
			System.out.println("identical");
		}else{
			System.out.println("different");
		}
		
		System.out.println(dclVoClazz.getClassLoader());
	}
	
	private static class DemoClassLoader extends ClassLoader{

	
		
	}

}
