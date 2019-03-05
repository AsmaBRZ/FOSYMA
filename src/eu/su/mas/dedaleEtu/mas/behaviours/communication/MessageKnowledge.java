package eu.su.mas.dedaleEtu.mas.behaviours.communication;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation;

public class MessageKnowledge  implements Serializable{


	private static final long serialVersionUID = -151755830024515985L;
	private String position;
	//Nodes known but not yet visited
	private List<String> openNodes;
	//Visited nodes
	private Set<String> closedNodes;
	private List<String[]> edges;
	
	
	public MessageKnowledge(String p, List<String> openNodes, Set<String> closedNodes,List<String[]> l) {
		super();
		this.position=p;
		this.openNodes = openNodes;
		this.closedNodes = closedNodes;
		this.edges=l;
	}

	public List<String> getOpenNodes() {
		return openNodes;
	}

	public void setOpenNodes(List<String> openNodes) {
		this.openNodes = openNodes;
	}

	public Set<String> getClosedNodes() {
		return closedNodes;
	}

	public void setClosedNodes(Set<String> closedNodes) {
		this.closedNodes = closedNodes;
	}
	

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public List<String[]> getEdges() {
		return edges;
	}
	public void setEdges(List<String[]> edges) {
		this.edges = edges;
	}


}
