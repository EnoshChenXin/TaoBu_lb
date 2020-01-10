package com.lianbei.commomview.dialog;


import com.lianbei.commomview.dialog.effects.BaseEffects;
import com.lianbei.commomview.dialog.effects.FadeIn;
import com.lianbei.commomview.dialog.effects.FlipH;
import com.lianbei.commomview.dialog.effects.FlipV;
import com.lianbei.commomview.dialog.effects.NewsPaper;
import com.lianbei.commomview.dialog.effects.SideFall;
import com.lianbei.commomview.dialog.effects.SlideLeft;
import com.lianbei.commomview.dialog.effects.SlideRight;
import com.lianbei.commomview.dialog.effects.SlideTop;
import com.lianbei.commomview.dialog.effects.SlideBottom;
import com.lianbei.commomview.dialog.effects.Fall;
import com.lianbei.commomview.dialog.effects.RotateBottom;
import com.lianbei.commomview.dialog.effects.RotateLeft;
import com.lianbei.commomview.dialog.effects.Slit;
import com.lianbei.commomview.dialog.effects.Shake;

/**
 * diaglog����Ч��
 * @author y_chen
 *
 */
public enum  Effectstype {

    Fadein(FadeIn.class),
    Slideleft(SlideLeft.class),
    Slidetop(SlideTop.class),
    SlideBottom(SlideBottom.class),
    Slideright(SlideRight.class),
    Fall(Fall.class),
    Newspager(NewsPaper.class),
    Fliph(FlipH.class),
    Flipv(FlipV.class),
    RotateBottom(RotateBottom.class),
    RotateLeft(RotateLeft.class),
    Slit(Slit.class),
    Shake(Shake.class),
    Sidefill(SideFall.class);
    private Class<? extends BaseEffects> effectsClazz;

    private Effectstype(Class<? extends BaseEffects> mclass) {
        effectsClazz = mclass;
    }

    public BaseEffects getAnimator() {
        BaseEffects bEffects=null;
	try {
		bEffects = effectsClazz.newInstance();
	} catch (ClassCastException e) {
		throw new Error("Can not init animatorClazz instance");
	} catch (InstantiationException e) {
		// TODO Auto-generated catch block
		throw new Error("Can not init animatorClazz instance");
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		throw new Error("Can not init animatorClazz instance");
	}
	return bEffects;
    }
}
