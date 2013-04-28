package de.hswt.hrm.plant;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import de.hswt.hrm.plant.ui.schemeeditor.SchemeBuilderFrame;

/**
 * This class is for early developing purposes only, and will be removed in the migration to RCP.
 * 
 * @author Michael Sieger
 * 
 */
public class MainFrame {

    private Shell shell;
    private Display display;

    public MainFrame() {
        display = new Display();
        shell = new Shell();
        initUI();
        shell.setSize(400, 400);
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        shell.dispose();
        display.dispose();
    }

    private void initUI() {

        Menu bar = createMenu();
        MenuItem dataItem = createDataItem(bar);
        Menu dataMenu = createDataMenu(bar, dataItem);
        createBuilderItem(dataMenu);
        createNewPlantItem(dataMenu);
        createShowPlantsItem(dataMenu);
        shell.setMenuBar(bar);

        shell.setLayout(new FillLayout());
    }

    private MenuItem createShowPlantsItem(Menu dataMenu) {
        MenuItem mItem = new MenuItem(dataMenu, SWT.NONE);
        mItem.setText("Anlagen anzeigen");
        mItem.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                // TODO start gui
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }
        });
        return mItem;
    }

    private MenuItem createNewPlantItem(Menu dataMenu) {
        MenuItem mItem = new MenuItem(dataMenu, SWT.NONE);
        mItem.setText("Neue Anlage");
        mItem.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                // TODO start gui
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }
        });
        return mItem;
    }

    private MenuItem createBuilderItem(Menu dataMenu) {
        MenuItem mItem = new MenuItem(dataMenu, SWT.NONE);
        mItem.setText("Neues Anlagenschema erstellen");
        mItem.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                SchemeBuilderFrame frame = new SchemeBuilderFrame(shell, SWT.NONE);
                shell.layout();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }
        });
        return mItem;
    }

    private Menu createDataMenu(Menu m, MenuItem dataItem) {
        Menu dataMenu = new Menu(m);
        dataItem.setMenu(dataMenu);
        return dataMenu;
    }

    private MenuItem createDataItem(Menu m) {
        MenuItem dataItem = new MenuItem(m, SWT.CASCADE);
        dataItem.setText("Data");
        return dataItem;
    }

    private Menu createMenu() {
        Menu m = new Menu(shell, SWT.BAR);
        return m;
    }
}