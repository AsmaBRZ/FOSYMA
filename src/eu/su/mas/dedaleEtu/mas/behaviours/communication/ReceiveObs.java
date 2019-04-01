package eu.su.mas.dedaleEtu.mas.behaviours.communication;

import java.util.List;
import java.util.Set;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedaleEtu.mas.agents.dummies.ExploratorAgent;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class ReceiveObs  extends OneShotBehaviour{
	private static final long serialVersionUID = -4242183380018190294L;
	private Agent agent;
	
	public ReceiveObs(Agent agent) {
		super();
		this.agent = agent;
	}
	@Override
	public void action() {
		final MessageTemplate msgTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);			
		final ACLMessage msg = myAgent.receive(msgTemplate);
		if (msg != null) {	
			//System.out.println("Agent \"+agent.getLocalName()"+ "in testing msg!=null");
			//System.out.println(agent.getLocalName()+"<----Result received from "+msg.getSender().getLocalName());
			try {
				Object[] content=(Object[]) msg.getContentObject();
				System.out.println("*******************RECEPTION****************************");			
				System.out.println(content[0]);
				System.out.println("***********************************************");
				List<Couple<String,List<Couple<Observation,Integer>>>> newObj=(List<Couple<String,List<Couple<Observation,Integer>>>> ) content[0];
				((ExploratorAgent) myAgent).addObjectsFound(newObj);
				
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
