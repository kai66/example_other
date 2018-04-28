package widget;

/**
 * Created by kai on 2018/1/13.
 */

public  @interface OnKeyType {
    /**
     * 隐藏对话框 - 结束Activity
     */
    public static final int DISMISS_KILL_ACTIVITY = 0;
    /**
     * 隐藏对话框 - 不结束Activity
     */
    public static final int DISMISS_NOT_KILL_ACTIVITY = 1;
    /**
     * 不隐藏对话框 - 不结束Activity
     */
    public static final int NOT_DISMISS_NOT_KILL_ACTIVITY = 2;

}
