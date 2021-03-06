package com.cloudhopper.sxmp;

/*
 * #%L
 * ch-sxmp
 * %%
 * Copyright (C) 2012 - 2013 Cloudhopper by Twitter
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.cloudhopper.commons.util.HexUtil;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author joelauer
 */
public class PostMO {
    private static final Logger logger = LoggerFactory.getLogger(PostMO.class);

    static public void main(String[] args) throws Exception {

        String URL = "https://sms.twitter.com/receive/cloudhopper";
        String text = "HELP";
        String srcAddr = "+16504304922";

        String ticketId = System.currentTimeMillis()+"";
        String operatorId = "20";

        StringBuilder string0 = new StringBuilder(200)
            .append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n")
            .append("<operation type=\"deliver\">\n")
            .append(" <account username=\"customer1\" password=\"password1\"/>\n")
            .append(" <deliverRequest>\n")
            .append("  <operatorId>" + operatorId + "</operatorId>\n")
            .append("  <sourceAddress type=\"international\">" + srcAddr  + "</sourceAddress>\n")
            .append("  <destinationAddress type=\"network\">40404</destinationAddress>\n")
            .append("  <text encoding=\"ISO-8859-1\">" + HexUtil.toHexString(text.getBytes()) + "</text>\n")
            .append(" </deliverRequest>\n")
            .append("</operation>\n")
            .append("");

        HttpClient client = new DefaultHttpClient();
        client.getParams().setBooleanParameter("http.protocol.expect-continue", false);

        long start = System.currentTimeMillis();

        // execute request
        try {
            HttpPost post = new HttpPost(URL);

            StringEntity entity = new StringEntity(string0.toString(), "ISO-8859-1");
            entity.setContentType("text/xml; charset=\"ISO-8859-1\"");
            post.setEntity(entity);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            String responseBody = client.execute(post, responseHandler);

            logger.debug("----------------------------------------");
            logger.debug(responseBody);
            logger.debug("----------------------------------------");
        } finally {
            // do nothing
        }

        long end = System.currentTimeMillis();

        logger.debug("Response took " + (end-start) + " ms");

    }
}
