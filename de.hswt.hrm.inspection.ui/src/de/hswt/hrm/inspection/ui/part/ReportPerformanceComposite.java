package de.hswt.hrm.inspection.ui.part;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.catalog.model.Activity;
import de.hswt.hrm.catalog.model.Catalog;
import de.hswt.hrm.catalog.model.Current;
import de.hswt.hrm.catalog.model.ICatalogItem;
import de.hswt.hrm.catalog.model.Target;
import de.hswt.hrm.catalog.model.tree.TreeCurrent;
import de.hswt.hrm.catalog.model.tree.TreeTarget;
import de.hswt.hrm.catalog.service.CatalogService;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.common.ui.swt.utils.ContentProposalUtil;
import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.i18n.I18n;
import de.hswt.hrm.i18n.I18nFactory;
import de.hswt.hrm.inspection.model.Inspection;
import de.hswt.hrm.inspection.model.util.PerformanceUtil;
import de.hswt.hrm.inspection.service.InspectionService;
import de.hswt.hrm.inspection.ui.performance.tree.PerformanceTreeContentProvider;
import de.hswt.hrm.inspection.ui.performance.tree.PerformanceTreeLabelProvider;
import de.hswt.hrm.inspection.ui.stub.PerformanceStub;
import de.hswt.hrm.misc.comment.model.Comment;
import de.hswt.hrm.misc.priority.model.Priority;
import de.hswt.hrm.misc.priority.service.PriorityService;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.scheme.model.SchemeComponent;

public class ReportPerformanceComposite extends AbstractComponentRatingComposite {

    @Inject
    private InspectionService inspectionService;

    @Inject
    private IEclipseContext context;

    @Inject
    private CatalogService catalogService;

    @Inject
    private IShellProvider shellProvider;
    
    @Inject
    private PriorityService priorityService;

//    private ListViewer componentsList;
    private ListViewer targetListViewer;
    private ListViewer currentListViewer;
    private ListViewer activityListViewer;
    private TreeViewer treeViewer;
    
    private Button addButton;
    
    private FormToolkit formToolkit = new FormToolkit(Display.getDefault());

    private static final Logger LOG = LoggerFactory.getLogger(ReportPerformanceComposite.class);
    private static final I18n I18N = I18nFactory.getI18n(ReportPerformanceComposite.class);

    /**
     * Do not use this constructor when instantiate this composite! It is only included to make the
     * WindowsBuilder working.
     * 
     * @param parent
     * @param style
     */
    private ReportPerformanceComposite(Composite parent, int style) {
        super(parent, SWT.NONE);
        createControls();
    }

    /**
     * Create the composite.
     * 
     * @param parent
     */
    public ReportPerformanceComposite(Composite parent) {
        super(parent, SWT.NONE);
        formToolkit.dispose();
        formToolkit = FormUtil.createToolkit();
    }

    @PostConstruct
    public void createControls() {
        setBackgroundMode(SWT.INHERIT_FORCE);
        GridLayout gl = new GridLayout(5, false);
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        gl.marginLeft = 5;
        gl.marginBottom = 5;
        setLayout(gl);

        Section targetSection = formToolkit.createSection(this, Section.TITLE_BAR);
        targetSection.setLayoutData(LayoutUtil.createFillData());
        formToolkit.paintBordersFor(targetSection);
        FormUtil.initSectionColors(targetSection);
        targetSection.setText(I18N.tr("Target"));

        targetListViewer = new ListViewer(targetSection, SWT.BORDER | SWT.V_SCROLL);
        List targetList = targetListViewer.getList();
        targetSection.setClient(targetList);

        Section currentSection = formToolkit.createSection(this, Section.TITLE_BAR);
        currentSection.setLayoutData(LayoutUtil.createFillData());
        formToolkit.paintBordersFor(currentSection);
        FormUtil.initSectionColors(currentSection);
        currentSection.setText(I18N.tr("Current"));

        currentListViewer = new ListViewer(currentSection, SWT.BORDER | SWT.V_SCROLL);
        List currentList = currentListViewer.getList();
        currentSection.setClient(currentList);

        Section activitySection = formToolkit.createSection(this, Section.TITLE_BAR);
        activitySection.setLayoutData(LayoutUtil.createFillData());
        formToolkit.paintBordersFor(activitySection);
        FormUtil.initSectionColors(activitySection);
        activitySection.setText(I18N.tr("Activity"));

        activityListViewer = new ListViewer(activitySection, SWT.BORDER | SWT.V_SCROLL);
        List activityList = activityListViewer.getList();
        activitySection.setClient(activityList);

        Composite buttonComposite = new Composite(this, SWT.NONE);
        formToolkit.adapt(buttonComposite);
        formToolkit.paintBordersFor(buttonComposite);
        buttonComposite.setLayout(new FillLayout(SWT.VERTICAL));

        addButton = new Button(buttonComposite, SWT.NONE);
        formToolkit.adapt(addButton, true, true);
        addButton.setText(">>");

        Button removeButton = new Button(buttonComposite, SWT.NONE);
        formToolkit.adapt(removeButton, true, true);
        removeButton.setText("<<");

        Section containedSection = formToolkit.createSection(this, Section.TITLE_BAR);
        containedSection.setLayoutData(LayoutUtil.createFillData());
        formToolkit.paintBordersFor(containedSection);
        FormUtil.initSectionColors(containedSection);
        containedSection.setText(I18N.tr("Assigned"));

        Composite composite = new Composite(containedSection, SWT.NONE);
        formToolkit.adapt(composite);
        formToolkit.paintBordersFor(composite);
        containedSection.setClient(composite);
        GridLayout gl_composite = new GridLayout(2, false);
        gl_composite.marginWidth = 0;
        gl_composite.marginHeight = 0;
        composite.setLayout(gl_composite);

        treeViewer = new TreeViewer(composite, SWT.BORDER);
        Tree tree = treeViewer.getTree();
        treeViewer.setContentProvider(new PerformanceTreeContentProvider());
        treeViewer.setLabelProvider(new PerformanceTreeLabelProvider());
        // TODO replace with data from DB is present
        treeViewer.setInput(new ArrayList<TreeTarget>());

        GridData gd_tree = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 8);
        gd_tree.heightHint = 241;
        tree.setLayoutData(gd_tree);
        formToolkit.paintBordersFor(tree);

        Label label = new Label(composite, SWT.NONE);
        label.setText(I18N.tr("Priority"));

        Combo combo = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
        combo.setLayoutData(LayoutUtil.createHorzFillData());
        initCommentAutoCompletion(combo);
        formToolkit.adapt(combo);
        formToolkit.paintBordersFor(combo);

        initalizeListViewer(targetListViewer);
        initalizeListViewer(activityListViewer);
        initalizeListViewer(currentListViewer);
        currentList.setEnabled(false);
        activityList.setEnabled(false);
    }

    private void initalizeListViewer(ListViewer viewer) {

        viewer.setContentProvider(ArrayContentProvider.getInstance());
        viewer.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                return ((ICatalogItem) element).getName();
            }
        });

    }

    @Override
    protected void checkSubclass() {
    }

    @Override
    public void dispose() {
        formToolkit.dispose();
        super.dispose();
    }

    @Override
    public void setSelectedComponent(Component component) {
    }

    public void initalize() {
        targetListViewer.getList().addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                IStructuredSelection selection = (IStructuredSelection) targetListViewer
                        .getSelection();
                if (selection == null) {
                    return;
                }
                Target t = (Target) selection.getFirstElement();

                try {

                    currentListViewer.setInput(catalogService.findCurrentByTarget(t));
                    currentListViewer.getList().setEnabled(true);
                }
                catch (DatabaseException e1) {
                    LOG.debug("An error occured", e);
                }
            }
        });

        targetListViewer.addDoubleClickListener(new IDoubleClickListener() {

            @Override
            public void doubleClick(DoubleClickEvent event) {
                IStructuredSelection selection = (IStructuredSelection) targetListViewer
                        .getSelection();
                if (selection == null) {
                    return;
                }
                Target t = (Target) selection.getFirstElement();
                TreeTarget tt = new TreeTarget(t);
                treeViewer.add(treeViewer.getInput(), tt);

            }
        });

        currentListViewer.getList().addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                IStructuredSelection selection = (IStructuredSelection) currentListViewer
                        .getSelection();
                if (selection == null) {
                    return;
                }
                Current c = (Current) selection.getFirstElement();

                try {

                    activityListViewer.setInput(catalogService.findActivityByCurrent(c));
                    activityListViewer.getList().setEnabled(true);
                }
                catch (DatabaseException e1) {
                    LOG.debug("An error occured", e);
                }
            }
        });
        
        addButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {

				IStructuredSelection selection = (IStructuredSelection) targetListViewer
						.getSelection();
				if (selection == null) {
					return;
				}

				Target target = (Target) selection.getFirstElement();
				selection = (IStructuredSelection) currentListViewer
						.getSelection();
				if (selection == null) {
					return;
				}
				Current current = (Current) selection.getFirstElement();

				selection = (IStructuredSelection) activityListViewer
						.getSelection();
				if (selection == null) {
					return;
				}
				Activity activity = (Activity) selection.getFirstElement();
	               
	               
	                treeViewer.add(treeViewer.getInput(),  
	                		PerformanceUtil.createTreeTriplet(target, current, activity));
	                
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});

    }
    
    private void initCommentAutoCompletion(Combo combo) {

		Collection<Priority> prioritities;
		try {
			prioritities = priorityService.findAll();
			String[] s = new String[prioritities.size()];
			int i = 0;

			for (Priority p : prioritities) {
				s[i] = p.getText();
				i++;
			}
			combo.setItems(s);
			ContentProposalUtil.enableContentProposal(combo);
		} catch (DatabaseException e) {
			LOG.debug("An error occured", e);
		}

	}

	@Override
	public void inspectionChanged(Inspection inspection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inspectionComponentSelectionChanged(SchemeComponent component) {
        if (component == null){
       		return;
        }
        Catalog c = component.getComponent().getCategory().get().getCatalog().get();
        try {

            targetListViewer.setInput(catalogService.findTargetByCatalog(c));
            currentListViewer.getList().removeAll();
            activityListViewer.getList().removeAll();
            currentListViewer.getList().setEnabled(false);
            activityListViewer.getList().setEnabled(false);

        }
        catch (DatabaseException e) {
            LOG.debug("An error occured", e);
        }
        initalize();
	}

	@Override
	public void plantChanged(Plant plant) {
		// TODO Auto-generated method stub
		
	}

}
