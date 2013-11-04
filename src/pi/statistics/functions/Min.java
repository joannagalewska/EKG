package pi.statistics.functions;

import java.util.Vector;

import pi.inputs.signal.Probe;
import pi.statistics.logic.Function;
import pi.statistics.logic.StatisticResult;

public class Min extends Function {

    private int min = 0;
    
    public Min() {
	super("Min");
    }

    @Override
    public void countResult() {
	Vector<Double> result = new Vector<Double>();
	result.add((double)min);
	StatisticResult.addValue(this.getName(), result);
    }

    @Override
    public void iterate(Probe probe) {
	if (probe.getValue() < min) min = probe.getValue();
    }
    
    public void setName(String waveName){
	super.setName(waveName);
    }

    @Override
    public void setWaveName(String waveName) {
	// TODO Auto-generated method stub
	
    }

}