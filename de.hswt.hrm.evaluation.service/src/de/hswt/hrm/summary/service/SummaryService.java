package de.hswt.hrm.summary.service;

import java.util.Collection;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.summary.dao.core.ISummaryDao;
import de.hswt.hrm.summary.model.Summary;

@Creatable
public class SummaryService {

    private final static Logger LOG = LoggerFactory.getLogger(SummaryService.class);

    private final ISummaryDao evalDao;

    @Inject
    public SummaryService(ISummaryDao evalDao) {

        checkNotNull(evalDao, "EvaluationDao must be injected properly");
        this.evalDao = evalDao;
        LOG.debug("Evaluation Dao injected successfully");
    }

    public Collection<Summary> findAll() throws DatabaseException {
        return evalDao.findAll();

    }

    public Summary findById(int id) throws ElementNotFoundException, DatabaseException {
        return evalDao.findById(id);
    }

    public Summary insert(final Summary evaluation) throws SaveException {
        return evalDao.insert(evaluation);
    }

    public void update(final Summary evaluation) throws ElementNotFoundException, SaveException {
        evalDao.update(evaluation);
    }

    public void refresh(Summary eval) throws DatabaseException {

        Summary fromDb = evalDao.findById(eval.getId());

        eval.setName(fromDb.getName());
        eval.setText(fromDb.getText());

    }

}
