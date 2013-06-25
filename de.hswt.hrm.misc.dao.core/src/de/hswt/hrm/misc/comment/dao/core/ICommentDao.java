package de.hswt.hrm.misc.comment.dao.core;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.misc.comment.model.Comment;

/**
 * Defines all the public methods to interact with the storage system for comments.
 */
public interface ICommentDao {

    /**
     * @return All comments from storage.
     */
    Collection<Comment> findAll() throws DatabaseException;

    /**
     * @param id
     *            of the comment.
     * @return Comment with the given id.
     * @throws ElementNotFoundException
     *             If the given id is not present in the database.
     */
    Comment findById(int id) throws DatabaseException, ElementNotFoundException;

    /**
     * Add a new comment to storage.
     * 
     * @param Comment
     *            that should be stored.
     * @return Newly generated comment(also holding the correct id).
     * @throws SaveException
     *             If the comment could not be inserted.
     */
    Comment insert(Comment comment) throws SaveException;

    /**
     * Update an existing comment in storage.
     * 
     * @param Comment
     *            that should be updated.
     * @throws ElementNotFoundException
     *             If the given comment is not present in the database.
     * @throws SaveException
     *             If the comment could not be updated.
     */
    void update(Comment comment) throws ElementNotFoundException, SaveException;
}
