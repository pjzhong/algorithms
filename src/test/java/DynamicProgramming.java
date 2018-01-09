import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * For more details, search for 背包九讲
 * X = 多少种物品
 * M = 第Xi种物品的可用数量
 * Y = 背包装量
 * G = 物品价值
 * C = 消耗
 * k = 拿取的数量
 * */
public class DynamicProgramming {

    @Test
    public void getClimbingWay() {
        int a = 111;
        int b = 20;

        System.out.printf(" %d  %d ", a, b);
        for(int i = 3; i <= 9; i++) {
            b = a + b;
            a = b - a;
            System.out.printf(" %d ", b);
        }
    }

    private int max = 0;

    @Test
    public void goldOres() {
        List<Item> items = new ArrayList<>();
        items.add(new Item(200, 2));
        items.add(new Item(300, 2));
        items.add(new Item(350, 1));
        items.add(new Item(4000, 3));
        items.add(new Item(5000, 5));

        maxGold(0, 0, 0, items);
        int result = getMostGold(10, items);
        int result2 = getMostGoldOptimizeSpace(10, items);
        System.out.println(max);
        System.out.println(result);
        System.out.println(result2);

        int completeResult = getMostGoldOptimizeSpaceComplete(10, items);
        System.out.println(completeResult);
    }


    /**
     * X = 矿脉数量
     * Y = 工人数量
     * X.G = 黄金数量
     * X.Y = 矿脉所需要的工人数
     * optimize this by using Dynamic Programming
     * 边界条件：
     *    当Y = 1， if(X => Y.X)  F(X, Y) = Y.G
     *    当Y = 1， if(X < Y.X) F(X, Y) = 0
     * 最优子结构(s)： 假设国王有x位矿工，并且发现了y座矿脉，每座矿脉需要y.x位矿工
     *     最终问题：F(x,y) = x座矿脉和y位矿工所能挖出最多的黄金
     *     1. F(x - 1, y) = (x - 1)座矿脉和y位矿工的所能挖出最多的黄金
     *     2. F(x - 1 , y - 1) = x - 1座矿脉 和 （y - x.y）座矿脉所能挖出的黄金加上当前矿脉的黄金
     *     ......
     * 状态转移公式：F(x, y ) = MAX(F(x - 1, y)， F(x - 1, y - x.y) + x.g)
     * 以上均为理论，与实现代码无直接关联
     *
     *
     * 下面代码的空间和时间复杂度O(workers & items.size)
     * */
    private int getMostGold(int workers, List<Item> items) {
        int[][] result = new int[items.size() + 1][workers + 1];
        Item item;
        for(int x = 1, size = items.size(); x <= size; x++) {
            item = items.get(x- 1);
            for(int y = 1; y <= workers; y++) {
                if(y >= item.cost) {
                    result[x][y] = Math.max(result[x - 1][y], result[x - 1][y - item.cost] + item.value);
                }
            }
        }

        return result[items.size()][workers];
    }

    /**
     * 优化空间复杂度，优化为O(workers)
     * 我首先遇到的问题是：在计算F(x, y)的时候，如何保证F(x - 1, y)和F(x - 1, y - x.y)的值可用
     * 这就要求我们要以 y <- workers ... 0 递减的顺序进行计算, 这样才能保证在计算F[y]的时候
     * F[y - x.y]保存的是F[x - 1, y - x.y]的值
     * */
    private int getMostGoldOptimizeSpace(int workers, List<Item> items) {
        int[] result = new int[workers + 1];
        Item item;
        for(int x = 0, size = items.size(); x < size; x++) {
            item = items.get(x);
            for(int y = workers; y >= item.cost; y--) {
                result[y] = Math.max(result[y], result[y - item.cost] + item.value);
            }
        }

        return result[workers];
    }

    /**
     * 完全背包问题
     * X = 矿脉数量
     * Y = 工人数量
     * G = 黄金数量
     * C = 矿脉所需要的工人数
     * F(X, Y) = MAX(F(X - 1, Y)， F(X, Y - C) + G)
     * */
    private int getMostGoldOptimizeSpaceComplete(int workers, List<Item> items) {
        int[] result = new int[workers + 1];
        Item item;
        for(int x = 0, size = items.size(); x < size; x++) {
            item = items.get(x);
            for(int y = item.cost; y <= workers; y++) {
                result[y] = Math.max(result[y], result[y - item.cost] + item.value);
            }
        }

        return result[workers];
    }

    private void maxGold(int start, int cost, int golds, List<Item> items) {
        for(int i = start, size = items.size(); i < size; i++) {
            Item item = items.get(i);
            if(cost + item.cost <= 10) {
                if(golds + item.value > max) { max = golds + item.value; }
                maxGold(i + 1, cost + item.cost, golds + item.value, items);
            }
        }
    }

    /**
     * X = 物品数量
     * Y = 背包装量
     * G = 物品价值
     * C = 消耗
     * F(X, Y) = MAX(F(X - 1, Y), F(X - 1, Y - C) + G);
     * */
    private int zeroOnePackage(int Bag, List<Item> items) {
        int result[] = new int[Bag + 1];

        for(int x = 0, size = items.size(); x < size; x++) {
            Item item = items.get(x);
            for(int y = Bag; y >= item.cost; y--) {
                result[y] = Math.max(result[y], result[y - item.cost] + item.value);
            }
        }

        return result[Bag];
    }

    /**
     * F(X, Y) = MAX(F(X - 1, Y - kC) + kG | 0 <= kC <= Y );
     * This code just little differ than zeroOnePackage question
     * 为什么这个算法就可行呢？首先想想为什么01背包中要按照v递减的次序来
       循环。让v递减是为了保证第i次循环中的状态 F[i, v]是由状态 F[i − 1, v − Ci]递推而来。
       换句话说，这正是为了保证每件物品只选一次，保证在考虑“选入第i件物品”这件策略时，
       依据的是一个绝无已经选入第i件物品的子结果 F[i − 1,v − Ci]。
       而现在完全背包的特点恰是每种物品可选无限件，所以在考虑“加选一件第i种物品”这种策略时，
       却正需要一个可能已选入第i种物品的子结果F [i, v − Ci]，
       所以就可以并且必须采用v递增的顺序循环。这就是这个简单的程序为何成立的道理。
     * */
    private int compltePackage(int Bag, List<Item> items) {
        int result[] = new int[Bag + 1];

        for(int x = 0, size = items.size(); x < size; x++) {
            Item item = items.get(x);
            for(int y = item.cost; y <= Bag; y++) {
                result[y] = Math.max(result[y], result[y - item.cost] + item.value);
            }
        }

        return result[Bag];
    }

    /**
     *
     * */
    public int multiplePackage(int Bag, List<Item> items) {
        return -1;
    }


    private class Item {
        private int value;
        private int cost;

        public Item(int value, int cost) {
            this.value = value;
            this.cost = cost;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Item item = (Item) o;

            if (value != item.value) return false;
            return cost == item.cost;

        }

        @Override
        public int hashCode() {
            int result = value;
            result = 31 * result + cost;
            return result;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Item{");
            sb.append("num=").append(value);
            sb.append(", cost=").append(cost);
            sb.append('}');
            return sb.toString();
        }
    }
}
