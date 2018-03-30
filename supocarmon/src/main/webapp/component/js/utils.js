function editExcelCode() {
	$("#excelCode").focus();
}
//运行js代码
function runExcelCode() {
	
	var startTime=new Date();
	$("#myConsole").html("")
	var codeStr = $("#excelCode").val();
	$('html,body').animate({
        scrollTop: $("#myConsole").offset().top - 60
    }, 0);
	try {
		eval(codeStr);
		$("#myConsole").css("border-color", "#5cb85c");
	} catch (e) {
		var error = "[error]\n" + e + "\n[!error]";
		$("#myConsole").css("border-color", "#ff0000");
		consoles(error);
	}
	var endTime=new Date();
	$("#runTime").text((+endTime-startTime)/1000)
	$("#excelRunCode").button('reset') ;
	
}
// 打印到我的控制台
function consoles(msg) {
	var str = $("#myConsole").html();
	if (str == '') {
		$("#myConsole").html(msg);
	} else {
		$("#myConsole").html(str + "\n" + msg);
	}
}

//----------------------------------------时间工具----------------------------------------------

//运算时间
function calcDate(){
	var calc1=$("#calc1").val();
	var calc2=$("#calc2").val();
	var calc3=$("#calc3").val();
	var calc4=$("#calc4").val();
	var calc5=$("#calc5").val();
	if(calc2==""){
		calc2=0;
	}
	if(calc3==""){
		calc3=0;
	}
	if(calc4==""){
		calc4=0;
	}
	if(calc5==""){
		calc5="00:00:00";
	}
	var sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	var date=sdf.parse(calc1);
	var timestamp=Date.parse(date);
	var calcTimestamp=0;
	var hms=calc5.split(":");
	var h=hms[0];
	var m=hms[1];
	var s=hms[2];
	calcTimestamp=(calc4*60*60*24*1000)+(calc3*60*60*24*30*1000)+(calc2*60*60*24*365*1000)+(h*60*60*1000)+(m*60*1000)+(s*1000);
	var sdf2 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	$("#calc6").val(sdf.format(new Date(timestamp-calcTimestamp)));
	$("#calc7").val(sdf2.format(new Date(timestamp+calcTimestamp)));
}

function calcDate2(){
	var calc10=$("#calc10").val();
	if(calc10==""){
		return ;
	}
	var dates=calc10.split(" 至 ");
	var date1=dates[0];
	var date2=dates[1];
	var timestamp1 = Date.parse(new Date(date1.replace(/-/g, '/')));
	var timestamp2 = Date.parse(new Date(date2.replace(/-/g, '/')));
	var timestamp=parseInt(timestamp1)-parseInt(timestamp2);
	timestamp=timestamp<0?timestamp*(-1):timestamp;
	var d=parseInt(timestamp/1000/60/60/24);
	var h=parseInt((timestamp-(d*1000*60*60*24))/1000/60/60);
	var m=parseInt((timestamp-(d*1000*60*60*24)-(h*1000*60*60))/1000/60);
	var s=parseInt((timestamp-(d*1000*60*60*24)-(h*1000*60*60)-(m*1000*60))/1000);
	
	$("#dateCalc").text("共相差 "+d+" 天 "+h+" 时 "+m+" 分 "+s+" 秒");
	
}
function tranTimestamp1(){
	var zh1=$("#zh1").val();
	var zh2=$("#zh2").val();
	var zh3=$("#zh3").val();
	var zh4=$("#zh4").val();
	//开始解析
	var sdf = new SimpleDateFormat (zh3);
	var date=sdf.parse(zh1);
	if(date=="error"){
		$("#zhtimesuccess").hide();
		$("#zhtimefail").show();
		$("#zh5").val("");
	}else{
		//验证成功
		var timestamp = Date.parse(date);
		$("#zh2").val(timestamp);
		$("#zhtimesuccess").show();
		$("#zhtimefail").hide();
		var Y=(date.getFullYear());
		var M = ((date.getMonth()+1) < 10 ? '0'+((date.getMonth()+1)) : date.getMonth()+1);
		var D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate());
		var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours());
		var m = (date.getMinutes() <10 ? '0' + date.getMinutes() : date.getMinutes());
		var s = (date.getSeconds() <10 ? '0' + date.getSeconds() : date.getSeconds());
		zh4=zh4.replace("yyyy",Y).replace("MM",M).replace("dd",D).replace("HH",h).replace("mm",m).replace("ss",s);
		$("#zh5").val(zh4);
	}
}
Date.prototype.format = function(format) {
    var date = {
           "M+": this.getMonth() + 1,
           "d+": this.getDate(),
           "H+": this.getHours(),
           "m+": this.getMinutes(),
           "s+": this.getSeconds(),
           "q+": Math.floor((this.getMonth() + 3) / 3),
           "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
           format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
           if (new RegExp("(" + k + ")").test(format)) {
                  format = format.replace(RegExp.$1, RegExp.$1.length == 1
                         ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
           }
    }
    return format;
}
var SimpleDateFormat = function (pattern)
{
	this.yyyy=pattern.indexOf("yyyy");
    this.MM=pattern.indexOf("MM");
    this.dd=pattern.indexOf("dd");
    this.HH=pattern.indexOf("HH");
    this.mm=pattern.indexOf("mm");
    this.ss=pattern.indexOf("ss");
    this.SSS=pattern.indexOf("SSS");
    this.pattern = pattern;
}
 
SimpleDateFormat.prototype.format = function (date)
{
	
	var Y=(date.getFullYear());
	var M = ((date.getMonth()+1) < 10 ? '0'+((date.getMonth()+1)) : date.getMonth()+1);
	var D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate());
	var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours());
	var m = (date.getMinutes() <10 ? '0' + date.getMinutes() : date.getMinutes());
	var s = (date.getSeconds() <10 ? '0' + date.getSeconds() : date.getSeconds());
	this.pattern=this.pattern.replace("yyyy",Y).replace("MM",M).replace("dd",D).replace("HH",h).replace("mm",m).replace("ss",s);
	return this.pattern;
}
SimpleDateFormat.prototype.dateSubStr = function (dateStr1,dateStr2,index)
{
	var obj={};
	var i1=this.pattern.indexOf(dateStr1,index);
	var i2=this.pattern.indexOf(dateStr2);
	if(i1==-1 && i2==-1){
		 throw new Error ("时间格式字符串错误");
	}
	if(i2==-1){
		return null;
	}
	var key=this.pattern.substring(i1+dateStr1.length,i2);//年和月之间的站位符
	var i3=this.pattern.indexOf(key,index);
	obj.key=key;
	obj.start=i3;
	obj.stop=i3+key.length;
	return obj;
	
}
SimpleDateFormat.prototype.toDate =function(obj,index,count){
	var result;
	//时
	if(obj==null){
		result=(this.timestamp.substring(index,index+count));
	}else{
		if(this.timestamp.substring(obj.start,obj.stop)==obj.key){
			result=this.timestamp.substring(obj.start-count,obj.start);
		}else{
			result="error"
		}
		
	}
	return result;
}
SimpleDateFormat.prototype.parse = function (str)
{
	if(str.length!=this.pattern.length){
		return "error";
	}
	var date=new Date();
	this.timestamp=str;
	if(this.yyyy!=-1){
		var ym=this.dateSubStr("yyyy","MM",this.yyyy);//年和月之间的站位符
		this.yyyy=this.toDate(ym,this.yyyy,4);
		if(this.yyyy>9999){
			return "error";
		}
	}
	if(this.MM!=-1){
		var md=this.dateSubStr("MM","dd",this.MM);//年和月之间的站位符
		this.MM=this.toDate(md,this.MM,2);
		if(this.MM>12){
			return "error";
		}
	}else{
		this.MM="00";
	}
	if(this.dd!=-1){
		var dh=this.dateSubStr("dd","HH",this.dd);//年和月之间的站位符
		this.dd=this.toDate(dh,this.dd,2);
		if(this.dd>31){
			return "error";
		}
	}else{
		this.dd="00";
	}
	if(this.HH!=-1){
		var hm=this.dateSubStr("HH","mm",this.HH);//年和月之间的站位符
		this.HH=this.toDate(hm,this.HH,2);
		if(this.HH>=24){
			return "error";
		}
	}else{
		this.HH="00";
	}
	if(this.mm!=-1){
		var ms=this.dateSubStr("mm","ss",this.mm);//分钟和秒之间的站位符
		this.mm=this.toDate(ms,this.mm,2);
		if(this.mm>=60){
			return "error";
		}
	}else{
		this.mm="00";
	}
	if(this.ss!=-1){
		var ss=this.dateSubStr("ss","SSS",this.ss);//年和月之间的站位符
		this.ss=this.toDate(ss,this.ss,2);
		if(this.ss>=60){
			return "error";
		}
	}else{
		this.ss="00";
	}
	var date=new Date(this.yyyy,this.MM-1,this.dd,this.HH,this.mm,this.ss);
	if(date=="Invalid Date"){
		return "error";
	}
	return date;
}




$(function() {

	// 失去焦点
	$("#excelCode").blur(function() {

	})
	$("#excelCode").change(function() {
		$("#myConsole").css("border-color", "#ccc");

	})
	// 点击运行代码按钮
	$("#excelRunCode").click(function() {
		$("#excelRunCode").button('loading');
		
		setTimeout(function(){
			runExcelCode();
		}, 30);
		
	})
	//----------------------------------------时间工具----------------------------------------------
		$("#zh2").val(Date.parse(new Date()));
		tranTimestamp2();
	
		//--------------end
		//zh1的值被改变
		$("#zh1").blur(function(){
			tranTimestamp1();
		})
		var timeInterval=setInterval(function(){
				var timestamp=Date.parse(new Date());
				var sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				var dateStr=sdf.format(new Date());
				$("#calc1").val(dateStr);
				$("#zh1").val(dateStr);
				var calc10Str=dateStr+" 至 "+"2020-01-01 00:00:00";
				$("#calc10").val(calc10Str);
				tranTimestamp1();
				calcDate();
				calcDate2();
		}, 1000);
	
		$("input").blur(function(){
			clearInterval(timeInterval);
		})
		
		$("input").focus(function(){
			clearInterval(timeInterval);
		})
		
		
		$("#zh2").blur(function(){
			tranTimestamp2();
		})
		
		
		
		
		function tranTimestamp2(){
			var zh4=$("#zh4").val();
			var zh3=$("#zh3").val();
			var timestamp = $("#zh2").val();
			var date = new Date(parseInt(timestamp));
			var Y=(date.getFullYear());
			var M = ((date.getMonth()+1) < 10 ? '0'+((date.getMonth()+1)) : date.getMonth()+1);
			var D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate());
			var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours());
			var m = (date.getMinutes() <10 ? '0' + date.getMinutes() : date.getMinutes());
			var s = (date.getSeconds() <10 ? '0' + date.getSeconds() : date.getSeconds());
			zh4=zh4.replace("yyyy",Y).replace("MM",M).replace("dd",D).replace("HH",h).replace("mm",m).replace("ss",s);
			zh3=zh3.replace("yyyy",Y).replace("MM",M).replace("dd",D).replace("HH",h).replace("mm",m).replace("ss",s);
			$("#zh5").val(zh4);
			$("#zh1").val(zh3);
		}
	
	
	
	
	laydate.render({
		  elem: '#zh1'
		  ,calendar: true
		  ,type: 'datetime'
		,format:$("#zh3").val()
	});
	laydate.render({
		elem: '#calc6'
			,calendar: true
			,type: 'datetime'
			,showBottom: false
	});
	laydate.render({
		elem: '#calc7'
			,calendar: true
			,type: 'datetime'
			,showBottom: false
	});
	laydate.render({
		elem: '#calc1'
			,calendar: true
			,type: 'datetime'
			
	});
	//限定可选时间
	laydate.render({
	  elem: '#calc5'
	  ,type: 'time'
	  ,btns: ['clear', 'confirm']
	}); 
	
	laydate.render({
		  elem: '#calc10'
		  ,type: 'datetime'
		  ,calendar: true
		  ,range: '至'
		  ,format: 'yyyy-MM-dd HH:mm:ss'
		}); 
})
