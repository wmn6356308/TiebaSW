package lxspider;

import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class VerifyTools
{
    //非对称密钥算法
    private static final String KEY_ALGORITHM = "SHA1withRSA";

    /**
     * 验签
     */
    public static boolean verify(byte[] message, byte[] signMessage, PublicKey publicKey) throws Exception {
        Signature signature;
        signature = Signature.getInstance(KEY_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(message);
        //String s = Base64.getDecoder().decode(signMessage).toString();
        //System.out.print(s);
        return signature.verify(Base64.getDecoder().decode(signMessage));
    }
}


