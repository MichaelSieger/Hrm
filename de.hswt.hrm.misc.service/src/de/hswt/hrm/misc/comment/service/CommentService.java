package de.hswt.hrm.misc.comment.service;

import java.util.Collection;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.misc.comment.dao.core.ICommentDao;
import de.hswt.hrm.misc.comment.model.Comment;

@Creatable
public class CommentService {

    private final static Logger LOG = LoggerFactory.getLogger(CommentService.class);

    private final ICommentDao commentDao;

    @Inject
    public CommentService(ICommentDao commentDao) {

        checkNotNull(commentDao, "CommentDao must be injected properly");
        this.commentDao = commentDao;
        LOG.debug("Comment Dao injected successfully");
    }

    public Collection<Comment> findAll() throws DatabaseException {
        return commentDao.findAll();

    }

    public Comment findById(int id) throws ElementNotFoundException, DatabaseException {
        return commentDao.findById(id);
    }

    public Comment insert(final Comment comment) throws SaveException {
        return commentDao.insert(comment);
    }

    public void update(final Comment comment) throws ElementNotFoundException, SaveException {
        commentDao.update(comment);
    }

    public void refresh(Comment comment) throws DatabaseException {

        Comment fromDb = commentDao.findById(comment.getId());

        comment.setName(fromDb.getName());
        comment.setText(fromDb.getText());

    }

}
