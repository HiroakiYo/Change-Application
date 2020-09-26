/**
 * Twittwer guessing game
 * @author Leo Yang
 * @version 1.0
 */


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * this is the class that contains the game of guessing twitter.
 */
public class Guesser {
    static int num = 10;
    static int score;
    public static void main(String[] args) {
        game();
    }

    /**
     * This is the method that is responsible for the flow of the entire game.
     */
    public static void game() {
        System.out.println("Welcome to the game of guessing who is this twitter from! \n You will have 10 questions in total. Good Luck!");
        int trials = num;
        while(trials > 0) {
            System.out.println("Loading Question...\n");
            String t = tResult();
            String k = kResult();
            int src=Math.random()>0.5?1:0;
            if (src == 0){
                System.out.println(t.substring(12,t.indexOf('"',12)));
                System.out.println("if it is from Kanye press 1 and hit return; if it is from Trump press 0 and hit enter");
                Scanner s = new Scanner(System.in);
                int r = s.nextInt();
                if (r == 0) {
                    score++;
                    System.out.println("You are correct");
                } else {
                    System.out.println("You are wrong");
                }
            } else {
                System.out.println(k.substring(10,k.indexOf('"',10)));
                System.out.println("if it is from Kanye press 1 and hit return; if it is from Trump press 0 and hit enter");
                Scanner s = new Scanner(System.in);
                int r = s.nextInt();
                if (r == 1) {
                    score++;
                    System.out.println("You are correct");
                } else {
                    System.out.println("You are wrong");
                }
            }
            trials--;
        }
        System.out.printf("\nOut of %d questions, you guessed %d right. Good Job!", num, score);
    }

    /**
     * This method pulls the twitter of Trump from url inside the method
     * @return raw trump twitter data from online
     */
    public static String tResult(){
        String requestUrl = "https://api.whatdoestrumpthink.com/api/v1/quotes/random";
        String s = httpRequest(requestUrl);
        return s;
    }

    /**
     * This method pulls the twitter of Kanye from url inside the method
     * @return raw Kanye twitter data from online
     */
    public static String kResult(){
        String requestUrl = "https://api.kanye.rest";
        String s = httpRequest(requestUrl);
        return s;
    }

    /**
     * this method is responsible for accessing information from the website specified
     * @param requestUrl url that you want to get the data from.
     * @return raw data acquired from the website.
     */
    public static String httpRequest(String requestUrl){
        StringBuffer buffer = new StringBuffer();
        try{
            URL url = new URL(requestUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
            BufferedReader bufferedReader =  new BufferedReader(inputStreamReader);

            String str = null;
            while((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();

            urlConnection.disconnect();;
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        return buffer.toString();
    }
}


