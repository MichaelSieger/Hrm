package de.hswt.hrm.summary.ui.wizzard;

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
import de.hswt.hrm.summary.model.Summary;
import de.hswt.hrm.summary.service.SummaryService;

public class SummaryWizzard extends Wizard {

    private static final Logger LOG = LoggerFactory.getLogger(SummaryWizzard.class);
    private static final I18n I18N = I18nFactory.getI18n(SummaryWizzard.class);

    @Inject
    private SummaryService evalService;

    private SummaryWizzardPageOne first;
    private Optional<Summary> eval;

    public SummaryWizzard(IEclipseContext context, Optional<Summary> eval) {
        this.eval = eval;
        this.first = new SummaryWizzardPageOne("First Page", eval);
        ContextInjectionFactory.inject(first, context);

        if (eval.isPresent()) {
            setWindowTitle(I18N.tr("Edit Summary"));
        }
        else {
            setWindowTitle(I18N.tr("Add a new Summary"));
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
        if (eval.isPresent()) {
            return editExistingEvaluation();
        }
        return insertNewEvaluation();
    }

    private boolean insertNewEvaluation() {

        Summary e = new Summary(first.getName(), first.getDesc());
        try {
            this.eval = Optional.of(evalService.insert(e));
        }
        catch (SaveException e2) {
            LOG.error("An erroor occured", e2);
            return false;
        }

        return true;

    }

    private boolean editExistingEvaluation() {
        Summary e = this.eval.get();

        try {
            e = setValues(eval);
            evalService.update(e);
            eval = Optional.of(e);
        }
        catch (DatabaseException de) {
            LOG.error("An error occured: ", de);
            return false;
        }

        return true;
    }

    private Summary setValues(Optional<Summary> e) {

        Summary eval = null;

        if (e.isPresent()) {
            eval = e.get();
            eval.setName(first.getName());
            eval.setText(first.getDesc());
        }

        return eval;
    }

    public Optional<Summary> getEval() {
        return eval;
    }

}
