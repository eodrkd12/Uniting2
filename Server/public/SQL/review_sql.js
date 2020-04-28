var pool=require('../../config/db_config');
  
module.exports=function(){
    return {
            get_review : function(cafe_name,univ_name,callback){
		    pool.getConnection(function(err,con){
			    var sql=`select * from review where cafe_name='${cafe_name}' and univ_name='${univ_name}'`
			    con.query(sql,function(err,result,fields){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
	    insert_review : function(nickname,date,cafe_name,univ_name,point,content,callback){
		    pool.getConnection(function(err,con){
			    var sql=`insert into review value('${nickname}','${date}','${cafe_name}','${univ_name}',${point},'${content}')`
			    con.query(sql,function(err,result,fields){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
	    update_review : function(nickname,date,cafe_name,univ_name,point,content,callback){
		    pool.getConnection(function(err,con){
			    var sql=`update review set content='${content}', point=point where user_nickname='${nickname}' and date='${date}' and cafe_name='${cafe_name}' and univ_name='${univ_name}'`
			    con.query(sql,function(err,result,fields){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
	    delete_review : function(nickname,date,cafe_name,univ_name,callback){
		    pool.getConnection(function(err,con){
			    var sql="delte from review where nickname='"+nickname+"' and date='"+date+"'cafe_name='"+cafe_name+"'univ_name='"+univ_name+"'"
			    con.query(sql,function(err,result,fields){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
	    get_my_review : function(nickname,callback){
		    pool.getConnection(function(err,con){
			    var sql=`select * from review where nickname='${nickname}'`
			    con.query(sql,function(err,result,fields){
				    con.release()
				    if(err) callback(err)
				    else callback(null,result)
			    })
		    })
	    },
	    get_average_review : function(cafe_name, univ_name, callback){
		    pool.getConnection(function(err, con){
			    var sql=`select avg(point) as avg from review where cafe_name='${cafe_name}' and univ_name='${univ_name}'`
			    con.query(sql, function(err, result, fields){
				    con.release()
				    if(err) callback(err)
				    else callback(null, result)
			    })
		    })
	    },
	    pool : pool
    }
}
