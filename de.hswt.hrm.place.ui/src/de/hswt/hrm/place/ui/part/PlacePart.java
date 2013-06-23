package de.hswt.hrm.place.ui.part;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;


import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.i18n.I18n;
import de.hswt.hrm.i18n.I18nFactory;

public class PlacePart {
    
    private final static I18n I18N = I18nFactory.getI18n(PlacePart.class);

    @Inject
    private IEclipseContext context;

	private FormToolkit toolkit = new FormToolkit(Display.getDefault());

	private Action editAction;
	private Action addAction;

	private PlaceComposite placeComposite;
	
	public PlacePart() {
		// toolkit can be created in PostConstruct, but then then
		// WindowBuilder is unable to parse the code
		toolkit.dispose();
		toolkit = FormUtil.createToolkit();
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {

		createActions();
		toolkit.setBorderStyle(SWT.BORDER);
		toolkit.adapt(parent);
		toolkit.paintBordersFor(parent);
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));

		Form form = toolkit.createForm(parent);
		form.getHead().setOrientation(SWT.RIGHT_TO_LEFT);
		form.setSeparatorVisible(true);
		toolkit.paintBordersFor(form);
		form.setText(I18N.tr("Places"));
		toolkit.decorateFormHeading(form);

		form.getToolBarManager().add(editAction);
		form.getToolBarManager().add(addAction);
		
		FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
		fillLayout.marginHeight = 1;
		form.getBody().setLayout(fillLayout);

		Section headerSection = toolkit.createSection(form.getBody(), Section.TITLE_BAR);
		toolkit.paintBordersFor(headerSection);
		headerSection.setExpanded(true);
		FormUtil.initSectionColors(headerSection);
		Composite temp = toolkit.createComposite(headerSection);
		temp.setLayout(new FillLayout());
        headerSection.setClient(temp);
		
		placeComposite = new PlaceComposite(temp, SWT.NONE);
        ContextInjectionFactory.inject(placeComposite, context);
        
		form.getToolBarManager().update(true);

	}

	private void createActions() {
		{
			editAction = new Action(I18N.tr("Edit")) {
				@Override
				public void run() {
					super.run();
					placeComposite.editPlace();
				}
			};
			editAction.setDescription(I18N.tr("Edit an exisitng place."));
		}
		{
			addAction = new Action(I18N.tr("Add")) {
				@Override
				public void run() {
					super.run();
					placeComposite.addPlace();
				}
			};
			addAction.setDescription(I18N.tr("Add a new place."));
		}
	}

	@PreDestroy
	public void dispose() {
		if (toolkit != null) {
			toolkit.dispose();
		}
	}

	@Focus
	public void setFocus() {
	}
}
