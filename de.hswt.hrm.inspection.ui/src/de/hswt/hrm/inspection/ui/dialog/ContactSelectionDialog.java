package de.hswt.hrm.inspection.ui.dialog;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.contact.ui.part.ContactComposite;

public class ContactSelectionDialog extends TitleAreaDialog {

    private ContactComposite contactComposite;
    private IEclipseContext context;
    private Contact contact;
    private Button okButton;
	private FormToolkit toolkit;
	private Form form;
    
    public ContactSelectionDialog(Shell parentShell, IEclipseContext context) {
        super(parentShell);
        this.context = context;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);

        draw(composite); // Contents of Dialog
        
        setMessage("Please select a contact");
        
        return composite;

    }

    private void draw(Composite parent) {
		toolkit = FormUtil.createToolkit();
		toolkit.adapt(parent);
		toolkit.paintBordersFor(parent);

		form = toolkit.createForm(parent);
		form.getHead().setOrientation(SWT.RIGHT_TO_LEFT);
		form.setSeparatorVisible(true);
		toolkit.paintBordersFor(form);
		form.setText("Contacts");
		toolkit.decorateFormHeading(form);

		FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
		fillLayout.marginHeight = 1;
		form.getBody().setLayout(fillLayout);

		Section headerSection = toolkit
				.createSection(form.getBody(), Section.TITLE_BAR);
		toolkit.paintBordersFor(headerSection);
		headerSection.setExpanded(true);
		FormUtil.initSectionColors(headerSection);
		headerSection.setLayout(new FillLayout());
    	
        contactComposite = new ContactComposite(headerSection);
        ContextInjectionFactory.inject(contactComposite, context);
        contactComposite.setAllowEditing(false);
        contactComposite.setBackgroundMode(SWT.INHERIT_NONE);
        contactComposite.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				validate();
			}
		});
        headerSection.setClient(contactComposite);
    }
    
    private void validate() {
        contact = contactComposite.getSelectedContact();
        if (contact != null) {
        	okButton.setEnabled(true);
        } else {
        	okButton.setEnabled(false);
        }
    }
    
    public Contact getContact() {
        return contact;
    }
    
    @Override
    public boolean close() {
    	if (contactComposite != null) {
    		contactComposite.dispose();
    	}
    	return super.close();
    }
    
    @Override
    protected void createButtonsForButtonBar(Composite parent) {
    	super.createButtonsForButtonBar(parent);
        okButton = getButton(IDialogConstants.OK_ID);
        okButton.setEnabled(false);
    }
}