package com.lianbei.taobu.taobu.view;


import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.taobu.viewmanager.BMIUtils;
import com.lianbei.taobu.utils.risenumber.NumberTextView;
import com.lianbei.taobu.views.MButton;

import butterknife.BindView;

public class BMICalcAct extends BaseActivity {

    public static String LEAN_TYPE = "LEAN_TYPE";//偏瘦
    public static String NORMAL_TYPE = "NORMAL_TYPE";//正常
    public static String OVERWEIGHT_TYPE = "OVERWEIGHT_TYPE";//过重
    public static String OBESITY_TYPE = "OBESITY_TYPE";//肥胖
    public static String NO_TYPE = "NO_TYPE";

    private static final int INIT_TYPE_COLOR = 0xfff1f1f1;
    private static final int LEAN_TYPE_COLOR = 0xffb0d5ed;
    private static final int NORMAL_TYPE_COLOR = 0xff91db95;
    private static final int OVERWEIGHT_TYPE_COLOR = 0xffffb973;
    private static final int OBESITY_TYPE_COLOR = 0xffe88a8b;
    private static final int NO_TYPE_TYPE_COLOR = 0xff343434;


    private int START_COLOR = 0xfff1f1f1;

    @BindView(R.id.bmi_txt)
    NumberTextView bmiTxt;
    @BindView(R.id.bg_bmi2)
    LinearLayout bg_bmi2;

    @BindView(R.id.suggest_title)
    TextView suggest_title;

    @BindView(R.id.six_RelBtn)
    RelativeLayout six_RelBtn;


    @BindView(R.id.MBI_btn)
    MButton MBI_btn;
    @BindView(R.id.danwei)// 单位 %
            TextView danwei;

    @BindView(R.id.choose_six)
    TextView chooseSix; //选中
    @BindView(R.id.other_six) //备选切换性别
            TextView otherSix;

    @BindView(R.id.health_tips_txt) //备选切换性别
            TextView healthTipsTxt;

    @BindView(R.id.height_tv)
    EditText heightTv;
    @BindView(R.id.weight_tv)
    EditText weightTv;
    @BindView(R.id.age_tv)
    EditText ageTv;

    float sixNum;//性别
    float heightNum;//身高
    float weightNum; //体重
    float ageNum; //年龄


    @Override
    public int getContentViewId() {
        return R.layout.activity_bmicalc;
    }

    @Override
    public void initViews() {
        createNavigationView ( R.id.navigation_view );
        StringBuffer healthTextContext = new StringBuffer ( );
        String[] healthText;
        healthText = getResources ().getStringArray ( R.array.whatbodyfatrete );
        for( String linetxt: healthText){
            healthTextContext.append ( "\u3000\u3000" + linetxt + "\n\n" );
        }
        suggest_title.setText ( "体脂率是什么意思" );//体脂率健康小贴士
        healthTipsTxt.setText(healthTextContext.toString());

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        MBI_btn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                try {
                    sixNum = sixType(chooseSix.getText ().toString ().trim ());
                    heightNum =(Float.parseFloat ( heightTv.getText ().toString ().trim () ))/100  ;
                    weightNum =Float.parseFloat( weightTv.getText ().toString ().trim () );
                    ageNum =Float.parseFloat( ageTv.getText ().toString ().trim () );

                    float BMI = weightNum /(heightNum*heightNum);
                    float BodyFatrate =(1.2f*BMI)+(0.23f*ageNum)-5.4f-(10.8f*sixNum);
                    danwei.setVisibility ( View.VISIBLE );
                    addGoldNNumber(bmiTxt, Constant.MINE_CURRENT_GOLD, BodyFatrate);
                    setHealthTipsContent(chooseSix.getText ().toString ().trim (),BodyFatrate,ageNum);
                } catch (NumberFormatException e) {
                    e.printStackTrace ( );
                }

            }
        } );
        six_RelBtn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                if((chooseSix.getText ().toString ().trim ()).equals ( "男" )){
                    chooseSix.setText ( "女" );
                    otherSix.setText ( "男" );
                }else{
                    chooseSix.setText ( "男" );
                    otherSix.setText ( "女" );
                }
            }
        } );

    }


    public void addGoldNNumber(NumberTextView view, float mStartValue, float  mEndValue) {
        view.setFromAndEndNumber(mStartValue, mEndValue);  //设置开始和结束的数字，支持整数型和浮点型
        view.setDuration(1500);  //设置动画时间
        view.setScale ( 2 );
        view.start();            //开始动画i
    }
    private void setHealthTipsContent(String six,float BodyFatrate,float age){
        suggest_title.setText ( "体脂率健康小贴士" );//
        StringBuffer healthTextContext = new StringBuffer (  ) ;
        String[] healthText;
        switch (BMIUtils.getHealthTipsType (six,BodyFatrate,age)){
            case "LEAN_TYPE":
                setBackground(LEAN_TYPE_COLOR);
                healthText = getResources ().getStringArray ( R.array.lean );
                break;
            case "NORMAL_TYPE":
                setBackground(NORMAL_TYPE_COLOR);
                healthText = getResources ().getStringArray ( R.array.normal );
                break;
            case "OVERWEIGHT_TYPE":
                setBackground(OVERWEIGHT_TYPE_COLOR);
                healthText = getResources ().getStringArray ( R.array.overweight );
                break;
            case "OBESITY_TYPE":
                setBackground(OBESITY_TYPE_COLOR);
                healthText = getResources ().getStringArray ( R.array.obesity );
                break;
            case "NO_TYPE":
                setBackground(NO_TYPE_TYPE_COLOR);
                healthText = getResources ().getStringArray ( R.array.nodata );
                break;
                default:
                    setBackground(NO_TYPE_TYPE_COLOR);
                    healthText = getResources ().getStringArray ( R.array.nodata );
                    break;
        }
        for (String linetxt: healthText) {
            healthTextContext.append ( "\u3000\u3000"+linetxt+"\n\n" );
        }
        healthTipsTxt.setText ( healthTextContext.toString ());
    }

    private void setBackground(int endColor){
        ValueAnimator colorAnim = ObjectAnimator.ofInt(bg_bmi2,"backgroundColor", START_COLOR, endColor);
        colorAnim.setDuration(1500);
        colorAnim.setEvaluator(new ArgbEvaluator ()); colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatCount (0 );
        colorAnim.start();
        START_COLOR = endColor;
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
    private float sixType(String strSix){
        if(strSix.equals ( "男" ))
            return 1;
        else
            return 0;
    }
}
