package gdx.ai.btree.branch;

import gdx.ai.btree.SingleRunningChildBranch;
import gdx.ai.btree.Task;
import java.util.List;

/**
 * A {@code Sequence} is a branch task that runs every children until one of them fails. If a child
 * task succeeds, the selector will start and run the next child task.
 *
 * @param <E> type of the blackboard object that tasks use to read or modify game state
 * @author implicit-invocation
 */
public class Sequence<E> extends SingleRunningChildBranch<E> {

  /** current running child index */
  private int currentIndex;

  public Sequence(List<Task<E>> tasks) {
    super(tasks);
  }

  @Override
  public void childSuccess(Task<E> runningTask) {
    super.childSuccess(runningTask);
    if (++currentIndex < children.size()) {
      //Run next child
      run();
    } else {
      //All children processed, return success status
      success();
    }
  }


  @Override
  protected int nextChildIndex() {
    return currentIndex;
  }

  @Override
  public void start() {
    currentIndex = 0;
    super.start();
  }
}
