package de.hswt.hrm.scheme.dao.jdbc;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.scheme.dao.core.ISchemeDao;
import de.hswt.hrm.scheme.model.Scheme;

public class SchemeDao implements ISchemeDao {

    @Override
    public Scheme insert(Scheme scheme) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Scheme> findAll() throws DatabaseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Scheme findById(int id) throws DatabaseException, ElementNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(Scheme scheme) throws ElementNotFoundException, SaveException {
        // TODO Auto-generated method stub
        
    }

}
