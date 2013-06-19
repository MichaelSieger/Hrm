//package de.hswt.hrm.misc.ui.CommentWizard;
//
//import java.net.URL;
//import java.util.Collection;
//
//import javax.inject.Inject;
//
//import org.eclipse.e4.xwt.IConstants;
//import org.eclipse.e4.xwt.XWT;
//import org.eclipse.e4.xwt.forms.XWTForms;
//import org.eclipse.jface.wizard.WizardPage;
//import org.eclipse.swt.events.KeyEvent;
//import org.eclipse.swt.events.KeyListener;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Text;
//import org.eclipse.ui.forms.widgets.Section;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.google.common.base.Optional;
//
//import de.hswt.hrm.common.database.exception.DatabaseException;
//import de.hswt.hrm.common.ui.swt.forms.FormUtil;
//import de.hswt.hrm.common.ui.swt.layouts.PageContainerFillLayout;
//import de.hswt.hrm.evaluation.model.Evaluation;
//import de.hswt.hrm.evaluation.service.EvaluationService;
//
//public class CommentWizardPageOne extends WizardPage {
//
//    private Optional<Comment> comment;
//    private Composite container;
//    private Text nameText;
//    private Text descText;
//
//    private Collection<Comment> comments;
//    private boolean first = true;
//
//    @Inject
//    private CommentService commentService;
//
//    private static final Logger LOG = LoggerFactory.getLogger(CommentWizardPageOne.class);
//
//    public CommentWizardPageOne(String title, Optional<Comment> comment) {
//        super(title);
//        this.comment = comment;
//        setDescription(createDescription());
//        setTitle("Summary Wizard");
//    }
//
//    private String createDescription() {
//        if (comment.isPresent()) {
//            return "Change a Comment";
//        }
//        return "Add a new Comment";
//    }
//
//    @Override
//    public void createControl(Composite parent) {
//        parent.setLayout(new PageContainerFillLayout());
//        URL url = CommentWizardPageOne.class.getClassLoader().getResource(
//                "de/hswt/hrm/misc/ui/CommentWizardXWT/CommentWizardWindow"
//                        + IConstants.XWT_EXTENSION_SUFFIX);
//        try {
//            container = (Composite) XWTForms.load(parent, url);
//        }
//        catch (Exception e) {
//            LOG.error("An error occured: ", e);
//        }
//
//        nameText = (Text) XWT.findElementByName(container, "name");
//        descText = (Text) XWT.findElementByName(container, "desc");
//
//        if (this.comment.isPresent()) {
//            updateFields();
//        }
//        try {
//            this.comments = commentService.findAll();
//        }
//        catch (DatabaseException e) {
//            LOG.error("An error occured", e);
//        }
//
//        FormUtil.initSectionColors((Section) XWT.findElementByName(container, "Mandatory"));
//        addKeyListener(nameText);
//        addKeyListener(descText);
//        setControl(container);
//        setPageComplete(false);
//
//    }
//
//    private void addKeyListener(Text text) {
//        text.addKeyListener(new KeyListener() {
//
//            @Override
//            public void keyReleased(KeyEvent e) {
//                checkPageComplete();
//            }
//
//            @Override
//            public void keyPressed(KeyEvent e) {
//            }
//        });
//    }
//
//    private void updateFields() {
//        Comment e = comment.get();
//        nameText.setText(e.getName());
//        descText.setText(e.getText());
//    }
//
//    private void checkPageComplete() {
//
//        if (first) {
//            first = false;
//            setPageComplete(false);
//            return;
//        }
//
//        setErrorMessage(null);
//
//        if (descText.getText().isEmpty()) {
//            setErrorMessage("Description must not be empty");
//        }
//
//        else if (nameText.getText().isEmpty()) {
//            setErrorMessage("Name must not be empty...");
//        }
//
//        else if (isAlreadyPresent(nameText.getText())) {
//            setErrorMessage("A Component with name " + nameText.getText() + " is already present");
//        }
//
//    }
//
//    private boolean isAlreadyPresent(String text) {
//
//        boolean present = false;
//
//        if (text == null | text.isEmpty()) {
//            present = true;
//        }
//
//        for (Comment e : this.commments) {
//            if (e.getName().equals(text)) {
//                present = true;
//            }
//        }
//
//        return present;
//
//    }
//
//    @Override
//    public void setErrorMessage(String newMessage) {
//        if (newMessage == null || newMessage.isEmpty()) {
//            setPageComplete(true);
//        }
//        else {
//            setPageComplete(false);
//        }
//        super.setErrorMessage(newMessage);
//    }
//
//    public String getName() {
//        return nameText.getText();
//    }
//
//    public String getDesc() {
//        return descText.getText();
//    }
//
//}