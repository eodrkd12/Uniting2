var express=require('express');
var router=express.Router();

var db_comment=require('../public/SQL/comment_sql')();

router.get('/',function(req,res,next){
    db_comment.get_comment(req.category,req.post_num,function(err,result){
        if(err) console.log(err);
        else res.send(result);
    })
})

module.exports=router;