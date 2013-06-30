package de.hswt.hrm.misc.ui.preferenceswizard;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.Wizard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.i18n.I18n;
import de.hswt.hrm.i18n.I18nFactory;
import de.hswt.hrm.inspection.model.Layout;
import de.hswt.hrm.inspection.service.LayoutService;

public class PreferencesWizard extends Wizard {

    private static final Logger LOG = LoggerFactory.getLogger(PreferencesWizard.class);
    private static final I18n I18N = I18nFactory.getI18n(PreferencesWizard.class);
    
    @Inject
    private LayoutService prefService;

    private PreferencesWizardPageOne first;
    private Optional<Layout> preference;

    public PreferencesWizard(IEclipseContext context, Optional<Layout> preference) {
        this.preference = preference;
        this.first = new PreferencesWizardPageOne("First Page", preference);
        ContextInjectionFactory.inject(first, context);

        if (preference.isPresent()) {
            setWindowTitle(I18N.tr("Edit Preference"));
        }
        else {
            setWindowTitle(I18N.tr("Add a new Preference"));
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

        Layout e = new Layout(first.getName(), first.getFileName());
        	try {
        		this.preference = Optional.of(prefService.insert(e));
        	}
        	catch (SaveException e2) {
        		LOG.error("An erroor occured", e2);
        		return false;
        	}

        return true;

    }

    private boolean editExistingPreference() {
        Layout e = this.preference.get();

        try {
            e = setValues(preference);
            prefService.update(e);
            preference = Optional.of(e);
        }
        catch (DatabaseException de) {
            LOG.error("An error occured: ", de);
            return false;
        }

        return true;
    }

    private Layout setValues(Optional<Layout> e) {

        Layout preference = null;

        if (e.isPresent()) {
            preference = e.get();
            preference.setName(first.getName());
            preference.setFileName(first.getFileName());
        }

        return preference;
    }

    public Optional<Layout> getPreference() {
        return preference;
    }

}
