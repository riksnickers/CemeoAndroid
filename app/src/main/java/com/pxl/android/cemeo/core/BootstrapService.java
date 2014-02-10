
package com.pxl.android.cemeo.core;


import android.widget.Toast;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;
import com.github.kevinsawicki.wishlist.Toaster;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.pxl.android.cemeo.util.Ln;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pxl.android.cemeo.core.Constants.Http.URL_CHECKINS;
import static com.pxl.android.cemeo.core.Constants.Http.URL_CONTACTS;
import static com.pxl.android.cemeo.core.Constants.Http.URL_LOCATIONS;
import static com.pxl.android.cemeo.core.Constants.Http.URL_MEETING;
import static com.pxl.android.cemeo.core.Constants.Http.URL_NEWS;
import static com.pxl.android.cemeo.core.Constants.Http.URL_PROPOSITION;
import static com.pxl.android.cemeo.core.Constants.Http.URL_PROP_ANSWER;
import static com.pxl.android.cemeo.core.Constants.Http.URL_USER_DATA;
import static com.pxl.android.cemeo.core.Constants.Http.URL_CREATE_MEETING;

/**
 * Cemeo API service
 */
public class BootstrapService {

    private UserAgentProvider userAgentProvider;

    /**
     * GSON instance to use for all request  with date format set up for proper parsing.
     */
    //public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    public static final Gson GSON = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

    /**
     * You can also configure GSON with different naming policies for your API. Maybe your api is Rails
     * api and all json values are lower case with an underscore, like this "first_name" instead of "firstName".
     * You can configure GSON as such below.
     *
     * public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd").setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES).create();
     *
     */


    /**
     * Read and connect timeout
     */
    private static final int TIMEOUT = 10 * 1000;


    private static class UsersWrapper {

        private User[] results;

        public User[] getRes(){
            return results;
        }

    }

    private static class MeetingWrapper {

        private List<Meeting> results;
    }



    private static class JsonException extends IOException {

        private static final long serialVersionUID = 3774706606129390273L;

        /**
         * Create exception from {@link JsonParseException}
         *
         * @param cause
         */
        public JsonException(JsonParseException cause) {
            super(cause.getMessage());
            initCause(cause);
        }
    }


    private final String apiKey;
    private final String username;
    private final String password;

    /**
     * Create bootstrap service
     *
     * @param username
     * @param password
     */
    public BootstrapService(final String username, final String password) {
        this.username = username;
        this.password = password;
        this.apiKey = null;
    }

    /**
     * Create bootstrap service
     *
     * @param userAgentProvider
     * @param apiKey
     */
    public BootstrapService(final String apiKey, final UserAgentProvider userAgentProvider) {
        this.userAgentProvider = userAgentProvider;
        this.username = null;
        this.password = null;
        this.apiKey = apiKey;
    }


    public String getApiKey() {
        return this.apiKey;
    }

    /**
     * Execute request
     *
     * @param request
     * @return request
     * @throws IOException
     */
    protected HttpRequest execute(HttpRequest request) throws IOException {
        if (!configure(request).ok())
            throw new IOException("Unexpected response code: " + request.code());
        return request;
    }

    private HttpRequest configure(final HttpRequest request) {
        request.connectTimeout(TIMEOUT).readTimeout(TIMEOUT);
        //request.userAgent(userAgentProvider.get());

        if (isPostOrPut(request))
            request.contentType(Constants.Http.CONTENT_TYPE_JSON); // Alle PUT & POST requests naar de api in JSON formaat


        return addCredentialsTo(request);
    }

    private boolean isPostOrPut(HttpRequest request) {
        return request.getConnection().getRequestMethod().equals(HttpRequest.METHOD_POST)
                || request.getConnection().getRequestMethod().equals(HttpRequest.METHOD_PUT);

    }

    private HttpRequest addCredentialsTo(HttpRequest request) {

        //request.header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY );
        //request.header(HEADER_PARSE_APP_ID, PARSE_APP_ID);


        /**
         * NOTE: This may be where you want to add a header for the api token that was saved when you
         * logged in. In the bootstrap sample this is where we are saving the session id as the token.
         * If you actually had received a token you'd take the "apiKey" (aka: token) and add it to the
         * header or form values before you make your requests.
         */

        /**
         * Add the user name and password to the request here if your service needs username or password for each
         * request. You can do this like this:
         * request.basic("myusername", "mypassword");
         */

        Ln.d("statuslog : Authorization : Bearer " + apiKey);

        //Authenticatie token meegeven bij elke request.
        request.header("Authorization", "Bearer " + apiKey);


        return request;
    }

    private <V> V fromJson(HttpRequest request, Class<V> target) throws IOException {
        Reader reader = request.bufferedReader();
        try {
            //json van httprequest omzetten naar een lijst
            return GSON.fromJson(reader, target);
        } catch (JsonParseException e) {
            throw new JsonException(e);
        } finally {
            try {
                reader.close();
            } catch (IOException ignored) {
                // Ignored
            }
        }
    }
    /**
     * Create Meeting
     *
     * @return result
     * @throws IOException
     */

    public Boolean createMeeting(Schedule schedule) throws IOException {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(schedule);

            Ln.d("statuslog : json = %s", json);

            //HttpRequest request = execute(HttpRequest.post(URL_CREATE_MEETING).send(json));

            HttpRequest request = HttpRequest.post(URL_CREATE_MEETING).header("Authorization", "Bearer " + apiKey).header("Content-Type" , "application/json").send(json).connectTimeout(800);

            if(request.ok()){
                Ln.d("statuslog : meeting aanmaken is gelukt!");
                return true;
            }else{
                Ln.d("statuslog : meeting aanmaken is mislukt!");
                return false;
            }





        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }

    /**
     * Answer prop
     *
     * @return result
     * @throws IOException
     */

    public Boolean answerProposition(PropositionAnswer answer) throws IOException {
        try {

            Gson gson = new Gson();
            String json = gson.toJson(answer);

            Ln.d("statuslog : json = %s", json);

            //HttpRequest request = execute(HttpRequest.post(URL_CREATE_MEETING).send(json));

            HttpRequest request = HttpRequest.post(URL_PROP_ANSWER).header("Authorization", "Bearer " + apiKey).header("Content-Type" , "application/json").send(json).connectTimeout(5000);

            Ln.d("statuslog : req = %s" , request.code());

            if(request.ok()){
                Ln.d("statuslog : Proposition is aanvaard !");
                return true;
            }else{
                Ln.d("statuslog : Proposition is aanvaarden is mislukt !");
                return false;
            }





        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }

    /**
     * Get all Contacts
     *
     * @return non-null but possibly empty list
     * @throws IOException
     */

    public List<Contact> getContacts() throws IOException {
        try {
            HttpRequest request = execute(HttpRequest.get(URL_CONTACTS));
            Ln.d("statuslog : request = %s", request);

            Contact[] contacts = fromJson(request , Contact[].class);
            //User[] users = fromJson(request, User[].class);

            //oude manier
            //UsersWrapper response = fromJson(request , UsersWrapper.class);
            //if (response != null && response.results != null)
/*
            if (request != null && users != null) {
                return Arrays.asList(users);
                //return response.results;
            }
*/

            if (request != null && contacts != null) {
                return Arrays.asList(contacts);
                //return response.results;
            }

            return Collections.emptyList();

        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }

    /**
     * Get all Contacts
     *
     * @return non-null but possibly empty list
     * @throws IOException
     */

    public List<User> getUsers() throws IOException {
        try {
            HttpRequest request = execute(HttpRequest.get(URL_CONTACTS));
            Ln.d("statuslog : request = %s", request);

            User[] users = fromJson(request, User[].class);

            if (request != null && users != null) {
                return Arrays.asList(users);
                //return response.results;
            }


            return Collections.emptyList();


        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }

    /**
     * Get all Contacts
     *
     * @return non-null but possibly empty list
     * @throws IOException
     */

    public User getUserData() throws IOException {
        try {
            HttpRequest request = execute(HttpRequest.get(URL_USER_DATA));

            User user = fromJson(request, User.class);


            if (request != null && user != null) {

                //return Arrays.asList(lijstje);
                return user;

            }

            Ln.d("statuslog : request = %s", request);

            //return Collections.emptyList();
            return null;

        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }


    /**
     * Get all Contacts
     *
     * @return non-null but possibly empty list
     * @throws IOException
     */

    public List<Location> getLocations() throws IOException {
        try {
            HttpRequest request = execute(HttpRequest.get(URL_LOCATIONS));
            Ln.d("statuslog : request = %s", request);

            Location[] locations = fromJson(request , Location[].class);

            if (request != null && locations != null) {
                return Arrays.asList(locations);
                //return response.results;
            }

            return Collections.emptyList();

        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }


    /**
     * Get all meetings
     *
     * @return non-null but possibly empty list of meetings
     * @throws IOException
     */

    public List<Meetings> getMeetings() throws IOException {

        try {
            HttpRequest request = execute(HttpRequest.get(URL_MEETING));

            Meetings[] meetings = fromJson(request, Meetings[].class);

            if (request != null && meetings != null) {
                return Arrays.asList(meetings);
                //return response.results;
            }


            return Collections.emptyList();

        } catch (HttpRequestException e) {
            throw e.getCause();
        }


    }

    /**
     * Get all propositions
     *
     * @return non-null but possibly empty list of meetings
     * @throws IOException
     */

    public List<MeetingProposition> getPropositions() throws IOException {

        try {
            HttpRequest request = execute(HttpRequest.get(URL_PROPOSITION));

            MeetingProposition[] meetingprop = fromJson(request, MeetingProposition[].class);

            if (request != null && meetingprop != null) {
                return Arrays.asList(meetingprop);
                //return response.results;
            }


            return Collections.emptyList();

        } catch (HttpRequestException e) {
            throw e.getCause();
        }


    }



}
