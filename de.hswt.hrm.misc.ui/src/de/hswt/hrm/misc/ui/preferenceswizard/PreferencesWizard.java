package de.hswt.hrm.misc.ui.preferenceswizard;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.Wizard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.misc.reportPreferences.model.ReportPreference;

public class PreferencesWizard extends Wizard {

    private static final Logger LOG = LoggerFactory.getLogger(PreferencesWizard.class);

//    @Inject
//    private ReportPreferenceService prefService;

    private PreferencesWizardPageOne first;
    private Optional<ReportPreference> preference;

    public PreferencesWizard(IEclipseContext context, Optional<ReportPreference> preference) {
        this.preference = preference;
        this.first = new PreferencesWizardPageOne("First Page", preference);
        ContextInjectionFactory.inject(first, context);

        if (preference.isPresent()) {
            setWindowTitle("Edit Preference: " + preference.get().getName());
        }
        else {
            setWindowTitle("Create new Preference");
        }
    }

    public void addPages() {
        addPage(first);
    }

    public boolean canFinish() {
        return first.isPageComplete();
    }

    @Override
    public boolean performFinish() {
        if (preference.isPresent()) {
            return editExistingPreference();
        }
        return insertNewPreference();
    }

    private boolean insertNewPreference() {

        ReportPreference e = new ReportPreference(first.getName(), first.getFileName());
//   TODO     try {
//            this.preference = Optional.of(prefService.insert(e));
//        }
//        catch (SaveException e2) {
//            LOG.error("An erroor occured", e2);
//            return false;
//        }

        return true;

    }

    private boolean editExistingPreference() {
        ReportPreference e = this.preference.get();

//  TODO      try {
//            e = setValues(preference);
//            prefService.update(e);
//            preference = Optional.of(e);
//        }
//        catch (DatabaseException de) {
//            LOG.error("An error occured: ", de);
//            return false;
//        }

        return true;
    }

    private ReportPreference setValues(Optional<ReportPreference> e) {

        ReportPreference preference = null;

        if (e.isPresent()) {
            preference = e.get();
            preference.setName(first.getName());
            preference.setFileName(first.getFileName());
        }

        return preference;
    }

    public Optional<ReportPreference> getPreference() {
        return preference;
    }

}
