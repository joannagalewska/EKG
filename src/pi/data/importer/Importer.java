package pi.data.importer;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import pi.inputs.signal.Channel;
import pi.inputs.signal.ECG;
import pi.inputs.signal.Probe;
import pi.population.Specimen;

public class Importer {

	private Document document;
	private String filePath;

	public Importer(String filePath) throws DocumentException {
		try {
			this.setFilePath(filePath);
			document = loadDocument(filePath);
		} catch (DocumentException docEx) {
			JOptionPane.showMessageDialog(null,
					"Something wrong with given file!");
		}
	}

	public String[] getAttributes() throws DocumentException {

		String[] test = new String[4];
		String xPath;
		List<?> nodes;

		xPath = "//patient";
		nodes = document.selectNodes(xPath);

		if (nodes.iterator().hasNext()) {
			Node node = (Node) nodes.iterator().next();
			test[0] = node.valueOf("@surname");
			test[1] = node.valueOf("@firstName");
			test[2] = node.valueOf("@birthDate");
			test[3] = node.valueOf("@ID");
		}

		return test;
	}

	public String getName() throws DocumentException {
		String[] test = new String[4];
		String xPath = "//patient";
		List<?> nodes = document.selectNodes(xPath);
		String name = null;

		if (nodes.iterator().hasNext()) {
			Node node = (Node) nodes.iterator().next();
			test[0] = node.valueOf("@surname");
			test[1] = node.valueOf("@firstName");
		}

		name = test[0] + " " + test[1];
		return name;
	}

	public Specimen importSpecimen() throws DocumentException {
		Specimen spec = new Specimen();

		ArrayList<ECG> vectorOfSignals = importSignals();
		if (vectorOfSignals.get(0) != null) {
		}
		if (vectorOfSignals.get(1) != null) {
		}

		return spec;
	}

	public ArrayList<ECG> importSignals() throws DocumentException {

		String xPath = "//ekgSignal";
		List<?> nodes = document.selectNodes(xPath);
		int size = nodes.size();
		ArrayList<ECG> vectorOfSignals = new ArrayList<>(size);

		int index = 0;
		for (Iterator<?> i = nodes.iterator(); i.hasNext();) {
			Node node = (Node) i.next();
			double interval = 1.0d / Double.parseDouble(node
					.valueOf("@frequency"));
			ECG ecg = new ECG();
			ecg.setChannel(importWaves(node, interval, ecg));
			ecg.findAll();
			vectorOfSignals.add(index, ecg);
			index++;
		}

		return vectorOfSignals;
	}

	public ArrayList<Channel> importWaves(Node signal, double interval, ECG ecg)
			throws DocumentException {

		String xPath = "./ekgWave";
		List<?> nodes = signal.selectNodes(xPath);
		int size = nodes.size();
		ArrayList<Channel> result = new ArrayList<>(size);

		int index = 0;
		for (Iterator<?> i = nodes.iterator(); i.hasNext();) {
			Node node = (Node) i.next();
			String[] values = node.getText().split("[ /\t/\n]");
			ArrayList<Probe> probes = new ArrayList<>(values.length);
			Channel channel = new Channel();
			channel.setName(node.valueOf("@lead"));

			int min = Integer.MAX_VALUE;
			int max = Integer.MIN_VALUE;

			for (int probeNo = 0; probeNo < values.length; probeNo++) {
				int probeValue = Integer.parseInt(values[probeNo]);
				Probe probe = new Probe(probeNo, probeValue);

				if (probeValue > max)
					max = probeValue;
				if (probeValue < min)
					min = probeValue;

				probes.add(probeNo, probe);
			}

			channel.setProbe(probes);
			channel.setTranslation(0.0d);
			channel.setInterval(interval);
			channel.setMaxValue((double) max / 1000.0d);
			channel.setMinValue((double) min / 1000.0d);
			channel.setStartAxis(0.0d);
			channel.setScale(0.2d);
			channel.setParent(ecg);
			channel.recalculate();
			result.add(index, channel);
			index++;
		}

		return result;
	}

	private Document loadDocument(String path) throws DocumentException {
		File inputFile = new File(path);
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputFile);
		return document;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
