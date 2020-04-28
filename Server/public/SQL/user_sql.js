var pool = require('../../config/db_config');

module.exports = function () {
    return {
        get_user: function (callback) {
            pool.getConnection(function (err, con) {
                var sql=`select * from user`;
                con.query(sql,function(err,result,fields){
                    con.release();
                    if(err) return;
                    else callback(null,result);
                })
            });
        },
	    get_dating: function(nickname,gender,univ_name,callback){
		    pool.getConnection(function(err,con){
			    var sql=`select * from dating_on where user_gender <> '${gender}' AND  user_nickname <> '${nickname}' AND univ_name LIKE '${univ_name}' AND user_nickname NOT IN (select B.user_nickname from join_room as A, join_room as B where A.user_nickname='${nickname}' and A.user_nickname <> B.user_nickname and A.room_id=B.room_id and A.is_dating='true')`
			    con.query(sql,function(err,result,fields){
				    con.release();
				    if(err) callback(err,result);
				    else callback(null,result);
			    })
		    })
	    },
	    get_dating_joined: function(nickname,callback){
		    pool.getConnection(function(err,con){
			    var sql=`select * from dating_on where user_nickname='${nickname}'`

			    con.query(sql,function(err,result,fields){
				    con.release()
				    if(err) callback(err,result)
				    else callback(null,result)
			    })
		    })
	    },
        join: function(id,pw,name,birthday,gender,nickname,webMail,universityName,enterYear,departmentName,profileImage, hobby, personality){
            pool.getConnection(function(err,con){
                var sql=`insert into user values('${id}','${pw}','${name}',${birthday},'${gender}','${nickname}','${webMail}','${universityName}',${enterYear},'${departmentName}','${profileImage}', '${hobby}', '${personality}')`
                con.query(sql, function(err,result,fields){
                    con.release();
                    if(err) console.log(err);
                    else console.log("회원가입 완료");
                })
            })
        }
	    ,
	    insert_dating: function(id,nickname,universityName,departmentName,birthday,gender,hobby,personality){
		    pool.getConnection(function(err,con){
			    var sql=`insert into dating_on values('${id}','${nickname}','${universityName}','${departmentName}','${birthday}','${gender}','false','${hobby}','${personality}')`
			    con.query(sql, function(err,result,fields){
				    con.release();
				    if(err) console.log(err)
				    else console.log("데이팅 등록 완료")
			    })
		    })
	    },
	    delete_dating: function(nickname){
		    pool.getConnection(function(err,con){
			    var sql="delete from dating_on where user_nickname='"+nickname+"'"
			    con.query(sql,function(err,result,fields){
				    con.release()
				    if(err) console.log(err)
				    else console.log("데이팅 삭제 완료")
			    })
		    })
	    },
	    check: function(id,callback){
		    pool.getConnection(function(err,con){
			    var sql=`select user_id from user where user_id='${id}'`
			    con.query(sql, function(err,result,fields){
				    con.release();
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
        check_nickname: function(nickname,callback){
            pool.getConnection(function(err,con){
                var sql=`select user_nickname from user where user_nickname='${nickname}'`
                con.query(sql,function(err,result,fields){
                    con.release();
                    if(err) callback(err)
                    else callback(null,result)
                })
            })
        }
        ,
        update_user: function(pw,nickname){ /////////// 상원 : 회원정보수정 (최신화)
            pool.getConnection(function(err,con){
                var sql=`update user set user_pw='${pw}' where user_nickname='${nickname}' `;
                con.query(sql,function(err,result,field){
                    con.release();
                    if(err) console.log(err);
                    else console.log('회원정보 수정');
                })
            })
        },

        delete_user: function(id){ ////// 상원 : 회원삭제 (최신화)
            pool.getConnection(function(err,con){
                var sql=`delete from user where user_id='${id}`;
                con.query(sql,function(err,result,field){
                    con.release();
                    if(err) console.log(err);
                    else console.log('회원 탈퇴')
                })
            })
        },
	   
	    login: function(id,callback){
            pool.getConnection(function(err,con){
                var sql=`select * from user where user_id='${id}'`
                
                con.query(sql, function(err,result,fields){
                    con.release();
                    if(err) callback(err);
                    else callback(null,result);
                })
            })
        },
	get_image:function(nickname,callback){
		pool.getConnection(function(err,con){
			var sql=`select user_image from user where user_id='${nickname}'`
			con.query(sql,function(err,result,fields){
				con.release()
				if(err) console.log(err)
				else callback(null,result)
			})
		})
	},
	    get_token:function(nickname,callback){
		    pool.getConnection(function(err,con){
			    var sql=`select token from user where user_nickname='${nickname}'`
			    con.query(sql,function(err,result,fields){
				    con.release()
				    if(err) console.log(err)
				    else callback(null,result)
			    })
		    })
	    },
	    get_profile:function(nickname,callback){
		    pool.getConnection(function(err,con){
			    var sql=`select * from user where user_nickname='${nickname}'`
			    con.query(sql,function(err,result,fields){
				    con.release()
				    if(err) console.log(err)
				    else callback(null,result)
			    })
		    })
	    },
	    insert_temporary_id:function(id, universityName, departmentName){
		    pool.getConnection(function(err,con){
			    var sql=`insert into user(user_id, univ_name, dept_name) values('${id}', '${universityName}', '${departmentName}')`
			    con.query(sql, function(err, result, fields){
				    con.release()
				    if(err) console.log(err)
				    else console.log("임시아이디 삽입완료")
			    })
		    })
	    },
	    delete_temporary_id:function(id){
		    pool.getConnection(function(err, con){
			    var sql=`DELETE FROM user where user_id = '${id}'`
			    con.query(sql, function(err, result, fields){
				    con.release()
				    if(err) console.log(err)
				    else console.log("임시아이디 삭제완료")
			    })
		    })
	    },
	    insert_temporary_nickname:function(id, nickname) {
		    pool.getConnection(function(err,con){
			    var sql=`update user set user_nickname='${nickname}' where user_id='${id}'`
			    con.query(sql, function(err, result, fields){
				    con.release()
				    if(err) console.log(err)
				    else console.log("임시닉네임 삽입완료")
			    })
		    })
	    },
	    change_nickname:function(id, nickname) {
		    pool.getConnection(function(err, con){
			    var sql=`update user set_user_nickname='${nickname}' where user_id='${id}'`
			    con.query(sql, function(err, result, fields) {
				    con.release()
				    if(err) console.log(err)
				    else console.log("닉네임 변경완료")
			    })
		    })
	    },
	    check_tmp_nickname:function(nickname, callback) {
		    pool.getConnection(function(err, con){
			    var sql=`select user_nickname from tmpdata where user_nickname='${nickname}'`
			    con.query(sql, function(err, result, fields){
				    con.release()
				    if(err) console.log(err)
				    else callback(null, result)
			    })
		    })
	    },

        pool: pool
    }
};
