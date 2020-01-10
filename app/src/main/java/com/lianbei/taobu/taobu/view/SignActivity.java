package com.lianbei.taobu.taobu.view;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.mine.model.Clockinfo;
import com.lianbei.taobu.mine.model.MemberInfo;
import com.lianbei.taobu.taobu.adapter.SectionsPagerAdapter;
import com.lianbei.taobu.taobu.viewmanager.TaoBuManager;
import com.lianbei.taobu.utils.TimeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 打卡目标/打卡
 */
public class SignActivity extends BaseActivity implements  CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener, View.OnClickListener{

    @BindView ( R.id.tv_month_day )
    TextView mTextMonthDay;
    @BindView ( R.id.tv_year )
    TextView mTextYear;
    @BindView ( R.id.tv_lunar )
    TextView mTextLunar;
    @BindView ( R.id.rl_tool )
    RelativeLayout mRelativeTool;
    @BindView ( R.id.calendarView )
    CalendarView mCalendarView;
    @BindView ( R.id.tv_current_day )
    TextView mTextCurrentDay;
    @BindView ( R.id.calendarLayout )
    CalendarLayout mCalendarLayout;
    @BindView ( R.id.sing_lin )
    LinearLayout sing_lin;
    @BindView ( R.id.sign_IBtn )
    ImageView sign_IBtn;
    @BindView ( R.id.sing_content_tv )
    TextView sing_content_tv;
    @BindView ( R.id.tv_clockok )
    TextView tv_clockok;

    @BindView ( R.id.sign1 )
    ImageView sign1;
    @BindView ( R.id.sign2 )
    ImageView sign2;
    @BindView ( R.id.sign3 )
    ImageView sign3;
    @BindView ( R.id.sign4 )
    ImageView sign4;
    @BindView ( R.id.sign5 )
    ImageView sign5;
    @BindView ( R.id.sign6 )
    ImageView sign6;
    @BindView ( R.id.sign7)
    ImageView sign7;

    @BindView ( R.id.tv_idate)
    TextView tv_idate;
    @BindView ( R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;


    List<ImageView> imageViewList = new ArrayList <> (  );
    ImageView[] imageViews = null;
    private int mYear;

    ArrayList<Fragment> list = new ArrayList <Fragment> ();
    private int[] TAB_TITLES = new int[]{R.string.TAB_TEXT_MY, R.string.TAB_TEXT_HOT, R.string.TAB_TEXT_DAREN} ;
    @BindView ( R.id.tabs )
    TabLayout tabs;
    TaoBuManager taoBuManager;
    Clockinfo clockinfo = null;
    @Override
    public int getContentViewId() {
        return R.layout.activity_sing;
    }

    @Override
    public void initViews() {
        //setStatusBarDarkMode();
        createNavigationView ( R.id.navigation_view );
        //setStatusBarDarkMode();
        mCalendarView.setOnYearChangeListener(this);
        mCalendarView.setOnCalendarSelectListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));

        list.add(new SignMyFragment ());
        list.add(new SignHotkFragment ());
        list.add(new SignTalentFragment ());
         //Tab
        tabs.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter ( this, getSupportFragmentManager ( ) ,list,TAB_TITLES);
        ViewPager viewPager = findViewById ( R.id.view_pager );
        viewPager.setAdapter ( sectionsPagerAdapter );
        tabs.setupWithViewPager ( viewPager );
        tabs.setTabIndicatorFullWidth ( false );
        imageViews = new ImageView[]{sign1,sign2,sign3,sign4,sign5,sign6,sign7};
    }

    @Override
    public void initData() {
        taoBuManager = new TaoBuManager (this);
        taoBuManager.GetCLOCKContent (clockcontentcompletion,"0");

    }
    @OnClick(R.id.sing_lin )
    @Override
    public void onClick(View view) {
        switch (view.getId ()){
            case R.id.sing_lin:
                if(clockinfo == null){
                    return;
                }
                if(clockinfo.getClock ().isFlag ()){
                    Intent intent = new Intent (SignActivity. this,AddDynamicActivity.class );
                    intent.putExtra ( "cate","2" );
                    startActivity (intent);
                }else{
                    setSingData("1");
                }
                break;
        }
    }

    /**
     * 签到接口
     */
    private void  setSingData(String tag){
        taoBuManager.SignIn ( signRequestCompletion, tag);
    }

    /**
     * 打卡签到页面状态
     * @param isflag
     */
    private void setSingTypeView(boolean isflag){
        if(!isflag){ //未签到
            sign_IBtn.setBackgroundResource ( 0 );
            sign_IBtn.setBackgroundResource (R.mipmap.sing);
            sing_content_tv.setText ( "打卡签到" );
            tv_clockok.setText ( "快来打卡" );

        }else{
            sign_IBtn.setBackgroundResource ( 0 );
            sign_IBtn.setBackgroundResource (R.mipmap.sing_press);
            sing_content_tv.setText ( "添加动态" );
            tv_clockok.setText ( "目标完成！" );
        }
    }

    @Override
    public void initListener() {

    }


    @Override
    public void onLeftClick() {
        finish ();
    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onRefreshClick() {

    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility( View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }

    /**
     * 签到
     */

    TaoBuManager.RequestCompletion signRequestCompletion = new TaoBuManager.RequestCompletion ( ) {
        @Override
        public void Success(Object value, String tag) {
            MemberInfo memberInfo =(MemberInfo)value;
            if(memberInfo.getCode () ==0){
                taoBuManager.GetCLOCKContent (clockcontentcompletion,"1");
            }
        }

        @Override
        public void Fail(Object value) {

        }

        @Override
        public void Error(Object... values) {

        }
    };
    TaoBuManager.RequestCompletion clockcontentcompletion = new TaoBuManager.RequestCompletion ( ) {
        @Override
        public void Success(Object value, String tag) {
            if(value != null){
                clockinfo = (Clockinfo)value;
                Clockinfo.MColok molok= clockinfo.getClock ();
                tv_idate.setText (molok.getIdate ()+"");
                //签到打卡
                setSingTypeView(molok.isFlag ());
                //日历
                setchemeDate(clockinfo.getList ());
                int week = molok.getWeek ();
                for (int i=0;i<imageViews.length;i++){
                    if(i<week){
                        imageViews[i].setBackgroundResource ( R.drawable.circle_view_press_layout );
                    }else{
                        imageViews[i].setBackground (ContextCompat.getDrawable(SignActivity.this, R.drawable.circle_view_layout));
                    }
                }
                if(tag.equals ( "1" )){
                    Intent intent = new Intent (SignActivity. this,AddDynamicActivity.class );
                    startActivity (intent);
                }

            }
        }

        @Override
        public void Fail(Object value) {

        }

        @Override
        public void Error(Object... values) {

        }
    };


    private void setchemeDate(List<Clockinfo.Coloklist> coloklist){
//        int year = mCalendarView.getCurYear();
//        int month = mCalendarView.getCurMonth();
        Map <String, Calendar> map = new HashMap <> ();
        Clockinfo.Coloklist coloklist1;
        for (int i =0;i<coloklist.size ();i++){
            coloklist1 = coloklist.get ( i );
            int idate = coloklist1.getIdate ();
            if(coloklist1.isFlag ()){
                java.util.Calendar calendar =  TimeUtils.getCalendar(String.valueOf (idate ));
                int year = calendar.get(java.util.Calendar.YEAR);
                int month = calendar.get(java.util.Calendar.MONTH)+1;
                int DAY = calendar.get(java.util.Calendar.DAY_OF_MONTH);
                map.put(getSchemeCalendar(year, month, DAY, 0xFF40db25, "假").toString(),
                        getSchemeCalendar(year, month, DAY, 0xFF40db25, "假"));
            }
        }
//        map.put(getSchemeCalendar(year, month, 3, 0xFF40db25, "假").toString(),
//                getSchemeCalendar(year, month, 3, 0xFF40db25, "假"));
//        map.put(getSchemeCalendar(year, month, 6, 0xFFe69138, "事").toString(),
//                getSchemeCalendar(year, month, 6, 0xFFe69138, "事"));
//        map.put(getSchemeCalendar(year, month, 9, 0xFFdf1356, "议").toString(),
//                getSchemeCalendar(year, month, 9, 0xFFdf1356, "议"));
//        map.put(getSchemeCalendar(year, month, 13, 0xFFedc56d, "记").toString(),
//                getSchemeCalendar(year, month, 13, 0xFFedc56d, "记"));
//        map.put(getSchemeCalendar(year, month, 14, 0xFFedc56d, "记").toString(),
//                getSchemeCalendar(year, month, 14, 0xFFedc56d, "记"));
//        map.put(getSchemeCalendar(year, month, 15, 0xFFaacc44, "假").toString(),
//                getSchemeCalendar(year, month, 15, 0xFFaacc44, "假"));
//        map.put(getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记").toString(),
//                getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记"));
//        map.put(getSchemeCalendar(year, month, 25, 0xFF13acf0, "假").toString(),
//                getSchemeCalendar(year, month, 25, 0xFF13acf0, "假"));
//        map.put(getSchemeCalendar(year, month, 27, 0xFF13acf0, "多").toString(),
//                getSchemeCalendar(year, month, 27, 0xFF13acf0, "多"));
//        map.put(getSchemeCalendar(year, month, 30, 0xFF13acf0, "多").toString(),
//                getSchemeCalendar(year, month, 30, 0xFF13acf0, "多"));
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        mCalendarView.setSchemeDate(map);
    }
}
