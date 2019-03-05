package eu.su.mas.dedaleEtu.mas.behaviours.communication;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class Ping extends SimpleBehaviour{
	private Agent agent;
	private String receiverName;
	private boolean finished=false;	
	private static final long serialVersionUID = 1L;
	
	public Ping(final Agent myagent,String receiverName) {
		this.receiverName=receiverName;
		this.myAgent=myagent;
	}
	@Override
	public void action() {
		ACLMessage msg=new ACLMessage(ACLMessage.INFORM);
		msg.setSender(this.myAgent.getAID());
		msg.setProtocol("UselessProtocol");
		msg.addReceiver(new AID(this.receiverName, AID.ISLOCALNAME));  
		msg.setContent("I ping you");
		finished=true;
		
	}

	@Override
	public boolean done() {
		
		return this.finished;
	}

}
