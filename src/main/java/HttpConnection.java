import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;

public class HttpConnection {




    public static void main(String[] args) {


        /*
        Описываем параметры запроса интернет адреса URL
        версию протокола
        метод отправки данных GET
        устанавливаем время тайм аут

        добавляем заголовок запроса метод аунтефикаций
         */
       HttpRequest httpRequest = HttpRequest.newBuilder()
               .uri(URI.create(""))
               .version(HttpClient.Version.HTTP_1_1)
               .timeout(Duration.ofSeconds(5))
               .GET()
            .header("Authorization", getBasicAuthenticationHeader("", ""))
               .build();



        //Описываем отправку запроса и получение ответа от сервера

       HttpClient httpClient = HttpClient.newBuilder()
               .version(HttpClient.Version.HTTP_1_1)
               .followRedirects(HttpClient.Redirect.NORMAL)
               .connectTimeout(Duration.ofSeconds(5))
               .build();


       //Отправляем запрос с обработкой сервера
       try {
           HttpResponse <byte[]> responce = httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofByteArray());

           if(responce.statusCode()>= 200 && responce.statusCode()<300){
               System.out.println(new String( responce.body(), StandardCharsets.UTF_8));

           }

           else{
            System.out.println("Код:" + responce.statusCode());

           }



       } catch (IOException e) {
           e.printStackTrace();
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
    }
    //Метод аунтефикации  кодировки Base64
    public static final String getBasicAuthenticationHeader(String login, String password) {

        String valueToEncode = login+ ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());

    }



}
