package de.hswt.hrm.contact.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import static com.google.common.base.Preconditions.*;

import de.hswt.hrm.common.database.DatabaseFactory;
import de.hswt.hrm.common.database.NamedParameterStatement;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.contact.dao.core.IContactDao;

public class ContactDao implements IContactDao {

    @Override
    public Collection<Contact> findAll() throws DatabaseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Contact findById(int id) throws DatabaseException, ElementNotFoundException {
        checkArgument(id >= 0, "Id must not be negative.");
        
        final String query = "SELECT Contact_ID, Contact_Name, Contact_First_Name, "
                + "Contact_Zip_Code, "
                + "Contact_City, Contact_Street, Contact_Street_Number, Contact_Shortcut, "
                + "Contact_Phone, Contact_Fax, Contact_Mobile, Contact_Email FROM Contact "
                + "WHERE Contact_ID = :id;";
        
        try (Connection con = DatabaseFactory.getConnection()) {
            try (NamedParameterStatement stmt = NamedParameterStatement.fromConnection(con, query)) {
                stmt.addParameter("id", id);
                ResultSet result = stmt.executeQuery();
                
                Collection<Contact> contacts = fromResultSet(result);
                if (contacts.size() < 1) {
                    throw new ElementNotFoundException();
                }
                else if (contacts.size() > 1) {
                    throw new DatabaseException("ID '" + id + "' is not unique.");
                }
                
                return contacts.iterator().next();
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    /**
     * @see {@link IContactDao#insert(Contact)}
     */
    @Override
    public Contact insert(Contact contact) throws SaveException {
    	final String query = "INSERT INTO Contact (Contact_Name, Contact_First_Name, "
    			+ "Contact_Zip_Code, Contact_City, Contact_Street, Contact_Street_Number, "
    			+ "Contact_Shortcut, Contact_Phone, Contact_Fax, Contact_Mobile, Contact_Email) " 
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
		    			
		    			// Create new contact with id
		    			Contact inserted = new Contact(id, contact.getLastName(), 
		    			        contact.getFirstName(), contact.getStreet(), 
		    			        contact.getStreetNo(), contact.getPostCode(), contact.getCity());
		    			
		    			inserted.setShortcut(contact.getShortcut().orNull());
		    			inserted.setPhone(contact.getPhone().orNull());
		    			inserted.setFax(contact.getFax().orNull());
		    			inserted.setMobile(contact.getMobile().orNull());
		    			inserted.setEmail(contact.getEmail().orNull());
		    			
		    			return inserted;
		    		}
		    		else {
		    		    throw new SaveException("Could not retrieve generated ID.");    
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

    private Collection<Contact> fromResultSet(ResultSet rs) throws SQLException {
        checkNotNull(rs, "Result must not be null.");
        Collection<Contact> contactList = new ArrayList<>();
        
        while (rs.next()) {
            //Contact_Name, Contact_First_Name, Contact_Zip_Code, "
            //        + "Contact_City, Contact_Street, Contact_Street_Number, Contact_Shortcut, "
            //        + "Contact_Phone, Contact_Fax, Contact_Mobile, Contact_Email
            int id = rs.getInt("Contact_ID");
            String lastName = rs.getString("Contact_Name");
            String firstName = rs.getString("Contact_First_Name"); 
            String zipCode = rs.getString("Contact_Zip_Code");
            String city = rs.getString("Contact_City");
            String street = rs.getString("Contact_Street");
            String streetNo = rs.getString("Contact_Street_Number");
            
            Contact contact = new Contact(id, lastName, firstName, street, streetNo, zipCode, city);
            contact.setShortcut(rs.getString("Contact_Shortcut"));
            contact.setPhone(rs.getString("Contact_Phone"));
            contact.setFax(rs.getString("Contact_Fax"));
            contact.setMobile(rs.getString("Contact_Mobile"));
            contact.setEmail(rs.getString("Contact_Email"));
            
            contactList.add(contact);
        }
        
        return contactList;
    }
}
