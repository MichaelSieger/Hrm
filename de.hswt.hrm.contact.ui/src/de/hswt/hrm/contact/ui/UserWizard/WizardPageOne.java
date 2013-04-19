package de.hswt.hrm.contact.ui.UserWizard;

import java.net.URL;
import java.util.HashMap;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class WizardPageOne extends WizardPage{
    private Composite container;
    UserWizardWindow window;


    protected WizardPageOne(String pageName) {
        super(pageName);
    }

    @Override
    public void createControl(Composite parent) {
        window =  new UserWizardWindow(parent, 0);      
        container = window;
        setKeyListener();       
        setControl(container);
        setPageComplete(false);
        }

    public HashMap getWidgets(){        
        return window.getWidgets();
    }
    
    public void setKeyListener(){
        HashMap widgets = window.getWidgets();
        for(Object object : widgets.values()){
        
            ((Control) object).addKeyListener(new KeyListener(){
    
                @Override
                public void keyPressed(KeyEvent e) {             
                }
    
                @Override
                public void keyReleased(KeyEvent e) {
                    getWizard().getContainer().updateButtons();                     
                }                
            });        
        }
    }        
}