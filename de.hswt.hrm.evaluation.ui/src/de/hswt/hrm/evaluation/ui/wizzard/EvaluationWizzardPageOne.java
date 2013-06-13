package de.hswt.hrm.evaluation.ui.wizzard;

import java.net.URL;
import java.util.Collection;

import org.eclipse.e4.xwt.IConstants;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.e4.xwt.forms.XWTForms;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import de.hswt.hrm.common.ui.swt.layouts.PageContainerFillLayout;
import de.hswt.hrm.evaluation.model.Evaluation;

public class EvaluationWizzardPageOne extends WizardPage {

	private Optional<Evaluation> eval;
	private Composite container;
	private Text nameText;
	private Text descText;
	private Collection<Evaluation> evaluations;

	private static final Logger LOG = LoggerFactory
			.getLogger(EvaluationWizzardPageOne.class);

	public EvaluationWizzardPageOne(String title, Optional<Evaluation> eval) {
		super(title);
		this.eval = eval;
		this.evaluations = evaluations;
		setDescription(createDescription());

	}

	private String createDescription() {
		if (eval.isPresent()) {
			return "Change an Evaluation";
		}
		return "Add a new Evaluation";
	}

	@Override
	public void createControl(Composite parent) {
		parent.setLayout(new PageContainerFillLayout());
		URL url = EvaluationWizzardPageOne.class.getClassLoader().getResource(
				"de/hswt/hrm/evaluation/ui/xwt/EvaluationWizardWindow"
						+ IConstants.XWT_EXTENSION_SUFFIX);
		try {
			container = (Composite) XWTForms.load(parent, url);
		} catch (Exception e) {
			LOG.error("An error occured: ", e);
		}

		nameText = (Text) XWT.findElementByName(container, "name");
		descText = (Text) XWT.findElementByName(container, "desc");

		if (this.eval.isPresent()) {
			updateFields();
		}

		nameText.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				checkPageComplete();
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

	}

	private void updateFields() {
		Evaluation e = eval.get();
		nameText.setText(e.getText());
		descText.setText(e.getText());
	}

	private void checkPageComplete() {

		setErrorMessage(null);
		if (isAlreadyPresent(nameText.getText())) {
			setErrorMessage("An Evaluation with name " + nameText.getText()
					+ "is already present");
		}

	}

	private boolean isAlreadyPresent(String text) {

		boolean present = false;

		if (text == null | text.isEmpty()) {
			present = true;
		}
		for (Evaluation e : evaluations) {
			if (e.getName().equals(text)) {
				present = true;
			}
		}
		return present;

	}

	@Override
	public void setErrorMessage(String newMessage) {
		if (newMessage == null || newMessage.isEmpty()) {
			setPageComplete(true);
		} else {
			setPageComplete(false);
		}
		super.setErrorMessage(newMessage);
	}

}
