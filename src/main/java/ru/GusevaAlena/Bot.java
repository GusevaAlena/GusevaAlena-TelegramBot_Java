package ru.GusevaAlena;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.FromJSON.Example;
import ru.FromJSON.Weather;


import java.util.List;
import java.util.Optional;


public class Bot extends TelegramLongPollingBot {
    private static final String TOKEN = "1949523364:AAHeY1aWjzaUJHGTMdgzPrw26Rj017zNP8E";
    private static final String USERNAME="@JavaLesson_weather_bot";

    public String getBotToken(){return TOKEN;}
    public String getBotUsername(){return USERNAME;}

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()){
            handleMessage(update.getMessage());
        }
    }
    @SneakyThrows
    private void handleMessage(Message message) {
        if(message.hasText()&&message.hasEntities()){
            Optional<MessageEntity> commandEntity =
            message.getEntities().stream().filter(e->"bot_command".equals(e.getType())).findFirst();
            if (commandEntity.isPresent()){
                String command = message.getText().substring(commandEntity.get().getOffset(),commandEntity.get().getLength());
                switch (command){
                    case "/enter_city":
                        execute(SendMessage.builder().text("Please enter the city").chatId(message.getChatId().toString()).build());
                        return;
                }
            }
        }
        if(message.hasText()){
            WeatherReq w = new WeatherReq();
            String weather = w.Request(message.getText());
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse(weather).getAsJsonObject();
            Example weather_resp = gson.fromJson(object,Example.class);
            execute(SendMessage.builder().text("The weather in "+ message.getText() + " is: \n" + getResult(weather_resp)).chatId(message.getChatId().toString()).build());
        }
    }
    public static String getResult(Example weather){
        List<Weather> w_list  = weather.getWeather();
        StringBuilder weatherSB = new StringBuilder();
        for (Weather rw:w_list){
            weatherSB.append(rw.getDescription());
        }
        StringBuilder result = new StringBuilder();
        double temp = Math.round(weather.getMain().getTemp()-273);
        double pressure = weather.getMain().getPressure();
        double humidity = weather.getMain().getHumidity();
        double FeelsLike = weather.getMain().getFeelsLike()-273;
        double WindSpeed = weather.getWind().getSpeed();
        result.append("Temperature: "+temp+" °C\n");
        result.append("Pressure: "+pressure+" hPa\n");
        result.append("Humidity: "+humidity+" %\n");
        result.append("Feels like: "+FeelsLike+" °C\n");
        result.append("Wind speed: "+WindSpeed+" m/s\n");
        return result.toString();
    }

}

