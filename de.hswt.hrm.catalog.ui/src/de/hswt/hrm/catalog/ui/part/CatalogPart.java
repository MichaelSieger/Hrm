package de.hswt.hrm.catalog.ui.part;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;

import de.hswt.hrm.common.ui.swt.forms.FormUtil;

public class CatalogPart {

    @Inject
    private IEclipseContext context;

    private FormToolkit toolkit = new FormToolkit(Display.getDefault());

    private IContributionItem addCatalogItemContribution;
    private IContributionItem editCatalogItemContribution;
    private IContributionItem addCatalogContribution;
    private IContributionItem editCatalogContribution;

    private TabFolder tabFolder;
    private TabItem itemsTab;
    private TabItem assignmentTab;

    private CatalogItemsComposite catalogItemsPart;
    private CatalogAssignmentComposite assignmentComposite;

    private Form form;

    public CatalogPart() {
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
        form.setText("Catalogs");
        toolkit.decorateFormHeading(form);
        createActions();

        FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
        fillLayout.marginHeight = 5;
        fillLayout.marginWidth = 5;
        form.getBody().setLayout(fillLayout);

        tabFolder = new TabFolder(form.getBody(), SWT.NONE);
        tabFolder.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (tabFolder.getItem(tabFolder.getSelectionIndex()).equals(itemsTab)) {
                    showCatalogItemsAction();
                }
                else if (tabFolder.getItem(tabFolder.getSelectionIndex()).equals(assignmentTab)) {
                    showCatalogActions();
                }
            }

            private void showCatalogActions() {
                hideAllItems();
                editCatalogContribution.setVisible(true);
                addCatalogContribution.setVisible(true);
                form.getToolBarManager().update(true);
            }

            private void showCatalogItemsAction() {
                hideAllItems();
                editCatalogItemContribution.setVisible(true);
                addCatalogItemContribution.setVisible(true);
                form.getToolBarManager().update(true);
            }
        });
        tabFolder.setBackgroundMode(SWT.INHERIT_FORCE);
        toolkit.adapt(tabFolder);
        toolkit.paintBordersFor(tabFolder);

        itemsTab = new TabItem(tabFolder, SWT.NONE);
        itemsTab.setText("Catalog items");

        catalogItemsPart = new CatalogItemsComposite(tabFolder);
        // important: inject the services
        ContextInjectionFactory.inject(catalogItemsPart, context);

        itemsTab.setControl(catalogItemsPart);

        assignmentTab = new TabItem(tabFolder, SWT.NONE);
        assignmentTab.setText("Item assignment");

        assignmentComposite = new CatalogAssignmentComposite(tabFolder);
        ContextInjectionFactory.inject(assignmentComposite, context);
        assignmentTab.setControl(assignmentComposite);

    }

    private void createActions() {
        // TODO translate
        Action editCatalogItemAction = new Action("Edit") {
            @Override
            public void run() {
                super.run();
                catalogItemsPart.editItem();
            }
        };
        editCatalogItemAction.setDescription("Edit an exisitng catalog item.");
        editCatalogItemContribution = new ActionContributionItem(editCatalogItemAction);
        form.getToolBarManager().add(editCatalogItemContribution);

        Action editCatalogAction = new Action("Edit") {
            @Override
            public void run() {
                super.run();
                // TODO Edit a Catalog
            }
        };
        editCatalogAction.setDescription("Edit an Existing Catalog");
        editCatalogContribution = new ActionContributionItem(editCatalogAction);
        form.getToolBarManager().add(editCatalogContribution);

        Action addCatalogItemAction = new Action("Add") {
            @Override
            public void run() {
                super.run();
                catalogItemsPart.addCatalogItem();
            }
        };
        addCatalogItemAction.setDescription("Add's a new catalog item.");
        addCatalogItemContribution = new ActionContributionItem(addCatalogItemAction);
        form.getToolBarManager().add(addCatalogItemContribution);

        Action addCatalogAction = new Action("Add") {
            @Override
            public void run() {
                super.run();
                // TODO Add a new Catalog
            }
        };
        addCatalogAction.setDescription("Add's a new catalog .");
        addCatalogContribution = new ActionContributionItem(addCatalogAction);
        form.getToolBarManager().add(addCatalogContribution);

        form.getToolBarManager().update(true);
    }

    private void hideAllItems() {
        editCatalogContribution.setVisible(false);
        addCatalogContribution.setVisible(false);
        editCatalogItemContribution.setVisible(false);
        addCatalogItemContribution.setVisible(false);
    }

    @PreDestroy
    public void dispose() {
        if (toolkit != null) {
            toolkit.dispose();
        }
    }

}
