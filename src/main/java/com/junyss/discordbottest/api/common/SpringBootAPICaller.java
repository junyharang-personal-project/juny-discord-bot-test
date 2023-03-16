package com.junyss.discordbottest.api.common;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class SpringBootAPICaller {

    private SpringBootAPICaller() {}

    public static String callApi(String httpMethodType, URL url) throws IOException {
        StringBuilder resultMessage = new StringBuilder();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(httpMethodType);
        connection.setConnectTimeout(5000);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setDoOutput(true);

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                JSONObject jsonObject = new JSONObject();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    jsonObject = new JSONObject(line);
                }

                JSONArray data = jsonObject.getJSONArray("data");

                for (int index = 0; index < data.length(); index++) {
                    JSONObject dataJSONObject = data.getJSONObject(index);

                    resultMessage.append(index + 1)
                                 .append("번째 크루 정보 \n")
                                 .append("고유 번호 : ").append(dataJSONObject.getInt("idx")).append("\n")
                                 .append("계정 정보(ID) : ").append(dataJSONObject.getString("id")).append("\n")
                                 .append("가입일 : ").append(dataJSONObject.getString("joinDate")).append("\n")
                                 .append("이름 : ").append(dataJSONObject.getString("name")).append("\n")
                                 .append("E-mail : ").append(dataJSONObject.getString("email")).append("\n\n\n");
                }
                return resultMessage.toString();
            } catch (IOException ioException) {
                log.error("Spring Boot API를 통해 값을 조회하는 중 문제가 발생하였습니다." + ioException.getMessage());
            }
        } else {
            log.error("Spring Boot API 작업 중 문제가 발생하였습니다. \n");
            resultMessage.append("명령을 수행하는데 문제가 발생하였어요. 😭").append("\n");
            resultMessage.append("Response Code : ");
            resultMessage.append(connection.getResponseCode()).append("\n");
            resultMessage.append("Response Message : ");
            resultMessage.append(connection.getResponseMessage()).append("\n");

            return resultMessage.toString();
        }
    return "Spring Boot API 호출 간 문제가 발생하였습니다.";
    }
}
