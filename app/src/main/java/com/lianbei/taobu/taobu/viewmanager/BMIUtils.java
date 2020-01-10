package com.lianbei.taobu.taobu.viewmanager;

/**
 * 体脂计算器
 */
public class BMIUtils {



    private String chooseSix = "男";
    public static String LEAN_TYPE = "LEAN_TYPE";//偏瘦
    public static String NORMAL_TYPE = "NORMAL_TYPE";//正常
    public static String OVERWEIGHT_TYPE = "OVERWEIGHT_TYPE";//过重
    public static String OBESITY_TYPE = "OBESITY_TYPE";//肥胖
    public static String NO_TYPE = "NO_TYPE";


    public  enum EnumBMI {
        LEAN_TYPE, NORMAL_TYPE, OVERWEIGHT_TYPE, OBESITY_TYPE, NO_TYPE;
    }
    public static  String getHealthTipsType(String six, float BodyFatrate, float age) {
        if (six.equals ( "男" )) {
            if (0 <= age && age <= 17) {
                if (5 <= BodyFatrate && BodyFatrate <= 8) {
                    //偏瘦
                    return LEAN_TYPE;
                } else if (9 <= BodyFatrate && BodyFatrate <= 19) {
                    //正常
                    return NORMAL_TYPE;
                } else if (20 <= BodyFatrate && BodyFatrate <= 24) {
                    //过重
                    return OVERWEIGHT_TYPE;
                } else if (25 <= BodyFatrate && BodyFatrate <= 45) {
                    //超重-肥胖
                    return OBESITY_TYPE;
                } else {
                    //无法识别
                    return NO_TYPE;
                }
            } else if (18 <= age && age <= 39) {
                if (5 <= BodyFatrate && BodyFatrate <= 10) {
                    //偏瘦
                    return LEAN_TYPE;
                } else if (11 <= BodyFatrate && BodyFatrate <= 21) {
                    //正常
                    return NORMAL_TYPE;
                } else if (22 <= BodyFatrate && BodyFatrate <= 26) {
                    //过重
                    return OVERWEIGHT_TYPE;
                } else if (27 <= BodyFatrate && BodyFatrate <= 45) {
                    //超重-肥胖
                    return OBESITY_TYPE;
                } else {
                    //无法识别
                    return NO_TYPE;
                }
            } else if (40 <= age && age <= 59) {
                if (5 <= BodyFatrate && BodyFatrate <= 11) {
                    //偏瘦
                    return LEAN_TYPE;
                } else if (12 <= BodyFatrate && BodyFatrate <= 22) {
                    //正常
                    return NORMAL_TYPE;
                } else if (23 <= BodyFatrate && BodyFatrate <= 27) {
                    //过重
                    return OVERWEIGHT_TYPE;
                } else if (28 <= BodyFatrate && BodyFatrate <= 45) {
                    //超重-肥胖
                    return OBESITY_TYPE;
                } else {
                    //无法识别
                    return NO_TYPE;
                }
            } else if (60 <= age) {
                if (5 <= BodyFatrate && BodyFatrate <= 13) {
                    //偏瘦
                    return LEAN_TYPE;
                } else if (14 <= BodyFatrate && BodyFatrate <= 24) {
                    //正常
                    return NORMAL_TYPE;
                } else if (25 <= BodyFatrate && BodyFatrate <= 29) {
                    //过重
                    return OVERWEIGHT_TYPE;
                } else if (30 <= BodyFatrate && BodyFatrate <= 45) {
                    //超重-肥胖
                    return OBESITY_TYPE;
                } else {
                    //无法识别
                    return NO_TYPE;
                }
            }
        } else if (six.equals ( "女" )) {
            if (0 <= age && age <= 17) {
                if (5 <= BodyFatrate && BodyFatrate <= 19) {
                    //偏瘦
                    return LEAN_TYPE;
                } else if (20 <= BodyFatrate && BodyFatrate <= 33) {
                    //正常
                    return NORMAL_TYPE;
                } else if (34 <= BodyFatrate && BodyFatrate <= 38) {
                    //过重
                    return OVERWEIGHT_TYPE;
                } else if (39 <= BodyFatrate && BodyFatrate <= 45) {
                    //超重-肥胖
                    return OBESITY_TYPE;
                } else {
                    //无法识别
                    return NO_TYPE;
                }
            } else if (18 <= age && age <= 39) {
                if (5 <= BodyFatrate && BodyFatrate <= 20) {
                    //偏瘦
                    return LEAN_TYPE;
                } else if (21 <= BodyFatrate && BodyFatrate <= 34) {
                    //正常
                    return NORMAL_TYPE;
                } else if (35 <= BodyFatrate && BodyFatrate <= 39) {
                    //过重
                    return OVERWEIGHT_TYPE;
                } else if (40 <= BodyFatrate && BodyFatrate <= 45) {
                    //超重-肥胖
                    return OBESITY_TYPE;
                } else {
                    //无法识别
                    return NO_TYPE;
                }
            } else if (40 <= age && age <= 59) {
                if (5 <= BodyFatrate && BodyFatrate <= 21) {
                    //偏瘦
                    return LEAN_TYPE;
                } else if (22 <= BodyFatrate && BodyFatrate <= 35) {
                    //正常
                    return NORMAL_TYPE;
                } else if (36 <= BodyFatrate && BodyFatrate <= 40) {
                    //过重
                    return OVERWEIGHT_TYPE;
                } else if (41 <= BodyFatrate && BodyFatrate <= 45) {
                    //超重-肥胖
                    return OBESITY_TYPE;
                } else {
                    //无法识别
                    return NO_TYPE;
                }
            } else if (60 <= age) {
                if (5 <= BodyFatrate && BodyFatrate <= 22) {
                    //偏瘦
                    return LEAN_TYPE;
                } else if (23 <= BodyFatrate && BodyFatrate <= 36) {
                    //正常
                    return NORMAL_TYPE;
                } else if (37 <= BodyFatrate && BodyFatrate <= 41) {
                    //过重
                    return OVERWEIGHT_TYPE;
                } else if (42 <= BodyFatrate && BodyFatrate <= 45) {
                    //超重-肥胖
                    return OBESITY_TYPE;
                } else {
                    //无法识别
                    return NO_TYPE;
                }
            }
        }
        return NO_TYPE;
    }
}