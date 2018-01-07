package cz.dialogcompat.sample.annotation;

import android.support.annotation.StringRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.dialogcompat.sample.R;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ToolBar {
    @StringRes int value() default R.string.app_name;
    boolean back() default true;
}