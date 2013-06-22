package de.hswt.hrm.misc.ui.PreferencesWizard;

import java.net.URL;
import java.util.Collection;

import javax.inject.Inject;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.e4.xwt.forms.XWTForms;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.layouts.PageContainerFillLayout;
import de.hswt.hrm.evaluation.model.Evaluation;
import de.hswt.hrm.evaluation.service.EvaluationService;
import de.hswt.hrm.misc.reportPreferences.model.ReportPreference;

public class PreferencesWizardPageOne extends WizardPage {

    private Optional<ReportPreference> preference;
    private Composite container;
    private Text nameText;
    private Text fileText;

    private Collection<ReportPreference> preferences;
    private boolean first = true;

//    @Inject
//    private ReportPreferencesSerice prefService;

    private static final Logger LOG = LoggerFactory.getLogger(PreferencesWizardPageOne.class);

    public PreferencesWizardPageOne(String title, Optional<ReportPreference> preference) {
        super(title);
        this.preference = preference;
        setDescription(createDescription());
        setTitle("Summary Wizard");
    }

    private String createDescription() {
        if (preference.isPresent()) {
            return "Change a Preference";
        }
        return "Add a new Preference";
    }

    @Override
    public void createControl(Composite parent) {
        parent.setLayout(new PageContainerFillLayout());
        URL url = PreferencesWizardPageOne.class.getClassLoader().getResource(
                "de/hswt/hrm/misc/ui/PreferencesWizard/PreferencesWizardWindow"
                        + IConstants.XWT_EXTENSION_SUFFIX);
        try {
            container = (Composite) XWTForms.load(parent, url);
        }
        catch (Exception e) {
            LOG.error("An error occured: ", e);
        }

        nameText = (Text) XWT.findElementByName(container, "name");
        fileText = (Text) XWT.findElementByName(container, "fileName");

        if (this.preference.isPresent()) {
            updateFields();
        }
//        try {
//           TODO this.preferences = prefService.findAll();
//        }
//        catch (DatabaseException e) {
//            LOG.error("An error occured", e);
//        }

        FormUtil.initSectionColors((Section) XWT.findElementByName(container, "Mandatory"));
        addKeyListener(nameText);
        addKeyListener(fileText);
        setControl(container);
        setPageComplete(false);

    }

    private void addKeyListener(Text text) {
        text.addKeyListener(new KeyListener() {

            @Override
            public void keyReleased(KeyEvent e) {
                checkPageComplete();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
        });
    }

    private void updateFields() {
        ReportPreference e = preference.get();
        nameText.setText(e.getName());
        fileText.setText(e.getFileName());
    }

    private void checkPageComplete() {

        if (first) {
            first = false;
            setPageComplete(false);
            return;
        }

        setErrorMessage(null);

        if (fileText.getText().isEmpty()) {
            setErrorMessage("FileName must not be empty");
        }

        else if (nameText.getText().isEmpty()) {
            setErrorMessage("Name must not be empty...");
        }

        else if (isAlreadyPresent(nameText.getText())) {
            setErrorMessage("A ReportPreference with name " + nameText.getText() + " is already present");
        }

    }

    private boolean isAlreadyPresent(String text) {

        boolean present = false;

        if (text == null | text.isEmpty()) {
            present = true;
        }

        for (ReportPreference e : this.preferences) {
            if (e.getName().equals(text)) {
                present = true;
            }
        }

        return present;

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

    public String getName() {
        return nameText.getText();
    }

    public String getFileName() {
        return fileText.getText();
    }

}
