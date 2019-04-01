package eu.su.mas.dedaleEtu.mas.agents.dummies;
import java.util.Iterator;

import eu.su.mas.dedaleEtu.mas.behaviours.ExploSoloBehaviour;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.graphstream.graph.Node;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedale.mas.agent.behaviours.startMyBehaviours;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation.MapAttribute;
import eu.su.mas.dedaleEtu.mas.behaviours.communication.ReceiveKnowledge;
import eu.su.mas.dedaleEtu.mas.behaviours.communication.SendKnwoledge;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.FSMBehaviour;
public class ExploratorAgent  extends AbstractDedaleAgent   {
	private static final long serialVersionUID = 2384524762066236260L;
    //Current knowledge of the agent regarding the environment
	private MapRepresentation map;
	//Nodes known but not yet visited
	private List<String> openedNodes;
	//Visited nodes
	private Set<String> closedNodes;
	//List of receivers
	protected List<String> receivers;
	private List<String> myHistory=new ArrayList<String>();
	private List<Behaviour> lb;
	//type 1:Explore 2:collect
	private int type=1;
	private List<Couple<String,List<Couple<Observation,Integer>>>> objetcsFound;
	
	//Definition of states
	private static final String explore="ExploSoloBehaviour";
	private static final String collect="CollectBehaviour";
	private static final String sendKnow="SendKnowledge";
	private static final String receiveKnow="ReceiveKnowledge";
	//private static final String ping="Ping";
	//private static final String receivePing="ReceivePing";
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
		
		fsm.registerState (new CollectBehaviour(this), collect);
		fsm.registerState (new SendKnwoledge(this,receivers,this.openedNodes,this.closedNodes),sendKnow);
		fsm.registerState (new ReceiveKnowledge(this),receiveKnow);
		fsm.registerTransition(explore,sendKnow,1);
		fsm.registerTransition(explore,collect,2);
		fsm.registerDefaultTransition(sendKnow,receiveKnow);
		fsm.registerDefaultTransition(collect,collect);
		fsm.registerTransition(receiveKnow,explore,1);
		fsm.registerTransition(receiveKnow,collect,2);
	    lb=new ArrayList<Behaviour>();
		lb.add(fsm);
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
	public void deleteObjectFound(Couple<String,List<Couple<Observation,Integer>>>  e) {
		this.objetcsFound.remove(e);
	}
	public List<String> getSPath(String idFrom,String idTo){
		return this.map.getShortestPath(idFrom, idTo);
	}
}
