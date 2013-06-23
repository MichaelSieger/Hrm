package de.hswt.hrm.misc.ui.PriorityWizard;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.Wizard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.misc.comment.model.Comment;
import de.hswt.hrm.misc.priority.model.Priority;

public class PriorityWizard extends Wizard {

    private static final Logger LOG = LoggerFactory.getLogger(PriorityWizard.class);

//    @Inject
//    private PriorityService prioService;

    private PriorityWizardPageOne first;
    private Optional<Priority> priority;

    public PriorityWizard(IEclipseContext context, Optional<Priority> prio) {
        this.priority = prio;
        this.first = new PriorityWizardPageOne("First Page", prio);
        ContextInjectionFactory.inject(first, context);

        if (prio.isPresent()) {
            setWindowTitle("Edit Priority: " + prio.get().getName());
        }
        else {
            setWindowTitle("Create new Priority");
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

//    TODO    Priority p = new Priority(first.getName(), first.getDesc(), prioService.findAll().size()+1);
//        try {
//            this.priority = Optional.of(prioService.insert(p));
//        }
//        catch (SaveException e2) {
//            LOG.error("An eror occured", e2);
//            return false;
//        }

        return true;

    }

    private boolean editExistingComment() {
    	Priority e = this.priority.get();

//        try {
//            e = setValues(priority);
//            prioService.update(e);
//            priority = Optional.of(e);
//        }
//        catch (DatabaseException de) {
//            LOG.error("An error occured: ", de);
//            return false;
//        }

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
