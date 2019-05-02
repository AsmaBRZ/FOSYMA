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
public class ReceiveState extends OneShotBehaviour{

	private static final long serialVersionUID = -4404490189062055618L;
	private boolean finished=false;
	private Agent myAgent;
	private int exitValue;
	public ReceiveState(Agent agent) {
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
				System.out.println("je sussssssssssssssssssssssssssssssssssssssi "+ content[0].toString());
				//System.out.println("*******************RECEPTION****************************");			
				//System.out.println(content[0]+ " "+content[1]+ " "+content[2]+ " "+content[3]);
				//System.out.println("***********************************************");
                int type =(Integer)content[0];
                if(type==1){
					String positionReceived=(String) content[1];
					List<String> openNodes=(List<String>)content[2];
					Set<String> closedNodes=(Set<String>)content[3];
					List<String[]> edges=(List<String[]>)content[4];
					List<Couple<String,List<Couple<Observation,Integer>>>> newObjsFound=(List<Couple<String,List<Couple<Observation,Integer>>>> )content[5];
					((MyAgent) myAgent).updateKnowledge(positionReceived,openNodes,closedNodes,edges);
					//System.out.println("before update receive: "+((MyAgent)this.myAgent).getObjetcsFound().toString());
					((MyAgent) myAgent).updateObjsFound(newObjsFound);
					//System.out.println("On receive:"+newObjsFound.toString());
					//System.out.println("after update receive: "+((MyAgent)this.myAgent).getObjetcsFound().toString());
	            }
                if(type==2){        	
                	System.out.println("je susi dans 222222222222222222222222222");
                	List<Boolean> newStatAgents=(List<Boolean>) content[1];
                	System.out.println(newStatAgents.toString());
    				((MyAgent)this.myAgent).updateStatAgents(newStatAgents);
                }
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
