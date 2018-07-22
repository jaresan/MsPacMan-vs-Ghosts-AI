package game.controllers.pacman.exercises.e4.path.uninformed;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

import game.controllers.pacman.exercises.e4.path.PathFinderState;
import game.controllers.pacman.exercises.e4.path.uninformed.base.SearchTreeNode;
import game.controllers.pacman.exercises.e4.path.uninformed.base.UninformedGraphSearch;

/**
 * UNIFORM-COST-SEARCH
 * 
 * TODO:
 * 
 * To make this work, you have to implement {@link UninformedGraphSearch#step()}.
 */
public class UCS extends UninformedGraphSearch<Object> {

	@Override
	public String getName() {
		return "UCS[" + getSteps() + "]";
	}

	@Override
	public void reset() {
		super.reset();
	}
	
	@Override
	protected Collection<SearchTreeNode> createCloseList() {
		return new HashSet<SearchTreeNode>();
	}

	@Override
	protected Collection<SearchTreeNode> createOpenList() {		
		return new PriorityQueue<SearchTreeNode>(20, new Comparator<SearchTreeNode>() {

			@Override
			public int compare(SearchTreeNode o1, SearchTreeNode o2) {
				return o1.pathCost - o2.pathCost;
			}
			
		});
	}

	@Override
	protected SearchTreeNode selectNextNode(Collection<SearchTreeNode> openList) {
		return ((PriorityQueue<SearchTreeNode>)openList).peek();
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

		if (this.start == this.end || toExpand.node == this.end) {
			this.state = PathFinderState.PATH_FOUND;
			this.path = this.getFoundPath(toExpand);
			return this.state;
		}

		Collection<SearchTreeNode> newOpened = this.getNewOpened(toExpand);
		this.opened.addAll(newOpened);

		this.opened.remove(toExpand);
		this.closed.add(toExpand);

		return this.state;
	}

}
