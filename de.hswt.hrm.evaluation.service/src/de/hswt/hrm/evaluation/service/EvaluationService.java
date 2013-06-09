package de.hswt.hrm.evaluation.service;

import java.util.Collection;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.evaluation.dao.core.IEvaluationDao;
import de.hswt.hrm.evaluation.model.Evaluation;

@Creatable
public class EvaluationService {

    private final static Logger LOG = LoggerFactory.getLogger(EvaluationService.class);

    private final IEvaluationDao evalDao;

    @Inject
    public EvaluationService(IEvaluationDao evalDao) {

        checkNotNull(evalDao, "EvaluationDao must be injected properly");
        this.evalDao = evalDao;
        LOG.debug("Evaluation Dao injected successfully");
    }

    public Collection<Evaluation> findAll() throws DatabaseException {
        return evalDao.findAll();

    }

    public Evaluation findById(int id) throws ElementNotFoundException, DatabaseException {
        return evalDao.findById(id);
    }

    public Evaluation insert(final Evaluation evaluation) throws SaveException {
        return evalDao.insert(evaluation);
    }

    public void update(final Evaluation evaluation) throws ElementNotFoundException, SaveException {
        evalDao.insert(evaluation);
    }

    public void refresh(Evaluation selectedPlace) throws DatabaseException {

    }

}
