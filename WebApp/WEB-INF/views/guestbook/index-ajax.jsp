<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-ajax.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
var isEnd = false;
var render = function(vo, mode) {
	var html = "<li data-no='" + vo.no + "'>"
	+ "<strong>" + vo.name  + "</strong>"
	+ "<p>"
	+ vo.content.replace("\n", "<br/>")
	+ "</p>"
	+ "<strong></strong>"
	+ "<a href='' data-no='" + vo.no + "'>삭제</a>"
	+ "</li>";
	
	if(mode == true) {
		$("#list-guestbook").preppend(html);
	} else {
		$("#list-guestbook").append(html);		
	}
}
var fetchList = function() {
	if(isEnd == true) { return; }
	var startNo = $("#list-guestbook li").last().data("no") || 0;
	console.log(startNo);
	
	$.ajax( {
		url: "${pageContext.servletContext.contextPath }/api/guestbook/list?no=" + startNo,
		type: "get",
		dataType: "json",
		data:"",
		success: function(response) {
			if(response.result != "success") {
				console.log(response.messgae);
				return;
			}
			
			$.each(response.data, function(index, vo){
				console.log(vo);
				render(vo, false);
			});
			
			if(response.data.length<5) {
				isEnd = true;
				$("#btn-next").prop("disabled", true);
			}
		}
	});
}

var deleteMessage = function() {
	
}

$(function() {
	$("#add-form").submit(function(event) {
		event.preventDefault();
	});
	
	$("#btn-next").click(function() {
		fetchList();
	});
	
	fetchList();
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook">
				</ul>
			</div>
			<div style="padding:20px;background-color:red;text-align:center;">
				<input id="btn-next" type="button" value="다음" style="padding:10px;" />
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p></p>
			</div>						
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>