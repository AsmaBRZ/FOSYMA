package eu.su.mas.dedaleEtu.mas.agents.dummies;

import java.util.ArrayList;
import java.util.List;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedale.mas.agent.behaviours.startMyBehaviours;

import eu.su.mas.dedaleEtu.mas.behaviours.RandomWalkBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.communication.SendKnwoledge;
import jade.core.behaviours.Behaviour;

/**
 * This example class start a Dummy agent that will possess two behaviours :
 * <ol>
 * <li> move randomly and test the API methods {@link RandomWalkBehaviour}.
 * <li> send a meaningless message to two other agents {@link SendKnwoledge} 
 * </ol>
 * @author hc
 *
 */
public class DummyMovingAgent extends AbstractDedaleAgent{

	private static final long serialVersionUID = -2991562876411096907L;
	protected List<String> receivers;

	/**
	 * This method is automatically called when "agent".start() is executed.
	 * Consider that Agent is launched for the first time. 
	 * 			1) set the agent attributes 
	 *	 		2) add the behaviours
	 *          
	 */
	protected void setup(){
		super.setup();

		//get the parameters representing the names of agents that i should communicate with
		final Object[] args = getArguments();
		if(args[0]!=null){
			receivers = (List<String>) args[0];
			//these data are currently not used by the agent, its just to show you how to get them if you need it 
		}else{
			System.out.println("Erreur lors du tranfert des parametres");
		}
		
		//use them as parameters for your behaviours is you want
		
		List<Behaviour> lb=new ArrayList<Behaviour>();
		
		/************************************************
		 * 
		 * ADD the behaviours of the Dummy Moving Agent
		 * 
		 ************************************************/
		lb.add(new RandomWalkBehaviour(this));
		if(receivers!=null) {
			System.out.println("Ma dataaaaaaaaaa");
			for(int i = 0 ; i < receivers.size(); i++) {
				System.out.println("I will try to say hello to Agent: "+receivers.get(i));
				//lb.add(new SayHello(this,receivers.get(i)));
			}
		}
		else
			System.out.println("Warning: Data is empty!");
		
		
		/***
		 * MANDATORY TO ALLOW YOUR AGENT TO BE DEPLOYED CORRECTLY
		 */
		
		addBehaviour(new startMyBehaviours(this,lb));

	}


	/**
	 * This method is automatically called after doDelete()
	 */
	protected void takeDown(){
		super.takeDown();
	}
	
	/**
	 * This method is automatically called before migration. 
	 * You can add here all the saving you need
	 */
	protected void beforeMove(){
		super.beforeMove();
	}
	
	/**
	 * This method is automatically called after migration to reload. 
	 * You can add here all the info regarding the state you want your agent to restart from 
	 * 
	 */
	protected void afterMove(){
		super.afterMove();
	}

}