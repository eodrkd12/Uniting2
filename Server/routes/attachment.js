var express=require('express');
var router=express.Router();

var db_attachment=require('../public/SQL/attachment_sql');

router.get('/',function(req,res,next){
    db_attachment.get_attachment(req.category,req.number,function(err,result){
        if(err) console.log(err);
        else res.send(result);
    })
})

module.exports=router;