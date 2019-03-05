package eu.su.mas.dedaleEtu.mas.behaviours.communication;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import eu.su.mas.dedaleEtu.mas.agents.dummies.ExploratorAgent;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * This example behaviour try to send a hello message (every 3s maximum) to agents Collect2 Collect1
 * @author hc
 *
 */
public class SendKnwoledge extends SimpleBehaviour{

	private static final long serialVersionUID = -2058134622078521998L;
	private MapRepresentation myMap;
	private Agent agent;
	//Nodes known but not yet visited
	private List<String> openNodes;
	//Visited nodes
	private Set<String> closedNodes;
	private List<String[]> edges;
	private boolean finished= false;
	private String receiver;
	public SendKnwoledge (final Agent myagent,String r ,List<String> openNodes ,Set<String> closedNodes) {
		super();
		this.agent=myagent;
		this.receiver=r;
		this.openNodes=openNodes;
		this.closedNodes=closedNodes;
	}
	@Override
	public void action() {	
		String myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();

		//A message is defined by : a performative, a sender, a set of receivers, (a protocol),(a content (and/or contentOBject))
		ACLMessage msg=new ACLMessage(ACLMessage.INFORM);
		msg.setSender(this.myAgent.getAID());
		msg.setProtocol("UselessProtocol");

		if (myPosition!=""){
			System.out.println("Agent "+agent.getLocalName()+ " is trying to reach its friends");
			try {
				//Creation of message's content
				//MessageKnowledge mk=new MessageKnowledge(myMap,openNodes,closedNodes);
				this.edges=((ExploratorAgent)agent).getMap().getEdges();
				Object[] mk= {myPosition,openNodes,closedNodes,edges};
				System.out.println("*******************ENVOI***************************");	
				System.out.println("position");
				/*System.out.println(myPosition);
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
				msg.setContentObject(mk);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			msg.addReceiver(new AID(this.receiver,AID.ISLOCALNAME));
			//Mandatory to use this method (it takes into account the environment to decide if someone is reachable or not)
			System.out.println("Agent \"+agent.getLocalName()"+ "Before send");
			
			((AbstractDedaleAgent)agent).sendMessage(msg);
			System.out.println("Agent \"+agent.getLocalName()"+ "After send");
			finished=true;
		}
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return finished;
	}
}