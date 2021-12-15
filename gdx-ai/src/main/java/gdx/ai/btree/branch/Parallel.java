package gdx.ai.btree.branch;

import gdx.ai.btree.BranchTask;
import gdx.ai.btree.Task;
import java.util.List;

/**
 * A {@code Parallel} is a special branch task that runs all children when stepped. Its actual
 * behavior depends on its {@link orchestrator} and {@link policy}.<br>
 * <br>
 * The execution of the parallel task's children depends on its {@link #orchestrator}:
 * <ul>
 * <li>{@link Orchestrator#Resume}: the parallel task restarts or runs each child every step</li>
 * <li>{@link Orchestrator#Join}: child tasks will run until success or failure but will not re-run until the parallel task has succeeded or failed</li>
 * </ul>
 *
 * The actual result of the parallel task depends on its {@link #policy}:
 * <ul>
 * <li>{@link Policy#Sequence}: the parallel task fails as soon as one child fails; if all children succeed, then the parallel
 * task succeeds. This is the default policy.</li>
 * <li>{@link Policy#Selector}: the parallel task succeeds as soon as one child succeeds; if all children fail, then the parallel
 * task fails.</li>
 * </ul>
 *
 * The typical use case: make the game entity react on event while sleeping or wandering.
 *
 * @param <E> type of the blackboard object that tasks use to read or modify game state
 * @author implicit-invocation
 * @author davebaol
 */
public class Parallel<E> extends BranchTask<E> {

  protected Policy policy;
  protected Orchestrator orchestrator;

  private boolean noRunningTasks;
  private Boolean lastResult;
  private int currentChildIndex;

  public Parallel(List<Task<E>> tasks) {
    this(Policy.Sequence, Orchestrator.Resume, tasks);
  }

  public Parallel(Policy policy, Orchestrator orchestrator, List<Task<E>> tasks) {
    super(tasks);
    this.orchestrator = orchestrator;
    this.policy = policy;
  }

  @Override
  public void run() {
    orchestrator.execute(this);
  }

  @Override
  public void childSuccess(Task<E> task) {
    lastResult = policy.onChildSuccess(this);
  }

  @Override
  public void childFail(Task<E> task) {
    lastResult = policy.onChildFail(this);
  }

  @Override
  public void childRunning(Task<E> runningTask, Task<E> reporter) {
    noRunningTasks = false;
  }

  public void resetAllChildren() {
    for (int i = 0, n = getChildCount(); i < n; i++) {
      Task<E> child = getChild(i);
      child.reset();
    }
  }

  /**
   * The enumeration of the child orchestrators supported by the {@link Parallel} task
   */
  public enum Orchestrator {
    Resume() {
      @Override
      public void execute(Parallel<?> parallel) {
        parallel.noRunningTasks = true;
        parallel.lastResult = null;
        final int size = parallel.children.size();
        for (parallel.currentChildIndex = 0; parallel.currentChildIndex < size;
            parallel.currentChildIndex++) {
          Task child = parallel.children.get(parallel.currentChildIndex);
          if (child.getStatus() == Status.RUNNING) {
            child.run();
          } else {
            child.setControl(parallel);
            child.start();
            child.run();
          }

          // current child has finished either with success or fail
          if (parallel.lastResult != null) {
            parallel.cancelRunningChildren(
                parallel.noRunningTasks ? parallel.currentChildIndex : 0);
            if (parallel.lastResult) {
              parallel.success();
            } else {
              parallel.fail();
            }
            return;
          }
        }

        parallel.running();
      }
    },

    Join() {
      @Override
      public void execute(Parallel<?> parallel) {
        parallel.noRunningTasks = true;
        parallel.lastResult = null;
        final int size = parallel.children.size();
        for (parallel.currentChildIndex = 0; parallel.currentChildIndex < size;
            parallel.currentChildIndex++) {
          Task child = parallel.children.get(parallel.currentChildIndex);

          switch (child.getStatus()) {
            case RUNNING:
              child.run();
              break;
            case SUCCEEDED:
            case FAILED:
              break;
            default:
              child.setControl(parallel);
              child.start();
              child.run();
              break;
          }

          if (parallel.lastResult != null) {
            parallel.cancelRunningChildren(
                parallel.noRunningTasks ? parallel.currentChildIndex + 1 : 0);
            parallel.resetAllChildren();
            if (parallel.lastResult) {
              parallel.success();
            } else {
              parallel.fail();
            }
            return;
          }

          parallel.running();
        }
      }
    };

    public abstract void execute(Parallel<?> parallel);
  }

  /** The enumeration of the policies supported by the {@link Parallel} task. */
  public enum Policy {
    Sequence() {
      /** The sequence policy makes the {@link Parallel} task fail as soon as one child fails; if all children succeed, then the
       * parallel task succeeds. This is the default policy. */
      public Boolean onChildSuccess(Parallel<?> parallel) {
        switch (parallel.orchestrator) {
          case Join:
            return parallel.noRunningTasks
                && parallel.children.get(parallel.children.size() - 1).getStatus()
                == Status.SUCCEEDED ? Boolean.TRUE : null;
          case Resume:
          default:
            return parallel.noRunningTasks
                && parallel.currentChildIndex == parallel.children.size() - 1 ? Boolean.TRUE : null;
        }
      }

      @Override
      public Boolean onChildFail(Parallel<?> parallel) {
        return Boolean.FALSE;
      }
    },


    Selector() {
      @Override
      public Boolean onChildSuccess(Parallel<?> eParallel) {
        return Boolean.TRUE;
      }

      @Override
      public Boolean onChildFail(Parallel<?> parallel) {
        return parallel.noRunningTasks && parallel.currentChildIndex == parallel.children.size() - 1
            ? Boolean.FALSE : null;
      }
    };

    /**
     * Called by parallel task each time one of its children succeeds.
     *
     * @param parallel the parallel task
     * @return {@code Boolean.TRUE} if parallel must succeed, {@code Boolean.FALSE} if parallel must
     * fail and {@code null} if parallel must keep on running.
     */
    public abstract Boolean onChildSuccess(Parallel<?> parallel);

    /**
     * Called by parallel task each time one of its children fails.
     *
     * @param parallel the parallel task
     * @return {@code Boolean.TRUE} if parallel must succeed, {@code Boolean.FALSE} if parallel must
     * fail and {@code null} if parallel must keep on running.
     */
    public abstract Boolean onChildFail(Parallel<?> parallel);
  }
}
