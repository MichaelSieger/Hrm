package de.hswt.hrm.inspection.treeviewer.model;

import java.util.ArrayList;
import java.util.List;

public class TreeCurrent {

    private String name;
    private List<TreeActivity> activity;

    public TreeCurrent() {
        this.activity = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeActivity> getActivity() {
        return activity;
    }

    public void setActivity(List<TreeActivity> activity) {
        this.activity = activity;
    }

}
