package de.hswt.hrm.component.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.util.Collection;

import org.junit.Test;

import com.google.common.base.Optional;

import de.hswt.hrm.catalog.dao.core.ICatalogDao;
import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.component.dao.core.ICategoryDao;
import de.hswt.hrm.component.dao.core.IComponentDao;
import de.hswt.hrm.component.model.Attribute;
import de.hswt.hrm.component.model.Category;
import de.hswt.hrm.component.model.Component;
import de.hswt.hrm.component.model.ComponentIcon;
import de.hswt.hrm.test.database.AbstractDatabaseTest;

public class ComponentDaoTest extends AbstractDatabaseTest {

    private ICategoryDao getCategoryDao() {
        ICatalogDao catalogDao = mock(ICatalogDao.class);
        return new CategoryDao(catalogDao);
    }

    private IComponentDao getInjectedDao() {
        ICategoryDao categoryDao = getCategoryDao();
        ComponentDao componentDao = new ComponentDao(categoryDao);

        return componentDao;
    }

    private Component getComponent() throws SaveException {
        ICategoryDao categoryDao = getCategoryDao();
        Category cat = new Category("Some Category", 2, 2, 1, true);
        cat = categoryDao.insert(cat);

        Component comp = new Component("Testcomp", 
        		new ComponentIcon(new byte[5], "some_file.jpg"), 
        		new ComponentIcon(new byte[5], "some_file.jpg"), 
        		new ComponentIcon(new byte[5], "some_file.jpg"),
        		new ComponentIcon(new byte[5], "some_file.jpg"), 
        		1, 
        		true);

        comp.setCategory(cat);
        return comp;
    }

    private boolean iconEquals(final Optional<ComponentIcon> a, final Optional<ComponentIcon> b) {
    	if (a.isPresent() != b.isPresent()) {
    		return false;
    	}
    	
    	if (a.isPresent()) {
    		return iconEquals(a.get(), b.get());
    	}
    	
    	return true;
    }
    
    private boolean iconEquals(final ComponentIcon a, final ComponentIcon b) {
        if (a.getBlob().length != b.getBlob().length) {
            return false;
        }

        byte[] blobA = a.getBlob();
        byte[] blobB = b.getBlob();
        for (int i = 0; i < blobA.length; i++) {
            if (blobA[i] != blobB[i]) {
                return false;
            }
        }
        
        return a.getFilename().equals(b.getFilename());
    }

    private void compareComponents(final Component expected, final Component actual) {
        assertEquals("BoolRating not set correctly.", expected.getBoolRating(),
                actual.getBoolRating());
        assertEquals("Category not set correctly.", expected.getCategory().orNull(), actual
                .getCategory().orNull());
        assertTrue("DownUpImage not set correctly.",
                iconEquals(expected.getDownUpImage(), actual.getDownUpImage()));
        assertTrue("LeftRightImage not set correctly.",
                iconEquals(expected.getLeftRightImage(), actual.getLeftRightImage()));
        assertEquals("Name not set correctly.", expected.getName(), actual.getName());
        assertEquals("Quantifier not set correctly.", expected.getQuantifier(),
                actual.getQuantifier());
        assertTrue("RightLeftImage not set correctly.",
                iconEquals(expected.getRightLeftImage(), actual.getRightLeftImage()));
        assertTrue("UpDownImage not set correctly.",
                iconEquals(expected.getUpDownImage(), actual.getUpDownImage()));
    }

    @Test
    public void testInsert() throws ElementNotFoundException, DatabaseException {
        IComponentDao componentDao = getInjectedDao();
        Component component = getComponent();

        Component parsed = componentDao.insert(component);
        assertTrue("ID not set correctly.", parsed.getId() >= 0);
        compareComponents(component, parsed);

        Component fromDb = componentDao.findById(parsed.getId());
        assertEquals("ID not set correctly.", parsed.getId(), fromDb.getId());
        compareComponents(component, fromDb);
    }
    
    @Test
    public void testUpdate() throws ElementNotFoundException, DatabaseException {
        IComponentDao componentDao = getInjectedDao();
        Component component = getComponent();
        Component parsed = componentDao.insert(component);
        assertTrue("ID not set correctly.", parsed.getId() >= 0);
        
        parsed.setName("Some other name");
        parsed.setDownUpImage(new ComponentIcon(new byte[10], "new_file_for_component.png"));
        componentDao.update(parsed);
        
        Component actual = componentDao.findById(parsed.getId());
        compareComponents(parsed, actual);
    }

    @Test
    public void testAddAttribute() throws DatabaseException {
        IComponentDao componentDao = getInjectedDao();
        Component component = getComponent();
        Component parsed = componentDao.insert(component);
        final String attribute1 = "Some Attribute";
        final String attribute2 = "Some Other Attribute";

        componentDao.addAttribute(parsed, attribute1);
        componentDao.addAttribute(parsed, attribute2);

        Collection<Attribute> attributes = componentDao.findAttributesByComponent(parsed);
        assertEquals("Wrong count of attributes retrieved.", 2, attributes.size());

        // Check if retrieved attributes equal to inserted ones

        Attribute[] attrs = attributes.toArray(new Attribute[0]);
        if (!attrs[0].getName().equals(attribute1) && !attrs[0].getName().equals(attribute2)
                || !attrs[1].getName().equals(attribute1) && !attrs[1].getName().equals(attribute2)) {
            fail("Wrong attribute retrieved");
        }

        // Ensure that we don't have retrieved the same attribute twice
        assertFalse("Same attribute retrieved twice.", attrs[0].equals(attrs[1]));
    }
}
