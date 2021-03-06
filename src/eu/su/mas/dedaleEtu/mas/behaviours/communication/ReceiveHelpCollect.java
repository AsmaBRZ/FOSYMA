package eu.su.mas.dedaleEtu.mas.behaviours.communication;

import java.util.List;
import java.util.Random;
import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.dummies.MyAgent;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class ReceiveHelpCollect  extends OneShotBehaviour{

	/**
	 * 
	 */
	private Agent myAgent;
	private static final long serialVersionUID = -3108680236914613694L;

	public ReceiveHelpCollect(Agent myAgent) {
		super();
		this.myAgent = myAgent;
	}

	@Override
	public void action() {
		System.out.println("je m'appelle "+((AbstractDedaleAgent)this.myAgent).getLocalName()+" je suis dans le behaviour help collect");

		final MessageTemplate msgTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);			
		final ACLMessage msg = myAgent.receive(msgTemplate);
		
		//Integer senderOrder=Integer.parseInt(senderName.substring(1,senderName.length()-1));
		if (msg != null) {	
			String myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
			int cpt=0;
			try {
				Object[] content=(Object[]) msg.getContentObject();
				//la position de l agent qui demande de l'aide
				String positionTarget=(String) content[0];
				if(this.myAgent instanceof eu.su.mas.dedaleEtu.mas.agents.dummies.AgentCollect) {
					List<String>  pathToTarget=((MyAgent)this.myAgent).getShortestPath(myPosition,positionTarget); 
					if(pathToTarget.size()>0){
						for(int k=0;k<pathToTarget.size()-1;k++) {
							myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
							pathToTarget=((MyAgent)this.myAgent).getShortestPath(myPosition,positionTarget);
							k=0;
							boolean succ=((MyAgent)this.myAgent).moveTo(pathToTarget.get(k));
							if(succ==false ){
								cpt+=1;
								k=k-1;
							}
							if(cpt>1){
								Random r= new Random();
								List<Couple<String,List<Couple<Observation,Integer>>>> lobs=((AbstractDedaleAgent)this.myAgent).observe();//myPosition
								
								int moveId=1+r.nextInt(lobs.size()-1);
								((MyAgent)this.myAgent).moveTo(lobs.get(moveId).getLeft());
								myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
								pathToTarget=((MyAgent)this.myAgent).getShortestPath(myPosition,positionTarget);
								k=0;
								cpt=0;
							}
						}
						
					}
					
					//System.out.println(((AbstractDedaleAgent)this.myAgent).getLocalName()+" my Expertise "+((MyAgent)this.myAgent).getMyExpertise());
				}
				
				
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
