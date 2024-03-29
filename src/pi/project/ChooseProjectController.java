package pi.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import pi.data.importer.pair.ImportPairController;
import pi.data.importer.pair.ImportPairView;
import pi.data.importer.population.pair.PopulationPairController;
import pi.data.importer.population.pair.PopulationPairView;
import pi.data.importer.population.single.PopulationSingleController;
import pi.data.importer.population.single.PopulationSingleView;
import pi.data.importer.signal.ImportSingleController;
import pi.data.importer.signal.ImportSingleView;
import pi.shared.SharedController;

public class ChooseProjectController implements ActionListener {

	Project model;
	ChooseProjectView view;
	private Project project;
	private ImportPairController importPairController;
	private PopulationSingleController populationSingleControlle;
	private PopulationPairController populationPairController;

	public ChooseProjectController(Project model, ChooseProjectView view) {
		this.model = model;
		this.view = view;

		view.setButtonsListener(this);
	}

	public void actionPerformed(ActionEvent ae) {

		String action = ae.getActionCommand();
		if (action.equals("CANCEL")) {
			view.dispose();
		}
		if (action.equals("NEXT")) {
			SharedController.getInstance().getFrame().getContent().removeAll();
			String selected = view.findSelectedRadio();
			SharedController.getInstance().setFirstPopulationSet(false);
			if (selected.equals("SINGLE_SIGNAL")) {
				view.setVisible(false);

				ImportSingleView importerView = new ImportSingleView();
				@SuppressWarnings("unused")
				ImportSingleController controller = new ImportSingleController(
						importerView);
				importerView.setBounds(400, 200, 450, 200);

			}

			if (selected.equals("TWO_SIGNALS")) {
				view.setVisible(false);

				ImportPairView importerView = new ImportPairView();
				setImportPairController(new ImportPairController(importerView));

				importerView.setBounds(400, 200, 500, 200);

			}
			if (selected.equals("TWO_POPULATIONS")) {
				view.setVisible(false);

				PopulationSingleView view = new PopulationSingleView();
				setPopulationSingleController(new PopulationSingleController(
						view));

			}
			if (selected.equals("POPULATION_DIFFERENCE")) {
				view.setVisible(false);

				PopulationPairView view = new PopulationPairView();
				setPopulationPairController(new PopulationPairController(view));

			}
		}

	}

	public ImportPairController getImportPairController() {
		return importPairController;
	}

	public void setImportPairController(ImportPairController controller) {
		this.importPairController = controller;
	}

	public PopulationSingleController getPopulationSingleController() {
		return populationSingleControlle;
	}

	public void setPopulationSingleController(
			PopulationSingleController controller) {
		this.populationSingleControlle = controller;
	}

	public PopulationPairController getPopulationPairController() {
		return populationPairController;
	}

	public void setPopulationPairController(PopulationPairController controller) {
		this.populationPairController = controller;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}