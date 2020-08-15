package com.neversonsilva.cursomc.services.validation.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

    public static List<Integer> decodeIntList(String value) {
        return Arrays.asList(value.split(",")).stream().map((String v) -> Integer.parseInt(v))
                .collect(Collectors.toList());
    }

    public static String decodeParam(String value) {
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}