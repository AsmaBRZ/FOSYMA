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
		String myName=this.myAgent.getName();
		Integer myOrder=Integer.parseInt(myName.substring(1,myName.length()-1));
		
		final MessageTemplate msgTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);			
		final ACLMessage msg = myAgent.receive(msgTemplate);
		String senderName=msg.getSender().getName();
		Integer senderOrder=Integer.parseInt(senderName.substring(1,senderName.length()-1));
		if (msg != null) {	
			String myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
			//System.out.println("Agent \"+agent.getLocalName()"+ "in testing msg!=null");
			//System.out.println(agent.getLocalName()+"<----Result received from "+msg.getSender().getLocalName());
			try {
				Object[] content=(Object[]) msg.getContentObject();
				//la position de l agent qui demande de l'aide
				String positionTarget=(String) content[0];
				//si je suis explorator ou collector moins prioritire , j y vais sans me poser de questions
				if(this.myAgent instanceof eu.su.mas.dedaleEtu.mas.agents.dummies.AgentExplo) {
					List<String>  pathToTarget=((MyAgent)this.myAgent).getShortestPath(myPosition,positionTarget); 
					for(int k=0;k<pathToTarget.size();k++) {
						((MyAgent)this.myAgent).moveTo(pathToTarget.get(k));
					}
				}
				//si je suis collecteur dont le nom est < au sender
				else{
					if(this.myAgent instanceof eu.su.mas.dedaleEtu.mas.agents.dummies.AgentExplo && myOrder<senderOrder) {
						List<String>  pathToTarget=((MyAgent)this.myAgent).getShortestPath(myPosition,positionTarget); 
						for(int k=0;k<pathToTarget.size();k++) {
							((MyAgent)this.myAgent).moveTo(pathToTarget.get(k));
						}
				}
			}

				
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
