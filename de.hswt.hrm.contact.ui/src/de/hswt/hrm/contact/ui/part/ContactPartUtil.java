package de.hswt.hrm.contact.ui.part;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.e4.xwt.XWT;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.google.common.base.Optional;

import de.hswt.hrm.common.ui.swt.table.ColumnDescription;
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.contact.ui.wizard.ContactWizard;
import de.hswt.hrm.i18n.I18n;
import de.hswt.hrm.i18n.I18nFactory;

public final class ContactPartUtil {
	private static final I18n I18N = I18nFactory.getI18n(ContactPartUtil.class);
	
    private ContactPartUtil() {

    }

    public static Optional<Contact> showWizard(IEclipseContext context, Shell shell,
            Optional<Contact> contact) {

        ContactWizard cw = new ContactWizard(contact);
        ContextInjectionFactory.inject(cw, context);

        WizardDialog wd = new WizardDialog(shell, cw);
        wd.open();
        return cw.getContact();
    }

    public static List<ColumnDescription<Contact>> getColumns() {
        List<ColumnDescription<Contact>> columns = new ArrayList<>();
        columns.add(getLastNameColumn());
        columns.add(getFirstNameColumn());
        columns.add(getStreetColumn());
        columns.add(getStreetNoColumn());
        columns.add(getPostCodeColumn());
        columns.add(getCityColumn());
        columns.add(getShortcutColumn());
        columns.add(getPhoneColumn());
        columns.add(getFaxColumn());
        columns.add(getMobileColumn());
        columns.add(getEmailColumn());

        return columns;

    }

    private static ColumnDescription<Contact> getLastNameColumn() {
        return new ColumnDescription<>("Last Name", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Contact c = (Contact) element;
                return c.getLastName();
            }
        }, new Comparator<Contact>() {
            @Override
            public int compare(Contact c1, Contact c2) {
                return c1.getLastName().compareToIgnoreCase(c2.getLastName());
            }
        });
    }

    private static ColumnDescription<Contact> getFirstNameColumn() {
        return new ColumnDescription<>("First Name", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Contact c = (Contact) element;
                return c.getFirstName();
            }
        }, new Comparator<Contact>() {
            @Override
            public int compare(Contact c1, Contact c2) {
                return c1.getFirstName().compareToIgnoreCase(c2.getFirstName());
            }
        });
    }

    private static ColumnDescription<Contact> getStreetColumn() {
        return new ColumnDescription<>("Street", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Contact c = (Contact) element;
                return c.getStreet();
            }
        }, new Comparator<Contact>() {
            @Override
            public int compare(Contact c1, Contact c2) {
                return c1.getStreet().compareTo(c2.getStreet());
            }
        });
    }

    private static ColumnDescription<Contact> getStreetNoColumn() {
        return new ColumnDescription<>("Street No.", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Contact c = (Contact) element;
                return c.getStreetNo();
            }
        }, new Comparator<Contact>() {
            @Override
            public int compare(Contact c1, Contact c2) {
                return c1.getStreetNo().compareToIgnoreCase(c2.getStreetNo());
            }
        });
    }

    private static ColumnDescription<Contact> getPostCodeColumn() {
        return new ColumnDescription<>("Post Code", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Contact c = (Contact) element;
                return c.getPostCode();
            }
        }, new Comparator<Contact>() {
            @Override
            public int compare(Contact c1, Contact c2) {
                return c1.getPostCode().compareToIgnoreCase(c2.getPostCode());
            }
        });
    }

    private static ColumnDescription<Contact> getCityColumn() {
        return new ColumnDescription<>("City", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Contact c = (Contact) element;
                return c.getCity();
            }
        }, new Comparator<Contact>() {
            @Override
            public int compare(Contact c1, Contact c2) {
                return c1.getCity().compareToIgnoreCase(c2.getCity());
            }
        });
    }

    private static ColumnDescription<Contact> getShortcutColumn() {
        return new ColumnDescription<>("Shortcut", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Contact c = (Contact) element;
                return c.getShortcut().get();
            }
        }, new Comparator<Contact>() {
            @Override
            public int compare(Contact c1, Contact c2) {
                return c1.getShortcut().get().compareToIgnoreCase(c2.getShortcut().get());
            }
        });
    }

    private static ColumnDescription<Contact> getPhoneColumn() {
        return new ColumnDescription<>("Phone No.", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Contact c = (Contact) element;
                return c.getPhone().get();
            }
        }, new Comparator<Contact>() {
            @Override
            public int compare(Contact c1, Contact c2) {
                return c1.getPhone().get().compareToIgnoreCase(c2.getPhone().get());
            }
        });
    }

    private static ColumnDescription<Contact> getFaxColumn() {
        return new ColumnDescription<>("Fax No.", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Contact c = (Contact) element;
                return c.getFax().get();
            }
        }, new Comparator<Contact>() {
            @Override
            public int compare(Contact c1, Contact c2) {
                return c1.getFax().get().compareToIgnoreCase(c2.getFax().get());
            }
        });
    }

    private static ColumnDescription<Contact> getMobileColumn() {
        return new ColumnDescription<>("Mobile No.", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Contact c = (Contact) element;
                return c.getMobile().get();
            }
        }, new Comparator<Contact>() {
            @Override
            public int compare(Contact c1, Contact c2) {
                return c1.getMobile().get().compareToIgnoreCase(c2.getMobile().get());
            }
        });
    }

    private static ColumnDescription<Contact> getEmailColumn() {
        return new ColumnDescription<>("E-Mail", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
                Contact c = (Contact) element;
                return c.getEmail().get();
            }
        }, new Comparator<Contact>() {
            @Override
            public int compare(Contact c1, Contact c2) {
                return c1.getEmail().get().compareToIgnoreCase(c2.getEmail().get());
            }
        });
    }
    
    public static void setListenerForToolbar(final EPartService service, ToolBar bar){    	
    	((ToolItem) XWT.findElementByName(bar, "toPlaces")).addListener(SWT.Selection, new Listener() {			
			@Override
			public void handleEvent(Event event) {
				setPartsVisibility(service, false ,true,false,false,false,false,false);
			}
		});
    	((ToolItem) XWT.findElementByName(bar, "toPlants")).addListener(SWT.Selection, new Listener() {			
			@Override
			public void handleEvent(Event event) {
				setPartsVisibility(service, false ,false,true,false,false,false,false);
			}
		});
    	((ToolItem) XWT.findElementByName(bar, "toCatalog")).addListener(SWT.Selection, new Listener() {			
			@Override
			public void handleEvent(Event event) {
				setPartsVisibility(service, false ,false,false,false,true,false,false);
			}
		});
    	((ToolItem) XWT.findElementByName(bar, "toCategory")).addListener(SWT.Selection, new Listener() {			
			@Override
			public void handleEvent(Event event) {
				setPartsVisibility(service, false ,false,false,false,false,true,false);
			}
		});
    	((ToolItem) XWT.findElementByName(bar, "toMain")).addListener(SWT.Selection, new Listener() {			
			@Override
			public void handleEvent(Event event) {
				setPartsVisibility(service, false ,false,false,false,false,false,true);
			}
		});
    	
    	
    }
    
   private static void setPartsVisibility(EPartService service , boolean clients, boolean places, boolean plants, boolean scheme, boolean catalog, boolean category, boolean main){
		service.findPart("Clients").setVisible(clients);
		service.findPart("Places").setVisible(places);
		service.findPart("Plants").setVisible(plants);
		service.findPart("Scheme").setVisible(scheme);
		service.findPart("Main").setVisible(main);
		service.findPart("Category").setVisible(category);
		service.findPart("Catalog").setVisible(catalog);  
		if(catalog)
			service.showPart("Catalog", PartState.VISIBLE);
		if(category)
			service.showPart("Category", PartState.VISIBLE);
    }

}
