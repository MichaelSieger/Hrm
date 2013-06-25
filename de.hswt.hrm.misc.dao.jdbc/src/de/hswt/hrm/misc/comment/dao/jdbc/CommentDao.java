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

                Collection<Comment> evaluations = fromResultSet(result);
                DbUtils.closeQuietly(result);

                return evaluations;
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Comment findById(int id) throws DatabaseException, ElementNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Comment insert(Comment comment) throws SaveException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(Comment comment) throws ElementNotFoundException, SaveException {
        // TODO Auto-generated method stub

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
