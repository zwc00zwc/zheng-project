package job.config;

/**
 * Created by alan.zheng on 2017/1/20.
 */
public enum JobCommand {
    /**
     * 启动
     */
    START("START"),
    /**
     * 暂停
     */
    PAUSE("PAUSE"),
    /**
     * 恢复
     */
    RESUME("RESUME")
    ;

    private String command;

    private JobCommand(String _command){
        command=_command;
    }

    public String getCommand() {
        return command;
    }
}
