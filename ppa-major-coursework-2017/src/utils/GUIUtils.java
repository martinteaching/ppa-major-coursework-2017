package utils;

import java.awt.MouseInfo;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.border.Border;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public final class GUIUtils {

	/**
	 * Stops the class being initialised
	 */
	private GUIUtils() {
	}

	/**
	 * A utility method for easily adding thin borders to components.
	 * 
	 * @param component
	 */
	public static void setBorder(JComponent component) {

		setBorder(component, BorderFactory.createEmptyBorder());

	}

	/**
	 * A utility method for easily adding pre-specified borders to components.
	 * 
	 * @param component
	 * @param border
	 */
	public static void setBorder(JComponent component, Border border) {

		component.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

	}

	/**
	 * Adds a JSeparator (a thin line) above a JLabel on a given component, in
	 * order to expedite the laying out of a frame.
	 * 
	 * @param component
	 * @param title
	 */
	public static void addSectionHeader(JComponent component, String title) {

		component.add(new JSeparator());

		JLabel sectionHeader = new JLabel(title);

		GUIUtils.setBorder(sectionHeader);

		component.add(sectionHeader);

	}

	/**
	 * 
	 */
	public static void mouseLoop() {

		while (true) {

			System.out.println((MouseInfo.getPointerInfo().getLocation().getX() - 25) + " "
					+ (MouseInfo.getPointerInfo().getLocation().getY() - 75));

		}

	}

	public static ArrayList<String> urlResponse(String request) throws UnsupportedEncodingException, IOException {

		URL url = new URL(request);

		ArrayList<String> responseLines = new ArrayList<String>();

		try (BufferedReader urlReader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {

			for (String urlLine; (urlLine = urlReader.readLine()) != null;) {

				responseLines.add(urlLine);

			}

		}

		return responseLines;

	}

	/**
	 * Very simple mechanism for issuing a HTTP request to a URL, and returning
	 * the resulting page as a list of strings.
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static String urlResponse(String stringURL, String rawPostData) throws UnsupportedEncodingException, IOException {

		byte[] postData = rawPostData.getBytes(StandardCharsets.UTF_8);

		int postDataLength = postData.length;

		URL url = new URL(stringURL);

		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setRequestMethod("POST");

		con.setDoOutput(true);

		try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {

			wr.write(postData);

		}

		BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));

		return readAll(rd);

	}

	private static String readAll(Reader rd) throws IOException {

		StringBuilder sb = new StringBuilder();

		int cp;

		while ((cp = rd.read()) != -1) {

			sb.append((char) cp);

		}

		return sb.toString();

	}

	public static void setUIFont(javax.swing.plaf.FontUIResource f) {
		
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		
		while (keys.hasMoreElements()) {
		
			Object key = keys.nextElement();
			
			Object value = UIManager.get(key);
			
			if (value != null && value instanceof javax.swing.plaf.FontUIResource) UIManager.put(key, f);
		
		}
	
	}

	public static String parseMinutes(String time) {
		
		LocalDateTime parsed = parse(time);
		
		if ( parsed != null ) {
			
			Duration duration = Duration.between(LocalDateTime.now(), parsed);
			
			long minutes = duration.toMinutes();
			
			if (minutes > 0 && minutes < 1000 ) {
				
				return minutes + "";
				
			} 
		
		} 
		
		return "";
		
	}

	private static LocalDateTime parse(String text) {

	    Parser parser = new Parser();
	   
	    List<DateGroup> groups = parser.parse(text);
	    
	    for (DateGroup group : groups) {
	    
	    	List<Date> dates = group.getDates();
	      
	        if (dates.size() > 0) {
	        
	        	return LocalDateTime.ofInstant(dates.get(0).toInstant(), ZoneId.systemDefault());
	        
	        }
	    
	    }
	    
	    return null;
	    
	}

}
