/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agent_Management;

import GUI.SellerItemsGui;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import static jade.tools.sniffer.Message.step;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SellerAgent extends Agent {
    
        private static final SellerAgent INSTANCE = new SellerAgent();
	// The catalogueSeller of books for sale (maps the title of a book to its price)
	private HashMap catalogueSeller;
        private ArrayList<String> parameters;
	// The GUI by means of which the user can add books in the catalogueSeller
	private SellerItemsGui myGui;
        // The amount that will be substracted from the price if the proposal is not accepted
        private String loweringNumberS;
        private int loweringNumberI;
        private String newPriceString;
        private int newPriceInt;
        private String minimalPriceS;
        private int minimalPriceI;
        public SellerAgent myAgent;
        private int valueStart;
        private int valueEnd;
        private MessageTemplate infoMT;
        private Random randomGenerator;

        
	// Put agent initializations here
        @Override
	protected void setup() {
		// Create the catalogueSeller
		catalogueSeller = new HashMap();
                //get the value that will be used to lower the price
                Object[] args= getArguments();
		// Create and show the GUI 
		myGui = new SellerItemsGui("Create new Iem", 500, 300, this);
		//myGui.showGui();

		// Register the book-selling service in the yellow pages
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("book-selling");
		sd.setName("JADE-book-trading");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
		}

		// Add the behaviour serving queries from buyer agents
		addBehaviour(new OfferRequestsServer());

		// Add the behaviour serving purchase orders from buyer agents
		addBehaviour(new PurchaseOrdersServer());
	}

	// Put agent clean-up operations here
        @Override
	protected void takeDown() {
		// Deregister from the yellow pages
		try {
			DFService.deregister(this);
		}
		catch (FIPAException fe) {
		}
		// Close the GUI
		//myGui.dispose();
		// Printout a dismissal message
		System.out.println("Seller-agent "+getAID().getName().replace("@192.168.0.11:1099/JADE", " ")+" terminating.");
	}

	/**
     This is invoked by the GUI when the user adds a new book for sale
     * @param title
     * @param parameters
	 */
	public void updateCatalogue(final String title, final ArrayList<String> parameters) {
		addBehaviour(new OneShotBehaviour() {
                        @Override
			public void action() {
				catalogueSeller.put(title, parameters);
				System.out.println(title+" inserted into catalogue by " + getAID().getName().replace("@192.168.0.11:1099/JADE", " ") + " Utility = "+parameters.get(0) + " Current Price = "+ parameters.get(1));
			}
		} );
	}

	/**
	   Inner class OfferRequestsServer.
	   This is the behaviour used by Book-seller agents to serve incoming requests 
	   for offer from buyer agents.
	   If the requested book is in the local catalogueSeller the seller agent replies 
	   with a PROPOSE message specifying the price. Otherwise a REFUSE message is
	   sent back.
	 */
	private class OfferRequestsServer extends CyclicBehaviour {
            private int stepInfo = 0;
                @Override
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				// CFP Message received. Process it
				String title = msg.getContent();
				ACLMessage reply = msg.createReply();

                                //if(catalogueSeller.get(title) != null){
                                ArrayList parameters =  (ArrayList) catalogueSeller.get(title);
                                System.out.println(catalogueSeller.get(title));
                                Integer price = Integer.valueOf(parameters.get(1).toString());
//				String parametersString = catalogueSeller.get(title).toString();
//                                valueStart = parametersString.indexOf("$v");
//                                valueEnd = parametersString.indexOf("v$");
//                                String newparam = parametersString.substring(valueStart + 2, valueEnd);
//                                Integer price = Integer.valueOf(newparam);
                                minimalPriceS = parameters.get(3).toString();
                                minimalPriceI = Integer.valueOf(minimalPriceS);
                                if(catalogueSeller.get(title) != null && price >= minimalPriceI){
//				if (price != null) {
					// The requested book is available for sale. Reply with the price
					reply.setPerformative(ACLMessage.PROPOSE);
					reply.setContent(String.valueOf(price));
                                        
                                        loweringNumberS = parameters.get(2).toString();
                                        if(!"0".equals(loweringNumberS)){
                                            loweringNumberI = Integer.valueOf(loweringNumberS);
                                            catalogueSeller.remove(title);
                                            newPriceInt = price - loweringNumberI;
                                            newPriceString = String.valueOf(newPriceInt);
                                            parameters.set(1, newPriceString);
//                                        parameters.set(1, "$v" + newPriceString + "v$");
                                            catalogueSeller.put(title, parameters);
                                            price = newPriceInt;
                                            System.out.println("The price of the book " + title + " selling by " + getAID().getName().replace("@192.168.0.11:1099/JADE", " ") + "has been lowered by "+ loweringNumberI +" to a new price which is " + price);
                                        }
                                           ACLMessage info = new ACLMessage(ACLMessage.REQUEST);
                            if (parameters.get(4) == "Always ask") {   
//                                
                                switch (stepInfo){
                                    case 0:
                                
                                info.addReceiver(msg.getSender());
                                info.setContent("What is your max value?");
                                info.setConversationId("requesting-info");
                                info.setReplyWith("info" + System.currentTimeMillis());
                                myAgent.send(info);
                                infoMT = MessageTemplate.and(MessageTemplate.MatchConversationId("requesting-info"), MessageTemplate.MatchInReplyTo(info.getReplyWith()));
                               stepInfo = 1;
                                          
                                           
                               break;
                                   case 1:


                                    ACLMessage replyInfo = myAgent.receive(infoMT);
                                    if (replyInfo != null){
                                    int information = Integer.valueOf(replyInfo.getContent());
                                    if (parameters.get(5) == "Always trust"){
                                        catalogueSeller.remove(title);
                                        newPriceInt = information;
                                        newPriceString = String.valueOf(newPriceInt);
                                        parameters.set(1, newPriceString);
                                        catalogueSeller.put(title, parameters);
                                        price = newPriceInt;
                                        System.out.println("The price of the item " + title + " selling by " + getAID().getName().replace("@192.168.0.11:1099/JADE", " ") + "has been lowered to "+ price +" due to the trust in information provided ");
                                    }if (parameters.get(5) == "Sometimes trust"){
                                        randomGenerator = new Random();
                                        int randomInt = randomGenerator.nextInt(9);
                                        if (randomInt <= 4){
                                            System.out.println("Agent " + getAID().getName().replace("@192.168.0.11:1099/JADE", " ") + "do not trust "  + msg.getSender().getName().replace("@192.168.0.11:1099/JADE", " ") + ". No actions have been taken");
                                        }if (randomInt > 5){
                                            catalogueSeller.remove(title);
                                        newPriceInt = information;
                                        newPriceString = String.valueOf(newPriceInt);
                                        parameters.set(1, newPriceString);
                                        catalogueSeller.put(title, parameters);
                                        price = newPriceInt;
                                        System.out.println("The price of the item " + title + " selling by " + getAID().getName().replace("@192.168.0.11:1099/JADE", " ") + "has been lowered to "+ price +" due to the trust in information provided ");
                                        }
                                    }if (parameters.get(5) == "Never trust"){
                                        System.out.println("Agent " + getAID().getName().replace("@192.168.0.11:1099/JADE", " ") + "do not trust "  + msg.getSender().getName().replace("@192.168.0.11:1099/JADE", " ") + ". No actions have been taken");
                                    }
                                    stepInfo = 2;
                                    }
                                    break;
                           }
                                    }
				else {
					// The requested book is NOT available for sale.
					reply.setPerformative(ACLMessage.REFUSE);
					reply.setContent("not-available");
                                        
                             
				}
				myAgent.send(reply);
			}
			else {
				block();
			}
		}
	}
        }// End of inner class OfferRequestsServer

	/**
	   Inner class PurchaseOrdersServer.
	   This is the behaviour used by Book-seller agents to serve incoming 
	   offer acceptances (i.e. purchase orders) from buyer agents.
	   The seller agent removes the purchased book from its catalogueSeller 
	   and replies with an INFORM message to notify the buyer that the
	   purchase has been sucesfully completed.
	 */
	private class PurchaseOrdersServer extends CyclicBehaviour {
                @Override
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				// ACCEPT_PROPOSAL Message received. Process it
				String title = msg.getContent();
				ACLMessage reply = msg.createReply();

                                ArrayList parameters =  (ArrayList) catalogueSeller.get(title);
                                System.out.println(catalogueSeller.get(title));
                                Integer price = Integer.valueOf(parameters.get(1).toString());
//				String parametersString = catalogueSeller.remove(title).toString();
//                                valueStart = parametersString.indexOf("$v");
//                                valueEnd = parametersString.indexOf("v$");
//                                String newparam = parametersString.substring(valueStart + 2, valueEnd);
//                                Integer price = Integer.valueOf(newparam);
                                    
				if (price != null) {
					reply.setPerformative(ACLMessage.INFORM);
					System.out.println(title+" sold to agent "+msg.getSender().getName().replace("@192.168.0.11:1099/JADE", " "));
				}
				else {
					// The requested book has been sold to another buyer in the meanwhile .
					reply.setPerformative(ACLMessage.FAILURE);
					reply.setContent("not-available");
				}
				myAgent.send(reply);
                                

			}
			else {
				block();
			}
            
		}
                
	} 
        
// End of inner class PurchaseOrdersServer
        public static SellerAgent getInstance() {
        return INSTANCE;
        }
        public SellerAgent getMyAgent(){
        return  myAgent;
        }
}

