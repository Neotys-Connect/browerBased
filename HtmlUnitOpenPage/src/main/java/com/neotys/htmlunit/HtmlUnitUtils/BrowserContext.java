package com.neotys.htmlunit.HtmlUnitUtils;

import com.google.common.base.Optional;
import com.neotys.extensions.action.engine.Context;

public class BrowserContext {
    private Optional<String> tracemode;
    private Context context;
    private Optional<String> proxyNAme;
    private Optional<String> performance;
    private Optional<String> clearcache;
    private Optional<String> clearCookies;

    public BrowserContext(Optional<String> tracemode, Context context, Optional<String> proxyNAme, Optional<String> performance, Optional<String> clearcache, Optional<String> clearCookies) {
        this.tracemode = tracemode;
        this.context = context;
        this.proxyNAme = proxyNAme;
        this.performance = performance;
        this.clearcache = clearcache;
        this.clearCookies = clearCookies;
    }

    public Optional<String> getClearcache() {
        return clearcache;
    }

    public void setClearcache(Optional<String> clearcache) {
        this.clearcache = clearcache;
    }

    public Optional<String> getClearCookies() {
        return clearCookies;
    }

    public void setClearCookies(Optional<String> clearCookies) {
        this.clearCookies = clearCookies;
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
