package pi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import pi.gui.menu.MenuController;
import pi.gui.menu.MenuView;
import pi.shared.SharedController;

public class OurFrame extends JFrame {

	private MenuView menubar;
	private MenuController menuController;
	private JPanel content;
	private ComponentListener cl = new ComponentListener() {
		
		@Override
		public void componentShown(ComponentEvent e) {}
		
		@Override
		public void componentResized(ComponentEvent e) {
			JPanel source = (JPanel) e.getSource();
			source.setPreferredSize(source.getSize());
		}
		
		@Override
		public void componentMoved(ComponentEvent e) {}
		
		@Override
		public void componentHidden(ComponentEvent e) {}
	};
	
	
	public OurFrame() {

		this.setLocation(100, 0);
		//this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		getContentPane().setLayout(new BorderLayout());
				//new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		content = new JPanel();
		content.setBackground(Color.white);
		content.setVisible(true);
		//content.setComponentOrientation()
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		content.addComponentListener(cl);

		
		getContentPane().add(content, BorderLayout.CENTER);
		
		SharedController.getInstance().setFrame(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		setMenubar(new MenuView(this));
		this.setJMenuBar(getMenubar());
		setMenuController(new MenuController(getMenubar()));
		// this.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		// JScrollPane pane = new
		// JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		// JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// this.setContentPane(pane);
		this.setSize(new Dimension(800,800));
		// this.setMinimumSize(new Dimension(1140, 1000));
		
		this.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
				content.revalidate();
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});


	}

	public MenuView getMenubar() {
		return menubar;
	}

	public void setMenubar(MenuView menubar) {
		this.menubar = menubar;
	}

	public MenuController getMenuController() {
		return menuController;
	}

	public void setMenuController(MenuController menuController) {
		this.menuController = menuController;
	}

	public JPanel getContent() {
		return content;
	}

	public void setContent(JPanel content) {
		this.content = content;
	}
}
