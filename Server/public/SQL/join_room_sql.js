var pool=require('../../config/db_config');
require('date-utils')


module.exports=function(){
    return {
        get_join_room_date: function(callback){
            pool.getConnection(function(err,con){
                var sql=`select * from join_room where category='date'`;
                con.query(sql,function(err,result,field){
                    con.release();
                    if(err)return;
                    callback(null,result);
                })
            })
        },

        get_join_room_study: function(callback){
            pool.getConnection(function(err,con){
                var sql=`select * from join_room where category='study'`;
                con.query(sql,function(err,result,field){
                    con.release();
                    if(err)return;
                    callback(null,result);
                })
            })
        },
        

        get_join_room_market: function(callback){
            pool.getConnection(function(err,con){
                var sql=`select * from join_room where category='market'`;
                con.query(sql,function(err,result,field){
                    con.release();
                    if(err)return;
                    callback(null,result);
                })
            })
        },


        create_dating_room: function(room_id,room_title,cate_name,maker,universityName,user,callback){
            pool.getConnection(function(err,con){
                var sql=`insert into chat_room value('${room_id}','${cate_name}','${maker}','${room_title}',2,'${universityName}',2,'','false','${user}')`;
                con.query(sql,function(err,result,field){
                    con.release()
                    if(err) callback(err)
                    else callback(null,result)
                })
            })
        },
	    create_open_room: function(roomId,roomTitle,category,maker,universityName,introduce,maxNum,callback){
		    pool.getConnection(function(err,con){
			    var sql=`insert into chat_room value('${roomId}','${category}','${maker}','${roomTitle}',${maxNum},'${universityName}',1,'${introduce}','true',null)`
			    con.query(sql,function(err,result,field){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    }
	    ,
	insert_join_room: function(room_id,user_nickname,time,isDating,callback){
		pool.getConnection(function(err,con){
			var sql=`insert into join_room value('${room_id}','${user_nickname}','${time}','${isDating}')`
			con.query(sql,function(err,result,field){
				con.release()
				if(err) callback(err)
				else callback(null,result)
			})
		})
	},
	    joined_true: function(user_nickname,callback){
		    pool.getConnection(function(err,con){
		    var sql="update dating_on set joined='true' where user_nickname='"+user_nickname+"'"
			    con.query(sql,function(err,result,field){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
	    joined_false: function(user_nickname,callback){
                    pool.getConnection(function(err,con){
                            var sql="update dating_on set joined='false' where user_nickname='"+user_nickname+"'"
                            con.query(sql,function(err,result,field){
                                    con.release()
                                    if(err) callback(err)
                                    else callback(null,result)
                            })
                    })
            },
	    get_chat_room: function(user_nickname,callback){
		    pool.getConnection(function(err,con){
			    var sql=`select chat_room.room_id, cate_name,maker,room_title,limit_num,univ_name,cur_num,introduce,chat_agree,partner  from chat_room,join_room where chat_room.room_id=join_room.room_id and join_room.user_nickname='${user_nickname}'`
			    con.query(sql,function(err,result,field){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
	    get_open_chat_room: function(universityName,category,callback){
		    pool.getConnection(function(err,con){
			    var sql
			    if(category=="전체")
				    sql=`select * from chat_room where cate_name<>'데이팅' and univ_name='${universityName}'`
			    else
				    sql=`select * from chat_room where cate_name='${category}' and univ_name='${universityName}'`
			    con.query(sql,function(err,result,field){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
		
	    },
	    get_category: function(room_id,callback){
		    pool.getConnection(function(err,con){
			    var sql=`select cate_name from chat_room where room_id='${room_id}'`
			    con.query(sql,function(err,result,field){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
	    exit_room: function(user_nickname,room_id,callback){
		    pool.getConnection(function(err,con){
			    var sql="delete from join_room where user_nickname='"+user_nickname+"' AND room_id='"+room_id+"'"
			    con.query(sql,function(err,result,field){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
	    inc_cur:function(room_id,callback){
		    pool.getConnection(function(err,con){
			    var sql="update chat_room set cur_num=cur_num+1 where room_id='"+room_id+"'"
			    con.query(sql,function(err,result,field){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    }
	    ,
	    sub_cur:function(room_id,callback){
		    pool.getConnection(function(err,con){
			    var sql="update chat_room set cur_num=cur_num-1 where room_id='"+room_id+"'"
			    con.query(sql,function(err,result,field){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
	    get_cur:function(room_id,callback){
		    pool.getConnection(function(err,con){
			    var sql="select cur_num from chat_room where room_id='"+room_id+"'"
			    con.query(sql,function(err,result,field){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
	    del_room:function(room_id,callback){
		    pool.getConnection(function(err,con){
			    var sql="delete from chat_room where room_id='"+room_id+"'"
			    con.query(sql,function(err,result,field){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    }) 
	    },
	    check_join:function(room_id,nickname,callback){
		    pool.getConnection(function(err,con){
			    var sql=`select count(*) as count from join_room where room_id='${room_id}' and user_nickname='${nickname}'`
			    con.query(sql,function(err,result,field){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
	    get_user_in_room:function(room_id,nickname,callback){
		    pool.getConnection(function(err,con){
			    var sql=`select user_nickname from join_room where room_id='${room_id}' and user_nickname<>'${nickname}'`
			    con.query(sql,function(err,result,field){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
	    get_join_time:function(roomId,nickname,callback){
		    pool.getConnection(function(err,con){
			    var sql=`select DATE_FORMAT(enter_date,"%Y-%m-%d %H:%i:%s") as enter_date from join_room where room_id='${roomId}' and user_nickname='${nickname}'`

			    con.query(sql,function(err,result,field){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
	    get_search:function(universityName,callback){
		    pool.getConnection(function(err,con){
			    var sql=`select * from chat_room where univ_name='${universityName}' AND cate_name<>'데이팅'`

			    con.query(sql,function(err,result,field){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    }
	    ,
	    get_partner:function(nickname,callback){
		    pool.getConnection(function(err,con){
			    var sql=`select B.* from join_room A, join_room B where A.room_id=B.room_id and A.user_nickname<>B.user_nickname and A.user_nickname='${nickname}' and A.is_dating='true' and B.is_dating='true'`
			    con.query(sql,function(err,result,field){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
	    get_room_info:function(roomId,callback){
		    pool.getConnection(function(err,con){
			    var sql=`select * from chat_room where room_id='${roomId}'`
			    con.query(sql,function(err,result,field){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
	    chat_agree:function(roomId,callback){
		    pool.getConnection(function(err,con){
			    var sql="update chat_room set chat_agree='true' where room_id='"+roomId+"'"
			    con.query(sql,function(err,result,fields){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
        pool:pool
    }
}
