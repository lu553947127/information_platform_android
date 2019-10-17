package com.shuangduan.zcy.model.api.retrofit;

import com.shuangduan.zcy.app.MyApplication;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.model.api.retrofit
 * @ClassName: SSLSocketClient
 * @Description: 添加https证书
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/7 11:37
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/7 11:37
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SSLSocketClient {

    //获取这个SSLSocketFactory
    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("TLS");
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream in = MyApplication.getInstance().getAssets().open("app.zicai365.com.pem");// 这个puk.crt文件可以是16进制的也可以是Base64后的
            Certificate ca = cf.generateCertificate(in);
            KeyStore trustStore = null;
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            trustStore.setCertificateEntry("ca", ca);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | CertificateException | KeyStoreException | IOException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }
}
