package gdx.ai.btree.branch;

import gdx.ai.btree.RandomSingleRunningChildBranch;
import gdx.ai.btree.Task;
import java.util.List;

/**
 * A {@code Selector} is a branch task that runs every children until one of them succeeds. If a
 * child task fails, the selector will start and run the next child task.
 *
 * @param <E> type of the blackboard object that tasks use to read or modify game state
 * @author implicit-invocation
 */
public class RandomSequence<E> extends RandomSingleRunningChildBranch<E> {

  /** current running child index */
  private int currentIndex;

  public RandomSequence(List<Task<E>> tasks) {
    super(tasks);
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
  public void childFail (Task<E> runningTask) {
    super.childFail(runningTask);
    fail(); // Return failure status when a child says it failed
  }
}
