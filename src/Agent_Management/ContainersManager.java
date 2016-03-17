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
import static jade.core.Profile.CONTAINER_NAME;
import jade.core.ProfileImpl;
import jade.wrapper.StaleProxyException;

/**
 *
 * @author Matthew
 */
public class ContainersManager extends Agent{


    private static final ContainersManager INSTANCE = new ContainersManager();
    ContainerController mainContainer;
    ContainerController buyerContainer;
    ContainerController sellerContainer;
    
    public ContainersManager(){
    }
    
        public static ContainersManager getInstance() {
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
    
    public void createBuyerContainer(){
        Runtime myRuntime = Runtime.instance();
        Profile myProfile = new ProfileImpl();
        myProfile.setParameter(CONTAINER_NAME, "BuyerContainer");
         buyerContainer = myRuntime.createAgentContainer(myProfile);
    }
    
    public ContainerController getBuyerContainer(){
        return buyerContainer;
    }
    public boolean buyerContainerExists(){
        if(buyerContainer == null){
            return false;
        }
        else 
            return true;
    }
    
    public void createSellerContainer(){
        Runtime myRuntime = Runtime.instance();
        Profile myProfile = new ProfileImpl();
        myProfile.setParameter(CONTAINER_NAME, "SellerContainer");
         sellerContainer = myRuntime.createAgentContainer(myProfile);
    }
    
    public ContainerController getSellerContainer(){
        return sellerContainer;
    }
    
    public boolean sellerContainerExists(){
        if(sellerContainer == null){
            return false;
        }
        else 
            return true;
    }
}
