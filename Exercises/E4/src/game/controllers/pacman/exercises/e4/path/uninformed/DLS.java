package game.controllers.pacman.exercises.e4.path.uninformed;

import game.controllers.pacman.exercises.e4.graph.Graph;
import game.controllers.pacman.exercises.e4.graph.Link;
import game.controllers.pacman.exercises.e4.graph.Node;
import game.controllers.pacman.exercises.e4.path.PathFinderState;
import game.controllers.pacman.exercises.e4.path.uninformed.DLS.DLSConfig;
import game.controllers.pacman.exercises.e4.path.uninformed.base.SearchTreeNode;
import game.controllers.pacman.exercises.e4.path.uninformed.base.UninformedGraphSearch;

/**
 * DEPTH-LIMITED-SEARCH
 * 
 * TODO:
 * 
 * To make this work you need to:
 * 1) implement {@link DFS},
 * 2) which means to implement {@link UninformedGraphSearch#step()}.
 */
public class DLS extends DFS<DLSConfig> {
	
	private boolean depthLimitHit = false;
	
	public static class DLSConfig {
		
		public int depthLimit;

		public DLSConfig(int depthLimit) {
			this.depthLimit = depthLimit;
		}
				
	}
	
	@Override
	public String getName() {
		return "DLS[limit=" + (config == null ? "null" : config.depthLimit) + "," + getSteps() + "]";
	}
	
	@Override
	public void init(Graph graph, Node start, Node goal, DLSConfig config) {
		super.init(graph, start, goal, config);
		depthLimitHit = false;
	}
	
	@Override
	protected SearchTreeNode makeSearchNode(Node node, int pathCost, SearchTreeNode parent) {
		if (parent != null && config != null && parent.level + 1 > config.depthLimit) {
			depthLimitHit = true;
			return null;			
		}
		return super.makeSearchNode(node, pathCost, parent);
	}

	@Override
	public PathFinderState step() {
		if (state != PathFinderState.RUNNING) return state;

		++steps;
		if (opened.size() == 0) {
			this.state = PathFinderState.PATH_NOT_FOUND;
			return this.state;
		}

		SearchTreeNode toExpand = this.selectNextNode(this.opened);

		for (Link link : toExpand.node.links.values()) {
			SearchTreeNode target =
					this.makeSearchNode(
							link.getOtherEnd(toExpand.node),
							toExpand.pathCost + link.distance,
							toExpand
					);

			if (target == null) {
				continue;
			}

			if (!this.closed.contains(target) && !this.opened.contains(target)) {
				this.opened.add(target);

				if (target.node == end) {
					this.state = PathFinderState.PATH_FOUND;
					this.path = this.getFoundPath(target);
				}
			}
		}

		if (toExpand.level != config.depthLimit) {
			this.closed.add(toExpand);
		}
		this.opened.remove(toExpand);

		return this.state;
	}

	public boolean isDepthLimitHit() {
		return depthLimitHit;
	}
	
}
