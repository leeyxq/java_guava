package com.fun.demo.guava.base;

import org.junit.Test;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * 对象通用方法工具
 * @author lixq
 *
 */
public class CommonObjectUtilities {

	@Test
	public void base(){
		// equals 判断相等 减少NullPointException风险 jdk7提供等同于Objects.equals方法
		System.out.println(Objects.equal("123", "123"));// true
		System.out.println(Objects.equal("123", null));// false
		System.out.println(Objects.equal(null, null));// true
		System.out.println(Objects.equal(null, "123"));// false
		
		// hashCode(args ...) 可变参数 可以非常简便的使用Objects.hashCode(field1， field2， ...， fieldn)
		Student student = new Student("lixq", 12, true);
		System.out.println(student.hashCode());
		System.out.println(Objects.hashCode("lixq"));
		System.out.println(Objects.hashCode("lixq", "12"));
		
		
		//toString() 可以自由快捷字符串化对象
		System.out.println(MoreObjects.toStringHelper(student).add("age", 12).add("name", "lixq"));
		
		//ComparisonChain是一个lazy的比较过程，当比较结果为0即相等的时候，继续比较下去，直到非0的情况，就会忽略后面的比较。ComparisonChain实现的compare和compareTo在代码可读性和性能上都有很大的提高。
		Student student2 = new Student("lixq", 12, true);
		System.out.println(student.compareTo(student2));
	}
}

class Student implements Comparable<Student>{
	private String name;
	
	public Student(String name, int age, boolean sex) {
		super();
		this.name = name;
		this.age = age;
		this.sex = sex;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public boolean isSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	private int age;
	private boolean sex;

	@Override
	public int compareTo(Student other) {
        return ComparisonChain.start()
        .compare(name, other.name)
        .compare(sex, other.sex, Ordering.natural().nullsLast())
        .result();
    }
	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + ", sex=" + sex + "]";
	}
}
