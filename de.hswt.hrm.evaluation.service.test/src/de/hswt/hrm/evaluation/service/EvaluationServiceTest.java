package de.hswt.hrm.evaluation.service;

import org.junit.Test;

import de.hswt.hrm.evaluation.model.Evaluation;
import de.hswt.hrm.test.database.AbstractDatabaseTest;

public class EvaluationServiceTest extends AbstractDatabaseTest {

    @Test
    private boolean compareEvaluationFields(final Evaluation expected, final Evaluation actual) {
        return true;

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
