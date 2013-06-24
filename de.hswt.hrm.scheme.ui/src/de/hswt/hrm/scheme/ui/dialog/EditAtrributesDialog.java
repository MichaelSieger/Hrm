package de.hswt.hrm.scheme.ui.dialog;

import java.util.Collection;
import java.util.List;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.widgets.Section;

import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.common.ui.swt.table.TableViewerController;
import de.hswt.hrm.common.ui.swt.utils.SWTResourceManager;
import de.hswt.hrm.component.model.Attribute;
import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.scheme.ui.util.SchemeDialogUtil;

public class EditAtrributesDialog extends TitleAreaDialog {

    private Component component;

    private Table table;
    private TableViewer tableViewer;
    private Collection<Attribute> attributes;

    public EditAtrributesDialog(Shell parentShell, Component component,
            Collection<Attribute> attributes) {
        super(parentShell);
        this.component = component;
        this.attributes = attributes;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);

        // Contents of Dialog
        draw(composite);

        setMessage("Edit Attributes for Component: " + component.getName());
        setTitle("Attributes ");

        return composite;

    }

    private void draw(Composite parent) {
        Section headerSection = new Section(parent, Section.TITLE_BAR);
        headerSection.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        headerSection.setText("Available Attributes");
        headerSection.setLayoutData(LayoutUtil.createFillData());
        FormUtil.initSectionColors(headerSection);
        headerSection.setLayout(new FillLayout());
        headerSection.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        Composite headerComposite = new Composite(headerSection, SWT.NONE);
        GridLayout gl = new GridLayout(2, false);
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        headerComposite.setLayout(gl);
        headerComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

        tableViewer = new TableViewer(headerSection, SWT.BORDER | SWT.FULL_SELECTION);
        table = tableViewer.getTable();
        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        headerSection.setClient(table);
        GridData gd = LayoutUtil.createFillData();
        gd.widthHint = 800;
        gd.heightHint = 300;
        table.setLayoutData(gd);
        initTable();
    }

    private void initTable() {

        List<ColumnDescription<Attribute>> columns = SchemeDialogUtil.getColumns();

        TableViewerController<Attribute> filler = new TableViewerController<>(tableViewer);
        filler.createColumns(columns);
        filler.createColumnSelectionMenu();

        tableViewer.setContentProvider(ArrayContentProvider.getInstance());
        tableViewer.setInput(attributes);
        tableViewer.setComparator(new ViewerComparator() {
            @Override
            public int compare(Viewer viewer, Object o1, Object o2) {
                Attribute a1 = (Attribute) o1;
                Attribute a2 = (Attribute) o2;
                return a1.getName().compareToIgnoreCase(a2.getName());
            }
        });

    }
}
