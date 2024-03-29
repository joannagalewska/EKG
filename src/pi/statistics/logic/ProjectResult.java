package pi.statistics.logic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.math3.stat.inference.MannWhitneyUTest;
import org.apache.commons.math3.stat.inference.TTest;
import org.apache.commons.math3.stat.inference.WilcoxonSignedRankTest;

import pi.statistics.tests.LillieAverage;
import pi.statistics.tests.LillieVariance;
import pi.statistics.tests.LillieforsNormality;
import pi.statistics.tests.Result;

public class ProjectResult {

	private PopulationResult popul1Result;
	private PopulationResult popul2Result;
	private Map<String, Map<String, Map<String, Map<String, Map<String, Vector<Double>>>>>> testResult = new HashMap<String, Map<String, Map<String, Map<String, Map<String, Vector<Double>>>>>>();
	private Map<String, Map<String, Map<String, Map<String, Vector<Double>>>>> channels;
	private Map<String, Map<String, Map<String, Vector<Double>>>> atrs;
	private Map<String, Map<String, Vector<Double>>> waves;
	private Map<String, Vector<Double>> stats;

	public void performUnpairedTest() {
		beforeUnpaired();
	}

	public void performDifferencesTest() {
		beforeUnpaired();
		afterUnpaired();
		afterBeforeDeffierences();
	}

	public void performPairedTest(int populNo) {
		PopulationResult popResult = null;
		if (populNo == 1) {
			popResult = popul1Result;
		} else if (populNo == 2)
			popResult = popul2Result;

		for (String channelName : popResult.getVectorsBefore().keySet()) {
			for (String atrName : popResult.getVectorsBefore().get(channelName)
					.keySet()) {
				for (String waveName : popResult.getVectorsBefore()
						.get(channelName).get(atrName).keySet()) {
					for (String statName : popResult.getVectorsBefore()
							.get(channelName).get(atrName).get(waveName)
							.keySet()) {
						if (!popResult.getVectorsAfter().get(channelName)
								.get(atrName).get(waveName).get(statName)
								.contains(null)
								&& !popResult.getVectorsBefore()
										.get(channelName).get(atrName)
										.get(waveName).get(statName)
										.contains(null)) {
							Vector<Object> vector1 = popResult
									.getVectorsBefore().get(channelName)
									.get(atrName).get(waveName).get(statName);
							double[] ar1 = new double[vector1.size()];
							for (int i = 0; i < vector1.size(); i++) {
								ar1[i] = (double) vector1.get(i);
							}
							Vector<Object> vector2 = popResult
									.getVectorsAfter().get(channelName)
									.get(atrName).get(waveName).get(statName);
							double[] ar2 = new double[vector2.size()];
							for (int i = 0; i < vector2.size(); i++) {
								ar2[i] = (double) vector2.get(i);
							}
							boolean normal = lillieforsTest(ar1, ar2);
							if (normal == true) {
								if (populNo == 1)
									tStudentPairedTest("P1AB", channelName,
											atrName, waveName, statName, ar1,
											ar2);
								else if (populNo == 2)
									tStudentPairedTest("P2AB", channelName,
											atrName, waveName, statName, ar1,
											ar2);
							} else {
								if (populNo == 1)
									wilcoxonTest("P1AB", channelName, atrName,
											waveName, statName, ar1, ar2);
								else if (populNo == 2)
									wilcoxonTest("P2AB", channelName, atrName,
											waveName, statName, ar1, ar2);
							}
						}
					}
				}
			}
		}
	}

	public void beforeUnpaired() {
		for (String channelName : popul1Result.getVectorsBefore().keySet()) {
			for (String atrName : popul1Result.getVectorsBefore()
					.get(channelName).keySet()) {
				for (String waveName : popul1Result.getVectorsBefore()
						.get(channelName).get(atrName).keySet()) {
					for (String statName : popul1Result.getVectorsBefore()
							.get(channelName).get(atrName).get(waveName)
							.keySet()) {
						if (!popul2Result.getVectorsBefore().get(channelName)
								.get(atrName).get(waveName).get(statName)
								.contains(null)
								&& !popul1Result.getVectorsBefore()
										.get(channelName).get(atrName)
										.get(waveName).get(statName)
										.contains(null)) {
							Vector<Object> vector1 = popul1Result
									.getVectorsBefore().get(channelName)
									.get(atrName).get(waveName).get(statName);
							double[] ar1 = new double[vector1.size()];
							for (int i = 0; i < vector1.size(); i++) {
								ar1[i] = (double) vector1.get(i);
							}
							Vector<Object> vector2 = popul2Result
									.getVectorsBefore().get(channelName)
									.get(atrName).get(waveName).get(statName);
							double[] ar2 = new double[vector2.size()];
							for (int i = 0; i < vector2.size(); i++) {
								ar2[i] = (double) vector2.get(i);
							}
							boolean normal = lillieforsTest(ar1, ar2);
							if (normal == true) {
								tStudentUnpairedTest("BB", channelName,
										atrName, waveName, statName, ar1, ar2);

							} else {
								mannWhitneyUTest("BB", channelName, atrName,
										waveName, statName, ar1, ar2);

							}
						}
					}
				}
			}
		}
	}

	public void afterUnpaired() {
		for (String channelName : popul1Result.getVectorsAfter().keySet()) {
			for (String atrName : popul1Result.getVectorsAfter()
					.get(channelName).keySet()) {
				for (String waveName : popul1Result.getVectorsAfter()
						.get(channelName).get(atrName).keySet()) {
					for (String statName : popul1Result.getVectorsAfter()
							.get(channelName).get(atrName).get(waveName)
							.keySet()) {
						if (!popul2Result.getVectorsAfter().get(channelName)
								.get(atrName).get(waveName).get(statName)
								.contains(null)
								&& !popul1Result.getVectorsAfter()
										.get(channelName).get(atrName)
										.get(waveName).get(statName)
										.contains(null)) {
							Vector<Object> vector1 = popul1Result
									.getVectorsAfter().get(channelName)
									.get(atrName).get(waveName).get(statName);
							double[] ar1 = new double[vector1.size()];
							for (int i = 0; i < vector1.size(); i++) {
								ar1[i] = (double) vector1.get(i);
							}
							Vector<Object> vector2 = popul2Result
									.getVectorsAfter().get(channelName)
									.get(atrName).get(waveName).get(statName);
							double[] ar2 = new double[vector2.size()];
							for (int i = 0; i < vector2.size(); i++) {
								ar2[i] = (double) vector2.get(i);
							}
							boolean normal = lillieforsTest(ar1, ar2);
							if (normal == true) {
								tStudentUnpairedTest("AA", channelName,
										atrName, waveName, statName, ar1, ar2);
							} else {
								mannWhitneyUTest("AA", channelName, atrName,
										waveName, statName, ar1, ar2);
							}
						}
					}
				}
			}
		}
	}

	public void afterBeforeDeffierences() {

		for (String channelName : popul1Result.getVectorsBefore().keySet()) {
			for (String atrName : popul1Result.getVectorsBefore()
					.get(channelName).keySet()) {
				for (String waveName : popul1Result.getVectorsBefore()
						.get(channelName).get(atrName).keySet()) {
					for (String statName : popul1Result.getVectorsBefore()
							.get(channelName).get(atrName).get(waveName)
							.keySet()) {
						if (!popul1Result.getVectorsAfter().get(channelName)
								.get(atrName).get(waveName).get(statName)
								.contains(null)
								&& !popul1Result.getVectorsBefore()
										.get(channelName).get(atrName)
										.get(waveName).get(statName)
										.contains(null)) {
							Vector<Object> vector1 = popul1Result
									.getVectorsBefore().get(channelName)
									.get(atrName).get(waveName).get(statName);
							Vector<Object> vector2 = popul1Result
									.getVectorsAfter().get(channelName)
									.get(atrName).get(waveName).get(statName);

							if (popul2Result.getVectorsBefore() == null) {
								continue;
							}
							if (popul2Result.getVectorsBefore()
									.get(channelName) == null) {
								continue;
							}
							if (popul2Result.getVectorsBefore()
									.get(channelName).get(atrName) == null) {
								continue;
							}
							if (popul2Result.getVectorsBefore()
									.get(channelName).get(atrName)
									.get(waveName) == null) {
								continue;
							}
							if (popul2Result.getVectorsBefore()
									.get(channelName).get(atrName)
									.get(waveName).get(statName) == null) {
								continue;
							}

							if (!popul2Result.getVectorsAfter()
									.get(channelName).get(atrName)
									.get(waveName).get(statName).contains(null)
									&& !popul2Result.getVectorsBefore()
											.get(channelName).get(atrName)
											.get(waveName).get(statName)
											.contains(null)) {
								Vector<Object> vector1P2 = popul2Result
										.getVectorsBefore().get(channelName)
										.get(atrName).get(waveName)
										.get(statName);
								Vector<Object> vector2P2 = popul2Result
										.getVectorsAfter().get(channelName)
										.get(atrName).get(waveName)
										.get(statName);
								int length;

								if (vector1.size() != vector2.size()
										|| vector1.size() != vector1P2.size()
										|| vector1P2.size() != vector2P2.size()) {
									length = vector1.size();
									if (vector2.size() < length)
										length = vector2.size();
									if (vector1P2.size() < length)
										length = vector1P2.size();
									if (vector2P2.size() < length)
										length = vector2P2.size();
								} else {
									length = vector1.size();
								}

								double[] ar1 = new double[length];
								for (int i = 0; i < length; i++) {
									ar1[i] = (double) vector2.get(i)
											- (double) vector1.get(i);
								}

								double[] ar2 = new double[length];
								for (int i = 0; i < length; i++) {
									ar2[i] = (double) vector2P2.get(i)
											- (double) vector1P2.get(i);
								}

								boolean normal = lillieforsTest(ar1, ar2);
								if (normal == true) {
									tStudentPairedTest("dAB", channelName,
											atrName, waveName, statName, ar1,
											ar2);
								} else {
									mannWhitneyUTest("dAB", channelName,
											atrName, waveName, statName, ar1,
											ar2);
								}
							}

						}
					}
				}
			}
		}
	}

	public boolean lillieforsTest(double[] ar1, double[] ar2) {
		LillieforsNormality.compute(ar1, 5, false);
		boolean normal = LillieforsNormality.isTrueForAlpha(0.05);
		if (normal == true) {
			LillieforsNormality.compute(ar2, 5, false);
			normal = LillieforsNormality.isTrueForAlpha(0.05);
		}
		return normal;
	}

	public void tStudentPairedTest(String testName, String channelName,
			String atrName, String waveName, String statName, double[] ar1,
			double[] ar2) {
		TTest test = new TTest();
		Vector<Double> result = new Vector<Double>();
		calculateStatistics(result, ar1);
		calculateStatistics(result, ar2);
		result.add(1.0d);
		result.add(1.0d);
		double pval = 0.0d;
		try {
			pval = test.pairedTTest(ar1, ar2);
		} catch (Exception ex) {
		}
		result.add(pval);
		addTestResult(testName, channelName, atrName, waveName, statName,
				result);
	}

	public void tStudentUnpairedTest(String testName, String channelName,
			String atrName, String waveName, String statName, double[] ar1,
			double[] ar2) {
		TTest test = new TTest();
		Vector<Double> result = new Vector<Double>();
		calculateStatistics(result, ar1);
		calculateStatistics(result, ar2);
		result.add(1.0d);
		result.add(-1.0d);
		double pval = 0.0d;
		try {
			pval = test.tTest(ar1, ar2);
		} catch (Exception ex) {

		}
		result.add(pval);
		addTestResult(testName, channelName, atrName, waveName, statName,
				result);
	}

	public void wilcoxonTest(String testName, String channelName,
			String atrName, String waveName, String statName, double[] ar1,
			double[] ar2) {
		WilcoxonSignedRankTest wilcoxTest = new WilcoxonSignedRankTest();
		Vector<Double> result = new Vector<Double>();
		calculateStatistics(result, ar1);
		calculateStatistics(result, ar2);
		result.add(-1.0d);
		result.add(1.0d);
		double pval = 0.0d;
		try {
			if (ar1.length <= 30 && ar2.length <= 30) {
				pval = wilcoxTest.wilcoxonSignedRankTest(ar1, ar2, true);
				result.add(pval);
				addTestResult(testName, channelName, atrName, waveName,
						statName, result);
			} else {
				pval = wilcoxTest.wilcoxonSignedRankTest(ar1, ar2, false);
				result.add(pval);
				addTestResult(testName, channelName, atrName, waveName,
						statName, result);
			}
		} catch (Exception ex) {
		}
		result.add(pval);
		addTestResult(testName, channelName, atrName, waveName, statName,
				result);

	}

	public void mannWhitneyUTest(String testName, String channelName,
			String atrName, String waveName, String statName, double[] ar1,
			double[] ar2) {
		MannWhitneyUTest mannWhitneyUTest = new MannWhitneyUTest();
		Vector<Double> result = new Vector<Double>();
		calculateStatistics(result, ar1);
		calculateStatistics(result, ar2);
		result.add(-1.0d);
		result.add(-1.0d);
		double pval = 0.0d;
		try {
			pval = mannWhitneyUTest.mannWhitneyUTest(ar1, ar2);
		} catch (Exception ex) {
		}
		result.add(pval);
		addTestResult(testName, channelName, atrName, waveName, statName,
				result);
	}

	public double[] vectorToDouble(Vector<Object> vector, int length) {
		double[] result = new double[length];
		Iterator<Object> it = vector.iterator();
		Double value = 0.0d;
		int place = 0;

		while (it.hasNext()) {
			try {
				value = (double) it.next();
				result[place] = value;
				place++;
			} catch (Exception ex) {

			}
			if (place >= length)
				break;
		}

		return result;
	}

	public double[] vectorToDouble(Vector<Object> vector) {
		return this.vectorToDouble(vector, vector.size());
	}

	public void calculateStatistics(Vector<Double> result, double[] list) {
		Result avg = new Result();
		Result var = new Result();

		LillieAverage.init(avg);
		for (int i = 0; i < list.length; i++)
			LillieAverage.iterate(list[i]);
		LillieAverage.finish();

		LillieVariance.init(var, avg.getValue().get(0));

		for (int i = 0; i < list.length; i++)
			LillieVariance.iterate(list[i]);

		LillieVariance.finish();

		result.add(avg.getValue().get(0));
		double sd = Math.sqrt(var.getValue().get(0));
		result.add(sd);
	}

	public PopulationResult getPopul1() {
		return popul1Result;
	}

	public void setPopul1(PopulationResult popul1) {
		this.popul1Result = popul1;
	}

	public PopulationResult getPopul2() {
		return popul2Result;
	}

	public void setPopul2(PopulationResult popul2) {
		this.popul2Result = popul2;
	}

	public Map<String, Map<String, Map<String, Map<String, Map<String, Vector<Double>>>>>> getTestResult() {
		return testResult;
	}

	public void addTestResult(String name, String channel, String atr,
			String wave, String statistic, Vector<Double> result) {
		if (testResult.containsKey(name) == false) {
			channels = new HashMap<String, Map<String, Map<String, Map<String, Vector<Double>>>>>();
			addChannels(channel, atr, wave, statistic, result);
			testResult.put(name, channels);
		} else if (testResult.get(name).get(channel) == null) {
			addChannels(channel, atr, wave, statistic, result);
		} else if (testResult.get(name).get(channel).get(atr) == null) {
			addAtrs(atr, wave, statistic, result);
		} else if (testResult.get(name).get(channel).get(atr).get(wave) == null) {
			addWaves(wave, statistic, result);
		} else if (testResult.get(name).get(channel).get(atr).get(wave)
				.get(statistic) == null) {
			addStats(statistic, result);
		}

	}

	public Map<String, Map<String, Map<String, Map<String, Vector<Double>>>>> getChannels() {
		return channels;
	}

	public void addChannels(String channel, String atr, String wave,
			String statistic, Vector<Double> result) {
		if (channels.containsKey(channel) == false) {
			atrs = new HashMap<String, Map<String, Map<String, Vector<Double>>>>();
			addAtrs(atr, wave, statistic, result);
			channels.put(channel, atrs);
		} else if (channels.get(channel).get(atr) == null) {
			addAtrs(atr, wave, statistic, result);
		} else if (channels.get(channel).get(atr).get(wave) == null) {
			addWaves(wave, statistic, result);
		} else if (channels.get(channel).get(atr).get(wave).get(statistic) == null) {
			addStats(statistic, result);
		}

	}

	public Map<String, Map<String, Vector<Double>>> getWaves() {
		return waves;
	}

	public void addWaves(String wave, String statistic, Vector<Double> result) {
		if (waves.containsKey(wave) == false) {
			stats = new HashMap<String, Vector<Double>>();
			addStats(statistic, result);
			waves.put(wave, stats);
		} else if (waves.get(wave).get(statistic) == null) {
			addStats(statistic, result);
		}

	}

	public Map<String, Vector<Double>> getStats() {
		return stats;
	}

	public void addStats(String statistic, Vector<Double> result) {
		stats.put(statistic, result);
	}

	public Map<String, Map<String, Map<String, Vector<Double>>>> getAtrs() {
		return atrs;
	}

	public void addAtrs(String atr, String wave, String statistic,
			Vector<Double> result) {
		if (atrs.containsKey(atr) == false) {
			waves = new HashMap<String, Map<String, Vector<Double>>>();
			addWaves(wave, statistic, result);
			atrs.put(atr, waves);
		} else if (atrs.get(atr).get(wave) == null) {
			addWaves(wave, statistic, result);
		} else if (atrs.get(atr).get(wave).get(statistic) == null) {
			addStats(statistic, result);
		}
	}

}
