package com.mercado.loja.resource.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static List<Integer> decodeIntList(String s){
		String[] x = s.split(",");
		List<Integer> a = new ArrayList<>();
		for(String i : x) {
			a.add(Integer.parseInt(i));
		}
		return a;
	}
}
