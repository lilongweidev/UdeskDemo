package com.llw.udeskdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cn.udesk.PreferenceHelper;
import cn.udesk.UdeskSDKManager;
import cn.udesk.config.UdeskConfig;
import udesk.core.UdeskConst;

public class MainActivity extends AppCompatActivity {

    private TextView tv_online_service;

    //替换成你们注册生成的域名
    private String UDESK_DOMAIN = "1593286.s3.udesk.cn";
    //替换成你们生成应用产生的appid
    private String AppId = "9d3a79847ba2c088";
    // 替换成你们在后台生成的密钥
    private String UDESK_SECRETKEY = "6b9ebea7645ad58a21919ac1e98eb745";

    String sdkToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_online_service = (TextView)findViewById(R.id.tv_online_service);
        //传入注册的域名和密钥
        readAndWriteDomainAndKey();
        //获得Token
        if (TextUtils.isEmpty(sdkToken)) {
            sdkToken = UUID.randomUUID().toString();
        }
        //使用前需要设置的信息:
        UdeskSDKManager.getInstance().initApiKey(getApplicationContext(), UDESK_DOMAIN,
                UDESK_SECRETKEY, AppId);


        tv_online_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, String> info = new HashMap<String, String>();
                //sdktoken 必填**
                info.put(UdeskConst.UdeskUserInfo.USER_SDK_TOKEN, sdkToken);
                info.put(UdeskConst.UdeskUserInfo.NICK_NAME,"麻花藤");
                info.put(UdeskConst.UdeskUserInfo.CELLPHONE,"15651818750");

                //只设置用户基本信息的配置
                UdeskConfig.Builder builder = new UdeskConfig.Builder();
                builder.setDefualtUserInfo(info);

                //咨询会话
                UdeskSDKManager.getInstance().entryChat(getApplicationContext(), builder.build(), sdkToken);
            }
        });

    }

    private void readAndWriteDomainAndKey() {
        sdkToken = PreferenceHelper.readString(getApplicationContext(), "init_base_name", "sdktoken");

        PreferenceHelper.write(getApplicationContext(), "init_base_name", "sdktoken", sdkToken);
        PreferenceHelper.write(getApplicationContext(), "init_base_name", "domain", UDESK_DOMAIN);
        PreferenceHelper.write(getApplicationContext(), "init_base_name", "appkey", AppId);
        PreferenceHelper.write(getApplicationContext(), "init_base_name", "appid", UDESK_SECRETKEY);
    }
}
