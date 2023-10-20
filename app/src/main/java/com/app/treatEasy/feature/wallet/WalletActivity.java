package com.app.treatEasy.feature.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.app.treatEasy.BaseAppApplication;
import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.baseui.BaseViewModelFactory;
import com.app.treatEasy.feature.add_money.UpdateWalletRes;
import com.app.treatEasy.networking.Resource;
import com.app.treatEasy.payment.CheckSumRes;
import com.app.treatEasy.payment.PaymentRes;
import com.app.treatEasy.payment.RechargeReqRes;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;
import com.google.gson.Gson;
import com.paytm.pgsdk.Log;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Set;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletActivity extends BaseActivity implements PaytmPaymentTransactionCallback {

    @Inject
    public BaseViewModelFactory mViewModelFactory;
    private WalletViewModel mViewModel;
    private int totalAmount;
    private TextView tvTotalAmount;
    private EditText etAmount;
    private Button btnAddAmount;
    int richargeId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);
       // PhonePeSdk.init(context, merchantId, apiKey);
      //  mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(WalletViewModel.class);
      //  observeData();
        initView();
       // PhonePe.init(this);
    }
    private void observeData() {
        mViewModel.getAddMoneyToWalletData().observe(this, this::observeAddMoneyData);
    }

    private void initView() {
        setUpToolBar(getString(R.string.add_money), true);

       // ((TextView) findViewById(R.id.txt_next)).setText(getString(R.string.proceed));

        tvTotalAmount = findViewById(R.id.tv_total_amount);
        btnAddAmount = findViewById(R.id.btnAddAmount);
        etAmount = findViewById(R.id.et_amount);

        btnAddAmount.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {
                                                              if (!etAmount.getText().toString().equals("")){
                                                                  createRicharge(AppPreferences.getPreferenceInstance(WalletActivity.this).getUserId(),
                                                                          etAmount.getText().toString());
                                                              }else {
                                                                  Toast.makeText(WalletActivity.this, "Add Money first", Toast.LENGTH_SHORT).show();
                                                              }
                                                          }
                                                      });



      //  totalAmount = Integer.parseInt(parseUserData().walletAmount);
      //  tvTotalAmount.setText(parseUserData().walletAmount);
    }

    private void observeAddMoneyData(Resource<WalletModel> walletModelResource) {
        switch (walletModelResource.mStatus) {
            case LOADING:
                showProgressDialog();
                break;

            case SUCCESS:
                dismissProgressDialog();
                totalAmount = totalAmount + Integer.parseInt(etAmount.getText().toString());
                updateUserData(TOTAL_AMOUNT, String.valueOf(totalAmount));
                tvTotalAmount.setText(String.valueOf(totalAmount));
                etAmount.setText(null);
                break;

            case ERROR:
                dismissProgressDialog();
                showAlertMessageDialog(getString(R.string.failure), getString(R.string.something_went_wrong),
                        null, getString(R.string.tv_ok), null, null);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
           /* WalletModel.PostAddMoneyToWallet model = new WalletModel.PostAddMoneyToWallet();
            model.userid = parseUserData().uid;
            model.amount = etAmount.getText().toString();
            model.trxn_id = "txtId_" + System.currentTimeMillis();
            mViewModel.addMoneyToWallet(model);*/
        }
    }

    public void createRicharge(String userId,String amount){

        showProgressDialog();

        Call<RechargeReqRes> call = RetrofitClient.getInstance().getMyApi().createRicharge(userId,amount);
        call.enqueue(new Callback<RechargeReqRes>(){
            @Override
            public void onResponse(Call<RechargeReqRes> call, Response<RechargeReqRes> response) {
                dismissProgressDialog();
                if (response.body().getStatusCode()==200){
                    richargeId=response.body().getData().getRechargeId();
                    Log.d("richargeId","richargeId"+richargeId);
                    createCheckSum(AppPreferences.getPreferenceInstance(WalletActivity.this).getUserId(),

                            AppPreferences.getPreferenceInstance(WalletActivity.this).getMobile(),
                            String.valueOf(response.body().getData().getRechargeId()) ,
                            etAmount.getText().toString());

                   // Toast.makeText(WalletActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RechargeReqRes> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void createCheckSum(String userId,String mobile,String richargeID,String amount){
        showProgressDialog();

        Call<CheckSumRes> call = RetrofitClient.getInstance().getMyApi().checkSum(userId,mobile,
                richargeID,amount);

        Log.d("userId","userId"+userId);
        Log.d("mobilenumber","mobilenumber"+mobile);
        Log.d("richargeID","richargeID"+richargeID);
        Log.d("amountFinal","amountFinal"+amount);

        call.enqueue(new Callback<CheckSumRes>(){
            @Override
            public void onResponse(Call<CheckSumRes> call, Response<CheckSumRes> response) {
                dismissProgressDialog();
                if (response.code()==200){

                    getPayment(response.body().getChecksum(),
                            response.body().getParams().getMid(),
                            response.body().getParams().getOrderId(),
                            response.body().getParams().getCustId(),
                            response.body().getParams().getIndustryTypeId(),
                            response.body().getParams().getChannelId(),
                            response.body().getParams().getTxnAmount(),
                            response.body().getParams().getWebsite(),
                            response.body().getParams().getCallbackUrl(),
                            response.body().getParams().getEmail(),
                            response.body().getParams().getMobileNo());
                            //Toast.makeText(WalletActivity.this, "msggggg", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CheckSumRes> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getPayment(String checksum,String MID,String ORDER_ID,String CUST_ID,String INDUSTRY_TYPE_ID,
                           String CHANNEL_ID,String TXN_AMOUNT,String WEBSITE,String CALLBACK_URL,String email,
                           String mobile){

        PaytmPGService paytmPGService = PaytmPGService.getStagingService();
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("CHECKSUMHASH",checksum);
        paramMap.put("MID", MID);
        paramMap.put("ORDER_ID", ORDER_ID);
        paramMap.put("CUST_ID", CUST_ID);
        paramMap.put("INDUSTRY_TYPE_ID", INDUSTRY_TYPE_ID);
        paramMap.put("CHANNEL_ID", CHANNEL_ID);
        paramMap.put("TXN_AMOUNT", TXN_AMOUNT);
        paramMap.put("MOBILE_NO",mobile);
        paramMap.put("EMAIL",email);
        paramMap.put("WEBSITE", WEBSITE);
        paramMap.put("CALLBACK_URL", CALLBACK_URL);

        PaytmOrder Order = new PaytmOrder(paramMap);

        paytmPGService.initialize(Order, null);

        paytmPGService.startPaymentTransaction(WalletActivity.this, true, true,
                new PaytmPaymentTransactionCallback() {
            @Override
            public void onTransactionResponse(Bundle inResponse) {
                String s = new Gson().toJson(inResponse.toString());
                Log.d("TAGSYALL1", "networkNotAvailable:"+inResponse.toString());

                JSONObject json = new JSONObject();
                Set<String> keys = inResponse.keySet();
                for (String key : keys) {
                    try {

                        json.put(key, JSONObject.wrap(inResponse.get(key)));
                    } catch (JSONException e) {

                    }
                }

                try {
                    String status = json.getString("STATUS");
                    String TXNID = json.getString("TXNID");
                    String BANKNAME = json.getString("BANKNAME");
                    String ORDERID = json.getString("ORDERID");
                    String TXNAMOUNT = json.getString("TXNAMOUNT");
                    String RESPCODE = json.getString("RESPCODE");

                    android.util.Log.d("status", status);

                    updateWallet(AppPreferences.getPreferenceInstance(WalletActivity.this).getUserId(),String.valueOf(richargeId),
                            TXNID, TXNAMOUNT);
                    if (status == "TXN_FAILURE") {
                        String msg = json.getString("RESPMSG");
                        Toast.makeText(WalletActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void networkNotAvailable(){
                Log.d("TAGSYALL12", "networkNotAvailable: ");
            }

            @Override
            public void clientAuthenticationFailed(String inErrorMessage) {
                Log.d("TAGSYALL", "clientAuthenticationFailed: ");
            }

            @Override
            public void someUIErrorOccurred(String inErrorMessage) {
                Log.d("TAGSYALL", "someUIErrorOccurred: ");
            }

            @Override
            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                Log.d("TAGSYALL", "onErrorLoadingWebPage: ");
            }

            @Override
            public void onBackPressedCancelTransaction() {
                Log.d("TAGSYALL", "onBackPressedCancelTransaction: ");
            }

            @Override
            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                Log.d("TAGSYALL", "onTransactionCancel: ");
            }
        });
    }
    @Override
    public void onTransactionResponse(Bundle inResponse) {

    }

    @Override
    public void networkNotAvailable() {

    }

    @Override
    public void clientAuthenticationFailed(String inErrorMessage) {

    }

    @Override
    public void someUIErrorOccurred(String inErrorMessage) {

    }
    @Override
    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl){

    }
    @Override
    public void onBackPressedCancelTransaction() {
    }
    @Override
    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
    }

    public void updateWallet(String userId,String richargeID,String txnAmount,String amount){
        showProgressDialog();

        Call<UpdateWalletRes> call = RetrofitClient.getInstance().getMyApi().updateWallet(userId,
                richargeID,txnAmount);

        Log.d("userId","userId"+userId);

        call.enqueue(new Callback<UpdateWalletRes>(){
            @Override
            public void onResponse(Call<UpdateWalletRes> call, Response<UpdateWalletRes> response) {
                dismissProgressDialog();
                if (response.code()==200){

                    Intent intent=new Intent(WalletActivity.this,SuccessActivity.class);
                    intent.putExtra("amount",amount);
                    startActivity(intent);
                    finish();
                   // switchActivity(SuccessActivity.class);
                    // getPayment(AppPreferences.getPreferenceInstance(WalletActivity.this).getUserId());
                   //tvTotalAmount.setText(amount);
                    //Toast.makeText(WalletActivity.this, "Wallet Updated Successfully", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UpdateWalletRes> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getPayment(String userId){
        showProgressDialog();

        Call<PaymentRes> call = RetrofitClient.getInstance().getMyApi().getPayment(userId);
        call.enqueue(new Callback<PaymentRes>(){
            @Override
            public void onResponse(Call<PaymentRes> call, Response<PaymentRes> response) {
                dismissProgressDialog();
                if (response.body().getStatusCode()==200){
                    //richargeId=response.body().getData();
                    Log.d("richargeId","richargeId"+richargeId);
                    Log.d("payment","payment"+response.body().getData());
                    AppPreferences.getPreferenceInstance(WalletActivity.this).setPayment(response.body().getData());
                    tvTotalAmount.setText(response.body().getData());
                    //Toast.makeText(WalletActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<PaymentRes> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPayment(AppPreferences.getPreferenceInstance(WalletActivity.this).getUserId());
    }
}