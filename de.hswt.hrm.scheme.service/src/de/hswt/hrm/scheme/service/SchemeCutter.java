package de.hswt.hrm.scheme.service;

import java.util.ArrayList;
import java.util.Collection;

import de.hswt.hrm.scheme.model.SchemeComponent;

public class SchemeCutter {
	
	/**
	 * Cuts away unused space in the scheme.
	 * After this the minimum x and y is 0. 
	 * 
	 * @param comps
	 * @return
	 */
	public Collection<SchemeComponent> cut(Collection<SchemeComponent> comps){
		Collection<SchemeComponent> result = new ArrayList<>();
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		for(SchemeComponent c : comps){
			minX = Math.min(minX, c.getX());
			minY = Math.min(minY, c.getY());
		}
		for(SchemeComponent c : comps){
			SchemeComponent nComp = new SchemeComponent(
					c.getX() - minX, c.getY() - minY, c.getDirection(), c.getComponent());
			result.add(nComp);
		}
		return result;
	}

}
