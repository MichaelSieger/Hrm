package de.hswt.hrm.main.ui.handler;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import de.hswt.hrm.main.ui.ViewSwitcher;

public class PartSwitchHandler {

	@Inject
	EPartService service;

	@Inject
	EModelService modelService;
	
	@Execute
	public void execute(@Named("de.hswt.hrm.main.switchpart.idinput") String id) {
		ViewSwitcher.setPartVisible(id);
	}
	
	@CanExecute
	public boolean canExecute() {
//		System.out.println("inside can execute");
		return true;
	}
}
