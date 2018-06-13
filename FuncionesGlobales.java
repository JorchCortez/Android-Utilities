package com.example.jorch.svmovil.ModulosYHelpers;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Spinner;

import com.example.jorch.svmovil.Entrega_Boletos;
import com.example.jorch.svmovil.MyApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.support.v4.app.ActivityCompat.requestPermissions;

/**
 * Created by infraestructurasorteostec on 14/03/18.
 */

public class FuncionesGlobales {
    public static int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            // Log exception.
            return 0;
        }
    }

    public static void EnviarMensaje(String mje, String titulo, Context CXT, ProgressDialog pd) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CXT);
        String texto = mje;
        builder.setMessage(texto)
                .setTitle(titulo)
                .setCancelable(false)
                .setPositiveButton("Aceptar", (dialog, id) -> {
                    if (pd != null && pd.isShowing() && pd.isIndeterminate())
                        pd.dismiss();
                });
        android.app.AlertDialog alert = builder.create();
        if (!alert.isShowing()) {
            alert.show();
        }
    }

    public static String generarInicioTituloPAOPDA(String prefijo, String idPlan) {
        String tituloFinal = "";
        String idPlanCeros = "";
        for (int i = idPlan.length(); i < 10; i++) {
            idPlanCeros = idPlanCeros + "0";
        }
        tituloFinal = prefijo + idPlanCeros;
        return tituloFinal;
    }

    //--- Metodo para seleccionar la opcion de los spinners
    public static int ObtenerDatoPorValor(Spinner spinner, String myString) {
        try {
            int index = 0;
            Log.e("string ", "valor: " + myString);
            for (int i = 0; i < spinner.getCount(); i++) {
                if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                    Log.e("string ", "actual: " + i);
                    index = i;
                    break;
                }
            }
            return index;
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean RevisarConexion(ConnectivityManager cm) throws ParseException {
        //---- Validar conexion a internet
        if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else {
            return false;
        }
    }

    public static void GenerarLogError(String ErrorLog, String Metodo) {
        String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath() + "/Error_De_Servicio/";
        File directory = new File(dir);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
        Date date = new Date();
        String filename = "ServiceError_" + Metodo + dateFormat.format(date) + ".txt";
        File file = new File(dir, filename);

        if (!directory.exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter fileWriter = new FileWriter(file)) {
            String finalErrorLog = "Metodo: " + Metodo + "\n\n" + ErrorLog;
            fileWriter.append(finalErrorLog);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            Log.e("ErrorLog", e.getMessage());
        }
    }

    public static void GuardarExceptionLog(String descripcion, String Metodo, String Clase) {
        String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath() + "/Error_De_Aplicacion/";
        File directory = new File(dir);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM-HH_mm");
        Date date = new Date();
        String filename = "LogExcepcion_" + Clase + dateFormat.format(date) + ".txt";
        File file = new File(dir, filename);

        if (!directory.exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter fileWriter = new FileWriter(file)) {
            String finalErrorLog = "Clase: " + Clase + "\n\nMetodo: " + Metodo + "\n\nDescripcion:\n" + descripcion;
            fileWriter.append(finalErrorLog);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            Log.e("ErrorLog", e.getMessage());
        }
    }

    public static String ObtenerIMEI() {
        String imei = "";
        imei = Settings.Secure.getString(MyApplication.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        return imei;
    }
}
