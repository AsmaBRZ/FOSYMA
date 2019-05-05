package eu.su.mas.dedaleEtu.mas.behaviours.communication;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import dataStructures.tuple.Couple;
import eu.su.mas.dedaleEtu.mas.agents.dummies.MyAgent;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

import jade.lang.acl.ACLMessage;

/**
 * This example behaviour try to send a hello message (every 3s maximum) to agents Collect2 Collect1
 * @author hc
 *
 */
public class SendKnwoledge extends OneShotBehaviour{

	private static final long serialVersionUID = -2058134622078521998L;
	private Agent agent;
	//Nodes known but not yet visited
	private List<String> openNodes;
	//Visited nodes
	private Set<String> closedNodes;
	private List<String[]> edges;
	//private String receiver;
	private List<String> receivers;
	public SendKnwoledge (final Agent myagent,List<String> r ,List<String> openNodes ,Set<String> closedNodes) {
		super();
		this.agent=myagent;
		this.receivers=r;
		this.openNodes=openNodes;
		this.closedNodes=closedNodes;
	}
	@Override
	public void action() {	
		String myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
		//A message is defined by : a performative, a sender, a set of receivers, (a protocol),(a content (and/or contentOBject))
		ACLMessage msg;
		if (myPosition!=""){
			System.out.println("Agent "+agent.getLocalName()+ " is trying to reach its friends to send the map");
			try {
				//Creation of message's content
				//MessageKnowledge mk=new MessageKnowledge(myMap,openNodes,closedNodes);
				List<Couple<String,List<Couple<Observation,Integer>>>>  objectsFound=((MyAgent)this.agent).getObjetcsFound();
				this.edges=((MyAgent)agent).getMap().getEdges();
				String myClass=this.agent.getClass().getName();
				Object[] mk= {myPosition,openNodes,closedNodes,edges,objectsFound,myClass};
				for(int i=0;i<this.receivers.size();i++) {
					//System.out.println("Agent "+agent.getLocalName()+ " message send to"+this.receivers.get(i));
					msg=new ACLMessage(ACLMessage.INFORM);
					msg.setSender(this.myAgent.getAID());
					msg.setProtocol("UselessProtocol");
					msg.setContentObject(mk);
					msg.addReceiver(new AID(this.receivers.get(i),AID.ISLOCALNAME));
					((AbstractDedaleAgent)agent).sendMessage(msg);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	@Override
	 public int onEnd() {
	      return 0;
	 } 

}