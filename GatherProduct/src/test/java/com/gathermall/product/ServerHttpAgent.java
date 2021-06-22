//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.gathermall.product;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.client.config.http.HttpAgent;
import com.alibaba.nacos.client.config.impl.ConfigHttpClientManager;
import com.alibaba.nacos.client.config.impl.ServerListManager;
import com.alibaba.nacos.client.config.impl.SpasAdapter;
import com.alibaba.nacos.client.identify.StsConfig;
import com.alibaba.nacos.client.security.SecurityProxy;
import com.alibaba.nacos.client.utils.ContextPathUtil;
import com.alibaba.nacos.client.utils.LogUtils;
import com.alibaba.nacos.client.utils.ParamUtil;
import com.alibaba.nacos.client.utils.TemplateUtils;
import com.alibaba.nacos.common.http.HttpClientConfig;
import com.alibaba.nacos.common.http.HttpRestResult;
import com.alibaba.nacos.common.http.client.NacosRestTemplate;
import com.alibaba.nacos.common.http.param.Header;
import com.alibaba.nacos.common.http.param.Query;
import com.alibaba.nacos.common.utils.ConvertUtils;
import com.alibaba.nacos.common.utils.ExceptionUtil;
import com.alibaba.nacos.common.utils.JacksonUtils;
import com.alibaba.nacos.common.utils.MD5Utils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.common.utils.ThreadUtils;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.alibaba.nacos.common.utils.VersionUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;

public class ServerHttpAgent implements HttpAgent {
    private static final Logger LOGGER = LogUtils.logger(ServerHttpAgent.class);
    private static final NacosRestTemplate NACOS_RESTTEMPLATE = ConfigHttpClientManager.getInstance().getNacosRestTemplate();
    private SecurityProxy securityProxy;
    private String namespaceId;
    private final long securityInfoRefreshIntervalMills;
    private ScheduledExecutorService executorService;
    private String accessKey;
    private String secretKey;
    private String encode;
    private int maxRetry;
    private volatile ServerHttpAgent.StsCredential stsCredential;
    final ServerListManager serverListMgr;

    public HttpRestResult<String> httpGet(String path, Map<String, String> headers, Map<String, String> paramValues, String encode, long readTimeoutMs) throws Exception {
        long endTime = System.currentTimeMillis() + readTimeoutMs;
        this.injectSecurityInfo(paramValues);
        String currentServerAddr = this.serverListMgr.getCurrentServerAddr();
        int maxRetry = this.maxRetry;
        HttpClientConfig httpConfig = HttpClientConfig.builder().setReadTimeOutMillis(Long.valueOf(readTimeoutMs).intValue()).setConTimeOutMillis(ConfigHttpClientManager.getInstance().getConnectTimeoutOrDefault(100)).build();

        do {
            try {
                Header newHeaders = this.getSpasHeaders(paramValues, encode);
                if (headers != null) {
                    newHeaders.addAll(headers);
                }

                Query query = Query.newInstance().initParams(paramValues);
                HttpRestResult<String> result = NACOS_RESTTEMPLATE.get(this.getUrl(currentServerAddr, path), httpConfig, newHeaders, query, String.class);
                if (!this.isFail(result)) {
                    this.serverListMgr.updateCurrentServerAddr(currentServerAddr);
                    return result;
                }

                LOGGER.error("[NACOS ConnectException] currentServerAddr: {}, httpCode: {}", this.serverListMgr.getCurrentServerAddr(), result.getCode());
            } catch (ConnectException var15) {
                LOGGER.error("[NACOS ConnectException httpGet] currentServerAddr:{}, err : {}", this.serverListMgr.getCurrentServerAddr(), var15.getMessage());
            } catch (SocketTimeoutException var16) {
                LOGGER.error("[NACOS SocketTimeoutException httpGet] currentServerAddr:{}， err : {}", this.serverListMgr.getCurrentServerAddr(), var16.getMessage());
            } catch (Exception var17) {
                LOGGER.error("[NACOS Exception httpGet] currentServerAddr: " + this.serverListMgr.getCurrentServerAddr(), var17);
                throw var17;
            }

            if (this.serverListMgr.getIterator().hasNext()) {
                currentServerAddr = (String)this.serverListMgr.getIterator().next();
            } else {
                --maxRetry;
                if (maxRetry < 0) {
                    throw new ConnectException("[NACOS HTTP-GET] The maximum number of tolerable server reconnection errors has been reached");
                }

                this.serverListMgr.refreshCurrentServerAddr();
            }
        } while(System.currentTimeMillis() <= endTime);

        LOGGER.error("no available server");
        throw new ConnectException("no available server");
    }

    public HttpRestResult<String> httpPost(String path, Map<String, String> headers, Map<String, String> paramValues, String encode, long readTimeoutMs) throws Exception {
        readTimeoutMs = 6000000;
        long endTime = System.currentTimeMillis() + readTimeoutMs;
        this.injectSecurityInfo(paramValues);
        String currentServerAddr = this.serverListMgr.getCurrentServerAddr();
        int maxRetry = this.maxRetry;
        HttpClientConfig httpConfig = HttpClientConfig.builder().setReadTimeOutMillis(Long.valueOf(readTimeoutMs).intValue()).setConTimeOutMillis(ConfigHttpClientManager.getInstance().getConnectTimeoutOrDefault(3000)).build();

        do {
            try {
                Header newHeaders = this.getSpasHeaders(paramValues, encode);
                if (headers != null) {
                    newHeaders.addAll(headers);
                }

                HttpRestResult<String> result = NACOS_RESTTEMPLATE.postForm(this.getUrl(currentServerAddr, path), httpConfig, newHeaders, paramValues, String.class);
                if (!this.isFail(result)) {
                    this.serverListMgr.updateCurrentServerAddr(currentServerAddr);
                    return result;
                }

                LOGGER.error("[NACOS ConnectException] currentServerAddr: {}, httpCode: {}", currentServerAddr, result.getCode());
            } catch (ConnectException var14) {
                LOGGER.error("[NACOS ConnectException httpPost] currentServerAddr: {}, err : {}", currentServerAddr, var14.getMessage());
            } catch (SocketTimeoutException var15) {
                LOGGER.error("[NACOS SocketTimeoutException httpPost] currentServerAddr: {}， err : {}", currentServerAddr, var15.getMessage());
            } catch (Exception var16) {
                LOGGER.error("[NACOS Exception httpPost] currentServerAddr: " + currentServerAddr, var16);
                throw var16;
            }

            if (this.serverListMgr.getIterator().hasNext()) {
                currentServerAddr = (String)this.serverListMgr.getIterator().next();
            } else {
                --maxRetry;
                if (maxRetry < 0) {
                    throw new ConnectException("[NACOS HTTP-POST] The maximum number of tolerable server reconnection errors has been reached");
                }

                this.serverListMgr.refreshCurrentServerAddr();
            }
        } while(System.currentTimeMillis() <= endTime);

        LOGGER.error("no available server, currentServerAddr : {}", currentServerAddr);
        throw new ConnectException("no available server, currentServerAddr : " + currentServerAddr);
    }

    public HttpRestResult<String> httpDelete(String path, Map<String, String> headers, Map<String, String> paramValues, String encode, long readTimeoutMs) throws Exception {
        long endTime = System.currentTimeMillis() + readTimeoutMs;
        this.injectSecurityInfo(paramValues);
        String currentServerAddr = this.serverListMgr.getCurrentServerAddr();
        int maxRetry = this.maxRetry;
        HttpClientConfig httpConfig = HttpClientConfig.builder().setReadTimeOutMillis(Long.valueOf(readTimeoutMs).intValue()).setConTimeOutMillis(ConfigHttpClientManager.getInstance().getConnectTimeoutOrDefault(100)).build();

        do {
            try {
                Header newHeaders = this.getSpasHeaders(paramValues, encode);
                if (headers != null) {
                    newHeaders.addAll(headers);
                }

                Query query = Query.newInstance().initParams(paramValues);
                HttpRestResult<String> result = NACOS_RESTTEMPLATE.delete(this.getUrl(currentServerAddr, path), httpConfig, newHeaders, query, String.class);
                if (!this.isFail(result)) {
                    this.serverListMgr.updateCurrentServerAddr(currentServerAddr);
                    return result;
                }

                LOGGER.error("[NACOS ConnectException] currentServerAddr: {}, httpCode: {}", this.serverListMgr.getCurrentServerAddr(), result.getCode());
            } catch (ConnectException var15) {
                LOGGER.error("[NACOS ConnectException httpDelete] currentServerAddr:{}, err : {}", this.serverListMgr.getCurrentServerAddr(), ExceptionUtil.getStackTrace(var15));
            } catch (SocketTimeoutException var16) {
                LOGGER.error("[NACOS SocketTimeoutException httpDelete] currentServerAddr:{}， err : {}", this.serverListMgr.getCurrentServerAddr(), ExceptionUtil.getStackTrace(var16));
            } catch (Exception var17) {
                LOGGER.error("[NACOS Exception httpDelete] currentServerAddr: " + this.serverListMgr.getCurrentServerAddr(), var17);
                throw var17;
            }

            if (this.serverListMgr.getIterator().hasNext()) {
                currentServerAddr = (String)this.serverListMgr.getIterator().next();
            } else {
                --maxRetry;
                if (maxRetry < 0) {
                    throw new ConnectException("[NACOS HTTP-DELETE] The maximum number of tolerable server reconnection errors has been reached");
                }

                this.serverListMgr.refreshCurrentServerAddr();
            }
        } while(System.currentTimeMillis() <= endTime);

        LOGGER.error("no available server");
        throw new ConnectException("no available server");
    }

    private String getUrl(String serverAddr, String relativePath) {
        return serverAddr + ContextPathUtil.normalizeContextPath(this.serverListMgr.getContentPath()) + relativePath;
    }

    private boolean isFail(HttpRestResult<String> result) {
        return result.getCode() == 500 || result.getCode() == 502 || result.getCode() == 503;
    }

    public static String getAppname() {
        return ParamUtil.getAppName();
    }

    public ServerHttpAgent(ServerListManager mgr) {
        this.securityInfoRefreshIntervalMills = TimeUnit.SECONDS.toMillis(5L);
        this.maxRetry = 3;
        this.serverListMgr = mgr;
    }

    public ServerHttpAgent(ServerListManager mgr, Properties properties) {
        this.securityInfoRefreshIntervalMills = TimeUnit.SECONDS.toMillis(5L);
        this.maxRetry = 3;
        this.serverListMgr = mgr;
        this.init(properties);
    }

    public ServerHttpAgent(Properties properties) throws NacosException {
        this.securityInfoRefreshIntervalMills = TimeUnit.SECONDS.toMillis(5L);
        this.maxRetry = 3;
        this.serverListMgr = new ServerListManager(properties);
        this.securityProxy = new SecurityProxy(properties, NACOS_RESTTEMPLATE);
        this.namespaceId = properties.getProperty("namespace");
        this.init(properties);
        this.securityProxy.login(this.serverListMgr.getServerUrls());
        this.executorService = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("com.alibaba.nacos.client.config.security.updater");
                t.setDaemon(true);
                return t;
            }
        });
        this.executorService.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                ServerHttpAgent.this.securityProxy.login(ServerHttpAgent.this.serverListMgr.getServerUrls());
            }
        }, 0L, this.securityInfoRefreshIntervalMills, TimeUnit.MILLISECONDS);
    }

    private void injectSecurityInfo(Map<String, String> params) {
        if (StringUtils.isNotBlank(this.securityProxy.getAccessToken())) {
            params.put("accessToken", this.securityProxy.getAccessToken());
        }

        if (StringUtils.isNotBlank(this.namespaceId) && !params.containsKey("tenant")) {
            params.put("tenant", this.namespaceId);
        }

    }

    private void init(Properties properties) {
        this.initEncode(properties);
        this.initAkSk(properties);
        this.initMaxRetry(properties);
    }

    private void initEncode(Properties properties) {
        this.encode = TemplateUtils.stringEmptyAndThenExecute(properties.getProperty("encode"), new Callable<String>() {
            public String call() throws Exception {
                return "UTF-8";
            }
        });
    }

    private void initAkSk(Properties properties) {
        String ramRoleName = properties.getProperty("ramRoleName");
        if (!StringUtils.isBlank(ramRoleName)) {
            StsConfig.getInstance().setRamRoleName(ramRoleName);
        }

        String ak = properties.getProperty("accessKey");
        if (StringUtils.isBlank(ak)) {
            this.accessKey = SpasAdapter.getAk();
        } else {
            this.accessKey = ak;
        }

        String sk = properties.getProperty("secretKey");
        if (StringUtils.isBlank(sk)) {
            this.secretKey = SpasAdapter.getSk();
        } else {
            this.secretKey = sk;
        }

    }

    private void initMaxRetry(Properties properties) {
        this.maxRetry = ConvertUtils.toInt(String.valueOf(properties.get("maxRetry")), 3);
    }

    public void start() throws NacosException {
        this.serverListMgr.start();
    }

    private Header getSpasHeaders(Map<String, String> paramValues, String encode) throws Exception {
        Header header = Header.newInstance();
        if (StsConfig.getInstance().isStsOn()) {
            ServerHttpAgent.StsCredential stsCredential = this.getStsCredential();
            this.accessKey = stsCredential.accessKeyId;
            this.secretKey = stsCredential.accessKeySecret;
            header.addParam("Spas-SecurityToken", stsCredential.securityToken);
        }

        if (StringUtils.isNotEmpty(this.accessKey) && StringUtils.isNotEmpty(this.secretKey)) {
            header.addParam("Spas-AccessKey", this.accessKey);
            Map<String, String> signHeaders = SpasAdapter.getSignHeaders(paramValues, this.secretKey);
            if (signHeaders != null) {
                header.addAll(signHeaders);
            }
        }

        String ts = String.valueOf(System.currentTimeMillis());
        String token = MD5Utils.md5Hex(ts + ParamUtil.getAppKey(), "UTF-8");
        header.addParam("Client-AppName", ParamUtil.getAppName());
        header.addParam("Client-RequestTS", ts);
        header.addParam("Client-RequestToken", token);
        header.addParam("Client-Version", VersionUtils.version);
        header.addParam("exConfigInfo", "true");
        header.addParam("RequestId", UuidUtils.generateUuid());
        header.addParam("Accept-Charset", encode);
        return header;
    }

    private ServerHttpAgent.StsCredential getStsCredential() throws Exception {
        boolean cacheSecurityCredentials = StsConfig.getInstance().isCacheSecurityCredentials();
        if (cacheSecurityCredentials && this.stsCredential != null) {
            long currentTime = System.currentTimeMillis();
            long expirationTime = this.stsCredential.expiration.getTime();
            int timeToRefreshInMillisecond = StsConfig.getInstance().getTimeToRefreshInMillisecond();
            if (expirationTime - currentTime > (long)timeToRefreshInMillisecond) {
                return this.stsCredential;
            }
        }

        String stsResponse = getStsResponse();
        ServerHttpAgent.StsCredential stsCredentialTmp = (ServerHttpAgent.StsCredential)JacksonUtils.toObj(stsResponse, new TypeReference<ServerHttpAgent.StsCredential>() {
        });
        this.stsCredential = stsCredentialTmp;
        LOGGER.info("[getSTSCredential] code:{}, accessKeyId:{}, lastUpdated:{}, expiration:{}", new Object[]{this.stsCredential.getCode(), this.stsCredential.getAccessKeyId(), this.stsCredential.getLastUpdated(), this.stsCredential.getExpiration()});
        return this.stsCredential;
    }

    private static String getStsResponse() throws Exception {
        String securityCredentials = StsConfig.getInstance().getSecurityCredentials();
        if (securityCredentials != null) {
            return securityCredentials;
        } else {
            String securityCredentialsUrl = StsConfig.getInstance().getSecurityCredentialsUrl();

            try {
                HttpRestResult<String> result = NACOS_RESTTEMPLATE.get(securityCredentialsUrl, Header.EMPTY, Query.EMPTY, String.class);
                if (!result.ok()) {
                    LOGGER.error("can not get security credentials, securityCredentialsUrl: {}, responseCode: {}, response: {}", new Object[]{securityCredentialsUrl, result.getCode(), result.getMessage()});
                    throw new NacosException(500, "can not get security credentials, responseCode: " + result.getCode() + ", response: " + result.getMessage());
                } else {
                    return (String)result.getData();
                }
            } catch (Exception var3) {
                LOGGER.error("can not get security credentials", var3);
                throw var3;
            }
        }
    }

    public String getName() {
        return this.serverListMgr.getName();
    }

    public String getNamespace() {
        return this.serverListMgr.getNamespace();
    }

    public String getTenant() {
        return this.serverListMgr.getTenant();
    }

    public String getEncode() {
        return this.encode;
    }

    public void shutdown() throws NacosException {
        String className = this.getClass().getName();
        LOGGER.info("{} do shutdown begin", className);
        ThreadUtils.shutdownThreadPool(this.executorService, LOGGER);
        ConfigHttpClientManager.getInstance().shutdown();
        SpasAdapter.freeCredentialInstance();
        LOGGER.info("{} do shutdown stop", className);
    }

    private static class StsCredential {
        @JsonProperty("AccessKeyId")
        private String accessKeyId;
        @JsonProperty("AccessKeySecret")
        private String accessKeySecret;
        @JsonProperty("Expiration")
        private Date expiration;
        @JsonProperty("SecurityToken")
        private String securityToken;
        @JsonProperty("LastUpdated")
        private Date lastUpdated;
        @JsonProperty("Code")
        private String code;

        private StsCredential() {
        }

        public String getAccessKeyId() {
            return this.accessKeyId;
        }

        public Date getExpiration() {
            return this.expiration;
        }

        public Date getLastUpdated() {
            return this.lastUpdated;
        }

        public String getCode() {
            return this.code;
        }

        public String toString() {
            return "STSCredential{accessKeyId='" + this.accessKeyId + '\'' + ", accessKeySecret='" + this.accessKeySecret + '\'' + ", expiration=" + this.expiration + ", securityToken='" + this.securityToken + '\'' + ", lastUpdated=" + this.lastUpdated + ", code='" + this.code + '\'' + '}';
        }
    }
}
