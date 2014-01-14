package pi.statistics.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class VectorsToTests {
    private Vector<Object> numbers;
    private Map<String, Vector<Object>> values;
    private Map<String, Map<String, Vector<Object>>> waves = new HashMap<String, Map<String, Vector<Object>>>();
    private Map<String, Map<String, Map<String, Vector<Object>>>> vectors = new HashMap<String, Map<String, Map<String, Vector<Object>>>>();

    public void clearVectors() {
	getVectors().clear();
	// values.clear();
	// numbers.clear();
    }

    public void printVectors() {
//	for (String vectName : getVectors().keySet()) {
//	    System.out.println(vectName);
//	    for (String waveName : getVectors().get(vectName).keySet()) {
//		System.out.println(waveName);
//		for (String statName : getVectors().get(vectName).get(waveName)
//			.keySet()) {
//		    System.out.println(statName);
//		    System.out.println(getVectors().get(vectName).get(waveName)
//			    .get(statName));
//		}
//	    }
//	}
	System.out.println(vectors);
    }

    public Map<String, Map<String, Vector<Object>>> getWaves() {
	return waves;
    }

    public void addWave(String name, String statName, Object value) {

	if (waves.containsKey(name) == false) {
	    values = new HashMap<String, Vector<Object>>();
	    addValue(statName, value);
	    waves.put(name, values);
	} else {
	    if (waves.get(name).get(statName) == null) {
		addValue(statName, value);
	    } else {
		waves.get(name).get(statName).add(value);
	    }
	}

    }

    public Map<String, Vector<Object>> getValues() {
	return values;
    }

    public void addValue(String name, Object value) {
	numbers = new Vector<Object>();
	addNumber(value);
	values.put(name, numbers);
    }

    public Vector<Object> getNumbers() {
	return numbers;
    }

    public void addNumber(Object number) {
	numbers.add(number);
    }

    public Map<String, Map<String, Map<String, Vector<Object>>>> getVectors() {
	return vectors;
    }

    public void addVector(String name, String waveName, String statName,
	    Object value) {
	
	if (vectors.containsKey(name) == false) {
	    waves = new HashMap<String, Map<String, Vector<Object>>>();
	    addWave(waveName, statName, value);
	    vectors.put(name, waves);
	} else {
	    if (vectors.get(name).get(waveName) == null) {
		addWave(waveName, statName, value);
	    } else {
		if (vectors.get(name).get(waveName).get(statName) == null) {
		    addValue(statName, value);
		} else
		    vectors.get(name).get(waveName).get(statName).add(value);
	    }
	}

    }



}
