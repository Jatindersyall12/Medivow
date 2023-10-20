package com.app.treatEasy.feature.scan_qr;

import static com.app.treatEasy.permissions.PermissionUtils.CAMERA;
import static com.app.treatEasy.permissions.PermissionUtils.VIBRATE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.feature.wallet.PaymentDetailActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;

public class ScanQRCodeActivity extends BaseActivity {

    private SurfaceView barcodeView;
    private BeepManager beepManager;
    private ScannerLiveView camera;
    private Button btnSubmit;
    private EditText et_amount;
    private LinearLayout doctorFee,llLabTest,llOpd,llAmount,llHospital;
    String checkTest="booking";
    String clientId;
    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() == null) {
                // Prevent duplicate scans
                return;
            }
            Log.d("Result", result.getText());

         //   barcodeView.setStatusText(result.getText());
            beepManager.playBeepSoundAndVibrate();
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);

        if (checkPermission()) {

        } else {
            requestPermission();
        }

        initView();


        doctorFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkTest="booking";
                llOpd.setBackgroundColor(Color.parseColor("#ffffff"));
                llLabTest.setBackgroundColor(Color.parseColor("#ffffff"));
                llHospital.setBackgroundColor(Color.parseColor("#ffffff"));
                doctorFee.setBackgroundColor(Color.parseColor("#ECEFF1"));


                llAmount.setVisibility(View.GONE);
                camera.setVisibility(View.VISIBLE);
            }
        });

        llLabTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkTest="labtest";
                doctorFee.setBackgroundColor(Color.parseColor("#ffffff"));
                llLabTest.setBackgroundColor(Color.parseColor("#ECEFF1"));
                llOpd.setBackgroundColor(Color.parseColor("#ffffff"));
                llHospital.setBackgroundColor(Color.parseColor("#ffffff"));

                llAmount.setVisibility(View.VISIBLE);
                camera.setVisibility(View.GONE);
            }
        });
        llHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkTest="hospital";
                doctorFee.setBackgroundColor(Color.parseColor("#ffffff"));
                llLabTest.setBackgroundColor(Color.parseColor("#ffffff"));
                llOpd.setBackgroundColor(Color.parseColor("#ffffff"));
                llHospital.setBackgroundColor(Color.parseColor("#ECEFF1"));

                llAmount.setVisibility(View.VISIBLE);
                camera.setVisibility(View.GONE);
            }
        });

        llOpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkTest="opd";
                doctorFee.setBackgroundColor(Color.parseColor("#ffffff"));
                llLabTest.setBackgroundColor(Color.parseColor("#ffffff"));
                llHospital.setBackgroundColor(Color.parseColor("#ffffff"));
                llOpd.setBackgroundColor(Color.parseColor("#ECEFF1"));

                llAmount.setVisibility(View.GONE);
                camera.setVisibility(View.VISIBLE);
            }
        });

        camera.setScannerViewEventListener(new ScannerLiveView.ScannerViewEventListener() {
            @Override
            public void onScannerStarted(ScannerLiveView scanner) {
                // method is called when scanner is started
               // Toast.makeText(ScanQRCodeActivity.this, "Scanner Started", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerStopped(ScannerLiveView scanner) {
                // method is called when scanner is stopped.
               // Toast.makeText(ScanQRCodeActivity.this, "Scanner Stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerError(Throwable err) {
                // method is called when scanner gives some error.
                Toast.makeText(ScanQRCodeActivity.this, "Scanner Error: " + err.getMessage(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCodeScanned(String data) {
                clientId=data;
                Log.d("scanId","scanId"+data);
              //  Toast.makeText(ScanQRCodeActivity.this, data, Toast.LENGTH_SHORT).show();
               // if (checkTest.equals("opd")){
                    Intent intent=new Intent(ScanQRCodeActivity.this, PaymentDetailActivity.class);
                    intent.putExtra("clientId",data);
                    startActivity(intent);
              //  }
              //  Log.d("scanId","scanId"+data);
            }
        });

      /*  btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (Double.parseDouble(et_amount.getText().toString())<=
                      Double.parseDouble(AppPreferences.getPreferenceInstance(ScanQRCodeActivity.this).getPayment())){

                   Intent intent=new Intent(ScanQRCodeActivity.this, PaymentDetailActivity.class);
                   intent.putExtra("clientId",clientId);
                   intent.putExtra("check",checkTest);
                   intent.putExtra("amount",et_amount.getText().toString());

                   startActivity(intent);
               }else {
                   Toast.makeText(ScanQRCodeActivity.this, "wallet value less than payment", Toast.LENGTH_SHORT).show();
               }
            }
        });*/
    }

    private void initView() {

        setUpToolBar(getString(R.string.scan_any_qr), true);

        camera =findViewById(R.id.camview);
        doctorFee =findViewById(R.id.doctorFee);
        llLabTest =findViewById(R.id.llLabTest);
        llOpd =findViewById(R.id.llOpd);
        llAmount =findViewById(R.id.llAmount);
        llHospital =findViewById(R.id.llHospital);
        btnSubmit =findViewById(R.id.btnSubmit);
        et_amount =findViewById(R.id.et_amount);

        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39);

        beepManager = new BeepManager(this);

    }



    @Override
    protected void onResume() {
        super.onResume();
        ZXDecoder decoder = new ZXDecoder();
        // 0.5 is the area where we have
        // to place red marker for scanning.
        decoder.setScanAreaPercent(0.8);
        // below method will set secoder to camera.
        camera.setDecoder(decoder);
        camera.startScanner();
    }
    @Override
    protected void onPause() {
        super.onPause();
      //  barcodeView.pause();
        camera.stopScanner();
        super.onPause();
    }
    public void pause(View view)
    {
       // barcodeView.pause();
    }
    public void resume(View view) {
      //  barcodeView.resume();
    }
    public void triggerScan(View view) {
     //   barcodeView.decodeSingle(callback);
    }
    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(getBaseContext(), intentResult.getContents(), Toast.LENGTH_SHORT).show();
                // if the intentResult is not null we'll set
                // the content and format of scan message
               // messageText.setText(intentResult.getContents());
               // messageFormat.setText(intentResult.getFormatName());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean checkPermission() {
        // here we are checking two permission that is vibrate
        // and camera which is granted by user and not.
        // if permission is granted then we are returning
        // true otherwise false.
        int camera_permission = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int vibrate_permission = ContextCompat.checkSelfPermission(getApplicationContext(), VIBRATE);
        return camera_permission == PackageManager.PERMISSION_GRANTED && vibrate_permission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // this method is to request
        // the runtime permission.
        int PERMISSION_REQUEST_CODE = 200;
        ActivityCompat.requestPermissions(this, new String[]{CAMERA, VIBRATE}, PERMISSION_REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // this method is called when user
        // allows the permission to use camera.
        if (grantResults.length > 0) {
            boolean cameraaccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean vibrateaccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            if (cameraaccepted && vibrateaccepted) {
                Toast.makeText(this, "Permission granted..", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denined \n You cannot use app without providing permission", Toast.LENGTH_SHORT).show();
            }
        }
    }
}