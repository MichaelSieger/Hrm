package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.widgets.Display;

import de.hswt.hrm.common.BundleUtil;
import de.hswt.hrm.scheme.model.GridImage;
import de.hswt.hrm.scheme.model.RenderedGridImage;
import de.hswt.hrm.scheme.service.GridImageConverter;
import de.hswt.hrm.scheme.ui.IImageTreeModel;

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
	public RenderedGridImage[] getImages() {
		try {
			GridImage img = new GridImage();
			img.setWidth(5);
			img.setHeight(5);
			img.setImage(loadBytes(BundleUtil.getStreamForFile(
					ImageTreeModelMock.class, "test/pdftest.pdf")));
			return new RenderedGridImage[] { GridImageConverter.convert(
					display, img) };
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private byte[] loadBytes(InputStream in) throws FileNotFoundException,
			IOException {
		byte[] r = new byte[in.available()];
		in.read(r);
		return r;

	}

}
