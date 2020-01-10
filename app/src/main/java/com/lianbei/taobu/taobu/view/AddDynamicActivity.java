package com.lianbei.taobu.taobu.view;


import android.Manifest;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.lianbei.taobu.MainActivity;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.taobu.adapter.PhotoPickerGridAdapter;
import com.lianbei.taobu.taobu.viewmanager.FileUploadManager;
import com.lianbei.taobu.taobu.viewmanager.TaoBuManager;
import com.lianbei.taobu.utils.CheckAndRequestPermission;
import com.lianbei.taobu.utils.DisplayUtil;
import com.lianbei.taobu.utils.ToastUtil;
import com.lianbei.taobu.utils.Validator;
import com.lidong.photopicker.PhotoPickerActivity;
import com.lidong.photopicker.PhotoPreviewActivity;
import com.lidong.photopicker.SelectModel;
import com.lidong.photopicker.intent.PhotoPickerIntent;
import com.lidong.photopicker.intent.PhotoPreviewIntent;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 【打卡】发布动态
 */

public class AddDynamicActivity extends BaseActivity implements View.OnClickListener {
    private String TAG =MainActivity.class.getSimpleName();
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private ArrayList <String> imagePaths = new ArrayList<>();
    @BindView ( R.id.gridView )
    GridView gridView;
    @BindView ( R.id.btn_title_bar_right )
    Button btn_title_bar_right;
    @BindView ( R.id.linear_back_title_bar )
    ImageButton linear_back_title_bar;
    @BindView ( R.id.edit_text )
    EditText edit_text;
    private PhotoPickerGridAdapter gridAdapter;
    private String Dytext;
    TaoBuManager taoBuManager;
    List<File> imageFileList  = new ArrayList <> (  );
    Map<String,String> map =new HashMap <> (  );

    @Override
    public int getContentViewId() {
        return R.layout.activity_add_dynamic;
    }

    @Override
    public void initViews() {
        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 3 ? 3 : cols;
        gridView.setNumColumns(cols);
        Intent intent =getIntent ();
        String cate =  intent.getStringExtra ( "cate" );
        String math_id  = intent.getStringExtra ( "math_id" );
        map.put("cate",cate);
        map.put("math_id",math_id);
    }

    @Override
    public void initData() {
        taoBuManager = new TaoBuManager (  this);
    }
    @Override
    public void initListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DisplayUtil.hideKeyboard ( AddDynamicActivity.this );
                requestPermissions ( AddDynamicActivity.this, new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE}, new RequestPermissionCallBack ( ) {
                    @Override
                    public void granted() {
                        String imgs = (String) parent.getItemAtPosition(position);
                        if ("000000".equals(imgs) ){
                            PhotoPickerIntent intent = new PhotoPickerIntent( AddDynamicActivity.this);
                            intent.setSelectModel( SelectModel.MULTI);
                            intent.setShowCarema(true); // 是否显示拍照
                            intent.setMaxTotal(9); // 最多选择照片数量，默认为6
                            intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                            startActivityForResult(intent, REQUEST_CAMERA_CODE);
                        }else{
                            PhotoPreviewIntent intent = new PhotoPreviewIntent(AddDynamicActivity.this);
                            intent.setCurrentItem(position);
                            intent.setPhotoPaths(imagePaths);
                            startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                        }
                    }

                    @Override
                    public void denied() {
                        Toast.makeText(AddDynamicActivity.this, "授权失败，请重新授权", Toast.LENGTH_LONG).show();
                    }
                } );


            }
        });
        imagePaths.add("000000");
        gridAdapter = new PhotoPickerGridAdapter(imagePaths,this);
        gridView.setAdapter(gridAdapter);
        btn_title_bar_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dytext =edit_text.getText().toString().trim();
                if(Validator.isStrNotEmpty ( Dytext ) || (imageFileList != null && imageFileList.size ()>0)){

                    TaoBuManager.getInstance ().uploadDynameImage (AddDynamicActivity.this ,imageFileList,Dytext, map, new TaoBuManager.RequestCompletion ( ) {
                        @Override
                        public void Success(Object value, String tag) {
                            if(value != null){
                                if(value.equals ( "0" )){
                                    //清除文件
                                    imageFileList.clear ();
                                    //动态发布成功
                                    finish ();
                                }
                            }
                        }

                        @Override
                        public void Fail(Object value) {

                        }

                        @Override
                        public void Error(Object... values) {

                        }
                    } );

                }else{
                    ToastUtil.showShort ( AddDynamicActivity.this,"您还没有写动态哦！" );
                }
//                new Thread(){
//                    @Override
//                    public void run() {
//                        super.run();
//                        // FileUploadManager.uploadMany(imagePaths, Dytext);
//                        // FileUploadManager.upload(imagePaths,Dytext);
//                    }
//                }.start();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> list = data.getStringArrayListExtra( PhotoPickerActivity.EXTRA_RESULT);
                    Log.d(TAG, "list: " + "list = [" + list.size());
                    loadAdpater(list);
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    ArrayList<String> ListExtra = data.getStringArrayListExtra( PhotoPreviewActivity.EXTRA_RESULT);
                    Log.d(TAG, "ListExtra: " + "ListExtra = [" + ListExtra.size());
                    loadAdpater(ListExtra);
                    break;
            }
        }
    }
    private void loadAdpater(ArrayList<String> paths){
        if (imagePaths!=null&& imagePaths.size()>0){
            imagePaths.clear();
        }
        if (paths.contains("000000")){
            paths.remove("000000");
        }
        paths.add("000000");
        imagePaths.addAll(paths);
        gridAdapter  = new PhotoPickerGridAdapter(imagePaths,this);
        gridView.setAdapter(gridAdapter);
        try{
            JSONArray obj = new JSONArray(imagePaths);
            imageFileList.clear ();
            for (String str : imagePaths ) {
                if(!str.equals ( "000000" )){
                    imageFileList.add ( new File (str));
                }
            }
            Log.e("--", obj.toString());
            Log.e("imageFileList--", imageFileList.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLeftClick() {

    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onRefreshClick() {

    }

    @OnClick({R.id.linear_back_title_bar})
    @Override
    public void onClick(View v) {
        switch (v.getId ()){
            case R.id.linear_back_title_bar:
                finish ();
                break;

        }

    }
}
