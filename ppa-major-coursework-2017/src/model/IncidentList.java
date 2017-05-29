package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.AbstractListModel;

public class IncidentList<E> extends AbstractListModel<E> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<E> incidents;
	
	public IncidentList() {
		
		super();
		
		incidents = new ArrayList<E>();
		
	}
	
	public void sort(Comparator<E> comparator) {
		
		Collections.sort(incidents, comparator);
		
		fireContentsChanged(this, 0, incidents.size());
		
	}
	
	public void addIncident(E incident) {
		
		incidents.add(incident);
		
		fireIntervalAdded(this, incidents.size() -1, incidents.size() -1);
		
	}
	
	@Override
	public int getSize() {
		
		return incidents.size();
	
	}

	@Override
	public E getElementAt(int index) {
		
		return incidents.get(index);
		
	}

}
