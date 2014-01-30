
package main;

import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import java.io.*;
import java.net.URI;

/**
 *
 * @author yayugu
 * singleton class
 */
final class Data {
    private static final Data instance = new Data();
    private Data(){};
    public static Data getInstance(){
        return Data.instance;
    }

    private Twitter twitter;
    public RequestToken requestToken;
    public AccessToken accessToken = null;
    public final String consumerKey = "5QaWBaKJC1hX2LeOlCtFcw";
    public final String consumerSecret = "6qXH3gCuiratVU0y5VYruh3QJDaIzKnuTBuqPZ0LJ4A";
    private final String DATA_LOCATION = System.getProperty("user.home")+"/.ikazuchi_tairiku";
    

    public void saveData(){
        System.err.println(DATA_LOCATION + " <- config file.");
        try{
            BufferedWriter bw =
                new BufferedWriter(
                    new OutputStreamWriter(
                        new FileOutputStream(DATA_LOCATION), "UTF-8"));
            bw.write(accessToken.getToken() + "\r\n");
            bw.write(accessToken.getTokenSecret() + "\r\n");
            bw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loadData() throws IOException {
        BufferedReader br =
            new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(DATA_LOCATION),"UTF-8"));
        String token = br.readLine();
        String tokenSecret = br.readLine();
        br.close();
        instantiateTwitter();
        accessToken = new AccessToken(token, tokenSecret);
        twitter.setOAuthAccessToken(accessToken);
    }
    
	public Twitter getTwitter() {
	    if(twitter == null) instantiateTwitter();
		return twitter;
	}

	public void instantiateTwitter() {
    	twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(consumerKey, consumerSecret);
	}
	public URI getAuthorizationURL() {
        try {
			requestToken = getTwitter().getOAuthRequestToken();
	        return new URI(requestToken.getAuthorizationURL());
		} catch (Exception e) {
			return null;
		}
	}
}

