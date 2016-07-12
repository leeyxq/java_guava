package com.fun.demo.guava.base;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import com.google.common.base.CaseFormat;
import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Joiner.MapJoiner;
import com.google.common.base.Splitter;

public class StringsTestCase {

	@Test
	public void joiner(){
		List<String> idsList = Lists.newArrayList("faz", "baz", null, "cc");
		//跳过null元素
		System.out.println(Joiner.on(", ").skipNulls().join(idsList));//faz, baz, cc
		
		//若有null元素，用某个值代替
		System.out.println(Joiner.on(", ").useForNull("nul").join(idsList));//faz, baz, nul, cc
		
		//链式连接
		System.out.println(Joiner.on(", ").join(idsList, "last"));//[faz, baz, null, cc], last
		System.out.println(Joiner.on(", ").join("first", "last"));//first, last
		
		//map 类型分割符
		MapJoiner MAP_JOINER = Joiner.on("; ")
				.useForNull("NODATA")
				.withKeyValueSeparator(":");
		ImmutableMap<String, ? extends Object> map = ImmutableMap.of("age",  12, "name", "你好");
		System.out.println(MAP_JOINER.join(map));//age:12; name:你好
	}
	
	@Test
	public void splitter(){
		String[] arrStrings = ",a,,b,".split(","); //String.split悄悄丢弃了尾部的分隔符
		System.out.println(Arrays.toString(arrStrings));//[, a, , b]
		
		Iterable<String> string = Splitter.on(",")
			.trimResults()//每个拆分出字符存去掉前后空格
			.omitEmptyStrings()//忽略空串
			.limit(2)//限制分拆字符存数量
			.split("foo,bar,,   qux");
		
		System.out.println(Joiner.on("; ").join(string));//按单个字符拆分 foo; bar,,   qux 
		
		System.out.println(Splitter.on(CharMatcher.BREAKING_WHITESPACE).split("foo,bar,,   qux"));//按字符匹配器拆分
	}
	
	@Test
	public void charMatcher(){
		String string = "\t129z中国";
		String noControl = CharMatcher.JAVA_ISO_CONTROL.removeFrom(string); //移除control字符 129z中国
		System.out.println(noControl);
		String theDigits = CharMatcher.DIGIT.retainFrom(string); //只保留数字字符 129
		System.out.println(theDigits);
		String spaced = CharMatcher.WHITESPACE.trimAndCollapseFrom(string, ' ');//去除两端的空格，并把中间的连续空格替换成单个空格 129z中国
		System.out.println(spaced);
		String noDigits = CharMatcher.JAVA_DIGIT.replaceFrom(string, "*"); //用*号替换所有数字 	***z中国
		System.out.println(noDigits);
		String lowerAndDigit = CharMatcher.JAVA_DIGIT.or(CharMatcher.JAVA_LOWER_CASE).retainFrom(string);// 只保留数字和小写字母 129z
		System.out.println(lowerAndDigit);
	}
	
	@Test
	public void charsets(){
		//提供所有java平台保证支持的6种字符集
		
		//jdk old
		String string = "a";
		try {
			byte[] bytes = string.getBytes("UTF-8");
			System.out.println(Arrays.toString(bytes));
		} catch (UnsupportedEncodingException e) {
		    // how can this possibly happen?
		    throw new AssertionError(e);
		}
		
		//guava
		System.out.println(Arrays.toString("a".getBytes(Charsets.UTF_8)));
	}
	
	@Test
	public void caseFormat(){
		//大小写转换
		System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "user_name"));// userName
	}
}
