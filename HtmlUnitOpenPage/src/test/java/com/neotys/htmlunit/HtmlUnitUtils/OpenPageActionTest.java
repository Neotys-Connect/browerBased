package com.neotys.htmlunit.HtmlUnitUtils;

import static org.junit.Assert.assertEquals;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import com.gargoylesoftware.htmlunit.javascript.host.performance.Performance;
import com.neotys.htmlunit.customActions.OpenPageAction;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class OpenPageActionTest {
	@Test
	public void shouldReturnType() throws IOException {

		WebClient client=new WebClient(BrowserVersion.CHROME);
		client.getOptions().setThrowExceptionOnScriptError(false);

		client.waitForBackgroundJavaScript(3000);
		//client.getOptions().
		HtmlPage page=client.getPage("http://sampledemo.neotys.com/");
		String output=page.getWebResponse().getContentAsString();
		Performance performance= (Performance)  page.executeJavaScript("performance").getJavaScriptResult();
		System.out.println(performance.getTiming().getConnectStart());
	}

}
