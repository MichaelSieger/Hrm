package de.hswt.hrm.main.handlers;

import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;

import de.hswt.hrm.common.Hrm;

public class LifeCycleHandler {

    /*
     * This method is called before the Eclipse Context is created
     */
    @PostContextCreate
    public void init() {

        /*
         * Getting the Database Connection
         */
        Hrm.init();
    }

}
