package com.example.jorch.svmovil.ModulosYHelpers;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import com.google.api.client.util.Base64;

import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AESCrypt
{
    public static String initVector;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encrypt(String key, String value)
    {
        try
        {
            initVector = generateIv();
            IvParameterSpec iv = new IvParameterSpec(Base64.decodeBase64(initVector));
            SecretKeySpec skeySpec = new SecretKeySpec(Base64.decodeBase64(key), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeBase64String(encrypted);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.e("Error!", "error: " + ex);
        }
        return null;
    }

    public static String decrypt(String key, String encrypted, String vector)
    {
        try
        {
            IvParameterSpec iv = new IvParameterSpec(Base64.decodeBase64(vector));
            SecretKeySpec skeySpec = new SecretKeySpec(Base64.decodeBase64(key), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
            return new String(original);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.e("Error!", "error: " + ex);
        }
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String generateIv()
    {
        SecureRandom random = new SecureRandom();
        byte[] ivBytes = new byte[16];
        random.nextBytes(ivBytes);
        String b64bites = Base64.encodeBase64String(ivBytes);

        Log.e("IV enc",new String(b64bites));
        Log.e("IV dec", Arrays.toString(Base64.decodeBase64(b64bites)));
        Log.e("IV not b64", Arrays.toString(ivBytes));
        return new String(b64bites);
    }

}