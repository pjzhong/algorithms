package gdx.ai.btree;

import java.util.ArrayList;
import java.util.List;

/**
 * A branch task defines a behavior tree branch, contains logic of starting or running sub-branches
 * and leaves
 *
 * @param <E> type of the blackboard object that tasks use to read or modify game state
 * @author implicit-invocation
 * @author davebaol
 */
public abstract class BranchTask<E> extends Task<E> {

  /** The children of this branch task. */
  protected List<Task<E>> children;

  public BranchTask() {
    this(new ArrayList<>());
  }

  public BranchTask(List<Task<E>> tasks) {
    this.children = tasks;
  }

  @Override
  protected int addChildToTask(Task<E> child) {
    children.add(child);
    return children.size() - 1;
  }

  @Override
  protected int getChildCount() {
    return children.size();
  }

  @Override
  public Task<E> getChild(int i) {
    return children.get(i);
  }

  @Override
  public void reset() {
    if (children != null) {
      children.clear();
    }
    super.reset();
  }
}
