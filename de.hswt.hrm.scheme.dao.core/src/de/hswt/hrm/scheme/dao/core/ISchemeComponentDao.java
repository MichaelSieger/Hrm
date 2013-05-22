package de.hswt.hrm.scheme.dao.core;

import java.util.Collection;

import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.scheme.model.Scheme;
import de.hswt.hrm.scheme.model.SchemeComponent;

public interface ISchemeComponentDao {
    
    Collection<Component> findAllComponentByScheme(final Scheme scheme);
    
    void insertComponent(final Scheme scheme, final SchemeComponent component);
    
}
