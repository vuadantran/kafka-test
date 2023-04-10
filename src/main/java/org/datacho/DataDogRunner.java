package org.datacho;


import com.datadog.api.client.ApiClient;
import com.datadog.api.client.ApiException;
import com.datadog.api.client.v1.api.MonitorsApi;
import com.datadog.api.client.v1.model.ApmStatsQueryColumnType;
import com.datadog.api.client.v1.model.Monitor;
import com.datadog.api.client.v1.model.MonitorType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DataDogRunner {
    public static void main(String[] args) {
        ApiClient defaultClient = ApiClient.getDefaultApiClient();
        defaultClient.setApiKey("38fd5bae45cb9e0cf1170f3d23e69389b9339601");

        MonitorsApi apiInstance = new MonitorsApi(defaultClient);

        Monitor body =
                new Monitor()
                        .name("my-monitor")
                        .type(MonitorType.LOG_ALERT)
                        .query(
                                "logs(\"service:foo AND type:error\").index(\"main\").rollup(\"count\").by(\"source\").last(\"5m\") > 2\n")
                        .message("some message Notify: @hipchat-channel")
                        .tags(Arrays.asList("test:example", "env:ci"))
                        .priority(3L);


        try {
            Monitor result = apiInstance.createMonitor(body);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling MonitorsApi#createMonitor");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
