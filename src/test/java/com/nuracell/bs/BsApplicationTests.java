package com.nuracell.bs;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;

//@SpringBootTest
class BsApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void base64Test() throws IOException {
		Base64.Encoder encoder = Base64.getEncoder();

		System.out.println("Amogus: " + Arrays.toString("Amogus".getBytes()));

		byte[] encode = encoder.encode("Amogus".getBytes());
		StringBuilder stringBuilder = new StringBuilder();
		for (byte b : encode) {
			stringBuilder.append((char) b);
		}
		System.out.println(stringBuilder + " " +Arrays.toString(encode));
//		StandardCharsets.UTF_8;
//		Charset
		FileInputStream fs = new FileInputStream("toEncode.txt");
		StringBuilder s = new StringBuilder();
		int bt;
		while((bt = fs.read()) != -1) {
			s.append((char) bt);
		}
		System.out.println(s);
		fs.close();

		Base64.Decoder decoder = Base64.getDecoder();
		System.out.println(Arrays.toString(decoder.decode(encode)));

		var usernameAndPassword = decoder.decode(s.toString());
		StringBuilder decodedPassword = new StringBuilder();
		for(byte b : usernameAndPassword) {
			decodedPassword.append((char) b);
		}

		System.out.println(decodedPassword);
	}

//	@Test
//	public void stackOverFlowTest() {
//		stackOverFlowTest();
//	}

}
