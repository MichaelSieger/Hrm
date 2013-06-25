package de.hswt.hrm.misc.comment.dao.jdbc;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.dbutils.DbUtils;

import de.hswt.hrm.common.database.DatabaseFactory;
import de.hswt.hrm.common.database.NamedParameterStatement;
import de.hswt.hrm.common.database.SqlQueryBuilder;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.misc.comment.dao.core.ICommentDao;
import de.hswt.hrm.misc.comment.model.Comment;

public class CommentDao implements ICommentDao {

    @Override
    public Collection<Comment> findAll() throws DatabaseException {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(TABLE_NAME, Fields.ID, Fields.NAME, Fields.TEXT);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                ResultSet result = stmt.executeQuery();

                Collection<Comment> comments = fromResultSet(result);
                DbUtils.closeQuietly(result);

                return comments;
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Comment findById(int id) throws DatabaseException, ElementNotFoundException {

        checkArgument(id >= 0, "Id must not be negative.");

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(TABLE_NAME, Fields.ID, Fields.NAME, Fields.TEXT);
        builder.where(Fields.ID);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.ID, id);
                ResultSet result = stmt.executeQuery();

                Collection<Comment> comments = fromResultSet(result);
                DbUtils.closeQuietly(result);

                if (comments.size() < 1) {
                    throw new ElementNotFoundException();
                }
                else if (comments.size() > 1) {
                    throw new DatabaseException("ID '" + id + "' is not unique.");
                }

                return comments.iterator().next();
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Comment insert(Comment comment) throws SaveException {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.insert(TABLE_NAME, Fields.NAME, Fields.TEXT);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.NAME, comment.getName());
                stmt.setParameter(Fields.TEXT, comment.getText());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows != 1) {
                    throw new SaveException();
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);

                        // Create new Comment with id
                        Comment inserted = new Comment(id, comment.getName(), comment.getText());
                        return inserted;
                    }
                    else {
                        throw new SaveException("Could not retrieve generated ID.");
                    }
                }
            }

        }
        catch (SQLException | DatabaseException e) {
            throw new SaveException(e);
        }
    }

    @Override
    public void update(Comment comment) throws ElementNotFoundException, SaveException {
        checkNotNull(comment, "Comment must not be null.");

        if (comment.getId() < 0) {
            throw new ElementNotFoundException("Element has no valid ID.");
        }

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.update(TABLE_NAME, Fields.NAME, Fields.TEXT);
        builder.where(Fields.ID);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.ID, comment.getId());
                stmt.setParameter(Fields.NAME, comment.getName());
                stmt.setParameter(Fields.TEXT, comment.getText());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows != 1) {
                    throw new SaveException();
                }
            }
        }
        catch (SQLException | DatabaseException e) {
            throw new SaveException(e);
        }
    }

    private Collection<Comment> fromResultSet(ResultSet rs) throws SQLException {
        checkNotNull(rs, "Result must not be null.");
        Collection<Comment> commentList = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(Fields.ID);
            String commentName = rs.getString(Fields.NAME);
            String commentText = rs.getString(Fields.TEXT);

            Comment comm = new Comment(id, commentName, commentText);

            commentList.add(comm);
        }

        return commentList;
    }

    private static final String TABLE_NAME = "Note";

    private static class Fields {
        public static final String ID = "Note_ID";
        public static final String NAME = "Note_Name";
        public static final String TEXT = "Note_Text";
    }

}
