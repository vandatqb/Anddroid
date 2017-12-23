package com.example.phanv.camera.Model.AccountModel;

/**
 * Created by phanv on 19-Dec-17.
 */

public class Setting {
    private boolean setting;
    private int min;
    private int max;

    public Setting(boolean setting, int min, int max) {
        this.setting = setting;
        this.min = min;
        this.max = max;
    }

    public boolean isSetting() {
        return setting;
    }

    public void setSetting(boolean setting) {
        this.setting = setting;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
