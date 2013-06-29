package de.hswt.hrm.scheme.dao.jdbc;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
import de.hswt.hrm.component.model.Attribute;
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
        checkNotNull(schemeComponent, "SchemeComponent must not be null.");
    	if (schemeComponent.getId() >= 0) {
    		LOG.info(String.format("SchemeComponent '%d' copied.", schemeComponent.getId()));
        }
        
    	checkState(schemeComponent.getScheme().getId() >= 0, "Scheme must have a valid ID.");
    	checkState(schemeComponent.getComponent().getId() >= 0, "Component must have a valid ID.");
        
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.insert(TABLE_NAME, Fields.COMPONENT, Fields.SCHEME, Fields.X_POS, Fields.Y_POS,
                Fields.DIRECTION);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {

                stmt.setParameter(Fields.COMPONENT, schemeComponent.getComponent().getId());
                stmt.setParameter(Fields.X_POS, schemeComponent.getX());
                stmt.setParameter(Fields.Y_POS, schemeComponent.getY());
                stmt.setParameter(Fields.DIRECTION, schemeComponent.getDirection().ordinal());
                stmt.setParameter(Fields.SCHEME, schemeComponent.getScheme().getId());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows != 1) {
                    throw new SaveException();
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);

                        SchemeComponent inserted = new SchemeComponent(
                        		id,
                        		schemeComponent.getScheme(),
                        		schemeComponent.getX(),
                                schemeComponent.getY(), schemeComponent.getDirection(),
                                schemeComponent.getComponent());

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
        checkState(schemeComponent.getId() >= 0, "SchemeComponent has no valid ID.");
    	checkState(schemeComponent.getScheme().getId() >= 0, "Scheme must have a valid ID.");
    	checkState(schemeComponent.getComponent().getId() >= 0, "Component must have a valid ID.");

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.update(TABLE_NAME, Fields.SCHEME, Fields.COMPONENT, Fields.X_POS,
                Fields.Y_POS, Fields.DIRECTION);
        builder.where(Fields.ID);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.COMPONENT, schemeComponent.getComponent().getId());
                stmt.setParameter(Fields.X_POS, schemeComponent.getX());
                stmt.setParameter(Fields.Y_POS, schemeComponent.getY());
                stmt.setParameter(Fields.DIRECTION, schemeComponent.getDirection().ordinal());
                stmt.setParameter(Fields.SCHEME, schemeComponent.getScheme().getId());

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
    
    @Override
    public void delete(SchemeComponent component) 
    		throws ElementNotFoundException, DatabaseException {
    	
    	checkNotNull(component, "SchemeComponent must not be null.");
        checkState(component.getId() >= 0, "SchemeComponent has no valid ID.");
        
        StringBuilder builder = new StringBuilder();
        builder.append("DELETE FROM ").append(TABLE_NAME);
        builder.append(" WHERE ").append(Fields.ID);
        builder.append(" = :").append(Fields.ID).append(";");
        
        String query = builder.toString();
        
        try (Connection con = DatabaseFactory.getConnection()) {
        	con.setAutoCommit(false);
        	
        	try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
        		stmt.setParameter(Fields.ID, component.getId());
        		
        		int affected = stmt.executeUpdate();
        		
        		if (affected > 1) {
        			con.rollback();
        			throw new DatabaseException("Query would accidently delete more than one row.");
        		}
        		else if (affected < 1) {
        			con.rollback();
        			throw new ElementNotFoundException();
        		}
        		
        		con.commit();
        	}
        }
        catch (SQLException e) {
        	throw new DatabaseException("Unknown error.", e);
        }
    }
    
    @Override
    public void delete(SchemeComponent component, Attribute attribute) throws DatabaseException {
    	checkNotNull(component, "Component must not be null.");
    	checkNotNull(attribute, "Attribute must not be null.");
    	checkState(component.getId() >= 0, "Component must have a valid ID.");
    	checkState(attribute.getId() >= 0, "Attribute must have a valid ID.");
    	checkState(component.getId() == attribute.getComponent().getId(), 
    			"Scheme Component does not match attributes component.");
    	
    	StringBuilder builder = new StringBuilder();
    	builder.append("DELETE FROM ").append(ATTR_CROSS_TABLE_NAME);
    	builder.append(" WHERE ");
    	builder.append(AttrCrossFields.FK_ATTRIBUTE);
    	builder.append(" = :").append(AttrCrossFields.FK_ATTRIBUTE);
    	builder.append(" AND ").append(AttrCrossFields.FK_COMPONENT);
    	builder.append(" = :").append(AttrCrossFields.FK_COMPONENT);
    	builder.append(";");
    	
    	String query = builder.toString();
    	
    	try (Connection con = DatabaseFactory.getConnection()) {
    		con.setAutoCommit(false);
    		
    		try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
    			stmt.setParameter(AttrCrossFields.FK_ATTRIBUTE, attribute.getId());
    			stmt.setParameter(AttrCrossFields.FK_COMPONENT, component.getId());
    			
    			int affected = stmt.executeUpdate();
    			
    			if (affected > 1) {
    				con.rollback();
    				throw new DatabaseException("Query would accidently affect more than one row.");
    			}
    			else if (affected < 1) {
    				con.rollback();
    				throw new DatabaseException("Query didn't affect any rows.");
    			}
    			
    			con.commit();
    		}
    	}
    	catch (SQLException e) {
    		throw new DatabaseException("Unexpected error.", e);
    	}
    }
    
    @Override
    public Map<Attribute, String> findAttributesOfSchemeComponent(SchemeComponent schemeComponent)
    		throws DatabaseException {
    	
    	checkNotNull(schemeComponent, "SchemeComponent must not be null.");
    	checkArgument(schemeComponent.getId() >= 0, "SchemeComponent must have a valid ID.");
    	
    	SqlQueryBuilder builder = new SqlQueryBuilder();
    	builder.select(ATTR_CROSS_TABLE_NAME, 
    			AttrCrossFields.FK_COMPONENT, 
    			AttrCrossFields.FK_ATTRIBUTE,
    			AttrCrossFields.VALUE);
    	builder.where(AttrCrossFields.FK_COMPONENT);
    	
    	String query = builder.toString();
    	
    	try (Connection con = DatabaseFactory.getConnection()) {
    		try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
    			stmt.setParameter(AttrCrossFields.FK_COMPONENT, schemeComponent.getId());
    			
    			ResultSet rs = stmt.executeQuery();

    			Map<Attribute, String> attrValues = new HashMap<>();
    			while (rs.next()) {
    				int attributeId = rs.getInt(AttrCrossFields.FK_ATTRIBUTE);
    				String value = rs.getString(AttrCrossFields.VALUE);
    				
    				Attribute attr = null;
    				try {
    					attr = componentDao.findAttributeById(attributeId);
    				}
    				catch (ElementNotFoundException | IllegalArgumentException e) {
    					throw new DatabaseException("Invalid attribute ID resolved.");
    				}
    				
    				attrValues.put(attr, value);
    			}
    			
    			DbUtils.closeQuietly(rs);
    			return Collections.unmodifiableMap(attrValues);
    		}
    	}
    	catch (SQLException e) {
    		throw new DatabaseException("Unknown error.", e);
    	}
    }
    
    @Override
    public void reassignAttributeValue(Attribute attribute, SchemeComponent sourceComp,
    		SchemeComponent targetComp) throws SaveException, DatabaseException {
    	
    	checkState(attribute.getId() >= 0, "Attribute must have a valid ID.");
    	checkState(sourceComp.getId() >= 0, "Source SchemeComponent must have a valid ID.");
    	checkState(targetComp.getId() >= 0, "Target SchemeComponent must have a valid ID.");
    	checkState(attribute.getComponent().getId() == targetComp.getComponent().getId(), 
    			"Target SchemeComponent does not match the attributes component.");
    	
    	final String sourceParam = "sourceSchemeComponent";
    	final String targetParam = "targetSchemeComponent";
    	
    	StringBuilder builder = new StringBuilder();
    	builder.append("UPDATE ").append(ATTR_CROSS_TABLE_NAME);
    	builder.append(" SET ").append(AttrCrossFields.FK_COMPONENT);
    	builder.append(" = :").append(targetParam);
    	builder.append(" WHERE ");
    	builder.append(AttrCrossFields.FK_ATTRIBUTE);
    	builder.append(" = :").append(AttrCrossFields.FK_ATTRIBUTE);
    	builder.append(" AND ");
    	builder.append(AttrCrossFields.FK_COMPONENT);
    	builder.append(" = :").append(sourceParam).append(";");

    	String query = builder.toString();
    	
    	try (Connection con = DatabaseFactory.getConnection()) {
    		con.setAutoCommit(false);
    		
    		try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
    			stmt.setParameter(AttrCrossFields.FK_ATTRIBUTE, attribute.getId());
    			stmt.setParameter(sourceParam, sourceComp.getId());
    			stmt.setParameter(targetParam, targetComp.getId());
    			
    			int affected = stmt.executeUpdate();
    			if (affected > 1) {
    				con.rollback();
    				throw new DatabaseException("Statement would accidently affect more than one row.");
    			}
    			else if (affected < 1) {
    				throw new SaveException("No rows affected.");
    			}
    			
    			con.commit();
    		}
    	}
    	catch (SQLException e) {
    		throw new DatabaseException("Unexpected error.", e);
    	}
    }
    
    @Override
    public void addAttributeValue(SchemeComponent comp, Attribute attribute, String value)
    		throws DatabaseException {
    	
    	checkArgument(comp.getId() >= 0, "SchemeComponent must have a valid ID.");
    	checkArgument(attribute.getId() >= 0, "Attribute must have a valid ID.");
    	
    	// Check if attribute belongs to the component
    	if (attribute.getComponent().getId() != comp.getComponent().getId()) {
    		throw new IllegalStateException("The given attribute does not belong to the component.");
    	}
    	
    	SqlQueryBuilder builder = new SqlQueryBuilder();
    	builder.insert(ATTR_CROSS_TABLE_NAME, 
    			AttrCrossFields.FK_COMPONENT, 
    			AttrCrossFields.FK_ATTRIBUTE, 
    			AttrCrossFields.VALUE);
    	
    	String query = builder.toString();
    	
    	try (Connection con = DatabaseFactory.getConnection()) {
    		try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
    			stmt.setParameter(AttrCrossFields.FK_COMPONENT, comp.getId());
    			stmt.setParameter(AttrCrossFields.FK_ATTRIBUTE, attribute.getId());
    			stmt.setParameter(AttrCrossFields.VALUE, value);
    			
    			int affected = stmt.executeUpdate();
    			if (affected != 1) {
    				throw new SaveException();
    			}
    		}
    	}
    	catch (SQLException e) {
    		throw new DatabaseException("Unknown error.", e);
    	}
    }
    
    @Override
    public void updateAttributeValue(SchemeComponent comp, Attribute attribute, String value) 
    		throws DatabaseException {
    	
    	checkState(attribute.getId() >= 0, "Attribute must have a valid ID.");
    	checkState(comp.getId() >= 0, "SchemeComponent must have a valid ID.");
    	checkState(attribute.getComponent().getId() == comp.getComponent().getId(), 
    			"SchemeComponent does not match the attributes component.");
    	
    	StringBuilder builder = new StringBuilder();
    	builder.append("UPDATE ").append(ATTR_CROSS_TABLE_NAME);
    	builder.append(" SET ").append(AttrCrossFields.VALUE);
    	builder.append(" = :").append(AttrCrossFields.VALUE);
    	builder.append(" WHERE ");
    	builder.append(AttrCrossFields.FK_ATTRIBUTE);
    	builder.append(" = :").append(AttrCrossFields.FK_ATTRIBUTE);
    	builder.append(" AND ");
    	builder.append(AttrCrossFields.FK_COMPONENT);
    	builder.append(" = :").append(AttrCrossFields.FK_COMPONENT).append(";");

    	String query = builder.toString();
    	
    	try (Connection con = DatabaseFactory.getConnection()) {
    		con.setAutoCommit(false);
    		
    		try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
    			stmt.setParameter(AttrCrossFields.FK_ATTRIBUTE, attribute.getId());
    			stmt.setParameter(AttrCrossFields.FK_COMPONENT, comp.getId());
    			stmt.setParameter(AttrCrossFields.VALUE, value);
    			
    			int affected = stmt.executeUpdate();
    			if (affected > 1) {
    				con.rollback();
    				throw new DatabaseException("Statement would accidently affect more than one row.");
    			}
    			else if (affected < 1) {
    				throw new SaveException("No rows affected.");
    			}
    			
    			con.commit();
    		}
    	}
    	catch (SQLException e) {
    		throw new DatabaseException("Unexpected error.", e);
    	}
    }
    
    @Override
    public boolean hasAttributeValue(SchemeComponent component, Attribute attribute) 
    		throws DatabaseException {
    	
    	checkState(attribute.getId() >= 0, "Attribute must have a valid ID.");
    	checkState(component.getId() >= 0, "SchemeComponent must have a valid ID.");
    	
    	StringBuilder builder = new StringBuilder();
    	builder.append("SELECT ").append(AttrCrossFields.VALUE);
    	builder.append(" FROM ").append(ATTR_CROSS_TABLE_NAME);
    	builder.append(" WHERE ");
    	builder.append(AttrCrossFields.FK_ATTRIBUTE);
    	builder.append(" = :").append(AttrCrossFields.FK_ATTRIBUTE);
    	builder.append(" AND ").append(AttrCrossFields.FK_COMPONENT);
    	builder.append(" = :").append(AttrCrossFields.FK_COMPONENT);
    	builder.append(";");
    	
    	String query = builder.toString();
    	
    	try (Connection con = DatabaseFactory.getConnection()) {
    		try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
    			stmt.setParameter(AttrCrossFields.FK_ATTRIBUTE, attribute.getId());
    			stmt.setParameter(AttrCrossFields.FK_COMPONENT, component.getId());
    			
    			ResultSet rs = stmt.executeQuery();
    			boolean hasValue = rs.next();
    			
    			DbUtils.closeQuietly(rs);
    			
    			return hasValue;
    		}
    	}
    	catch (SQLException e) {
    		throw new DatabaseException("Unexpected error.", e);
    	}
    }
    
    private Collection<SchemeComponent> fromResultSet(ResultSet rs) throws SQLException, ElementNotFoundException, DatabaseException {
        checkNotNull(rs, "Result must not be null.");
        
        LOG.debug("Parsing resultset from scheme component.");
        
        Collection<SchemeComponent> schemeComponentList = new ArrayList<>();
        Scheme scheme = null;
        while (rs.next()) {
            int id = rs.getInt(Fields.ID);
            int xPos = rs.getInt(Fields.X_POS);
            int yPos = rs.getInt(Fields.Y_POS);
            Direction dir = Direction.values()[rs.getInt(Fields.DIRECTION)];
            int componentId = rs.getInt(Fields.COMPONENT);
            checkState(componentId >= 0, "Invalid component ID retrieved from database.");
            Component component = componentDao.findById(componentId);
            
            int schemeId = rs.getInt(Fields.SCHEME);
            if (scheme == null || scheme.getId() != schemeId) {
        		scheme = schemeDao.findById(schemeId);
            }
            
            SchemeComponent schemeComponent = new SchemeComponent(id, scheme, xPos, yPos, dir, component);
            schemeComponent.setComponent(component);
            
            schemeComponentList.add(schemeComponent);
            
            LOG.debug("SchemeComponent parsed.");
        }

        LOG.debug("Finished parsing resultset from scheme component.");
        
        return schemeComponentList;
    }

    private static final String TABLE_NAME = "Scheme_Component";
    private static final String ATTR_CROSS_TABLE_NAME = "Scheme_Component_Attribute";

    private static class Fields {
        public static final String ID = "Scheme_Component_ID";
        public static final String SCHEME = "Scheme_Component_Scheme_FK";
        public static final String COMPONENT = "Scheme_Component_Component_FK";
        public static final String X_POS = "Scheme_Component_X_Position";
        public static final String Y_POS = "Scheme_Component_Y_Position";
        public static final String DIRECTION = "Scheme_Component_Direction";

    }
    
    private static class AttrCrossFields {
    	public static final String FK_COMPONENT = "Scheme_Component_Attribute_Component_FK";
    	public static final String FK_ATTRIBUTE = "Scheme_Component_Attribute_Attribute_FK";
    	public static final String VALUE = "Scheme_Component_Attribute_Value";
    }

}
