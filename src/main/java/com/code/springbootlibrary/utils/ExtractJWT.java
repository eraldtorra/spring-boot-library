package com.code.springbootlibrary.utils;


import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ExtractJWT {

    public static String payloadJWTExtraction(String token,String extraction) {

        token.replace("Bearer ", "");
        String[] split_string = token.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();

        String payload = new String(decoder.decode(split_string[1]));

        String[] split_payload = payload.split(",");

        Map<String,String > map = new HashMap<>();

        for (String entry: split_payload) {
            String[] split_entry = entry.split(":");
            if (split_entry[0].equals(extraction)) {

                int remove = 1;
                if (split_entry[1].endsWith("}")) {
                    remove = 2;
                }
                split_entry[1] = split_entry[1].substring(0, split_entry[1].length() - remove);
                split_entry[1] = split_entry[1].substring(1);

                map.put(split_entry[0], split_entry[1]);
            }
        }

        if(map.containsKey(extraction)) {
            return map.get(extraction);
        }

        return null;

    }
}
