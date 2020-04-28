var pool=require('../../config/db_config');

module.exports=function(){
    return {
        get_university:function(callback){
            pool.getConnection(function(err,con){
                var sql=`select * from university`
                con.query(sql,function(err,result){
                    if(err) callback(err,result);
                    callback(null,result);
                })
            })
        },
        pool:pool
    }
}
