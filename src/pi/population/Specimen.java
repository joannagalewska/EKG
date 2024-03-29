package pi.population;

import pi.inputs.signal.ECG;
import pi.statistics.logic.SpecimenResult;

public class Specimen {

	private String name = "";
	private String surname = "";
	private String birth = null;
	private String id = "";
	private String path = "";
	private String pathAfter = "";

	private SpecimenResult statisticResults = null;

	private int age = -1;
	private int weight = 0;
	private int activityDuration = -1;
	private int hiv = 0;
	private int methadone = 0;
	private int metadonTimeApplication = 0;
	private int timeToGoodMood = 0;
	private int goodMoodDuration = 0;
	private ECG before;
	private ECG after;

	public ECG loadSignal(String path) {
		ECG result = new ECG();
		return result;
	}

	public boolean loadBefore(String path) {
		this.before = this.loadSignal(path);

		if (this.before == null)
			return false;
		return true;
	}

	public boolean loadAfter(String path) {
		this.setAfter(this.loadSignal(path));

		if (this.before == null)
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getActivityDuration() {
		return activityDuration;
	}

	public void setActivityDuration(int activityDuration) {
		this.activityDuration = activityDuration;
	}

	public int getHiv() {
		return hiv;
	}

	public void setHiv(int hiv) {
		this.hiv = hiv;
	}

	public int getMethadone() {
		return methadone;
	}

	public void setMethadone(int methadone) {
		this.methadone = methadone;
	}

	public int getMetadonTimeApplication() {
		return metadonTimeApplication;
	}

	public void setMetadonTimeApplication(int metadonTimeApplication) {
		this.metadonTimeApplication = metadonTimeApplication;
	}

	public int getTimeToGoodMood() {
		return timeToGoodMood;
	}

	public void setTimeToGoodMood(int timeToGoodMood) {
		this.timeToGoodMood = timeToGoodMood;
	}

	public int getGoodMoodDuration() {
		return goodMoodDuration;
	}

	public void setGoodMoodDuration(int goodMoodDuration) {
		this.goodMoodDuration = goodMoodDuration;
	}

	public ECG getAfter() {
		return after;
	}

	public void setAfter(ECG after) {
		this.after = after;
	}

	public ECG getBefore() {
		return before;
	}

	public void setBefore(ECG before) {
		this.before = before;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDetails(String[] array) {
		this.name = array[0];
		this.surname = array[1];
		this.birth = array[2];
		this.id = array[3];
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getWeight() {
		return weight;
	}

	public SpecimenResult getStatisticResults() {
		return statisticResults;
	}

	public void setStatisticResults(SpecimenResult statisticResults) {
		this.statisticResults = statisticResults;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPathAfter() {
		return pathAfter;
	}

	public void setPathAfter(String pathAfter) {
		this.pathAfter = pathAfter;
	}
}
