package de.hswt.hrm.evaluation.ui.wizzard;

import java.util.Collection;

import javax.inject.Inject;

import org.eclipse.jface.wizard.Wizard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.evaluation.model.Evaluation;
import de.hswt.hrm.evaluation.service.EvaluationService;

public class EvaluationWizzard extends Wizard {

    private static final Logger LOG = LoggerFactory.getLogger(EvaluationWizzard.class);

    @Inject
    private EvaluationService evalService;

    private EvaluationWizzardPageOne first;
    private Optional<Evaluation> eval;

    public EvaluationWizzard(Optional<Evaluation> eval, Collection<Evaluation> evaluations) {
        this.eval = eval;
        this.first = new EvaluationWizzardPageOne("First Page", eval, evaluations);

        if (eval.isPresent()) {
            setWindowTitle("Edit Evaluation: " + eval.get().getName());
        }
        else {
            setWindowTitle("Create new Evaluation");
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
        // TODO Auto-generated method stub
        return false;
    }

    private boolean editExistingEvaluation() {
        Evaluation e = this.eval.get();

        try {
            e = evalService.findById(e.getId());
            e = setValues(eval);
            evalService.update(e);
            eval = Optional.of(e);
        }
        catch (DatabaseException de) {
            LOG.error("An error occured: ", de);
        }

        return true;
    }

    private Evaluation setValues(Optional<Evaluation> e) {
        Evaluation eval;

        if (e.isPresent()) {
            eval = e.get();

        }

        return null;
    }

}
