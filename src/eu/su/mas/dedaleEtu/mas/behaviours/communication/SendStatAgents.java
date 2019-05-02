package eu.su.mas.dedaleEtu.mas.behaviours.communication;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.dummies.MyAgent;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendStatAgents extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5569462208192636013L;
	private Agent myAgent;
	//private String receiver;
	private List<String> receivers;
	
	public SendStatAgents(Agent agent, List<String> receivers) {
		super();
		this.myAgent = agent;
		this.receivers = receivers;
	}

	@Override
	public void action() {
		String myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
		//A message is defined by : a performative, a sender, a set of receivers, (a protocol),(a content (and/or contentOBject))
		ACLMessage msg;
		if (myPosition!=""){
			System.out.println("Agent "+this.myAgent.getLocalName()+ " is trying to reach its friends to send the stat of agents");
			try {
				//Creation of message's content
				//MessageKnowledge mk=new MessageKnowledge(myMap,openNodes,closedNodes);
				Object[] mk= {2,((MyAgent)this.myAgent).getStatAgents()};
				for(int i=0;i<this.receivers.size();i++) {
					msg=new ACLMessage(ACLMessage.INFORM);
					msg.setSender(this.myAgent.getAID());
					msg.setProtocol("UselessProtocol");
					msg.setContentObject(mk);
					msg.addReceiver(new AID(this.receivers.get(i),AID.ISLOCALNAME));
					((AbstractDedaleAgent)this.myAgent).sendMessage(msg);
				}
				
				/*System.out.println("*******************ENVOI***************************");	
				System.out.println("position");
				System.out.println(myPosition);
				System.out.println("open");
				System.out.println(openNodes);
				System.out.println("close");
				System.out.println(closedNodes);
				System.out.println("edges");
				for(int i=0;i<edges.size();i++){
					System.out.println("i="+i+" "+edges.get(i)[0]);
					System.out.println("i="+i+" "+edges.get(i)[1]);
				}
				System.out.println("***********************************************");*/
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Mandatory to use this method (it takes into account the environment to decide if someone is reachable or not)
			//System.out.println("Agent" +agent.getLocalName()+ "Before send");

			
			//System.out.println("Agent "+agent.getLocalName()+ "After send");
			//finished=true;
		}

		
	}

}
