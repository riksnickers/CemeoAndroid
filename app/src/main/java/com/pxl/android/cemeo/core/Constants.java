

package com.pxl.android.cemeo.core;

/**
 * Bootstrap constants
 */
public class Constants {

    public static class Auth {
        private Auth() {
        }

        /**
         * Account type id
         */
        public static final String BOOTSTRAP_ACCOUNT_TYPE = "com.pxl.android.cemeo";

        /**
         * Account name
         */
        public static final String BOOTSTRAP_ACCOUNT_NAME = "Cemeo";

        /**
         * Provider id
         */
        public static final String BOOTSTRAP_PROVIDER_AUTHORITY = "com.pxl.android.cemeo.sync";

        /**
         * Auth token type
         */
        public static final String AUTHTOKEN_TYPE = "bearer";
    }


    public static class Http {
        private Http() {
        }

        /**
         * Base URL for all requests
         */
        //hotspot
        public static final String URL_BASE = "http://192.168.43.72:12429";

        //thuis
        //public static final String URL_BASE = "http://192.168.0.101:12429";

        /**
         * Authentication URL
         */
        public static final String URL_AUTH = URL_BASE + "/token";

        /**
         * List Users URL
         */
        public static final String URL_USERS = URL_BASE + "/api/Meeting/Contacts";

        /**
         * List News URL
         */
        public static final String URL_NEWS = URL_BASE + "/api/Meeting/Contacts";

        /**
         * List Upcoming meetings URL
         */
        public static final String URL_MEETING = URL_BASE + "/api/Meeting/Upcomming?latest=10";

        /**
         * Create Meetings URL
         */
        public static final String URL_CREATE_MEETING = URL_BASE + "/api/Meeting/Schedule";


        /**
         * List Checkin's URL
         */
        public static final String URL_CHECKINS = URL_BASE + "/api/Meeting/Contacts";

        //public static final String PARSE_APP_ID = "zHb2bVia6kgilYRWWdmTiEJooYA17NnkBSUVsr4H";
        //public static final String PARSE_REST_API_KEY = "N2kCY1T3t3Jfhf9zpJ5MCURn3b25UpACILhnf5u9";
        //public static final String HEADER_PARSE_REST_API_KEY = "X-Parse-REST-API-Key";
        //public static final String HEADER_PARSE_APP_ID = "X-Parse-Application-Id";
        public static final String CONTENT_TYPE_JSON = "application/json";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String SESSION_TOKEN = "sessionToken";


    }


    public static class Extra {
        private Extra() {
        }

        public static final String NEWS_ITEM = "news_item";

        public static final String MEETING = "meeting";

        public static final String USER = "user";

        public static final String CONTACTS_SELECTED = "contacts";

    }

    public static class Intent {
        private Intent() {
        }

        /**
         * Action prefix for all intents created
         */
        public static final String INTENT_PREFIX = "com.pxl.android.cemeo.";

    }


}


