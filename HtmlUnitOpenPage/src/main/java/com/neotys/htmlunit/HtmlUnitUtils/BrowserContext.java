package com.neotys.htmlunit.HtmlUnitUtils;

import com.google.common.base.Optional;
import com.neotys.extensions.action.engine.Context;

public class BrowserContext {
    private Optional<String> tracemode;
    private Context context;
    private Optional<String> proxyNAme;
    private Optional<String> performance;

    public BrowserContext(Optional<String> tracemode, Context context, Optional<String> proxyNAme,Optional<String> perf) {
        this.tracemode = tracemode;
        this.context = context;
        this.proxyNAme = proxyNAme;
        this.performance=perf;

    }

    public Optional<String> getTracemode() {
        return tracemode;
    }

    public Optional<String> getPerformance() {
        return performance;
    }

    public void setPerformance(Optional<String> performance) {
        this.performance = performance;
    }

    public void setTracemode(Optional<String> tracemode) {
        this.tracemode = tracemode;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Optional<String> getProxyNAme() {
        return proxyNAme;
    }

    public void setProxyNAme(Optional<String> proxyNAme) {
        this.proxyNAme = proxyNAme;
    }
}
