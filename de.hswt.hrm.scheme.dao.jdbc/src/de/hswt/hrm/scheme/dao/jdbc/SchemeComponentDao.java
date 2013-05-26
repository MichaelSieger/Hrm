package de.hswt.hrm.scheme.dao.jdbc;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.scheme.dao.core.ISchemeComponentDao;
import de.hswt.hrm.scheme.model.Scheme;
import de.hswt.hrm.scheme.model.SchemeComponent;

public class SchemeComponentDao implements ISchemeComponentDao {

    @Override
    public Collection<Component> findAllComponentByScheme(Scheme scheme) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void insertComponent(Scheme scheme, SchemeComponent component) {
        // TODO Auto-generated method stub

    }

    @Override
    public Collection<SchemeComponent> findAll() throws DatabaseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SchemeComponent findById(int id) throws DatabaseException, ElementNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SchemeComponent insert(SchemeComponent schemeComponent) throws SaveException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(SchemeComponent schemeComponent) throws ElementNotFoundException,
            SaveException {
        // TODO Auto-generated method stub
        
    }

}
