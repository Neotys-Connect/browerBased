package com.neotys.htmlunit.HtmlUnitUtils;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLFormElement;
import com.gargoylesoftware.htmlunit.javascript.host.performance.Performance;
import com.gargoylesoftware.htmlunit.javascript.host.performance.PerformanceEntry;
import com.google.common.base.Optional;
import com.neotys.extensions.action.engine.SampleResult;
import com.neotys.htmlunit.customActions.common.Constants;
import com.neotys.htmlunit.customActions.common.UXDataToNeoLoad;
import org.apache.http.conn.HttpHostConnectException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class NeoLoadBrowserEngine {

    private WebClient webClient;
    private Optional<String> proxyHost;
    private Optional<String> proxyPort;
    private BrowserVersion browserVersion;
    private BrowserContext context;
    private static final int ERROR_400=400;
    private static final int ERROR_500=500;
    private HtmlPage lastpage;

    public NeoLoadBrowserEngine( Optional<String> proxyHost, Optional<String> proxyPort,String strbrowserversion,BrowserContext context) {
         this.context=context;
        switch(strbrowserversion) {
            case "FIREFOX_45":
                browserVersion = BrowserVersion.FIREFOX_60;
                break;
            case "FIREFOX_68":
                browserVersion = BrowserVersion.FIREFOX_68;
                break;
            case "INTERNET_EXPLORER":
                browserVersion = BrowserVersion.INTERNET_EXPLORER;
                break;
            case "CHROME":
                browserVersion = BrowserVersion.CHROME;
                break;
            default:
                browserVersion = BrowserVersion.BEST_SUPPORTED;
                break;
        }

        if(proxyHost.isPresent()&& proxyPort.isPresent())
        {
            this.webClient=new WebClient(browserVersion,proxyHost.get(),Integer.parseInt(proxyPort.get()));

        }
        else
            this.webClient=new WebClient(browserVersion);

        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.webClient.getOptions().setThrowExceptionOnScriptError(false);


        NeoLoadJavascriptListener neoloadJavascriptListener=new NeoLoadJavascriptListener(context);

        webClient.setJavaScriptErrorListener(neoloadJavascriptListener);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        this.webClient.waitForBackgroundJavaScript(10000);
        this.webClient.waitForBackgroundJavaScriptStartingBefore(10000);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
    }

    private void traceinfo(String message)
    {
        if(context.getTracemode().isPresent())
        {
            context.getContext().getLogger().debug(message);
        }
    }
    private void reportPerformance(Performance performance,String page) throws Exception {
       PerformanceMetrics metrics=new PerformanceMetrics(performance);
        UXDataToNeoLoad uxDataToNeoLoad=new UXDataToNeoLoad(context,Optional.absent());
        uxDataToNeoLoad.sendToNeoLoadWeb(uxDataToNeoLoad.convertWhiteblockMonitoringToCustomMonitor(metrics,page));
    }

    public BrowserContext getContext() {
        return context;
    }

    public void setContext(BrowserContext context) {
        this.context = context;
    }

    public void clearCacheAndCookies()
    {

        if(context.getClearcache().isPresent())
        {
            if(context.getClearcache().get().toUpperCase().equalsIgnoreCase(Constants.TRUE))
                webClient.getCache().clear();
        }
        if(context.getClearCookies().isPresent())
        {
            if(context.getClearCookies().get().toUpperCase().equalsIgnoreCase(Constants.TRUE))
                webClient.getCookieManager().clearCookies();
        }
    }
    public String selectItem(DomElement element,String fieldvalue,SampleResult result) throws Exception {
        HtmlPage page = null;
        WebResponse response = null;
        clearCacheAndCookies();
        if(element instanceof HtmlSelect)
        {
            HtmlSelect selectField = (HtmlSelect) element;
            page=selectField.setSelectedAttribute(fieldvalue,true);
            response=page.getWebResponse();
        }
        else
        {
            if(element instanceof HtmlCheckBoxInput)
            {
                HtmlCheckBoxInput checkbox=(HtmlCheckBoxInput) element;
                checkbox.setDefaultValue(fieldvalue);
                page= (HtmlPage) checkbox.setChecked(true);
                response=page.getWebResponse();
            }

        }

        if(response!=null)
        {


            if(response.getStatusCode()>ERROR_400)
                throw new HttpExeption("Page generated and Eror : "+ lastpage.getWebResponse().getStatusCode() + " page response : "+lastpage.getWebResponse().getContentAsString());



            result.setDuration(response.getLoadTime());
            if(context.getPerformance().isPresent())
            {
                measurePerformance();
            }
            lastpage=page;
            return response.getContentAsString();
        }
         else
          return null;
    }
    public String clickOnLinkByURL(String url,SampleResult result) throws Exception {
        clearCacheAndCookies();
        lastpage=(HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
        HtmlAnchor link=lastpage.getAnchorByHref(url);
        HtmlPage httpage=(HtmlPage)link.click();
        WebResponse response=httpage.getWebResponse();

        if(response.getStatusCode()>ERROR_400)
            throw new HttpExeption("Page generated an Error : "+ lastpage.getWebResponse().getStatusCode() + " page response : "+lastpage.getWebResponse().getContentAsString());



        result.setDuration(response.getLoadTime());
        if(context.getPerformance().isPresent())
        {
            measurePerformance();
        }
        lastpage=httpage;

        return response.getContentAsString();
    }
    private void getCurrentHtmlPage()
    {
        if(lastpage==null)
            lastpage=(HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
    }
    public String selectItemFromListByName(String name,String fieldvalue,SampleResult result) throws Exception {
        getCurrentHtmlPage();
        DomElement element=lastpage.getElementByName(name);

        String output= selectItem(element,fieldvalue,result);
        return output;
    }

    public String selectItemFromListById(String id,String fieldvalue,SampleResult result) throws Exception {
        getCurrentHtmlPage();
        DomElement element=lastpage.getElementById(id);

        String output= selectItem(element,fieldvalue,result);
        return output;

    }
    public String clickElementByXpath(String xpath,SampleResult result) throws Exception {
        getCurrentHtmlPage();
        DomElement element=lastpage.getFirstByXPath(xpath);
        String output= clickElement(element,result);
        return output;

    }
    public void closeBrowser()
    {
        webClient.close();
    }


    public String setTextonElementByName(String text,String id, SampleResult result,Optional<String> formname) throws Exception {
        getCurrentHtmlPage();
        DomElement element;
        if(formname.isPresent())
        {
            HtmlForm form=lastpage.getFormByName(formname.get());
            try {
                element = form.getInputByName(id);
            }
            catch (ElementNotFoundException e)
            {
                element=form.getTextAreaByName(id);
            }
        }
        else
            element=lastpage.getElementByName(id);

        String output=setTextTOElement(element,text,result);
        return output;

    }

    public String setTextonElementById(String text,String id, SampleResult result) throws Exception {
        lastpage=(HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
        DomElement element=lastpage.getElementById(id);

        String output= setTextTOElement(element,text,result);
        return output;

    }

    private void measurePerformance() throws Exception {
        String pagename = lastpage.getPage().getUrl().toString();
        reportPerformance((Performance) lastpage.executeJavaScript("performance").getJavaScriptResult(),pagename);

    }

    public String setTextTOElement(DomElement element,String text,SampleResult result) throws Exception {
        clearCacheAndCookies();
        if(element instanceof HtmlTextInput)
        {
            HtmlTextInput textField = (HtmlTextInput) element;
            textField.setValueAttribute(text);
        }
        else
        {
            if(element instanceof HtmlTextArea)
            {
                HtmlTextArea textAreaField = (HtmlTextArea) element;
                textAreaField.setTextContent(text);
            }
            else
            {
                if(element instanceof HtmlPasswordInput)
                {
                    HtmlPasswordInput passwordInput=(HtmlPasswordInput) element;
                    passwordInput.setValueAttribute(text);
                }
                else
                {
                     if(element instanceof  HtmlHiddenInput)
                     {
                         HtmlHiddenInput input= (HtmlHiddenInput) element;
                         ((HtmlHiddenInput) element).setValueAttribute(text);

                     }
                     else
                         element.setTextContent(text);
                }
            }
        }
        getCurrentHtmlPage();
        if(lastpage.getWebResponse().getStatusCode()>ERROR_400)
            throw new HttpExeption("Page generated and Eror : "+ lastpage.getWebResponse().getStatusCode() + " page response : "+lastpage.getWebResponse().getContentAsString());


        result.setDuration(lastpage.getWebResponse().getLoadTime());
        if(context.getPerformance().isPresent())
        {
            measurePerformance();
        }

        return lastpage.getWebResponse().getContentAsString();
    }
    public String clickONeElementByName(String name, SampleResult result,Optional<String> forname) throws Exception {
        getCurrentHtmlPage();
        DomElement element;
        if(forname.isPresent())
        {
            HtmlForm formElement=lastpage.getFormByName(forname.get());
            element=formElement.getInputByName(name);
        }
        else
        {
            element=lastpage.getElementByName(name);
        }
        String output= clickElement(element,result);
        return output;

    }


    public String clickElement(DomElement element , SampleResult result) throws Exception {
        WebResponse response;
        clearCacheAndCookies();
        lastpage=element.click();

        if(lastpage.getWebResponse().getStatusCode()>ERROR_400)
            throw new HttpExeption("Page generated and Eror : "+ lastpage.getWebResponse().getStatusCode() + " page response : "+lastpage.getWebResponse().getContentAsString());


        result.setDuration(lastpage.getWebResponse().getLoadTime());
        if(context.getPerformance().isPresent())
        {
            measurePerformance();
        }

        return lastpage.getWebResponse().getContentAsString();
    }
    public String clickOneElementID(String elementid,SampleResult result) throws Exception {
        lastpage=(HtmlPage) webClient.getCurrentWindow().getEnclosedPage();

        DomElement element=lastpage.getElementById(elementid);
        String output= clickElement(element,result);
        return output;
    }
    public String loadPage(String url, SampleResult result) throws Exception {
        clearCacheAndCookies();
        HtmlPage page = this.webClient.getPage(url);
        traceinfo("Page has been loaded "+ page.getWebResponse().getStatusMessage());
        if(page.getWebResponse().getStatusCode()>ERROR_400)
            throw new HttpExeption("Page generated and Eror : "+ page.getWebResponse().getStatusCode() + " page response : "+page.getWebResponse().getContentAsString());


        result.setDuration(page.getWebResponse().getLoadTime());


        lastpage=page;
        if(context.getPerformance().isPresent())
        {

            measurePerformance();
        }


        return page.getWebResponse().getContentAsString();
    }

    public WebClient getWebClient() {
        return webClient;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Optional<String> getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(Optional<String> proxyHost) {
        this.proxyHost = proxyHost;
    }

    public Optional<String> getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(Optional<String> proxyPort) {
        this.proxyPort = proxyPort;
    }
}
