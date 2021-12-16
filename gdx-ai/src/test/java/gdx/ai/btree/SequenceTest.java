package gdx.ai.btree;

import gdx.ai.btree.Task.Status;
import gdx.ai.btree.branch.Sequence;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SequenceTest {


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
  public void sequence() {
    Sequence<String> sequence = new Sequence<>(tasks);
    behaviorTree.addChildToTask(sequence);

    behaviorTree.run();
    Assertions.assertEquals(1, task1.executions);
    Assertions.assertEquals(0, task2.executions);
    Assertions.assertEquals(Status.RUNNING, sequence.getStatus());


    task1.resStatus = Status.SUCCEEDED;
    behaviorTree.run();
    Assertions.assertEquals(2, task1.executions);
    Assertions.assertEquals(1, task2.executions);
    Assertions.assertEquals(Status.RUNNING, sequence.getStatus());


    task2.resStatus = Status.SUCCEEDED;
    behaviorTree.run();
    Assertions.assertEquals(2, task1.executions);
    Assertions.assertEquals(2, task2.executions);
    Assertions.assertEquals(Status.SUCCEEDED, sequence.getStatus());

    behaviorTree.run();
    Assertions.assertEquals(3, task1.executions);
    Assertions.assertEquals(3, task2.executions);
    Assertions.assertEquals(Status.SUCCEEDED, sequence.getStatus());


    task1.resStatus = Status.RUNNING;
    task2.resStatus = Status.RUNNING;
    behaviorTree.run();
    Assertions.assertEquals(4, task1.executions);
    Assertions.assertEquals(3, task2.executions);
    Assertions.assertEquals(Status.RUNNING, sequence.getStatus());
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
