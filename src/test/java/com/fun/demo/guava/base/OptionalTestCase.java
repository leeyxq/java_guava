package com.fun.demo.guava.base;

import org.junit.Test;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;

public class OptionalTestCase {

	@Test
	public void base() {
		
		Optional<Integer> notNull = Optional.of(5);//返回一个包含非空指的Optional对象，若值为null即可失败，抛出NullpointException
		System.out.println(notNull.isPresent());//如果Optional包含的T实例不为null，则返回true；若T实例为null，返回false
		System.out.println(notNull.get());
		
		Optional<Integer> nullObj = Optional.absent();//获得一个Optional对象，其内部包含了空值
		System.out.println(nullObj.isPresent());
		System.out.println(nullObj.or(123));//若Optional实例中包含了传入的T的相同实例，返回Optional包含的该T实例，否则返回输入的T实例作为默认值
		System.out.println(nullObj.orNull());//返回Optional实例中包含的非空T实例，如果Optional中包含的是空值，返回null，逆操作是fromNullable()
		
		Optional<Integer> nullable = Optional.fromNullable(null);//将一个T的实例转换为Optional对象，T的实例可以不为空，也可以为空[Optional.fromNullable(null)，和Optional.absent()等价
		System.out.println(MoreObjects.firstNonNull(nullable, notNull).get());
	}
}
