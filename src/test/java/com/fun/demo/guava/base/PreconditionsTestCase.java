package com.fun.demo.guava.base;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import org.junit.Test;

/**
 * 预置条件检测
 * @author lixq
 *
 */
public class PreconditionsTestCase {

	@Test
	public void base(){
		//guava建议将Preconditions配置成静态导入 eclipse静态导入配置参见：http://www.cnblogs.com/javaexam2/archive/2011/07/26/2632541.html
		
		Integer age = -10;
		checkArgument(age>0, "传入参数age必须大于0，当前传入值为:%d", age);//检测参数是否为true，抛出IllegalArgumentException
		
		checkNotNull(age);//检测参数是否为null，抛出NullPointerException jdk1.7 使用Objects.requireNonNull
		
		checkState(age > 18, "未成年人不可观看，您的当前年龄为:%s" ,age);//检查对象的一些状态,抛出IllegalStateException，如 Iterator可以用来next是否在remove之前被调用
	}
	
	
}
