package com.bs.utown.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bs.utown.R;
import com.bs.utown.base.BaseActivity;
import com.bs.utown.bean.UserInfo;
import com.bs.utown.constant.Constant;
import com.bs.utown.listener.DiadisListener;
import com.bs.utown.pickerview.TimePickerView;
import com.bs.utown.pickerview.other.pickerViewUtil;
import com.bs.utown.util.Logs;
import com.bs.utown.util.ObjectSave;
import com.bs.utown.util.PhotoUtil;
import com.bs.utown.util.TimeUtil;
import com.bs.utown.util.ToastUtil;
import com.bs.utown.view.DialogCode;
import com.bs.utown.view.RoundImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Description: 账户资料
 * AUTHOR: Champion Dragon
 * created at 2018/6/27
 **/
public class UserMsgActivity extends BaseActivity implements View.OnClickListener {
    private RoundImageView head;
    private TextView gender, birthday;
    private EditText name, uid;
    private File fileHead = new File(Constant.fileDir, Constant.filehead);
    private File fileTemp = new File(Constant.fileDir, Constant.temp);
    private static final int looking = 1;
    private static final int photoing = 2;
    private UserInfo userInfo;
    private PopupWindow popupWindow, popupGender;
    private Bitmap bitmap = null;
    private Uri uriTemp;
    private String tag = "UserMsgActivity";
    private DialogCode diaCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_user_msg);
        initView();
        initPopWindow();
        initGender();
        mkdir();
        initData();
    }

    /*文件夹不存在就新建*/
    private void mkdir() {
        if (!fileHead.exists()) {
            fileHead.mkdirs();
        }
        if (!fileTemp.exists()) {
            fileTemp.mkdirs();
        }
    }

    /*初始化数据*/
    private void initData() {
        userInfo = ObjectSave.getUserInfo();
        String headPath = userInfo.getHeadpath();
        String names = userInfo.getName();
        String uids = userInfo.getUID();
        String genders = userInfo.getGender();
        String birthdays = userInfo.getBirthday();
        Logs.v(tag + " 72 " + headPath + "  " + userInfo + "  " + names + " \n " + uids + "  " + genders + "  " + birthdays);
        if (headPath != null && !headPath.isEmpty()) {
            head.setImageBitmap(BitmapFactory.decodeFile(headPath));
        }
        if (names != null && !names.isEmpty()) {
            name.setText(names);
        }
        if (uids != null && !uids.isEmpty()) {
            uid.setText(uids);
        }
        if (genders != null && !genders.isEmpty()) {
            gender.setText(genders);
        }
        if (birthdays != null && !birthdays.isEmpty()) {
            birthday.setText(birthdays);
        }
    }

    private void initView() {
        head = (RoundImageView) findViewById(R.id.usermsg_head);
        head.setOnClickListener(this);
        findViewById(R.id.back_usermsg).setOnClickListener(this);
        findViewById(R.id.usermsg_save).setOnClickListener(this);
        gender = (TextView) findViewById(R.id.usermsg_gender);
        birthday = (TextView) findViewById(R.id.usermsg_birthday);
        findViewById(R.id.usermsg_gender_rl).setOnClickListener(this);
        findViewById(R.id.usermsg_birthday_rl).setOnClickListener(this);
        findViewById(R.id.usermsg_code_rl).setOnClickListener(this);
        name = (EditText) findViewById(R.id.usermsg_name);
        uid = (EditText) findViewById(R.id.usermsg_uid);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.usermsg_head:
                popWindow(popupWindow);
                break;
            case R.id.usermsg_save:
                SaveData();
                break;
            case R.id.back_usermsg:
                baseapp.finishActivity();
                break;
            case R.id.btn_cancle:
                popupWindow.dismiss();// popwindow消失
                break;
            case R.id.photo_ing:
                //启动系统的拍照功能
                Intent takephoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //新建个.jpg文件存放拍照出来的图片
                uriTemp = Uri.fromFile(new File(fileTemp, "临时.jpg"));
                takephoto.putExtra(MediaStore.EXTRA_OUTPUT, uriTemp);
                startActivityForResult(takephoto, photoing);
                popupWindow.dismiss();
                break;
            case R.id.photo_look:
                //启动系统给的查询照片功能
                Intent pic = new Intent(Intent.ACTION_GET_CONTENT);
                //设置成所有照片类型
                pic.setType("image/*");
                startActivityForResult(pic, looking);
                popupWindow.dismiss();
                break;
            case R.id.gender_cancle:
                popupGender.dismiss();// popwindow消失
                break;
            case R.id.gender_female:
                gender.setText("女");
                popupGender.dismiss();
                break;
            case R.id.gender_male:
                gender.setText("男");
                popupGender.dismiss();
                break;
            case R.id.usermsg_gender_rl:
                popWindow(popupGender);
                break;
            case R.id.usermsg_code_rl:
                OpenDialog();
                break;
            case R.id.usermsg_birthday_rl:
                setBirthday();
                break;
        }

    }

    /*设置生日时间*/
    private void setBirthday() {
        pickerViewUtil.alertTimerPicker(UserMsgActivity.this, TimePickerView.Type.YEAR_MONTH_DAY, Constant.formatbusinesstime,
                "请选择生日日期", 19, new pickerViewUtil.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        birthday.setText(date);
                    }
                });
    }


    //创建二维码弹框
    private void OpenDialog() {
        if (diaCode != null) {

        } else {
            diaCode = new DialogCode(this, name.getText().toString(), uid.getText().toString(), userInfo.getHeadpath(),
                    TimeUtil.long2time(System.currentTimeMillis(), Constant.formatbusinesstime), new DiadisListener() {
                @Override
                public void dismiss(Object object) {
                    ToastUtil.showShort("关闭开门对话框");
                    closeDialog();
                }
            });
        }
    }

    //关闭
    private void closeDialog() {
        if (diaCode != null) {
            diaCode.closeDia();
            diaCode = null;
        }
    }


    /*保存数据*/
    private void SaveData() {
        userInfo.setName(name.getText().toString());
        userInfo.setBirthday(birthday.getText().toString());
        userInfo.setGender(gender.getText().toString());
        userInfo.setUID(uid.getText().toString());
        ObjectSave.SaveUserInfo(userInfo);
        baseapp.finishActivity();
    }


    /*初始化头像popwindow*/
    private void initPopWindow() {
        View popView = View.inflate(this, R.layout.popowindow_photo, null);
        popupWindow = new PopupWindow(popView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        popupWindow.setAnimationStyle(R.style.PopupAnimation); // 设置弹出动画
        ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(this, R.color.transparent));
        popupWindow.setBackgroundDrawable(colorDrawable);// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setFocusable(true);// 设置PopupWindow可获得焦点
        popupWindow.setOutsideTouchable(true);// PopupWindow以外的区域是否可点击,点击后是否会消失。
        popView.findViewById(R.id.btn_cancle).setOnClickListener(this);
        popView.findViewById(R.id.photo_ing).setOnClickListener(this);
        popView.findViewById(R.id.photo_look).setOnClickListener(this);
        // popupWindow消失时监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpaha(UserMsgActivity.this, 1.0f);
            }
        });
    }

    /*初始化性别的popwindow*/
    private void initGender() {
        View popView = View.inflate(this, R.layout.popowindow_gender, null);
        popupGender = new PopupWindow(popView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        popupGender.setAnimationStyle(R.style.PopupAnimation); // 设置弹出动画
        ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(this, R.color.transparent));
        popupGender.setBackgroundDrawable(colorDrawable);// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupGender.setFocusable(true);// 设置PopupWindow可获得焦点
        popupGender.setOutsideTouchable(true);// PopupWindow以外的区域是否可点击,点击后是否会消失。
        popView.findViewById(R.id.gender_cancle).setOnClickListener(this);
        popView.findViewById(R.id.gender_female).setOnClickListener(this);
        popView.findViewById(R.id.gender_male).setOnClickListener(this);
        // popupWindow消失时监听
        popupGender.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpaha(UserMsgActivity.this, 1.0f);
            }
        });
    }


    /*popwindow显示*/
    private void popWindow(PopupWindow window) {
        View rootView = findViewById(R.id.usermsgactivity); // 设置当前根目录
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int y = dm.heightPixels * 1 / 12;
        //相对位移，popwindow出现在距离底部整个屏幕1/12距离
        window.showAtLocation(rootView, Gravity.BOTTOM, 0, y);
        // popupWindow.update();//更新后显示，比如做了长宽缩小放大的处理
        backgroundAlpaha(this, 0.5f);
    }

    /**
     * 设置添加屏幕的背景透明度
     */
    public void backgroundAlpaha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case looking:
                    Uri uri = data.getData();
                    setBitmap(uri);
                    break;
                case photoing:
                    setBitmap(uriTemp);
                    break;
            }
        }

    }

    /*将图片设置在iv上*/
    private void setBitmap(Uri uri) {
        try {
            bitmap = MediaStore.Images.Media.getBitmap(
                    this.getContentResolver(), uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*裁剪图片*/
        bitmap = PhotoUtil.SizeImage(bitmap);
        head.setImageBitmap(bitmap);
        executor.submit(SavePic);

    }

    Runnable SavePic = new Runnable() {
        @Override
        public void run() {
            Save();
        }
    };

    /**
     * 保存数据（本地路径，服务器）
     */
    private void Save() {
        File file = PhotoUtil.SavePhoto(bitmap, fileHead.getAbsolutePath(), "lcb");
        userInfo.setHeadpath(file.getAbsolutePath());
        ObjectSave.SaveUserInfo(userInfo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
            /*清理缓存*/
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
            System.gc();
        }
    }


}
