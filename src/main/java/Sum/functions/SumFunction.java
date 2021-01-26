package Sum.functions;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;

import java.util.*;

public class SumFunction extends AbstractFunction {
    private static final List<String> desc = new LinkedList<>();
    private static final String KEY = "__sum";

    static {
        desc.add("a");
        desc.add("b");
        desc.add("Name of variable in which to store the result (optional)");
    }

    private CompoundVariable varName;
    private CompoundVariable a;
    private CompoundVariable b;

    @Override
    public String execute(SampleResult sampleResult, Sampler sampler) throws InvalidVariableException {
        String n1 = a.execute().trim();
        String n2 = b.execute().trim();
        int sum;
        sum = Integer.parseInt(n1) + Integer.parseInt(n2);
        String response = String.valueOf(sum);
        if (varName != null) {
            JMeterVariables vars = getVariables();
            final String varTrim = varName.execute().trim();
            if (vars != null && varTrim.length() > 0){
                vars.put(varTrim, response);
            }
        }
        return response;
    }

    @Override
    public void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        checkParameterCount(parameters, 2, 3);
        Object[] values = parameters.toArray();

        a = (CompoundVariable) values[0];
        b = (CompoundVariable) values[1];
        if (values.length>2){
            varName = (CompoundVariable) values[2];
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
