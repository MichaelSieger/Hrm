package de.hswt.hrm.photo.dao.jdbc;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import de.hswt.hrm.common.database.DatabaseFactory;
import de.hswt.hrm.common.database.NamedParameterStatement;
import de.hswt.hrm.common.database.SqlQueryBuilder;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.common.exception.NotImplementedException;
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
        // TODO Auto-generated method stub
        return null;
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

    
    private Collection<Photo> fromResultSet(final ResultSet rs) {
        // Add component here
        throw new NotImplementedException();
    }

    private static final String TABLE_NAME = "Picture";

    private static final class Fields {
        private static final String ID = "Picture_ID";
        private static final String BLOB = "Picture_Blob";
        private static final String NAME = "Picture_Name";
        private static final String LABEL = "Picture_Label";
        }
}
