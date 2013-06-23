package de.hswt.hrm.plant.ui.part;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.component.service.ComponentService;
import de.hswt.hrm.i18n.I18n;
import de.hswt.hrm.i18n.I18nFactory;
import de.hswt.hrm.plant.model.Plant;
import de.hswt.hrm.plant.service.PlantService;
import de.hswt.hrm.plant.ui.shared.PlantComposite;
import de.hswt.hrm.scheme.model.Scheme;
import de.hswt.hrm.scheme.service.SchemeService;
import de.hswt.hrm.scheme.ui.part.SchemeComposite;

public class PlantPart {

    private final static Logger LOG = LoggerFactory.getLogger(PlantPart.class);
    private final static I18n I18N = I18nFactory.getI18n(PlantPart.class);

    @Inject
    private PlantService plantService;

    @Inject
    private SchemeService schemeService;

    @Inject
    private ComponentService componentService;

    @Inject
    private IShellProvider shellProvider;

    @Inject
    private IEclipseContext context;

    @Inject
    private EPartService service;

    private FormToolkit toolkit = new FormToolkit(Display.getDefault());

    private IContributionItem addContribution;
    private IContributionItem editContribution;
    private IContributionItem editSchemeContribution;

    private Section plantsHeaderSection;
    private TabFolder tabFolder;
    private TabItem plantsTab;
    private TabItem schemeTab;

    private Form form;

    private SchemeComposite schemeComposite;
    private PlantComposite plantComposite;

    public PlantPart() {
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
        form.setText(I18N.tr("Plants"));
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

        plantsTab = new TabItem(tabFolder, SWT.NONE);
        plantsTab.setText(I18N.tr("Plants"));

        tabFolder.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (tabFolder.getItem(tabFolder.getSelectionIndex()).equals(plantsTab)) {
                    showPlantActions();
                }
                else if (tabFolder.getItem(tabFolder.getSelectionIndex()).equals(schemeTab)) {
                    initSchemeTabForSelection();
                }
                else {
                    hideAllActions();
                    form.getToolBarManager().update(true);
                }
            }
        });

        plantsHeaderSection = toolkit.createSection(tabFolder, Section.TITLE_BAR);
        plantsTab.setControl(plantsHeaderSection);
        toolkit.paintBordersFor(plantsHeaderSection);
        plantsHeaderSection.setExpanded(true);
        FormUtil.initSectionColors(plantsHeaderSection);

        // composite = toolkit.createComposite(plantsHeaderSection, SWT.NONE);
        // toolkit.paintBordersFor(composite);
        // plantsHeaderSection.setClient(composite);
        // composite.setLayout(new GridLayout(1, false));

        plantComposite = new PlantComposite(plantsHeaderSection);
        ContextInjectionFactory.inject(plantComposite, context);
        plantsHeaderSection.setClient(plantComposite);

        GridData gd = LayoutUtil.createFillData();
        gd.widthHint = 800;
        gd.heightHint = 300;
        schemeTab = new TabItem(tabFolder, SWT.NONE);
        schemeTab.setText(I18N.tr("Scheme"));

        if (plantService == null) {
            LOG.error("PlantService not injected to PlantPart.");
        }
    }

    private void createActions() {
        // TODO translate
        Action schemeAction = new Action(I18N.tr("Edit Scheme")) {
            @Override
            public void run() {
                initSchemeTabForSelection();
            }
        };
        schemeAction.setDescription(I18N.tr("Edit the scheme of the selected plant."));
        editSchemeContribution = new ActionContributionItem(schemeAction);
        form.getToolBarManager().add(editSchemeContribution);

        Action editAction = new Action(I18N.tr("Edit")) {
            @Override
            public void run() {
                super.run();
                plantComposite.editPlant();
            }
        };
        editAction.setDescription(I18N.tr("Edit an exisitng plant."));
        editContribution = new ActionContributionItem(editAction);
        form.getToolBarManager().add(editContribution);

        Action addAction = new Action(I18N.tr("Add")) {
            @Override
            public void run() {
                super.run();
                plantComposite.addPlant();
            }
        };
        addAction.setDescription(I18N.tr("Add a new plant."));
        addContribution = new ActionContributionItem(addAction);
        form.getToolBarManager().add(addContribution);

        form.getToolBarManager().update(true);
    }

    private void showSchemeActions() {
        hideAllActions();
        for (IContributionItem item : schemeComposite.getContributionItems()) {
            item.setVisible(true);
        }
        form.getToolBarManager().update(true);
    }

    private void showPlantActions() {
        hideAllActions();
        addContribution.setVisible(true);
        editContribution.setVisible(true);
        editSchemeContribution.setVisible(true);
        form.getToolBarManager().update(true);
    }

    private void hideAllActions() {
        addContribution.setVisible(false);
        editContribution.setVisible(false);
        editSchemeContribution.setVisible(false);
        if (schemeComposite != null) {
            for (IContributionItem item : schemeComposite.getContributionItems()) {
                item.setVisible(false);
            }
        }
        form.getToolBarManager().update(true);
    }

    @PreDestroy
    public void dispose() {
        if (toolkit != null) {
            toolkit.dispose();
        }
    }

    protected void initSchemeTabForSelection() {
        hideAllActions();
        tabFolder.setSelection(schemeTab);

        Optional<Plant> plant = plantComposite.getPlant();
        if (!plant.isPresent()) {
            schemeTab.setControl(null);
            return;
        }

        Scheme scheme = null;
        try {
            scheme = schemeService.findCurrentSchemeByPlant(plant.get());
        }
        catch (ElementNotFoundException e) {
            scheme = new Scheme(plant.get());
            LOG.info(String.format("No scheme found for plant '%d'", plant.get().getId()));
        }
        catch (DatabaseException e) {
            LOG.error("Error loading scheme.", e);
            // FIXME: Add message box.
            return;
        }
        schemeComposite = new SchemeComposite(tabFolder, SWT.NONE, componentService, scheme);
        ContextInjectionFactory.inject(schemeComposite, context);
        for (IContributionItem item : schemeComposite.getContributionItems()) {
            form.getToolBarManager().add(item);
        }
        schemeTab.setControl(schemeComposite);

        showSchemeActions();
        schemeComposite.setVisible(true);
    }

}
