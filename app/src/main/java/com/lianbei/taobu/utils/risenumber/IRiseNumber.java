package com.lianbei.taobu.utils.risenumber;

public interface IRiseNumber {
    public void start();

    public void withNumber(int number);

    public void withNumber(float number);

    public void setFromAndEndNumber(int startNum, int EndNum);

    public void setFromAndEndNumber(float startNum, float EndNum);

    public void setDuration(long time);
    public void setScale(int time);

    public void setOnEndListener(NumberTextView.EndListener listener);


}
