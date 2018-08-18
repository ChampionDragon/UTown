package com.bs.utown.user;

import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.bean.CarBean;
import com.bs.utown.bean.DataBean;
import com.bs.utown.constant.SpKey;
import com.bs.utown.gridpasswordview.GridPasswordView;
import com.bs.utown.pickerview.other.pickerViewUtil;
import com.bs.utown.view.XKeyboardView;

import java.util.ArrayList;

/**
 * Description: 添加车辆
 * AUTHOR: Champion Dragon
 * created at 2018/6/22
 **/
public class UserCaraddActivity extends BaseActivity implements View.OnClickListener {
    private TextView name, color;
    private GridPasswordView plate;
    private XKeyboardView keyboard;
    private CheckBox checkBox;
    private LinearLayout linearLayout;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_user_caradd);
        initView();
        initKeyborard();
    }

    private void initView() {
        findViewById(R.id.back_caradd).setOnClickListener(this);
        findViewById(R.id.car_color_rl).setOnClickListener(this);
        findViewById(R.id.car_name_rl).setOnClickListener(this);
        findViewById(R.id.car_confirm).setOnClickListener(this);
        name = (TextView) findViewById(R.id.car_name_tv);
        color = (TextView) findViewById(R.id.car_color_tv);
        checkBox = (CheckBox) findViewById(R.id.car_checkbox);
        linearLayout = (LinearLayout) findViewById(R.id.car_ll);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    linearLayout.setVisibility(View.GONE);
                } else {
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        plate = (GridPasswordView) findViewById(R.id.car_gpvPlateNumber);
        keyboard = (XKeyboardView) findViewById(R.id.car_keyboard);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_caradd:
                baseapp.finishActivity();
                break;
            case R.id.car_color_rl:
                Color();
                break;
            case R.id.car_name_rl:
                Name();
                break;
            case R.id.car_confirm:
                Confirm();
                break;
        }
    }

    private void Name() {
        list = DataBean.getCar();
        pickerViewUtil.alertBottomWheelOption(this, "选择汽车品牌", list, new pickerViewUtil.OnWheelViewClick() {
            @Override
            public void onClick(View view, int postion) {
                name.setText(list.get(postion));
            }
        });
    }

    private void Color() {
        list = DataBean.getColor();
        pickerViewUtil.alertBottomWheelOption(this, "选择汽车颜色", list, new pickerViewUtil.OnWheelViewClick() {
            @Override
            public void onClick(View view, int postion) {
                color.setText(list.get(postion));
            }
        });
    }


    private void Confirm() {
        CarBean carBean = new CarBean();
        if (checkBox.isChecked()) {
            carBean.setPlateNum("无车牌");
        } else {
            carBean.setPlateNum(plate.getPassWord());
        }
        carBean.setColor(color.getText().toString());
        carBean.setName(name.getText().toString());
        setResult(UserCarmgrActivity.Addcar, getIntent().putExtra(SpKey.carAdd, carBean));
        finish();
    }


    private void initKeyborard() {
        keyboard.setIOnKeyboardListener(new XKeyboardView.IOnKeyboardListener() {
            @Override
            public void onInsertKeyEvent(String text) {
                plate.appendPassword(text);
            }

            @Override
            public void onDeleteKeyEvent() {
                plate.deletePassword();
            }

            @Override
            public void hideKeyboard() {
                        /*隐藏键盘*/
                if (keyboard.isShown()) {
                    keyboard.setVisibility(View.GONE);
                }
            }
        });
        plate.togglePasswordVisibility();
        plate.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public boolean beforeInput(int position) {
                if (position == 0) {
                    keyboard.setKeyboard(new Keyboard(UserCaraddActivity.this, R.xml.provice));
                    keyboard.setVisibility(View.VISIBLE);
                    return true;
                } else if (position >= 1 && position < 2) {
                    keyboard.setKeyboard(new Keyboard(UserCaraddActivity.this, R.xml.english));
                    keyboard.setVisibility(View.VISIBLE);
                    return true;
                } else if (position >= 2 && position < 8) {
                    keyboard.setKeyboard(new Keyboard(UserCaraddActivity.this, R.xml.qwerty_without_chinese));
                    keyboard.setVisibility(View.VISIBLE);
                    return true;
                }
                keyboard.setVisibility(View.GONE);
                return false;
            }

            @Override
            public void onTextChanged(String psw) {
            }

            @Override
            public void onInputFinish(String psw) {
            }
        });
    }


    /*捕获返回键的监听，如果自定义键盘显示按返回键令其消失，自定义键盘不显示则正常返回功能*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyboard.isShown()) {
                keyboard.setVisibility(View.GONE);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
