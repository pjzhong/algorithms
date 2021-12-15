package gdx.ai.btree;

/**
 * A {@code LeafTask} is a terminal task of a behavior tree, contains action or condition logic, can
 * not have any child.
 *
 * @param <E> type of the blackboard object that tasks use to read or modify game state
 * @author implicit-invocation
 * @author davebaol
 */
public abstract class LeafTask<E> extends Task<E> {

  @Override
  protected int addChildToTask(Task<E> child) {
    throw new IllegalStateException("A leaf task cannot have any children");
  }

  @Override
  protected int getChildCount() {
    return 0;
  }

  @Override
  public Task<E> getChild(int i) {
    throw new IndexOutOfBoundsException("A leaf task can not have any child");
  }

  /**
   * This method contains the update logic of this leaf task. The actual implementation MUST return
   * one of {@link Status#RUNNING} , {@link Status#SUCCEEDED} or {@link Status#FAILED}. Other return
   * values will cause an {@code IllegalStateException}.
   *
   * @return the status of this leaf task
   */
  public abstract Status execute();

  @Override
  public void run() {
    Status result = execute();
    if (result == null) {
      throw new IllegalStateException("Invalid status 'null' returned by the execute method");
    }
    switch (result) {
      case SUCCEEDED:
        success();
        return;
      case FAILED:
        fail();
        return;
      case RUNNING:
        running();
        return;
      default:
        throw new IllegalStateException(
            "Invalid status '" + result.name() + "' returned by the execute method");
    }
  }

  @Override
  public final void childSuccess(Task<E> task) {
    throw new IndexOutOfBoundsException("A leaf task can not have any child");
  }

  @Override
  public final void childFail(Task<E> task) {

  }

  @Override
  public final void childRunning(Task<E> runningTask, Task<E> reporter) {

  }
}
