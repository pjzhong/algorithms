package rnd;

/**
 * 
 * 可权重接口
 * 
 * desc:拥有权重weight属性的类都可以实现该接口，用于统一根据权重随机抽取的行为
 * 
 * @since 2016年12月15日 下午4:01:38
 * @author levy
 */
public interface WeightAble<T> {

	/**
	 * 获取权重
	 * 
	 * @return
	 * @since 2016年12月21日 下午4:04:38
	 * @author levy
	 */
	int getWeight();

	/**
	 * 获取对应值
	 * 
	 * @return
	 * @since 2016年12月21日 下午4:05:30
	 * @author levy
	 */
	 T getV();
}
