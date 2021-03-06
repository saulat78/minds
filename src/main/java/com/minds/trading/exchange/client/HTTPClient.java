package com.minds.trading.exchange.client;

import java.io.IOException;
import java.util.List;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class HTTPClient
{
    public String postHttp(String url, List<NameValuePair> params, List<NameValuePair> headers) throws IOException
    {
        HttpPost post = new HttpPost(url);
        post.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        post.getEntity().toString();

        if (headers != null)
        {
            for (NameValuePair header : headers)
            {
                post.addHeader(header.getName(), header.getValue());
            }
        }

      //  HttpClient httpClient = HttpClientBuilder.create().build();
      //  HttpResponse response = httpClient.execute(post);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(post);
        
        HttpEntity entity = response.getEntity();
        String toReturn = null;
        if (entity != null)
        {
            toReturn= EntityUtils.toString(entity);

        }
        response.close();
        httpClient.close();
        return toReturn;
    }

    public String getHttp(String url, List<NameValuePair> headers) throws IOException
    {
        HttpRequestBase request = new HttpGet(url);

        if (headers != null)
        {
            for (NameValuePair header : headers)
            {
                request.addHeader(header.getName(), header.getValue());
            }
        }

       // HttpClient httpClient = HttpClientBuilder.create().build();
        //HttpResponse response = httpClient.execute(request);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(request);
        String toReturn = null;
        HttpEntity entity = response.getEntity();
        if (entity != null)
        {
        	 toReturn=  EntityUtils.toString(entity);

        }
        response.close();
        httpClient.close();
        return toReturn;
    }
}
