package entity;

/**
 * 状态码实体类
 */
public class StatusCode {

    /**
     * 成功
     */
    public static final int OK = 20000;

    /**
     * 失败
     */
    public static final int ERROR = 20001;

    /**
     * 用户名和密码不匹配
     */
    public static final int LOGINERROR = 20002;

    /**
     * 权限不足
     */
    public static final int ACCESSERROR = 20003;

    /**
     * 远程调用失败
     */
    public static final int REMOTERROR = 20004;

    /**
     * 重复操作
     */
    public static final int REPEATERROR = 20005;

}
