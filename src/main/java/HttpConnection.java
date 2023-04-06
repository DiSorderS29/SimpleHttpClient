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
        ��������� ��������� ������� �������� ������ URL
        ������ ���������
        ����� �������� ������ GET
        ������������� ����� ���� ���

        ��������� ��������� ������� ����� ������������
         */
       HttpRequest httpRequest = HttpRequest.newBuilder()
               .uri(URI.create(""))
               .version(HttpClient.Version.HTTP_1_1)
               .timeout(Duration.ofSeconds(5))
               .GET()
            .header("Authorization", getBasicAuthenticationHeader("", ""))
               .build();



        //��������� �������� ������� � ��������� ������ �� �������

       HttpClient httpClient = HttpClient.newBuilder()
               .version(HttpClient.Version.HTTP_1_1)
               .followRedirects(HttpClient.Redirect.NORMAL)
               .connectTimeout(Duration.ofSeconds(5))
               .build();


       //���������� ������ � ���������� �������
       try {
           HttpResponse <byte[]> responce = httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofByteArray());

           if(responce.statusCode()>= 200 && responce.statusCode()<300){
               System.out.println(new String( responce.body(), StandardCharsets.UTF_8));

           }

           else{
            System.out.println("���:" + responce.statusCode());

           }



       } catch (IOException e) {
           e.printStackTrace();
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
    }
    //����� ������������  ��������� Base64
    public static final String getBasicAuthenticationHeader(String login, String password) {

        String valueToEncode = login+ ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());

    }



}
