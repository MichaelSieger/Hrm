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
import org.eclipse.ui.forms.widgets.Section;

import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.common.ui.swt.utils.SWTResourceManager;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.contact.ui.part.ContactComposite;
import de.hswt.hrm.i18n.I18n;
import de.hswt.hrm.i18n.I18nFactory;

public class ContactSelectionDialog extends TitleAreaDialog {

    private ContactComposite contactComposite;
    private IEclipseContext context;
    private Contact contact;
    private Button okButton;
    
    private static final I18n I18N = I18nFactory.getI18n(ContactSelectionDialog.class);
    
    public ContactSelectionDialog(Shell parentShell, IEclipseContext context) {
        super(parentShell);
        this.context = context;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);

        draw(composite); // Contents of Dialog
        
        setMessage(I18N.tr("Please select")+" "+I18N.tr("a contact")+".");
        setTitle(I18N.tr("Contact selection"));
        
        return composite;
    }
    
    @Override
    protected Control createButtonBar(Composite parent) {
        Control c = super.createButtonBar(parent);
        getButton(OK).setText(I18N.tr("OK"));
        getButton(CANCEL).setText(I18N.tr("Cancel"));
        return c;
    }

    private void draw(Composite parent) {
		Section headerSection = new Section(parent, Section.TITLE_BAR);
		headerSection.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		headerSection.setText(I18N.tr("Contacts"));
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