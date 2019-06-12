package com.test.httpszuul.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http2.Http2Protocol;
import org.apache.tomcat.util.net.SSLHostConfig;
import org.apache.tomcat.util.net.SSLHostConfigCertificate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * @author wufan
 * @date 2019/6/3 17:58
 */
@Component
public class MultipleCerConfig {
    @Value("${https.port}")
    private Integer port;

    @Value("${https.baidu.key-store-password}")
    private String key_store_password;

    @Value("${https.baidu.key-password}")
    private String key_password;

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createSslConnector());
        return tomcat;
    }

    /**
    * 给 SSLHostConfig 加证书
    */
    private SSLHostConfigCertificate getCert(SSLHostConfig sslHostConfig,String alias,String key_store_name, String key_store_password,String key_password) throws IOException {
        File keystore = new ClassPathResource(key_store_name).getFile();
        SSLHostConfigCertificate cert =
                new SSLHostConfigCertificate(sslHostConfig, SSLHostConfigCertificate.Type.RSA);
        cert.setCertificateKeystoreFile(keystore.getAbsolutePath());
//        cert.setCertificateKeyAlias(alias);
        cert.setCertificateKeystorePassword(key_store_password);
        cert.setCertificateKeyPassword(key_password);
        return cert;
    }

    /**
     * 根据不同的 hostname 配置不同的 SSLHostConfig
     */
    private SSLHostConfig getSSLHostConfig(String hostname,String key_store_name, String key_store_password,String key_password) throws IOException {
        SSLHostConfig sslHostConfig = new SSLHostConfig();
        //  根据不同的需求，给证书添加 hostname
        sslHostConfig.setHostName(hostname);
//        sslHostConfig.set
        SSLHostConfigCertificate cert = getCert(sslHostConfig,"tomcat",key_store_name ,key_store_password,key_password);
        sslHostConfig.addCertificate(cert);
        return sslHostConfig;
    }

    /**
     * 配置多证书
     */
    private Connector createSslConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        try {
            SSLHostConfig defaultSslHostConfig = new SSLHostConfig();
            SSLHostConfigCertificate cert = getCert(defaultSslHostConfig,"default","htx-server.jks" ,key_store_password,key_password);
            defaultSslHostConfig.addCertificate(cert);


            SSLHostConfig sslHostConfig1 = getSSLHostConfig("www.baidu.com","htx-server.jks" ,key_store_password,key_password);
            SSLHostConfig sslHostConfig2 = getSSLHostConfig("sina.com","sina.jks" ,"114114","114114");

            connector.addSslHostConfig(defaultSslHostConfig);
            connector.addSslHostConfig(sslHostConfig1);
            connector.addSslHostConfig(sslHostConfig2);


            connector.setAttribute("SSLEnabled", "true");
            connector.addUpgradeProtocol(new Http2Protocol());
            connector.setScheme("https");
            connector.setSecure(true);
            connector.setPort(port);

            return connector;
        }
        catch (IOException ex) {
            throw new IllegalStateException("can't access keystore: [" + "keystore"
                    + "] or truststore: [" + "keystore" + "]", ex);
        }
    }

}
