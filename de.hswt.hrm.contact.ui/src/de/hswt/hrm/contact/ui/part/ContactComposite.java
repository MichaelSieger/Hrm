package de.hswt.hrm.contact.ui.part;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Text;

public class ContactComposite extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Table table;
	private Text text;

	private TableViewer tableViewer;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ContactComposite(Composite parent, int style) {
		super(parent, SWT.NONE);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Form form = toolkit.createForm(this);
		form.setSeparatorVisible(true);
		toolkit.paintBordersFor(form);
		form.setText("Contacts");
		form.getBody().setLayout(new FillLayout(SWT.HORIZONTAL));
		toolkit.decorateFormHeading(form);
		
		tableViewer = new TableViewer(form.getBody(), SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		toolkit.paintBordersFor(table);
		
		text = new Text(form.getHead(), SWT.BORDER | SWT.H_SCROLL | SWT.SEARCH | SWT.CANCEL);
		form.setHeadClient(text);
		toolkit.adapt(text, true, true);

	}

	public TableViewer getTableViewer() {
		return tableViewer;
	}
}
