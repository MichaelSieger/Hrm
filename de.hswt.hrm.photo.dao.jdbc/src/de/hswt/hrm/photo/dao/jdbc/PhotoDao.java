package de.hswt.hrm.photo.dao.jdbc;

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
import de.hswt.hrm.photo.dao.core.IPhotoDao;
import de.hswt.hrm.photo.model.Photo;

public class PhotoDao implements IPhotoDao {

    private final IPhotoDao photoDao;

    // TODO: add LOG messages
    public PhotoDao(final IPhotoDao photoDao) {
        checkNotNull(photoDao, "ComponentDao not properly injected to PhotoDao.");

        this.photoDao = photoDao;
    }

    @Override
    public Collection<Photo> findAll() throws DatabaseException {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(TABLE_NAME, Fields.ID, Fields.BLOB, Fields.NAME, Fields.LABEL);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                ResultSet result = stmt.executeQuery();

                Collection<Photo> photos = fromResultSet(result);
                DbUtils.closeQuietly(result);

                return photos;
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Photo findById(int id) throws DatabaseException, ElementNotFoundException {
        return null;
    }

    @Override
    public Photo insert(Photo photo) throws SaveException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(Photo photo) throws ElementNotFoundException, SaveException {
        // TODO Auto-generated method stub

    }

    private Collection<Photo> fromResultSet(final ResultSet rs) throws SQLException {
        checkNotNull(rs, "Result must not be null.");
        Collection<Photo> photoList = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(Fields.ID);
            String blob = rs.getString(Fields.BLOB);
            String name = rs.getString(Fields.NAME);
            String label = rs.getString(Fields.LABEL);

            Photo photo = new Photo(id, blob, name, label);

            photoList.add(photo);
        }

        return photoList;
    }

    private static final String TABLE_NAME = "Picture";

    private static final class Fields {
        private static final String ID = "Picture_ID";
        private static final String BLOB = "Picture_Blob";
        private static final String NAME = "Picture_Name";
        private static final String LABEL = "Picture_Label";
    }
}
