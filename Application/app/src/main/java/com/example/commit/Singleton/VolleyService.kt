package com.example.commit.Singleton

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.*
import com.example.commit.MainActivity.MakeRoomActivity
import org.json.JSONArray
import org.json.JSONObject


//VolleyService를 사용하기위한 싱글톤

object VolleyService {
    //학교
    val ip: String = "http://52.78.27.41:1901"

    //아이디 중복체크
    fun idCheckReq(id: String, context: Context, success: (Int) -> Unit) {
        val url = "${ip}/user/check"

        val json = JSONObject()
        json.put("id", id)

        var request = object : JsonObjectRequest(Method.POST
            , url
            , json
            , Response.Listener {
                success(0)
            }
            , Response.ErrorListener {
                if (it is com.android.volley.ParseError) {
                    success(1)
                }
            }
        ) {
            override fun getBodyContentType(): String {
                return "application/json"
            }
        }

        Volley.newRequestQueue(context).add(request)
    }

    //닉네임 중복체크
    fun nicknameCheckReq(nickname: String, context: Context, success: (Int) -> Unit) {
        val url = "${ip}/user/check/nickname"

        val json = JSONObject()
        json.put("nickname", nickname)

        var request = object : JsonObjectRequest(Method.POST
            , url
            , json
            , Response.Listener {
                Toast.makeText(context, "중복된 닉네임입니다.", Toast.LENGTH_SHORT).show()
                success(0)
            }
            , Response.ErrorListener {
                if (it is com.android.volley.ParseError) {
                    Toast.makeText(context, "사용 가능한 닉네임입니다.", Toast.LENGTH_SHORT).show()
                    success(1)
                }
            }
        ) {
            override fun getBodyContentType(): String {
                return "application/json"
            }
        }

        Volley.newRequestQueue(context).add(request)
    }

    //이메일 인증 코드 요청
    fun codeReq(context: Context, success: (String) -> Unit) {
        val url = "${ip}/code"//요청 URL

        val json = JSONObject() // 서버로 전송할 json 객체

        var request = object : JsonObjectRequest(Method.GET
            , url
            , json
            , Response.Listener {
                Log.d("test", "코드 생성 ${it.toString()}")
                success(it.getString("code"))
            }
            , Response.ErrorListener {
                Log.d("test", it.toString())
            }
        ) {
            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        //요청을 보내는 부분
        Volley.newRequestQueue(context).add(request)
    }

    //로그인 요청
    fun loginReq(id: String, pw: String, context: Context, success: (JSONObject) -> Unit) {
        val url = "${ip}/user/login"//요청 URL

        val json = JSONObject() // 서버로 전송할 json 객체
        json.put("id", id) // json 객체에 데이터 삽입, 첫번째 파라미터가 키, 두번째 파라미터가 값

        var result = JSONObject()

        // Request객체를 생성하여야 함 종류는 다양하지만 여기선 JsonObjectRequest객체를 생성
        // 객체 생성 파라미터(메소드타입(GET,POST,PUT,DELETE) / URL / 보낼 데이터(json) / 통신 성공 리스너 / 통신 실패 리스너
        var request = object : JsonObjectRequest(Method.POST
            , url
            , json
            , Response.Listener {
                result.put("user", it)
                // 통신 성공 리스너 : 통신 성공 시에 호출
                if (pw != it.getString("user_pw"))
                    result.put("code", 2)
                else if (pw == it.getString("user_pw"))
                    result.put("code", 3)
                success(result)
            }
            , Response.ErrorListener {
                // 통신 실패 리스너 : 통신 실패 시에 호출
                if (it is com.android.volley.TimeoutError) {
                    Log.d("test", "TimeoutError")
                    result.put("code", 0)
                } else if (it is com.android.volley.ParseError) {
                    Log.d("test", "ParserError")
                    result.put("code", 1)
                }
                success(result)
            }
        ) {
            //객체 생성 괄호(소괄호)를 닫은 후에 추가하는 요청 Body 부분(비어있어도 됨)
            //getBodyContentType()은 보내는 데이터의 타입을 정의하는 것
            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        //요청을 보내는 부분
        Volley.newRequestQueue(context).add(request)
    }

    /*fun imageReq(id:String, bitmap: Bitmap, context: Context){
        var url="${ip}/user/image"

        var request = object : StringRequest(
            Method.POST,
            url,
            Response.Listener {

            },
            Response.ErrorListener {

            }){
            override fun getParams(): MutableMap<String, String> {
                var params=HashMap<String,String>()

                var image= getStringImage(bitmap)
                var id=id

                params.put("id",id)
                params.put("image",image)

                return params
            }
        }

        Volley.newRequestQueue(context).add(request)
    }*/

    //회원가입 요청
    fun joinReq(
        id: String,
        pw: String,
        name: String,
        birthday: String,
        gender: String,
        nickname: String,
        webMail: String,
        universityName: String,
        departmentName: String,
        enterYear: String,
        bitmap: Bitmap,
        hobby: String,
        personality: String,
        context: Context,
        success: (String) -> Unit
    ) {
        val url = "${ip}/user"//요청 URL

        var stringImage = ImageManager.BitmapToString(bitmap)

        val json = JSONObject() // 서버로 전송할 json 객체
        json.put("id", id) // json 객체에 데이터 삽입, 첫번째 파라미터가 키, 두번째 파라미터가 값
        json.put("pw", pw)
        json.put("name", name)
        json.put("birthday", birthday)
        json.put("gender", gender)
        json.put("nickname", nickname)
        json.put("web_mail", webMail)
        json.put("university_name", universityName)
        json.put("department_name", departmentName)
        json.put("enter_year", enterYear)
        json.put("image", stringImage)
        json.put("hobby", hobby)
        json.put("personality", personality)

        // Request객체를 생성하여야 함 종류는 다양하지만 여기선 JsonObjectRequest객체를 생성
        // 객체 생성 파라미터(메소드타입(GET,POST,PUT,DELETE) / URL / 보낼 데이터(json) / 통신 성공 리스너 / 통신 실패 리스너
        var request = object : JsonObjectRequest(Method.POST
            , url
            , json
            , Response.Listener {
                // 통신 성공 리스너 : 통신 성공 시에 호출
                success(it.getString("result"))
            }
            , Response.ErrorListener {
                // 통신 실패 리스너 : 통신 실패 시에 호출
                Log.d("test", it.toString())
            }
        ) {
            //객체 생성 괄호(소괄호)를 닫은 후에 추가하는 요청 Body 부분(비어있어도 됨)
            //getBodyContentType()은 보내는 데이터의 타입을 정의하는 것
            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        //요청을 보내는 부분
        Volley.newRequestQueue(context).add(request)
    }

    //학교 검색
    fun search_university(context: Context, success: (JSONArray?) -> Unit) {
        val url = "${ip}/university"

        var jsonObject = JSONObject()

        var jsonArray: JSONArray = JSONArray()
        jsonArray.put(jsonObject)

        //Log.d("test", "name : ${jsonObject.getString("name")}")

        var request = object : JsonArrayRequest(Method.POST
            , url
            , jsonArray
            , Response.Listener {
                success(it)
            }
            , Response.ErrorListener {
                Log.d("test", it.toString())
            }) {

        }
        Volley.newRequestQueue(context).add(request)
    }

    //학과 검색
    fun search_department(
        universityName: String,
        context: Context,
        success: (JSONArray?) -> Unit
    ) {
        val url = "${ip}/department"

        var jsonObject = JSONObject()
        jsonObject.put("univ_name", universityName)

        var jsonArray: JSONArray = JSONArray()
        jsonArray.put(jsonObject)

        var request = object : JsonArrayRequest(Method.POST
            , url
            , jsonArray
            , Response.Listener {
                success(it)
            }
            , Response.ErrorListener {
                Log.d("test", "학과검색 에러 : ${it.toString()}")
            }) {

        }
        Volley.newRequestQueue(context).add(request)
    }

    //데이팅 유저 불러오기
    fun datingUserReq(
        nickname: String,
        gender: String,
        universityName: String,
        context: Context,
        success: (JSONArray?) -> Unit
    ) {
        val url = "${ip}/user/dating"

        var jsonArray = JSONArray()

        var jsonObject = JSONObject()
        jsonObject.put("nickname", nickname)
        jsonObject.put("gender", gender)
        jsonObject.put("univ_name", universityName)


        jsonArray.put(jsonObject)

        var request = object : JsonArrayRequest(
            Method.POST,
            url,
            jsonArray,
            Response.Listener {
                Log.d("uniting",it.toString())
                success(it)
            },
            Response.ErrorListener {
                Log.d("test", it.toString())
            }) {
        }


        Volley.newRequestQueue(context).add(request)
    }

    //채팅방 생성
    fun createChatRoomReq(
        maker: String,
        user: String,
        roomTitle: String,
        category: String,
        universityName: String,
        context: Context,
        success: (JSONObject?) -> Unit
    ) {
        val url = "${ip}/join_room"

        var jsonObject = JSONObject()

        jsonObject.put("cate_name", category)
        jsonObject.put("maker", maker)
        jsonObject.put("user", user)
        jsonObject.put("univ_name", universityName)
        jsonObject.put("room_title", roomTitle)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            jsonObject,
            Response.Listener {
                Log.d("test", it.toString())
                success(it)
            },
            Response.ErrorListener {
                Log.d("test", it.toString())
            }) {

        }
        Volley.newRequestQueue(context).add(request)
    }

    fun createOpenChatReq(
        maker: String,
        roomTitle: String,
        category: String,
        universityName: String,
        introduce: String,
        maxNum: Int,
        context: Context,
        success: (JSONObject?) -> Unit
    ) {
        val url = "${ip}/join_room/open_chat"

        var jsonObject = JSONObject()

        jsonObject.put("category", category)
        jsonObject.put("maker", maker)
        jsonObject.put("univ_name", universityName)
        jsonObject.put("room_title", roomTitle)
        jsonObject.put("max_num", maxNum)
        jsonObject.put("introduce", introduce)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            jsonObject,
            Response.Listener {
                Log.d("test", it.toString())
                success(it)
            },
            Response.ErrorListener {
                Log.d("test", it.toString())
            }) {

        }
        Volley.newRequestQueue(context).add(request)
    }

    fun joinChatRoomReq(roomId: String, user: String, context: Context, success: (Int) -> Unit) {
        val url = "${ip}/join_room/join_room"

        var jsonObject = JSONObject()

        jsonObject.put("room_id", roomId)
        jsonObject.put("user", user)

        Log.d("test", user)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            jsonObject,
            Response.Listener {
                if (it.get("result").toString() == "success") {
                    success(1)
                } else {
                    success(0)
                }
            },
            Response.ErrorListener {
                Log.d("test", "방 입장 오류 : ${it.toString()}")
            }) {

        }
        Volley.newRequestQueue(context).add(request)

    }

    fun getJoinTimeReq(
        roomId: String,
        nickname: String,
        context: Context,
        success: (String) -> Unit
    ) {
        val url = "${ip}/join_room/get_join_time"

        var jsonObject = JSONObject()

        jsonObject.put("room_id", roomId)
        jsonObject.put("nickname", nickname)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            jsonObject,
            Response.Listener {
                var time = it.getString("enter_date")
                success(time)
            },
            Response.ErrorListener {
            }) {

        }
        Volley.newRequestQueue(context).add(request)
    }

    //게시글 불러오기 : 태그 이용
    fun postReq(tag: String, context: Context, success: (JSONArray?) -> Unit) {
        val url = "${ip}/post/${tag}"

        val jsonArray = JSONArray()

        var request = object : JsonArrayRequest(
            Method.GET,
            url,
            jsonArray,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {
                Log.d("test", it.toString())
            }) {

        }
        Volley.newRequestQueue(context).add(request)
    }

    fun myChatRoomListReq(nickname: String, context: Context, success: (JSONArray?) -> Unit) {
        var url = "${ip}/join_room/my_chat_room"

        var jsonArray = JSONArray()

        var jsonObject = JSONObject()
        jsonObject.put("nickname", nickname)

        jsonArray.put(jsonObject)
        var request = object : JsonArrayRequest(
            Method.POST,
            url,
            jsonArray,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }) {

        }
        Volley.newRequestQueue(context).add(request)
    }

    fun openChatRoomListReq(
        universityName: String,
        category: String,
        context: Context,
        success: (JSONArray?) -> Unit
    ) {
        var url = "${ip}/join_room/open_chat_room"

        var jsonArray = JSONArray()

        var jsonObject = JSONObject()
        jsonObject.put("univ_name", universityName)
        jsonObject.put("category", category)

        Log.d("test", "${universityName} ${category}")

        jsonArray.put(jsonObject)

        var request = object : JsonArrayRequest(
            Method.POST,
            url,
            jsonArray,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }) {

        }
        Volley.newRequestQueue(context).add(request)
    }


    fun exitReq(nickname: String, roomId: String, context: Context, success: (Int?) -> Unit) {
        var url = "${ip}/join_room/exit"

        var jsonObject = JSONObject()
        jsonObject.put("nickname", nickname)
        jsonObject.put("room_id", roomId)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            jsonObject,
            Response.Listener {

            },
            Response.ErrorListener {

            }) {

        }
        Volley.newRequestQueue(context).add(request)
    }

    fun datingExitReq(nickname: String, context: Context) {
        var url = "${ip}/join_room/exit/dating"

        var jsonObject = JSONObject()
        jsonObject.put("nickname", nickname)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            jsonObject,
            Response.Listener {

            },
            Response.ErrorListener {

            }) {
        }
        Volley.newRequestQueue(context).add(request)
    }

    fun checkJoinReq(
        roomId: String,
        nickname: String,
        context: Context,
        success: (String) -> Unit
    ) {
        var url = "${ip}/join_room/check_join"

        var jsonObject = JSONObject()
        jsonObject.put("room_id", roomId)
        jsonObject.put("nickname", nickname)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            jsonObject,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {

            }) {

        }
        Volley.newRequestQueue(context).add(request)
    }

    fun getUserInRoom(
        roomId: String,
        nickname: String,
        context: Context,
        success: (JSONArray?) -> Unit
    ) {
        var url = "${ip}/join_room/user_in_room"

        var jsonArray = JSONArray()

        var jsonObject = JSONObject()
        jsonObject.put("room_id", roomId)
        jsonObject.put("nickname", nickname)

        jsonArray.put(jsonObject)

        var request = object : JsonArrayRequest(
            Method.POST,
            url,
            jsonArray,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }) {

        }
        Volley.newRequestQueue(context).add(request)
    }
    //==========세현==========


    //이메일로 아이디 찾기 ->상원
    fun findReq(email: String, context: Context, success: (Int) -> Unit) {
        val url = "${ip}/user"

        val json_search = JSONObject()
        json_search.put("email", email)

        var jsonArray: JSONArray = JSONArray()
        jsonArray.put(json_search)

        var request = object : JsonObjectRequest(Method.POST
            , url
            , json_search
            , Response.Listener {
                if (email != it.getString("email"))
                    success(1)
                else if (email == it.getString("email"))
                    success(2)
            }
            , Response.ErrorListener {
                if (it is com.android.volley.TimeoutError) {
                    Log.d("test", "TimeoutError")
                    success(0)
                }
            }) {
            override fun getBodyContentType(): String {
                return "applycation/json_search"
            }
        }
    } //ID만 찾을떄쓰는 함수

    fun findReq2(id: String, email: String, context: Context, success: (Int) -> Unit) {
        val url = "${ip}/user"

        val json_search2 = JSONObject()
        json_search2.put("id", id)

        var jsonArray: JSONArray = JSONArray()
        jsonArray.put(json_search2)

        var request = object : JsonObjectRequest(Method.POST
            , url
            , json_search2
            , Response.Listener {
                if (email != it.getString("email"))
                    success(2)
                else if (email == it.getString("email"))
                    success(3)
            }
            , Response.ErrorListener {
                if (it is com.android.volley.TimeoutError) {
                    Log.d("test", "TimeoutError")
                    success(0)
                } else if (it is com.android.volley.ParseError) {
                    Log.d("test", "ParseError")
                    success(1)
                }
            }) {
            override fun getBodyContentType(): String {
                return "applycation/json_search2"
            }
        }

    }//비번 찾을때 함수

    fun check_num(number: Int, context: Context, success: (Int) -> Unit) {
        val url = "${ip}/user"

        val json_num = JSONObject()
        json_num.put("number", number)

        var jsonArray: JSONArray = JSONArray()
        jsonArray.put(json_num)

        var request = object : JsonObjectRequest(Method.POST
            , url
            , json_num
            , Response.Listener {
                if (number == it.getInt("number"))
                    success(3)
            }
            , Response.ErrorListener {
                if (it is com.android.volley.TimeoutError) {
                    Log.d("test", "TimeoutError")
                    success(0)
                } else if (it is com.android.volley.ParseError) {
                    Log.d("test", "ParseError")
                    success(1)
                }
            }) {
            override fun getBodyContentType(): String {
                return "application/json_num"
            }
        }
    }

    fun change_pw(pw: String, pw2: String, context: Context, success: (Int) -> Unit) {
        val url = "${ip}/user"

        val json_ch = JSONObject()
        json_ch.put("pw", pw)

        var request = object : JsonObjectRequest(Method.POST
            , url
            , json_ch
            , Response.Listener {
                if (pw != pw2) {
                    success(2)
                } else if (pw == pw2) {
                    success(3)
                }
            }


            , Response.ErrorListener {
                if (it is com.android.volley.TimeoutError) {
                    Log.d("test", "TimeoutError")
                    success(0)
                } else if (it is com.android.volley.ParseError) {
                    Log.d("test", "ParseError")
                    success(1)
                }
            }) {
            override fun getBodyContentType(): String {
                return "application/json_ch"
            }
        }
    }


    fun rcreateOpenChatReq(
        nickname: Any,
        roomTitle: Any,
        category: String,
        univ: Any,
        최대인원: Any,
        makeRoomActivity: MakeRoomActivity,
        function: (Nothing) -> Unit
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertReviewReq(
        nickname: String,
        cafeName: String,
        universityName: String,
        point: Int,
        content: String,
        context: Context,
        success: (String) -> Unit
    ) {
        val url = "${ip}/review/insert"

        val json_insertreview = JSONObject()
        json_insertreview.put("nickname", nickname)
        json_insertreview.put("cafe_name", cafeName)
        json_insertreview.put("univ_name", universityName)
        json_insertreview.put("point", point)
        json_insertreview.put("content", content)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            json_insertreview,
            Response.Listener {

            },
            Response.ErrorListener {
            }) {

        }
        Volley.newRequestQueue(context).add(request)
    }

    fun getReviewReq(
        cafeName: String,
        universityName: String,
        context: Context,
        success: (JSONArray?) -> Unit
    ) {
        var url = "${ip}/review/get"

        var jsonArray = JSONArray()

        var jsonObject = JSONObject()
        jsonObject.put("cafe_name", cafeName)
        jsonObject.put("univ_name", universityName)

        jsonArray.put(jsonObject)

        var request = object : JsonArrayRequest(
            Method.POST,
            url,
            jsonArray,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }) {

        }
        Volley.newRequestQueue(context).add(request)
    }

    fun getSearchReq(universityName: String, context: Context, success: (JSONArray?) -> Unit) {

        var url = "${ip}/join_room/search"

        var jsonArray = JSONArray()
        var jsonObject = JSONObject()

        jsonObject.put("univ_name", universityName)
        jsonArray.put(jsonObject)

        var request = object : JsonArrayRequest(
            Method.POST,
            url,
            jsonArray,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }) {

        }
        Volley.newRequestQueue(context).add(request)
    }

    fun getReviewsScoreReq(
        cafeName: String,
        universityName: String,
        context: Context,
        success: (String?) -> Unit
    ) {
        var url = "${ip}/review/get_average"

        var jsonObject = JSONObject()
        jsonObject.put("cafe_name", cafeName)
        jsonObject.put("univ_name", universityName)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            jsonObject,
            Response.Listener {
                success(it.getString("average"))
            },
            Response.ErrorListener {
            }) {

        }
        Volley.newRequestQueue(context).add(request)
    }

    fun getImageReq(nickname: String, context: Context, success: (JSONObject?) -> Unit) {
        var url = "http://52.78.27.41:1901/user/getImage"

        var json = JSONObject()
        json.put("nickname", nickname)


        var request = object : JsonObjectRequest(Method.POST,
            url,
            json,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }) {

        }
        Volley.newRequestQueue(context).add(request)

    }

    fun createFCMGroupReq(
        token: String,
        roomId: String,
        context: Context,
        success: (String) -> Unit
    ) {
        val url = "${ip}/join_room/fcm/create"

        val json = JSONObject()
        json.put("notification_key_name", roomId)

        json.put("token", token)

        var request = object : JsonObjectRequest(Method.POST
            , url
            , json
            , Response.Listener {
                success(it.getString("notification_key"))
            }
            , Response.ErrorListener {

            }) {}

        Volley.newRequestQueue(context).add(request)
    }

    fun getNotificationKeyReq(roomId: String, context: Context, success: (String) -> Unit) {
        val url = "${ip}/join_room/fcm/get"

        val json = JSONObject()
        json.put("notification_key_name", roomId)

        var request = object : JsonObjectRequest(Method.POST
            , url
            , json
            , Response.Listener {
                success(it.getString("notification_key"))
            }
            , Response.ErrorListener {

            }) {}

        Volley.newRequestQueue(context).add(request)
    }

    fun updateFCMGroupReq(
        operation: String,
        key: String,
        token: String,
        roomId: String,
        context: Context
    ) {
        val url = "${ip}/join_room/fcm/${operation}"

        val json = JSONObject()
        json.put("notification_key_name", roomId)
        json.put("notification_key", key)
        json.put("token", token)

        var request = object : JsonObjectRequest(Method.POST
            , url
            , json
            , Response.Listener {
            }
            , Response.ErrorListener {

            }) {}

        Volley.newRequestQueue(context).add(request)
    }

    fun sendFCMReq(roomId: String, title: String, content: String, context: Context) {
        var url = "${ip}/join_room/fcm/send"

        var json = JSONObject()
        json.put("topic", roomId)
        json.put("content", content)
        json.put("title", title)

        var request = object : JsonObjectRequest(Method.POST,
            url,
            json,
            Response.Listener {
            },
            Response.ErrorListener {
            }) {
        }

        Volley.newRequestQueue(context).add(request)
    }

    fun delectuser(id: String, context: Context, success: (String?) -> Unit) {
        var url = "${id}/user/"

        var jsonObject = JSONObject()
        jsonObject.put("id", id)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            jsonObject,
            Response.Listener {

            },
            Response.ErrorListener {

            }) {
        }
        Volley.newRequestQueue(context).add(request)
    }

    fun getJoinDating(nickname: String, context: Context, success: (JSONObject?) -> Unit) {
        var url = "${ip}/user/dating/joined"

        var jsonObject = JSONObject()
        jsonObject.put("nickname", nickname)


        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            jsonObject,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {
                Log.d("uniting","${it}")
                if(it is com.android.volley.ParseError)
                    success(null)
            }) {

        }

        Volley.newRequestQueue(context).add(request)
    }

    fun getMyPartner(nickname: String, context: Context, success: (JSONObject?) -> Unit) {
        var url = "${ip}/join_room/get_partner"

        var jsonObject = JSONObject()
        jsonObject.put("nickname", nickname)


        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            jsonObject,
            Response.Listener {
                Log.d("uniting", "VolleyService.getMyPartner it : ${it.toString()}")
                success(it)
            },
            Response.ErrorListener {
            }) {

        }

        Volley.newRequestQueue(context).add(request)

    }

    //데이팅 onoff
    fun datingOnOff(id:String,nickname: String,universityName: String,departmentName: String,birthday: String,gender: String,hobby:String,personality:String, yn: Boolean, context: Context, success: (String?) -> Unit) {
        var url = "${ip}/user/dating_on_off"

        var jsonObject = JSONObject()
        jsonObject.put("id", id)
        jsonObject.put("nickname", nickname)
        jsonObject.put("univ_name", universityName)
        jsonObject.put("dept_name", departmentName)
        jsonObject.put("birthday", birthday)
        jsonObject.put("gender", gender)
        jsonObject.put("hobby",hobby)
        jsonObject.put("personality",personality)
        if(yn==true) jsonObject.put("yn", "Y")
        else jsonObject.put("yn","N")

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            jsonObject,
            Response.Listener {
                Log.d("uniting","${it}")
                success(it.getString("result"))
            },
            Response.ErrorListener {
                Log.d("uniting","${it}")
            }) {
        }
        Volley.newRequestQueue(context).add(request)
    }

    fun getRoomInfoReq(roomId: String, context: Context, success: (JSONObject?) -> Unit) {
        var url = "${roomId}/join_room/get_room_info"

        var jsonObject = JSONObject()
        jsonObject.put("room_id", roomId)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            jsonObject,
            Response.Listener {
                success(it)
            },
            Response.ErrorListener {

            }) {
        }
        Volley.newRequestQueue(context).add(request)
    }

    fun chatAgreeReq(roomId: String?, context: Context, success: (String) -> Unit) {
        var url = "${roomId}/join_room/agree"

        var jsonObject = JSONObject()
        jsonObject.put("room_id", roomId)

        var request = object : JsonObjectRequest(
            Method.POST,
            url,
            jsonObject,
            Response.Listener {
                success(it.getString("result"))
            },
            Response.ErrorListener {
            }) {
        }
        Volley.newRequestQueue(context).add(request)
    }
}

