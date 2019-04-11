package eu.su.mas.dedaleEtu.mas.behaviours.communication;

import java.io.IOException;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class InformDeadlock extends OneShotBehaviour{

	private static final long serialVersionUID = 7640768714166905141L;
	private String receiver;
	private Agent agent;

	public InformDeadlock(Agent a,String r) {
		this.agent=a;
		this.receiver=r;
	}

	@Override
	public void action() {
		String myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
		ACLMessage msg=new ACLMessage(ACLMessage.INFORM);
		msg.setSender(this.myAgent.getAID());
		msg.setProtocol("UselessProtocol");
		if (myPosition!=""){
			System.out.println("Agent "+agent.getLocalName()+ " is trying to reach its friends on deadlock");
			try {
				//Creation of message's content
				Object[] content= {-1};
				msg.setContentObject(content);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				msg.addReceiver(new AID(this.receiver, AID.ISLOCALNAME)); 
			((AbstractDedaleAgent)this.agent).sendMessage(msg);
		}
		
	}

}
