var express=require('express');
var moment=require('moment');
require('moment-timezone');
moment.tz.setDefault("Asia/Seoul");

var router=express.Router();

var db_join_room=require('../public/SQL/join_room_sql')();
var db_user=require('../public/SQL/user_sql')();
var request=require('request')

var admin=require('firebase-admin')
var serviceAccount=require("../../realtimechatting-70acf-firebase-adminsdk-34z0s-ed88f96e29.json")

admin.initializeApp({
                credential: admin.credential.cert(serviceAccount),
                databaseURL: 'https://realtimechatting-70acf.firebaseio.com'
        })


router.get('/date',function(req,res,next){
    db_join_room.get_join_room_date(function(err,result){
        if(err) console.log(err);
        else {
            console.log("데이팅 조회 성공");
            res.send(result);
        }
    })
})

router.get('/study',function(req,res,next){
    db_join_room.get_join_room_study(function(err,result){
        if(err) console.log(err);
        else {
            console.log("스터디 조회 성공");
            res.send(result);
        }
    })
})

router.get('/market',function(req,res,next){
    db_join_room.get_join_room_study(function(err,result){
        if(err) console.log(err);
        else {
            console.log("장터 조회 성공");
            res.send(result);
        }
    })
})
///////////////////////////////////////// 
router.post('/',function(req,res,next){//채팅방 생성
	var cate_name=req.body.cate_name
	var maker=req.body.maker
	var user=req.body.user
	var universityName=req.body.univ_name
        var time=moment().format('YYYY-MM-DD_HH-mm-ss')

	var code=""

	for(var i=0;i<10;i++){
	    	var num=Math.floor(Math.random()*10)
	        code+=num
	}

        var room_id=`${time}-${code}`
	var room_title=`데이팅`

	db_user.get_token(user,function(err,result){
		if(err) console.log(err)
		else{
			console.log(result)
			var registrationTokens=[result[0].token]

			admin.messaging().subscribeToTopic(registrationTokens, room_id).then(function(response){
				console.log('success subscribeToTopic : ', response)
			}).catch(function(error){
				console.log('error subscribeToTopic : ',error)
			})
		}
	})


    	db_join_room.create_dating_room(room_id,room_title,cate_name,maker,universityName,user,function(err,result){
	    if(err) console.log(err)
	    else{
		    db_join_room.insert_join_room(room_id,maker,time,'true',function(err,result){
			    if(err) console.log(err)
			    else{
				    db_join_room.insert_join_room(room_id,user,time,'true',function(err,result){
					    if(err) console.log(err)
					    else{
						    db_join_room.joined_true(maker,function(err,result){
							    if(err) console.log(err)
							    else{
								    db_join_room.joined_true(user,function(err,result){
									    if(err) console.log(err)
									    else{
										    var object=new Object()
										    object.room_id=room_id
										    res.send(object)
									    }
								    })
							    }
						    })
					    }
				    })
			    }
		    })
	    }
    })
})

router.post('/open_chat',function(req,res,next){
	var category=req.body.category
	var maker=req.body.maker
	var universityName=req.body.univ_name
	var time=moment().format('YYYY-MM-DD_HH-mm-ss')

	var code=""

	for(var i=0;i<6;i++){
        	var num=Math.floor(Math.random()*10)
	        code+=num
	}

	var roomId=`${time}-${code}`
	var roomTitle=req.body.room_title
	var maxNum=req.body.max_num
	var introduce=req.body.introduce

	db_join_room.create_open_room(roomId,roomTitle,category,maker,universityName,introduce,maxNum,function(err,result){
		if(err) console.log(err)
		else{
			db_join_room.insert_join_room(roomId,maker,time,'false',function(err,result){
				if(err) console.log(err)
				else{
					var object=new Object()
		                        object.room_id=roomId
		                        res.send(object)
				}
			})
		}
	})
})

router.post('/get_partner',function(req,res,next){
	var nickname=req.body.nickname

	db_join_room.get_partner(nickname,function(err,result){
		if(err) console.log(err)
		else{
			console.log(result)
			var partner=result[0].user_nickname
			var object=new Object()
			object.room=result
			db_user.get_profile(partner,function(err,resultPartner){
				if(err) console.log(err)
				else {
					object.partner=resultPartner
					const buf=resultPartner[0].user_image
		                        var str=buf.toString()
		                        object.image=str

					res.send(object)
				}
			})
		}
	})
})

router.post('/join_room',function(req,res,next){
	var roomId=req.body.room_id
	var nickname=req.body.user
	var time=moment().format('YYYY-MM-DD HH:mm:ss')


	db_join_room.insert_join_room(roomId,nickname,time,function(err,result){
		if(err) console.log(err)
		else{
			db_join_room.inc_cur(roomId,function(err,result){
				if(err) console.log(err)
				else{
					var object=new Object()
					object.result="success"
					res.send(object)
				}
			})
		}
	})
})

router.post('/check_join',function(req,res,next){
	var roomId=req.body.room_id
	var nickname=req.body.nickname

	db_join_room.check_join(roomId,nickname,function(err,result){
		if(err) console.log(err)
		else{
			var object=new Object()
			if(result[0].count==1){
				object.result="true"
			}
			else{
				object.result="false"
			}
			res.send(object)
		}
	})
})

router.post('/my_chat_room',function(req,res,next){
	var nickname=req.body[0].nickname
	db_join_room.get_chat_room(nickname,function(err,result){
		if(err) console.log(err)
		else res.send(result)
	})
})

router.post('/open_chat_room',function(req,res,next){
	var universityName=req.body[0].univ_name
	var category=req.body[0].category

	console.log(`${universityName} : ${category}`)

	db_join_room.get_open_chat_room(universityName,category,function(err,result){
		if(err) console.log(err)
		else {
			console.log(result)
			res.send(result)
		}
	})
})

router.post('/get_room_info',function(req,res,next){
	var roomId=req.body.room_id

	db_join_room.get_room_info(roomId,function(err,result){
		if(err) console.log(err)
		else{
			res.send(result[0])
		}
	})
})

router.post('/exit',function(req,res,next){
	var roomId=req.body.room_id
	var nickname=req.body.nickname
	
	db_join_room.exit_room(nickname,roomId,function(err,result){
		if(err) console.log(err)
		else{
			db_join_room.sub_cur(roomId,function(err,result){
				if(err) console.log(err)
				else{
					db_join_room.get_cur(roomId,function(err,result){
						if(err) console.log(err)
						else{
							console.log(result[0].cur_num)
							if(result[0].cur_num<1){
								db_join_room.del_room(roomId,function(err,result){
									if(err) console.log(err)
									else{
										var object=new Object()
										object.result='delete_room'
										res.send(object)
									}
								})
							}
							else{
								var object=new Object()
								object.result='exit'
								res.send(object)
							}
						}
					})
				}
			})
		}		
	})
})

router.post('/exit/dating',function(req,res,next){
	var nickname=req.body.nickname
	db_join_room.joined_false(nickname,function(err,result){
		if(err) console.log(err)
		else{
			var object=new Object()
			object.result='ok'
			res.send(object)
		}
	})
})

router.post('/user_in_room',function(req,res,next){
	
	var roomId=req.body[0].room_id
	var nickname=req.body[0].nickname
	
	db_join_room.get_user_in_room(roomId,nickname,function(err,result){
		if(err) console.log(err)
		else{
			res.send(result)
		}
	})
})

router.post('/get_join_time',function(req,res,next){
	var roomId=req.body.room_id
	var nickname=req.body.nickname

	db_join_room.get_join_time(roomId,nickname,function(err,result){
		if(err) console.log(err)
		else{
			res.send(result[0])
		}
	})
})


router.post('/search',function(req,res,next){
	var universityName = req.body[0].univ_name

	db_join_room.get_search(universityName,function(err,result){
		if(err) console.log(err)
		else{
			res.send(result)
		}
	})
})

router.post('/fcm/create',function(req,res,next){

	var registration_ids=[req.body.token]
	console.log(registration_ids)

	var options = {
                method: 'post',
                url: 'https://fcm.googleapis.com/fcm/notification',
		headers: {
                        'Content-Type':'application/json',
                        'Authorization':'key=AAAAUTWRvps:APA91bHfWoTIhtP8NSwSsv31WVlZJDnHyAgC8ADTjBdnHbufN7o34wE1qjEK5T3yRHOdlHoJUZL_jpy4_EKsTnJX0UgoLZJyDYBPGpPMgoAijgEIwKQllI88d5XPxWC-gSKWUyrQReA0',
                        'project_id':'348791094939'
                },
                json:{
                       "operation":"create",
                       "notification_key_name":req.body.notification_key_name,
                       "registration_ids":registration_ids
                }
        }

	request(options, function(err,response,body){
		console.log(body)
		res.send(body)
	})
});


router.post('/fcm/get',function(req,res,next){

	console.log(req.body.notification_key_name)

        var options = {
                method: 'get',
                url: 'https://fcm.googleapis.com/fcm/notification?notification_key_name='+req.body.notification_key_name,
                headers: {
                        'Content-Type':'application/json',
                        'Authorization':'key=AAAAUTWRvps:APA91bHfWoTIhtP8NSwSsv31WVlZJDnHyAgC8ADTjBdnHbufN7o34wE1qjEK5T3yRHOdlHoJUZL_jpy4_EKsTnJX0UgoLZJyDYBPGpPMgoAijgEIwKQllI88d5XPxWC-gSKWUyrQReA0',
                        'project_id':'348791094939'
                },
                json:{}
        }

        request(options, function(err,response,body){
                console.log(body)
                res.send(body)
        })
})

router.post('fcm/add',function(req,res,next){

	var registration_ids=[req.body.token]
        console.log(registration_ids)

        var options = {
                method: 'post',
                url: 'https://fcm.googleapis.com/fcm/notification',
                headers: {
                        'Content-Type':'application/json',
                        'Authorization':'key=AAAAUTWRvps:APA91bHfWoTIhtP8NSwSsv31WVlZJDnHyAgC8ADTjBdnHbufN7o34wE1qjEK5T3yRHOdlHoJUZL_jpy4_EKsTnJX0UgoLZJyDYBPGpPMgoAijgEIwKQllI88d5XPxWC-gSKWUyrQReA0',
                        'project_id':'348791094939'
                },
                json:{
                       "operation":"add",
                       "notification_key_name":req.body.notification_key_name,
  			"notofication_key":req.body.notification_key,
                       "registration_ids":registration_ids
                }
        }

        request(options, function(err,response,body){
                console.log(body)
                res.send(body)
        })
})

router.post('fcm/remove',function(req,res,next){

	var registration_ids=[req.body.token]
        console.log(registration_ids)

        var options = { 
                method: 'post',
                url: 'https://fcm.googleapis.com/fcm/notification',
                headers: {
                        'Content-Type':'application/json',
                        'Authorization':'key=AAAAUTWRvps:APA91bHfWoTIhtP8NSwSsv31WVlZJDnHyAgC8ADTjBdnHbufN7o34wE1qjEK5T3yRHOdlHoJUZL_jpy4_EKsTnJX0UgoLZJyDYBPGpPMgoAijgEIwKQllI88d5XPxWC-gSKWUyrQReA0',
                        'project_id':'348791094939'
                },
                json:{
                       "operation":"remove",
                       "notification_key_name":req.body.notification_key_name,
                        "notofication_key":req.body.notification_key,
                       "registration_ids":registration_ids
                }
        }       

        request(options, function(err,response,body){
                console.log(body)
                res.send(body)
        })

})

router.post('/fcm/send',function(req,res,next){

	var topic = req.body.topic
	var content = req.body.content
	var title=req.body.title

	if(title=="CHAT_REQUEST")  title="대화 요청"
        else if(title=="CHAT_AGREE") title="요청 수락"
        else if(title=="CHAT_DISAGREE") title="요청 거부"
        
	var message={
		notification : {
			body : content,
			title : title
		},
		topic : topic

	}

	console.log(title)

	admin.messaging().send(message)
		.then((response) => {
			//Response is a message ID string
			console.log('Successfully sent message:', response)
			var object=new Object()
			object.result=response
			res.send(object)
		})
		.catch((error) => {
			console.log('Error sending message:', error)
			var object=new Object()
			object.result=error
			res.send(object)
		})
})

router.post('/agree',function(req,res,next){
	var roomId=req.body.room_id

	db_join_room.chat_agree(roomId,function(err,result){
		if(err){
			console.log(err)
		}
		else{
			var object=new Object()
			object.result="수락"
			res.send(object)
		}
	})
})

module.exports=router;
