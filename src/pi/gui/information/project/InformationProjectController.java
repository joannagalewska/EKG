package pi.gui.information.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pi.shared.SharedController;

public class InformationProjectController implements ActionListener {
	private InformationProjectView view;

	public InformationProjectController(InformationProjectView view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String action = arg0.getActionCommand();

		if (action.equals("OK")) {
			String name = this.view.getProjectField().getText();
			if (name.equals("")) {
				name = null;
			}
			SharedController.getInstance().getProject().setName(name);

			String first = this.view.getFirstField().getText();
			if (first.equals("")) {
				first = null;
			}
			SharedController.getInstance().getProject().getFirstPopulation()
					.setName(first);

			if (SharedController.getInstance().getProject()
					.getSecondPopulation() != null) {
				String second = this.view.getSecondField().getText();
				if (second.equals("")) {
					second = null;
				}
				SharedController.getInstance().getProject()
						.getSecondPopulation().setName(second);
			}

			this.view.setVisible(false);
		} else if (action.equals("CANCEL")) {
			this.view.setVisible(false);
		}
	}

}
