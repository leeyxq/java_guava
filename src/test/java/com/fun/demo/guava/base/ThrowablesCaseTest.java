package com.fun.demo.guava.base;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

import com.google.common.base.Throwables;

/**
 * 
 * @author root
 *
 */
public class ThrowablesCaseTest {
	@Test
	public void test1() {
		try {
			throw new Exception();
		} catch (Throwable t) {
			// Guava的异常链处理方法：
			// 1.Throwable getRootCause(Throwable)
			// 2.List<Throwable> getCausalChain(Throwable)
			// 3.String getStackTraceAsString(Throwable)
			String ss = Throwables.getStackTraceAsString(t);
			System.out.println("ss:" + ss);
			Throwables.propagate(t); //如果t是一个 RuntimeException or Error的异常实例, 将会抛出一个RuntimeException
		}
	}

	@Test
	public void test2() throws IOException, SQLException {
		try {
			//throw new IOException();
			//int i = 1;
			//System.out.println(10/(i-1));
			
			throw new SQLException();
			
		} catch (Throwable t) {
			Throwables.propagateIfInstanceOf(t, SQLException.class); // 当且仅当为SQLException类型异常实例，将抛出SQLException类型异常
			Throwables.propagateIfInstanceOf(t.getCause(), IOException.class);
			throw Throwables.propagate(t);// 抛出其他异常，返回异常类型为RuntimeException
		}
	}
}
