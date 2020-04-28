var express = require('express');

var moment=require('moment');
require('moment-timezone');
moment.tz.setDefault("Asia/Seoul");

var router = express.Router();

var db_review = require('../public/SQL/review_sql')();

router.post('/get', function(req,res,next){
	var cafe_name=req.body[0].cafe_name
	var univ_name=req.body[0].univ_name

	db_review.get_review(cafe_name,univ_name,function(err,result){
		if(err) console.log(err)
		else res.send(result)
	})
})

router.post('/insert', function(req,res,next){
	var nickname=req.body.nickname
	var date=moment().format('YYYY-MM-DD HH:mm:ss')
	var cafe_name=req.body.cafe_name
	var univ_name=req.body.univ_name
	var point=req.body.point
	var content=req.body.content

	db_review.insert_review(nickname,date,cafe_name,univ_name,point,content,function(err,result){
		if(err) console.log(err)
		else{
			var object=new Object()
			object.result="success"
			res.send(object)
		}
	})
})

router.post('/update', function(req,res,next){	
	var nickname=req.body.nickname
	var date=req.body.date
	var cafe_name=req.body.cafe_name
	var univ_name=req.body.univ_name
	var point=req.body.point
	var content=req.body.content

	db_review.update_review(nickname,date,cafe_name,univ_name,point,content,function(err,result){
		if(err) console.log(err)
		else{
			var object=new Object()
			object.result="success"
			res.send(object)
		}
	})
})

router.post('/delete', function(req,res,next){
	var nickname=req.body.nickname
	var date=req.body.date
	var cafe_name=req.body.cafe_name
	var univ_name=req.body.cafe_name

	db_review.delete_review(nickname,date,cafe_name,univ_name,function(err,result){
		if(err) console.log(err)
		else{
			var object=new Object()
			object.result="success"
			res.send(object)
		}
	})
})

router.post('/get_my_review', function(req,res,next){
	var nickname=req.body[0].nickname
	
	db_review.get_my_review(nickname,function(err,result){
		if(err) console.log(err)
		else res.send(result)
	})
})

router.post('/get_average', function(req,res,next){
	var cafe_name=req.body.cafe_name
	var univ_name=req.body.univ_name

	db_review.get_average_review(cafe_name, univ_name, function(err, result){
		if(err) console.log(err)
		else{
			var object= new Object()
			object.average=result[0].avg
			res.send(object)
		}
	})
})

module.exports = router;
