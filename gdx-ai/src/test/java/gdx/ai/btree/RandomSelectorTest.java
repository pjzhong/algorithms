package gdx.ai.btree;

import gdx.ai.btree.Task.Status;
import gdx.ai.btree.branch.RandomSelector;
import gdx.ai.btree.branch.Selector;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class RandomSelectorTest {


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

  @RepeatedTest(10)
  public void randomSelector() {
    RandomSelector<String> sequence = new RandomSelector<>(tasks);
    behaviorTree.addChildToTask(sequence);

    behaviorTree.run();
    Assertions.assertEquals(1, task1.executions + task2.executions);
    Assertions.assertEquals(Status.RUNNING, sequence.getStatus());


    behaviorTree.run();
    Assertions.assertEquals(2, task1.executions + task2.executions);
    Assertions.assertEquals(Status.RUNNING, sequence.getStatus());

    task1.resStatus = Status.SUCCEEDED;
    task2.resStatus = Status.SUCCEEDED;

    behaviorTree.run();
    Assertions.assertEquals(3, task1.executions + task2.executions);
    Assertions.assertEquals(Status.SUCCEEDED, sequence.getStatus());

    task1.resStatus = Status.FAILED;
    task2.resStatus = Status.SUCCEEDED;
    behaviorTree.run();
    Assertions.assertTrue(4 <= task1.executions + task2.executions);
    Assertions.assertTrue(task1.executions + task2.executions <= 5);
    Assertions.assertEquals(Status.SUCCEEDED, sequence.getStatus());

    task1.resStatus = Status.RUNNING;
    task2.resStatus = Status.RUNNING;
    behaviorTree.run();
    Assertions.assertTrue(5 <= task1.executions + task2.executions);
    Assertions.assertTrue(task1.executions + task2.executions <= 6);
    Assertions.assertEquals(Status.RUNNING, sequence.getStatus());


    task1.resStatus = Status.FAILED;
    task2.resStatus = Status.SUCCEEDED;
    behaviorTree.run();
    Assertions.assertTrue(6 <= task1.executions + task2.executions);
    Assertions.assertTrue(task1.executions + task2.executions <= 8);
    Assertions.assertEquals(Status.SUCCEEDED, sequence.getStatus());


    task1.resStatus = Status.RUNNING;
    task2.resStatus = Status.RUNNING;
    behaviorTree.run();
    Assertions.assertTrue(task1.executions + task2.executions <= 9);
    Assertions.assertEquals(Status.RUNNING, sequence.getStatus());
  }

  private static class TestTask<E> extends LeafTask<E> {

    Status resStatus = Status.RUNNING;
    int executions = 0;

    @Override
    public Status execute() {
      executions++;
      return resStatus;
    }
  }

}
