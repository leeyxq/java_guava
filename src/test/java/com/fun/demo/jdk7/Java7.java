package com.fun.demo.jdk7;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import com.google.common.base.Charsets;

/**
 * java new futures see to:
 * http://www.oracle.com/technetwork/java/javase/jdk7-relnotes-418459.html
 * 
 * @author root
 *
 */
public class Java7 {

	/**
	 * 语言 增强
	 * 
	 * @throws SQLException
	 */
	@Test
	public void languageEnhancements() {

		// 1. switch 语句中支持String类型变量
		String string = "case";
		switch (string) {
		case "hello":
			System.out.println("hello");
			break;
		case "case":
			System.out.println("case");
			break;
		default:
			System.out.println("other");
			break;
		}

		// 2. 在一个catch()中可以捕获多个异常
		try {
			Integer.parseInt("Hello");
		} catch (NumberFormatException | NullPointerException e) {

		}

		// 3. 泛型自动推断
		Map<String, List<Object>> trades = new TreeMap<>();

		// 4. 自动资源管理 不用再手动关闭流了
		String path = "/root/workspace/guava/src/test/java/com/fun/demo/guava/base/Java7.java";
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			System.out.println(br.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (InputStream isInputStream = new FileInputStream(path); BufferedInputStream bis = new BufferedInputStream(isInputStream)) {
			byte[] bufBytes = new byte[4];
			bis.read(bufBytes);
			System.out.println(new String(bufBytes));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 4. 简化可变参数方法调用
		List<Integer> list = Arrays.asList(1, 2, 3, 4);
		List<String> stooges = Arrays.asList("Larry", "Moe", "Curly");
		String[] array = stooges.toArray(new String[4]);

		// 5. 带有下划线的数字字面量
		int i = 1_000_000;

		// 6. 二进制字面量
		byte b = 0b1;
		final short[] HAPPY_FACE = { (short) 0b0000011111100000, (short) 0b0000100000010000, (short) 0b0001000000001000, (short) 0b0010000000000100, (short) 0b0100000000000010,
				(short) 0b1000011001100001, (short) 0b1000011001100001, (short) 0b1000000000000001, (short) 0b1000000000000001, (short) 0b1001000000001001, (short) 0b1000100000010001,
				(short) 0b0100011111100010, (short) 0b0010000000000100, (short) 0b0001000000001000, (short) 0b0000100000010000, (short) 0b0000011111100000 };
		int anInt1 = 0b10100001010001011010000101000101;
		long aLong = 0b1010000101000101101000010100010110100001010001011010000101000101L;
		byte aByte = (byte) 0b00100001;

	}

	@Test
	public void io() {
		// 新增子包 ava.nio.file 里面有Path, Paths, FileSystem, FileSystems

		// Path
		Path path = Paths.get("/root/workspace/guava/src/test/java/com/fun/demo/guava/base/T1.java");
		System.out.println(path.getNameCount());// 路径节点数 深度 12
		System.out.println(path.getRoot());// 根路径 /
		System.out.println(path.getParent());// 父路径
												// /root/workspace/guava/src/test/java/com/fun/demo/guava/base

		// 1. 文件更改通知
		try {
			Path path2 = Paths.get("/root/workspace/guava/src/test/java/com/fun/demo/guava/base");
			final WatchService watchService = FileSystems.getDefault().newWatchService();
			path2.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
			new Thread(new Runnable() {
				@Override
				public void run() {
					WatchKey key = null;
					while (true) {
						try {
							key = watchService.take();
							for (WatchEvent<?> event : key.pollEvents()) {
								Kind<?> kind = event.kind();
								System.out.println("Event on " + event.context().toString() + " is " + kind);
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 2. 文件删存
		// try {
		// java.nio.file.Files.delete(path);//会抛出异常的文件删除
		// java.nio.file.Files.deleteIfExists(path);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		// 3. 小文件读写
		try {
			byte[] bytes = Files.readAllBytes(Paths.get("/root/workspace/guava/src/test/java/com/fun/demo/guava/base/Java7.java"));
			List<String> lineStrings = Files.readAllLines(Paths.get("/root/workspace/guava/src/test/java/com/fun/demo/guava/base/Java7.java"), Charset.forName("utf-8"));
			System.out.println(Arrays.toString(bytes));
			for (String string : lineStrings) {
				System.out.println(string);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 4. 带buffer的文件读写
		Charset charset = Charset.forName("utf-8");
		try (BufferedReader reader = Files.newBufferedReader(Paths.get("/root/workspace/guava/src/test/java/com/fun/demo/guava/base/Java7.java"), charset)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}

		// 5 zip 文件系统支持
		Path zipfile = Paths.get("/root/apache-tomcat-6.0.45.zip");
		try (FileSystem fs = FileSystems.newFileSystem(zipfile, Java7.class.getClassLoader())) {
			Path path2 = fs.getPath("/apache-tomcat-6.0.45/RUNNING.txt");
			System.out.println(Files.readAllLines(path2, Charsets.UTF_8).get(0));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 6. 提供一个缓缓存的
		try (BufferedReader bReader = Files.newBufferedReader(path, Charsets.UTF_8)) {

		} catch (IOException e) {
			e.printStackTrace();
		}

		// 7. 流copy
		try {
			Path p1 = Paths.get("dest3.txt");
			Files.copy(path, Files.newOutputStream(p1, StandardOpenOption.CREATE));
			FileSystem fs = p1.getFileSystem();
			System.out.println(p1.getParent() + "/" + p1.getFileName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		new Java7().io();
	}
}
