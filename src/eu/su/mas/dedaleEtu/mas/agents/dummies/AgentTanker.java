package eu.su.mas.dedaleEtu.mas.agents.dummies;

import java.util.List;

import eu.su.mas.dedaleEtu.mas.behaviours.Donothing;
import eu.su.mas.dedaleEtu.mas.behaviours.ExploSoloBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.LocksmithBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.MovetoTarget;
import eu.su.mas.dedaleEtu.mas.behaviours.RandomSearchBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.communication.AskHelpCollect;
import eu.su.mas.dedaleEtu.mas.behaviours.communication.ReceiveHelpCollect;
import eu.su.mas.dedaleEtu.mas.behaviours.communication.ReceiveKnowledge;
import eu.su.mas.dedaleEtu.mas.behaviours.communication.SendKnwoledge;
import eu.su.mas.dedaleEtu.mas.behaviours.communication.SendStatAgents;
import jade.core.behaviours.FSMBehaviour;

public class AgentTanker extends MyAgent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Definition of states
	private static final String explore="ExploSoloBehaviour";
	private static final String receiveStatAgents="ReceiveStatAgents";
	private static final String sendStatAgents="SendStatAgents";
	private static final String sendKnow="SendKnowledge";
	private static final String receiveKnow="ReceiveKnowledge";
	private static final String mandatory="startMyBehaviours";
	private static final String donothing="Donothing ";

	private FSMBehaviour fsm ;

	@SuppressWarnings("unchecked")
	protected void setup(){
		super.setup();	
		
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
		fsm.registerState (new SendKnwoledge(this,receivers,this.openedNodes,this.closedNodes),sendKnow);
		fsm.registerState (new ReceiveKnowledge(this),receiveKnow);
		
		fsm.registerTransition(explore,sendKnow,1);
		fsm.registerTransition(explore,donothing,7);
		fsm.registerDefaultTransition(sendKnow,sendStatAgents);
		fsm.registerDefaultTransition(sendStatAgents,receiveKnow);
		fsm.registerDefaultTransition(receiveKnow,explore);
		fsm.registerDefaultTransition(donothing,sendKnow);
		
	}
}
