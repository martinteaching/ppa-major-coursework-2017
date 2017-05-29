package view.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import controller.Container;
import controller.Controller;

public abstract class Cycle extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private String identifier;
	
	public Cycle(String identifier) {
		
		this.identifier = identifier;
		
		setLayout(new BorderLayout());
		
	}

	@Override
	public int hashCode() {
		
		final int prime = 31;
		
		int result = 1;
		
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		
		return result;
	
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			
			return true;
		
		if (obj == null)
			
			return false;
		
		if (getClass() != obj.getClass())
		
			return false;
		
		Cycle other = (Cycle) obj;
		
		if (identifier == null) {
		
			if (other.identifier != null)
			
				return false;
		
		} else if (!identifier.equals(other.identifier))
		
			return false;
		
		return true;
	
	}
	
}