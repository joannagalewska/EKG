package pi.statistics.logic;

public class SpecimenResult {
    private ChannelResult after;
    private ChannelResult before;
  
//    public void compareResult() {
//	System.out.println("START");
//	for (String name : before.getValue().keySet()) {// PO CHANNELACH
//	    for (String waveName : before.getValue().get(name).getWavesResult()
//		    .keySet()) {// PO WAVE
//		for (String statName : before.getValue().get(name).getWavesResult()
//			.get(waveName).getValue().keySet()) {// PO STATYSTYKACH
//		    double varBefore = before.getValue().get(name).getWavesResult()
//			    .get(waveName).getValue().get(statName);
//
//		    for (String nameAfter : after.getValue().keySet()) {
//
//			if (name.equals(nameAfter)) {
//			    for (String waveNameAfter : after.getValue()
//				    .get(nameAfter).getWavesResult().keySet()) {
//				if (waveName.equals(waveNameAfter)) {
//				    for (String statNameAfter : after
//					    .getValue().get(nameAfter)
//					    .getWavesResult().get(waveNameAfter)
//					    .getValue().keySet()) {
//					if (statName.equals(statNameAfter)) {
//
//					    
//					    double varAfter = after.getValue()
//						    .get(nameAfter).getWavesResult()
//						    .get(waveNameAfter)
//						    .getValue()
//						    .get(statNameAfter);
//
//					    if (varBefore > varAfter) {
//						System.out.println("Przed wi�ksze, kanal: " + name + ", wave: " + waveName + ", statystyka: " + statName);
//						// TODO wniosek do raportu
//					    } else if (varBefore < varAfter) {
//						System.out.println("Po wi�ksze, kanal: " + name + ", wave: " + waveName + ", statystyka: " + statName);
//						// TODO wniosek do raportu
//					    } else {
//						System.out.println("Rowne, kanal: " + name + ", wave: " + waveName + ", statystyka: " + statName);
//						// TODO wniosek do raportu
//					    }
//					}
//				    }
//				}
//			    }
//			}
//		    }
//		}
//	    }
//	}
//    }
    public static String[] channelsList = { "I", "II", "III", "V1", "V2", "V3", "V4",
	    "V5", "V6" };
public static String[] statsList = { "Average", "Max", "Min", "Amplitude",
	    "Variance", "SD" };
public static String[] wavesList = { "pWave", "tWave", "uWave", "prInterval",
	    "prSegment", "stSegment", "stInterval", "qtInterval",  "qrsComplex", "J-point", "RR_interval"};

    public void addToVectors(VectorsToTests vectors, ChannelResult result){

	for (String channelName : channelsList) {
	    for (String waveName : wavesList) {
		for (String statName :statsList) {
		    try {
			 double value = result.getValue().get(channelName).getWavesResult()
				    .get(waveName).getValue().get(statName);
			    vectors.addVector(channelName, waveName, statName, value);
		    }
		    catch (Exception ex){
			vectors.addVector(channelName, waveName, statName, null);
		    }
//	for (String name : result.getValue().keySet()) {// PO CHANNELACH
//	    for (String waveName : result.getValue().get(name).getWavesResult()
//		    .keySet()) {// PO WAVE
//		for (String statName : result.getValue().get(name).getWavesResult()
//			.get(waveName).getValue().keySet()) {// PO STATYSTYKACH
//		    double value = result.getValue().get(name).getWavesResult()
//			    .get(waveName).getValue().get(statName);
//		    vectors.addVector(name, waveName, statName, value);
		}
	    }
	}
    }
    
    public ChannelResult getAfter() {
	return after;
    }

    public void setAfter(ChannelResult after) {
	this.after = after;
    }

    public ChannelResult getBefore() {
	return before;
    }

    public void setBefore(ChannelResult before) {
	this.before = before;
    }
}
