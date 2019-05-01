package eu.su.mas.dedaleEtu.mas.behaviours.communication;

import java.util.List;
import java.util.Set;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.dummies.MyAgent;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class ReceiveHelpCollect  extends OneShotBehaviour{

	/**
	 * 
	 */
	private Agent myAgent;
	private static final long serialVersionUID = -3108680236914613694L;

	public ReceiveHelpCollect(Agent myAgent) {
		super();
		this.myAgent = myAgent;
	}

	@Override
	public void action() {
		System.out.println("je m'appelle "+((AbstractDedaleAgent)this.myAgent).getLocalName()+" je suis dans le behaviour help collect");

		final MessageTemplate msgTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);			
		final ACLMessage msg = myAgent.receive(msgTemplate);
		
		//Integer senderOrder=Integer.parseInt(senderName.substring(1,senderName.length()-1));
		if (msg != null) {	
			String myName=((AbstractDedaleAgent)this.myAgent).getLocalName();
			System.out.println("eeeeeeeeeeeeee"+myName.substring(1,myName.length()-1));
			String senderName=msg.getSender().getLocalName();
			//Integer senderOrder=Integer.parseInt(senderName.substring(1,senderName.length()-1));
			//System.out.println("moimmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmi: "+myOrder+" lui: "+senderOrder);
			String myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
			//System.out.println("Agent \"+agent.getLocalName()"+ "in testing msg!=null");
			//System.out.println(agent.getLocalName()+"<----Result received from "+msg.getSender().getLocalName());
			try {
				Object[] content=(Object[]) msg.getContentObject();
				//la position de l agent qui demande de l'aide
				String positionTarget=(String) content[0];
				System.out.println(positionTarget);
				//si je suis explorator ou collector moins prioritire , j y vais sans me poser de questions
				if(this.myAgent instanceof eu.su.mas.dedaleEtu.mas.agents.dummies.AgentCollect) {
					List<String>  pathToTarget=((MyAgent)this.myAgent).getShortestPath(myPosition,positionTarget); 
					for(int k=0;k<pathToTarget.size()-1;k++) {
						System.out.println("je vais vers la position "+positionTarget);
						((MyAgent)this.myAgent).moveTo(pathToTarget.get(k));
					}
					System.out.println(((AbstractDedaleAgent)this.myAgent).getLocalName()+" my Expertise "+((MyAgent)this.myAgent).getMyExpertise());
					//List<String>  pathToTarget2=((MyAgent)this.myAgent).getShortestPath(myPosition,positionTarget);
					try {
						Thread.sleep(10000);
						System.out.println("I am sleeeping");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}// the behaviour goes to sleep until the arrival of a new message in the agent's Inbox.
					//si j'arrive a l'endroit */
				}
				
				
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
