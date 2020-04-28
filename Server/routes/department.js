var express=require('express');
var router=express.Router();

var db_department=require('../public/SQL/department_sql')();

router.post('/',function(req,res,next){
	var univ_name=req.body[0].univ_name
	console.log(univ_name)
    db_department.get_department(univ_name,function(err,result){
        if(err) console.log(err);
        else res.send(result);
    })
})

module.exports=router;
