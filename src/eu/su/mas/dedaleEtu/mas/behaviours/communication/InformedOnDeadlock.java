package eu.su.mas.dedaleEtu.mas.behaviours.communication;

import java.io.IOException;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class InformedOnDeadlock extends OneShotBehaviour{

	private static final long serialVersionUID = 7640768714166905141L;
	private Agent agent;

	public InformedOnDeadlock(Agent a) {
		this.agent=a;
	}

	@Override
	public void action() {
		final MessageTemplate msgTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);			
		final ACLMessage msg = agent.receive(msgTemplate);
		if (msg != null) {	
			System.out.println(agent.getLocalName()+"<----Result received on deadlock from "+msg.getSender().getLocalName());
			try {
				Object[] content=(Object[]) msg.getContentObject();
				int c=(Integer)content[0];
				if(c==-1) {
					//deadlock situation
					
				}
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
		}
		
	}

}
