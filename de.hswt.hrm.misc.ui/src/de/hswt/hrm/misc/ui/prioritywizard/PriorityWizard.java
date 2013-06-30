package de.hswt.hrm.misc.ui.prioritywizard;

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
import de.hswt.hrm.misc.comment.model.Comment;
import de.hswt.hrm.misc.priority.model.Priority;
import de.hswt.hrm.misc.priority.service.PriorityService;

public class PriorityWizard extends Wizard {

    private static final Logger LOG = LoggerFactory.getLogger(PriorityWizard.class);
    private static final I18n I18N = I18nFactory.getI18n(PriorityWizard.class);

    @Inject
    private PriorityService prioService;

    private PriorityWizardPageOne first;
    private Optional<Priority> priority;

    public PriorityWizard(IEclipseContext context, Optional<Priority> prio) {
        this.priority = prio;
        this.first = new PriorityWizardPageOne("First Page", prio);
        ContextInjectionFactory.inject(first, context);

        if (prio.isPresent()) {
            setWindowTitle(I18N.tr("Edit Priority"));
        }
        else {
            setWindowTitle("Add a new Priority");
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
        if (priority.isPresent()) {
            return editExistingComment();
        }
        return insertNewPriority();
    }

    private boolean insertNewPriority() {
    Priority p;
	try {
		p = new Priority(first.getName(), first.getDesc(), prioService.findAll().size()+1);
		this.priority = Optional.of(prioService.insert(p));
	} catch (DatabaseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

        return true;

    }

    private boolean editExistingComment() {
    	Priority e = this.priority.get();
        try {
            e = setValues(priority);
            prioService.update(e);
            priority = Optional.of(e);
        }
        catch (DatabaseException de) {
            LOG.error("An error occured: ", de);
            return false;
        }

        return true;
    }

    private Priority setValues(Optional<Priority> p) {

        Priority prio = null;

        if (p.isPresent()) {
            prio = p.get();
            prio.setName(first.getName());
            prio.setText(first.getDesc());
        }

        return prio;
    }

    public Optional<Priority> getPriority() {
        return priority;
    }

}
