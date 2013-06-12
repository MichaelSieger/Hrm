package de.hswt.hrm.contact.model;

import com.google.common.base.Optional;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Represents a contact.
 */
public final class Contact {
    private int id;
    private String name;
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

    private static final String IS_MANDATORY = "Field is a mandatory.";

    public Contact(final String Name, final String street,
            final String streetNo, final String postCode, final String city) {

        this(-1, Name,  street, streetNo, postCode, city);
    }

    public Contact(int id, final String Name, final String street,
            final String streetNo, final String postCode, final String city) {

        this.id = id;

        setName(Name);
        setStreet(street);
        setStreetNo(streetNo);
        setPostCode(postCode);
        setCity(city);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        checkArgument(!isNullOrEmpty(name), IS_MANDATORY);
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        checkArgument(!isNullOrEmpty(street), IS_MANDATORY);
        this.street = street;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        checkArgument(!isNullOrEmpty(streetNo), IS_MANDATORY);
        this.streetNo = streetNo;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        checkArgument(!isNullOrEmpty(postCode), IS_MANDATORY);
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        checkArgument(!isNullOrEmpty(city), IS_MANDATORY);
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
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((fax == null) ? 0 : fax.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
        result = prime * result + ((postCode == null) ? 0 : postCode.hashCode());
        result = prime * result + ((shortcut == null) ? 0 : shortcut.hashCode());
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Contact other = (Contact) obj;
        if (city == null) {
            if (other.city != null) {
                return false;
            }
        }
        else if (!city.equals(other.city)) {
            return false;
        }
        if (email == null) {
            if (other.email != null) {
                return false;
            }
        }
        else if (!email.equals(other.email)) {
            return false;
        }
        if (fax == null) {
            if (other.fax != null) {
                return false;
            }
        }
        else if (!fax.equals(other.fax)) {
            return false;
        }
        
        if (id != other.id) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        }
        else if (!name.equals(other.name)) {
            return false;
        }
        if (mobile == null) {
            if (other.mobile != null) {
                return false;
            }
        }
        else if (!mobile.equals(other.mobile)) {
            return false;
        }
        if (phone == null) {
            if (other.phone != null) {
                return false;
            }
        }
        else if (!phone.equals(other.phone)) {
            return false;
        }
        if (postCode == null) {
            if (other.postCode != null) {
                return false;
            }
        }
        else if (!postCode.equals(other.postCode)) {
            return false;
        }
        if (shortcut == null) {
            if (other.shortcut != null) {
                return false;
            }
        }
        else if (!shortcut.equals(other.shortcut)) {
            return false;
        }
        if (street == null) {
            if (other.street != null) {
                return false;
            }
        }
        else if (!street.equals(other.street)) {
            return false;
        }
        return true;
    }

}
