package de.hswt.hrm.misc.dao.context;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.misc.comment.dao.core.ICommentDao;
import de.hswt.hrm.misc.comment.dao.jdbc.CommentDao;

public class JdbcCommentDaoContextFunction extends ContextFunction {

    private final static Logger LOG = LoggerFactory.getLogger(JdbcCommentDaoContextFunction.class);

    @Override
    public Object compute(IEclipseContext context) {

        ICommentDao commentDao = ContextInjectionFactory.make(CommentDao.class, context);
        context.set(ICommentDao.class, commentDao);

        LOG.debug("Made CommentDao available in Eclipse context.");
        return commentDao;

    }

}
