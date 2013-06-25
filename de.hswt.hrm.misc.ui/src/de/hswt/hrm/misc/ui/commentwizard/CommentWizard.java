package de.hswt.hrm.misc.ui.commentwizard;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.Wizard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.database.exception.SaveException;
import de.hswt.hrm.misc.comment.model.Comment;
import de.hswt.hrm.misc.comment.service.CommentService;

public class CommentWizard extends Wizard {

    private static final Logger LOG = LoggerFactory.getLogger(CommentWizard.class);

    @Inject
    private CommentService commentService;

    private CommentWizardPageOne first;
    private Optional<Comment> comment;

    public CommentWizard(IEclipseContext context, Optional<Comment> comment) {
        this.comment = comment;
        this.first = new CommentWizardPageOne("First Page", comment);
        ContextInjectionFactory.inject(first, context);

        if (comment.isPresent()) {
            setWindowTitle("Edit Comment: " + comment.get().getName());
        }
        else {
            setWindowTitle("Create new Comment");
        }
    }

    public void addPages() {
        addPage(first);
    }

    public boolean canFinish() {
        return first.isPageComplete();
    }

    @Override
    public boolean performFinish() {
        if (comment.isPresent()) {
            return editExistingComment();
        }
        return insertNewComment();
    }

    private boolean insertNewComment() {

        Comment c = new Comment(first.getName(), first.getDesc());
        //FIXME Enable CommentService
//        try {
//            this.comment = Optional.of(commentService.insert(c));
//        }
//        catch (SaveException e2) {
//            LOG.error("An eror occured", e2);
//            return false;
//        }

        return true;

    }

    private boolean editExistingComment() {
        Comment e = this.comment.get();
//FIXME enable CommmentService
//        try {
//            e = setValues(comment);
//            commentService.update(e);
//            comment = Optional.of(e);
//        }
//        catch (DatabaseException de) {
//            LOG.error("An error occured: ", de);
//            return false;
//        }

        return true;
    }

    private Comment setValues(Optional<Comment> e) {

        Comment comment = null;

        if (e.isPresent()) {
            comment = e.get();
            comment.setName(first.getName());
            comment.setText(first.getDesc());
        }

        return comment;
    }

    public Optional<Comment> getComment() {
        return comment;
    }

}
