package com.fun.demo.guava.base;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.common.primitives.Ints;

public class PrivatesCaseTest {

	@Test
	public void ints(){
		//由于java里的基本类型不能当作对象或泛型的类型参数使用
		List<Integer>  list = Ints.asList(10, 12, 30, 0);//把基本类型数组转换为包装类Integer类型List
		Ints.toArray(list); //把包装类Integer类型List转换为基本类型数组  asList逆操作
		int[] array = new int[]{12 ,324 ,43};
		Ints.concat(array);//串联多个原生类型数组
		Ints.contains(array, 12);//判断原生类型数组是否包含给定值	
		Ints.join(", ", array);//把数组用给定分隔符连接为字符串
		Ints.min(array);// 数组中最小的值
	}
	
	@Test
	public void toBytes(){
		//字节转换
		
		System.out.println(Ints.BYTES);//常量：表示该原生类型需要的字节数 4
		byte[] arrry = Ints.toByteArray(10);
		System.out.println(Arrays.toString(arrry));//按大字节序返回value的字节数组 [0, 0, 0, 10]
		int i = Ints.fromByteArray(new byte[]{0, 0, 0, 100});//使用字节数组的前Prims.BYTES个字节，按大字节序返回原生类型值；如果bytes.length <= Prims.BYTES，抛出IAE
		System.out.println(i);
	}
}
