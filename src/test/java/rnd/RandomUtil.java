package rnd;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.Random;

public class RandomUtil {

	private final static ThreadLocal<SecureRandom> secureRandomThreadLocal = new ThreadLocal<SecureRandom>();

	private static Random getRandom() {
		SecureRandom random = secureRandomThreadLocal.get();
		if (random == null) {
			random = new SecureRandom();
			secureRandomThreadLocal.set(random);
		}
		return random;
	}

	public static float nextFloat() {
		return getRandom().nextFloat();
	}

	/**
	 * 区间中随机取值
	 * 
	 * @param min
	 * @param max
	 * @param rate
	 *            因子 [保留3位数,rate为1000]
	 * @return
	 * @since 2017年8月2日 下午4:29:41
	 * @author levy
	 */
	public static double random(double min, double max, double rate) {
		int x = (int) (min * rate);
		int y = (int) (max * rate);
		int r = 0;
		if (x < 0) {
			int shift = 0 - x;
			y += shift;
			x = 0;
			r = nextInt(y - x + 1) + x - shift;
		} else {
			r = nextInt(y - x + 1) + x;
		}
		return r / rate;
	}

	/**
	 * 随机一个小于max的int值
	 * 
	 * @param max
	 * @return
	 * @since 2016年4月29日 上午11:39:40
	 */
	public static int nextInt(int max) {
		return getRandom().nextInt(max);
	}

	/**
	 * 根据一个范围[min,max],生成一个随机的整数
	 * 
	 * @param min
	 * @param max
	 * @return
	 * @since 2017年4月17日 上午11:15:37
	 * @author PCY
	 */
	public static int rnd(int min, int max) {
		return getRandom().nextInt(max - min + 1) + min;
	}

	/**
	 * 计算成功率
	 *
	 * @param ratio
	 *            成功率
	 * @return 返回是否成功
	 * @since 2018年11月21日 16:08:42
	 * @author ZJP
	 */
	public static boolean calcSucRate(double ratio) {
		double random = getRandom().nextDouble();
		return Double.compare(random, ratio) <= 0;
	}

	/**
	 * 根据权重随机出值
	 * 
	 * @param list
	 * @return
	 * @since 2016年12月12日 下午3:13:38
	 * @author levy
	 */
	public static <T> T random(Collection<? extends WeightAble<T>> list) {
		return random(list,
				list.stream().mapToInt(WeightAble::getWeight).sum());
	}

	/**
	 * 根据权重随机出值
	 *
	 * @param list
	 * @return
	 * @since 2016年12月12日 下午3:13:38
	 * @author levy
	 */
	public static <T> T random(Collection<? extends WeightAble<T>> list,
			int sum) {
		int random = nextInt(sum);
		int partSum = 0;
		for (WeightAble<T> wa : list) {
			partSum += wa.getWeight();
			if (random < partSum)
				return wa.getV();
		}
		return null;
	}

}
