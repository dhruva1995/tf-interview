package com.example.demo;

import java.util.*;



public class Driver {




	public static void main(String[] args) {
		Map<String, List<Object>> inputs = new HashMap<>();
		inputs.put("user", Arrays.asList("Dhruva", "Kalyan", "MRND"));
		inputs.put("dob", Arrays.asList(1, 2, 3, 4));
		
		String template = "{ 'user': '$user', 'dob': '$dob' }";
		
		//Usage ...
		TemplateIterator it = new TemplateIterator(template, inputs);

		while(it.hasNext()) {
			System.out.println(it.next());
		}

		
		
		
		
	}
	
	

	


	


}
