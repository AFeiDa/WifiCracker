package pers.afei.cmd;

/**
 * 一些与连接wifi有关的cmd命令
 */
public class Command {
    /**
     * 列出所有已保存的wlan的配置文件
     */
    public static final String SHOW_PROFILE = "netsh wlan show profile";    

    /**
     * 导出已保存wlan的配置文件，密码以明文显示
     */
    public static final String EXPORT_PRIFILE = "netsh wlan export profile key=clear";

    /**
     * 删除wlan配置文件，后面要加配置文件名或者*（*表示全部删除）
     */
    public static final String DELETE_PROFILE = "netsh wlan delete profile ";

    /**
     * 添加wlan配置文件，后面要加配置文件的路径
     */
    public static final String ADD_PROFILE = "netsh wlan add profile ";

    /**
     * 连接wifi，后面跟wifi名，连接前需要预先添加配置文件
     */
    public static final String CONNECT = "netsh wlan connect ";

    /**
     * 列出wlan接口(网卡)
     */
    public static final String SHOW_INTERFACE = "netsh wlan show interface";

    /**
     * 启用名为WLAN的网卡
     */
    public static final String OPEN_INTERFACE = "netsh interface set interface \"WLAN\" enabled"; 

    /**
     * 关闭名为WLAN的网卡
     */
    public static final String CLOSE_INTERFACE = "netsh interface set interface \"WLAN\" disabled"; 

    /**
     * 列出所有可用wifi
     */
    public static final String SHOW_NETWORKS = "netsh wlan show networks mode=bssid";
}