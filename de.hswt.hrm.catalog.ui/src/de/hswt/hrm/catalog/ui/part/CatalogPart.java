package de.hswt.hrm.catalog.ui.part;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class CatalogPart {

    private final static Logger LOG = LoggerFactory.getLogger(CatalogPart.class);

    @Inject
    private IEclipseContext context;

    private FormToolkit toolkit = new FormToolkit(Display.getDefault());

    private IContributionItem addContribution;
    private IContributionItem editContribution;

    private TabFolder tabFolder;
    private TabItem itemsTab;
    private TabItem catalogsTab;

    private CatalogItemsPart catalogItemsPart;
    private CatalogMatchingComposite matchComposite;

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
                    showCatalogItemsActions(true);
                }
                else {
                    showCatalogItemsActions(false);
                }
            }
        });
        tabFolder.setBackgroundMode(SWT.INHERIT_FORCE);
        toolkit.adapt(tabFolder);
        toolkit.paintBordersFor(tabFolder);

        itemsTab = new TabItem(tabFolder, SWT.NONE);
        itemsTab.setText("Catalog Items");

        catalogItemsPart = new CatalogItemsPart(tabFolder);
        // important: inject the services
        ContextInjectionFactory.inject(catalogItemsPart, context);

        itemsTab.setControl(catalogItemsPart);

        catalogsTab = new TabItem(tabFolder, SWT.NONE);
        catalogsTab.setText("Catalogs");

        matchComposite = new CatalogMatchingComposite(tabFolder);
        ContextInjectionFactory.inject(matchComposite, context);
        catalogsTab.setControl(matchComposite);

    }

    private void createActions() {
        // TODO translate
        Action editAction = new Action("Edit") {
            @Override
            public void run() {
                super.run();
                catalogItemsPart.editPlant();
            }
        };
        editAction.setDescription("Edit an exisitng catalog item.");
        editContribution = new ActionContributionItem(editAction);
        form.getToolBarManager().add(editContribution);

        Action addAction = new Action("Add") {
            @Override
            public void run() {
                super.run();
                catalogItemsPart.addCatalogItem();
            }
        };
        addAction.setDescription("Add's a new catalog item.");
        addContribution = new ActionContributionItem(addAction);
        form.getToolBarManager().add(addContribution);

        form.getToolBarManager().update(true);
    }

    private void showCatalogItemsActions(boolean show) {
        addContribution.setVisible(show);
        editContribution.setVisible(show);
        form.getToolBarManager().update(true);
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
