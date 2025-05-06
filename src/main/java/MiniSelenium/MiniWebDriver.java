package MiniSelenium;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MiniWebDriver {
    private final String baseUrl = "http://localhost:64312/session";
    private final String sessionId;
    private final String sessionUrl;

    public MiniWebDriver() throws Exception {
        JSONObject caps = new JSONObject()
                .put("capabilities", new JSONObject()
                        .put("alwaysMatch", new JSONObject()
                                .put("browserName", "chrome")));

        JSONObject response = sendRequest("POST", baseUrl, caps);
        sessionId = response.getJSONObject("value").getString("sessionId");
        sessionUrl = baseUrl + "/" + sessionId;
    }

    public void navigate(String url) throws Exception {
        JSONObject payload = new JSONObject().put("url", url);
        sendRequest("POST", sessionUrl + "/url", payload);
    }

    public String findElement(String using, String value) throws Exception {
        JSONObject payload = new JSONObject()
                .put("using", using)
                .put("value", value);
        JSONObject response = sendRequest("POST", sessionUrl + "/element", payload);
        return response.getJSONObject("value").getString("element-6066-11e4-a52e-4f735466cecf");
    }

    public void click(String elementId) throws Exception {
        sendRequest("POST", sessionUrl + "/element/" + elementId + "/click", new JSONObject());
    }

    public void sendKeys(String elementId, String text) throws Exception {
        JSONArray keys = new JSONArray();
        for (char c : text.toCharArray()) {
            keys.put(String.valueOf(c));
        }
        JSONObject payload = new JSONObject().put("text", text).put("value", keys);
        sendRequest("POST", sessionUrl + "/element/" + elementId + "/value", payload);
    }

    public String getText(String elementId) throws Exception {
        JSONObject response = sendRequest("GET", sessionUrl + "/element/" + elementId + "/text", null);
        return response.getString("value");
    }

    public void quit() throws Exception {
        sendRequest("DELETE", sessionUrl, null);
    }

    private JSONObject sendRequest(String method, String urlString, JSONObject payload) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json"); // Add Accept header

        if (method.equals("POST") || method.equals("DELETE")) {
            conn.setDoOutput(true);
            if (payload != null) {
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(payload.toString().getBytes());
                }
            }
        }

        int responseCode = conn.getResponseCode();
        InputStream is;
        if (responseCode >= 200 && responseCode < 300) {
            is = conn.getInputStream();
        } else {
            is = conn.getErrorStream();
        }

        Scanner scanner = new Scanner(is).useDelimiter("\\A");
        String responseBody = scanner.hasNext() ? scanner.next() : "";
        scanner.close();

        if (responseCode >= 400) {
            throw new IOException("HTTP " + responseCode + " Error: " + responseBody);
        }

        return new JSONObject(responseBody);
    }
}
