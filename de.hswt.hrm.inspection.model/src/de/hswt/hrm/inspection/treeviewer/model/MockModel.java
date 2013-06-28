package de.hswt.hrm.inspection.treeviewer.model;

import java.util.ArrayList;
import java.util.List;

public class MockModel {

    public List<TreeTarget> getAssignedItems() {
        List<TreeTarget> mock = new ArrayList<>();

        TreeTarget target = new TreeTarget();
        target.setName("Target 1");
        mock.add(target);

        TreeCurrent current = new TreeCurrent();
        current.setName("Current 1");
        target.getCurrent().add(current);

        TreeActivity activity = new TreeActivity();
        activity.setName("Activity 1 ");
        current.getActivity().add(activity);

        target = new TreeTarget();
        target.setName("Target 2");
        mock.add(target);

        current = new TreeCurrent();
        current.setName("Current 2");
        target.getCurrent().add(current);

        activity = new TreeActivity();
        activity.setName("Activity 2 ");
        current.getActivity().add(activity);

        return mock;

    }

}
