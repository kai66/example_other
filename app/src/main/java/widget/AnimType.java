package widget;

/**
 * Created by kai on 2018/1/13.
 */

public @interface AnimType {
    /**
     * 居中 无效果
     */
    public static final int CENTER_NORMAL = 0;
    /**
     * 居中缩放
     * 进入：居中，由小变大
     * 退出：居中，由大变小
     */
    public static final int CENTER_SCALE = 1;
    /**
     * 底部进入
     * 进入：从底部，到顶部
     * 退出：从顶部，到底部
     */
    public static final int BOTTOM_2_TOP = 2;
    /**
     * 顶部进入
     * 进入：从顶部，到底部
     * 退出：从底部，到顶部
     */
    public static final int TOP_2_BOTTOM = 3;


}
