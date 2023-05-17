package com.example.demo;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * An iterator that generates templated strings based on combinations of
 * template variable values.
 */
public class TemplateIterator implements Iterator<String> {
    /**
     * The template string used for generating the output strings.
     */
    private final String template;
    /**
     * The mapping between template variables and lists of possible values for each
     * variable.
     */
    private final Map<String, List<Object>> input;
    /**
     * The current state of the iterator, which represents the index of values for
     * each template variable.
     */
    private final List<Integer> state;

    /**
     * The number of possible values for each template variable.
     */
    private final List<Integer> limits;
    /**
     * The list of input template variables.
     */
    private final List<String> templateVars;

    /**
     * Since the state starts all zeros for generating the first pair using a flag.
     */
    private boolean inital = true;

    /**
     * The context map holding the values for the current state.
     */
    private Map<String, Object> context;

    /**
     * Constructs a TemplateIterator with the specified template and input map.
     *
     * @param template The template string to use for generating the output strings.
     * @param input    The mapping of template variables to lists of possible values
     *                 for each variable.
     */
    public TemplateIterator(String template, Map<String, List<Object>> input) {
        this.template = template;
        this.input = input;
        this.state = new ArrayList<>();
        this.limits = new ArrayList<>();
        this.context = new HashMap<>();
        this.templateVars = input.keySet().stream()
                .filter(templateVar -> !input.get(templateVar).isEmpty())
                .collect(Collectors.toList());

        for (int i = 0; i < templateVars.size(); i++) {
            state.add(0);
            this.updateContext(i, 0);
            limits.add(input.get(templateVars.get(i)).size());
        }
    }

    /**
     * Checks if there is a next combination of template variable values to
     * generate.
     *
     * @return {@code true} if there is a next combination, {@code false} otherwise.
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
                this.state.set(i, currValue + 1);
                this.updateContext(i, currValue + 1);
                break;
            } else {
                this.state.set(i, 0);
                this.updateContext(i, 0);
            }
        }
        return i >= 0;
    }

    /**
     * Generates the next templated string based on the current state.
     *
     * @return The next templated string.
     */
    @Override
    public String next() {
        return generate(context, template);
    }

    /**
     * Updates the context map with the value of the specified input index for the
     * given template variable index.
     *
     * @param indexOfTemplateVar   The index of the template variable.
     * @param indexOfInputToChoose The index of the input value to choose.
     */
    private void updateContext(int indexOfTemplateVar, int indexOfInputToChoose) {
        String templateVar = this.templateVars.get(indexOfTemplateVar);
        context.put(templateVar, this.input.get(templateVar).get(indexOfInputToChoose));
    }

    /**
     * Generates a templated string using the provided context and template string.
     *
     * @param context  The context map containing the values for template variables.
     * @param template The template string to be evaluated.
     * @return The generated templated string.
     */
    private static String generate(Map<String, Object> context, String template) {
        final VelocityContext velocityContext = new VelocityContext(context);
        final StringWriter stringWriter = new StringWriter();
        final StringReader reader = new StringReader(template);
        Velocity.evaluate(velocityContext, stringWriter, "Velocity String Template Evaluation", reader);
        return stringWriter.toString();
    }
}
