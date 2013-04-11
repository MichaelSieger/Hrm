package de.hswt.hrm.contact.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;

import de.hswt.hrm.common.database.DatabaseFactory;
import de.hswt.hrm.common.database.DatabaseUtil;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.contact.dao.core.IContactDao;

public class ContactDao implements IContactDao {

    @Override
    public Collection<Contact> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Contact findById(long id) throws ElementNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see {@link IContactDao#insert(Contact)}
     */
    @Override
    public void insert(Contact contact) throws SaveException {
    	final String query = "INSERT INTO Contact (Contact_Name, Contact_First_Name, "
    			+ "Contact_Zip_Code, Contact_City, Contact_Street, Contact_Street_Number, "
    			+ "Contact_Shortcut, Contact_Phone, Contact_Fax, Contact_Mobil, Contact_Email) " 
    			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    	
    	try (Connection con = DatabaseFactory.getConnection()) {
	    	try(PreparedStatement stmt = con.prepareStatement(query)) {
	    		
	    		stmt.setString(1, contact.getLastName());
	    		stmt.setString(2, contact.getFirstName());
	    		stmt.setString(3, contact.getPostCode());
	    		stmt.setString(4, contact.getCity());
	    		stmt.setString(5, contact.getStreet());
	    		stmt.setString(6, contact.getStreetNo());
	    		
	    		if (contact.getShortcut().isPresent()) {
	    			stmt.setString(7, contact.getShortcut().get());
	    		}
	    		else {
	    			stmt.setNull(7, Types.VARCHAR);
	    		}
	    		
	    		if (contact.getPhone().isPresent()) {
	    			stmt.setString(8, contact.getPhone().get());
	    		}
	    		else {
	    			stmt.setNull(8, Types.VARCHAR);
	    		}
	    		
	    		if (contact.getFax().isPresent()) {
	    			stmt.setString(9, contact.getFax().get());
	    		}
	    		else {
	    			stmt.setNull(9, Types.VARCHAR);
	    		}
	    		
	    		if (contact.getMobile().isPresent()) {
	    			stmt.setString(10, contact.getMobile().get());
	    		}
	    		else {
	    			stmt.setNull(10, Types.VARCHAR);
	    		}
	    		
	    		if (contact.getEmail().isPresent()) {
	    			stmt.setString(11, contact.getEmail().get());
	    		}
	    		else {
	    			stmt.setNull(11, Types.VARCHAR);
	    		}
	    		
	    		int affectedRows = stmt.executeUpdate();
	    		if (affectedRows != 1) {
	    			throw new SaveException();
	    		}
	    		
	    		try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
		    		if (generatedKeys.next()) {
		    			int id = generatedKeys.getInt(1);
		    			// TODO retrieve object from database and return it
		    		}
	    		}
	    		catch (SQLException e) {
	    			throw new SaveException("Could not retrieve generated ID.", e);
	    		}
	    	}
    	}
    	catch (SQLException|DatabaseException e) {
    		throw new SaveException(e);
    	}
    }

    @Override
    public void update(Contact contact) throws ElementNotFoundException, SaveException {
        // TODO Auto-generated method stub
        
    }

}
