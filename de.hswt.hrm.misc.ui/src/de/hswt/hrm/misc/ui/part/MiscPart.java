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
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.i18n.I18n;
import de.hswt.hrm.i18n.I18nFactory;
import de.hswt.hrm.misc.ui.prioritycomposite.PriorityComposite;
import de.hswt.hrm.summary.ui.part.SummaryComposite;

import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class MiscPart {

    private final static Logger LOG = LoggerFactory.getLogger(MiscPart.class);
    private static final I18n I18N = I18nFactory.getI18n(MiscPart.class);

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
    private IContributionItem movePriorityUpContribution;
    private IContributionItem movePriorityDownContribution;
    private IContributionItem addStyleContribution;
    private IContributionItem editStyleContribution;

    private TabFolder tabFolder;

    private Form form;

    private TabItem summaryTab;
	private TabItem commentsTab;
	private TabItem priorityTab;
	private TabItem reportPreferencesTab;

	private SummaryComposite evaluationComposite;

	private CommentComposite commentsComposite;

	private ReportPreferencesComposite reportPreferencesComposite;
	
	private PriorityComposite priorityComposite;

    public MiscPart() {
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
        form.setText(I18N.tr("Miscellaneous"));
        toolkit.decorateFormHeading(form);

        FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
        fillLayout.marginHeight = 5;
        fillLayout.marginWidth = 5;
        form.getBody().setLayout(fillLayout);

        tabFolder = new TabFolder(form.getBody(), SWT.NONE);
        tabFolder.setBackgroundMode(SWT.INHERIT_FORCE);
        toolkit.adapt(tabFolder);
        toolkit.paintBordersFor(tabFolder);

        summaryTab = new TabItem(tabFolder, SWT.NONE);
        summaryTab.setText(I18N.tr("Summaries"));

        commentsTab = new TabItem(tabFolder, SWT.NONE);
        commentsTab.setText(I18N.tr("Comments"));

        priorityTab = new TabItem(tabFolder, SWT.NONE);
        priorityTab.setText(I18N.tr("Priorities"));
        
        reportPreferencesTab = new TabItem(tabFolder, SWT.NONE);
        reportPreferencesTab.setText(I18N.tr("Report preferences"));

        tabFolder.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (tabFolder.getItem(tabFolder.getSelectionIndex()).equals(summaryTab)) {
                    showSummaryActions();
                } else if (tabFolder.getItem(tabFolder.getSelectionIndex()).equals(commentsTab)) {
                    showCommentsActions();
                } else if (tabFolder.getItem(tabFolder.getSelectionIndex()).equals(priorityTab)) {
                	showPriorityActions();
                } else if (tabFolder.getItem(tabFolder.getSelectionIndex()).equals(reportPreferencesTab)) {
                	showReportPreferencesActions();
                } else {
                	hideAllActions();
                	form.getToolBarManager().update(true);
                }
            }
        });
        
        Section evaluationSection = toolkit.createSection(tabFolder, Section.TITLE_BAR);
        evaluationSection.setText(I18N.tr("Summaries"));
		summaryTab.setControl(evaluationSection);

        evaluationComposite = new SummaryComposite(evaluationSection);
		ContextInjectionFactory.inject(evaluationComposite, context);
		evaluationSection.setClient(evaluationComposite);

        Section commentsSection = toolkit.createSection(tabFolder, Section.TITLE_BAR);
        commentsSection.setText(I18N.tr("Comments"));
		commentsTab.setControl(commentsSection);

        commentsComposite = new CommentComposite(commentsSection);
		ContextInjectionFactory.inject(commentsComposite, context);
		commentsSection.setClient(commentsComposite);


        Section reportPreferencesSection = toolkit.createSection(tabFolder, Section.TITLE_BAR);
        reportPreferencesSection.setText(I18N.tr("Report preferences"));
		reportPreferencesTab.setControl(reportPreferencesSection);

        reportPreferencesComposite = new ReportPreferencesComposite(reportPreferencesSection);
		ContextInjectionFactory.inject(reportPreferencesComposite, context);
		reportPreferencesSection.setClient(reportPreferencesComposite);
		
        Section prioritySection = toolkit.createSection(tabFolder, Section.TITLE_BAR);
        prioritySection.setText(I18N.tr("Priorities"));
        priorityTab.setControl(prioritySection);
		
		priorityComposite = new PriorityComposite(prioritySection);
		ContextInjectionFactory.inject(priorityComposite, context);
		prioritySection.setClient(priorityComposite);

		createActions();
		showSummaryActions();
    }

    private void createActions() {
        Action addSummaryAction = new Action(I18N.tr("Add")) {
            @Override
            public void run() {
                evaluationComposite.addEvaluation();
            }
        };
        addSummaryAction.setDescription(I18N.tr("Add a new summary."));
        addSummaryContribution = new ActionContributionItem(addSummaryAction);

        Action editSummaryAction = new Action(I18N.tr("Edit")) {
            @Override
            public void run() {
                evaluationComposite.editEvaluation();
            }
        };
        editSummaryAction.setDescription(I18N.tr("Edit an existing summary."));
        editSummaryContribution = new ActionContributionItem(editSummaryAction);

        
        Action addCommentAction = new Action(I18N.tr("Add")) {
            @Override
            public void run() {
                commentsComposite.addComment();
            }
        };
        addCommentAction.setDescription(I18N.tr("Add a new comment."));
        addCommentContribution = new ActionContributionItem(addCommentAction);

        Action editCommentAction = new Action(I18N.tr("Edit")) {
            @Override
            public void run() {
               commentsComposite.editComment();
            }
        };
        editCommentAction.setDescription(I18N.tr("Edit an existing comment."));
        editCommentContribution = new ActionContributionItem(editCommentAction);

        Action addStyleAction = new Action(I18N.tr("Add Layout")) {
            @Override
            public void run() {
                reportPreferencesComposite.addPrefernence();
            }
        };
        addStyleAction.setDescription(I18N.tr("Add a new report layout."));
        addStyleContribution = new ActionContributionItem(addStyleAction);

        Action editStyleAction = new Action(I18N.tr("Edit Layout")) {
            @Override
            public void run() {
            	reportPreferencesComposite.editPreference();
            }
        };
        editStyleAction.setDescription(I18N.tr("Edit an existing layout."));
        editStyleContribution = new ActionContributionItem(editStyleAction);
        
        Action editPriorityAction = new Action(I18N.tr("Edit")) {
            @Override
            public void run() {
            	priorityComposite.editPriority();
            }
        };
        editPriorityAction.setDescription(I18N.tr("Edit an existing priority."));
        editPriorityContribution = new ActionContributionItem(editPriorityAction);
        
        Action addPriorityAction = new Action(I18N.tr("Add")) {
            @Override
            public void run() {
            	priorityComposite.addPriority();
            }
        };
        addPriorityAction.setDescription(I18N.tr("Add a new priority."));
        addPriorityContribution = new ActionContributionItem(addPriorityAction);
        
        Action movePriorityUPAction = new Action(I18N.tr("Move")+" "+I18N.tr("Up")) {
            @Override
            public void run() {
            	priorityComposite.movePriorityUp();
            }
        };
        movePriorityUPAction.setDescription(I18N.tr("Move priority up."));
        movePriorityUpContribution = new ActionContributionItem(movePriorityUPAction);
        Action movePriorityDownAction = new Action(I18N.tr("Move")+" "+I18N.tr("Down")) {
            @Override
            public void run() {
            	priorityComposite.movePriorityDown();
            }
        };
        movePriorityDownAction.setDescription(I18N.tr("Move priority down."));
        movePriorityDownContribution = new ActionContributionItem(movePriorityDownAction);
        
        form.getToolBarManager().add(editSummaryContribution);
        form.getToolBarManager().add(addSummaryContribution);
        form.getToolBarManager().add(editCommentContribution);
        form.getToolBarManager().add(addCommentContribution);
        form.getToolBarManager().add(editStyleContribution);
        form.getToolBarManager().add(addStyleContribution);
        form.getToolBarManager().add(movePriorityUpContribution);
        form.getToolBarManager().add(movePriorityDownContribution);
        form.getToolBarManager().add(editPriorityContribution);
        form.getToolBarManager().add(addPriorityContribution);


        form.getToolBarManager().update(true);
    }

    private void showSummaryActions() {
    	hideAllActions();
        editSummaryContribution.setVisible(true);
        addSummaryContribution.setVisible(true);
        form.getToolBarManager().update(true);
    }
    
    private void showCommentsActions() {
    	hideAllActions();
    	editCommentContribution.setVisible(true);
    	addCommentContribution.setVisible(true);
    	form.getToolBarManager().update(true);
    }

    private void showPriorityActions() {
    	hideAllActions();
    	addPriorityContribution.setVisible(true);
    	editPriorityContribution.setVisible(true);
    	movePriorityUpContribution.setVisible(true);
    	movePriorityDownContribution.setVisible(true);
    	form.getToolBarManager().update(true);
    }

    private void showReportPreferencesActions() {
    	hideAllActions();
        addStyleContribution.setVisible(true);
        editStyleContribution.setVisible(true);
    	form.getToolBarManager().update(true);
    }

    private void hideAllActions() {
        editSummaryContribution.setVisible(false);
        addSummaryContribution.setVisible(false);
        addCommentContribution.setVisible(false);
        editCommentContribution.setVisible(false);
        addStyleContribution.setVisible(false);
        editStyleContribution.setVisible(false);
        addPriorityContribution.setVisible(false);
        editPriorityContribution.setVisible(false);
        movePriorityUpContribution.setVisible(false);
        movePriorityDownContribution.setVisible(false);
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
