package eu.su.mas.dedaleEtu.mas.behaviours;
import eu.su.mas.dedaleEtu.mas.agents.dummies.AgentCollect;
import eu.su.mas.dedaleEtu.mas.agents.dummies.MyAgent;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation;
import jade.core.Agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;	
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation.MapAttribute;
import jade.core.behaviours.OneShotBehaviour;


/**
 * This behaviour allows an agent to explore the environment and learn the associated topological map.
 * The algorithm is a pseudo - DFS computationally consuming because its not optimised at all.</br>
 * 
 * When all the nodes around him are visited, the agent randomly select an open node and go there to restart its dfs.</br> 
 * This (non optimal) behaviour is done until all nodes are explored. </br> 
 * 
 * Warning, this behaviour does not save the content of visited nodes, only the topology.</br> 
 * Warning, this behaviour is a solo exploration and does not take into account the presence of other agents (or well) and indefinitely tries to reach its target node
 * @author hc
 *
 */
public class ExploSoloBehaviour extends OneShotBehaviour{
	private static final long serialVersionUID = 8567689731496787661L;
	/**
	 * Current knowledge of the agent regarding the environment
	 */
	private Agent agent;
	private int exitValue;
	public ExploSoloBehaviour(final AbstractDedaleAgent myagent) {
		this.agent=myagent;
	}
	@Override
	public void action() {
		System.out.println(agent.getLocalName()+" I am exploring "+((AbstractDedaleAgent)this.myAgent).getCurrentPosition());
		//System.out.println(agent.getLocalName()+" My list of open nodes"+((MyAgent)this.myAgent).getOpenedNodes().toString());
		((MyAgent) agent).addHist(((AbstractDedaleAgent)this.myAgent).getCurrentPosition());
		List<String> hist=((MyAgent) agent).getHist();
		int N=hist.size();
		if((N>1 && hist.get(N-1)==hist.get(N-2))){//and list open not empty
			//interblocage --> informer les agents qui sont dans l'interblocage		
		}
		if(((MyAgent)agent).getMap()==null)
			((MyAgent)agent).setMap(new MapRepresentation());

		//0) Retrieve the current position
		String myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
		if (myPosition!=null){
			//List of observable from the agent's current position
			List<Couple<String,List<Couple<Observation,Integer>>>> lobs=((AbstractDedaleAgent)this.myAgent).observe();//myPosition
			//System.out.println(lobs);
			//If there are any observations, I add them to my list of objects found
			for(int i=0;i<lobs.size();i++){
				Couple<String,List<Couple<Observation,Integer>>> element=lobs.get(i);
				if(element.getRight().size()>1) {
					((MyAgent)this.myAgent).addObjectFound(element);
				}
			}
			try {
				this.myAgent.doWait(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//1) remove the current node from openlist and add it to closedNodes.
			((MyAgent)agent).addClosedNode(myPosition);
			((MyAgent)agent).removeOpenedNode(myPosition);
			((MyAgent)agent).addNodeMap(myPosition);

			//2) get the surrounding nodes and, if not in closedNodes, add them to open nodes.
			String nextNode=null;
			Iterator<Couple<String, List<Couple<Observation, Integer>>>> iter=lobs.iterator();
			while(iter.hasNext()){
				String nodeId=iter.next().getLeft();
				if (!((MyAgent)agent).containsClosedNode(nodeId)){
					//new node W
					if (!((MyAgent)agent).containsOpenedNode(nodeId)){
						((MyAgent)agent).addOpenedNode(nodeId);
						((MyAgent)agent).addNodeMap(nodeId, MapAttribute.open);
						((MyAgent)agent).addEdgeMap(myPosition, nodeId);
						//add only edge with the node B	
					}else{
						//the node exist, but not necessarily the edge
						((MyAgent)agent).addEdgeMap(myPosition, nodeId);
					}
					if (nextNode==null) nextNode=nodeId;
				}
			}

			//3) while openNodes is not empty, continue.
			System.out.println(agent.getLocalName()+" my list stat ag "+((MyAgent)this.agent).getStatAgents().toString());
			if (((MyAgent)agent).isEmptyOpenedNodes()){	
				((MyAgent)this.agent).setMYStatAgent();
				System.out.println("MYYYY Exploration  successufully done");

				if(((MyAgent)this.agent).exploGlobalDone()){
					
					
					((MyAgent)agent).setObjetcsFound(((MyAgent)agent).treasure_sorted());
					System.out.println("Exploration GLOBALLLLLLE successufully done, behaviour removed.");
					
					
					//only collector  moves from explo to collect
					if(agent instanceof eu.su.mas.dedaleEtu.mas.agents.dummies.AgentExplo) {
						System.out.println("I am "+ agent.getLocalName()+" I Move to openLock car je suis un explorateur");
						this.exitValue=2;
					}
					//only explo moves to randaom searching for the moment :D
					if(agent instanceof eu.su.mas.dedaleEtu.mas.agents.dummies.AgentCollect) {
						System.out.println("I am "+ agent.getLocalName()+" I Move to random exploration car je suis un collecteur");
						this.exitValue=3;
					}
					if(agent instanceof eu.su.mas.dedaleEtu.mas.agents.dummies.AgentTanker) {
						System.out.println("I am "+ agent.getLocalName()+" I m Silo I wait for Ti");
						this.exitValue=7;
					}
				}
				//si tous le monde n'a pas fini
				else{
					this.exitValue=1;
				}
			//si je n'ai pas fini
			}else{
				this.exitValue=1;
				//4) select next move.
				//4.1 If there exist one open node directly reachable, go for it,
				//	 otherwise choose one from the openNode list, compute the shortestPath and go for it
				if (nextNode==null){
					//no directly accessible openNode
					//chose one, compute the path and take the first step.
					//System.out.println(agent.getLocalName()+" Before compute"+((AbstractDedaleAgent)this.myAgent).getCurrentPosition());
					nextNode=((MyAgent)agent).getShortestPath(myPosition,((MyAgent)agent).getOpenNodes().get(0)).get(0);
				//	System.out.println(agent.getLocalName()+" After compute "+((AbstractDedaleAgent)this.myAgent).getCurrentPosition());
				}
				((AbstractDedaleAgent)this.myAgent).moveTo(nextNode);
			}
		}
		//System.out.println("Finished=true");
		//finished=true;
	}
	
	@Override
	public int onEnd() {
		return this.exitValue;
	}
/*
	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return finished;
	}*/

}
