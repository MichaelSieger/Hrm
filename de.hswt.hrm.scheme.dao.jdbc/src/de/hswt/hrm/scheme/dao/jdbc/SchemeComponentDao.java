package de.hswt.hrm.scheme.dao.jdbc;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hswt.hrm.common.database.DatabaseFactory;
import de.hswt.hrm.common.database.NamedParameterStatement;
import de.hswt.hrm.common.database.SqlQueryBuilder;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.component.dao.core.IComponentDao;
import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.scheme.dao.core.ISchemeComponentDao;
import de.hswt.hrm.scheme.dao.core.ISchemeDao;
import de.hswt.hrm.scheme.model.Direction;
import de.hswt.hrm.scheme.model.Scheme;
import de.hswt.hrm.scheme.model.SchemeComponent;

public class SchemeComponentDao implements ISchemeComponentDao {
    private final static Logger LOG = LoggerFactory.getLogger(SchemeComponentDao.class);
    private final ISchemeDao schemeDao;
    private final IComponentDao componentDao;
    
    @Inject
    public SchemeComponentDao(final ISchemeDao schemeDao, final IComponentDao componentDao) {
        checkNotNull(schemeDao, "SchemeDao not injected properly.");
        checkNotNull(componentDao, "ComponentDao not injected properly.");
        
        this.schemeDao = schemeDao;
        LOG.debug("SchemeDao injected into SchemeComponentDao.");
        this.componentDao = componentDao;
        LOG.debug("ComponentDao injected into SchemeComponentDao.");
    }

    @Override
    public Collection<SchemeComponent> findAllComponentByScheme(final Scheme scheme) 
            throws DatabaseException {
        
        checkArgument(scheme.getId() >= 0, "Scheme has no valid ID.");
        
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(TABLE_NAME, Fields.ID, Fields.SCHEME, Fields.COMPONENT, Fields.X_POS,
                Fields.Y_POS, Fields.DIRECTION);
        builder.where(Fields.SCHEME);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.SCHEME, scheme.getId());
                
                ResultSet result = stmt.executeQuery();

                Collection<SchemeComponent> schemeComponents = fromResultSet(result);
                DbUtils.closeQuietly(result);

                return schemeComponents;
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void insertComponent(Scheme scheme, SchemeComponent component) {
        component.setScheme(scheme);
        insertSchemeIfNecessary(component);
        insertComponentIfNecessary(component);
    }

    @Override
    public Collection<SchemeComponent> findAll() throws DatabaseException {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(TABLE_NAME, Fields.ID, Fields.SCHEME, Fields.COMPONENT, Fields.X_POS,
                Fields.Y_POS, Fields.DIRECTION);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                ResultSet result = stmt.executeQuery();

                Collection<SchemeComponent> schemeComponents = fromResultSet(result);
                DbUtils.closeQuietly(result);

                return schemeComponents;
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public SchemeComponent findById(int id) throws DatabaseException, ElementNotFoundException {
        checkArgument(id >= 0, "Id must not be negative.");

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(TABLE_NAME, Fields.ID, Fields.SCHEME, Fields.COMPONENT, Fields.X_POS,
                Fields.Y_POS, Fields.DIRECTION);
        builder.where(Fields.ID);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.ID, id);
                ResultSet result = stmt.executeQuery();

                Collection<SchemeComponent> schemeComponents = fromResultSet(result);
                DbUtils.closeQuietly(result);

                if (schemeComponents.size() < 1) {
                    throw new ElementNotFoundException();
                }
                else if (schemeComponents.size() > 1) {
                    throw new DatabaseException("ID '" + id + "' is not unique.");
                }

                return schemeComponents.iterator().next();
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public SchemeComponent insert(SchemeComponent schemeComponent) throws SaveException {
        if (schemeComponent.getId() >= 0) {
            throw new IllegalStateException("SchemeComponent has already a valid ID.");
        }
        
        insertSchemeIfNecessary(schemeComponent);
        insertComponentIfNecessary(schemeComponent);
        
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.insert(TABLE_NAME, Fields.COMPONENT, Fields.SCHEME, Fields.X_POS, Fields.Y_POS,
                Fields.DIRECTION);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {

                stmt.setParameter(Fields.COMPONENT, schemeComponent.getComponent());
                stmt.setParameter(Fields.X_POS, schemeComponent.getX());
                stmt.setParameter(Fields.Y_POS, schemeComponent.getY());
                stmt.setParameter(Fields.DIRECTION, schemeComponent.getDirection());
                
                if (schemeComponent.getScheme().isPresent() 
                        && schemeComponent.getScheme().get().getId() >= 0) {
                    
                    stmt.setParameter(Fields.SCHEME, schemeComponent.getScheme().get().getId());
                }
                else {
                    stmt.setParameter(Fields.SCHEME, null);
                }

                int affectedRows = stmt.executeUpdate();
                if (affectedRows != 1) {
                    throw new SaveException();
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);

                        SchemeComponent inserted = new SchemeComponent(id, schemeComponent.getX(),
                                schemeComponent.getY(), schemeComponent.getDirection(),
                                schemeComponent.getComponent());
                        inserted.setScheme(schemeComponent.getScheme().orNull());

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
    public void update(SchemeComponent schemeComponent) 
            throws ElementNotFoundException, SaveException {
        
        checkNotNull(schemeComponent, "SchemeComponent must not be null.");

        if (schemeComponent.getId() < 0) {
            throw new ElementNotFoundException("Element has no valid ID.");
        }
        
        insertSchemeIfNecessary(schemeComponent);
        insertComponentIfNecessary(schemeComponent);

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.update(TABLE_NAME, Fields.SCHEME, Fields.COMPONENT, Fields.X_POS,
                Fields.Y_POS, Fields.DIRECTION);
        builder.where(Fields.ID);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.COMPONENT, schemeComponent.getComponent());
                stmt.setParameter(Fields.X_POS, schemeComponent.getX());
                stmt.setParameter(Fields.Y_POS, schemeComponent.getY());
                stmt.setParameter(Fields.DIRECTION, schemeComponent.getDirection());
                
                if (schemeComponent.getScheme().isPresent() 
                        && schemeComponent.getScheme().get().getId() >= 0) {
                    stmt.setParameter(Fields.SCHEME, schemeComponent.getScheme().get().getId());
                }
                else {
                    stmt.setParameter(Fields.SCHEME, null);
                }

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
    
    private void insertSchemeIfNecessary(final SchemeComponent schemeComponent) {
        throw new RuntimeException();
    }
    
    private void insertComponentIfNecessary(final SchemeComponent schemeComponent) {
        throw new RuntimeException();
    }

    private Collection<SchemeComponent> fromResultSet(ResultSet rs) throws SQLException, ElementNotFoundException, DatabaseException {
        checkNotNull(rs, "Result must not be null.");
        Collection<SchemeComponent> schemeComponentList = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt(Fields.ID);
            int xPos = rs.getInt(Fields.X_POS);
            int yPos = rs.getInt(Fields.Y_POS);
            Direction dir = Direction.values()[rs.getInt(Fields.DIRECTION)];
            int componentId = rs.getInt(Fields.COMPONENT);
            Component component = null;
            if (componentId >= 0) {
                component = componentDao.findById(componentId);
            }
            
            int schemeId = rs.getInt(Fields.SCHEME);
            Scheme scheme = null;
            if (schemeId >= 0) {
                scheme = schemeDao.findById(schemeId);
            }
            
            SchemeComponent schemeComponent = new SchemeComponent(id, xPos, yPos, dir, null);
            schemeComponent.setComponent(component);
            schemeComponent.setScheme(scheme);

            schemeComponentList.add(schemeComponent);
        }

        return schemeComponentList;
    }

    private static final String TABLE_NAME = "Scheme_Component";

    private static class Fields {
        public static final String ID = "Scheme_Component_ID";
        public static final String SCHEME = "Scheme_Component_Scheme_FK";
        public static final String COMPONENT = "Scheme_Component_Component_FK";
        public static final String X_POS = "Scheme_Component_X_Position";
        public static final String Y_POS = "Scheme_Component_Y_Position";
        public static final String DIRECTION = "Scheme_Component_Direction";

    }

}
