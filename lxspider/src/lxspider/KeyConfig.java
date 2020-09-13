package lxspider;

public class KeyConfig {

    private static final String BASE_FILE_PATH = "./";
    //private static final String BASE_FILE_PATH = "";

    //私钥存放路径
    public static final String PRIVATE_KEY_FILE_PATH = BASE_FILE_PATH + "/privateKey.keystore";
    //公钥存放路径
    public static final String PUBLICKEY_FILE_PATH = BASE_FILE_PATH + "/publicKey.keystore";
    //Cer证书存放路径
    public static final String CER_FILE_PATH = BASE_FILE_PATH + "/publicCer.cer";

    //私钥别名
    public static final String PRIVATE_ALIAS = "privateKey";
    //公钥别名
    public static final String PUBLIC_ALIAS = "publicKey";
    //获取keystore所需的密码
    public static final String KEYSTORE_PASSWORD = "wmnTestPrivateKey";
    //获取私钥所需密码
    public static final String KEY_PASSWORD = "qwerpoiulkjhfdsa";
}
