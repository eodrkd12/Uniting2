var express=require('express');
var router=express.Router();

var db_post=require('../public/SQL/post_sql')();

router.get('/market',function(req,res,next){
    db_post.get_post_market(function(err,result){
        if(err) console.log(err);
        else res.send(result);
    })
})

router.get('/study',function(req,res,next){
    db_post.get_post_study(function(err,result){
        if(err) console.log(err);
        else res.send(result);
    })
})

router.get('/mypost',function(req,res,next){
    db_post.get_post_mypost(req.id,function(err,result){
        if(err) console.log(err);
        else res.send(result);
    })
})
//모든 게시글 정보 삽입으로 수정해야 함
router.post('/',function(req,res,next){
    db_post.create_post(req.category,req.number)
    res.send('success')
})

router.delete('/',function(req,res,next){
    db_post.delete_post(req.category,req.number)
    res.send('success');
})

router.put('/',function(req,res,next){
    db_post.update_post(req.category,req.number,req.content)
    res.send('success');
})

module.exports=router;
