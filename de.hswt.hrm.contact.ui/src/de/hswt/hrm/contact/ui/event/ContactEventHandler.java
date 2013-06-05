package de.hswt.hrm.contact.ui.event;

import java.util.Collection;

import javax.inject.Inject;

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
import de.hswt.hrm.contact.model.Contact;
import de.hswt.hrm.contact.service.ContactService;
import de.hswt.hrm.contact.ui.filter.ContactFilter;
import de.hswt.hrm.contact.ui.part.ContactPartUtil;

public class ContactEventHandler {
    private final static Logger LOG = LoggerFactory.getLogger(ContactEventHandler.class);
    private static final String DEFAULT_SEARCH_STRING = "Suche";
    private static final String EMPTY = "";
    private Contact contact;

    private final IEclipseContext context;
    private final ContactService contactService;

    @Inject
    public ContactEventHandler(IEclipseContext context, ContactService contactService) {

        if (context == null) {
            LOG.error("EclipseContext was not injected to PlaceEventHandler.");
        }

        if (contactService == null) {
            LOG.error("PlaceService was not injected to PlaceEventHandler.");
        }

        this.context = context;
        this.contactService = contactService;

    }
}
