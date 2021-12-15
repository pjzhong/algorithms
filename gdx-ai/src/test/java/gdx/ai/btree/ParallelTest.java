package gdx.ai.btree;


import gdx.ai.btree.Task.Status;
import gdx.ai.btree.branch.Parallel;
import gdx.ai.btree.branch.Parallel.Orchestrator;
import gdx.ai.btree.branch.Parallel.Policy;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParallelTest {

  private BehaviorTree<String> behaviorTree;
  private TestTask<String> task1;
  private TestTask<String> task2;
  private List<Task<String>> tasks;

  @BeforeEach
  public void setUp() {
    behaviorTree = new BehaviorTree<>();
    task1 = new TestTask<>();
    task2 = new TestTask<>();
    tasks = Arrays.asList(task1, task2);
  }

  @Test
  public void smoke() {
    behaviorTree.addChildToTask(task1);
    behaviorTree.run();

    Assertions.assertEquals(1, task1.executions);
    Assertions.assertEquals(Status.RUNNING, task1.getStatus());
  }

  /**
   * Resume orchestrator - all tasks start or run on each step
   *
   * Sequence policy - all tasks have to succeed for the parallel to succeed
   */
  @Test
  public void testResumeOrchestratorSequencePolicy() {
    Parallel<String> parallel = new Parallel<>(Policy.Sequence, Orchestrator.Resume, tasks);
    behaviorTree.addChildToTask(parallel);
    behaviorTree.run();

    Assertions.assertEquals(1, task1.executions);
    Assertions.assertEquals(1, task2.executions);
    Assertions.assertEquals(Status.RUNNING, parallel.getStatus());

    task2.resStatus = Status.SUCCEEDED;
    behaviorTree.run();

    Assertions.assertEquals(2, task1.executions);
    Assertions.assertEquals(2, task2.executions);
    Assertions.assertEquals(Status.RUNNING, parallel.getStatus());

    behaviorTree.run();

    Assertions.assertEquals(3, task1.executions);
    Assertions.assertEquals(3, task2.executions);
    Assertions.assertEquals(Status.RUNNING, parallel.getStatus());

    task1.resStatus = Status.SUCCEEDED;
    behaviorTree.run();

    Assertions.assertEquals(4, task1.executions);
    Assertions.assertEquals(4, task2.executions);
    Assertions.assertEquals(Status.SUCCEEDED, parallel.getStatus());
  }

  /**
   * Resume orchestrator - all tasks start or run on each step
   *
   * Select policy - only one task has to succeed for the parallel task to succeed
   */
  @Test
  public void testResumeOrchestratorSelectorPolicy() {
    Parallel<String> parallel = new Parallel<>(Policy.Selector, Orchestrator.Resume, tasks);
    behaviorTree.addChildToTask(parallel);
    behaviorTree.run();

    Assertions.assertEquals(1, task1.executions);
    Assertions.assertEquals(1, task2.executions);
    Assertions.assertEquals(Status.RUNNING, parallel.getStatus());

    behaviorTree.run();
    Assertions.assertEquals(2, task1.executions);
    Assertions.assertEquals(2, task2.executions);
    Assertions.assertEquals(Status.RUNNING, parallel.getStatus());

    task1.resStatus = Status.SUCCEEDED;
    behaviorTree.run();
    Assertions.assertEquals(3, task1.executions);
    Assertions.assertEquals(2, task2.executions);
    Assertions.assertEquals(Status.SUCCEEDED, parallel.getStatus());

    // Resume orchestrator - all tasks start/run
    // Select policy - one task succeed, then parallel succeed
    behaviorTree.run();
    Assertions.assertEquals(4, task1.executions);
    Assertions.assertEquals(2, task2.executions);
    Assertions.assertEquals(Status.SUCCEEDED, parallel.getStatus());
  }

  /**
   * Join orchestrator - all tasks run until success/failure then don't run again until the parallel
   * task has succeeded or failed<br>
   *
   * Sequence policy - all tasks have to succeed for the parallel task to succeed
   */
  @Test
  public void testJoinOrchestratorSequencePolicySequentialOrder() {
    Parallel<String> parallel = new Parallel<>(Policy.Sequence, Orchestrator.Join, tasks);
    behaviorTree.addChildToTask(parallel);
    behaviorTree.run();

    Assertions.assertEquals(1, task1.executions);
    Assertions.assertEquals(1, task2.executions);
    Assertions.assertEquals(Status.RUNNING, parallel.getStatus());

    task1.resStatus = Status.SUCCEEDED;
    behaviorTree.run();

    Assertions.assertEquals(2, task1.executions);
    Assertions.assertEquals(2, task2.executions);
    Assertions.assertEquals(Status.RUNNING, parallel.getStatus());

    behaviorTree.run();

    // Join strategy - task 1 will not execute again until the parallel task succeeds or fails
    Assertions.assertEquals(2, task1.executions);
    Assertions.assertEquals(3, task2.executions);
    Assertions.assertEquals(Status.RUNNING, parallel.getStatus());

    task2.resStatus = Status.SUCCEEDED;
    behaviorTree.run();

    Assertions.assertEquals(2, task1.executions);
    Assertions.assertEquals(4, task2.executions);
    Assertions.assertEquals(Status.SUCCEEDED, parallel.getStatus());

    task1.resStatus = Status.RUNNING;
    task2.resStatus = Status.RUNNING;
    behaviorTree.run();
    Assertions.assertEquals(3, task1.executions);
    Assertions.assertEquals(5, task2.executions);
    Assertions.assertEquals(Status.RUNNING, parallel.getStatus());

    behaviorTree.run();
    Assertions.assertEquals(4, task1.executions);
    Assertions.assertEquals(6, task2.executions);
    Assertions.assertEquals(Status.RUNNING, parallel.getStatus());
  }

  @Test
  public void testJoinOrchestratorSequencePolicyInverseOrder() {
    Parallel<String> parallel = new Parallel<>(Policy.Sequence, Orchestrator.Join, tasks);
    behaviorTree.addChildToTask(parallel);
    behaviorTree.run();

    Assertions.assertEquals(1, task1.executions);
    Assertions.assertEquals(1, task2.executions);
    Assertions.assertEquals(Status.RUNNING, parallel.getStatus());

    task2.resStatus = Status.SUCCEEDED;
    behaviorTree.run();

    Assertions.assertEquals(2, task1.executions);
    Assertions.assertEquals(2, task2.executions);
    Assertions.assertEquals(Status.RUNNING, parallel.getStatus());

    behaviorTree.run();
    // Join strategy - task 1 will not execute again until the parallel task succeeds or fails
    Assertions.assertEquals(3, task1.executions);
    Assertions.assertEquals(2, task2.executions);
    Assertions.assertEquals(Status.RUNNING, parallel.getStatus());

    task1.resStatus = Status.SUCCEEDED;
    behaviorTree.run();

    Assertions.assertEquals(4, task1.executions);
    Assertions.assertEquals(2, task2.executions);
    Assertions.assertEquals(Status.SUCCEEDED, parallel.getStatus());

    task1.resStatus = Status.RUNNING;
    task2.resStatus = Status.RUNNING;
    behaviorTree.run();
    Assertions.assertEquals(5, task1.executions);
    Assertions.assertEquals(3, task2.executions);
    Assertions.assertEquals(Status.RUNNING, parallel.getStatus());

    behaviorTree.run();
    Assertions.assertEquals(6, task1.executions);
    Assertions.assertEquals(4, task2.executions);
    Assertions.assertEquals(Status.RUNNING, parallel.getStatus());
  }

  @Test
  public void testJoinOrchestratorSelectorPolicy() {
    Parallel<String> parallel = new Parallel<>(Policy.Selector, Orchestrator.Join, tasks);
    behaviorTree.addChildToTask(parallel);
    behaviorTree.run();


    Assertions.assertEquals(1, task1.executions);
    Assertions.assertEquals(1, task2.executions);
    Assertions.assertEquals(Status.RUNNING, parallel.getStatus());

    task1.resStatus = Status.FAILED;
    behaviorTree.run();

    Assertions.assertEquals(2, task1.executions);
    Assertions.assertEquals(2, task2.executions);
    Assertions.assertEquals(Status.RUNNING, parallel.getStatus());

    // Join strategy - task 1 will not execute again until the parallel task
    // succeeds or fails
    task2.resStatus = Status.SUCCEEDED;
    behaviorTree.run();

    Assertions.assertEquals(2, task1.executions);
    Assertions.assertEquals(3, task2.executions);
    Assertions.assertEquals(Status.SUCCEEDED, parallel.getStatus());

    behaviorTree.run();

    Assertions.assertEquals(3, task1.executions);
    Assertions.assertEquals(4, task2.executions);
    Assertions.assertEquals(Status.SUCCEEDED, parallel.getStatus());


    behaviorTree.run();

    Assertions.assertEquals(4, task1.executions);
    Assertions.assertEquals(5, task2.executions);
    Assertions.assertEquals(Status.SUCCEEDED, parallel.getStatus());
  }


  private static class TestTask<E> extends LeafTask<E> {

    Task.Status resStatus = Status.RUNNING;
    int executions = 0;

    @Override
    public Status execute() {
      executions++;
      return resStatus;
    }
  }

}
