package org.http.handler;

import okhttp3.*;
import org.example.KafkaProcessing;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HttpDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpDemo.class);

    public static final int MAX_CONNECTION_TIME_OUT = 60;
    private static final String BASE_URL = "http://eth-dev.inspirelab.io:3500/eth/v1/node/syncing";
    OkHttpClient client = this.createHttpClient();

    private OkHttpClient createHttpClient() {
        OkHttpClient.Builder build = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(MAX_CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(MAX_CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(10000,5, TimeUnit.MINUTES))
                .writeTimeout(MAX_CONNECTION_TIME_OUT, TimeUnit.SECONDS)
//                .addInterceptor(new LoggingInterceptor())
//                .addNetworkInterceptor(new LoggingInterceptor());
                .eventListener(new PrintingEventListener());
        return build.build();
    }

    public void demo() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL)
                .build();

        for (int i = 0; i < 2; i++) {
            LOGGER.info("Request " + i);

            client.newCall(request).execute();

            Call call = client.newCall(request);
            Response response = call.execute();

            LOGGER.info("Res: " + response.body().string());
        }
    }
}

class LoggingInterceptor implements Interceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);
    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        LOGGER.info(String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);
        
        long t2 = System.nanoTime();
        LOGGER.info(String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return response;
    }

}


class PrintingEventListener extends EventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrintingEventListener.class);

    private long callStartNanos;

    private void printEvent(String name) {
        long nowNanos = System.nanoTime();
        if (name.equals("callStart")) {
            callStartNanos = nowNanos;
        }
        long elapsedNanos = nowNanos - callStartNanos;
        LOGGER.warn(String.format("%.3f %s", elapsedNanos / 1000000000d, name));
    }

    @Override public void callStart(Call call) {
        printEvent("callStart");
    }

    @Override public void proxySelectStart(Call call, HttpUrl url) {
        printEvent("proxySelectStart");
    }

    @Override public void proxySelectEnd(Call call, HttpUrl url, List<Proxy> proxies) {
        printEvent("proxySelectEnd");
    }

    @Override public void dnsStart(Call call, String domainName) {
        printEvent("dnsStart");
    }

    @Override public void dnsEnd(Call call, String domainName, List<InetAddress> inetAddressList) {
        printEvent("dnsEnd");
    }

    @Override public void connectStart(
            Call call, InetSocketAddress inetSocketAddress, Proxy proxy) {
        printEvent("connectStart");
    }

    @Override public void secureConnectStart(Call call) {
        printEvent("secureConnectStart");
    }

    @Override public void secureConnectEnd(Call call, Handshake handshake) {
        printEvent("secureConnectEnd");
    }

    @Override public void connectEnd(
            Call call, InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocol) {
        printEvent("connectEnd");
    }

    @Override public void connectFailed(Call call, InetSocketAddress inetSocketAddress, Proxy proxy,
                                        Protocol protocol, IOException ioe) {
        printEvent("connectFailed");
    }

    @Override public void connectionAcquired(Call call, Connection connection) {
        printEvent("connectionAcquired");
        LOGGER.info("Connection hash " + System.identityHashCode(connection));
    }

    @Override public void connectionReleased(Call call, Connection connection) {
        printEvent("connectionReleased");
    }

    @Override public void requestHeadersStart(Call call) {
        printEvent("requestHeadersStart");
    }

    @Override public void requestHeadersEnd(Call call, Request request) {
        printEvent("requestHeadersEnd");
    }

    @Override public void requestBodyStart(Call call) {
        printEvent("requestBodyStart");
    }

    @Override public void requestBodyEnd(Call call, long byteCount) {
        printEvent("requestBodyEnd");
    }

    @Override public void requestFailed(Call call, IOException ioe) {
        printEvent("requestFailed");
    }

    @Override public void responseHeadersStart(Call call) {
        printEvent("responseHeadersStart");
    }

    @Override public void responseHeadersEnd(Call call, Response response) {
        printEvent("responseHeadersEnd");
    }

    @Override public void responseBodyStart(Call call) {
        printEvent("responseBodyStart");
    }

    @Override public void responseBodyEnd(Call call, long byteCount) {
        printEvent("responseBodyEnd");
    }

    @Override public void responseFailed(Call call, IOException ioe) {
        printEvent("responseFailed");
    }

    @Override public void callEnd(Call call) {
        printEvent("callEnd");
    }

    @Override public void callFailed(Call call, IOException ioe) {
        printEvent("callFailed");
    }
}
