public class FuncionesGlobales
{
    public static int tryParseInt(String value)
    {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            // Log exception.
            return 0;
        }
    }

    public static Double tryParseDouble(String value)
    {
        try
        {
            return Double.valueOf(value);
        }
        catch(NumberFormatException nfe) {
            return 0.0;
        }
    }

    public static boolean isPrimitive(String value)
    {
        boolean status=true;
        if(value.length()<1)
            return false;
        for(int i = 0;i<value.length();i++){
            char c=value.charAt(i);
            if(Character.isDigit(c) || c=='.'){

            }else{
                status=false;
                break;
            }
        }
        return status;
    }

    public static void CreateMessageDialog(String mje, String titulo, Context CXT, ProgressDialog pd) {
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
 
    public static int SelectByValue(Spinner spinner, String myString) {
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

    public static boolean CheckConnection(ConnectivityManager cm) throws ParseException {
        //---- Validar conexion a internet
        if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else {
            return false;
        }
    }

    public static void GenerateErrorLog(String ErrorLog, String Metodo) {
        String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath() + "/Error_Log/";
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
            String finalErrorLog = "Method: " + Metodo + "\n\n" + ErrorLog;
            fileWriter.append(finalErrorLog);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            Log.e("ErrorLog", e.getMessage());
        }
    }

    public static void GenerateExceptionLog(String descripcion, String Metodo, String Clase) {
        String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath() + "/ApplicationError/";
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

    public static String GetIMEI() {
        String imei = "";
        imei = Settings.Secure.getString(MyApplication.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        return imei;
    }

    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    public static String Normalize(String texto) {
        String convertedString = Normalizer
                .normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");//.replaceAll("&(?!amp;)", "&amp;");
        String convertedFinal = StringEscapeUtils.escapeHtml4(convertedString);
        return convertedFinal;
    }

    public static void PrintLongString(String veryLongString)
    {
        int maxLogSize = 1000;
        for(int i = 0; i <= veryLongString.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > veryLongString.length() ? veryLongString.length() : end;
            Log.v("next", " " + veryLongString.substring(start, end));
        }
    }

    public static String ConvertToValidDate(String fecha)
    {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aaa",Locale.US);
        String fechaValida = "";
        try {
            fechaValida = TextUtils.isEmpty(fecha) ? formatoFecha.format(new Date()) : formatoFecha.parse(new Date(fecha).toString()).toString();
        } catch (Exception e) {
            return formatoFecha.format(new Date());
        }
        fecha = fechaValida    ;

        return fecha;
    }

    public static void hideSoftKeyboard(Activity activity)
    {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static String DeleteLastCharacter(String str)
    {
        return str.substring(0, str.length() - 1);
    }

    public static String AddZeroLeft(int numero)
    {
        String revisarValor;
        try {
             revisarValor = (numero <= 9) ? "0" + numero : String.valueOf(numero);
        }
        catch (Exception e)
        {
            return "00";
        }
        return revisarValor;
    }

    public static void DisableEditText(EditText editText)
    {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
    }

    public static boolean between(int i, int minValueInclusive, int maxValueInclusive) {
        return (i >= minValueInclusive && i <= maxValueInclusive);
    }

        public static boolean esAplicacionInstalada(String packageName, PackageManager packageManager) {

        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }
}
