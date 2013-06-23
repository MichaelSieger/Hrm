package de.hswt.hrm.component.ui.part;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.window.IShellProvider;
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
import org.eclipse.ui.forms.widgets.Section;

import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.component.service.CategoryService;

public class ComponentPart {

    @Inject
    private CategoryService categoryService;

    @Inject
    private IShellProvider shellProvider;

    @Inject
    private IEclipseContext context;

    private FormToolkit toolkit = new FormToolkit(Display.getDefault());
    private Form form;

    private TabFolder tabFolder;

    private TabItem categoriesTab;
    private TabItem componentsTab;

    private ActionContributionItem editCategoryContribution;
    private ActionContributionItem addCategoryContribution;
    private ActionContributionItem editComponentContribution;
    private ActionContributionItem addComponentContribution;

    private CategoryComposite categoriesComposite;
    private ComponentComposite componentComposite;

    public ComponentPart() {
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
        form.setText("Components");
        toolkit.decorateFormHeading(form);

        FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
        fillLayout.marginHeight = 5;
        fillLayout.marginWidth = 5;
        form.getBody().setLayout(fillLayout);

        tabFolder = new TabFolder(form.getBody(), SWT.NONE);
        tabFolder.setBackgroundMode(SWT.INHERIT_FORCE);
        tabFolder.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (tabFolder.getItem(tabFolder.getSelectionIndex()).equals(categoriesTab)) {
                    showCategoriesActions();
                }
                else if (tabFolder.getItem(tabFolder.getSelectionIndex()).equals(componentsTab)) {
                    showComponentsActions();
                }
            }
        });

        categoriesTab = new TabItem(tabFolder, SWT.NONE);
        categoriesTab.setText("Categories");

        componentsTab = new TabItem(tabFolder, SWT.NONE);
        componentsTab.setText("Components");

        Section categoriesSection = toolkit.createSection(tabFolder, Section.TITLE_BAR);
        categoriesSection.setText("Component categories");
        categoriesTab.setControl(categoriesSection);

        categoriesComposite = new CategoryComposite(categoriesSection);
        ContextInjectionFactory.inject(categoriesComposite, context);
        categoriesSection.setClient(categoriesComposite);

        Section componentsSection = toolkit.createSection(tabFolder, Section.TITLE_BAR);
        componentsSection.setText("Components");
        componentsTab.setControl(componentsSection);

        createActions();

        componentComposite = new ComponentComposite(componentsSection);
        ContextInjectionFactory.inject(componentComposite, context);
        componentsSection.setClient(componentComposite);
    }

    protected void showComponentsActions() {
        hideAllItems();
        editComponentContribution.setVisible(true);
        addComponentContribution.setVisible(true);
        form.getToolBarManager().update(true);
    }

    protected void showCategoriesActions() {
        hideAllItems();
        editCategoryContribution.setVisible(true);
        addCategoryContribution.setVisible(true);
        form.getToolBarManager().update(true);
    }

    private void hideAllItems() {
        editCategoryContribution.setVisible(false);
        addCategoryContribution.setVisible(false);
        editComponentContribution.setVisible(false);
        addComponentContribution.setVisible(false);
    }

    private void createActions() {
        Action editCategoryAction = new Action("Edit") {
            @Override
            public void run() {
                categoriesComposite.editCategory();
            }
        };
        editCategoryAction.setDescription("Edit an exisitng category.");
        editCategoryContribution = new ActionContributionItem(editCategoryAction);

        Action addCategoryAction = new Action("Add") {
            @Override
            public void run() {
                categoriesComposite.addCategory();
            }
        };
        addCategoryAction.setDescription("Add's a new category.");
        addCategoryContribution = new ActionContributionItem(addCategoryAction);

        Action editComponentAction = new Action("Edit") {
            @Override
            public void run() {
                componentComposite.editComponent();
            }
        };
        editComponentAction.setDescription("Edit an exisitng component.");
        editComponentContribution = new ActionContributionItem(editComponentAction);

        Action addComponentAction = new Action("Add") {
            @Override
            public void run() {
                componentComposite.addComponent();
            }
        };
        addComponentAction.setDescription("Add's a new component.");
        addComponentContribution = new ActionContributionItem(addComponentAction);

        form.getToolBarManager().add(editCategoryContribution);
        form.getToolBarManager().add(addCategoryContribution);
        form.getToolBarManager().add(editComponentContribution);
        form.getToolBarManager().add(addComponentContribution);
        form.getToolBarManager().update(true);
    }

    @PreDestroy
    public void dispose() {
        if (toolkit != null) {
            toolkit.dispose();
        }
    }

}
