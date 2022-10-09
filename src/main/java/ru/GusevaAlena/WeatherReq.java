package ru.GusevaAlena;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import ru.FromJSON.*;
public class WeatherReq {
    @SneakyThrows
    public String Request(String location){
        String api_key = "338234f7733c87b7abd934cd8b83aa39";
        String myUrl = "http://api.openweathermap.org/data/2.5/weather?q="+location+
                "&appid="+api_key;
        URL url = new URL(myUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while((inputLine=in.readLine())!=null){
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}
