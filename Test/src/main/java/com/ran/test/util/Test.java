package com.ran.test.util;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		for(StatusEnum se:StatusEnum.values()){//enum类型的3个方法
			int tmp = se.ordinal();
			System.out.println("tmp="+tmp+",se="+se.toString());
		}
/*		switch (StatusEnum.values()){
		   case StatusEnum.INIT:
		       System.out.println("init");
		   case StatusEnum.FINISH:
			   System.out.println("finish");
		}*/
			

	}

}
