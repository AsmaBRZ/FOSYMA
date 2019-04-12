package eu.su.mas.dedaleEtu.mas.agents.dummies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.agent.behaviours.startMyBehaviours;
import eu.su.mas.dedaleEtu.mas.behaviours.ExploSoloBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.RandomSearchBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.communication.ReceiveHelpCollect;
import eu.su.mas.dedaleEtu.mas.behaviours.communication.ReceiveKnowledge;
import eu.su.mas.dedaleEtu.mas.behaviours.communication.SendKnwoledge;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.FSMBehaviour;

public class AgentExplo extends MyAgent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4641146536413948081L;
	//agentName
		//communicationRange
		//initialLocation
		//BackPackCapacityGold
		//BackPackCapacityDiamond
		//detectionRadius
		//strengthExpertise
		//LockPickingExpertise		
		//Definition of states
	private static final String explore="ExploSoloBehaviour";
	private static final String randomSearch="RandomSearchBehaviour";
	private static final String sendKnow="SendKnowledge";
	private static final String receiveKnow="ReceiveKnowledge";
	private static final String goToHelp="ReceiveHelpCollect ";
	private static final String mandatory="startMyBehaviours";
	
	private FSMBehaviour fsm ;
	
	@SuppressWarnings("unchecked")
	protected void setup(){
		super.setup();	
		this.objetcsFound=new ArrayList<Couple<String,List<Couple<Observation,Integer>>>> ();
		this.openedNodes=new ArrayList<String>();
		this.closedNodes=new HashSet<String>();
		//get the parameters given into the object[]
		final Object[] args = getArguments();
		if(args[0]!=null){
			receivers = (List<String>) args[2];
			//these data are currently not used by the agent, its just to show you how to get them if you need it 
		}else{	
			System.out.println("Erreur lors du tranfert des parametres");
		}		
		fsm = new FSMBehaviour(this);
		// Define the different states and behaviours
		fsm.registerFirstState (new ExploSoloBehaviour(this), explore);
		fsm.registerState (new RandomSearchBehaviour(this), randomSearch);
		fsm.registerState (new ReceiveHelpCollect(this), goToHelp);
		fsm.registerState (new SendKnwoledge(this,receivers,this.openedNodes,this.closedNodes),sendKnow);
		fsm.registerState (new ReceiveKnowledge(this),receiveKnow);
		fsm.registerDefaultTransition(explore,sendKnow);
		fsm.registerDefaultTransition(sendKnow,receiveKnow);
		fsm.registerTransition(receiveKnow,explore,1);
		fsm.registerTransition(receiveKnow,randomSearch,3);
		fsm.registerDefaultTransition(randomSearch,goToHelp);
		fsm.registerDefaultTransition(goToHelp,sendKnow);
		lb=new ArrayList<Behaviour>();
		lb.add(fsm);
		/***
		* MANDATORY TO ALLOW YOUR AGENT TO BE DEPLOYED CORRECTLY
		*/
		addBehaviour(new startMyBehaviours(this,lb));	
		System.out.println("the  agent "+this.getLocalName()+ " is started");
		}
}
