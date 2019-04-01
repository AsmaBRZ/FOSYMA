package eu.su.mas.dedaleEtu.mas.behaviours.communication;

import java.io.IOException;
import java.util.List;
import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.dummies.ExploratorAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SendObs  extends OneShotBehaviour{
	private List<String> receivers;
	private Agent agent;
	private List<Couple<String,List<Couple<Observation,Integer>>>> objetcsFound;
	private static final long serialVersionUID = 5905797849939321704L;
	private String myPosition;
	public SendObs( Agent agent,List<String> receivers) {
		super();
		this.receivers = receivers;
		this.agent = agent;
		this.objetcsFound = ((ExploratorAgent) this.agent).getObjetcsFound();
	}
	@Override
	public void action() {
		this.myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
		ACLMessage msg;
		if (myPosition!="" && !this.objetcsFound.isEmpty()){
			System.out.println("Agent "+agent.getLocalName()+ " is trying to reach its friends");
			try {
				Object[] mk= {myPosition,this.objetcsFound};
				for(int i=0;i<this.receivers.size();i++) {
					msg=new ACLMessage(ACLMessage.INFORM);
					msg.setSender(this.myAgent.getAID());
					msg.setProtocol("UselessProtocol");
					msg.setContentObject(mk);
					msg.addReceiver(new AID(this.receivers.get(i),AID.ISLOCALNAME));
					((AbstractDedaleAgent)agent).sendMessage(msg);
					System.out.println("sending "+this.objetcsFound.toString());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
