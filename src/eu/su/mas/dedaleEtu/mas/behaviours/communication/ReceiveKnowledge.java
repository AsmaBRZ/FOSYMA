package eu.su.mas.dedaleEtu.mas.behaviours.communication;
import java.util.List;
import java.util.Set;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.dummies.MyAgent;
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
		System.out.println("je m'appelle "+((AbstractDedaleAgent)this.myAgent).getLocalName()+" je suis dans le behaviour receive knowledge");

		final MessageTemplate msgTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);			
		final ACLMessage msg = myAgent.receive(msgTemplate);
		
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
				List<Couple<String,List<Couple<Observation,Integer>>>> newObjsFound=(List<Couple<String,List<Couple<Observation,Integer>>>> )content[4];;
				((MyAgent) myAgent).updateKnowledge(positionReceived,openNodes,closedNodes,edges);
				//System.out.println("before update receive: "+((MyAgent)this.myAgent).getObjetcsFound().toString());
				((MyAgent) myAgent).updateObjsFound(newObjsFound);
				//System.out.println("On receive:"+newObjsFound.toString());
				//System.out.println("after update receive: "+((MyAgent)this.myAgent).getObjetcsFound().toString());
				
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(((MyAgent)this.myAgent).getType()==1) {
			((MyAgent)this.myAgent).createmyTr();
			System.out.println(((MyAgent)this.myAgent).getLocalName()+"--- Je vais ouvrir les trésors :D");
				this.exitValue=2;

		}
		else{
			if(((MyAgent)this.myAgent).getType()==2) {
			this.exitValue=3;
			System.out.println(((MyAgent)this.myAgent).getLocalName()+"--- Je continue à explorer :D");
			}
			else{
				this.exitValue=1;
		}
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
