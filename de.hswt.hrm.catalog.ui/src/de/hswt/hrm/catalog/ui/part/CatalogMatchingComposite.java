package de.hswt.hrm.catalog.ui.part;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.catalog.model.Activity;
import de.hswt.hrm.catalog.model.Catalog;
import de.hswt.hrm.catalog.model.Current;
import de.hswt.hrm.catalog.model.ICatalogItem;
import de.hswt.hrm.catalog.model.Target;
import de.hswt.hrm.catalog.service.CatalogService;
import de.hswt.hrm.catalog.ui.event.CatalogMatchingEventHandler;
import de.hswt.hrm.catalog.ui.filter.CatalogTextFilter;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.common.ui.xwt.XwtHelper;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.RowLayout;

public class CatalogMatchingComposite extends Composite {

    private static final Logger LOG = LoggerFactory.getLogger(CatalogMatchingComposite.class);

    @Inject
    CatalogService catalogService;
    @Inject
    private IEclipseContext context;
    @Inject
    private IShellProvider shellProvider;
    private FormToolkit formToolkit = new FormToolkit(Display.getDefault());
    private Text matchedTargetSearch;
    private Text matchedCurrentSearch;
    private Text matchedActivitySearch;
    private Text availableTargetSearch;
    private Text availableCurrentSearch;
    private Text availableActivitySearch;
    private Text nameText;
    private Text descText;

    private ListViewer catalog;
    private ListViewer availableTarget;
    private ListViewer availableCurrent;
    private ListViewer availableActivity;
    private ListViewer matchedTarget;
    private ListViewer matchedCurrent;
    private ListViewer matchedActivity;

    private Button targetUp;
    private Button targetDown;
    private Button currentUp;
    private Button currentDown;
    private Button activityUp;
    private Button activityDown;

    private Collection<Catalog> catalogsFromDB;

    private List<Activity> activityFromDB;

    private List<Current> currentFromDB;

    private List<Target> targetFromDB;

    /**
     * Do not use this constructor when instantiate this composite! It is only included to make the
     * WindowsBuilder working.
     * 
     * @param parent
     * @param style
     */
    private CatalogMatchingComposite(Composite parent, int style) {
        super(parent, SWT.NONE);
        setBackgroundMode(SWT.INHERIT_FORCE);
        createControls();
    }

    /**
     * Create the composite.
     * 
     * @param parent
     */
    public CatalogMatchingComposite(Composite parent) {
        super(parent, SWT.NONE);
        formToolkit.dispose();
        formToolkit = FormUtil.createToolkit();
    }

    @PostConstruct
    public void createControls() {

        if (catalogService == null) {
            LOG.error("SchemeService not properly injected to SchemePart.");
        }
        GridLayout gridLayout = new GridLayout(4, true);
        gridLayout.marginLeft = 5;
        gridLayout.marginBottom = 5;
        setLayout(gridLayout);

        Section catalogSection = formToolkit.createSection(this, Section.TITLE_BAR);
        GridData gd_catalogSection = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        gd_catalogSection.heightHint = 333;
        gd_catalogSection.widthHint = 134;
        catalogSection.setLayoutData(gd_catalogSection);
        catalogSection.setBounds(0, 0, 105, 21);
        formToolkit.paintBordersFor(catalogSection);
        catalogSection.setText("Catalogs");

        catalog = new ListViewer(catalogSection, SWT.BORDER | SWT.V_SCROLL);
        org.eclipse.swt.widgets.List list = catalog.getList();
        catalogSection.setClient(list);

        Section matchedTargetSection = formToolkit.createSection(this, Section.TITLE_BAR);
        GridData gd_matchedTargetSection = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        gd_matchedTargetSection.heightHint = 222;
        gd_matchedTargetSection.widthHint = 166;
        matchedTargetSection.setLayoutData(gd_matchedTargetSection);
        matchedTargetSection.setBounds(0, 0, 105, 21);
        formToolkit.paintBordersFor(matchedTargetSection);
        matchedTargetSection.setText("Matched Target");

        Composite composite_1 = new Composite(matchedTargetSection, SWT.NONE);
        formToolkit.adapt(composite_1);
        formToolkit.paintBordersFor(composite_1);
        matchedTargetSection.setClient(composite_1);
        composite_1.setLayout(new GridLayout(1, false));

        matchedTargetSearch = new Text(composite_1, SWT.H_SCROLL | SWT.SEARCH | SWT.CANCEL);
        matchedTargetSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        formToolkit.adapt(matchedTargetSearch, true, true);

        matchedTarget = new ListViewer(composite_1, SWT.BORDER | SWT.V_SCROLL);
        org.eclipse.swt.widgets.List list_1 = matchedTarget.getList();
        list_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

        Section matchedCurrentSection = formToolkit.createSection(this, Section.TITLE_BAR);
        GridData gd_matchedCurrentSection = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        gd_matchedCurrentSection.heightHint = 299;
        gd_matchedCurrentSection.widthHint = 215;
        matchedCurrentSection.setLayoutData(gd_matchedCurrentSection);
        matchedCurrentSection.setBounds(0, 0, 105, 21);
        formToolkit.paintBordersFor(matchedCurrentSection);
        matchedCurrentSection.setText("Matched Current");

        Composite composite = new Composite(matchedCurrentSection, SWT.NONE);
        formToolkit.adapt(composite);
        formToolkit.paintBordersFor(composite);
        matchedCurrentSection.setClient(composite);
        composite.setLayout(new GridLayout(1, false));

        matchedCurrentSearch = new Text(composite, SWT.H_SCROLL | SWT.SEARCH | SWT.CANCEL);
        matchedCurrentSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        formToolkit.adapt(matchedCurrentSearch, true, true);

        matchedCurrent = new ListViewer(composite, SWT.BORDER | SWT.V_SCROLL);
        org.eclipse.swt.widgets.List list_2 = matchedCurrent.getList();
        list_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

        Section matchedActivitySection = formToolkit.createSection(this, Section.TITLE_BAR);
        GridData gd_matchedActivitySection = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        gd_matchedActivitySection.heightHint = 301;
        gd_matchedActivitySection.widthHint = 216;
        matchedActivitySection.setLayoutData(gd_matchedActivitySection);
        matchedActivitySection.setBounds(0, 0, 105, 21);
        formToolkit.paintBordersFor(matchedActivitySection);
        matchedActivitySection.setText("Matched Activity");

        Composite composite_2 = new Composite(matchedActivitySection, SWT.NONE);
        formToolkit.adapt(composite_2);
        formToolkit.paintBordersFor(composite_2);
        matchedActivitySection.setClient(composite_2);
        composite_2.setLayout(new GridLayout(1, false));

        matchedActivitySearch = new Text(composite_2, SWT.H_SCROLL | SWT.SEARCH | SWT.CANCEL);
        matchedActivitySearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        formToolkit.adapt(matchedActivitySearch, true, true);

        matchedActivity = new ListViewer(composite_2, SWT.BORDER | SWT.V_SCROLL);
        org.eclipse.swt.widgets.List list_3 = matchedActivity.getList();
        list_3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        new Label(this, SWT.NONE);

        Composite targetBtnComposite = formToolkit.createComposite(this, SWT.NONE);
        targetBtnComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
        targetBtnComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        formToolkit.paintBordersFor(targetBtnComposite);

        targetUp = formToolkit.createButton(targetBtnComposite, "New Button", SWT.ARROW
                | SWT.CENTER);

        targetDown = formToolkit.createButton(targetBtnComposite, "New Button", SWT.DOWN
                | SWT.ARROW | SWT.CENTER);

        Composite matchedBtnComposite = formToolkit.createComposite(this, SWT.NONE);
        matchedBtnComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        formToolkit.paintBordersFor(matchedBtnComposite);
        matchedBtnComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

        currentUp = formToolkit.createButton(matchedBtnComposite, "New Button", SWT.ARROW
                | SWT.CENTER);

        currentDown = formToolkit.createButton(matchedBtnComposite, "New Button", SWT.DOWN
                | SWT.ARROW | SWT.CENTER);

        Composite activityBtnComposite = formToolkit.createComposite(this, SWT.NONE);
        activityBtnComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        formToolkit.paintBordersFor(activityBtnComposite);
        activityBtnComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

        activityUp = formToolkit.createButton(activityBtnComposite, "New Button", SWT.ARROW
                | SWT.CENTER);

        activityDown = formToolkit.createButton(activityBtnComposite, "New Button", SWT.DOWN
                | SWT.ARROW | SWT.CENTER);

        Section sctnNewSection = formToolkit.createSection(this, Section.TITLE_BAR);
        GridData gd_sctnNewSection = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        gd_sctnNewSection.widthHint = 246;
        gd_sctnNewSection.heightHint = 306;
        sctnNewSection.setLayoutData(gd_sctnNewSection);
        formToolkit.paintBordersFor(sctnNewSection);
        sctnNewSection.setText("Information");

        Composite composite_3 = new Composite(sctnNewSection, SWT.NONE);
        formToolkit.adapt(composite_3);
        formToolkit.paintBordersFor(composite_3);
        sctnNewSection.setClient(composite_3);
        composite_3.setLayout(new GridLayout(3, false));

        Label nameLabel = new Label(composite_3, SWT.NONE);
        formToolkit.adapt(nameLabel, true, true);
        nameLabel.setText("Name");
        new Label(composite_3, SWT.NONE);

        nameText = new Text(composite_3, SWT.BORDER);
        nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        formToolkit.adapt(nameText, true, true);

        Label descLabel = new Label(composite_3, SWT.NONE);
        descLabel.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
        formToolkit.adapt(descLabel, true, true);
        descLabel.setText("Description");
        new Label(composite_3, SWT.NONE);

        descText = new Text(composite_3, SWT.BORDER | SWT.READ_ONLY | SWT.MULTI);
        descText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        formToolkit.adapt(descText, true, true);

        Section sctnNewSection_1 = formToolkit.createSection(this, Section.TITLE_BAR);
        GridData gd_sctnNewSection_1 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        gd_sctnNewSection_1.widthHint = 166;
        gd_sctnNewSection_1.heightHint = 306;
        sctnNewSection_1.setLayoutData(gd_sctnNewSection_1);
        formToolkit.paintBordersFor(sctnNewSection_1);
        sctnNewSection_1.setText("Available Target");

        Composite composite_4 = new Composite(sctnNewSection_1, SWT.NONE);
        formToolkit.adapt(composite_4);
        formToolkit.paintBordersFor(composite_4);
        sctnNewSection_1.setClient(composite_4);
        composite_4.setLayout(new GridLayout(1, false));

        availableTargetSearch = new Text(composite_4, SWT.H_SCROLL | SWT.SEARCH | SWT.CANCEL);
        availableTargetSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        formToolkit.adapt(availableTargetSearch, true, true);

        availableTarget = new ListViewer(composite_4, SWT.BORDER | SWT.V_SCROLL);
        org.eclipse.swt.widgets.List list_5 = availableTarget.getList();
        list_5.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

        Section sctnNewSection_2 = formToolkit.createSection(this, Section.TITLE_BAR);
        sctnNewSection_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        formToolkit.paintBordersFor(sctnNewSection_2);
        sctnNewSection_2.setText("Available Current");

        Composite composite_5 = new Composite(sctnNewSection_2, SWT.NONE);
        formToolkit.adapt(composite_5);
        formToolkit.paintBordersFor(composite_5);
        sctnNewSection_2.setClient(composite_5);
        composite_5.setLayout(new GridLayout(1, false));

        availableCurrentSearch = new Text(composite_5, SWT.H_SCROLL | SWT.SEARCH | SWT.CANCEL);
        availableCurrentSearch
                .setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        formToolkit.adapt(availableCurrentSearch, true, true);

        availableCurrent = new ListViewer(composite_5, SWT.BORDER | SWT.V_SCROLL);
        org.eclipse.swt.widgets.List list_6 = availableCurrent.getList();
        list_6.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

        Section sctnNewSection_3 = formToolkit.createSection(this, Section.TITLE_BAR);
        sctnNewSection_3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        formToolkit.paintBordersFor(sctnNewSection_3);
        sctnNewSection_3.setText("Available Activity");

        Composite composite_6 = new Composite(sctnNewSection_3, SWT.NONE);
        formToolkit.adapt(composite_6);
        formToolkit.paintBordersFor(composite_6);
        sctnNewSection_3.setClient(composite_6);
        composite_6.setLayout(new GridLayout(1, false));

        availableActivitySearch = new Text(composite_6, SWT.H_SCROLL | SWT.SEARCH | SWT.CANCEL);
        availableActivitySearch
                .setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        formToolkit.adapt(availableActivitySearch, true, true);

        availableActivity = new ListViewer(composite_6, SWT.BORDER | SWT.V_SCROLL);
        org.eclipse.swt.widgets.List list_7 = availableActivity.getList();
        list_7.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

        initData();
        initListViewers();

    }

    // Obtain items from the Database
    private void initData() {
        try {
            catalogsFromDB = catalogService.findAllCatalog();
            activityFromDB = (List<Activity>) catalogService.findAllActivity();
            currentFromDB = (List<Current>) catalogService.findAllCurrent();
            targetFromDB = (List<Target>) catalogService.findAllTarget();
        }
        catch (DatabaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void initListViewers() {
        initializeCatalogViewer(catalog);
        initalize(availableActivity);
        initalize(availableCurrent);
        initalize(availableTarget);
        initalize(matchedActivity);
        initalize(matchedCurrent);
        initalize(matchedTarget);
        fillAvailableViewer(targetFromDB, currentFromDB, activityFromDB);

    }

    // Init Catalog TableViewer
    private void initializeCatalogViewer(ListViewer catalog) {
        catalog.setContentProvider(ArrayContentProvider.getInstance());
        catalog.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                Catalog c = (Catalog) element;
                return c.getName();
            }
        });
        catalog.setComparator(new ViewerComparator() {
            @Override
            public int compare(Viewer viewer, Object e1, Object e2) {

                Catalog c1 = (Catalog) e1;
                Catalog c2 = (Catalog) e2;
                return c1.getName().compareToIgnoreCase(c2.getName());

            }

        });
        catalog.setInput(catalogsFromDB);
    }

    private void initalize(ListViewer lv) {

        lv.setContentProvider(ArrayContentProvider.getInstance());

        lv.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                ICatalogItem c = (ICatalogItem) element;
                return c.getName();
            }
        });

        lv.setComparator(new ViewerComparator() {

            @Override
            public int compare(Viewer viewer, Object e1, Object e2) {

                ICatalogItem ic1 = (ICatalogItem) e1;
                ICatalogItem ic2 = (ICatalogItem) e2;
                return ic1.getName().compareToIgnoreCase(ic2.getName());

            }

        });
    }

    private void fillAvailableViewer(Collection<Target> targets, Collection<Current> currents,
            Collection<Activity> activities) {

        availableTarget.setInput(targets);
        availableCurrent.setInput(currents);
        availableActivity.setInput(activities);

        availableActivity.addFilter(new CatalogTextFilter());
        availableCurrent.addFilter(new CatalogTextFilter());
        availableTarget.addFilter(new CatalogTextFilter());

        matchedTarget.addFilter(new CatalogTextFilter());
        matchedActivity.addFilter(new CatalogTextFilter());
        matchedCurrent.addFilter(new CatalogTextFilter());

    }
}
