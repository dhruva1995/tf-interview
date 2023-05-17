package com.example.demo;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class TemplateIterator implements Iterator<String> {
    private final String template;
    private final Map<String, List<Object>> input;
    /**
     * Holds the index for the values in the input
     */
    private final List<Integer> state;
    /**
     * Holds the size of combinations of each input
     */
    private final List<Integer> limits;
    /**
     * Hols the input template variables
     */
    private final List<String> templateVars;
    private boolean inital = true;

    public TemplateIterator(String template, Map<String, List<Object>> inputs) {
        this.template = template;
        this.input = inputs;

        templateVars = new ArrayList<>(input.keySet());

        state = new ArrayList<>();

        limits = new ArrayList<>();
        for (int i = 0; i < templateVars.size(); i++) {
            state.add(0);
            limits.add(input.get(templateVars.get(i)).size());
            inital |= input.get(templateVars.get(i)).size() > 0;
        }
    }

    /**
     * Takes time proportional to the number of template variables.
     * Updates the state for the next operation.
     */
    @Override
    public boolean hasNext() {
        if (inital) {
            inital = false;
            return true;
        }
        int i = state.size() - 1;
        for (; i >= 0; i--) {

            if (state.get(i) < limits.get(i) - 1) {
                int currValue = state.get(i);
                state.set(i, currValue + 1);
                break;
            } else {
                state.set(i, 0);
            }
        }
        return i >= 0;
    }

    /**
     * Generates template with the current state.
     */
    @Override
    public String next() {
        Map<String, String> context = mapStateToContext(input, state, templateVars);
        return generate(context, template);
    }

    private Map<String, String> mapStateToContext(Map<String, List<Object>> input,
            List<Integer> curr, List<String> templateVars) {
        Map<String, String> context = new HashMap<>();
        for (int i = 0; i < templateVars.size(); i++) {
            String templateVar = templateVars.get(i);
            int index = curr.get(i);
            String value;
            if (index < limits.get(i)) {
                value = input.get(templateVar).get(index).toString();
            } else {
                value = templateVar;
            }
            context.put(templateVar, value);
        }
        return context;
    }

    private static String generate(Map<String, String> context, String template) {
        final VelocityContext velocityContext = new VelocityContext(context);
        final StringWriter stringWriter = new StringWriter();
        final StringReader reader = new StringReader(template);
        Velocity.evaluate(velocityContext, stringWriter, "Velocity String Template Evaluation", reader);
        return stringWriter.toString();
    }
}
