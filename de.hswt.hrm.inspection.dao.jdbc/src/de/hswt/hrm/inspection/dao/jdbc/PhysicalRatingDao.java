package de.hswt.hrm.inspection.dao.jdbc;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.inspection.dao.core.IPhysicalRatingDao;
import de.hswt.hrm.inspection.model.PhysicalRating;
import de.hswt.hrm.scheme.model.Scheme;

public class PhysicalRatingDao implements IPhysicalRatingDao {

	@Override
	public Collection<PhysicalRating> findAll() throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PhysicalRating findById(int id) throws DatabaseException,
			ElementNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PhysicalRating insert(PhysicalRating physical) throws SaveException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(PhysicalRating physical)
			throws ElementNotFoundException, SaveException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<PhysicalRating> findByScheme(Scheme scheme) {
		// TODO Auto-generated method stub
		return null;
	}

}
