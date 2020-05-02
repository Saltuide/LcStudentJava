package com.example.testprojectwithdasha;

import android.os.AsyncTask;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewsParser extends AsyncTask<String, String, String> {
   /* @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //В этом методе код перед началом выполнения фонового процесса
    }

    */

    @Override
    protected String doInBackground(String... params) {
            /*Этот метод выполняется в фоне
            Тут мы обращаемся к сайту и вытаскиваем его html код
            */
        /*
        String answer = "";// В эту переменную мы будем класть ответ от сайта. Пока что она пустая
        String url = "https://ictis.sfedu.ru/rasp/HTML/82.htm";// Адрес сайта с расписанием
        Document document = null;
        try {
            document = Jsoup.connect(url).get();// Коннектимся и получаем страницу
            answer = document.body().html();// Получаем код из тега body страницы
        } catch (IOException e) {
            // Если произошла ошибка, значит вероятнее всего, отсутствует соединение с интернетом
            // Загружаем в переменную answer оффлайн версию из txt файла
            try {
                BufferedReader read = new BufferedReader(new InputStreamReader(openFileInput("timetable.txt")));
                String str = "";
                while ((str = read.readLine())!=null){
                    answer +=str;
                }
                read.close();
                offline = true;//работаем в оффлайн режиме
            } catch (FileNotFoundException ex) {
                //Если файла с сохранённым расписанием нет, то записываем в answer пустоту
                answer = "";
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        //Убираю лишний текст из html
        //Заменяю html код отсутствия пары на запись nolessone
        //Убираю двойные пробелы
        answer = answer.replace("Пары","")
                .replace("Время","")
                .replace("<br>","br")
                .replace("<font face=\"Arial\" size=\"1\"></font><p align=\"CENTER\"><font face=\"Arial\" size=\"1\"></font>","nolessone")//Заменяем "сигнатуру" пустой пары на nolessone
                .replace("  ","");
        return Jsoup.parse(answer).text();//Вытаскиваем текст из кода в переменной answer и передаём в UI-поток
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

         */
            /*Этот метод выполняется при завершении фонового кода
            Сюда возвращаются данные из потока
             */

        /*
        request = "";//Начинаем формировать ответ
        String temp = result.toString();//Делаём временную строку
        // Записываем содержимое, в файл timetable.txt, в котором будем хранить оффлайн версию расписания
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(openFileOutput("timetable.txt",MODE_PRIVATE)));
            writer.write(temp);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean start = false;
        for(String str:temp.split("Неделя: ")){
            if(start) {
                //В начало каждой недели добавляем слово newweek и добавляем в request
                request += "newweek"+str.split("Расписание")[0] + "\n";
            }
            start = true;
        }
        // Добавляем к дням недели приставку newday, для дальнейшей разбивки строки
        request = request.replace("Пнд","newdayПнд").replace("Втр","newdayВтр")
                .replace("Срд","newdayСрд").replace("Чтв","newdayЧтв")
                .replace("Птн","newdayПтн").replace("Сбт","newdayСбт");
            /*Получаем дату дня
            Если count = 0, то вернётся дата сегодняшнего дня
            Если count = -1, то вчерашнего
            Если count = 1, то завтрашнего и т.д
             */

        /*
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,count);
        Date dayformat = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM");
        //Выводим результат
        timetable.setText(request);
        if(offline && !temp.equals("")){
            //Уведомляем пользователя, что загружена оффлайн версия расписания
            Toast.makeText(getApplicationContext(),"Загружена оффлайн версия расписания!",Toast.LENGTH_LONG).show();
        }
        //Если наш ответ равен пустоте, значит произошла ошибка
        if(temp.equals("")){
            Toast.makeText(getApplicationContext(),"Произошла ошибка!",Toast.LENGTH_LONG).show();
        }

    }
    */
         return null;
}

}
