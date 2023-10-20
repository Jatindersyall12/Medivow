package com.app.treatEasy.apputils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.app.treatEasy.R;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.app.treatEasy.apputils.AppConstants.PASSWORD_8_DIGIT;
import static com.app.treatEasy.apputils.AppConstants.PASSWORD_HAVE_1_NUMERIC;
import static com.app.treatEasy.apputils.AppConstants.PASSWORD_HAVE_CAPITAL_NUMBER;
import static com.app.treatEasy.apputils.AppConstants.PASSWORD_HAVE_SMALL_NUMBER;
import static com.app.treatEasy.apputils.AppConstants.PASSWORD_HAVE_USERNAME;

public class BaseUtils {

    public static boolean isKeyboardOpen = false;

    /**
     * This method will return true if network connection is available
     *
     * @param context
     * @return
     */
    public static boolean checkNetwork(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    /**
     * Method to open soft keyboard
     *
     * @param activity
     */
    public static void hideKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static boolean validatePassword(String email, String password) {
        if (TextUtils.isEmpty(password)) {
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            return false;
        }

        if (password.contains(email.split("@")[0]))
            return false;

        String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        /**
         * ^                 # start-of-string
         * (?=.*[0-9])       # a digit must occur at least once
         * (?=.*[a-z])       # a lower case letter must occur at least once
         * (?=.*[A-Z])       # an upper case letter must occur at least once
         * (?=.*[@#$%^&+=])  # a special character must occur at least once
         * (?=\S+$)          # no whitespace allowed in the entire string
         * .{8,}             # anything, at least eight places though
         * $                 # end-of-string
         */
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);

        if (matcher.matches()) {
//            This will match for repeated characters
            Pattern pattern1 = Pattern.compile("^.*([0-9A-Za-z])\\1\\1+.*$");
            Matcher matcher1 = pattern1.matcher(password);
            if (matcher1.matches()) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


    /**
     * Method to validate password
     *
     * @param email
     * @param password
     * @return
     */
    public static HashMap<String, Boolean> passwordError(String email, String password) {
        HashMap<String, Boolean> passwordErrorMap = new HashMap<>();

        passwordErrorMap.put(PASSWORD_8_DIGIT, false);                                        //Check password length is 8 digit
        passwordErrorMap.put(PASSWORD_HAVE_CAPITAL_NUMBER, false); // Check password have minimum 1 capital character
        passwordErrorMap.put(PASSWORD_HAVE_SMALL_NUMBER, false);  // Check password have minimum 1 small character
        passwordErrorMap.put(PASSWORD_HAVE_1_NUMERIC, false);
        passwordErrorMap.put(PASSWORD_HAVE_USERNAME, false);

        /**
         * Use a HashMap to validate password with String key and boolean value.
         * If a condition is true then that particular value will be true otherwise its false
         * */
        if (!TextUtils.isEmpty(password)) {
            passwordErrorMap.put(PASSWORD_8_DIGIT, passwordlength(password));                                        //Check password length is 8 digit
            passwordErrorMap.put(PASSWORD_HAVE_CAPITAL_NUMBER, countUpperCaseLower(password, true) > 0); // Check password have minimum 1 capital character
            passwordErrorMap.put(PASSWORD_HAVE_SMALL_NUMBER, countUpperCaseLower(password, false) > 0);  // Check password have minimum 1 small character
            passwordErrorMap.put(PASSWORD_HAVE_1_NUMERIC, containsDigit(password));                                 // Check password have minimum 1 numeric character

            if (!TextUtils.isEmpty(email))
                passwordErrorMap.put(PASSWORD_HAVE_USERNAME, password.contains(email.split("@")[0]));


            String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
            /**
             * ^                 # start-of-string
             * (?=.*[0-9])       # a digit must occur at least once
             * (?=.*[a-z])       # a lower case letter must occur at least once
             * (?=.*[A-Z])       # an upper case letter must occur at least once
             * (?=.*[@#$%^&+=])  # a special character must occur at least once
             * (?=\S+$)          # no whitespace allowed in the entire string
             * .{8,}             # anything, at least eight places though
             * $                 # end-of-string
             */

            Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
            Matcher matcher = pattern.matcher(password);

            if (matcher.matches()) {
//            This will match for repeated characters
                Pattern pattern1 = Pattern.compile("^.*([0-9A-Za-z])\\1\\1+.*$");
                Matcher matcher1 = pattern1.matcher(password);
                    /*if (matcher1.matches()) {
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    return false;*/
            }
        }

        return passwordErrorMap;
    }


    /**
     * Check password have both lowercase and uppercase
     *
     * @param password
     * @param isUpperCase used for get uppercase or lowercase
     */
    private static Integer countUpperCaseLower(String password, boolean isUpperCase) {
        int uppercase = 0, lowercase = 0, count = 0;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i))) {
                lowercase++;
                break;
            } else if (Character.isUpperCase(password.charAt(i))) {
                uppercase++;
                break;
            }
        }

        if (isUpperCase)
            count = uppercase;
        else
            count = lowercase;


        return count;
    }


    /**
     * Check password have numeric character
     *
     * @param s
     */
    private static boolean containsDigit(String s) {
        boolean containsDigit = false;

        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (containsDigit = Character.isDigit(c)) {
                    break;
                }
            }
        }

        return containsDigit;
    }

    /**
     * Check password have minimum 8 character
     *
     * @param s
     */
    public static boolean passwordlength(String s) {
        boolean is8Digit = false;

        if (s.length() >= 8) {
            return true;
        }
        return is8Digit;
    }

    /**
     * This method is used to make a phone call.
     *
     * @param number
     */
    public static void makeCall(Context context, String number) {

        PackageManager pm = context.getPackageManager();
        //pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY);

        if (pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY)) {
            //calling functionality
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + number));
            context.startActivity(callIntent);

        } else {
            // no calling feature
            Log.v("Call Error", "Device does not support calling feature.");

        }

    }


    public static LatLng getLatLongFromAddress(Context context, String address) {
        LatLng destLatLang = null;
        Geocoder coder = new Geocoder(context);
        try {
            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(address, 1);
            for (Address add : adresses) {
                destLatLang = new LatLng(add.getLatitude(), add.getLongitude());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return destLatLang;
    }


    public static String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");

        return df.format(c);
    }

    /**
     * Set Image in imageView
     *
     * @param imageUrl
     * @param imageView
     */
    public static void setImage(String imageUrl, ImageView imageView) {

        imageUrl = TextUtils.isEmpty(imageUrl) ? null : imageUrl;

        Picasso.get()
                .load(imageUrl)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                });
    }

    /**
     * Set Image in imageView
     *
     * @param imageUrl
     * @param imageView
     */
    public static void setImage(String imageUrl, ImageView imageView, int placeholder) {

        imageUrl = TextUtils.isEmpty(imageUrl) ? null : imageUrl;

        Picasso.get()
                .load(imageUrl)
                .placeholder(placeholder)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                });
    }
}