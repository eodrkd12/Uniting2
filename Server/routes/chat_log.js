var express=require('express');
var router=express.Router();

var db_chat_log=require('../public/SQL/chat_log_sql');

router.get('/chat_log',function(req,res,next){
    db_chat_log.get_chat(req.category,req.room_num,req.user_id,function(err,result){
        if(err) console.log(err);
        else res.send(result);
    })
})

router.post('/chat_log',function(req,res,next){
    db_chat_log.insert_chat(req.content,req.image);
    res.send("success");
    
})
module.exports=router;