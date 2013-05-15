package de.hswt.hrm.catalog.dao.context;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;

public class JdbcCurrentDaoContextFunction extends ContextFunction {

	@Override
	public Object compute(IEclipseContext context) {
		System.out.println("### CurrentDao");
		return null;
	}

}
