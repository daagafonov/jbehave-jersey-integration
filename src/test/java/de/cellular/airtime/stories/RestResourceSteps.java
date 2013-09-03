package de.cellular.airtime.stories;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import junit.framework.Assert;
import net.anotheria.util.StringUtils;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;

/**
 * JBehave class to support REST resources general behavior.
 * 
 * @author daagafonov@gmail.com
 * 
 */
public class RestResourceSteps {

	/**
	 * 
	 */
	private Client client;

	/**
	 * 
	 */
	private Map<String, String> headers = new HashMap<String, String>();

	/**
	 * 
	 */
	private ClientResponse response;

	/**
	 * 
	 */
	private Map<String, String> variables = new HashMap<String, String>();

	/**
	 * 
	 */
	private JSONObject json;

	/**
	 * 
	 */
	private String basePath;

	@BeforeStories
	public void beforeStories() throws Exception {
		final DefaultClientConfig config = new DefaultClientConfig();
		config.getClasses().add(JacksonJsonProvider.class);
		client = Client.create(config);
		client.addFilter(new LoggingFilter());
	}

	@Given("I set base path")
	public void setBasePath(@Named("server") String bp) {
		System.out.println(" >>> basePath=" + bp);
		this.basePath = bp;
	}

	@Given("I add \"$headerName\" header equal to \"$headerValue\"")
	public void header(String headerName, String headerValue) {
		System.out.println(" >>> name=" + headerName + ", value=" + headerName);
		headers.put(headerName, headerValue);
	}

	@When("I send a $method request on \"$path\" with body:\n$request")
	public void sendRequest(String method, String path, String request) {
		System.out.println(" >>> method=" + method + ", path=" + path + ", request=" + request);
		WebResource resource = client.resource(basePath).path(path);
		WebResource.Builder b = resource.getRequestBuilder();

		for (String key : headers.keySet()) {
			b = b.header(key, headers.get(key));
		}

		if ("POST".equalsIgnoreCase(method)) {
			response = b.post(ClientResponse.class, request);
		} else if ("GET".equalsIgnoreCase(method)) {
			// prepare GET request... fill query string
			response = b.get(ClientResponse.class);
		}
	}

	@When("I send a $method request on \"$path\" with the remembered \"$var\" as \"$value\" node in JSON body:\n$request")
	public void sendRequest(String method, String path, String var, String value, String request) {
		sendRequest(method, path, replaceVar(request, value, variables.get(var)));
	}

	@Then("the response status code should be $responseCode")
	public void responseShouldBe(int responseCode) {
		System.out.println("actual response code is " + response.getStatus());
		Assert.assertEquals(responseCode, response.getStatus());
	}

	@Then("the header \"$headerName\" should be contains \"$headerValue\"")
	@Alias("the header \"$headerName\" should contains \"$headerValue\"")
	public void responseShouldBe(String headerName, String headerValue) {
		Assert.assertEquals(headerValue, response.getHeaders().getFirst(headerName));
	}

	@Then("the response should be in JSON")
	public void responseShouldBeJSON() {
		try {
			json = new JSONObject(response.getEntity(String.class));
			// System.out.println(json.toString(4));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("json = new JSONObject(response.getEntity(String.class)) failed...");
		}
	}

	@Then("the JSON node \"$node\" should be equal to \"$value\"")
	public void nodeShouldHaveValue(String node, String value) throws JSONException {
		// System.out.println(variables);
		for (String s : node.split("\\.")) {
			stack.add(0, s);
		}
		String v = (String) getValue(json);
		Assert.assertEquals(value, v);
	}

	@Then("the JSON node \"$node\" should exists")
	public void nodeShouldBeExist(String node) throws JSONException {
		for (String s : node.split("\\.")) {
			stack.add(0, s);
		}
		String v = (String) getValue(json);
		Assert.assertTrue(!StringUtils.isEmpty(v));
	}

	@Then("I remember JSON node \"$node\" as \"$var\"")
	public void nodeRemember(String node, String var) throws JSONException {
		for (String s : node.split("\\.")) {
			stack.add(0, s);
		}
		String v = (String) getValue(json);
		variables.put(var, v);
	}

	@Then("the JSON node \"$node\" should be equal to the remembered \"$var\"")
	public void nodeShouldBeEqualToVar(String node, String var) throws JSONException {
		for (String s : node.split("\\.")) {
			stack.add(0, s);
		}
		String v = (String) getValue(json);
		Assert.assertEquals(variables.get(var), v);
	}

	/**
	 * 
	 */
	private Stack<String> stack = new Stack<String>();

	private Object getValue(JSONObject json1) throws JSONException {
		if (stack.isEmpty()) {
			return json1.toString();
		}
		String s = stack.pop();
		if (json1.isNull(s)) {
			return null;
		} else {
			if (stack.isEmpty()) {
				return json1.getString(s);
			} else {
				return getValue(json1.getJSONObject(s));
			}
		}
	}

	/**
	 * 
	 * @param initial
	 * @param var
	 * @param value
	 * @return
	 */
	private String replaceVar(String initial, String var, String value) {
		// System.out.println("initial=" + initial);
		// System.out.println("var=" + var);
		// System.out.println("value=" + value);
		String res = initial.replaceAll("%" + var + "%", value);
		// System.out.println("result=" + res);
		return res;
	}

}
