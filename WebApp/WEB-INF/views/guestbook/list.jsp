<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="java.util.List"%>
<% pageContext.setAttribute( "newLine", "\n" ); %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<form action="${pageContext.request.contextPath }/guestbook/insert" method="post">
					<input type="hidden" name="a" value="add" />		
					<table>
						<tr>
							<td>이름</td><td><input type="text" name="name"></td>
							<td>비밀번호</td><td><input type="password" name="password"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" id="content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>
				<ul>
					<c:set var="totalCount" value="${fn:length(list) }"/>
					<c:forEach items="${list }" var="vo" varStatus="status" >
						<li>
							<table>
								<tr>
									<td>[${totalCount - status.index }]</td>
									<td>${vo.name }</td>
									<td>${vo.regDate }</td>
									<td><a href="${pageContext.request.contextPath }/guestbook/delete/${vo.no }">삭제</a></td>
								</tr>
								<tr>
									<td colspan=4>
									${fn:replace(vo.content, newLine, "<br>") }
									</td>
								</tr>
							</table>
							<br>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>