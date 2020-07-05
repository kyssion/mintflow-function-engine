package org.mintflow.vertx.http.util;

import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.http.impl.CookieImpl;
import io.vertx.core.json.Json;

import java.util.Map;

public class HttpUtil {
    public static class ResponseUtil {
        public static void addCookies(MultiMap cookies, HttpServerResponse httpServerResponse) {
            for (Map.Entry<String, String> item : cookies) {
                httpServerResponse.addCookie(new CookieImpl(item.getKey(), item.getValue()));
            }
        }

        public static void addHeader(MultiMap header, HttpServerResponse httpServerResponse) {
            for (Map.Entry<String, String> item : header) {
                httpServerResponse.putHeader(item.getKey(), item.getValue());
            }
        }

        public static void addStatusCode(Integer statusCode, HttpServerResponse httpServerResponse) {
            httpServerResponse.setStatusCode(statusCode);
        }

        public static void addStatusMessage(String statusMessage, HttpServerResponse httpServerResponse) {
            httpServerResponse.setStatusMessage(statusMessage);
        }

        public static void addData(Object data, HttpServerResponse httpServerResponse) {
            httpServerResponse.setChunked(true);
            httpServerResponse.write(Json.encode(data));
        }
    }

}
