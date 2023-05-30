package com.Travel.Travel.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

//las clases de utileria solo llevan metodos statics
public class BestTravelUtil {
    private static final Random dateRandom = new Random();
    public static LocalDateTime getRandomSoon(){
        //hora de salida
        var randomHours =  dateRandom.nextInt(5-2)+2; //devuelve horas entre 2 y 5
        var now = LocalDateTime.now();
        return now.plusHours(randomHours);
    }

    public static LocalDateTime getRandomLatter(){
        //hora a aumentar a la fecha de llegada
        var randomHours =  dateRandom.nextInt(12-6)+6; //devuelve horas entre 6 y 12
        var now = LocalDateTime.now();
        return now.plusHours(randomHours);
    }

    public static void writeNotification(String text, String path)throws IOException {
        var fileWriter = new FileWriter(path,true);
        var bufferedWriter = new BufferedWriter(fileWriter);
        try(fileWriter; bufferedWriter){
            bufferedWriter.write(text);
            bufferedWriter.newLine();
        }
    }
}
