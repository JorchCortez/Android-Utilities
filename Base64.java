package com.example.jorch.svmovil.ModulosYHelpers;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class Base64
{
    public String encodeByteArray (String StoredPath)
    {
        String encoded;
        byte[] byteArray = filetoByteArray(StoredPath);
        encoded = android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT);
        //Log.e("ENCODE"," Cadena " + encoded);
        return encoded;
    }

    private byte[] filetoByteArray(String path) {
        byte[] data;
        try {
            InputStream input = new FileInputStream(path);
            int byteReads;
            ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
            while ((byteReads = input.read()) != -1) {
                output.write(byteReads);
            }

            data = output.toByteArray();
            output.close();
            input.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
