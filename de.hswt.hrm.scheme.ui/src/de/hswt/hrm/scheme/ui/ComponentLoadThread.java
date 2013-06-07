package de.hswt.hrm.scheme.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.component.service.ComponentService;
import de.hswt.hrm.scheme.model.RenderedComponent;
import de.hswt.hrm.scheme.service.ComponentConverter;
import de.hswt.hrm.scheme.ui.part.SchemePart;

/**
 * Loads the Components and renders them. 
 * For every finished RenderedComponent setRenderedComponents is called on the part.
 * 
 * @author Michael Sieger
 *
 */
public class ComponentLoadThread extends Thread{
	
	private final static Logger LOG = LoggerFactory.getLogger(ComponentLoadThread.class);

	private final SchemePart part;
	private final ComponentService compService;
	private final Display display;
	
	public ComponentLoadThread(SchemePart part, Display display, ComponentService compService) {
		super();
		this.part = part;
		this.compService = compService;
		this.display = display;
	}

	@Override
	public void run() {
		Collection<Component> comp;
		try {
			comp = compService.findAll();
		} catch (DatabaseException e) {
			throw Throwables.propagate(e);
		}
		List<RenderedComponent> result = new ArrayList<RenderedComponent>();
		for(Component c : comp){
		    //Ignore components without category
		    if(c.getCategory().isPresent()){
		        try {
                    result.add(ComponentConverter.convert(display, c));
                }
                catch (IOException e) {
                	LOG.error("Error on drawing image", e);
                }
		        //Always copy the list before passing to the ui thread.
		        final List<RenderedComponent> rCopy = new ArrayList<>(result);
		        display.asyncExec(new Runnable() {
					
					@Override
					public void run() {
						part.setRenderedComponents(rCopy);
					}
				});
		    }else{
		    	LOG.warn(String.format("The Component %s has no category, and cannot be drawn", c.getName()));
		    }
		}
	}
	
}