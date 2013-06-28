package de.hswt.hrm.inspection.ui.grid;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.widgets.Display;

import com.google.common.base.Throwables;

import de.hswt.hrm.common.BundleUtil;
import de.hswt.hrm.component.model.Component;

public class SamplingPoints {
	
	private static final String[] DIRS = new String[]
			{
				"r",
				"l",
				"d",
				"u"
			};
	private final Component[] airMeasurement;
	private final Component[] waterAnalysis;
	private final Component[] contactCulture;
	private final Component[] photo;
	private final Component[] climateParameter;
	private final Component[] dustConcentration;
	
	public SamplingPoints(Display display){
		try{
			airMeasurement = getComponents("pl");
			waterAnalysis = getComponents("pw");
			contactCulture = getComponents("pkk");
			photo = getComponents("pf");
			climateParameter = getComponents("pkp");
			dustConcentration = getComponents("ps");
		}catch(Exception e){
			throw Throwables.propagate(e);
		}
		
	}
	
	private byte[] getBytes(String fname) throws IOException{
		try(InputStream in = BundleUtil.getStreamForFile(SamplingPoints.class, "resources/"+fname)){
			byte[] data = new byte[in.available()];
			in.read(data);
			return data;
		}
	}
	
	private Component[] getComponents(String name) throws IOException{
		return new Component[] {getComponent(name + "1"), 
								getComponent(name + "2")};
	}
	
	private Component getComponent(String name) throws IOException{
		return new Component
				(name, getBytes(name+DIRS[0]), 
						getBytes(name+DIRS[1]), 
						getBytes(name+DIRS[2]), 
						getBytes(name+DIRS[3]), false);
	}

}
