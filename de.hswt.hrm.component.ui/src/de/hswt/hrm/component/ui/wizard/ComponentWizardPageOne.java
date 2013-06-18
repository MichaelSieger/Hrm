package de.hswt.hrm.component.ui.wizard;

import java.util.ArrayList;

import javax.inject.Inject;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.List;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.LayoutUtil;
import de.hswt.hrm.common.ui.swt.layouts.PageContainerFillLayout;
import de.hswt.hrm.component.model.Category;
import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.component.service.CategoryService;

public class ComponentWizardPageOne extends WizardPage {

	@Inject
	private CategoryService categoryService;

	private Optional<Component> component;

	private final FormToolkit formToolkit = new FormToolkit(
			Display.getDefault());

	private Text nameText;

	private Text attributeText;

	private List attributeList;

	private Combo weightCombo;
	private ComboViewer categoryComboViewer;

	private int weight;

	private Category category;

	private String name;

	private boolean rating;

	private Button ratingCheckButton;

	private ArrayList<String> attributes = new ArrayList<String>();
	
	/**
	 * Create the wizard.
	 */
	public ComponentWizardPageOne(Optional<Component> component) {
		super("Component wizard");
		this.component = component;

		setTitle("Component wizard");
		setDescription(createDescription());
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		parent.setLayout(new PageContainerFillLayout());

		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);

		GridLayout gl = new GridLayout();
		gl.marginHeight = 0;
		gl.marginWidth = 0;
		container.setLayout(gl);

		Section definitionSection = formToolkit.createSection(container,
				Section.TITLE_BAR);
		definitionSection.setLayoutData(LayoutUtil.createHorzFillData());
		formToolkit.paintBordersFor(definitionSection);
		definitionSection.setText("Component definition");
		definitionSection.setExpanded(true);
		FormUtil.initSectionColors(definitionSection);
		
		Composite definitioncomposite = new Composite(definitionSection,
				SWT.NONE);
		formToolkit.adapt(definitioncomposite);
		formToolkit.paintBordersFor(definitioncomposite);
		definitionSection.setClient(definitioncomposite);
		definitioncomposite.setLayout(new GridLayout(2, false));

		Label nameLabel = new Label(definitioncomposite, SWT.NONE);
		nameLabel.setLayoutData(LayoutUtil.createLeftCenteredGridData());
		formToolkit.adapt(nameLabel, true, true);
		nameLabel.setText("Name");

		nameText = new Text(definitioncomposite, SWT.BORDER);
		nameText.setLayoutData(LayoutUtil.createHorzCenteredFillData());
		formToolkit.adapt(nameText, true, true);
		nameText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				checkPageComplete();
			}
		});

		Label categoryLabel = new Label(definitioncomposite, SWT.NONE);
		categoryLabel.setLayoutData(LayoutUtil.createLeftCenteredGridData());
		formToolkit.adapt(categoryLabel, true, true);
		categoryLabel.setText("Category");

		categoryComboViewer = new ComboViewer(definitioncomposite, SWT.NONE);
		categoryComboViewer.getCombo().setLayoutData(
				LayoutUtil.createHorzCenteredFillData());
		formToolkit.adapt(categoryComboViewer.getCombo());
		formToolkit.paintBordersFor(categoryComboViewer.getCombo());
		categoryComboViewer.getCombo().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				checkPageComplete();
			}
		});

		Label weightLabel = new Label(definitioncomposite, SWT.NONE);
		weightLabel.setLayoutData(LayoutUtil.createLeftCenteredGridData());
		formToolkit.adapt(weightLabel, true, true);
		weightLabel.setText("Weight");

		weightCombo = new Combo(definitioncomposite, SWT.NONE);
		weightCombo.setLayoutData(LayoutUtil.createHorzCenteredFillData());
		formToolkit.adapt(weightCombo);
		formToolkit.paintBordersFor(weightCombo);
		weightCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				checkPageComplete();
			}
		});

		ratingCheckButton = new Button(definitioncomposite, SWT.CHECK);
		ratingCheckButton.setLayoutData(LayoutUtil.createLeftCenteredGridData(
				2, 1));
		formToolkit.adapt(ratingCheckButton, true, true);
		ratingCheckButton.setText("with rating");

		Section attributesSection = formToolkit.createSection(container,
				Section.TITLE_BAR);
		attributesSection.setLayoutData(LayoutUtil.createFillData());
		formToolkit.paintBordersFor(attributesSection);
		attributesSection.setText("Attributes");
		attributesSection.setExpanded(true);
		FormUtil.initSectionColors(attributesSection);

		Composite attributesComposite = new Composite(attributesSection,
				SWT.NONE);
		formToolkit.adapt(attributesComposite);
		formToolkit.paintBordersFor(attributesComposite);
		attributesSection.setClient(attributesComposite);
		attributesComposite.setLayout(new GridLayout(2, false));

		attributeText = new Text(attributesComposite, SWT.BORDER);
		attributeText.setLayoutData(LayoutUtil.createHorzCenteredFillData());
		formToolkit.adapt(attributeText, true, true);

		Button addAttributeButton = new Button(attributesComposite, SWT.NONE);
		formToolkit.adapt(addAttributeButton, true, true);
		addAttributeButton.setText("Add");
		addAttributeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!attributeText.getText().isEmpty()) {
					attributeList.add(attributeText.getText());
				}
				attributes.clear();
				for (String attr : attributeList.getItems()) {
					attributes.add(attr);
				}
			}
		});

		attributeList = new List(attributesComposite, SWT.BORDER);
		attributeList.setLayoutData(LayoutUtil.createFillData(2));
		formToolkit.adapt(attributeList, true, true);

		initializeCombos();

		if (this.component.isPresent()) {
			updateFields(container);
		}
	}

	private String createDescription() {
		if (component.isPresent()) {
			return "Change a category.";
		}
		return "Define a new category";
	}

	private void initializeCombos() {
		for (int i = 1; i < 7; i++) {
			weightCombo.add(Integer.toString(i));
		}
		weightCombo.select(0);
		
		categoryComboViewer.setContentProvider(new ArrayContentProvider());
		categoryComboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Category) element).getName();
			}
		});

		try {
			categoryComboViewer.setInput(categoryService.findAll());
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		categoryComboViewer.refresh(true);
	}

	private void updateFields(Composite c) {
        Component comp = component.get();

        nameText.setText(comp.getName());
        ratingCheckButton.setSelection(comp.getBoolRating());

        if (comp.getQuantifier().isPresent()) {
            weightCombo.select(comp.getQuantifier().get() - 1);
        }
        
        StructuredSelection selection = 
        		new StructuredSelection(comp.getCategory().get());
        categoryComboViewer.setSelection(selection);
        categoryComboViewer.refresh();
    }

	private void checkPageComplete() {
		ISelection selection = categoryComboViewer.getSelection();
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		category = (Category) structuredSelection.getFirstElement();
		name = nameText.getText();

		if (nameText.getText().isEmpty()) {
			setPageComplete(false);
			setErrorMessage("Please provide a name.");
			return;
		}
		if (category != null) {
			setPageComplete(false);
			setErrorMessage("Please select a category.");
			return;
		}
		setPageComplete(true);
	}

	public String getName() {
		return name;
	}

	public int getQuantifier() {
		return weight;
	}

	public boolean getRating() {
		return rating;
	}

	public Category getCategory() {
		return category;
	}

	public java.util.List<String> getAttributes() {
		return attributes;
	}
}
