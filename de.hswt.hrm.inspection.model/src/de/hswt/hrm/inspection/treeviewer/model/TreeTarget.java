package de.hswt.hrm.inspection.treeviewer.model;

import java.util.ArrayList;
import java.util.List;

public class TreeTarget {

    private String name;
    private List<TreeCurrent> current;

    public TreeTarget() {
        this.current = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<TreeCurrent> getCurrent() {
        return current;
    }

    public void setTarget(List<TreeCurrent> current) {
        this.current = current;
    }

    public void setName(String name) {
        this.name = name;
    }

}
