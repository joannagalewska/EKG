package pi.data.importer.open;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import pi.graph.signal.GraphView;
import pi.project.Project;
import pi.shared.SharedController;

public class OpenPopulationController implements ActionListener {

	private OpenPopulationView view;
	private GraphView graphFirstView;
	private GraphView graphSecondView;
	private File selectedFile;

	public OpenPopulationController(OpenPopulationView view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		if (action.equals("OPEN")) {

			if (!this.view.getPathArea().getText().isEmpty()) {

				OpenThread runnable = new OpenThread();
				Thread thread = new Thread(runnable);
				thread.start();

				SharedController.getInstance().getFrame().getMenubar()
						.setInProject(true);

				this.view.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "Please fill in path!");
			}

		}
		if (action.equals("CANCEL")) {
			view.dispose();
		}
		if (action.equals("CHOOSE")) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(SharedController.getInstance()
					.getLastDirectory());
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"XML (*.xml)", "xml");
			fileChooser.addChoosableFileFilter(filter);
			fileChooser.setFileFilter(filter);
			int returnValue = fileChooser.showDialog(null, "Choose project...");

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				selectedFile = fileChooser.getSelectedFile();
				String path = selectedFile.getAbsolutePath();
				SharedController.getInstance().setLastDirectory(
						fileChooser.getSelectedFile());
				this.view.getPathArea().setText(path);
				view.setPath(path);
			}
		}

	}

	public GraphView getGraphFirstView() {
		return graphFirstView;
	}

	public void setGraphFirstView(GraphView graphFirstView) {
		this.graphFirstView = graphFirstView;
	}

	public GraphView getGraphSecondView() {
		return graphSecondView;
	}

	public void setGraphSecondView(GraphView graphSecondView) {
		this.graphSecondView = graphSecondView;
	}

	class OpenThread implements Runnable {

		@Override
		public void run() {
			PopImporter pi = new PopImporter();
			SharedController.getInstance().getFrame().getContent().removeAll();

			XMLReader p;
			try {

				p = XMLReaderFactory.createXMLReader();
				p.setContentHandler(pi);

				try {
					p.parse(view.getPath());
				} catch (SAXException | IOException | NullPointerException ex) {
					JOptionPane.showMessageDialog(null,
							"Please provide compatible file!");
				}
				Project importedProject = pi.getProject();

				int type = importedProject.getType();

				SharedController.getInstance().setProject(importedProject);
				SharedController.getInstance().createProjectToolbar();

				if (type == 1) {
					setGraphFirstView(new GraphView(
							importedProject.getFirstPopulation(), 1));
				} else if (type == 2) {
					setGraphFirstView(new GraphView(
							importedProject.getFirstPopulation(), 1));
					setGraphSecondView(new GraphView(
							importedProject.getFirstPopulation(), 2));

				} else if (type == 3 || type == 4) {
					setGraphFirstView(new GraphView(
							importedProject.getFirstPopulation(), 1));
					setGraphSecondView(new GraphView(
							importedProject.getSecondPopulation(), 2));
				}

				SharedController.getInstance().getProject()
						.setPath(view.getPathArea().getText().toString());
				SharedController
						.getInstance()
						.getFrame()
						.setTitle(
								SharedController.getInstance().getFrame()
										.getTitle()
										+ " "
										+ view.getPathArea().getText()
												.toString());

			} catch (SAXException e1) {
				e1.printStackTrace();
			}

		}

	}

}