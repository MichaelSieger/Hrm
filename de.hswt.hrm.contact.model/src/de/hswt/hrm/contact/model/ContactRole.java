package de.hswt.hrm.contact.model;

/**
 * Represents the role of a contact. (E. g. principal or agent)
 */
public class ContactRole {
    private long id;
    private String name;
    
    public ContactRole(final String name) {
        this(-1, name);
    }
    
    public ContactRole(long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }
    
}
