package com.bs.utown.gridpasswordview;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

/**
 * Description: 改变密码的隐藏时的文字
 * AUTHOR: Champion Dragon
 * created at 2018/6/22
 **/

public class CustomPasswordTransformationMethod extends PasswordTransformationMethod {
    String transformation;

    public CustomPasswordTransformationMethod(String transformation) {
        this.transformation = transformation;
    }

    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return new PasswordCharSequence(source);
    }

    private class PasswordCharSequence implements CharSequence {
        private CharSequence mSource;

        public PasswordCharSequence(CharSequence source) {
            mSource = source;
        }

        @Override
        public int length() {
            return mSource.length();
        }

        @Override
        public char charAt(int index) {
            return transformation.charAt(0);
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            return mSource.subSequence(start, end);
        }
    }

}