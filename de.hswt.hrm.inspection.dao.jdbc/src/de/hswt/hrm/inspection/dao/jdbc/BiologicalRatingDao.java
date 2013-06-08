package de.hswt.hrm.inspection.dao.jdbc;

import java.util.Collection;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.ElementNotFoundException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.common.exception.NotImplementedException;
import de.hswt.hrm.inspection.dao.core.IBiologicalRatingDao;
import de.hswt.hrm.inspection.model.BiologicalRating;

public class BiologicalRatingDao implements IBiologicalRatingDao {

	@Override
	public Collection<BiologicalRating> findAll() throws DatabaseException {
		throw new NotImplementedException();
	}

	@Override
	public BiologicalRating findById(int id) throws DatabaseException,
			ElementNotFoundException {
		throw new NotImplementedException();
	}

	@Override
	public BiologicalRating insert(BiologicalRating biological)
			throws SaveException {
		throw new NotImplementedException();
	}

	@Override
	public void update(BiologicalRating biological)
			throws ElementNotFoundException, SaveException {
		throw new NotImplementedException();
	}
	
	
	
	private static final class Fields {
		public static final String ID = "";
		public static final String BACTERIA = "";
		public static final String RATING = "";
		public static final String QUANTIFIER = "";
		public static final String COMMENT = "";
		public static final String FK_COMPONENT = "";
		public static final String FK_REPORT = "";
		public static final String FK_FLAG = "";
	}
}
