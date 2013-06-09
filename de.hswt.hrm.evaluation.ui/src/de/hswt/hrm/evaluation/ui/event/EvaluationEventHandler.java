package de.hswt.hrm.evaluation.ui.event;

import java.util.Collection;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.evaluation.model.Evaluation;
import de.hswt.hrm.evaluation.service.EvaluationService;
import de.hswt.hrm.evaluation.ui.filter.EvaluationFilter;
import de.hswt.hrm.evaluation.ui.part.EvaluationPartUtil;

public class EvaluationEventHandler {

    private IEclipseContext context;
    private EvaluationService evalService;

    private final static String DEFAULT_SEARCH_STRING = "Search";
    private final static Logger LOG = LoggerFactory.getLogger(EvaluationEventHandler.class);

    public void leaveText(Event event) {

        Text text = (Text) event.widget;
        if (text.getText().isEmpty()) {
            text.setText(DEFAULT_SEARCH_STRING);
        }
        TableViewer tf = (TableViewer) XWT.findElementByName(text, "evaluationTable");
        tf.refresh();

    }

    public void onKeyUp(Event event) {
        Text searchText = (Text) event.widget;
        TableViewer tf = (TableViewer) XWT.findElementByName(searchText, "evaluationTable");
        EvaluationFilter f = (EvaluationFilter) tf.getFilters()[0];
        f.setSearchString(searchText.getText());
        tf.refresh();
    }

    public void onFocusOut(Event event) {
        Text text = (Text) event.widget;
        text.setText(DEFAULT_SEARCH_STRING);
        TableViewer tf = (TableViewer) XWT.findElementByName(text, "evaluationTable");
        tf.refresh();
    }

    @SuppressWarnings("unchecked")
    public void buttonSelected(Event event) {

        Button b = (Button) event.widget;
        Optional<Evaluation> newEvaluation = EvaluationPartUtil.showWizard(context,
                event.display.getActiveShell(), Optional.<Evaluation> absent());

        TableViewer tv = (TableViewer) XWT.findElementByName(b, "evaluationTable");

        Collection<Evaluation> evaluations = (Collection<Evaluation>) tv.getInput();
        if (newEvaluation.isPresent()) {
            evaluations.add(newEvaluation.get());
            tv.refresh();
        }
    }

    public void tableEntrySelected(Event event) {

        TableViewer tv = (TableViewer) XWT.findElementByName(event.widget, "evaluationTable");

        // obtain the place in the column where the doubleClick happend
        Evaluation selectedPlace = (Evaluation) tv.getElementAt(tv.getTable().getSelectionIndex());
        if (selectedPlace == null) {
            return;
        }

        // Refresh the selected place with values from the database
        try {
            evalService.refresh(selectedPlace);
            Optional<Evaluation> updatedEvaluation = EvaluationPartUtil.showWizard(context,
                    event.display.getActiveShell(), Optional.of(selectedPlace));

            if (updatedEvaluation.isPresent()) {
                tv.refresh();
            }
        }
        catch (DatabaseException e) {
            LOG.error("Could not retrieve the evaluations from database.", e);

            // TODO: übersetzen
            MessageDialog.openError(event.display.getActiveShell(), "Connection Error",
                    "Could not update selected evaluation from database.");
        }
    }

}
