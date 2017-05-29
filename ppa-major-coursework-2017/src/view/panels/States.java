package view.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import view.events.AddAlienHead;
import view.events.DateRangeChange;
import view.events.InvalidRange;

public class States extends Cycle implements Observer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int MAX_HEAD_SIZE = 50;
	public static final int MIN_HEAD_SIZE = 10;
	
	private JLayeredPane mapLayers;
	
	private controller.States controller_States;
	
	private controller.Information controller_Information;

	private BufferedImage alienImage;
	
	public States(controller.States controller_States, controller.Information controller_Information) {
		
		super("States");
		
		this.controller_States = controller_States;
		
		this.controller_Information = controller_Information;
	
		showMap();
		
		try {
			
			alienImage = ImageIO.read( new File("library/alien.png") );
		
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}

	private void showMap() {
		
		removeAll();
		
		revalidate();
		
		repaint();
		
		mapLayers = new JLayeredPane();
		
		mapLayers.setLayout(null);
		
		mapLayers.setPreferredSize(new Dimension(1024, 500));
        
		JPanel map = new JPanel() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics panelGraphics) {
				
				BufferedImage mapImage = null;
				
				try {
				
					// Read the image from a sensible location, like a library folder
					mapImage = ImageIO.read( new File("library/states.jpg") );
					
				} catch (IOException e) {

					e.printStackTrace();
				
				}
				
				// Draw the loaded image onto the panel
				panelGraphics.drawImage(mapImage, 0, 0, null);
				
			}
		
		};
		
		mapLayers.add(map, new Integer(0), 0);
		
		map.setBounds(0, 0, 1024, 576);
		
		add(mapLayers, BorderLayout.CENTER);
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
		if ( arg instanceof DateRangeChange ) {
			
			showMap();
		
		} else if ( arg instanceof AddAlienHead ) {

			showAlien(((AddAlienHead) arg).getSize(), ((AddAlienHead) arg).getxPosition(), ((AddAlienHead) arg).getyPosition(), ((AddAlienHead) arg).getState());
			
		} else if ( arg instanceof InvalidRange ) {
			
			JOptionPane.showMessageDialog(this, "<html><body><p style='width: 200px;'> The range you have entered is not valid. </p></body></html>");
			
		}

	}

	private void showAlien(int xySize, int xPosition, int yPosition, String stateCode) {
		
		// For z-layering
		int heads = 1;
			
		JPanel alienHead = new JPanel() {
			
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics panelGraphics) {
				
				panelGraphics.drawImage(alienImage, 0, 0, xySize, xySize, null);
					
			}
		
		};
		
		alienHead.addMouseListener(new MouseAdapter()  {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				controller_Information.alienClicked(stateCode);
				
			}

		});
		
		alienHead.setOpaque(false);
		
		mapLayers.add(alienHead, new Integer(heads), heads++);
				
		alienHead.setBounds(xPosition, yPosition, xySize, xySize);
			
	}

}
