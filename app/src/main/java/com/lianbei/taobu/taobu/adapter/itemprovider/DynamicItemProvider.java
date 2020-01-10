package com.lianbei.taobu.taobu.adapter.itemprovider;

import android.view.View;
import android.widget.AdapterView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.lianbei.taobu.R;
import com.lianbei.taobu.taobu.adapter.BaseDynamicItemProvider;
import com.lianbei.taobu.taobu.adapter.DynamicPhotoPickerGridAdapter;
import com.lianbei.taobu.circle.adapter.base.BaseDisscussItemProvider;
import com.lianbei.taobu.taobu.model.DynamicBean;
import com.lianbei.taobu.taobu.view.MyGridView;
import com.lianbei.taobu.utils.Validator;
import com.lidong.photopicker.intent.PhotoPreviewIntent;

import java.util.ArrayList;
import java.util.Arrays;

public class DynamicItemProvider extends BaseDynamicItemProvider {
    private DynamicPhotoPickerGridAdapter gridAdapter;

    public DynamicItemProvider(String channelCode) {
        super ( channelCode );
    }

    @Override
    public int viewType() {
        return 0;
    }

    @Override
    public int layout() {
        return R.layout.item_dynamic_layout;
    }

    @Override
    protected void setData(BaseViewHolder helper, DynamicBean.DynamicList dynamicBean) {
        helper.setText ( R.id.clocNnum, "未知" );
        if (Validator.isStrNotEmpty ( dynamicBean.getContent ( ) )) {
            helper.setVisible ( R.id.content_lin, true );
            helper.setGone ( R.id.clocNnum, false );
            helper.setText ( R.id.content, dynamicBean.getContent ( ) + "" );
        }

        ArrayList<String> imageList = new ArrayList<String>(Arrays.asList(dynamicBean.getList ( ).split(",")));
        if (Validator.isStrNotEmpty ( dynamicBean.getList ( ) ) && imageList != null && imageList.size () > 0) {
            helper.setVisible ( R.id.image_lin, true );
            helper.setGone ( R.id.clocNnum, false );
            //image 赋值
            MyGridView myGridView = helper.getView ( R.id.gridView );

            int NumColumns = 3;
            if (imageList.size () == 1) {
                NumColumns = 1;
            } else if (imageList.size () == 2 || imageList.size () == 4) {
                NumColumns = 2;
            } else if (imageList.size () == 3 || imageList.size () > 4) {
                NumColumns = 3;
            }
            int cols = this.mContext.getResources ( ).getDisplayMetrics ( ).widthPixels / this.mContext.getResources ( ).getDisplayMetrics ( ).densityDpi;
            cols = cols < NumColumns ? NumColumns : cols;
            myGridView.setNumColumns ( cols );
            gridAdapter = new DynamicPhotoPickerGridAdapter ( imageList, this.mContext );
            myGridView.setAdapter ( gridAdapter );
            myGridView.setOnItemClickListener ( new AdapterView.OnItemClickListener ( ) {
                @Override
                public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
                    PhotoPreviewIntent intent = new PhotoPreviewIntent ( mContext );
                    intent.setCurrentItem ( position );
                    intent.setCurrentType ( -1 );
                    intent.setPhotoPaths ( imageList );
                    mContext.startActivity ( intent );
                }
            } );
        }
        if (false) {
            helper.setBackgroundRes ( R.id.like_btn, R.mipmap.like2 );
            helper.setTextColor ( R.id.like_num, this.mContext.getResources ( ).getColor ( R.color.gary ) );
        } else {
            helper.setBackgroundRes ( R.id.like_btn, R.mipmap.like );
            helper.setTextColor ( R.id.like_num, this.mContext.getResources ( ).getColor ( R.color.text_grey ) );
        }
        helper.setText ( R.id.message2_num, dynamicBean.getNum ( ) + "" );
        helper.setText ( R.id.like_num, dynamicBean.getLike ( ) + "" );
        helper.addOnClickListener ( R.id.like_lin );
        helper.addOnClickListener ( R.id.like_btn );
        helper.addOnClickListener ( R.id.message2_lin );
        helper.addOnClickListener ( R.id.message2_btn );

    }
}
