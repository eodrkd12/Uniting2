var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

var indexRouter = require('./routes/index');
var userRouter = require('./routes/user');
var join_roomRouter = require('./routes/join_room');
var postRouter = require('./routes/post'); //라우터 파일 참조
var attachmentRouter=require('./routes/attachment');
var codeRouter=require('./routes/code');
var universityRouter=require('./routes/university');
var departmentRouter=require('./routes/department');
var reviewRouter=require('./routes/review');
var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(logger('dev'));
app.use(express.json({limit: 5000000}));
app.use(express.urlencoded({limit: 5000000, extended: true, parameterLimit: 50000 }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));



app.use('/', indexRouter);
app.use('/user', userRouter);
app.use('/join_room',join_roomRouter);
app.use('/post',postRouter); // 라우터에 url 주소 지정
app.use('/attachment',attachmentRouter);
app.use('/code',codeRouter);
app.use('/university',universityRouter);
app.use('/department',departmentRouter);
app.use('/review',reviewRouter);
// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
