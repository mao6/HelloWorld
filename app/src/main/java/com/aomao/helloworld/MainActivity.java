package com.aomao.helloworld;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class MainActivity extends AppCompatActivity {

    private String token="uQqdh1hS1rhScQAiunzlpKfY0S//rj9A25K1FNIsE3zyJIGysZhjSyhJE5IEtKySP+CrgSUrb+6+hw44hmKB7Q==";
    private EditText user_id;
    private EditText user_name;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user_id= (EditText) findViewById(R.id.user_id);
        user_name= (EditText) findViewById(R.id.user_name);
        login= (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connect(token);
            }
        });



    }

    /**
     * 建立与融云服务器的连接
     *
     * @param token
     */
    private void connect(String token) {

        if (getApplicationInfo().packageName.equals(App.getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {

                    Log.d("LoginActivity", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {

                    Log.d("LoginActivity", "--onSuccess" + userid);
//                    startActivity(new Intent(MainActivity.this, ConversationActivity.class));
//                    finish();
                    Toast.makeText(MainActivity.this,"连接融云成功",Toast.LENGTH_SHORT).show();

                    RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

                        @Override
                        public UserInfo getUserInfo(String userId) {

                            return new UserInfo("4", "王麻子", Uri.parse("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3684554024,4152101820&fm=58"));//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
                        }

                    }, true);

                    RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

                        @Override
                        public UserInfo getUserInfo(String userId) {

                            return new UserInfo("3", "张三", Uri.parse("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1928069816,1212834053&fm=58"));//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
                        }

                    }, true);


                    RongIM.getInstance().startPrivateChat(MainActivity.this, "4", "王麻子");
//                    RongIM.getInstance().startConversationList(MainActivity.this);
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 *                  http://www.rongcloud.cn/docs/android.html#常见错误码
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                    Log.d("LoginActivity", "--onError" + errorCode);
                }
            });
        }
    }

}
