package com.example.demo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Driver {

	public static void main(String[] args) {
		Map<String, List<Object>> inputs = new HashMap<>();
		inputs.put("user", Arrays.asList("Dhruva", "Chandra", null));
		inputs.put("dob", Arrays.asList(1, 2, 3, 4));
		inputs.put("address", Arrays.asList());
		
		String template = "{ 'user': '$user', 'dob': '$dob' , address: $address}";
		
		//Usage ...
		TemplateIterator it = new TemplateIterator(template, inputs);

		while(it.hasNext()) {
			System.out.println(it.next());
		}
	}
}
