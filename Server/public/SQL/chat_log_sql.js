var pool=require('../../config/db_config');

module.exports=function(){
    return {
        get_chat: function(category,room_num,user_id,callback){
            pool.get_chat(function(err,con){
                var sql=`select * from chat_log where category='${category}' AND room_num='${room_num}' AND user_id='${user_id}'`;
                con.query(sql,function(err,result,field){
                    con.release();
                    if(err)return;
                    callback(null,result);
                })
            })
        },
        insert_chat: function(content,image){
            pool.getConnection(function(err,con){
                var sql=`insert into user values('${content}','${image}')`
                con.query(sql,function(err,result,field){
                    con.release();
                    if(err) console.log(err);
                    else console.log("채팅 입력 완료");
                    
                })
            })
        }
        ,
        pool:pool
    }
}
