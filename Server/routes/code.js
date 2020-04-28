var express=require('express');
var router=express.Router();

function generateCode(){
    var code=""

    for(var i=0;i<6;i++){
        var num=Math.floor(Math.random()*10)
        code+=num
    }
    console.log("코드 생성 : "+code)
    return code;
}

router.get('/',function(req,res,next){
    var json=new Object()
    json.code=generateCode()
    
    res.send(json)
})

module.exports=router
