package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.widgets.Display;

import de.hswt.hrm.common.BundleUtil;
import de.hswt.hrm.component.model.Category;
import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.component.model.ComponentIcon;
import de.hswt.hrm.scheme.model.RenderedComponent;
import de.hswt.hrm.scheme.service.ComponentConverter;
import de.hswt.hrm.scheme.ui.tree.IImageTreeModel;

/**
 * A Mock for the grid image tree data.
 * 
 * @author Michael Sieger
 * 
 */
public class ImageTreeModelMock implements IImageTreeModel {

	private final Display display;

	public ImageTreeModelMock(Display display) {
		super();
		this.display = display;
	}

	@Override
	public List<RenderedComponent> getImages() {
		
		try{
			return Arrays.asList(
					ComponentConverter.convert(display, getVentilationComponent()),
					ComponentConverter.convert(display, getFilterComponent()),
					ComponentConverter.convert(display, getFilterComponent2()),
					ComponentConverter.convert(display, getBioMesureComponent()));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	private Component getBioMesureComponent() throws FileNotFoundException, IOException{
		Component c = new Component("Keimmessung", 
				new ComponentIcon(loadBytes("pkk1d.pdf"), "pkk1d.pdf"),
				new ComponentIcon(loadBytes("pkk1l.pdf"), "pkk1l.pdf"),
				new ComponentIcon(loadBytes("pkk1r.pdf"), "pkklr.pdf"),
				new ComponentIcon(loadBytes("pkk1u.pdf"), "pkklu.pdf"), 1, true);
		c.setCategory(getMeasureCategory());
		return c;
	}
	
	private Component getVentilationComponent() throws FileNotFoundException, IOException{
		Component c = new Component(
				"Außenluft", 
				new ComponentIcon(loadBytes("1_0.pdf"), "1_0.pdf"),
				new ComponentIcon(loadBytes("1_l_0.pdf"), "1_l_0.pdf"), null, null, 3, true);
		c.setCategory(getVentilationCategory());
		return c;
	}
	
	private Component getFilterComponent2() throws FileNotFoundException, IOException{
		Component c = new Component(
				"F6", 
				new ComponentIcon(loadBytes("8_r_0.pdf"), "8_r_0.pdf"), 
				new ComponentIcon(loadBytes("8_l_0.pdf"), "8_l_0.pdf"), null, null, 3, true);
		c.setCategory(getFilterCategory());
		return c;
	}
	
	private Component getFilterComponent() throws FileNotFoundException, IOException{
		Component c = new Component(
				"F5", 
				new ComponentIcon(loadBytes("7_r_0.pdf"), "7_r_0.pdf"),
				new ComponentIcon(loadBytes("7_l_0.pdf"), "7_l_0.pdf"), null, null, 3, true);
		c.setCategory(getFilterCategory());
		return c;
	}
	
	private Category getMeasureCategory(){
		return new Category("Messungen", 1, 1, 1, false);
	}
	
	private Category getFilterCategory(){
		return new Category("Filter", 3, 3, 3, true);
	}
	
	private Category getVentilationCategory(){
		return new Category("Ventilation", 3, 3, 3, true);
	}

	private byte[] loadBytes(String name) throws FileNotFoundException,
			IOException {
		try(InputStream in = BundleUtil.getStreamForFile(
				ImageTreeModelMock.class, "test/"+name)){
			byte[] r = new byte[in.available()];
			in.read(r);
			return r;
		}
	}

}
