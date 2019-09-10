package com.neotys.htmlunit.HtmlUnitUtils;

import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;

import java.net.MalformedURLException;
import java.net.URL;

public class NeoLoadJavascriptListener implements JavaScriptErrorListener {
    private BrowserContext context;

    public NeoLoadJavascriptListener(BrowserContext context) {
        this.context = context;
    }

    private void traceInfo(String message) {

    }

    @Override
    public void scriptException(HtmlPage htmlPage, ScriptException e) {
            context.getContext().getLogger().error("Javascirpt Exception ",e);
    }

    @Override
    public void timeoutError(HtmlPage htmlPage, long l, long l1) {
        context.getContext().getLogger().error("Timeout error on page "+htmlPage.getUrl().toString());

    }

    @Override
    public void malformedScriptURL(HtmlPage htmlPage, String s, MalformedURLException e) {
        context.getContext().getLogger().error("Malformat URL error on page "+htmlPage.getUrl().toString()+" , "+s,e);

    }

    @Override
    public void loadScriptError(HtmlPage htmlPage, URL url, Exception e) {
        context.getContext().getLogger().error("load script error on page "+htmlPage.getUrl().toString()+" , "+url.toString(),e);

    }

    @Override
    public void warn(String s, String s1, int i, String s2, int i1) {
        context.getContext().getLogger().warn("Javacript warning "+s+ " "+ s1+" "+s2);

    }
}
