package com.marcelo.book_store_jsf.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Hashing {
    public static String hash(String input) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(input);
    }

    public static boolean matches(String input, String hashed) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(input, hashed);
    }
}
