package com.example.demo;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class DemoApplication {


	public static void main(String[] args) {
		Map<String, List<Object>> map = new HashMap<>();
		map.put("users", Arrays.asList("Dhruva", "Chandra"));
		map.put("dob", Arrays.asList(1, 2, 3, 4));
		

		
	}
	//parse template
	private Map<String, String> mapStateToContext(Map<String, List<Object>> input,
	List<Integer> curr, List<String> templateVars) {
	   Map<String, String> context = new HashMap<>();
	   for(int i = 0; i < templateVars.size(); i++) {
		   String templateVar = templateVars.get(i);
		   int index = curr.get(i);
		   context.put(templateVar, input.get(templateVar).get(index).toString());
	   }
	   return context;
   }


	private Map<String, String> nextContext(Map<String, List<Object>> input,
	 List<Integer> curr, List<Integer> limits, List<String> templateVars) {
		if(!generateNext(curr, limits)) {
			return null;
		}
		return mapStateToContext(input, curr, templateVars);
	}

	

	private boolean generateNext(List<Integer> curr, List<Integer> limit) {
		int i = curr.size() - 1;
		for(; i >= 0; i--) {
			if(curr.get(i) < limit.get(i)) {
				int currValue = curr.get(i);
				curr.set(i, currValue + 1);
				for(int j = i + 1; j < curr.size(); j++) {
					curr.set(j, 0);
				}
				break;
			}
		}
		return i >= 0;
	}

	private static String generate(Map<String, String> context, String template) {
		final VelocityContext velocityContext = new VelocityContext(context);
		final StringWriter stringWriter = new StringWriter();
		final StringReader reader = new StringReader(template);
		Velocity.evaluate(velocityContext, stringWriter, "Velocity String Template Evaluation", reader);
		return stringWriter.toString();
	}

}
