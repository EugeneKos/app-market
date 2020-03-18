package ru.market.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class JSONObjectUtil {
    private JSONObjectUtil(){}

    public static <T> T getJsonObjectFromInputStream(InputStream inputStream, Class<T> type){
        InputStream copied = copy(inputStream);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(copied, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static InputStream copy(InputStream inputStream){
        //fixme: При копировании stream остается пустой и контроллер не может дессерелизовать объект обратно ПРОБЛЕМА!!!
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[255];
            while (inputStream.read(buffer) != -1){
                byteArrayOutputStream.write(buffer);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
}
