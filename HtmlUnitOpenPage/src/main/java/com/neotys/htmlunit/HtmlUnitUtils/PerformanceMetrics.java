package com.neotys.htmlunit.HtmlUnitUtils;

import com.gargoylesoftware.htmlunit.javascript.host.performance.Performance;
import com.neotys.htmlunit.customActions.common.Constants;
import com.neotys.htmlunit.customActions.common.NLWebblockData;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PerformanceMetrics {
    private long advanced_redirectTime;
    private long advanced_dnslookupTime;
    private long advanced_tcpHandShakeTime;
    private long advanced_GenerateresponseTime;
    private long advanced_DOMLoadingTime;
    private long advanced_LoadingTime;
    private long advanced_DOMInteractTime;
    private long uXTime;

    public PerformanceMetrics(Performance performance)
    {
        long start= performance.getTiming().getRedirectStart() == 0 ?  performance.getTiming().getFetchStart() :  performance.getTiming().getRedirectStart();
        advanced_redirectTime =performance.getTiming().getRedirectEnd()-performance.getTiming().getRedirectStart();
        if(performance.getTiming().getLoadEventEnd()>0)
        {
             uXTime =performance.getTiming().getLoadEventEnd()-start;
            advanced_tcpHandShakeTime =performance.getTiming().getConnectEnd()-performance.getTiming().getConnectStart();
            advanced_dnslookupTime =performance.getTiming().getDomainLookupEnd()-performance.getTiming().getDomainLookupStart();
            advanced_GenerateresponseTime =performance.getTiming().getResponseEnd()-performance.getTiming().getResponseStart();
            advanced_LoadingTime =performance.getTiming().getLoadEventEnd()-performance.getTiming().getLoadEventStart();
            advanced_DOMLoadingTime =performance.getTiming().getDomContentLoadedEventEnd()-performance.getTiming().getDomContentLoadedEventStart();
            advanced_DOMInteractTime =performance.getTiming().getLoadEventEnd()-performance.getTiming().getNavigationStart();
        }
    }


    public List<NLWebblockData> getWhiteblockDataTONL()
    {
        List<NLWebblockData> data=new ArrayList<>();
        Field[] listofField=this.getClass().getDeclaredFields();
        Arrays.stream(listofField).forEach(f->{
            if(f.getType().equals(Long.class))
            {
                try {
                    String metricname=f.getName();
                    data.add(new NLWebblockData(Constants.UX_UNIT,((NLWebblockData) f.get(this)).getValue(), Instant.now().toEpochMilli(),metricname));

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });


        return data;
    }

    public long getuXTime() {
        return uXTime;
    }

    public long getAdvanced_redirectTime() {
        return advanced_redirectTime;
    }

    public long getAdvanced_dnslookupTime() {
        return advanced_dnslookupTime;
    }

    public long getAdvanced_tcpHandShakeTime() {
        return advanced_tcpHandShakeTime;
    }

    public long getAdvanced_GenerateresponseTime() {
        return advanced_GenerateresponseTime;
    }

    public long getAdvanced_DOMLoadingTime() {
        return advanced_DOMLoadingTime;
    }

    public long getAdvanced_LoadingTime() {
        return advanced_LoadingTime;
    }

    public long getAdvanced_DOMInteractTime() {
        return advanced_DOMInteractTime;
    }


}
