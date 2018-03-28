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
	
	
	
})
