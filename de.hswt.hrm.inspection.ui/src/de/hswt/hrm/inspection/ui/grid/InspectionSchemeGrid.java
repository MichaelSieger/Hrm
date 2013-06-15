package de.hswt.hrm.inspection.ui.grid;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import static com.google.common.base.Preconditions.*;

import de.hswt.hrm.scheme.model.RenderedComponent;
import de.hswt.hrm.scheme.model.Scheme;
import de.hswt.hrm.scheme.model.SchemeComponent;
import de.hswt.hrm.scheme.service.ComponentConverter;
import de.hswt.hrm.scheme.ui.SchemeGrid;
import de.hswt.hrm.scheme.ui.SchemeGridItem;

public class InspectionSchemeGrid {
	
	private final static Logger LOG = LoggerFactory
			.getLogger(InspectionSchemeGrid.class);
	
	private static final int PPG = 50;
	
	private final SchemeGrid grid;
	private final Scheme scheme;
	
	public InspectionSchemeGrid(final Composite parent, int style, Scheme scheme){
		checkNotNull(scheme);
		this.scheme = scheme;
		grid = new SchemeGrid(parent, style, 1, 1, PPG);
		grid.setItems(Collections2.transform(scheme.getSchemeComponents(), new Function<SchemeComponent, SchemeGridItem>() {
			public SchemeGridItem apply(SchemeComponent comp){
				RenderedComponent rcomp = null;;
				try {
					rcomp = ComponentConverter.convert(parent.getDisplay(), comp.getComponent());
				} catch (IOException e) {
					LOG.error("Error on rendering Component", e);
					return null;
				}
				return new SchemeGridItem(
						rcomp, 
						comp.getDirection(), 
						comp.getX(), 
						comp.getY());
			}
		}));
	}

}
