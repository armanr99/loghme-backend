package com.loghme.controllers.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;

import static com.loghme.configs.GeneralConstants.PATH_DELIM;

public class HTTPHandler {
    public static String getPathParam(HttpServletRequest request) {
        StringTokenizer tokenizer = new StringTokenizer(request.getPathInfo(), PATH_DELIM);
        return tokenizer.nextToken();
    }
}
