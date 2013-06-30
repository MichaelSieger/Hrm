package de.hswt.hrm.component.dao.jdbc;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.DatabaseFactory;
import de.hswt.hrm.common.database.NamedParameterStatement;
import de.hswt.hrm.common.database.SqlQueryBuilder;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.component.dao.core.ICategoryDao;
import de.hswt.hrm.component.dao.core.IComponentDao;
import de.hswt.hrm.component.model.Attribute;
import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.component.model.ComponentIcon;

public class ComponentDao implements IComponentDao {
    private final static Logger LOG = LoggerFactory.getLogger(ComponentDao.class);
    private final ICategoryDao categoryDao;

    @Inject
    public ComponentDao(ICategoryDao categoryDao) {
        checkNotNull(categoryDao, "CategoryDao not properly injected to ComponentDao.");

        this.categoryDao = categoryDao;
        LOG.debug("CategoryDao injected into ComponentDao.");
    }

    @Override
    public Collection<Component> findAll() throws DatabaseException {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(TABLE_NAME, Fields.ID, Fields.NAME, Fields.SYMBOL_LR, Fields.SYMBOL_RL,
                Fields.SYMBOL_UD, Fields.SYMBOL_DU, Fields.QUANTIFIER, Fields.BOOL_RATING,
                Fields.CATEGORY);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                ResultSet result = stmt.executeQuery();
                Collection<Component> components = fromResultSet(result);

                DbUtils.closeQuietly(result);

                return components;
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Component findById(int id) throws DatabaseException, ElementNotFoundException {
        checkArgument(id >= 0, "Id must not be negative.");

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(TABLE_NAME, Fields.ID, Fields.NAME, Fields.SYMBOL_LR, Fields.SYMBOL_RL,
                Fields.SYMBOL_UD, Fields.SYMBOL_DU, Fields.QUANTIFIER, Fields.BOOL_RATING,
                Fields.CATEGORY);
        builder.where(Fields.ID);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.ID, id);
                ResultSet result = stmt.executeQuery();

                Collection<Component> components = fromResultSet(result);
                DbUtils.closeQuietly(result);

                if (components.size() < 1) {
                    throw new ElementNotFoundException();
                }
                else if (components.size() > 1) {
                    throw new DatabaseException("ID '" + id + "' is not unique.");
                }

                return components.iterator().next();
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Attribute findAttributeById(final int id) throws ElementNotFoundException,
            DatabaseException {

        checkArgument(id >= 0, "ID must be valid.");

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(ATTRIBUTE_TABLE_NAME, AttributeFields.ID, AttributeFields.NAME,
                AttributeFields.FK_COMPONENT);
        builder.where(AttributeFields.ID);

        String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(AttributeFields.ID, id);

                ResultSet rs = stmt.executeQuery();
                rs.next();
                String name = rs.getString(AttributeFields.NAME);
                int componentId = rs.getInt(AttributeFields.FK_COMPONENT);

                Component component = null;
                try {
                    component = findById(componentId);
                }
                catch (ElementNotFoundException e) {
                    String msg = String.format(
                            "Attribute '%d' has an invalid component ID (%d) as FK.", id,
                            componentId);
                    LOG.error(msg, e);
                    throw new DatabaseException("Invalid component retrieved in attribute row!");
                }

                if (rs.next()) {
                    throw new DatabaseException("ID not unique.");
                }

                DbUtils.closeQuietly(rs);

                return new Attribute(id, name, component);
            }
        }
        catch (SQLException e) {
            throw new DatabaseException("Unknown error.", e);
        }
    }

    @Override
    public Collection<Attribute> findAttributesByComponent(Component component)
            throws DatabaseException {

        checkNotNull(component, "Component must not be null.");
        checkArgument(component.getId() >= 0, "Component must have a valid ID.");

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(ATTRIBUTE_TABLE_NAME, AttributeFields.ID, AttributeFields.NAME);
        builder.where(AttributeFields.FK_COMPONENT);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(AttributeFields.FK_COMPONENT, component.getId());
                ResultSet result = stmt.executeQuery();

                Collection<Attribute> attributes = fromAttributeResultSet(result, component);
                DbUtils.closeQuietly(result);

                return attributes;
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Collection<String> findAttributeNames() throws DatabaseException {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT DISTINCT ");
        builder.append(AttributeFields.NAME);
        builder.append(" FROM ").append(ATTRIBUTE_TABLE_NAME).append(";");

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(query)) {

                ResultSet rs = stmt.executeQuery();

                Collection<String> names = new ArrayList<>();
                while (rs.next()) {
                    String name = rs.getString(AttributeFields.NAME);
                    names.add(name);
                }

                DbUtils.closeQuietly(rs);

                return names;
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    /**
     * @see {@link IComponentDao#insert(Component)}
     */
    @Override
    public Component insert(Component component) throws SaveException {
        checkNotNull(component, "Component must not be null.");
        checkState(component.getCategory().isPresent(), "Component must have a valid category set.");
        checkState(component.getCategory().get().getId() >= 0,
                "Component must have a valid category set.");

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.insert(TABLE_NAME, Fields.NAME, Fields.SYMBOL_LR, Fields.SYMBOL_RL,
                Fields.SYMBOL_UD, Fields.SYMBOL_DU, Fields.QUANTIFIER, Fields.BOOL_RATING,
                Fields.CATEGORY);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            con.setAutoCommit(false);

            Optional<ComponentIcon> downUpImage = Optional.<ComponentIcon>absent();
            Optional<ComponentIcon> leftRightImage = Optional.<ComponentIcon>absent();
            Optional<ComponentIcon> rightLeftImage = Optional.<ComponentIcon>absent();
            Optional<ComponentIcon> upDownImage = Optional.<ComponentIcon>absent();
            try {
                // Insert the blobs
            	if (component.getDownUpImage().isPresent()) {
            		downUpImage = Optional.of(insertIcon(con, component.getDownUpImage().get()));
            	}
            	
            	if (component.getLeftRightImage().isPresent()) {
            		leftRightImage = Optional.of(insertIcon(con, component.getLeftRightImage().get()));
            	}
            	
            	if (component.getRightLeftImage().isPresent()) {
            		rightLeftImage = Optional.of(insertIcon(con, component.getRightLeftImage().get()));
            	}
            	
            	if (component.getUpDownImage().isPresent()) {
            		upDownImage = Optional.of(insertIcon(con, component.getUpDownImage().get()));
            	}
            }
            catch (Exception e) {
                LOG.error("Unable to insert one or more component images.", e);
                con.rollback();
                throw new SaveException("Unable to insert one or more component images.", e);
            }

            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.NAME, component.getName());
                if (leftRightImage.isPresent()) {
                	stmt.setParameter(Fields.SYMBOL_LR, leftRightImage.get().getId());
                }
                else {
                	stmt.setParameterNull(Fields.SYMBOL_LR);
                }
                
                if (rightLeftImage.isPresent()) {
                	stmt.setParameter(Fields.SYMBOL_RL, rightLeftImage.get().getId());
                }
                else {
                	stmt.setParameterNull(Fields.SYMBOL_RL);
                }
                
                if (upDownImage.isPresent()) {
                	stmt.setParameter(Fields.SYMBOL_UD, upDownImage.get().getId());
                }
                else {
                	stmt.setParameterNull(Fields.SYMBOL_UD);
                }
                
                if (downUpImage.isPresent()) {
                	stmt.setParameter(Fields.SYMBOL_DU, downUpImage.get().getId());
                }
                else {
                	stmt.setParameterNull(Fields.SYMBOL_DU);
                }
                
                stmt.setParameter(Fields.BOOL_RATING, component.getBoolRating());
                stmt.setParameter(Fields.CATEGORY, component.getCategory().get().getId());

                stmt.setParameter(Fields.QUANTIFIER, component.getQuantifier().or(-1));

                int affectedRows = stmt.executeUpdate();
                if (affectedRows != 1) {
                    con.rollback();
                    throw new SaveException();
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);

                        // Create new Component with id
                        Component inserted = new Component(id, component.getName(),
                                leftRightImage.orNull(), rightLeftImage.orNull(),
                                upDownImage.orNull(), downUpImage.orNull(), component
                                        .getQuantifier().or(-1), component.getBoolRating());

                        inserted.setCategory(component.getCategory().orNull());
                        con.commit();
                        return inserted;
                    }
                    else {
                        con.rollback();
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
    public void update(Component component) throws ElementNotFoundException, SaveException {
        checkNotNull(component, "Component must not be null.");
        checkState(component.getId() >= 0, "Component has an invalid ID.");

        if (component.getId() < 0) {
            throw new ElementNotFoundException("Element has no valid ID.");
        }

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.update(TABLE_NAME, Fields.NAME, Fields.SYMBOL_LR, Fields.SYMBOL_RL,
                Fields.SYMBOL_UD, Fields.SYMBOL_DU, Fields.QUANTIFIER, Fields.BOOL_RATING,
                Fields.CATEGORY);
        builder.where(Fields.ID);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
        	con.setAutoCommit(false);

        	// FIXME: later we must delete images!!!
        	Optional<ComponentIcon> downUpImage = Optional.<ComponentIcon>absent();
            Optional<ComponentIcon> leftRightImage = Optional.<ComponentIcon>absent();
            Optional<ComponentIcon> rightLeftImage = Optional.<ComponentIcon>absent();
            Optional<ComponentIcon> upDownImage = Optional.<ComponentIcon>absent();
            try {
                // Insert the blobs
            	if (component.getDownUpImage().isPresent()) {
            		if (component.getDownUpImage().get().getId() < 0) {
            			downUpImage = Optional.of(insertIcon(con, component.getDownUpImage().get()));
            		}
            		else {
            			downUpImage = component.getDownUpImage();
            		}
            	}
            	
            	if (component.getLeftRightImage().isPresent()) {
            		if (component.getLeftRightImage().get().getId() < 0) {
            			leftRightImage = Optional.of(insertIcon(con, component.getLeftRightImage().get()));
            		}
            		else {
            			leftRightImage = component.getLeftRightImage();
            		}
            	}
            	
            	if (component.getRightLeftImage().isPresent()) {
            		if (component.getRightLeftImage().get().getId() < 0) {
            			rightLeftImage = Optional.of(insertIcon(con, component.getRightLeftImage().get()));
            		}
            		else {
            			rightLeftImage = component.getRightLeftImage();
            		}
            	}
            	
            	if (component.getUpDownImage().isPresent()) {
            		if (component.getUpDownImage().get().getId() < 0) {
            			upDownImage = Optional.of(insertIcon(con, component.getUpDownImage().get()));
            		}
            		else {
            			upDownImage = component.getUpDownImage();
            		}
            	}
            }
            catch (Exception e) {
                LOG.error("Unable to insert one or more component images.", e);
                con.rollback();
                throw new SaveException("Unable to insert one or more component images.", e);
            }
        	
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(Fields.ID, component.getId());
            	stmt.setParameter(Fields.NAME, component.getName());
                
                if (leftRightImage.isPresent()) {
                	stmt.setParameter(Fields.SYMBOL_LR, leftRightImage.get().getId());
                }
                else {
                	stmt.setParameterNull(Fields.SYMBOL_LR);
                }
                if (rightLeftImage.isPresent()) {
                	stmt.setParameter(Fields.SYMBOL_RL, rightLeftImage.get().getId());
                }
                else {
                	stmt.setParameterNull(Fields.SYMBOL_RL);
                }
                if (upDownImage.isPresent()) {
                	stmt.setParameter(Fields.SYMBOL_UD, upDownImage.get().getId());
                }
                else {
                	stmt.setParameterNull(Fields.SYMBOL_UD);
                }
                if (downUpImage.isPresent()) {
                	stmt.setParameter(Fields.SYMBOL_DU, downUpImage.get().getId());
                }
                else {
                	stmt.setParameterNull(Fields.SYMBOL_DU);
                }
                
                stmt.setParameter(Fields.QUANTIFIER, component.getQuantifier().get());
                stmt.setParameter(Fields.BOOL_RATING, component.getBoolRating());
                stmt.setParameter(Fields.CATEGORY, component.getCategory().get().getId());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows != 1) {
                    throw new SaveException();
                }
                
                con.commit();
            }
        }
        catch (SQLException | DatabaseException e) {
            throw new SaveException(e);
        }
    }

    @Override
    public Attribute addAttribute(final Component component, final String attributeName)
            throws SaveException {

        checkNotNull(component, "Component must not be null.");
        checkArgument(component.getId() >= 0, "Component must have a valid ID.");

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.insert(ATTRIBUTE_TABLE_NAME, AttributeFields.NAME, AttributeFields.FK_COMPONENT);

        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(AttributeFields.NAME, attributeName);
                stmt.setParameter(AttributeFields.FK_COMPONENT, component.getId());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows != 1) {
                    throw new SaveException();
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);

                        // Create new Component with id
                        Attribute attribute = new Attribute(id, attributeName, component);
                        return attribute;
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
    public void deleteAttribute(final Attribute attribute) throws DatabaseException {
        checkNotNull(attribute, "Attribute must not be null.");
        checkArgument(attribute.getId() >= 0, "Attribute must have a valid ID.");

        StringBuilder builder = new StringBuilder();
        builder.append("DELETE FROM ").append(ATTRIBUTE_TABLE_NAME);
        builder.append(" WHERE ").append(AttributeFields.ID);
        builder.append(" = ?;");

        String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setInt(1, attribute.getId());

                con.setAutoCommit(false);
                int affected = stmt.executeUpdate();

                if (affected > 1) {
                    con.rollback();
                    throw new DatabaseException("Accidently more than one row affected.");
                }
                else if (affected < 1) {
                    con.rollback();
                    throw new ElementNotFoundException();
                }

                con.commit();
            }
        }
        catch (SQLException | DatabaseException e) {
            throw new DatabaseException("Unknown error.", e);
        }
    }

    private Collection<Component> fromResultSet(ResultSet rs) throws SQLException,
            DatabaseException {
        checkNotNull(rs, "Result must not be null.");
        Collection<Component> componentList = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(Fields.ID);
            String name = rs.getString(Fields.NAME);
            long start = System.nanoTime();
            ComponentIcon leftRightImage = findIcon(rs.getInt(Fields.SYMBOL_LR));
            ComponentIcon rightLeftImage = findIcon(rs.getInt(Fields.SYMBOL_RL));
            ComponentIcon upDownImage = findIcon(rs.getInt(Fields.SYMBOL_UD));
            ComponentIcon downUpImage = findIcon(rs.getInt(Fields.SYMBOL_DU));
            long stop = System.nanoTime();
            LOG.debug(String.format("Blobs retrieved in %d ms.", (stop - start) / 1000000));
            int quantifier = rs.getInt(Fields.QUANTIFIER);
            boolean boolRating = rs.getBoolean(Fields.BOOL_RATING);
            Component component = new Component(id, name, leftRightImage, rightLeftImage,
                    upDownImage, downUpImage, quantifier, boolRating);
            component.setCategory(categoryDao.findById(rs.getInt(Fields.CATEGORY)));
            componentList.add(component);
        }

        return componentList;
    }

    private Collection<Attribute> fromAttributeResultSet(final ResultSet rs,
            final Component component) throws SQLException, DatabaseException {

        checkNotNull(rs, "ResultSet must not be null.");
        Collection<Attribute> attributeList = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(AttributeFields.ID);
            String name = rs.getString(AttributeFields.NAME);

            Attribute attribute = new Attribute(id, name, component);
            attributeList.add(attribute);
        }

        return attributeList;
    }

    public ComponentIcon findIcon(int id) throws DatabaseException {
        if (id <= 0) {
            return null;
        }
        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.select(BLOB_TABLE_NAME, BlobFields.BLOB, BlobFields.FILENAME);
        builder.where(BlobFields.ID);
        final String query = builder.toString();

        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.setParameter(BlobFields.ID, id);
                
                ResultSet result = stmt.executeQuery();
                
                if (!result.next()) {
                    throw new ElementNotFoundException();
                }
                
                byte[] blob = result.getBytes(BlobFields.BLOB);
                String filename = result.getString(BlobFields.FILENAME); 
                DbUtils.closeQuietly(result);
                
                return new ComponentIcon(id, blob, filename);
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    /**
     * Inserts the blob as component image and returns its ID.
     * 
     * @param blob
     * @return
     * @throws DatabaseException
     */
    private ComponentIcon insertIcon(final Connection con, final ComponentIcon icon)
            throws DatabaseException {

        try {
            checkState(!con.getAutoCommit(),
                    "AutoCommit must be disabled as we should run in a transaction.");
        }
        catch (SQLException e) {
            throw new SaveException("Unknown error.", e);
        }

        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ").append(BLOB_TABLE_NAME);
        builder.append(" (").append(BlobFields.BLOB);
        builder.append(", ").append(BlobFields.FILENAME).append(")");
        builder.append(" VALUES (?, ?);");

        try (PreparedStatement stmt = con.prepareStatement(builder.toString())) {
            stmt.setBytes(1, icon.getBlob());
            stmt.setString(2, icon.getFilename());

            int affected = stmt.executeUpdate();
            if (affected != 1) {
                throw new SaveException();
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    
                    DbUtils.closeQuietly(generatedKeys);
                    
                    // Create new Component with id
                    return new ComponentIcon(id, icon.getBlob(), icon.getFilename());
                }
                else {
                    throw new SaveException("Could not retrieve generated ID.");
                }
            }
        }
        catch (SQLException e) {
            throw new DatabaseException("Unknown error.", e);
        }
    }

    private static final String TABLE_NAME = "Component";
    private static final String BLOB_TABLE_NAME = "Component_Picture";
    private static final String ATTRIBUTE_TABLE_NAME = "Attribute";

    private static class Fields {
        public static final String ID = "Component_ID";
        public static final String NAME = "Component_Name";
        public static final String SYMBOL_LR = "Component_Symbol_LR_FK";
        public static final String SYMBOL_RL = "Component_Symbol_RL_FK";
        public static final String SYMBOL_UD = "Component_Symbol_DU_FK";
        public static final String SYMBOL_DU = "Component_Symbol_UD_FK";
        public static final String QUANTIFIER = "Component_Quantifier";
        public static final String BOOL_RATING = "Component_Bool_Rating";
        public static final String CATEGORY = "Component_Category_FK";
    }

    private static class BlobFields {
        public static final String ID = "Component_Picture_ID";
        public static final String BLOB = "Component_Picture_Blob";
        public static final String FILENAME = "Component_Picture_Filename";
    }

    private static class AttributeFields {
        public static final String ID = "Attribute_ID";
        public static final String NAME = "Attribute_Name";
        public static final String FK_COMPONENT = "Attribute_Component_FK";
    }
}
