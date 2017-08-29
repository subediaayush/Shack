package com.tin.shack.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by aayushsubedi on 8/8/17.
 */

public class Serializer {
	public static byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		try {
			ObjectOutputStream o = new ObjectOutputStream(b);
			o.writeObject(obj);
			o.close();
			
			return b.toByteArray();
		} finally {
			b.close();
		}
	}
	
	public static <E> E deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream b = new ByteArrayInputStream(bytes);
		try {
			ObjectInputStream o = new ObjectInputStream(b);
			return (E) o.readObject();
		} finally {
			b.close();
		}
	}
}
