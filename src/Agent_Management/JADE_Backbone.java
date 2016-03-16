/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agent_Management;

import jade.core.Agent;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.StaleProxyException;

/**
 *
 * @author Matthew
 */
public class JADE_Backbone extends Agent{


    private static final JADE_Backbone INSTANCE = new JADE_Backbone();
    ContainerController mainContainer;
    
    public JADE_Backbone(){
    }
    
        public static JADE_Backbone getInstance() {
        return INSTANCE;
    }
        public void startRMA(){
            Runtime myRuntime = Runtime.instance();
            Profile myProfile = new ProfileImpl();
            mainContainer = myRuntime.createMainContainer(myProfile);
            // .. load a container into the above variable ..

            try {
                AgentController rma = mainContainer.createNewAgent("rma", "jade.tools.rma.rma", null);
                rma.start();
                } catch(StaleProxyException e) {
                    }
        }
    /**
     *
     * @return 
     */
    public ContainerController getMainContainer(){
        return mainContainer;
    }

}
