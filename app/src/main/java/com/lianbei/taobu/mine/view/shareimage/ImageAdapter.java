package com.lianbei.taobu.mine.view.shareimage;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private Integer[] imagesId;
    private ImageView[] imageView;// 加载图片的imageView数组
    public ImageAdapter(Context context, Integer[] imageIds) {
        this.context = context;
        this.imagesId = imageIds;
        imageView = new ImageView[imageIds.length];
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imagesId.length;
    }

    @Override
    public Object getItem(int position) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imagesId[position]);
        return bitmap;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (imageView[position] == null)
            imageView[position] = new ImageView(context);
        //创建BitMap对象，用于显示图片
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                imagesId[position]);
        Matrix matrix = new Matrix();   //矩阵，用于图片比例缩放
        matrix.postScale((float)270/bitmap.getWidth(),
                (float)480/bitmap.getHeight());    //设置高宽比例（三维矩阵）
        //图片不能超出屏幕范围，否则报错，这里进行缩小
        Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        imageView[position].setImageBitmap(MyImgView.createReflectedImage(newBmp));
        imageView[position].setLayoutParams(new Gallery.LayoutParams(270, 480));
        return imageView[position];
    }

    public View getView2(int position, View convertView, ViewGroup parent) {
        if (imageView[position] == null)
            imageView[position] = new ImageView(context);
         // new MyTask().execute(position);//开启异步任务加载图片
//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
//                imagesId[position]);
        imageView[position].setLayoutParams(new Gallery.LayoutParams(270, 480));
        return imageView[position];
    }
    private class MyTask extends AsyncTask<Integer, Void, Void> {
        private Bitmap bitmap;
        private int position;
        @Override
        protected Void doInBackground(Integer... params) {
            //在这里加载图片
            position = params[0];
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            bitmap = BitmapFactory.decodeResource(context.getResources(), imagesId[params[0]],options);
            Matrix matrix = new Matrix();   //矩阵，用于图片比例缩放
            matrix.postScale((float)270/bitmap.getWidth(),
                    (float)480/bitmap.getHeight());    //设置高宽比例（三维矩阵）
            //图片不能超出屏幕范围，否则报错，这里进行缩小
            Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
            imageView[position].setImageBitmap(MyImgView.createReflectedImage(newBmp));
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            //因为该方法是由UI线程调用，所以在这里设置ImageView的图片
            super.onPostExecute(result);
            imageView[position].setImageBitmap(bitmap);
        }
    }
}
