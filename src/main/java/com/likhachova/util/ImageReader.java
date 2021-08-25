package com.likhachova.util;

import java.io.*;
import java.util.Base64;

public class ImageReader {

    public static String getImage(String path){
        InputStream inputStream = getFileFromResourceAsStream(path);
        String imgAsBase64 = null;
        byte[] imgBytes = new byte[0];
        try {
            imgBytes = readFromInputStream(inputStream);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        String encodedBytes = Base64.getEncoder().withoutPadding().encodeToString(imgBytes);
        imgAsBase64 = "data:image/png;base64," + encodedBytes;

        return imgAsBase64;
    }

    private static byte[] readFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[8096];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }

    // works for jar
    private static InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = ImageReader.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("File was not found! " + fileName);
        }
        return inputStream;
    }
}
