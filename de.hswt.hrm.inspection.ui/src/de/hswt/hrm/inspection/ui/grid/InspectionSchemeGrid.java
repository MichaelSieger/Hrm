package de.hswt.hrm.inspection.ui.grid;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import de.hswt.hrm.scheme.model.RenderedComponent;
import de.hswt.hrm.scheme.model.Scheme;
import de.hswt.hrm.scheme.model.SchemeComponent;
import de.hswt.hrm.scheme.service.ComponentConverter;
import de.hswt.hrm.scheme.ui.SchemeGrid;
import de.hswt.hrm.scheme.ui.SchemeGridItem;

/**
 * A SchemeGrid where Components can be colored.
 * The Components can also be selected.
 * 
 * @author Michael Sieger
 *
 */
public class InspectionSchemeGrid {
	
	private final static Logger LOG = LoggerFactory
			.getLogger(InspectionSchemeGrid.class);
	
	private static final int PPG = 50;
	
	private final SchemeGrid grid;
	private final Scheme scheme;
	private SchemeComponent selected;
	private SchemeComponentSelectionListener listener;
	
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
	
	/**
	 * Sets the Listener that is notified if a component is selected.
	 * @param listener
	 */
	public void setSelectionListener(SchemeComponentSelectionListener listener){
		this.listener = listener;
		if(listener != null){
			listener.selected(selected);
		}
	}
	
	/**
	 * Sets the selected item. A selection event is fired, if the 
	 * component differs from the one before.
	 * @param selected
	 */
	public void setSelected(SchemeComponent selected){
		if(this.selected != selected){
			this.selected = selected;
			if(listener != null){
				listener.selected(selected);
			}
		}
	}

}
