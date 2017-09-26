<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<script src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js" type="text/javascript"></script>
<script>
var FormValidator = {
	$buttonCheckEmail: null,
	$inputTextEmail: null,
	$imgCheck: null,
	
	init:function () {
		this.$inputTextEmail = $("#email");
		this.$buttonCheckEmail = $("#btn-checkemail");
		this.$imgCheck = $("#img-check");
		
		this.$inputTextEmail.change(this.onInputTextEmailChanged.bind(this));
		this.$buttonCheckEmail.click(this.onButtonCheckEmailClicked.bind(this));
		$("#join-form").submit(this.onJoinFormSubmit.bind(this));
	},
	onJoinFormSubmit: function() {
		console.log("onJoinFormSubmit");
		
		var $inputTextName = $("#name");
		if($inputTextName.val() === '') {
			alert("이름은 필수다.");
			$inputTextName.focus();
			return false;
		}
		
		if(this.$inputTextEmail.val() === '') {
			alert("이메일 필수다.");
			this.$inputTextEmail.focus();
			return false;
		}
		
		if(this.$imgCheck.is(":visible") == false) {
			alert("중복체크 필수다.");
			return false;
		}
		
		var $inputPassword = $("#password");
		if($inputPassword.val() === '') {
			alert("비밀번호 필수다.");
			$inputPassword.focus();
			return false;
		}
		
		var $inputCheckAgree = $("#agree-prov");
		if($inputCheckAgree.is(":checked") == false) {
			alert("약관동의 필수다.");
			$inputCheckAgree.focus();
			return false;
		}
		
		return true;
	},
	onInputTextEmailChanged: function() {
		this.$imgCheck.hide();
		this.$buttonCheckEmail.show();
	},
	onCheckEmailAjaxSuccess: function(response) {
		if(response.result != "success"){
			console.log( response.message );
			return;
		}
		
		if( response.data == true ) {
			alert( "이미 사용하고 있는 email입니다." );
			this.$inputTextEmail.
			val( "" ).
			focus();
			return;
		}
		
		this.$imgCheck.show();
		this.$buttonCheckEmail.hide();
	},
	onCheckEmailAjaxError: function(xhr, stauts, e) {
		console.error( status + ":" + e );
	},	
	onButtonCheckEmailClicked: function() {		
		var email = this.$inputTextEmail.val();
		if( email == "" ) {
			return;
		}
		
		//ajax 통신
		$.ajax( {
			url: "${pageContext.servletContext.contextPath }/api/user/checkemail?email=" + email,
			type: "get",
			dataType: "json",
			data:"",
			success: this.onCheckEmailAjaxSuccess.bind(this),
			error: this.onCheckEmailAjaxError
		});
	}
}

$( function(){
	FormValidator.init();
});
</script>
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">

				<form id="join-form" name="joinForm" method="post" action="${pageContext.request.contextPath }/user/join">
					<label class="block-label" for="name">이름</label>
					<input id="name" name="name" type="text" value="">

					<label class="block-label" for="email">이메일</label>
					<input id="email" name="email" type="text" value="">
					<img id="img-check" src="${pageContext.servletContext.contextPath }/assets/images/check-ok.png" style="display:none; width:24px"/>
					<input id="btn-checkemail" type="button" value="중복체크">
					
					<label class="block-label">패스워드</label>
					<input id="password" name="password" type="password" value="">
					
					<fieldset>
						<legend>성별</legend>
						<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
						<label>남</label> <input type="radio" name="gender" value="male">
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
					
				</form>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>