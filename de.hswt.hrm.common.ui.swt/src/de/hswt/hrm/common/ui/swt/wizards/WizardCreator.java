package de.hswt.hrm.common.ui.swt.wizards;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.ProgressMonitorPart;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class WizardCreator {

	public static final WizardDialog createWizardDialog(Shell shell, IWizard wizard) {
		return new WizardDialog(shell, wizard)  {
            
            @Override
            protected Control createDialogArea(Composite parent) {
                Control ctrl = super.createDialogArea(parent);
                getProgressMonitor();
                return ctrl;
            }
           
            @Override
            protected IProgressMonitor getProgressMonitor() {
                ProgressMonitorPart monitor = (ProgressMonitorPart) super.getProgressMonitor();
                GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
                gridData.heightHint = 0;
                monitor.setLayoutData(gridData);
                monitor.setVisible(false);
                return monitor;
            }
        }; 
	}
}
