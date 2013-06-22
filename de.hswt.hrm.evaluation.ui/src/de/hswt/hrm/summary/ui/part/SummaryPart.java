package de.hswt.hrm.summary.ui.part;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.summary.model.Summary;
import de.hswt.hrm.summary.service.SummaryService;

public class SummaryPart {

    private final static Logger LOG = LoggerFactory.getLogger(SummaryPart.class);

    @Inject
    private SummaryService evalService;

    @Inject
    private IShellProvider shellProvider;

    @Inject
    private IEclipseContext context;

    private FormToolkit toolkit = new FormToolkit(Display.getDefault());
    private TableViewer tableViewer;

    private Action editAction;
    private Action addAction;
    private Section headerSection;
    private Composite evaluationComposite;

    private Collection<Summary> evaluations;

    private Form form;

    public SummaryPart() {
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
        toolkit.paintBordersFor(form);
        form.setText("Evaluation");
        toolkit.decorateFormHeading(form);

        FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
        fillLayout.marginHeight = 1;
        form.getBody().setLayout(fillLayout);

        headerSection = toolkit.createSection(form.getBody(), Section.TITLE_BAR);
        toolkit.paintBordersFor(headerSection);
        headerSection.setExpanded(true);
        FormUtil.initSectionColors(headerSection);

        evaluationComposite = new SummaryComposite(headerSection);
        ContextInjectionFactory.inject(evaluationComposite, context);
        headerSection.setClient(evaluationComposite);

        createActions();
    }

    private void createActions() {
        editAction = new Action("Edit") {
            @Override
            public void run() {
                super.run();
                editEvaluation();
            }
        };
        editAction.setDescription("Edit an exisitng Evaluation.");
        addAction = new Action("Add") {
            @Override
            public void run() {
                super.run();
                addEvaluation();
            }
        };
        addAction.setDescription("Add's a new Evaluation.");

        form.getToolBarManager().add(editAction);
        form.getToolBarManager().add(addAction);
        form.getToolBarManager().update(true);
    }

    @PreDestroy
    public void dispose() {
        if (toolkit != null) {
            toolkit.dispose();
        }
    }

    private void showDBConnectionError() {
        // TODO translate
        MessageDialog.openError(shellProvider.getShell(), "Connection Error",
                "Could not load evaluations from Database.");
    }

    /**
     * This Event is called whenever the add button is pressed.
     * 
     * @param event
     */

    private void addEvaluation() {
        Summary eval = null;

        Optional<Summary> newEval = SummaryPartUtil.showWizard(context,
                shellProvider.getShell(), Optional.fromNullable(eval));

        if (newEval.isPresent()) {
            evaluations.add(newEval.get());
            tableViewer.refresh();
        }
    }

    /**
     * This method is called whenever a doubleClick onto the Tableviewer occurs. It obtains the
     * contact from the selected column of the TableViewer. The Contact is passed to the
     * ContactWizard. When the Wizard has finished, the contact will be updated in the Database
     * 
     * @param event
     *            Event which occured within SWT
     */
    private void editEvaluation() {
        // obtain the contact in the column where the doubleClick happend
        Summary selectedEval = (Summary) tableViewer.getElementAt(tableViewer.getTable()
                .getSelectionIndex());
        if (selectedEval == null) {
            return;
        }
        try {
            evalService.refresh(selectedEval);
            Optional<Summary> updatedEval = SummaryPartUtil.showWizard(context,
                    shellProvider.getShell(), Optional.of(selectedEval));

            if (updatedEval.isPresent()) {
                tableViewer.refresh();
            }
        }
        catch (DatabaseException e) {
            LOG.error("Could not retrieve the place from database.", e);
            showDBConnectionError();
        }
    }

}
