package lxspider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Scanner;

public class KeyTools
{
    /**
     * 通过keystore获取private key
     *
     */
    public static PrivateKey getPrivateKey() {

        FileInputStream is = null;
        PrivateKey privateKey = null;
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            is = new FileInputStream(new File(KeyConfig.PRIVATE_KEY_FILE_PATH));
            keyStore.load(is, KeyConfig.KEYSTORE_PASSWORD.toCharArray());
            privateKey = (PrivateKey) keyStore.getKey(KeyConfig.PRIVATE_ALIAS, KeyConfig.KEY_PASSWORD.toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return privateKey;
    }

    /**
     * 通过 cer证书获取公钥
     */
    public static PublicKey getPublicKeyFromCer(){
        PublicKey publicKey = null;
        FileInputStream in = null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            in = new FileInputStream(KeyConfig.CER_FILE_PATH);
            
            if(in.equals(null))
            {
            	System.out.println("没有找到证书，系统退出");
            	Scanner end = new Scanner(System.in);
            	end.nextLine();
            	System.exit(1);
            }
            
            Certificate c = cf.generateCertificate(in);
            publicKey = c.getPublicKey();

        } catch (CertificateException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return publicKey;
    }
    
}

