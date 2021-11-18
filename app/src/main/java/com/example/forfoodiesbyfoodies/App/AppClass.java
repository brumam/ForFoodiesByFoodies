package com.example.forfoodiesbyfoodies.App;

import com.example.forfoodiesbyfoodies.Helpers.User;

public class AppClass {
    public static class Session{
        public static User user;

        public static void logout(){
            user = null;

        }
    }
}
