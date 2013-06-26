package de.hswt.hrm.photo.service;

import java.util.Collection;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.photo.dao.core.IPhotoDao;
import de.hswt.hrm.photo.model.Photo;

@Creatable
public class PhotoService {

    private final static Logger LOG = LoggerFactory.getLogger(PhotoService.class);

    private final IPhotoDao photoDao;

    @Inject
    public PhotoService(IPhotoDao photoDao) {

        checkNotNull(photoDao, "PhotoDao must be injected properly");
        this.photoDao = photoDao;
        LOG.debug("Photo Dao injected successfully");
    }

    public Collection<Photo> findAll() throws DatabaseException {
        return photoDao.findAll();

    }

    public Photo findById(int id) throws ElementNotFoundException, DatabaseException {
        return photoDao.findById(id);
    }

    public Photo insert(final Photo photo) throws SaveException {
        return photoDao.insert(photo);
    }

    public void update(final Photo photo) throws ElementNotFoundException, SaveException {
        photoDao.update(photo);
    }

    public void refresh(Photo photo) throws DatabaseException {

        Photo fromDb = photoDao.findById(photo.getId());

        photo.setName(fromDb.getName());
        photo.setLabel(fromDb.getLabel());
        photo.setBlob(fromDb.getBlob());

    }

}
