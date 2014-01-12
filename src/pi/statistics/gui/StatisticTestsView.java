package pi.statistics.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import pi.project.Project;
import pi.shared.SharedController;
import pi.statistics.gui.histogram.Histogram;

public class StatisticTestsView extends JFrame {
    private static final long serialVersionUID = 1L;

    private StatisticTestsController controller;

    public JLabel channelLabel = new JLabel("Channel");

    private JComboBox<String> channelCombo = new JComboBox<String>();
    private JList<String> wavesList;

    private String channelStr = "I";
    private String waveStr = "pWave";

    private JButton closeButton = new JButton("Close");
    private JButton saveButton = new JButton("Save");

    private Histogram histogram = new Histogram();

    public class MyTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean isCellEditable(int row, int column) {
	    return false;
	}
    }

    private JTabbedPane tabbedPane = new JTabbedPane();

    private JTable report = new JTable();
    private DefaultTableModel model = new MyTableModel();
    private JScrollPane reportPane = new JScrollPane(report);

    private JPanel detailPanel = new JPanel();

    private JLabel hypoTestLabel = new JLabel("Test performed");
    private JLabel hypoEqualLabel = new JLabel("P-Value");
    private JLabel hypoRightLabel = new JLabel("Right sided test");
    private JLabel hypoLeftLabel = new JLabel("Left sided test");

    private JTextField hypoTestEdit = new JTextField();
    private JTextField hypoEqualEdit = new JTextField();
    private JTextField hypoRightEdit = new JTextField();
    private JTextField hypoLeftEdit = new JTextField();

    public StatisticTestsView() {
	this.setTitle("Statistics");

	this.setLayout(null);
	this.setSize(new Dimension(1000, 500));
	this.setResizable(false);

	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	this.setLocation(x, y);

	this.controller = new StatisticTestsController(this);

	this.channelLabel.setBounds(15, 20, 100, 15);
	this.add(this.channelLabel);

	this.channelCombo.setBounds(55, 18, 100, 19);
	this.channelCombo.setActionCommand("CHANGE_FIGURE");
	this.channelCombo.addActionListener(controller);
	fillChannelCombo();
	this.add(this.channelCombo);

	fillWavesList();
	this.wavesList.setBounds(15, 45, 140, 390);
	this.wavesList.addListSelectionListener(new ListSelectionListener() {
	    @Override
	    public void valueChanged(ListSelectionEvent arg0) {
		prepare(getChannelStr(), getWavesList().getSelectedValue());
		report.changeSelection(0, 1, false, false);
		changeSelection();
	    }
	});
	this.add(this.wavesList);

	this.closeButton.setActionCommand("CLOSE");
	this.closeButton.addActionListener(controller);
	this.closeButton.setBounds(15, 440, 140, 25);
	this.add(this.closeButton);

	this.saveButton.setActionCommand("SAVE");
	this.saveButton.addActionListener(controller);
	this.saveButton.setBounds(850, 440, 140, 25);
	this.add(this.saveButton);

	this.report.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	this.report.setCellSelectionEnabled(true);
	this.report.setDragEnabled(false);

	this.report.addMouseListener(new java.awt.event.MouseAdapter() {
	    @Override
	    public void mouseClicked(java.awt.event.MouseEvent evt) {
		changeSelection();
	    }
	});

	this.detailPanel.setBounds(165, 18, 820, 80);
	this.add(this.detailPanel);

	this.detailPanel.setLayout(null);
	this.detailPanel.setBorder(BorderFactory.createTitledBorder("Details"));

	this.hypoTestLabel.setBounds(10, 25, 110, 15);
	this.detailPanel.add(this.hypoTestLabel);
	this.hypoTestEdit.setBounds(125, 23, 150, 20);
	this.detailPanel.add(this.hypoTestEdit);

	this.hypoEqualLabel.setBounds(10, 50, 110, 15);
	this.detailPanel.add(this.hypoEqualLabel);
	this.hypoEqualEdit.setBounds(125, 48, 150, 20);
	this.detailPanel.add(this.hypoEqualEdit);

	this.hypoRightLabel.setBounds(300, 25, 110, 15);
	this.detailPanel.add(this.hypoRightLabel);
	this.hypoRightEdit.setBounds(415, 23, 150, 20);
	this.detailPanel.add(this.hypoRightEdit);

	this.hypoLeftLabel.setBounds(300, 50, 110, 15);
	this.detailPanel.add(this.hypoLeftLabel);
	this.hypoLeftEdit.setBounds(415, 48, 150, 20);
	this.detailPanel.add(this.hypoLeftEdit);

	this.reportPane.setBounds(165, 100, 820, 337);
	this.report.getTableHeader().setReorderingAllowed(false);

	this.histogram.setBounds(165, 100, 820, 337);
	this.histogram.recalculate();
	this.histogram.draw();

	this.tabbedPane.setBounds(165, 100, 820, 337);
	this.tabbedPane.add("Results", this.reportPane);
	this.tabbedPane.add("Histogram", this.histogram);
	this.add(this.tabbedPane);

    }

    public void changeSelection() {
	int row = report.getSelectedRow();
	int column = report.getSelectedColumn();

	if ((row == -1) || (column == -1))
	    return;

	String channel = getChannelStr();
	String wave = getWaveStr();

	String statistics = model.getValueAt(row, 0).toString();

	controller.setDetails(column, channel, wave, statistics);
    }

//    class ShowThread implements Runnable {
//
//	StatisticTestsView view;
//
//	public ShowThread(StatisticTestsView view) {
//	    this.view = view;
//	}
//
//	public void run() {
//	    Project project = SharedController.getInstance().getProject();
//	    int specimens = project.getFirstPopulation().getSpecimen().size();
//
//	    int columns = 1;
//
//	    if (project.getSecondPopulation() != null) {
//		columns = 5;
//		specimens += project.getSecondPopulation().getSpecimen().size();
//	    }
//
//	    int figures = StatMapper.getFigureAvaibles();
//	    int attributes = StatMapper.getAttributeAvaibles();
//	    int total = figures * attributes * columns;
//
//	    SharedController.getInstance().getProgressView()
//		    .init(specimens + total);
//	    project.calculateStatistic();
//
//	    view.prepare(view.getChannelStr(), view.getWaveStr());
//	    view.report.changeSelection(0, 1, false, false);
//
//	    SharedController.getInstance().getProgressView().close();
//	    view.setVisible(true);
//
//	}
//    }
//
//    public void showWithData() {
//	ShowThread runnable = new ShowThread(this);
//	Thread thread = new Thread(runnable);
//	thread.start();
//
//    }

    public void prepare(String channel, String wave) {
	int type = SharedController.getInstance().getProject().getType();

	String[] columns;

	this.channelStr = channel;
	this.waveStr = wave;

	if (type == Project.POPULATION_SINGLE) {
	    columns = new String[2];
	    Project project = SharedController.getInstance().getProject();
	    columns[0] = "";
	    columns[1] = project.getFirstPopulation().getName();
	    //columns[2] = project.getSecondPopulation().getName();

	    this.getModel().setDataVector(null, columns);

	    for (int i = 0; i < 2; i++)
		columns[i] = "";
	    for (int i = 0; i < 10; i++)
		this.model.addRow(columns);

	    controller.set(channel, wave);

	} else if (type == Project.POPULATION_PAIR) {
	    columns = new String[6];
	    Project project = SharedController.getInstance().getProject();
	    columns[0] = "";
	    columns[1] = project.getFirstPopulation().getName() + ": B with A";
	    columns[2] = project.getSecondPopulation().getName() + ": B with A";
	    columns[3] = "B with B";
	    columns[4] = "A with A";
	    columns[5] = "(A - B) with (A - B)";

	    this.getModel().setDataVector(null, columns);

	    for (int i = 0; i < 6; i++)
		columns[i] = "";
	    for (int i = 0; i < 10; i++)
		this.model.addRow(columns);

	    controller.set(channel, wave);
	}

	this.report.setModel(this.getModel());
    }

    public void fillChannelCombo() {
	for (String channelName : SharedController.getInstance()
		.getProjectRes().getPopul1().getResult().get(0).getBefore()
		.getValue().keySet()) {
	    int tmp = SharedController.getInstance().getProjectRes()
		    .getPopul1().getResult().get(0).getBefore().getValue()
		    .keySet().size();
	    ArrayList<String> names = new ArrayList<String>();
	    for (int i = 0; i < tmp; i++) {
		if (!names.contains(channelName)) {
		    this.channelCombo.addItem(channelName);
		    names.add(channelName);
		}
	    }
	}
    }
    
    public void fillWavesList() {
	this.wavesList = new JList<String>(StatisticWindowController.wavesList);
    }

    public String getChannelStr() {
	return channelStr;
    }

    public void setChannelStr(String channelStr) {
	this.channelStr = channelStr;
    }

    public String getWaveStr() {
	return waveStr;
    }

    public void setWaveStr(String waveStr) {
	this.waveStr = waveStr;
    }

    public JList<String> getWavesList() {
	return wavesList;
    }

    public void setWavesList(JList<String> wavesList) {
	this.wavesList = wavesList;
    }

    public JComboBox<String> getChannelCombo() {
	return channelCombo;
    }

    public void setChannelCombo(JComboBox<String> channelCombo) {
	this.channelCombo = channelCombo;
    }

    public DefaultTableModel getModel() {
	return model;
    }

    public void setModel(DefaultTableModel model) {
	this.model = model;
    }

    public JTable getReport() {
	return report;
    }

    public void setReport(JTable report) {
	this.report = report;
    }

    public Histogram getHistogram() {
	return histogram;
    }

    public void setHistogram(Histogram histogram) {
	this.histogram = histogram;
    }

    public JTextField getHypoTestEdit() {
	return hypoTestEdit;
    }

    public void setHypoTestEdit(JTextField hypoTestEdit) {
	this.hypoTestEdit = hypoTestEdit;
    }

    public JTextField getHypoEqualEdit() {
	return hypoEqualEdit;
    }

    public void setHypoEqualEdit(JTextField hypoEqualEdit) {
	this.hypoEqualEdit = hypoEqualEdit;
    }

    public JTextField getHypoRightEdit() {
	return hypoRightEdit;
    }

    public void setHypoRightEdit(JTextField hypoRightEdit) {
	this.hypoRightEdit = hypoRightEdit;
    }

    public JTextField getHypoLeftEdit() {
	return hypoLeftEdit;
    }

    public void setHypoLeftEdit(JTextField hypoLeftEdit) {
	this.hypoLeftEdit = hypoLeftEdit;
    }
}