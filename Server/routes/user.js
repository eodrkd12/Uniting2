var express = require('express');
var router = express.Router();

var db_user = require('../public/SQL/user_sql')();

var moment=require('moment');
require('moment-timezone');
moment.tz.setDefault("Asia/Seoul");

router.get('/', function(req, res, next) {
  db_user.get_user(function(err,result){
    if(err) console.log(err);
    else res.send(result);
  })
});

router.get('/data', function(req, res) {
  db_user.data(function(err, result) {
    if(err) console.log(err)
    else res.send(result)
  })
})


router.post('/dating', function(req, res, next) {
  db_user.get_dating(req.body[0].nickname,req.body[0].gender,req.body[0].univ_name,function(err,result){
    if(err) console.log(err);
    else res.send(result);
  })
});

router.post('/dating/joined', function(req,res,next){

	db_user.get_dating_joined(req.body.nickname,function(err,result){
		if(err)
			console.log(err)
		else {
			res.send(result[0])
		}
	})
})

router.post('/login', function(req,res,next){
  db_user.login(req.body.id,function(err,result){
    if(err) console.log(err);
    else {
      var data=result[0]
      res.send(data);
    }
  })
})

router.post('/check', function(req,res,next){
  db_user.check(req.body.id,function(err,result){
    if(err) console.log(err)
    else 
      res.send(result[0])
  })
})
router.post('/check/nickname', function(req,res,next){
  db_user.check_nickname(req.body.nickname,function(err,result){
    if(err) console.log(err)
    else 
      res.send(result[0])
  })
})

router.post('/', function(req,res,next){
	var id=req.body.id
	var pw=req.body.pw
	var name=req.body.name
	var birthday=req.body.birthday
	var gender=req.body.gender
	var nickname=req.body.nickname
	var webMail=req.body.web_mail
	var universityName=req.body.university_name
	var departmentName=req.body.department_name
	var enterYear=req.body.enter_year
	var image=req.body.image

	db_user.join(id,pw,name,birthday,gender,nickname,webMail,universityName,enterYear,departmentName,image)
	db_user.insert_dating(id,nickname,universityName,departmentName,birthday,gender)

	var json=new Object()
	json.result="success"
	res.send(json)
})

router.post('/image', function(req,res,next){
	var image=req.body.image

	db_user.image(image)

	res.send("success")
})

router.put('/',function(req,res,next){ // 상원 회원정보수정 (최신화)
  db_user.update_user(req.body.pw,req.body.nickname)
  res.send('success');
})

router.delete('/',function(req,res,next){ // 상원 회원정보 삭제 (최신화)
  db_user.delete_user(req.body.id)
  res.send('success');
})

router.post('/getImage',function(req,res,next){
	var nickname=req.body.nickname
	console.log("닉네임" + nickname)
	db_user.get_image(nickname,function(err,result){
		if(err) console.log(err)
		else{
			console.log(result[0])
			const buf=result[0].user_image
			var str=buf.toString()
			
			var object=new Object()
			object.user_image=str
			res.send(object)
		}
	})
})

router.post('/dating_on_off',function(req,res,next){
	var id=req.body.id
	var nickname=req.body.nickname
	var universityName=req.body.univ_name
	var departmentName=req.body.dept_name
	var birthday=req.body.birthday
	birthday=birthday.substr(0,10)
	var gender=req.body.gender
	var hobby=req.body.hobby
	var personality=req.body.personality
	var yn=req.body.yn

	

	var object=new Object()
	if(yn==="Y"){
		db_user.insert_dating(id,nickname,universityName,departmentName,birthday,gender,hobby,personality)
		object.result="On"
	}
	else{
		db_user.delete_dating(nickname)
		object.result="Off"
	}
	res.send(object)
})

router.post('/insertPersonality', function(req, res, next) {
	var personality = req.body.personality
	console.log(personality)
	db_user.insert_personality(personality)

	res.send("success")
})

router.post('/insert/id', function(req, res, next) {
	var id=req.body.id
	var universityName=req.body.univ_name
	var departmentName=req.body.dept_name

	db_user.insert_id(id, universityName, departmentName)

	res.send("success")
})

router.post('/delete/id', function(req, res, next) {
	var id=req.body.id

	db_user.delete_id(id)

	res.send("success")
})

router.post('/insert/nickname', function(req, res, next) {
	var id=req.body.id
	var nickname=req.body.nickname

	db_user.insert_nickname(id, nickname)

	res.send("success")
})

router.post('/change/deptname', function(req, res, next) {
	var id=req.body.id
	var departmentName=req.body.dept_name

	db_user.change_deptname(id, departmentName)
//	db_user.change_nickname_in_dating_on(id,nickname)

	res.send("success")
})

router.post('/change/hobby', function(req, res, next) {
	var id=req.body.id
	var hobby=req.body.hobby

	db_user.change_hobby(id, hobby)

	res.send("success")
})

router.post('/change/personlity', function(req, res, next) {
	var id=req.body.id
	var personality=req.body.personality

	db_user.change_personality(id, personality)

	res.send("success")
})

router.post('/insert/tmp/nickname', function(req, res, next) {
	var nickname=req.body.nickname

	db_user.insert_tmp_nickname(nickname)

	res.send("success")
})

router.post('/check/tmp/nickname', function(req, res, next) {
	var id=req.body.id
	var nickname=req.body.nickname

	db_user.check_tmp_nickname(nickname, function(err, result){
		if(err) console.log(err)
		else res.send(result[0])
	})
})

router.post('/delete/tmp/nickname', function(req,res,next){
	var nickname=req.body.nickname

	db_user.delete_tmp_nickname(nickname)

	res.send("success")
})

router.post('/update/image',function(req,res,next){
	var nickname=req.body.nickname
	var bitmap=req.body.bitmap

	db_user.update_image(nickname,bitmap)

	var object=new Object()
	object.result="success"
	res.send(object)
})

router.post('/remove/token',function(req,res,next){
	var nickname=req.body.nickname

	db_user.remove_token(nickname)

	var object=new Object()
	object.result="success"
	res.send(object)
})

router.post('/insert/token',function(req,res,next){
	var nickname=req.body.nickname
	var token=req.body.token
	db_user.insert_token(nickname,token)

	var object=new Object()
	object.result="success"
	res.send(object)
})

module.exports = router;
