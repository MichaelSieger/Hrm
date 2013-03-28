package de.hswt.hrm.plant;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

/**
 * This class is for early developing purposes only, and will
 * be removed in the migration to RCP.
 * 
 * @author Michael Sieger
 *
 */
public class MainFrame{
	
	private Shell shell;
	
	public MainFrame(){
		Display display = new Display();
		shell = new Shell();
		initUI();
		shell.pack();
		shell.setSize(400, 400);
		shell.open();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()){
				display.sleep();
			}
		}
		shell.dispose();
		display.dispose();
	}

	private void initUI(){
		
		Menu bar = createMenu();
		MenuItem dataItem = createDataItem(bar);
		Menu dataMenu = createDataMenu(bar, dataItem);
		createPlantItem(dataMenu);

		shell.setMenuBar(bar);
	}
	
	private MenuItem createPlantItem(Menu dataMenu){
		MenuItem newPlantItem = new MenuItem(dataMenu, SWT.NONE);
		newPlantItem.setText("Neue Anlage erstellen");
		newPlantItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				System.out.println("HAALLO");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		return newPlantItem;
	}
	
	private Menu createDataMenu(Menu m, MenuItem dataItem){
		Menu dataMenu = new Menu(m);
		dataItem.setMenu(dataMenu);
		return dataMenu;
	}
	
	private MenuItem createDataItem(Menu m){
		MenuItem dataItem = new MenuItem(m, SWT.CASCADE);
		dataItem.setText("Data");
		return dataItem;
	}
	
	private Menu createMenu(){
		Menu m = new Menu(shell, SWT.BAR);
		return m;
	}
}