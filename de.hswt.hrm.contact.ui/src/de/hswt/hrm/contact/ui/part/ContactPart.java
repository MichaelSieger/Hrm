package de.hswt.hrm.contact.ui.part;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

import de.hswt.hrm.common.database.exception.DatabaseException;
import de.hswt.hrm.common.ui.swt.table.ColumnComparator;
import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.common.ui.swt.table.TableViewerController;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.contact.service.ContactService;
import de.hswt.hrm.contact.ui.filter.ContactFilter;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.wb.swt.SWTResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.DoubleClickEvent;

public class ContactPart {

	private final static Logger LOG = LoggerFactory
			.getLogger(ContactPartPld.class);

	@Inject
	private ContactService contactService;

	@Inject
	private IShellProvider shellProvider;

	private IEclipseContext context;

	private static final String DEFAULT_SEARCH_STRING = "Search";

	private static final String EMPTY = "";

	private FormToolkit toolkit;
	private Table table;
	private Text searchText;
	private TableViewer tableViewer;

	private Color defaultForeGround;
	private Action editAction;
	private Action addAction;

	public ContactPart() {
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent, IEclipseContext context) {
		this.context = context;

		createToolkit();
		createActions();
		toolkit.setOrientation(SWT.LEFT_TO_RIGHT);
		toolkit.setBorderStyle(SWT.BORDER);
		toolkit.adapt(parent);
		toolkit.paintBordersFor(parent);
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));

		Form form = toolkit.createForm(parent);
		form.setSeparatorVisible(true);
		toolkit.paintBordersFor(form);
		form.setText("Contacts");
		FillLayout fillLayout = new FillLayout(SWT.HORIZONTAL);
		fillLayout.marginHeight = 8;
		fillLayout.marginWidth = 8;
		form.getBody().setLayout(fillLayout);
		toolkit.decorateFormHeading(form);

		tableViewer = new TableViewer(form.getBody(), SWT.BORDER
				| SWT.FULL_SELECTION);
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				editContact();
			}
		});
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		toolkit.paintBordersFor(table);

		searchText = new Text(form.getHead(), SWT.BORDER | SWT.H_SCROLL
				| SWT.ICON_SEARCH | SWT.SEARCH | SWT.ICON_CANCEL | SWT.CANCEL);
		defaultForeGround = searchText.getForeground();
		searchText.setText(DEFAULT_SEARCH_STRING);
		searchText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (DEFAULT_SEARCH_STRING.equals(searchText.getText())) {
					searchText.setText(EMPTY);
					searchText.setForeground(defaultForeGround);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (searchText.getText().isEmpty()) {
					searchText.setText(DEFAULT_SEARCH_STRING);
					searchText.setForeground(SWTResourceManager
							.getColor(SWT.COLOR_GRAY));
					updateTableFilter("");
				}
			}
		});
		searchText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateTableFilter(searchText.getText());
			}
		});
		form.setHeadClient(searchText);
		toolkit.adapt(searchText, true, true);
		searchText.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

		form.getToolBarManager().add(addAction);
		form.getToolBarManager().add(editAction);
		form.getToolBarManager().update(true);
		
		initializeTable();
		refreshTable(parent);
		
	}

	private void createToolkit() {
		FormColors result = new FormColors(Display.getCurrent());
		result.createColor(IFormColors.H_GRADIENT_START, new Color(Display.getCurrent(),
//				255, 244, 163).getRGB());
		255, 185, 49).getRGB());
		result.createColor(IFormColors.H_GRADIENT_END, new Color(Display.getCurrent(),
//				255, 247, 232).getRGB());
//		255, 236, 198).getRGB());
		255, 244, 163).getRGB());
		result.createColor(IFormColors.H_BOTTOM_KEYLINE1, new Color(Display.getCurrent(),
				255, 168, 0).getRGB());
		result.createColor(IFormColors.H_BOTTOM_KEYLINE2, new Color(Display.getCurrent(),
				255, 168, 0).getRGB());
		result.createColor(IFormColors.TITLE, SWTResourceManager
				.getColor(SWT.COLOR_BLACK).getRGB());
//		result.createColor(IFormColors.TITLE, new Color(Display.getCurrent(),
//				122, 184, 0).getRGB());
//				255, 168, 0).getRGB());
		toolkit = new FormToolkit(result);
	}

	private void createActions() {
		{
			editAction = new Action("Edit") {
				@Override
				public void run() {
					super.run();
					editContact();
				}
			};
			editAction.setDescription("Edit an exisitng contact.");
		}
		{
			addAction = new Action("Add") {
				@Override
				public void run() {
					super.run();
					addContact();
				}
			};
			addAction.setDescription("Add's a new contact.");
		}
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

	private void refreshTable(Composite parent) {
		try {
			tableViewer.setInput(contactService.findAll());
		} catch (DatabaseException e) {
			LOG.error("Unable to retrieve list of contacts.", e);
			showDBConnectionError();
		}
	}

	private void initializeTable() {
		List<ColumnDescription<Contact>> columns = ContactPartUtil.getColumns();

		// Create columns in tableviewer
		TableViewerController<Contact> filler = new TableViewerController<>(
				tableViewer);
		filler.createColumns(columns);

		// Enable column selection
		filler.createColumnSelectionMenu();

		// Enable sorting
		ColumnComparator<Contact> comperator = new ColumnComparator<>(columns);
		filler.enableSorting(comperator);

		// Add dataprovider that handles our collection
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());

		// Enable filtering
		tableViewer.addFilter(new ContactFilter());
	}

	private void showDBConnectionError() {
		MessageDialog.openError(shellProvider.getShell(), "Connection Error",
				"Could not load contacts from Database.");
	}

	private void updateTableFilter(String filterString) {
		ContactFilter filter = (ContactFilter) tableViewer.getFilters()[0];
		filter.setSearchString(filterString);
		tableViewer.refresh();
	}
	
	/**
	 * This Event is called whenever the add button is pressed.
	 * 
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	private void addContact() {
		Contact contact = null;

		Optional<Contact> newContact = ContactPartUtil.showWizard(context,
				shellProvider.getShell(), Optional.fromNullable(contact));

		Collection<Contact> contacs = (Collection<Contact>) tableViewer
				.getInput();
		if (newContact.isPresent()) {
			contacs.add(newContact.get());
			tableViewer.refresh();
		}
	}

	/**
	 * This method is called whenever a doubleClick onto the Tableviewer occurs.
	 * It obtains the contact from the selected column of the TableViewer. The
	 * Contact is passed to the ContactWizard. When the Wizard has finished, the
	 * contact will be updated in the Database
	 * 
	 * @param event
	 *            Event which occured within SWT
	 */
	private void editContact() {
		// obtain the contact in the column where the doubleClick happend
		Contact selectedContact = (Contact) tableViewer
				.getElementAt(tableViewer.getTable().getSelectionIndex());
		if (selectedContact == null) {
			return;
		}
		try {
			contactService.refresh(selectedContact);
			Optional<Contact> updatedPlace = ContactPartUtil.showWizard(
					context, shellProvider.getShell(),
					Optional.of(selectedContact));

			if (updatedPlace.isPresent()) {
				tableViewer.refresh();
			}
		} catch (DatabaseException e) {
			LOG.error("Could not retrieve the place from database.", e);
			showDBConnectionError();
		}
	}

}
