package gdx.ai.btree;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A {@code SingleRunningChildBranch} task is a branch task that supports only one running child at
 * a time.
 *
 * handle common login for Sequence and Random  branch that run one task at a time
 *
 * @param <E> type of the blackboard object that tasks use to read or modify game state
 * @author implicit-invocation
 * @author davebaol
 */
public abstract class RandomSingleRunningChildBranch<E> extends BranchTask<E> {

  protected Task<E> runningChild;

  public RandomSingleRunningChildBranch() {
    super();
  }

  public RandomSingleRunningChildBranch(List<Task<E>> array) {
    super(array);
  }

  @Override
  public void run() {
    if (runningChild != null) {
      runningChild.run();
    } else {
      int currentChildIndex = nextChildIndex();
      int last = children.size();
      if (currentChildIndex < last) {
        // Random swap
        int otherChildIndex = ThreadLocalRandom.current().nextInt(currentChildIndex, last);
        Task<E> tmp = children.get(currentChildIndex);
        children.set(currentChildIndex, children.get(otherChildIndex));
        children.set(otherChildIndex, tmp);

        runningChild = children.get(currentChildIndex);
        runningChild.setControl(this);
        runningChild.start();
        runningChild.run();
      }
    }
  }

  /**
   * the next child index to run, may be sequence or random
   *
   * @since 2021年12月16日 00:42:12
   */
  protected abstract int nextChildIndex();

  @Override
  public void start() {
    runningChild = null;
  }

  @Override
  public void childSuccess(Task<E> task) {
    this.runningChild = null;
  }

  @Override
  public void childFail(Task<E> task) {
    this.runningChild = null;
  }

  @Override
  public void childRunning(Task<E> runningTask, Task<E> reporter) {
    runningChild = runningTask;
    running();
  }

  @Override
  public void reset() {
    this.runningChild = null;
    super.reset();
  }
}
