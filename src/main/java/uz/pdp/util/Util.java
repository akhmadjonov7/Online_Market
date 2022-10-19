package uz.pdp.util;

import com.auth0.jwt.algorithms.Algorithm;

public interface Util {
    String UPLOAD_DIRECTORY = "C:/Users/Akhmadjonov.7/Desktop/upload/";

    Algorithm algorithm = Algorithm.HMAC256("DOTA");
}
