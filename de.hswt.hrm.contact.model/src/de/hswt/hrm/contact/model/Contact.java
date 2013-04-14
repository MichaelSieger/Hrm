package de.hswt.hrm.contact.model;

import com.google.common.base.Optional;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Represents a contact.
 */
public class Contact {
    private int id;
    private String lastName;
    private String firstName;
    private String company;
    private String street;
    private String streetNo;
    private String postCode;
    private String city;
    
    // optional
    private String shortcut;
    private String phone;
    private String fax;
    private String mobile;
    private String email;
    
    public Contact(final String lastName, final String firstName, final String street,
            final String streetNo, final String postCode, final String city) {
        
        this(-1, lastName, firstName, street, streetNo, postCode, city);
    }
    
    public Contact(int id, final String lastName, final String firstName, final String street,
            final String streetNo, final String postCode, final String city) {
        // Check values
        checkArgument(!isNullOrEmpty(lastName), "LastName is a mandatory field");
        checkArgument(!isNullOrEmpty(firstName), "FirstName is a mandatory field");
        checkArgument(!isNullOrEmpty(street), "Street is a mandatory field");
        checkArgument(!isNullOrEmpty(streetNo), "StreetNo is a mandatory field");
        checkArgument(!isNullOrEmpty(postCode), "PostCode is a mandatory field");
        checkArgument(!isNullOrEmpty(city), "City is a mandatory field");
        
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.street = street;
        this.streetNo = streetNo;
        this.postCode = postCode;
        this.city = city;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    
    public String getStreetNo() {
    	return streetNo;
    }
    
    public void setStreetNo(String streetNo) {
    	this.streetNo = streetNo;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Optional<String> getShortcut() {
        return Optional.fromNullable(shortcut);
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public Optional<String> getPhone() {
        return Optional.fromNullable(phone);
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Optional<String> getFax() {
        return Optional.fromNullable(fax);
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Optional<String> getMobile() {
        return Optional.fromNullable(mobile);
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Optional<String> getEmail() {
        return Optional.fromNullable(email);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((company == null) ? 0 : company.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((fax == null) ? 0 : fax.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
        result = prime * result + ((postCode == null) ? 0 : postCode.hashCode());
        result = prime * result + ((shortcut == null) ? 0 : shortcut.hashCode());
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Contact other = (Contact) obj;
        if (city == null) {
            if (other.city != null)
                return false;
        }
        else if (!city.equals(other.city))
            return false;
        if (company == null) {
            if (other.company != null)
                return false;
        }
        else if (!company.equals(other.company))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        }
        else if (!email.equals(other.email))
            return false;
        if (fax == null) {
            if (other.fax != null)
                return false;
        }
        else if (!fax.equals(other.fax))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        }
        else if (!firstName.equals(other.firstName))
            return false;
        if (id != other.id)
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        }
        else if (!lastName.equals(other.lastName))
            return false;
        if (mobile == null) {
            if (other.mobile != null)
                return false;
        }
        else if (!mobile.equals(other.mobile))
            return false;
        if (phone == null) {
            if (other.phone != null)
                return false;
        }
        else if (!phone.equals(other.phone))
            return false;
        if (postCode == null) {
            if (other.postCode != null)
                return false;
        }
        else if (!postCode.equals(other.postCode))
            return false;
        if (shortcut == null) {
            if (other.shortcut != null)
                return false;
        }
        else if (!shortcut.equals(other.shortcut))
            return false;
        if (street == null) {
            if (other.street != null)
                return false;
        }
        else if (!street.equals(other.street))
            return false;
        return true;
    }
    
}
