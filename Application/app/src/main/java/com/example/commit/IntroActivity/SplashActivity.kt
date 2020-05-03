package com.example.commit.IntroActivity

import android.accounts.AccountManager
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import com.example.commit.Class.UserInfo
import com.example.commit.MainActivity.MainActivity
import android.app.NotificationManager
import android.app.NotificationChannel
import android.os.Build
import android.app.Notification
import android.graphics.Color
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R
import android.view.View
import android.widget.Toast
import com.example.commit.Singleton.VolleyService
import org.json.JSONObject


class SplashActivity: AppCompatActivity() {
    val Duration:Long = 2000
    //onCreate : 액티비티가 생성될 때 실행되는 메소드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView : 액티비티와 연결되는 레이아웃파일을 설정
        setContentView(com.example.commit.R.layout.activity_splash)

        val view = window.decorView

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                view.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                //window.statusBarColor = Color.parseColor("#f2f2f2")
            }
        }

        //스마트폰 인터넷 사용 권한 허가
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork()
                .build())

        //알림 채널 생성
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var fcmPref=this.getSharedPreferences("FCM", Context.MODE_PRIVATE)
            if(fcmPref.getString("id","")==""){
                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val notificationChannel = NotificationChannel(
                    "fcm_uniting",
                    "fcm_uniting",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationChannel.description = "uniting fcm channel"
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(true)
                notificationChannel.vibrationPattern = longArrayOf(100, 200, 100, 200)
                notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
                notificationChannel.setShowBadge(true)
                notificationChannel.enableVibration(true)
                notificationManager.createNotificationChannel(notificationChannel)
            }
        }

        //프리퍼런스 검사 있으면 Main으로 startActivity 호출하고 return
        var userPref=this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        UserInfo.ID=userPref.getString("ID","")
        UserInfo.PW=userPref.getString("PW","")
        if(UserInfo.ID!="") {
            VolleyService.loginReq(UserInfo.ID, UserInfo.PW, this, {success->
                when(success.getInt("code")) {
                    0 -> {
                        //통신 실패
                        Handler().postDelayed({
                            var intent:Intent = Intent(this,LoginActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)  //액티비티 전환시 애니메이션을 무시
                            startActivity(intent)
                            finish()
                        },Duration)
                        Toast.makeText(this, "서버와의 통신에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                    1 -> {
                        //로그인 실패 : ID not exist
                        Handler().postDelayed({
                            var intent:Intent = Intent(this,LoginActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)  //액티비티 전환시 애니메이션을 무시
                            startActivity(intent)
                            finish()
                        },Duration)
                        Toast.makeText(this, "계정을 확인해주세요.", Toast.LENGTH_SHORT).show()
                    }
                    2 -> {
                        //로그인 실패 : PW error
                        Handler().postDelayed({
                            var intent:Intent = Intent(this,LoginActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)  //액티비티 전환시 애니메이션을 무시
                            startActivity(intent)
                            finish()
                        },Duration)
                        Toast.makeText(this, "ID / PW를 확인해주세요.", Toast.LENGTH_SHORT).show()
                    }
                    3 -> {
                        //로그인 성공
                        //프리퍼런스 저장
                        var user = success.getJSONObject("user")
                        UserInfo.NAME = user.getString("user_name")
                        UserInfo.BIRTH = user.getString("user_birthday")
                        UserInfo.GENDER = user.getString("user_gender")
                        UserInfo.NICKNAME = user.getString("user_nickname")
                        UserInfo.EMAIL = user.getString("user_email")
                        UserInfo.UNIV = user.getString("univ_name")
                        UserInfo.ENTER = user.getString("enter_year")
                        UserInfo.DEPT = user.getString("dept_name")
                        UserInfo.IMG = user.getString("user_image")
                        UserInfo.HOBBY = user.getString("user_hobby")
                        UserInfo.PERSONALITY = user.getString("user_personality")
                        //var token=FirebaseInstanceId.getInstance().token
                        UserInfo.FCM_TOKEN = user.getString("token")

                        /*val accountName = getAccount(this)
                        UserInfo.GOOGLE_ACCOUNT=accountName

                        val scope = "audience:server:client_id:" +
                                "348791094939-n14jfd8f4epba5ii0m5h39vjedf3647b.apps.googleusercontent.com"

                        var idToken: String? = null
                        try {
                            idToken = GoogleAuthUtil.getToken(this, accountName, scope)
                            UserInfo.GOOGLE_ID_TOKEN=idToken
                        } catch (e: Exception) {
                            Log.d("test", "Exception while getting idToken: $e")
                        }*/


                        var pref = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                        var editor = pref.edit()
                        editor.putString("ID", UserInfo.ID)
                            .putString("PW", UserInfo.PW)
                            .putString("NAME", UserInfo.NAME)
                            .putString("BIRTH", UserInfo.BIRTH)
                            .putString("GENDER", UserInfo.GENDER)
                            .putString("NICKNAME", UserInfo.NICKNAME)
                            .putString("EMAIL", UserInfo.EMAIL)
                            .putString("UNIV", UserInfo.UNIV)
                            .putString("ENTER", UserInfo.ENTER)
                            .putString("DEPT", UserInfo.DEPT)
                            .putString("HOBBY", UserInfo.HOBBY)
                            .putString("PERSONALITY", UserInfo.PERSONALITY)
                            .putString("IMG", UserInfo.IMG)
                            .putString("FCM_TOKEN", UserInfo.FCM_TOKEN)
                            /*.putString("GOOGLE_ID_TOKEN",UserInfo.GOOGLE_ID_TOKEN)
                            .putString("GOOGLE_ACCOUNT",UserInfo.GOOGLE_ACCOUNT)*/
                            .apply()

                        Handler().postDelayed({
                            var intent:Intent = Intent(this,MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)  //액티비티 전환시 애니메이션을 무시
                            startActivity(intent)
                            finish()
                        },Duration)
                    }
                }
            })
            /*UserInfo.PW=userPref.getString("PW","")
            UserInfo.NAME=userPref.getString("NAME","")
            UserInfo.BIRTH=userPref.getString("BIRTH","")
            UserInfo.GENDER=userPref.getString("GENDER","")
            UserInfo.NICKNAME=userPref.getString("NICKNAME","")
            UserInfo.EMAIL=userPref.getString("EMAIL","")
            UserInfo.UNIV=userPref.getString("UNIV","")
            UserInfo.ENTER=userPref.getString("ENTER","")
            UserInfo.DEPT=userPref.getString("DEPT","")
            UserInfo.HOBBY=userPref.getString("HOBBY","")
            UserInfo.PERSONALITY=userPref.getString("PERSONALITY","")
            UserInfo.IMG=userPref.getString("IMG","")
            UserInfo.FCM_TOKEN=userPref.getString("FCM_TOKEN","")*/
            /*UserInfo.GOOGLE_ID_TOKEN=userPref.getString("GOOGLE_ID_TOKEN","")
            UserInfo.GOOGLE_ACCOUNT=userPref.getString("GOOGLE_ACCOUNT","")*/
        }
        else {
            Handler().postDelayed({
                var intent= Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)  //액티비티 전환시 애니메이션을 무시
                startActivity(intent)
                finish()
            }, Duration)
        }




    }
}