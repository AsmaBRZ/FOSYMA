package eu.su.mas.dedaleEtu.mas.behaviours.communication;
import java.util.List;
import java.util.Set;

import eu.su.mas.dedaleEtu.mas.agents.dummies.ExploratorAgent;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
public class ReceiveKnowledge extends OneShotBehaviour{

	private static final long serialVersionUID = -4404490189062055618L;
	private boolean finished=false;
	private Agent myAgent;
	private int exitValue;
	public ReceiveKnowledge(Agent agent) {
		super();
		this.myAgent = agent;
	}
	@Override
	public void action() {	
		final MessageTemplate msgTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);			
		final ACLMessage msg = myAgent.receive(msgTemplate);
		
		System.out.println("Agent \"+agent.getLocalName()"+ "before testing msg!=null");
		if (msg != null) {	
			//System.out.println("Agent \"+agent.getLocalName()"+ "in testing msg!=null");
			//System.out.println(agent.getLocalName()+"<----Result received from "+msg.getSender().getLocalName());
			try {
				Object[] content=(Object[]) msg.getContentObject();
				//System.out.println("*******************RECEPTION****************************");			
				//System.out.println(content[0]+ " "+content[1]+ " "+content[2]+ " "+content[3]);
				//System.out.println("***********************************************");

				String positionReceived=(String) content[0];
				List<String> openNodes=(List<String>)content[1];
				Set<String> closedNodes=(Set<String>)content[2];
				List<String[]> edges=(List<String[]>)content[3];
				((ExploratorAgent) myAgent).updateKnowledge(positionReceived,openNodes,closedNodes,edges);
				
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// the behaviour goes to sleep until the arrival of a new message in the agent's Inbox.
		}
		if(((ExploratorAgent)this.myAgent).getType()==2) {
			this.exitValue=2;
			System.out.println("Receive: collection!");
		}
		if(((ExploratorAgent)this.myAgent).getType()==1) {
			this.exitValue=1;
			System.out.println("Receive: exploration!");
		}
	}
	@Override
	public int onEnd() {
		return this.exitValue;
	}
/*
	@Override
	public boolean done() {
		return finished;
	}*/
	
}
