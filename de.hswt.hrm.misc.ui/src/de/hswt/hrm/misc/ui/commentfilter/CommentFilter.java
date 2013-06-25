package de.hswt.hrm.misc.ui.commentfilter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import de.hswt.hrm.misc.comment.model.Comment;
import de.hswt.hrm.summary.model.Summary;

public class CommentFilter extends ViewerFilter {

    private String searchString;

    public void setSearchString(String substring) {
        searchString = (".*" + substring + ".*").toLowerCase();
    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        if (searchString == null || searchString.length() == 0) {
            return true;
        }

        Comment e = (Comment) element;

        if (e.getName().toLowerCase().matches(searchString)) {
            return true;
        }

        if (e.getText().toLowerCase().matches(searchString)) {
            return true;
        }

        return false;
    }

}
