package com.neotys.htmlunit.HtmlUnitUtils;

import static org.junit.Assert.assertEquals;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
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
		HtmlPage page=client.getPage("http://ec2-3-127-90-16.eu-central-1.compute.amazonaws.com:8780");
		String output=page.getWebResponse().getContentAsString();
		Performance performance= (Performance)  page.executeJavaScript("performance").getJavaScriptResult();
		System.out.println(performance.getTiming().getConnectStart());
		HtmlAnchor anchor=page.getAnchorByHref("SelectCat.action?catId=2");
		String response=anchor.click().getWebResponse().getContentAsString();
	}

}
