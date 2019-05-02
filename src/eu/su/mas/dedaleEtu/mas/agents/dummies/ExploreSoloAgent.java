package eu.su.mas.dedaleEtu.mas.agents.dummies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedale.mas.agent.behaviours.startMyBehaviours;
import eu.su.mas.dedaleEtu.mas.behaviours.ExploSoloBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.communication.SendKnwoledge;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation;
import jade.core.behaviours.Behaviour;

/**
 * ExploreSolo agent. 
 * It explore the map using a DFS algorithm.
 * It stops when all nodes have been visited
 *  
 *  
 * @author hc
 *
 */

public class ExploreSoloAgent extends AbstractDedaleAgent {

	private static final long serialVersionUID = -6431752665590433727L;
	private MapRepresentation map;
	private List<String> openNodes;
	private Set<String> closedNodes;
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
		try {
			Thread.sleep(1000);
			System.out.println("I am sleeeping");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// the behaviour goes to sleep until the arrival of a new message in the agent's Inbox.
	
		//get the parameters given into the object[]
		final Object[] args = getArguments();
		if(args[0]!=null){
			receivers = (List<String>) args[2];
			//these data are currently not used by the agent, its just to show you how to get them if you need it 
		}else{
			System.out.println("Erreur lors du tranfert des parametres");
		}		

		List<Behaviour> lb=new ArrayList<Behaviour>();
		
		/************************************************
		 * 
		 * ADD the behaviours of the Dummy Moving Agent
		 * 
		 ************************************************/
		this.openNodes=new ArrayList<String>();
		this.closedNodes=new HashSet<String>();
		//lb.add(new ExploSoloBehaviour(this,this.map,this.openNodes,this.closedNodes));
/*	
		if(data!=null) {
			for(int i = 0 ; i < data.size(); i++) {
				lb.add(new SayHello(this,data.get(i)));
			}
		}
		else
			System.out.println("Warning: Data is empty!");
*/
	//	lb.add(new SendKnwoledge(this,receivers.get(0),this.map));	
		//lb.add(new ReceiveHello(this));

		/***
		 * MANDATORY TO ALLOW YOUR AGENT TO BE DEPLOYED CORRECTLY
		 */
		
		
		addBehaviour(new startMyBehaviours(this,lb));
		
		System.out.println("the  agent "+this.getLocalName()+ " is started");

	}

	public MapRepresentation getMap() {
		return map;
	}

	public void setMap(MapRepresentation myMap) {
		this.map = myMap;
	}
	
	
	
}
