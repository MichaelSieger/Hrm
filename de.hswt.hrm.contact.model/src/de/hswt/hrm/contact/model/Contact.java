package de.hswt.hrm.contact.model;

/**
 * Represents a contact.
 */
public class Contact {
    private long id;
    private String lastName;
    private String firstName;
    private String company;
    private String street;
    private String postCode;
    private String city;
    private String acronym;
    private String phone;
    private String fax;
    private String mobile;
    private String email;
    
    public Contact(final String lastName, final String firstName, final String street,
            final String postCode, final String city) {
        
        this(-1, lastName, firstName, street, postCode, city);
    }
    
    public Contact(long id, final String lastName, final String firstName, final String street,
            final String postCode, final String city) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.street = street;
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

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }
}
