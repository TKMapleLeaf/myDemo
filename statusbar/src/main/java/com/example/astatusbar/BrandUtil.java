package com.example.astatusbar;

import android.os.Build;
import android.support.annotation.NonNull;

public final class BrandUtil {

    public enum BRAND {
        /**
         * SAMSUNG
         */
        SAMSUNG,
        /**
         * HTC
         */
        HTC,
        /**
         * SONY
         */
        SONY,
        /**
         * LG
         */
        LG,
        /**
         * XIAOMI
         */
        XIAOMI,
        /**
         * HUAWEI
         */
        HUAWEI,
        /**
         * MEIZU
         */
        MEIZU,
        /**
         * MOTOROLA
         */
        MOTOROLA,
        /**
         * ZTE
         */
        ZTE,
        /**
         * COOLPAD
         */
        COOLPAD,
        /**
         * LENOVO
         */
        LENOVO,
        /**
         * OPPO
         */
        OPPO,
        /**
         * VIVO
         */
        VIVO,
        /**
         * GIONEE
         */
        GIONEE,
        /**
         * SMARTISAN
         */
        SMARTISAN
    }

    private BrandUtil() {

    }

    public static boolean checkBrand(@NonNull BRAND brand) {
        if (Build.MANUFACTURER != null && Build.MANUFACTURER.toUpperCase().contains(brand.toString())) {
            return true;
        } else if (Build.BRAND != null && Build.BRAND.toUpperCase().contains(brand.toString())) {
            return true;
        } else if (Build.MODEL != null && Build.MODEL.toUpperCase().contains(brand.toString())) {
            return true;
        } else {
            return false;
        }
    }

}
