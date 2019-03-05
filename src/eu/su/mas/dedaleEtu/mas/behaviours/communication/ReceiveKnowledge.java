package eu.su.mas.dedaleEtu.mas.behaviours.communication;
import java.util.List;
import java.util.Set;

import eu.su.mas.dedaleEtu.mas.agents.dummies.ExploratorAgent;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
public class ReceiveKnowledge extends SimpleBehaviour{

	private static final long serialVersionUID = -4404490189062055618L;
	private boolean finished=false;
	private Agent agent;
	
	public ReceiveKnowledge(Agent agent) {
		super();
		this.agent = agent;
	}
	@Override
	public void action() {	
		final MessageTemplate msgTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);			
		final ACLMessage msg = agent.receive(msgTemplate);
		if (msg != null) {		
			System.out.println(agent.getLocalName()+"<----Result received from "+msg.getSender().getLocalName());
			try {
				Object[] content=(Object[]) msg.getContentObject();
				System.out.println("*******************RECEPTION****************************");			
				System.out.println(content[0]+ " "+content[1]+ " "+content[2]+ " "+content[3]);
				System.out.println("***********************************************");

				String positionReceived=(String) content[0];
				List<String> openNodes=(List<String>)content[1];
				Set<String> closedNodes=(Set<String>)content[2];
				List<String[]> edges=(List<String[]>)content[3];

				((ExploratorAgent) agent).updateKnowledge(positionReceived,openNodes,closedNodes,edges);
				
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			block();// the behaviour goes to sleep until the arrival of a new message in the agent's Inbox.
		}
	}

	@Override
	public boolean done() {
		return finished;
	}
	
}
