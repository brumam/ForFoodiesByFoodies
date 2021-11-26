package com.example.forfoodiesbyfoodies.App;

import com.example.forfoodiesbyfoodies.Helpers.User;

public class AppClass {
//    Public User Helper class that I called in the Log in Activity
    public static class Session{
        public static User user;

        public static void logout(){
            user = null;

        }
    }
}
