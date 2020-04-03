package com.neotys.htmlunit.customActions;

import java.util.List;
import java.util.Map;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.*;
import com.gargoylesoftware.htmlunit.javascript.host.Window;
import com.gargoylesoftware.htmlunit.javascript.host.performance.PerformanceTiming;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.neotys.action.result.ResultFactory;
import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;
import com.neotys.extensions.action.engine.Context;
import com.neotys.extensions.action.engine.Logger;
import com.neotys.extensions.action.engine.SampleResult;
import com.neotys.htmlunit.HtmlUnitUtils.BrowserContext;
import com.neotys.htmlunit.HtmlUnitUtils.HttpExeption;
import com.neotys.htmlunit.HtmlUnitUtils.NeoLoadBrowserEngine;

import static com.neotys.action.argument.Arguments.getArgumentLogString;
import static com.neotys.action.argument.Arguments.parseArguments;


public final class OpenPageActionEngine implements ActionEngine {


	private static final String STATUS_CODE_INVALID_PARAMETER = "NL-BT-OPEN_ACTION-01";
	private static final String STATUS_CODE_TECHNICAL_ERROR = "NL-BT-OPEN_ACTION-02";
	private static final String STATUS_CODE_BAD_CONTEXT = "NL-BT-OPEN_ACTION-03";
	private NeoLoadBrowserEngine neoLoadBrowserEngine;

	public SampleResult execute(Context context, List<ActionParameter> parameters) {
		final SampleResult sampleResult = new SampleResult();
		final StringBuilder requestBuilder = new StringBuilder();
		final StringBuilder responseBuilder = new StringBuilder();

		final Map<String, Optional<String>> parsedArgs;
		try {
			parsedArgs = parseArguments(parameters, OpenPageActionOption.values());
		} catch (final IllegalArgumentException iae) {
			return ResultFactory.newErrorResult(context, STATUS_CODE_INVALID_PARAMETER, "Could not parse arguments: ", iae);
		}



		final String btUrl = parsedArgs.get(OpenPageActionOption.URL.getName()).get();
		final String browserVersion=parsedArgs.get(OpenPageActionOption.BrowserVersion.getName()).get();
		final Optional<String> proxyHost=parsedArgs.get(OpenPageActionOption.ProxyHost.getName());
		final Optional<String> proxyPort=parsedArgs.get(OpenPageActionOption.ProxyPORT.getName());
		final Optional<String> performance=parsedArgs.get(OpenPageActionOption.Performance.getName());
		final Optional<String> clearcache=parsedArgs.get(OpenPageActionOption.ClearCache.getName());
		final Optional<String> clearcookies=parsedArgs.get(OpenPageActionOption.ClearCookie.getName());

		final Optional<String> tracemode=parsedArgs.get((OpenPageActionOption.TraceMode.getName()));


		final Logger logger = context.getLogger();
		if (logger.isDebugEnabled()) {
			logger.debug("Executing " + this.getClass().getName() + " with parameters: "
					+ getArgumentLogString(parsedArgs, OpenPageActionOption.values()));
		}
		

	
		try {

			neoLoadBrowserEngine= (NeoLoadBrowserEngine) context.getCurrentVirtualUser().get("NeoLoadBrowser");
			if(neoLoadBrowserEngine==null) {
				BrowserContext browserContext = new BrowserContext(tracemode, context, Optional.<String>absent(), performance,clearcache,clearcookies);
				neoLoadBrowserEngine = new NeoLoadBrowserEngine(proxyHost, proxyPort, browserVersion, browserContext);
				context.getCurrentVirtualUser().put("NeoLoadBrowser", neoLoadBrowserEngine);
			}
			else
			{
				neoLoadBrowserEngine.getContext().setContext(context);
				neoLoadBrowserEngine.getContext().setTracemode(tracemode);
				neoLoadBrowserEngine.getContext().setPerformance(performance);
				neoLoadBrowserEngine.getContext().setClearCookies(clearcookies);
				neoLoadBrowserEngine.getContext().setClearcache(clearcache);
			}
			String output=neoLoadBrowserEngine.loadPage(btUrl,sampleResult);

			appendLineToStringBuilder(responseBuilder, output);

	    }
	    catch (HttpExeption e)
		{
			return ResultFactory.newErrorResult(context, STATUS_CODE_BAD_CONTEXT, "HTTP error: ", e);

		}
		catch(Exception e)
		{
			return  ResultFactory.newErrorResult(context, STATUS_CODE_TECHNICAL_ERROR,  "HTtml Unit technical Error ", e);
		}
		
		sampleResult.setRequestContent(requestBuilder.toString());
		sampleResult.setResponseContent(responseBuilder.toString());
		

		return sampleResult;
	}

	private void appendLineToStringBuilder(final StringBuilder sb, final String line){
		sb.append(line).append("\n");
	}

	/**
	 * This method allows to easily create an error result and log exception.
	 */
	private static SampleResult getErrorResult(final Context context, final SampleResult result, final String errorMessage, final Exception exception) {
		result.setError(true);
		result.setStatusCode("NL-HtmlUnitOpenPage_ERROR");
		result.setResponseContent(errorMessage);
		if(exception != null){
			context.getLogger().error(errorMessage, exception);
		} else{
			context.getLogger().error(errorMessage);
		}
		return result;
	}

	@Override
	public void stopExecute() {
		// TODO add code executed when the test have to stop.
		neoLoadBrowserEngine.closeBrowser();
	}

}
