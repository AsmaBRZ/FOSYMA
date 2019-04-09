package eu.su.mas.dedaleEtu.mas.agents.dummies;
import java.util.Iterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.graphstream.graph.Node;
import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation.MapAttribute;
import eu.su.mas.dedaleEtu.mas.behaviours.communication.Triple;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.FSMBehaviour;
public class MyAgent extends AbstractDedaleAgent   {
	//agentName
	//communicationRange
	//initialLocation
	//BackPackCapacityGold
	//BackPackCapacityDiamond
	//detectionRadius
	//strengthExpertise
	//LockPickingExpertise
	private static final long serialVersionUID = 2384524762066236260L;
    //Current knowledge of the agent regarding the environment
	protected MapRepresentation map;
	//Nodes known but not yet visited
	protected List<String> openedNodes;
	//Visited nodes
	protected Set<String> closedNodes;
	//List of receivers (agents) 
	protected List<String> receivers;
	protected List<String> myHistory=new ArrayList<String>();
	protected List<Behaviour> lb;
	//type 1:Explore 2:collect 3: explore
	protected int type=1;
	protected List<Couple<String,List<Couple<Observation,Integer>>>> objetcsFound;
	protected FSMBehaviour fsm ;
	protected String role;
	protected void setup(){
		super.setup();	
	}

	public MapRepresentation getMap() {
		return map;
	}

	public void setMap(MapRepresentation myMap) {
		this.map = myMap;
	}
	public void updateKnowledge(String positionReceived,List<String> newOpenedNodes ,Set<String> newClosedNodes,List<String[]> newEdges) {
		Iterator<String> itClose=newClosedNodes.iterator();
		//I Check its opened nodes
		for(int i=0;i<newOpenedNodes.size();i++) {
			//The node is not closed 
			if(!closedNodes.contains((newOpenedNodes).get(i))) {
				if(!openedNodes.contains(newOpenedNodes.get(i))) {
					openedNodes.add(newOpenedNodes.get(i));
					addNodeMap(newOpenedNodes.get(i), MapAttribute.open);
				}
			}		
		}
		while(itClose.hasNext()){
			String node=itClose.next();
			if(openedNodes.contains(node)) {
				addClosedNode(node);
				removeOpenedNode(node);
				removeNodeMap(node);
				addNodeMap(node);
			}
			if(!openedNodes.contains(node) && !closedNodes.contains(node)) {
				closedNodes.add(node);
				addNodeMap(node);
			}
			
		}
		//Add edges
		for(int i=0;i<newEdges.size();i++) {
			if(!map.getEdges().contains(newEdges.get(i))) {
				map.getEdges().add(newEdges.get(i));
				addEdgeMap(newEdges.get(i)[0],newEdges.get(i)[1]);
			}
		}

	}
	public List<String> getOpenNodes() {
		return openedNodes;
	}
	public void setOpenedNodes(List<String> openNodes) {
		this.openedNodes = openNodes;
	}
	public Set<String> getClosedNodes() {
		return closedNodes;
	}
	public void setClosedNodes(Set<String> closedNodes) {
		this.closedNodes = closedNodes;
	}
	public void addClosedNode(String n) {
		closedNodes.add(n);
	}
	public void addOpenedNode(String n) {
		openedNodes.add(n);
	}
	public void removeClosedNode(String n) {
		closedNodes.remove(n);
	}
	public void removeOpenedNode(String n) {
		openedNodes.remove(n);
	}
	public void addNodeMap(String n) {
		map.addNode(n);
	}
	public void addEdgeMap(String idNode1, String idNode2) {
		map.addEdge(idNode1, idNode2);
	}
	public List<String> getShortestPathMap(String idFrom, String idTo) {
		return map.getShortestPath(idFrom, idTo);
	}
	public List<Node> getNodesMap() {
		return map.getNodes();
	}
	public boolean containsClosedNode(String o) {
		return closedNodes.contains(o);
	}
	public boolean containsOpenedNode(String o) {
		return openedNodes.contains(o);
	}
	public void addNodeMap(String id, MapAttribute mapAttribute) {
		map.addNode(id, mapAttribute);
	}
	public boolean isEmptyClosedNodes() {
		return closedNodes.isEmpty();
	}
	public boolean isEmptyOpenedNodes() {
		return openedNodes.isEmpty();
	}
	public List<String> getShortestPath(String idFrom, String idTo) {
		return map.getShortestPath(idFrom, idTo);
	}
	public List<String> getOpenedNodes() {
		return openedNodes;
	}
	public void addHist(String h) {
		this.myHistory.add(h);
	}
	public List<String> getHist() {
		return this.myHistory;
	}
	public void removeNodeMap(String id) {
		this.map.removeNode(id);
	}
	public int getType() {
		return this.type;
	}
	public void setType(int t) {
		this.type=t;
	}

	public List<Couple<String,List<Couple<Observation,Integer>>>>  getObjetcsFound() {
		return objetcsFound;
	}

	public void setObjetcsFound(List<Couple<String,List<Couple<Observation,Integer>>>>  objetcsFound) {
		this.objetcsFound = objetcsFound;
	}
	public void addObjectFound(Couple<String,List<Couple<Observation,Integer>>> e) {
		if(!this.objetcsFound.contains(e)) {
			this.objetcsFound.add(e);
		}
	}
	
	public void addObjectsFound(List<Couple<String,List<Couple<Observation,Integer>>>> e) {
		for(int i=0;i<e.size();i++) {
			this.objetcsFound.add(e.get(i));
		}
	}
	public void removeObjectsFound(Couple<String,List<Couple<Observation,Integer>>>  e) {
		this.objetcsFound.remove(e);
	}
	//remove observation from my list if I succeed picking it
	public void removeObjectFound(Triple<String,Observation,Integer> e) {
		String node=e.getLeft();
		Observation myObservation=e.getMiddle();
		Integer value=e.getRight();
		//List<Couple<String,List<Couple<Observation,Integer>>>>
		//je boucle sur les couples <String,List<Couple<Observation,Integer>>
		for(int i=0;i<this.objetcsFound.size();i++) {
			//<Couple<String,List<Couple<Observation,Integer>>>
			//je recupere la liste de chaque couple
			String target=this.objetcsFound.get(i).getLeft();
			if(target==node) {
				//serach for the observation
				for(int j=0;j<this.objetcsFound.get(i).getRight().size();j++) {
					// Couple<Observation,Integer>
					Observation obs=this.objetcsFound.get(i).getRight().get(j).getLeft();
					Integer val=this.objetcsFound.get(i).getRight().get(j).getRight();
					if(obs.equals(myObservation ) && val==value) {
						//we delete the observation that we picked ! 
						Couple<Observation,Integer> coupleToRemove= this.objetcsFound.get(i).getRight().get(j);
						this.objetcsFound.get(i).getRight().remove(coupleToRemove);
					}
				}	
			}
		}
	}
	public void updateObjsFound(List<Couple<String,List<Couple<Observation,Integer>>>> newObjsFound) {
		for (int i=0;i<newObjsFound.size();i++) {
			if(!this.objetcsFound.contains(newObjsFound.get(i))) {
				this.objetcsFound.add(newObjsFound.get(i));
			}
		}
	}
	public List<String> getSPath(String idFrom,String idTo){
		return this.map.getShortestPath(idFrom, idTo);
	}

	public List<String> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<String> receivers) {
		this.receivers = receivers;
	}

	public FSMBehaviour getFsm() {
		return fsm;
	}

	public void setFsm(FSMBehaviour fsm) {
		this.fsm = fsm;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}
