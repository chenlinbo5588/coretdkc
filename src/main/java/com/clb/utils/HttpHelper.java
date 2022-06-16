package com.clb.utils;



import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

public class HttpHelper {
    public HttpHelper() {
    }

    public static String doPost(String httpUrl, Map<String, Object> mapParam) throws IOException {
        String encoding = "UTF-8";
        String realContentType = "application/json";
        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String result = null;

//        Properties prop = System.getProperties();
//        prop.setProperty("http.proxyHost", "192.168.202.50");
//        prop.setProperty("http.proxyPort", "8887");
//        prop.setProperty("https.proxyHost", "192.168.202.50");
//        prop.setProperty("https.proxyPort", "8887");


        try {
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", realContentType);
            os = connection.getOutputStream();
            String param = JsonHelper.object2Json(mapParam);
            if (null != param && "" != param.trim()) {
                os.write(param.getBytes());
            }

            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(is, encoding));
                StringBuffer sbf = new StringBuffer();

                String temp;
                while((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }

                result = sbf.toString();
            }
        } catch (MalformedURLException var27) {
            var27.printStackTrace();
            throw var27;
        } catch (IOException var28) {
            var28.printStackTrace();
            throw var28;
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException var26) {
                    var26.printStackTrace();
                }
            }

            if (null != os) {
                try {
                    os.close();
                } catch (IOException var25) {
                    var25.printStackTrace();
                }
            }

            if (null != is) {
                try {
                    is.close();
                } catch (IOException var24) {
                    var24.printStackTrace();
                }
            }

            connection.disconnect();
        }

        return result;
    }
}
