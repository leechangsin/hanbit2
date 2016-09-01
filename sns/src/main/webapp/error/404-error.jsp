<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SNS:404-error</title>
</head>
<jsp:useBean id="now" class="java.util.Date"/>
<body>
	<div align=center>
		<h2>MySns:404-error 발생!!</h2>
		<hr>
		
		<table>
			<tr width=100% bgcolor="pink">
				<td>
					요청하신 파일을 찾을 수 없습니다.<br>
					URL 주소를 다시한번 확인해 주세요!!
				</td>
			</tr>
			<tr>
				<td>
					${now }<p>
					요청 실패 URI : ${pageContext.errorData.requestURI }<br>
					상태코드 : ${pageContext.errorData.statusCode }
				</td>
			</tr>
		</table>
	</div>
</body>
</html>