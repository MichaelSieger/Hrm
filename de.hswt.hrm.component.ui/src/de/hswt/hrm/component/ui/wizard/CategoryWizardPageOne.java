package de.hswt.hrm.component.ui.wizard;

import java.net.URL;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.e4.xwt.forms.XWTForms;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.ui.swt.layouts.PageContainerFillLayout;
import de.hswt.hrm.component.model.Category;

public class CategoryWizardPageOne extends WizardPage {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryWizardPageOne.class);
    private static int MAX_QUANIFIER = 10;

    private Composite container;
    private Optional<Category> category;

    private int[] validGridSizeValue = { 1, 2, 4, 6 };

    private Text nameText;

    private Combo weightCombo;

    private List widthList;

    private List heightList;

    private Button ratingCheckButton;

    private String name;

    private int weight;

    private int width;

    private int height;

    private boolean rating;

    private boolean first = true;

    public CategoryWizardPageOne(String title, Optional<Category> category) {
        super(title);
        this.category = category;
        setDescription(createDescription());
    }

    private String createDescription() {
        if (category.isPresent()) {
            return "Change a category.";
        }
        return "Define a new category";
    }

    public void createControl(Composite parent) {
        parent.setLayout(new PageContainerFillLayout());
        URL url = CategoryWizardPageOne.class.getClassLoader().getResource(
                "de/hswt/hrm/component/ui/xwt/CategoryWizardPageOne"
                        + IConstants.XWT_EXTENSION_SUFFIX);
        try {
            container = (Composite) XWTForms.load(parent, url);
        }
        catch (Exception e) {
            LOG.error("An error occured: ", e);
        }

        nameText = (Text) XWT.findElementByName(container, "name");
        weightCombo = (Combo) XWT.findElementByName(container, "defaultQuantifier");
        widthList = (List) XWT.findElementByName(container, "width");
        heightList = (List) XWT.findElementByName(container, "height");
        ratingCheckButton = (Button) XWT.findElementByName(container, "defaultBoolRating");

        initItems();

        if (this.category.isPresent()) {
            updateFields();
        }

        nameText.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
                checkPageComplete();
            }
        });

        addSelectionListener(widthList);
        addSelectionListener(weightCombo);
        addSelectionListener(heightList);
        addSelectionListener(ratingCheckButton);

        setControl(container);
        checkPageComplete();
    }

    private void initItems() {
        for (Integer value : validGridSizeValue) {
            widthList.add(String.valueOf(value));
            heightList.add(String.valueOf(value));
        }
        for (int i = 1; i <= MAX_QUANIFIER; i++) {
            weightCombo.add(String.valueOf(i));
        }
    }

    private void updateFields() {
        Category cat = category.get();
        nameText.setText(cat.getName());
        weightCombo.select(weightCombo.indexOf(String.valueOf(cat.getDefaultQuantifier())));
        ratingCheckButton.setSelection(cat.getDefaultBoolRating());
        widthList.select(widthList.indexOf(String.valueOf(cat.getWidth())));
        heightList.select(heightList.indexOf(String.valueOf(cat.getHeight())));
    }

    private void checkPageComplete() {
        if (first) {
            first = false;
            setPageComplete(false);
            return;
        }

        rating = ratingCheckButton.getSelection();

        setErrorMessage(null);
        // FIXME check if category is not empty or it already exists
        name = nameText.getText();

        if (weightCombo.getSelectionIndex() > -1) {
            weight = Integer.parseInt(weightCombo.getItem(weightCombo.getSelectionIndex()));
        }
        else {
            setErrorMessage("Select a standard weight.");
            return;
        }

        if (widthList.getSelectionIndex() > -1) {
            width = Integer.parseInt(widthList.getItem(widthList.getSelectionIndex()));
        }
        else {
            setErrorMessage("Select a grid width.");
            return;
        }

        if (heightList.getSelectionIndex() > -1) {
            height = Integer.parseInt(heightList.getItem(heightList.getSelectionIndex()));
        }
        else {
            setErrorMessage("Select a grid height.");
            return;
        }
    }

    @Override
    public void setErrorMessage(String newMessage) {
        if (newMessage == null || newMessage.isEmpty()) {
            setPageComplete(true);
        }
        else {
            setPageComplete(false);
        }
        super.setErrorMessage(newMessage);
    }

    private void addSelectionListener(Control control) {
        control.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                checkPageComplete();
            }
        });
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isRating() {
        return rating;
    }
}
