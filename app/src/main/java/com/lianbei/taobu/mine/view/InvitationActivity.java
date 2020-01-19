package com.lianbei.taobu.mine.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.crazysunj.cardslideview.CardHolder;
import com.crazysunj.cardslideview.CardLinearSnapHelper;
import com.crazysunj.cardslideview.CardSlideView;
import com.crazysunj.cardslideview.CardViewHolder;
import com.crazysunj.cardslideview.OnPageChangeListener;
import com.crazysunj.cardslideview.PageTransformer;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.mine.model.InvitationImageBean;
import com.lianbei.taobu.mine.view.shareimage.InvitationImageView;
import com.lianbei.taobu.shop.view.gradual;
import com.lianbei.taobu.utils.BlurTransformation;
import com.lianbei.taobu.utils.GlideUtils;
import com.lianbei.taobu.utils.ThreadUtil;
import com.lianbei.taobu.utils.ToastUtil;
import com.lianbei.taobu.utils.ViewGenerationImage;
import com.lianbei.taobu.views.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;
import butterknife.BindView;
import butterknife.OnClick;

public class InvitationActivity extends BaseActivity implements View.OnClickListener {
    private boolean isCard = true;

    @BindView(R.id.main_bg)
    ImageView mainBG;

    @BindView(R.id.invitation_code)
    TextView invitation_code;

    @BindView(R.id.main_float_bg)
    gradual main_float_bg;

    @BindView(R.id.btn_back)
    ImageButton btn_back;


    @BindView(R.id.slide_view)
    CardSlideView<InvitationImageBean> slideView;

    private List<InvitationImageBean> list;
    private Drawable[] drawables;
    int[][] bitmapList111;
    private static Bitmap[] bitmapList;
    private final String[] imageArray = {
            "https://lbstorage.oss-cn-hangzhou.aliyuncs.com/taobu/appshare/invitation_1.jpg",
            "https://lbstorage.oss-cn-hangzhou.aliyuncs.com/taobu/appshare/invitation_2.jpg",
            "https://lbstorage.oss-cn-hangzhou.aliyuncs.com/taobu/appshare/invitation_1.jpg",
            "https://lbstorage.oss-cn-hangzhou.aliyuncs.com/taobu/appshare/invitation_2.jpg",
            "https://lbstorage.oss-cn-hangzhou.aliyuncs.com/taobu/appshare/invitation_1.jpg",
            "https://lbstorage.oss-cn-hangzhou.aliyuncs.com/taobu/appshare/invitation_2.jpg",
            "https://lbstorage.oss-cn-hangzhou.aliyuncs.com/taobu/appshare/invitation_1.jpg",
            "https://lbstorage.oss-cn-hangzhou.aliyuncs.com/taobu/appshare/invitation_2.jpg",
    };

    @Override
    public void initBeforeView() {
        super.initBeforeView();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_invitation;
    }

    @Override
    public void initViews() {
        main_float_bg.post(new Runnable() {
            @Override
            public void run() {
                main_float_bg.setViewType(1);
            }
        });

    }

    private void getBitMap(String url) {
        //stringLis.add(0,"我的");

        Glide.with(InvitationActivity.this)
                .load(url)
                .apply(new RequestOptions()
                        .transform(new BlurTransformation(InvitationActivity.this, 30)))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        ThreadUtil.runInThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < list.size(); i++) {
                                    if (url.equals(list.get(i).getImg())) {
                                        Palette(ImageUtils.drawableToBitmap(resource), i);
                                    }
                                }
                            }
                        });
                    }
                });
    }

    @Override
    public void initData() {
        list = new ArrayList<InvitationImageBean>();
        for (String s : imageArray) {
            list.add(new InvitationImageBean(s));
        }
        mainBG.post(new Runnable() {
            @Override
            public void run() {
                if (bitmapList == null || bitmapList.length < list.size()) {
                    //  colorint = new Integer[listurl.size()];
                    bitmapList111 = new int[list.size()][];
                    bitmapList = new Bitmap[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        getBitMap(list.get(i).getImg());
                    }
                    //getBitMap(listurl.get(0));
                }
            }
        });
        drawables = new Drawable[imageArray.length];
        slideView.setOnPageChangeListener(new MyPageChangeListener());
        slideView.bind(list, new MyCardHolder());
    }

    @OnClick({R.id.btn_back,R.id.copy_code})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.copy_code:
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager)getSystemService ( Context.CLIPBOARD_SERVICE );
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText ( "Label", invitation_code.getText().toString().trim());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip ( mClipData );
                ToastUtil.showShort(this,"邀请码已复制");
                break;
        }

    }

    class MyPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            if (bitmapList111 != null) {
                if (bitmapList111[position] != null && !bitmapList111[position].equals("")) {
                    setBackground2(bitmapList111[position][0], bitmapList111[position][1]);
                }
            }

            if (drawables[position] != null) {
                mainBG.setImageDrawable(drawables[position]);
                return;
            }

            InvitationImageBean data = list.get(position);
            Log.e("MainActivity", "onPageSelected" + "position:" + position);

            Glide.with(InvitationActivity.this)
                    .load(data.getImg())
                    .apply(new RequestOptions()
                            .transform(new BlurTransformation(InvitationActivity.this, 30)))
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            drawables[position] = resource;
                            mainBG.setImageDrawable(resource);
                            // colorint.add(BitmapUtil.getImageViewColor(ImageUtils.drawableToBitmap(resource)));
                            //   colorint[i] = BitmapUtil.getImageViewColor(ImageUtils.drawableToBitmap(resource));
                            // Palette(ImageUtils.drawableToBitmap(resource), position);
                        }
                    });
        }
    }

    static class MyCardHolder implements CardHolder<InvitationImageBean> {

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
            return inflater.inflate(R.layout.share_image_item, container, false);
        }

        @Override
        public void onBindView(@NonNull CardViewHolder holder, InvitationImageBean data, int position) {
            Log.e("MainActivity", "onBindView---data:" + data + "position:" + position);
            ImageView imageView = holder.getView(R.id.image);
            final String img = data.getImg();
            Glide.with(imageView.getContext()).load(img).apply(new RequestOptions().dontAnimate()).into(imageView);
            holder.itemView.setOnClickListener(v -> {
                Log.e("MainActivity", "setOnClickListener---data:" + data + "position:" + position);
                InvitationImageView.start(v.getContext(), img);
            });
        }
    }

    private void Palette(Bitmap resource, int i) {
        if (resource == null) {
            return;
        }
        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //记得判空
                if (palette == null) return;
                //palette取色不一定取得到某些特定的颜色，这里通过取多种颜色来避免取不到颜色的情况
                if (palette.getDarkVibrantColor(Color.TRANSPARENT) != Color.TRANSPARENT) {
                    createLinearGradientBitmap(palette.getDarkVibrantColor(Color.TRANSPARENT), palette.getVibrantColor(Color.TRANSPARENT), i);
                } else if (palette.getDarkMutedColor(Color.TRANSPARENT) != Color.TRANSPARENT) {
                    createLinearGradientBitmap(palette.getDarkMutedColor(Color.TRANSPARENT), palette.getMutedColor(Color.TRANSPARENT), i);
                } else {
                    createLinearGradientBitmap(palette.getLightMutedColor(Color.TRANSPARENT), palette.getLightVibrantColor(Color.TRANSPARENT), i);
                }
            }
        });
    }

    Bitmap bgBitmap;
    Canvas mCanvas;
    Paint mPaint;

    //创建线性渐变背景色
    private void createLinearGradientBitmap(int darkColor, int color, int i) {
        int bgColors[] = new int[2];
        bgColors[0] = darkColor;
        bgColors[1] = color;
        bitmapList111[i] = bgColors;
        Log.e("bgColors[0]:", bgColors[0] + "bgColors[1]:" + bgColors[1]);
        Bitmap bgBitmap = Bitmap.createBitmap(mainBG.getWidth(), mainBG.getHeight(), Bitmap.Config.ARGB_8888);

        if (mCanvas == null) {
            mCanvas = new Canvas();
        }
        if (mPaint == null) {
            mPaint = new Paint();
        }
        mCanvas.setBitmap(bgBitmap);
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        LinearGradient gradient = new LinearGradient(0, 0, 0, bgBitmap.getHeight(), bgColors[0], bgColors[1], Shader.TileMode.CLAMP);
        mPaint.setShader(gradient);
        RectF rectF = new RectF(0, 0, bgBitmap.getWidth(), bgBitmap.getHeight());
        mCanvas.drawRoundRect(rectF, 100, 100, mPaint); //这个用来绘制圆角的哈
        mCanvas.drawRect(rectF, mPaint);
        Log.e("createbgBitmap:", bgBitmap + "");
        bitmapList[i] = bgBitmap;
        //  bitmapList111[i] =bitmapList;
        // bitmapList = null;
        //imageView_bg.setImageBitmap(bgBitmap);
        //  handler.sendEmptyMessage(1);
        // main_float_bg.setImageBitmap(bgBitmap);
    }

    private void setBackground2(int ColorIntTop, int ColorIntBottom) {
        main_float_bg.setViewType(1);
        main_float_bg.setAlpha(0.5f);
        main_float_bg.setColorInt(ColorIntTop, ColorIntBottom);
        main_float_bg.requestLayout();
        main_float_bg.invalidate();

    }

    @Override
    public void initListener() {
    }

    static class MyScale implements PageTransformer {

        @Override
        public void transformPage(@NonNull View view, float offsetPercent, int orientation) {
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
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


}
