package de.hswt.hrm.misc.ui.commentwizard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import com.google.common.base.Optional;

import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.common.ui.swt.wizards.WizardCreator;
import de.hswt.hrm.misc.comment.model.Comment;

public class CommentPartUtil {

	public static Optional<Comment> showWizard(IEclipseContext context,
			Shell shell, Optional<Comment> comment) {

		CommentWizard ew = new CommentWizard(context,comment);
		ContextInjectionFactory.inject(ew, context);

		WizardDialog wd = WizardCreator.createWizardDialog(shell, ew);
		wd.open();
		return ew.getComment();

	}

	public static List<ColumnDescription<Comment>> getColumns() {
		List<ColumnDescription<Comment>> columns = new ArrayList<>();

		columns.add(getNameColumn());
		columns.add(getTextColumn());

		return columns;
	}

	private static ColumnDescription<Comment> getNameColumn() {
		return new ColumnDescription<>("Name", new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Comment e = (Comment) element;
				return e.getName();
			}
		}, new Comparator<Comment>() {
			@Override
			public int compare(Comment e1, Comment e2) {
				return e1.getName().compareToIgnoreCase(e2.getName());
			}
		});
	}

	private static ColumnDescription<Comment> getTextColumn() {
		return new ColumnDescription<>("Text", new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Comment e = (Comment) element;
				return e.getText();
			}
		}, new Comparator<Comment>() {
			@Override
			public int compare(Comment e1, Comment e2) {
				return e1.getText().compareToIgnoreCase(e2.getText());
			}
		});
	}
}
