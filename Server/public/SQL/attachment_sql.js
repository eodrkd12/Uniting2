var pool=require('../../config/db_config');

module.exports=function(){
    return {
        get_attachment:function(category,number,callback){
            pool.getConnection(function(err,con){
                var sql=`select * from attachment where category='${category}' AND number=${number}`
                con.query(sql,function(err,result){
                    if(err) return;
                    callback(null,result);
                })
            })
        },  
        pool:pool
    }
}