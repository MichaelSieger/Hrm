package de.hswt.hrm.inspection.ui.part;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.inspection.ui.grid.InspectionSchemeGrid;
import de.hswt.hrm.scheme.ui.SchemeGridItem;

public class ComponentSelectionComposite extends Composite {

	@Inject
	private IEclipseContext context;
	
	private Class<? extends AbstractComponentRatingComposite> ratingCompositeClass;

	private FormToolkit toolkit = new FormToolkit(Display.getDefault());

	private ListViewer componentsList;

	private AbstractComponentRatingComposite ratingComposite;
	
	private InspectionSchemeGrid schemeGrid;

	/**
	 * Do not use this constructor when instantiate this composite! It is only
	 * included to make the WindowsBuilder working.
	 * 
	 * @param parent
	 * @param style
	 */
	private ComponentSelectionComposite(Composite parent, int style) {
		super(parent, SWT.NONE);
		createControls();
	}

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param ratingCompositeClass
	 */
	public ComponentSelectionComposite(Composite parent,
			Class<? extends AbstractComponentRatingComposite> ratingCompositeClass) {
		super(parent, SWT.NONE);
		this.ratingCompositeClass = ratingCompositeClass;
		toolkit.dispose();
		toolkit = FormUtil.createToolkit();
	}

	@PostConstruct
	public void createControls() {
		FillLayout fl = new FillLayout();
		fl.marginHeight = 5;
		fl.marginWidth = 5;
		setLayout(fl);

		SashForm horizontalSash = new SashForm(this, SWT.NONE);
		horizontalSash.setBackgroundMode(SWT.INHERIT_DEFAULT);
		toolkit.adapt(horizontalSash);
		toolkit.paintBordersFor(horizontalSash);

		Section listSection = toolkit.createSection(horizontalSash,
				Section.TITLE_BAR);
		toolkit.paintBordersFor(listSection);
		listSection.setText("Components");
		FormUtil.initSectionColors(listSection);

		// TODO use this list to show and select components
		componentsList = new ListViewer(listSection, SWT.BORDER);
		listSection.setClient(componentsList.getList());
		toolkit.adapt(componentsList.getList(), true, true);

		SashForm verticalSash = new SashForm(horizontalSash, SWT.VERTICAL);
		verticalSash.setBackgroundMode(SWT.INHERIT_FORCE);
		toolkit.adapt(verticalSash);
		toolkit.paintBordersFor(verticalSash);

		ratingComposite = getRatingInstance(verticalSash);
		ContextInjectionFactory.inject(ratingComposite, context);

		Section schemeSection = toolkit.createSection(verticalSash,
				Section.TITLE_BAR);
		toolkit.paintBordersFor(schemeSection);
		schemeSection.setText("Scheme");
		schemeSection.setExpanded(true);
		FormUtil.initSectionColors(schemeSection);

		Composite schemeComposite = new Composite(schemeSection, SWT.NONE);
		schemeSection.setClient(schemeComposite);
		toolkit.adapt(schemeComposite);
		toolkit.paintBordersFor(schemeComposite);
		FillLayout fl_schemeComposite = new FillLayout(SWT.HORIZONTAL);
		fl_schemeComposite.marginHeight = 5;
		schemeComposite.setLayout(fl_schemeComposite);
		verticalSash.setWeights(new int[] {3, 1});
		horizontalSash.setWeights(new int[] {1, 5});

		ScrolledComposite schemeScroll = new ScrolledComposite(schemeComposite, SWT.H_SCROLL | SWT.V_SCROLL);
		schemeScroll.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
		schemeGrid = new InspectionSchemeGrid(schemeScroll, SWT.NONE);
		// TODO set the component selection to the ratingCompiste
	}

	private AbstractComponentRatingComposite getRatingInstance(Control control) {
		AbstractComponentRatingComposite composite = null;
		if (ratingCompositeClass != null) {
			try {
				composite = ratingCompositeClass.getDeclaredConstructor(
						Composite.class).newInstance(control);
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return composite;
	}
	
	public void setSchemeGridItems(Collection<SchemeGridItem> items){
		schemeGrid.setItems(items);
	}

	@Override
	public void dispose() {
		toolkit.dispose();
		super.dispose();
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
