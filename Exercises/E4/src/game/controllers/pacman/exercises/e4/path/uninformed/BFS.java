package game.controllers.pacman.exercises.e4.path.uninformed;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import game.controllers.pacman.exercises.e4.path.uninformed.base.SearchTreeNode;
import game.controllers.pacman.exercises.e4.path.uninformed.base.UninformedGraphSearch;

/**
 * BREADTH-FIRST-SEARCH
 *
 * TODO: 
 * 
 * To make this work, you have to implement {@link UninformedGraphSearch#step()}.
 */
public class BFS extends UninformedGraphSearch<Object> {

	@Override
	public String getName() {
		return "BFS[" + getSteps() + "]";
	}

	@Override
	protected Collection<SearchTreeNode> createCloseList() {
		return new HashSet<SearchTreeNode>();
	}

	@Override
	protected Collection<SearchTreeNode> createOpenList() {
		return new ArrayList<SearchTreeNode>();
	}

	@Override
	protected SearchTreeNode selectNextNode(Collection<SearchTreeNode> openList) {
		return ((List<SearchTreeNode>)openList).get(0);
	}

}
