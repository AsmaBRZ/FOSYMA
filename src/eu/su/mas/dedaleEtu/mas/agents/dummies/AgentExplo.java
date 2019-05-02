package eu.su.mas.dedaleEtu.mas.agents.dummies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.agent.behaviours.startMyBehaviours;
import eu.su.mas.dedaleEtu.mas.behaviours.LocksmithBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.Donothing;
import eu.su.mas.dedaleEtu.mas.behaviours.ExploSoloBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.MovetoTarget;
import eu.su.mas.dedaleEtu.mas.behaviours.RandomSearchBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.communication.AskHelpCollect;
import eu.su.mas.dedaleEtu.mas.behaviours.communication.ReceiveHelpCollect;
import eu.su.mas.dedaleEtu.mas.behaviours.communication.ReceiveKnowledge;
import eu.su.mas.dedaleEtu.mas.behaviours.communication.SendKnwoledge;
import eu.su.mas.dedaleEtu.mas.behaviours.communication.SendStatAgents;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.FSMBehaviour;

public class AgentExplo extends MyAgent{

	//agentName
		//communicationRange
		//initialLocation
		//BackPackCapacityGold
		//BackPackCapacityDiamond
		//detectionRadius
		//strengthExpertise
		//LockPickingExpertise
		private static final long serialVersionUID = 2384524762066236260L;
		
		//Definition of states
		private static final String explore="ExploSoloBehaviour";
		private static final String receiveStatAgents="ReceiveStatAgents";
		private static final String sendStatAgents="SendStatAgents";
		private static final String openlock="LocksmithBehaviour";
		private static final String sendKnow="SendKnowledge";
		private static final String receiveKnow="ReceiveKnowledge";
		private static final String mandatory="startMyBehaviours";
		private static final String randomSearch="RandomSearchBehaviour";
		private static final String goToHelp="ReceiveHelpCollect ";
		private static final String donothing="Donothing ";
		private static final String MovetoTarget="MovetoTarget ";
		private static final String Askforhelp="Askforhelp ";


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
				((MyAgent)this).initStatAgents(receivers.size());
				//these data are currently not used by the agent, its just to show you how to get them if you need it 
			}else{
				System.out.println("Erreur lors du tranfert des parametres");
			}		
			fsm = new FSMBehaviour(this);
			// Define the different states and behaviours
			fsm.registerFirstState (new ExploSoloBehaviour(this), explore);
			fsm.registerState(new SendStatAgents(this,this.receivers), sendStatAgents);
			
			fsm.registerState (new RandomSearchBehaviour(this), randomSearch);
			fsm.registerState (new LocksmithBehaviour(this), openlock);
			fsm.registerState (new ReceiveHelpCollect(this), goToHelp);
			fsm.registerLastState (new Donothing(), donothing);
			fsm.registerState (new MovetoTarget(this), MovetoTarget);
			fsm.registerState (new AskHelpCollect(this,receivers), Askforhelp);
			fsm.registerState (new SendKnwoledge(this,receivers,this.openedNodes,this.closedNodes),sendKnow);
			fsm.registerState (new ReceiveKnowledge(this),receiveKnow);
			
			fsm.registerTransition(explore,sendKnow,1);
			fsm.registerTransition(explore,openlock,2);
			
			fsm.registerDefaultTransition(sendKnow,sendStatAgents);
			fsm.registerDefaultTransition(sendStatAgents,receiveKnow);
			fsm.registerDefaultTransition(receiveKnow,explore);
			
			
			fsm.registerTransition(openlock, MovetoTarget,1);
			fsm.registerTransition(openlock,donothing,2);
			fsm.registerTransition(openlock,openlock,3);
			fsm.registerDefaultTransition(Askforhelp,openlock);
			fsm.registerTransition(MovetoTarget, openlock,1);
			fsm.registerTransition(MovetoTarget, MovetoTarget,2);
			//fsm.registerTransition(MovetoTarget, donothing,3);


			//fsm.registerDefaultTransition(collect,goToHelp);
			//fsm.registerDefaultTransition(goToHelp,collect);
		    lb=new ArrayList<Behaviour>();
			lb.add(fsm);
		    /***
		     * MANDATORY TO ALLOW YOUR AGENT TO BE DEPLOYED CORRECTLY
		    */
		 	addBehaviour(new startMyBehaviours(this,lb));	
		 	System.out.println("the  agent "+this.getLocalName()+ " is started");
		}
}
