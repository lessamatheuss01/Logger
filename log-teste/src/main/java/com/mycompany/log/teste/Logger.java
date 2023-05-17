/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.log.teste;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author mathe
 */
public class Logger {

    private static final String LOG_DIRECTORY = "C:/Users/mathe/OneDrive/Documentos/Arquivos/";
    private static final String LOG_FILENAME_PREFIX = "log_";
    private static final String LOG_FILENAME_SUFFIX = ".txt";
    private static final Integer MAX_FILE_SIZE = 1024 * 1024; // 1MB

    private static File currentLogFile;
    private static PrintWriter printWriter;

    public static void log(String massage, LogLevel level) {
        // Verifica se o arquivo de LOG atual ainda pode ser usad
        if (currentLogFile != null && currentLogFile.length() >= MAX_FILE_SIZE) {
            close();
        }
        
        // Cria um novo arquivo de LOG, se necessário
        if (currentLogFile == null) {
            createNewLogFile();
        }
        
        // Escreve a mensagem no arquivo de LOG se o nível for igual ou superior ao nível configurado
        String message = "Teste";
        if(level.ordinal() >= getLogLevel().ordinal()){
            printWriter.println(level + ":" + message);
            printWriter.flush();
        }
    }

    private enum LogLevel{
        BASIC, MEDIUM, HIGH;
    }
    
    private static void createNewLogFile() {
        File directory = new File(LOG_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }

         // Cria um nome único para o arquivo de LOG
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = dateFormat.format(new Date());
        String filename = LOG_DIRECTORY + LOG_FILENAME_PREFIX + date + LOG_FILENAME_SUFFIX;
        currentLogFile = new File(filename);
        try {
            printWriter = new PrintWriter(new BufferedWriter(new FileWriter(currentLogFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void close(){
        if(printWriter != null){
            printWriter.close();
        }
        
        currentLogFile = null;
        printWriter = null;
    }

    // Define o nível de LOG com base na variável de ambiente "LOG_LEVEL"
    private static LogLevel getLogLevel() {
        String loglevel = System.getenv("LOG_LEVEL");
        if (loglevel == null) { 
            return LogLevel.BASIC;
        }
        switch (loglevel.toUpperCase()) {
            case "BASIC":
                return LogLevel.BASIC;
            case "MEDIUM":
                return LogLevel.MEDIUM;
            case "HIGH":
                return LogLevel.HIGH;
            default:
                return LogLevel.BASIC;
        }
    }
    
    public static void main(String[] args) {
        Logger.log("nada", LogLevel.BASIC);
        Logger.log("nada", LogLevel.MEDIUM);
        Logger.log("teste",LogLevel.HIGH);
        Logger.log("teste",LogLevel.MEDIUM);
    }
}
