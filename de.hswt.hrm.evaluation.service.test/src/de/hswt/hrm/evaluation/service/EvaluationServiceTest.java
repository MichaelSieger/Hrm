package de.hswt.hrm.evaluation.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.evaluation.dao.core.IEvaluationDao;
import de.hswt.hrm.evaluation.model.Evaluation;
import de.hswt.hrm.test.database.AbstractDatabaseTest;

public class EvaluationServiceTest extends AbstractDatabaseTest {

    private void compareEvaluationFields(final Evaluation expected, final Evaluation actual) {
        assertThat("The name is not unique", expected.getName(), not(equalTo(actual.getName())));

    }

    // private EvaluationService createInjectedContactService() throws DatabaseException,
    // SQLException {
    // IEvaluationDao dao = null;
    //
    // IEclipseContext context = EclipseContextFactory.create();
    //
    // ContactService service = ContextInjectionFactory.make(ContactService.class, context);
    //
    // return service;
    // }

}
