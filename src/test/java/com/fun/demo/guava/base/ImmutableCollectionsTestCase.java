package com.fun.demo.guava.base;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 不可变集合 https://github.com/google/guava/wiki/ImmutableCollectionsExplained
 * @author lixq
 *
 */
public class ImmutableCollectionsTestCase {

	@Test
	public void test1(){
		//不可变集合 被不可信库使用时安全、线程安全、内存高效(不需要支持可变性)
		//1.创建方式 ImmutableSet.copyOf(set)
		ImmutableSet<Integer> adStatuses =ImmutableSet.copyOf(Sets.newHashSet(1,2,3,4));
		System.out.println(adStatuses);
		
		//2.创建方式 ImmutableSet.of("a", "b", "c") or ImmutableMap.of("a", 1, "b", 2)
		ImmutableSet<String> menuSet = ImmutableSet.of("a", "b", "c");
		System.out.println(menuSet);
		ImmutableMap<String, Integer> immutableMap= ImmutableMap.of("a", 1, "b", 2);
		System.out.println(immutableMap);
		
		//3.创建方式 Builder
		final ImmutableSet<Student> students =
			       ImmutableSet.<Student>builder()
			           .addAll(ImmutableSet.of(new Student("1", 12, true),new Student("1", 12, true)))
			           .add(new Student("1", 12, true))
			           .build();
		System.out.println(students);
	}
	
	@Test
    public void test2(){
		ImmutableSet<String> foobar = ImmutableSet.of("foo", "bar", "baz");
		ImmutableList.copyOf(foobar);//会直接返回foobar.asList()
		// ImmutableXXX.copyOf 聪明的copy 避免线性拷贝，可以最大限度地减少防御性编程风格所带来的性能开销
		List<String> list = Lists.newArrayList("foo", "bar", "baz");
		ImmutableList<String> immutableList = ImmutableList.copyOf(list);//将给定列表按着先后顺序转换为不可变列表
		list.set(0, "first");
		System.out.println(list);
		System.out.println(immutableList);
	}
}
