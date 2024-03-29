package pi.statistics.functions;

import pi.statistics.logic.Function;
import pi.statistics.logic.StatisticResult;

public class SD extends Function {

	public SD() {
		super("SD");
	}

	@Override
	public void countResult(StatisticResult statResult) {
		double sd = 0;
		double var = statResult.getValue().get("Variance").doubleValue();
		if (var != 0) {
			sd = Math.sqrt(var);
		}
		statResult.addValue(this.getName(), sd);
	}

	@Override
	public void iterate(double value) {

	}

	@Override
	public void backToBegin() {

	}

}
