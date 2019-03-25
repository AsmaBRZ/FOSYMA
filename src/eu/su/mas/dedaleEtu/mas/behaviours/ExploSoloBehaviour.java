package eu.su.mas.dedaleEtu.mas.behaviours;
import eu.su.mas.dedaleEtu.mas.agents.dummies.ExploratorAgent;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation;
import jade.core.Agent;
import java.util.Iterator;
import java.util.List;
import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;	
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation.MapAttribute;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;


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
	private boolean finished=false;
	private static final long serialVersionUID = 8567689731496787661L;
	private int exitValue=1;
	/**
	 * Current knowledge of the agent regarding the environment
	 */
	private Agent agent;

	public ExploSoloBehaviour(final AbstractDedaleAgent myagent) {
		agent=myagent;
	}

	@Override
	public void action() {
		System.out.println(agent.getLocalName()+" I am exploring "+((AbstractDedaleAgent)this.myAgent).getCurrentPosition());
		((ExploratorAgent) agent).addHist(((AbstractDedaleAgent)this.myAgent).getCurrentPosition());
		List<String> hist=((ExploratorAgent) agent).getHist();
		int N=hist.size();
		if((N>1 && hist.get(N-1)==hist.get(N-2))){
			//interblocage --> informer les agents qui sont dans l'interblocage
			
		}
		if(((ExploratorAgent)agent).getMap()==null)
			((ExploratorAgent)agent).setMap(new MapRepresentation());

		//0) Retrieve the current position
		String myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();

		if (myPosition!=null){
			//List of observable from the agent's current position
			List<Couple<String,List<Couple<Observation,Integer>>>> lobs=((AbstractDedaleAgent)this.myAgent).observe();//myPosition
			//System.out.println("LOBS");
			//System.out.println(lobs);

			/**
			 * Just added here to let you see what the agent is doing, otherwise he will be too quick
			 */
			try {
				this.myAgent.doWait(2000);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//1) remove the current node from openlist and add it to closedNodes.
			((ExploratorAgent)agent).addClosedNode(myPosition);
			((ExploratorAgent)agent).removeOpenedNode(myPosition);

			((ExploratorAgent)agent).addNodeMap(myPosition);

			//2) get the surrounding nodes and, if not in closedNodes, add them to open nodes.
			String nextNode=null;
			Iterator<Couple<String, List<Couple<Observation, Integer>>>> iter=lobs.iterator();
			while(iter.hasNext()){
				String nodeId=iter.next().getLeft();
				if (!((ExploratorAgent)agent).containsClosedNode(nodeId)){
					//new node W
					if (!((ExploratorAgent)agent).containsOpenedNode(nodeId)){
						((ExploratorAgent)agent).addOpenedNode(nodeId);
						((ExploratorAgent)agent).addNodeMap(nodeId, MapAttribute.open);
						((ExploratorAgent)agent).addEdgeMap(myPosition, nodeId);
						//add only edge with the node B	
					}else{
						//the node exist, but not necessarily the edge
						((ExploratorAgent)agent).addEdgeMap(myPosition, nodeId);
					}
					if (nextNode==null) nextNode=nodeId;
				}
			}

			//3) while openNodes is not empty, continue.

			if (((ExploratorAgent)agent).isEmptyOpenedNodes()){
				System.out.println("Exploration successufully done, behaviour removed.");
			}else{
				//4) select next move.
				//4.1 If there exist one open node directly reachable, go for it,
				//	 otherwise choose one from the openNode list, compute the shortestPath and go for it
				if (nextNode==null){
					//no directly accessible openNode
					//chose one, compute the path and take the first step.
					nextNode=((ExploratorAgent)agent).getShortestPath(myPosition,((ExploratorAgent)agent).getOpenNodes().get(0)).get(0);
				}
				((AbstractDedaleAgent)this.myAgent).moveTo(nextNode);
			}
		}
		//System.out.println("Finished=true");
		//finished=true;
	}

/*
	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return finished;
	}*/

}
