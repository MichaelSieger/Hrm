package de.hswt.hrm.catalog.model.tree;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.hswt.hrm.catalog.model.Catalog;

public class TreeCatalog {
	private final Catalog catalog;
	private List<TreeTarget> targets;
	
    public TreeCatalog(final Catalog catalog) {
        this(catalog, new LinkedList<TreeTarget>());
    }
    
    public TreeCatalog(final Catalog catalog, List<TreeTarget> targets) {
    	this.catalog = catalog;
    	this.targets = targets;
    }

    public String getName() {
        return catalog.getName();
    }

    /**
     * @return Unmodifiable collection of matching currents.
     */
    public List<TreeTarget> getTargets() {
    	return Collections.unmodifiableList(targets);
    }

    public void setTargets(List<TreeTarget> targets) {
        this.targets = targets;
    }
}
