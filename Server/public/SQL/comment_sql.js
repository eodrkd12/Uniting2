var pool=require('../../config/db_config');

module.exports=function(){
    return {
        get_comment:function(category,post_num,callback){
            pool.getConnection(function(err,con){
                var sql=`select * from comment where category='${category}' AND post_num=${post_num}`
                con.query(sql,function(err,result){
                    if(err) return;
                    callback(null,result);
                })
            })
        },  
        pool:pool
    }
}