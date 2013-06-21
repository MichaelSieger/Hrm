package de.hswt.hrm.contact.ui.part;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.SWT;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Form;

import de.hswt.hrm.common.ui.swt.forms.FormUtil;
import de.hswt.hrm.contact.service.ContactService;
import de.hswt.hrm.i18n.I18n;
import de.hswt.hrm.i18n.I18nFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.ui.forms.widgets.Section;

public class ContactPart {

	private final static Logger LOG = LoggerFactory
			.getLogger(ContactPart.class);
	
	private final static I18n I18N = I18nFactory.getI18n(ContactPart.class);

	@Inject
	private ContactService contactService;

	@Inject
	private IShellProvider shellProvider;

	@Inject
	private IEclipseContext context;

	private FormToolkit toolkit = new FormToolkit(Display.getDefault());

	private Action editAction;
	private Action addAction;
	private Section headerSection;
	private ContactComposite contactComposite;

	private Form form;

	public ContactPart() {
		// toolkit can be created in PostConstruct, but then then
		// WindowBuilder is unable to parse the code
		toolkit.dispose();
		toolkit = FormUtil.createToolkit();
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {

		toolkit.setBorderStyle(SWT.BORDER);
		toolkit.adapt(parent);
		toolkit.paintBordersFor(parent);
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));

		form = toolkit.createForm(parent);
		form.getHead().setOrientation(SWT.RIGHT_TO_LEFT);
		form.setSeparatorVisible(true);
		toolkit.paintBordersFor(form);
		form.setText(I18N.tr("Contacts"));
		toolkit.decorateFormHeading(form);

		FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
		fillLayout.marginHeight = 1;
		form.getBody().setLayout(fillLayout);

		headerSection = toolkit
				.createSection(form.getBody(), Section.TITLE_BAR);
		toolkit.paintBordersFor(headerSection);
		headerSection.setExpanded(true);
		FormUtil.initSectionColors(headerSection);
		headerSection.setLayout(new FillLayout());

		contactComposite = new ContactComposite(headerSection);
		ContextInjectionFactory.inject(contactComposite, context);
		headerSection.setClient(contactComposite);

		if (contactService == null) {
			LOG.error("ContactService not injected to ContactPart.");
		}
		
		createActions();
	}

	private void createActions() {
	    //TODO translate
		editAction = new Action(I18N.tr("Edit")) {
			@Override
			public void run() {
				super.run();
				contactComposite.editContact();
			}
		};
		editAction.setDescription(I18N.tr("Edit an existing contact."));
		
		addAction = new Action(I18N.tr("Add")) {
			@Override
			public void run() {
				super.run();
				contactComposite.addContact();
			}
		};
		addAction.setDescription(I18N.tr("Add a new contact."));
		
		form.getToolBarManager().add(editAction);
		form.getToolBarManager().add(addAction);
		form.getToolBarManager().update(true);
	}

	@PreDestroy
	public void dispose() {
		if (toolkit != null) {
			toolkit.dispose();
		}
	}

	@Focus
	public void setFocus() {
	}
}
