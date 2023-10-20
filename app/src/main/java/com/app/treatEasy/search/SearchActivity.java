package com.app.treatEasy.search;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.app.treatEasy.BaseAppApplication;
import com.app.treatEasy.R;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.listeners.ItemClickListener;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.state.RetrofitClient;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends BaseActivity implements ItemClickListener {

    Spinner spType;
    ArrayList<String>arrayType;
    List<SerachResponse.Datum>searchData;
    List<SerachResponse.Datum>searchDataPackages;
    List<SearchDoctorRes.Datum>searchDatadoctor;
    List<SearchResModel.Datum>searchDataPackage;
    List<SearchHospitalRes.Datum>searchDataHospital;
    //List<SerachResponse.Datum>searchDataHospital;
    List<SerachResponse.Datum>searchDataService;
    String type="",cityId;
    private EditText et_search;
    private  RecyclerView mRecycler,recycler_viewservice,recycler_viewDoctor,recycler_viewHospital;
    SearchAdapter mAdapter;
    TextView tvDataNotFound;
    ImageView imgSearch;
    List<SearchDoctorRes.Datum>doctorSearch;
    LinearLayout llPackages,llServices,llDoctors,llHospitals;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);
        BaseAppApplication.getApp().getDaggerAppComponent().provideIn(this);
        initView();

        arrayType=new ArrayList<>();
        doctorSearch=new ArrayList<>();
        searchDataPackage=new ArrayList<>();

        addList();

        type=getIntent().getStringExtra("type");
        cityId=getIntent().getStringExtra("cityId");

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,arrayType);
        spType.setAdapter(aa);

        et_search=findViewById(R.id.et_search);
        mRecycler=findViewById(R.id.recycler_view);
        tvDataNotFound=findViewById(R.id.tvDataNotFound);

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_search.getText().toString().equals("")){
                    if (type.equals("Doctor")){
                        getDoctorList(et_search.getText().toString());
                    }else if (type.equals("Hospital")){
                        getHospitalList(et_search.getText().toString());
                    }else if (type.equals("Packages")){
                        getPackageList(et_search.getText().toString());
                    }
                }else {
                    et_search.setError("Please Enter Text First");
                }
            }
        });

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!et_search.getText().toString().equals("")){
                        getDoctorList(et_search.getText().toString());
                    }else {
                        et_search.setError("Please Enter Text First");
                    }
                    return true;
                }
                return false;
            }
        });
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (arrayType.get(i).equals("Package")){
                    type="1";
                }else if (arrayType.get(i).equals("Doctors")){
                    type="2";
                }else if (arrayType.get(i).equals("Hospitals")){
                    type="3";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void initView() {
        setUpToolBar("Search", true);
        spType=findViewById(R.id.spType);
        imgSearch=findViewById(R.id.imgSearch);
        llPackages=findViewById(R.id.llPackages);
        llServices=findViewById(R.id.llServices);
        llDoctors=findViewById(R.id.llDoctors);
        llHospitals=findViewById(R.id.llHospitals);
        recycler_viewservice=findViewById(R.id.recycler_viewservice);
        recycler_viewDoctor=findViewById(R.id.recycler_viewDoctor);
        recycler_viewHospital=findViewById(R.id.recycler_viewHospital);
    }
    @Override
    public void OnItemClick(View view, int position) {

        if (searchDataPackages.size()>0){
            Bundle bundle = new Bundle();
            bundle.putString(PACKAGE_ID,searchData.get(position).getId());
            bundle.putString(PACKAGE_NAME, searchData.get(position).getDisplayName());
            //  switchActivity(DoctorsByCategoryActivity.class, bundle);   // call after hospital list
           /*else if (searchData.get(position).getIsDoctor().equals("1")){
                Intent intent=new Intent(SearchActivity.this, DoctorDeatil.class);
                intent.putExtra("id",searchData.get(position).getId());
                startActivity(intent);
            }else if (searchData.get(position).getIsHospital().equals("1")){
                Intent intent=new Intent(SearchActivity.this, DescriptionActivity.class);
                intent.putExtra("id",searchData.get(position).getId());
                intent.putExtra("title",searchData.get(position).getDisplayName());
                startActivity(intent);
            }else if (searchData.get(position).getIsService().equals("1")){
                Intent intent=new Intent(SearchActivity.this, ServiceDetailActivity.class);
                intent.putExtra("id",searchData.get(position).getId());
                intent.putExtra("title",searchData.get(position).getDisplayName());
                startActivity(intent);
            }*/
        }
    }
    public void addList(){
        arrayType.add("Type");
        arrayType.add("Package");
        arrayType.add("Doctors");
        arrayType.add("Hospitals");
    }
    public void getDoctorList(String filter){
      showProgressDialog();

        searchDatadoctor=new ArrayList<>();

        // List<DoctorResponseModel.Datum> doctorListlist = new ArrayList<>();
        Log.d("userID", AppPreferences.getPreferenceInstance(this).getUserId());
        Call<SearchDoctorRes> call = RetrofitClient.getInstance().getMyApi().getSearchForDoctor(AppPreferences.getPreferenceInstance(this).getUserId(),
                filter,cityId,"1");
        call.enqueue(new Callback<SearchDoctorRes>() {
            @Override
            public void onResponse(Call<SearchDoctorRes> call, Response<SearchDoctorRes> response) {
                dismissProgressDialog();
                if (response.body().getStatusCode()==200){
                    searchDatadoctor=response.body().getData();
                    if (searchDatadoctor.size()>0){
                        mRecycler.setLayoutManager(new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL,false));
                        SearchAdapter   mAdapter = new SearchAdapter(SearchActivity.this, SearchActivity.this,searchDatadoctor);
                        mRecycler.setAdapter(mAdapter);
                    }else {
                        Toast.makeText(SearchActivity.this, "No data found !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<SearchDoctorRes> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(SearchActivity.this, "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getHospitalList(String filter){
        showProgressDialog();

        searchDataHospital=new ArrayList<>();

        // List<DoctorResponseModel.Datum> doctorListlist = new ArrayList<>();
        Log.d("userID", AppPreferences.getPreferenceInstance(this).getUserId());
        Call<SearchHospitalRes> call = RetrofitClient.getInstance().getMyApi().getSearchForHospital(AppPreferences.getPreferenceInstance(this).getUserId(),
                filter,cityId,"2");
        call.enqueue(new Callback<SearchHospitalRes>() {
            @Override
            public void onResponse(Call<SearchHospitalRes> call, Response<SearchHospitalRes> response) {
                dismissProgressDialog();
                if (response.body().getStatusCode()==200){
                    searchDataHospital=response.body().getData();
                    if (searchDataHospital.size()>0){
                        mRecycler.setLayoutManager(new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL,false));
                        SearchHospitalAdapter   mAdapter = new SearchHospitalAdapter(SearchActivity.this, searchDataHospital);
                        mRecycler.setAdapter(mAdapter);
                    }else {
                        Toast.makeText(SearchActivity.this, "No data found !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<SearchHospitalRes> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(SearchActivity.this, "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getPackageList(String filter){
        showProgressDialog();

        searchDataPackage=new ArrayList<>();

        // List<DoctorResponseModel.Datum> doctorListlist = new ArrayList<>();
        Log.d("userID", AppPreferences.getPreferenceInstance(this).getUserId());
        Call<SearchResModel> call = RetrofitClient.getInstance().getMyApi().getSearchForPackage(AppPreferences.getPreferenceInstance(this).getUserId(),
                filter,cityId,"3");
        call.enqueue(new Callback<SearchResModel>() {
            @Override
            public void onResponse(Call<SearchResModel> call, Response<SearchResModel> response) {
                dismissProgressDialog();
                if (response.body().getStatusCode()==200){
                    searchDataPackage=response.body().getData();
                    if (searchDataPackage.size()>0){
                        mRecycler.setLayoutManager(new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL,false));
                        SearchPackageAdapter   mAdapter = new SearchPackageAdapter(SearchActivity.this, searchDataPackage);
                        mRecycler.setAdapter(mAdapter);
                    }else {
                        Toast.makeText(SearchActivity.this, "No data found !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<SearchResModel> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(SearchActivity.this, "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

   /* public void AdapterSet(List<SerachResponse.Datum>list){

        mRecycler.setLayoutManager(new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL,false));
        SearchAdapter   mAdapter = new SearchAdapter(SearchActivity.this, this,list);
        //  mRecycler.setAdapter(mAdapter);
        mRecycler.setAdapter(mAdapter);
    }*/
}