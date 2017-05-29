package view.panels;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class StatisticNameAndValue extends JPanel implements Comparable<StatisticNameAndValue> {

	private static final long serialVersionUID = 1L;

	private JLabel title;
	
	private JLabel value;
	
	private boolean selectedByUser;
	
	public StatisticNameAndValue(String title, String value) {
		
		this.title = new JLabel(title, SwingConstants.CENTER);
		
		this.value = new JLabel(value, SwingConstants.CENTER);
		
		this.value.setFont(new Font("Monospace",Font.PLAIN,20));
		
		setupCentre();
		
	}
	
	public void setupCentre() {
		
		setLayout(new BorderLayout());
		
		add(title, BorderLayout.NORTH);
		
		add(value, BorderLayout.CENTER);
		
	}
	
	public String getTitle() {
		
		return title.getText();
		
	}
	
	public void setSelectedByUser() {
		
		selectedByUser = true;
		
	}
	
	@Override
	public int compareTo(StatisticNameAndValue o) {
		
		if ( selectedByUser && !o.selectedByUser ) return -1;
		
		if ( !selectedByUser && o.selectedByUser ) return 1;
		
		return title.getText().compareTo(o.title.getText());
		
	}

	// Generated:
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "IndividualStatistic [title=" + title.getText() + ", value=" + value.getText() + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatisticNameAndValue other = (StatisticNameAndValue) obj;
		if (title.getText() == null) {
			if (other.title.getText() != null)
				return false;
		} else if (!title.getText().equals(other.title.getText()))
			return false;
		if (value.getText() == null) {
			if (other.value.getText() != null)
				return false;
		} else if (!value.getText().equals(other.value.getText()))
			return false;
		return true;
	}

}
