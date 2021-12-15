package gdx.ai.btree;

public abstract class Task<E> {


  public enum Status {
    FRESH, RUNNING, FAILED, SUCCEEDED, CANCELLED;
  }

  /** The status of this task. */
  protected Status status = Status.FRESH;

  /** The parent of this task */
  protected Task<E> control;

  /** The behavior tree this task belongs to. */
  protected BehaviorTree<E> tree;

  /**
   * This method will add a child to the list of this task's children
   */
  protected abstract int addChildToTask(Task<E> child);

  /**
   * Returns the number of children of this task
   */
  protected abstract int getChildCount();

  /**
   * Returns the child at the given index.
   */
  public abstract Task<E> getChild(int i);

  /**
   * Return the status of this task.
   */
  public final Status getStatus() {
    return status;
  }

  /**
   * This method will set a task as this task's control(parent)
   *
   * @param control the parent task
   */
  public final void setControl(Task<E> control) {
    this.control = control;
    this.tree = control.tree;
  }

  /** This method will be called once before this task's first run. */
  public void start() {
  }

  /**
   * This method will be called by {@link Task#success()}, {@link #fail()} or {@link #cancel()},
   * meaning that this task's status has just been set to {@link Status#SUCCEEDED}, {@link
   * Status#FAILED} or {@link Status#CANCELLED} respectively.
   */
  public void end() {
  }

  /**
   * This method contains the update logic of this task. The actual implementation MUST call {@link
   * #running()}, {@link #success()} or {@link #fail()} exactly once.
   */
  public abstract void run();

  /**
   * This method will be called in {@link #run()} to inform control that this task needs to run
   * again
   */
  public final void running() {
    status = Status.RUNNING;
    if (control != null) {
      control.childRunning(this, this);
    }
  }

  /**
   * This method will be called in {@link #run()} to inform control that this task has finished
   * running with a success result
   */
  public final void success() {
    status = Status.SUCCEEDED;
    end();
    if (control != null) {
      control.childSuccess(this);
    }
  }

  /**
   * This method will be called in {@link #run()} to inform control that this task has finished
   * running with a failure result
   */
  public final void fail() {
    status = Status.FAILED;
    end();
    if (control != null) {
      control.childFail(this);
    }
  }


  /**
   * This method will be called when one of the children of this task succeeds
   *
   * @param task the task that succeeded
   */
  public abstract void childSuccess(Task<E> task);

  /**
   * This method will be called when one of the children of this task fails
   *
   * @param task the task that failed
   */
  public abstract void childFail(Task<E> task);

  /**
   * This method will be called when one of the ancestors of this task needs to run again
   *
   * @param runningTask the task that needs to run again
   * @param reporter the task that reports, usually one of this task's children
   */
  public abstract void childRunning(Task<E> runningTask, Task<E> reporter);

  /**
   * Terminates this task and all its running children. This method MUST be called only if this task
   * is running.
   */
  public final void cancel() {
    status = Status.CANCELLED;
    end();
  }


  /**
   * Terminates the running children of this task starting from the specified index up to the end.
   *
   * @param startIndex the start index
   */
  protected void cancelRunningChildren(int startIndex) {
    for (int i = startIndex, n = getChildCount(); i < n; i++) {
      Task<E> child = getChild(i);
      if (child.status == Status.RUNNING) {
        child.cancel();
      }
    }
  }

  public void reset() {
    control = null;
    tree = null;
    status = Status.FRESH;
  }

}
