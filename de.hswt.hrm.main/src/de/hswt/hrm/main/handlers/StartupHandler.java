package de.hswt.hrm.main.handlers;

import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;

import de.hswt.hrm.common.Hrm;

public class StartupHandler {

    /*
     * This method is called before the Eclipse Context is created and therefore before the GUI is
     * started. 
     */
    @PostContextCreate
    public void init() {

       /*
        * Getting the Database Connection
        */
        Hrm.init();
    }

}
