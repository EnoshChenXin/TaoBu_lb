package com.lianbei.taobu.utils.clip;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.lianbei.taobu.R;
import com.lianbei.taobu.utils.DirectoryHelp;

import java.io.File;

public class ClipActivity extends Activity {
	private ClipImageLayout mClipImageLayout;
	private String path;
	private ProgressDialog loadingDialog;
	private Button photo_rotate;
	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		requestWindowFeature( Window.FEATURE_NO_TITLE);//去掉标题栏
		super.onCreate(savedInstanceState);
		setContentView( R.layout.activity_clipimage);
		//这步必须要加
		getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loadingDialog=new ProgressDialog (this);
        loadingDialog.setTitle("请稍后...");
		path=getIntent().getStringExtra("path");
        Log.e("tag","path="+path);
		//if(TextUtils.isEmpty(path) || !(new File (path).exists())){
		if(TextUtils.isEmpty(path) || !(new File (path).exists())){
			Toast.makeText(this, "图片加载失败[001]", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		bitmap=ImageTools.convertToBitmap(path, 600,600);
		if(bitmap==null){
			Toast.makeText(this, "图片加载失败[002]", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		photo_rotate = (Button)findViewById(R.id.photo_rotate);
		mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_clipImageLayout);
		mClipImageLayout.setBitmap(bitmap);
		photo_rotate.setOnClickListener(new OnClickListener () {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("tag","photo rotate>>>");
				Matrix matrix = new Matrix ();
		        matrix.postRotate(90);
		        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		        mClipImageLayout.setBitmap(bitmap);
			}
		});
		((Button)findViewById(R.id.id_action_clip)).setOnClickListener( new OnClickListener () {
			@Override
			public void onClick(View arg0) {
				Log.e("tag","action clip>>>");
				loadingDialog.show();
				runOnUiThread(new Runnable () {
					@Override
					public void run() {
						Bitmap bitmap = mClipImageLayout.clip();
						String path= DirectoryHelp.USER_PHOTO_CLICP_NAME_TMP;
						ImageTools.savePhotoToSDCard(ClipActivity.this,bitmap,path);
						loadingDialog.dismiss();
						Intent intent = new Intent ();
						intent.putExtra("path",path);
						setResult(RESULT_OK, intent);
						finish();
					}
				});
//				new Thread(new Runnable() {
//					@Override
//					public void run() {
//						Bitmap bitmap = mClipImageLayout.clip();
//						String path= DirectoryHelp.USER_PHOTO_CLICP_NAME_TMP;
//						ImageTools.savePhotoToSDCard(ClipActivity.this,bitmap,path);
//						loadingDialog.dismiss();
//						Intent intent = new Intent();
//						intent.putExtra("path",path);
//						setResult(RESULT_OK, intent);
//						finish();
//					}
//				}).start();
			}
		});
		
		((Button)findViewById(R.id.id_action_clip_cancel)).setOnClickListener( new OnClickListener () {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
}
