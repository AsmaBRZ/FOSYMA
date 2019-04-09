package eu.su.mas.dedaleEtu.mas.behaviours.communication;

import java.util.List;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class AskHelpCollect extends OneShotBehaviour {
	private Agent agent;
	private List<String> receivers;
	private static final long serialVersionUID = -3610239324451390825L;
	public AskHelpCollect(Agent agent, List<String> receivers) {
		super();
		this.agent = agent;
		this.receivers = receivers;
	}
	@Override
	public void action() {
		String myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
		ACLMessage msg;
		if (myPosition!=""){
			System.out.println("Agent "+agent.getLocalName()+ " is trying to reach its friends");
			//Object[] mk= {'Collectons',myPosition};
			for(int i=0;i<this.receivers.size();i++) {
				
				msg=new ACLMessage(ACLMessage.REQUEST);
				msg.setSender(this.myAgent.getAID());
				msg.setProtocol("UselessProtocol");
				msg.setContent("Collectons");
				msg.addReceiver(new AID(this.receivers.get(i),AID.ISLOCALNAME));
				((AbstractDedaleAgent)agent).sendMessage(msg);
			}
		
		}
	}
}
