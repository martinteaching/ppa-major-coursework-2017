package view.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Comparator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Incident;
import model.IncidentList;
import utils.GUIUtils;

public class Information extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH = 650;
	
	public static final int HEIGHT = 200;
	
	private JList<Incident> incidentList;
	
	private JScrollPane scroll_incidentList;
	
	private IncidentList<Incident> model_incidentList;
	
	private JComboBox<String> sort;
	
	private DefaultComboBoxModel<String> model_sort;
	
	private controller.Information controller_Information;
	
	public Information(controller.Information controller_Information) {
		
		setLayout(new BorderLayout());
		
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		
		setupList();
		
		addHeader();
		
		pack();
		
		this.controller_Information = controller_Information;
		
	}
	
	private void setupList() {
		
		incidentList = new JList<Incident>();
		
		GUIUtils.setBorder(incidentList);
		
		model_incidentList = new IncidentList<Incident>();
		
		incidentList.setModel(model_incidentList);
		
		scroll_incidentList = new JScrollPane();
		
		scroll_incidentList.setViewportView(incidentList);
		
		incidentList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				if ( !e.getValueIsAdjusting() ) {
					
					controller_Information.showDetails(incidentList.getSelectedValue().getIndicentID());
					
				}
				
			}
			
			
		});
		
		GUIUtils.setBorder(scroll_incidentList);
		
		add(scroll_incidentList, BorderLayout.CENTER);
		
	}
	
	public Incident getSelectedIncident() {
		
		return incidentList.getSelectedValue();
		
	}
	
	private void addHeader() {
		
		sort = new JComboBox<String>();
		
		model_sort = new DefaultComboBoxModel<String>();
		
		sort.setModel(model_sort);
		
		model_sort.addElement("-");
		model_sort.addElement("Date");
		model_sort.addElement("City");
		model_sort.addElement("Shape");
		model_sort.addElement("Duration");
		model_sort.addElement("Posted");
		
		sort.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				
				if ( e.getSource() instanceof JComboBox ) {
					
					try {
						
						if (((JComboBox<Incident>)e.getSource()).getSelectedItem().equals("Date")) {
					
							model_incidentList.sort(new Comparator<Incident>() {
	
								@Override
								public int compare(Incident o1, Incident o2) {
									
									return o1.getDateAndTime().compareTo(o2.getDateAndTime());
									
								}
								
								
							});
						
						}
					
					} catch (ClassCastException cce) {}
				
				}
				
			}
			
			
		});
		
		GUIUtils.setBorder(sort);
		
		add(sort, BorderLayout.NORTH);
		
	}
	
	public void addIncident(Incident incident) {
		
		model_incidentList.addIncident(incident);
		
	}

}