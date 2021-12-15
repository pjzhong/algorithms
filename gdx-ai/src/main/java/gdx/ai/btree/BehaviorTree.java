package gdx.ai.btree;

/**
 * The behavior tree itself.
 *
 * @param <E> type of the blackboard object that tasks use to read or modify game state
 * @author implicit-invocation
 * @author davebaol
 */
public class BehaviorTree<E> extends Task<E> {

  private Task<E> rootTask;
  private E object;

  public BehaviorTree() {
    this(null, null);
  }

  public BehaviorTree(Task<E> rootTask) {
    this(rootTask, null);
  }

  public BehaviorTree(Task<E> rootTask, E object) {
    this.rootTask = rootTask;
    this.object = object;
    this.tree = this;
  }

  @Override
  protected int addChildToTask(Task<E> child) {
    if (this.rootTask != null) {
      throw new IllegalStateException("A behavior tree cannot have more than one root task");
    }
    this.rootTask = child;
    return 0;
  }

  @Override
  public int getChildCount() {
    return rootTask == null ? 0 : 1;
  }

  @Override
  public Task<E> getChild(int i) {
    if (i == 0 && rootTask != null) {
      return rootTask;
    }
    throw new IndexOutOfBoundsException("index can't be >= size: " + i + " >= " + getChildCount());
  }

  @Override
  public void run() {
    if (rootTask.status != Status.RUNNING) {
      rootTask.setControl(this);
      rootTask.start();
    }
    rootTask.run();
  }

  @Override
  public void childSuccess(Task<E> task) {
    success();
  }

  @Override
  public void childFail(Task<E> task) {
    fail();
  }

  @Override
  public void childRunning(Task<E> runningTask, Task<E> reporter) {
    running();
  }

  @Override
  public void reset() {
    super.reset();
  }
}
