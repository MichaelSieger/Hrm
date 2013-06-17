package de.hswt.hrm.misc.ui.part;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.evaluation.ui.part.EvaluationComposite;

import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class MiskPart {

    private final static Logger LOG = LoggerFactory.getLogger(MiskPart.class);

    @Inject
    private IShellProvider shellProvider;

    @Inject
    private IEclipseContext context;

    @Inject
    private EPartService service;

    private FormToolkit toolkit = new FormToolkit(Display.getDefault());

    private IContributionItem addSummaryContribution;
    private IContributionItem editSummaryContribution;
    private IContributionItem addCommentContribution;
    private IContributionItem editCommentContribution;
    private IContributionItem addPriorityContribution;
    private IContributionItem editPriorityContribution;

    private TabFolder tabFolder;

    private Form form;

    private TabItem summaryTab;
	private TabItem commentsTab;
	private TabItem priorityTab;
	private TabItem reportDirectoryTab;

	private EvaluationComposite evaluationComposite;

    public MiskPart() {
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
        toolkit.setBorderStyle(SWT.BORDER);
        toolkit.adapt(parent);
        toolkit.paintBordersFor(parent);
        parent.setLayout(new FillLayout(SWT.HORIZONTAL));

        form = toolkit.createForm(parent);
        form.getHead().setOrientation(SWT.RIGHT_TO_LEFT);
        form.setSeparatorVisible(true);
        form.getBody().setBackgroundMode(SWT.INHERIT_FORCE);
        toolkit.paintBordersFor(form);
        form.setText("Plants");
        toolkit.decorateFormHeading(form);
        createActions();

        FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
        fillLayout.marginHeight = 5;
        fillLayout.marginWidth = 5;
        form.getBody().setLayout(fillLayout);

        tabFolder = new TabFolder(form.getBody(), SWT.NONE);
        tabFolder.setBackgroundMode(SWT.INHERIT_FORCE);
        toolkit.adapt(tabFolder);
        toolkit.paintBordersFor(tabFolder);

        summaryTab = new TabItem(tabFolder, SWT.NONE);
        summaryTab.setText("Summaries");

        commentsTab = new TabItem(tabFolder, SWT.NONE);
        commentsTab.setText("Comments");

        priorityTab = new TabItem(tabFolder, SWT.NONE);
        priorityTab.setText("Priorities");
        
        reportDirectoryTab = new TabItem(tabFolder, SWT.NONE);
        reportDirectoryTab.setText("Report directory");

        tabFolder.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (tabFolder.getItem(tabFolder.getSelectionIndex()).equals(summaryTab)) {
                    showSummaryActions();
                } else if (tabFolder.getItem(tabFolder.getSelectionIndex()).equals(commentsTab)) {
                    showCommentsActions();
                } else if (tabFolder.getItem(tabFolder.getSelectionIndex()).equals(priorityTab)) {
                	showPriorityActions();
                } else if (tabFolder.getItem(tabFolder.getSelectionIndex()).equals(reportDirectoryTab)) {
                	showReportDirectoryActions();
                } else {
                	hideAllActions();
                	form.getToolBarManager().update(true);
                }
            }
        });
        
        evaluationComposite = new EvaluationComposite(tabFolder);
		ContextInjectionFactory.inject(evaluationComposite, context);
		summaryTab.setControl(evaluationComposite);
    }

    private void createActions() {
        // TODO translate
        Action addSummaryAction = new Action("Add summary.") {
            @Override
            public void run() {
                super.run();
                evaluationComposite.addEvaluation();
            }
        };
        addSummaryAction.setDescription("Add's a new summary.");
        addSummaryContribution = new ActionContributionItem(addSummaryAction);
        form.getToolBarManager().add(addSummaryContribution);

        Action editSummaryAction = new Action("Edit") {
            @Override
            public void run() {
                super.run();
                evaluationComposite.editEvaluation();
            }
        };
        editSummaryAction.setDescription("Edit an exisitng plant.");
        editSummaryContribution = new ActionContributionItem(editSummaryAction);
        form.getToolBarManager().add(editSummaryContribution);

        form.getToolBarManager().update(true);
    }

    private void showSummaryActions() {
    	hideAllActions();
//    	for (IContributionItem item : schemeComposite.getContributionItems()) {
//        	item.setVisible(true);
//        }
        form.getToolBarManager().update(true);
    }
    
    private void showCommentsActions() {
    	hideAllActions();

    	form.getToolBarManager().update(true);
    }

    private void showPriorityActions() {
    	hideAllActions();

    	form.getToolBarManager().update(true);
    }

    private void showReportDirectoryActions() {
    	hideAllActions();

    	form.getToolBarManager().update(true);
    }

    private void hideAllActions() {
        editSummaryContribution.setVisible(false);
        addSummaryContribution.setVisible(false);
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
