package com.fun.demo.guava.base;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import com.sun.istack.internal.Nullable;

import com.google.common.base.Function;

/**
 * 	Ordering是Guava流式比较器类， 用来构建复杂的比较器，适用与各种集合对象
 * @author lixq
 *
 */
public class OrderingTestCase {

	@Test
	public void base() {
		
		//1.1构造一个排序器
		Ordering<String> byLengthOrdering = new Ordering<String>(){
			@Override
			public int compare(String left, String right) {
				return Ints.compare(left.length(), right.length());
			}
		}.nullsFirst();
		List<String> list = Lists.newArrayList("中国2","23", null , "343d");
		System.out.println(byLengthOrdering.reverse().isOrdered(list));//false
		System.out.println(byLengthOrdering.sortedCopy(list));//[null, 23, 中国2, 343d]
		System.out.println(byLengthOrdering.max(list));//343d
		
		//1.2 Ordering.natural() 构造一个自然排序的排序器  
		Ordering<String> byNaturalOrdering = Ordering.natural().nullsFirst();//nullsFirst() null元素排序在最前面
		System.out.println(byNaturalOrdering.sortedCopy(list));//[null, 23, 343d, 中国2]
		
		//对一个类，创建一个排序器 利用function返回的结果进行排序，如学生按年龄进行排序
		Ordering<Student> ageOrdering = Ordering.natural().nullsFirst().onResultOf(new Function<Student, Integer>() {
			  public Integer apply(Student foo) {
			    return foo.getAge();
			  }
		});
		//[Student [name=dz, age=3, sex=true], Student [name=lixq, age=12, sex=true], Student [name=wa, age=43, sex=true]]
		System.out.println(ageOrdering.sortedCopy(Lists.newArrayList(new Student("lixq", 12, true), new Student("wa", 43, true), new Student("dz", 3, true))));
	}
}
class Foo {
	  @Nullable String sortedBy;
	  int notSortedBy;
}
