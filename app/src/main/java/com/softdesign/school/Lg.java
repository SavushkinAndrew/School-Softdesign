package com.softdesign.school;
import android.util.Log;




/**
 * Зарефакторить код логера в соответствии с данными на лекции рекомендациями, исспользовать подход DRY Don’t repeat yourself (не повторяй себя) - 
 * т.е. избегаем повторения уже ранее написанного кода + Javadoc, 
 * логер должен исспользовать различные уровни вывода логов (Verbose, debug, info, error, warn, assert ).
 */
public class Lg {

    private static final String PREFIX = "HTC ";
    public static final int LOGCAT_BUFFER_SIZE = 3000;

    /*
    * Возвращает true, если условие верное, false если нет
    */
    private static boolean shouldLog(String tag,String text) {
        if (text.length() > LOGCAT_BUFFER_SIZE) {
            while (text.length() > LOGCAT_BUFFER_SIZE){
                text = text.substring(0, LOGCAT_BUFFER_SIZE);
                Log.i(PREFIX + tag, text);
            }
            return true;
        }else {
            return false;
        }

    }

    /*
    * Проверяет условие и отправляет текст в информационные логи
    */
    public static void i (String tag, String text) {
        if (shouldLog(tag,text)){
            Log.i(PREFIX + tag, text);
        }else {
            Log.i(PREFIX + tag, text);
        }
    }

    /*
   * Проверяет условие и отправляет текст в логи ошибок
   */
    public static void e (String tag, String text) {
        if (shouldLog(tag,text)) {
            Log.e(PREFIX + tag, text);
        } else {
            Log.e(PREFIX + tag, text);
        }
    }

    /*
   * Проверяет условие и отправляет текст в логи warn
   */
    public static void w (String tag, String text) {
        if (shouldLog(tag,text)) {
            Log.w(PREFIX + tag,text);
        } else {
            Log.w(PREFIX + tag, text);
        }
    }
}
