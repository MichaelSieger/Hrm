package de.hswt.hrm.misc.priority.dao.jdbc;

import static com.google.common.base.Preconditions.checkNotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.misc.priority.dao.core.IPriorityDao;
import de.hswt.hrm.misc.priority.model.Priority;

public class PriorityDao implements IPriorityDao {

    @Override
    public Collection<Priority> findAll() throws DatabaseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Priority findById(int id) throws DatabaseException, ElementNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Priority insert(Priority priority) throws SaveException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(Priority priority) throws ElementNotFoundException, SaveException {
        // TODO Auto-generated method stub

    }

    private Collection<Priority> fromResultSet(ResultSet rs) throws SQLException {
        checkNotNull(rs, "Result must not be null.");
        Collection<Priority> priorityList = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(Fields.ID);
            String name = rs.getString(Fields.NAME);
            String text = rs.getString(Fields.TEXT);
            int priority = rs.getInt(Fields.PRIORITY);

            Priority prio = new Priority(id, name, text, priority);

            priorityList.add(prio);
        }

        return priorityList;
    }

    private static final String TABLE_NAME = "Priority";

    private static class Fields {
        public static final String ID = "Priority_Id";
        public static final String NAME = "Priority_Name";
        public static final String TEXT = "Priority_Text";
        public static final String PRIORITY = "Priority_Priority";

    }

}
