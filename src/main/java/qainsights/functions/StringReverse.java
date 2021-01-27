package qainsights.functions;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;

import java.util.*;

public class StringReverse extends AbstractFunction {
    private static final List<String> desc = new LinkedList<>();
    private static final String KEY = "__strReverse";

    static {
        desc.add("Enter the string");
        desc.add("Name of variable in which to store the result (optional)");
    }

    private CompoundVariable varName;
    private CompoundVariable stringInput;

    @Override
    public String execute(SampleResult sampleResult, Sampler sampler) throws InvalidVariableException {
        String str = stringInput.execute().trim();
        StringBuilder response = new StringBuilder();
        response.append(str);
        response.reverse();

        if (varName != null) {
            JMeterVariables vars = getVariables();
            final String varTrim = varName.execute().trim();
            if (vars != null && varTrim.length() > 0){
                vars.put(varTrim, String.valueOf(response));
            }
        }
        return String.valueOf(response);
    }

    @Override
    public void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkParameterCount(parameters, 1, 2);
        Object[] values = parameters.toArray();

        stringInput = (CompoundVariable) values[0];

        if (values.length>1){
            varName = (CompoundVariable) values[1];
        } else {
            varName = null;
        }
    }

    @Override
    public String getReferenceKey() {
        return KEY;
    }

    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }
}