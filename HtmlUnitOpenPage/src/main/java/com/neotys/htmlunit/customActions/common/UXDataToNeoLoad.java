package com.neotys.htmlunit.customActions.common;

import com.google.common.base.Optional;
import com.neotys.ascode.swagger.client.ApiClient;
import com.neotys.ascode.swagger.client.api.ResultsApi;
import com.neotys.ascode.swagger.client.model.CustomMonitor;
import com.neotys.ascode.swagger.client.model.CustomMonitorValues;
import com.neotys.ascode.swagger.client.model.CustomMonitorValuesInner;
import com.neotys.ascode.swagger.client.model.MonitorPostRequest;
import com.neotys.htmlunit.HtmlUnitUtils.BrowserContext;
import com.neotys.htmlunit.HtmlUnitUtils.PerformanceMetrics;
import com.neotys.rest.dataexchange.client.DataExchangeAPIClient;
import com.neotys.rest.dataexchange.model.EntryBuilder;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;



public class UXDataToNeoLoad {

    Optional<DataExchangeAPIClient> dataExchangeAPIClient;
    BrowserContext context;
    long startime;
    long endtime;


    public List<CustomMonitor> convertWhiteblockMonitoringToCustomMonitor(PerformanceMetrics performanceMetrics,String pagename)
    {
        List<NLWebblockData> data=performanceMetrics.getWhiteblockDataTONL();
        traceInfo(data.stream().map(d->{
            StringBuilder str =new StringBuilder();
            str.append("metric :"+ d.getMetricName());
            str.append("time :"+d.getTime());
            str.append("unit :"+d.getUnit());
            return str.toString();
        }).collect(Collectors.joining("\t")));

        return data.stream()
                .map(brdata->toCustomMonitor(brdata,pagename))
                .collect(Collectors.toList());
    }

    private CustomMonitor toCustomMonitor(final NLWebblockData nlWebblockData ,final String pageName)
    {
        CustomMonitor monitor=new CustomMonitor();

        List<String> path = new ArrayList<>();
        path.add(Constants.BROWSERBASED);
        path.add(context.getContext().getCurrentVirtualUser().getCurrentStep());
        path.add(pageName);
        path.addAll(Arrays.asList(nlWebblockData.getMetricName().split("_")));
        traceInfo(path.stream().collect(Collectors.joining("/")));
        monitor.setPath(path);
        monitor.setUnit(nlWebblockData.getUnit());
        monitor.setName(path.get(path.size()-1));
        traceInfo(path.get(path.size()-1));
        CustomMonitorValues valuesInners=new CustomMonitorValues();
       // valuesInners.s
        CustomMonitorValuesInner customMonitorValuesInner=new CustomMonitorValuesInner();
        Instant instant = Instant.now();
        customMonitorValuesInner.setTimestamp(instant.getEpochSecond());
        customMonitorValuesInner.setValue((float)nlWebblockData.getValue());
        traceInfo("value :" +String.valueOf(nlWebblockData.getValue())+" ts:"+String.valueOf(instant.getEpochSecond()));
        valuesInners.add(customMonitorValuesInner);
       //
        monitor.setValues(valuesInners);
        return monitor;
    }

    public UXDataToNeoLoad(BrowserContext context, Optional<DataExchangeAPIClient> apiclient)
    {
        dataExchangeAPIClient=apiclient;
        this.context=context;

    }




    private  void traceInfo(String log)
    {
        if(context.getTracemode().isPresent())
        {
            if(context.getTracemode().get().toUpperCase().equals(Constants.TRUE))
            {
                context.getContext().getLogger().info(log);
            }
        }
    }


    private String getBasePath(final BrowserContext context) {
        final String webPlatformApiUrl = context.getContext().getWebPlatformApiUrl();
        final StringBuilder basePathBuilder = new StringBuilder(webPlatformApiUrl);
        if(!webPlatformApiUrl.endsWith("/")) {
            basePathBuilder.append("/");
        }
        basePathBuilder.append(Constants.NLWEB_VERSION + "/");
        return basePathBuilder.toString();
    }
    public void sendToNeoLoadWeb(List<CustomMonitor> monitor) throws Exception {
        String testid=context.getContext().getTestId();

        ApiClient neoLoadWebApiClient = new ApiClient();
        neoLoadWebApiClient.setApiKey(context.getContext().getAccountToken());
        neoLoadWebApiClient.setBasePath(getBasePath(context));

        if(testid!=null)
        {
            ResultsApi resultsApi=new ResultsApi(neoLoadWebApiClient);
            MonitorPostRequest monitorPostRequest=new MonitorPostRequest();
            monitorPostRequest.monitors(monitor);
            traceInfo(generateLogfromMonitorRequest(monitorPostRequest));
            resultsApi.postTestMonitors(monitorPostRequest,testid);
        }
    }


    private String generateLogfromMonitorRequest(MonitorPostRequest request)
    {

        String output= request.getMonitors().stream().map(monitor->{
            StringBuilder builder=new StringBuilder();
            builder.append("Name:");
            builder.append(monitor.getName());
            builder.append("Path");
            builder.append(monitor.getPath().stream().collect(Collectors.joining("/")));
            builder.append("Value:");
            monitor.getValues().stream().forEach(value-> builder.append("v:"+value.getValue().toString()+" ts:"+value.getTimestamp().toString()));
            return builder.toString();
        }).collect(Collectors.joining("\n"));

        return output;
    }


}
