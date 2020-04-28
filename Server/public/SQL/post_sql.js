var pool=require('../../config/db_config');

module.exports=function(){
    return {
        get_post_market: function(callback){
            pool.getConnection(function(err,con){
                var sql=`select * from post where category='market'`;
                con.query(sql,function(err,result,field){
                    con.release();
                    if(err)return;
                    callback(null,result);
                })
            })
        },
        get_post_study: function(callback){
            pool.getConnection(function(err,con){
                var sql=`select * from post where category='study'`;
                con.query(sql,function(err,result,field){
                    con.release();
                    if(err)return;
                    callback(null,result);
                })
            })
        },
        get_post_mypost: function(id,callback){
            pool.getConnection(function(err,con){
                var sql=`select * from post where writer='${id}'`;
                con.query(sql,function(err,result,field){
                    con.release();
                    if(err)return;
                    callback(null,result);
                })
            })
        },
        //임시로 카테고리와 방번호만 insert함 나중에는 모든 정보를 삽입해야 함
        create_post: function(category, number){
            pool.getConnection(function(err,con){
                var sql=`insert into post(category,number) value('${category}',${number})`;
                con.query(sql,function(err,result,field){
                    con.release();
                    if(err) console.log(err);
                    else console.log('게시글 생성');
                })
            })
        },
        delete_post: function(category,number){
            pool.getConnection(function(err,con){
                var sql=`delete from post where category='${category}' AND number=${number}`;
                con.query(sql,function(err,result,field){
                    con.release();
                    if(err) console.log(err);
                    else console.log('게시글 삭제')
                })
            })
        },
        update_post: function(category,number,content){
            pool.getConnection(function(err,con){
                var sql=`update post set content='${content} where category='${category} AND number=${number}`;
                con.query(sql,function(err,result,field){
                    con.release();
                    if(err) console.log(err);
                    else console.log('게시물 수정');
                })
            })
        },
        pool:pool
    }
}