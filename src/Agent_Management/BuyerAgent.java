package Agent_Management;

import GUI.BuyerItemsGui;
import GUI.SellerItemsGui;
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import java.util.ArrayList;
import java.util.HashMap;

public class BuyerAgent extends Agent {
    
        private static final BuyerAgent INSTANCE = new BuyerAgent();

        public static HashMap catalogueBuyer;
        private ArrayList<String> parameters;
        private BuyerItemsGui myGui;
        private String title;
        // Information about the book buyer is interested to obtain
        private static String targetBook;
	// The title of the book to buy
	public static String targetBookTitle;
        // The separator which will tell agent where is the price is "/" currently
        private int separator;
        // The maximum price the buyer is willing to pay
        private String maxPriceS;
        private int maxPriceI;
        private String increasingNumberS;
        private int increasingNumber;
        private String newPriceString;
        private int newPriceInt;
        public BuyerAgent myAgent;
	// The list of known seller agents
	public static AID[] sellerAgents;
        
        public BuyerAgent(){
            //myGui = new BuyerItemsGui("Create new Iem", 500, 300, this);
        }

	// Put agent initializations here
        @Override
	protected void setup() {
		// Printout a welcome message
		System.out.println("Hallo! Buyer-agent "+getAID().getName().replace("@192.168.0.11:1099/JADE", " ")+" is ready.");
                catalogueBuyer = new HashMap();
		// Get the title of the book to buy as a start-up argument
		myGui = new BuyerItemsGui("Create new Iem", 500, 300, this);
                DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription bd = new ServiceDescription();
		bd.setType("book-buying");
		bd.setName("JADE-book-trading");
		dfd.addServices(bd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
		}
		addBehaviour(new TickerBehaviour(this, 30000) {
                                @Override
				protected void onTick() {
                                    setParameters();
					System.out.println("Trying to buy "+targetBookTitle);
					// Update the list of seller agents
					DFAgentDescription template = new DFAgentDescription();
					ServiceDescription sd = new ServiceDescription();
					sd.setType("book-selling");
					template.addServices(sd);
					try {
						DFAgentDescription[] result = DFService.search(myAgent, template); 
						System.out.println("Found the following seller agents:");
						sellerAgents = new AID[result.length];
						for (int i = 0; i < result.length; ++i) {
							sellerAgents[i] = result[i].getName();
							System.out.println(sellerAgents[i].getName().replace("@192.168.0.11:1099/JADE", " "));
						}
					}
					catch (FIPAException fe) {
					}

					// Perform the request
					myAgent.addBehaviour(new RequestPerformer());
				}
			} );
		//}

	}

	// Put agent clean-up operations here
        @Override
	protected void takeDown() {
		// Printout a dismissal message
		System.out.println("Buyer-agent "+getAID().getName().replace("@192.168.0.11:1099/JADE", " ")+" terminating.");
	}

	/**
	   Inner class RequestPerformer.
	   This is the behaviour used by Book-buyer agents to request seller 
	   agents the target book.
	 */
	public class RequestPerformer extends Behaviour {
		private AID bestSeller; // The agent who provides the best offer 
		private int bestPrice;  // The best offered price
		private int repliesCnt = 0; // The counter of replies from seller agents
		private MessageTemplate mt; // The template to receive replies
		private int step = 0;

                @Override
		public void action() {
			switch (step) {
			case 0:
				// Send the cfp to all sellers
				ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
				for (int i = 0; i < sellerAgents.length; ++i) {
					cfp.addReceiver(sellerAgents[i]);
				} 
				cfp.setContent(targetBookTitle);
				cfp.setConversationId("book-trade");
				cfp.setReplyWith("cfp"+System.currentTimeMillis()); // Unique value
				myAgent.send(cfp);
				// Prepare the template to get proposals
				mt = MessageTemplate.and(MessageTemplate.MatchConversationId("book-trade"),
						MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
				step = 1;
				break;
			case 1:
				// Receive all proposals/refusals from seller agents
				ACLMessage reply = myAgent.receive(mt);
				if (reply != null) {
					// Reply received
					if (reply.getPerformative() == ACLMessage.PROPOSE) {
						// This is an offer 
						int price = Integer.parseInt(reply.getContent());
						if ((bestSeller == null || price < bestPrice) && price <= maxPriceI) {
							// This is the best offer at present that is lower than the maximum price
							bestPrice = price;
							bestSeller = reply.getSender();
                                                        
						}
					ArrayList parameters =  (ArrayList) catalogueBuyer.get(targetBookTitle);
                                             increasingNumberS = (String) parameters.get(2);
                                          price = Integer.valueOf(parameters.get(1).toString());
                                        if(!"0".equals(increasingNumberS)){
                                            increasingNumber = Integer.valueOf(increasingNumberS);
                                            catalogueBuyer.remove(title);
                                            newPriceInt = price + increasingNumber;
                                            newPriceString = String.valueOf(newPriceInt);
                                            parameters.set(1, newPriceString);
//                                        parameters.set(1, "$v" + newPriceString + "v$");
                                            catalogueBuyer.put(targetBookTitle, parameters);
                                            price = newPriceInt;
                                            System.out.println("The price of the book " + targetBookTitle + " buying by " + getAID().getName().replace("@192.168.0.11:1099/JADE", " ") + "has been increased by "+ increasingNumber +" to a new price which is " + price);
                                        }
                                    
                                }
                                        
					repliesCnt++;
                                        ArrayList parameters =  (ArrayList) catalogueBuyer.get(targetBookTitle);
                                        MessageTemplate infoTemplate = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
                                        ACLMessage msgInfo = myAgent.receive(infoTemplate);
                                        if (msgInfo != null){
                                        ACLMessage replyInfo = msgInfo.createReply();
                                        replyInfo.addReceiver(reply.getSender());
                                            if (msgInfo != null){
                                                String info = msgInfo.getContent();
                                                System.out.println(info);
                                                replyInfo.setPerformative(ACLMessage.INFORM);
                                                if ("Always truth".equals(parameters.get(4).toString())){
                                                replyInfo.setContent(parameters.get(3).toString());
                                                }if ("Always lie".equals(parameters.get(4).toString())){
                                                String falseInfoS = parameters.get(3).toString();
                                                int falseInfoI = Integer.valueOf(falseInfoS);
                                                int newFalseInfoI = falseInfoI - 100;
                                                replyInfo.setContent(Integer.toString(newFalseInfoI));
                                            }
                                                myAgent.send(replyInfo);
                                                
                                            }}
					if (repliesCnt >= sellerAgents.length) {
						// We received all replies
						step = 2; 
					}
				}
				else {
					block();
				}
				break;
			case 2:
				// Send the purchase order to the seller that provided the best offer
				ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
				order.addReceiver(bestSeller);
				order.setContent(targetBookTitle);
				order.setConversationId("book-trade");
				order.setReplyWith("order"+System.currentTimeMillis());
				myAgent.send(order);
				// Prepare the template to get the purchase order reply
				mt = MessageTemplate.and(MessageTemplate.MatchConversationId("book-trade"),
						MessageTemplate.MatchInReplyTo(order.getReplyWith()));
				step = 3;
				break;
			case 3:      
				// Receive the purchase order reply
				reply = myAgent.receive(mt);
				if (reply != null) {
					// Purchase order reply received
					if (reply.getPerformative() == ACLMessage.INFORM) {
						// Purchase successful. We can terminate
						System.out.println(targetBookTitle+" successfully purchased from agent "+reply.getSender().getName().replace("@192.168.0.11:1099/JADE", " "));
						System.out.println("Price = "+bestPrice);
						myAgent.doDelete();
					}
					else {
						System.out.println("Attempt failed: requested book already sold.");
					}

					step = 4;
				}
				else {
					block();
				}
				break;
			}        
		}

                @Override
		public boolean done() {
			if (step == 2 && bestSeller == null) {
				System.out.println("Attempt failed: "+targetBookTitle+" not available for sale");
			}
			return ((step == 2 && bestSeller == null) || step == 4);
		}
	}  // End of inner class RequestPerformer
        
        	public void updateCatalogue(final String title, final ArrayList<String> parameters) {
		addBehaviour(new OneShotBehaviour() {
                        @Override
			public void action() {
				catalogueBuyer.put(title, parameters);
				System.out.println(title+" inserted into catalogue by " + getAID().getName().replace("@192.168.0.11:1099/JADE", " ") + " Utility = "+parameters.get(0) + " Current Price = "+ parameters.get(1));
                                
                        }
		} );
	}

        public static BuyerAgent getInstance() {
        return INSTANCE;
        }
        public BuyerAgent getMyAgent(){
        return myAgent;
        }
        
        
        public void setParameters(){
            

//        @Override
//        public void action() {
            targetBook = catalogueBuyer.toString();
            separator = targetBook.indexOf("=");
            targetBookTitle = targetBook.substring(1,(separator));
            ArrayList parameters =  (ArrayList) catalogueBuyer.get(targetBookTitle);
            System.out.println(catalogueBuyer.get(targetBookTitle));
            //Integer price = Integer.valueOf(parameters.get(1).toString());
		if (parameters.isEmpty() == false) {
			
                        maxPriceS = parameters.get(3).toString();
                        maxPriceI = Integer.valueOf(maxPriceS);
			System.out.println("Target book is "+targetBookTitle + " and its max price is " + maxPriceI);
                }	else {
			// Make the agent terminate
			System.out.println("No target book title specified");
			doDelete();
		}
        }
        public void runBuyer(){
            	// Add a TickerBehaviour that schedules a request to seller agents every minute
			addBehaviour(new TickerBehaviour(this, 30000) {
                                @Override
				protected void onTick() {
                                    setParameters();
					System.out.println("Trying to buy "+targetBookTitle);
					// Update the list of seller agents
					DFAgentDescription template = new DFAgentDescription();
					ServiceDescription sd = new ServiceDescription();
					sd.setType("book-selling");
					template.addServices(sd);
					try {
						DFAgentDescription[] result = DFService.search(myAgent, template); 
						System.out.println("Found the following seller agents:");
						sellerAgents = new AID[result.length];
						for (int i = 0; i < result.length; ++i) {
							sellerAgents[i] = result[i].getName();
							System.out.println(sellerAgents[i].getName().replace("@192.168.0.11:1099/JADE", " "));
						}
					}
					catch (FIPAException fe) {
					}

					// Perform the request
					myAgent.addBehaviour(new RequestPerformer());
				}
			} );
        }
        }

