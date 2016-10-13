<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<html>
<head>
<jsp:useBean id="member" class="sns.member.Member"/>
	<jsp:setProperty name="member" property="*"/>
<jsp:useBean id="memberDao" class="sns.member.MemberDao"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String action = request.getParameter("action");

	if(action.equals("new")){
		if(memberDao.addMember(member))
			out.println("<script>alert('정상적으로 등록 되었습니다. 로그인 하세요!!'); window.close();</script>");
		else
			out.println("<script>alert('같은 아이디가 존재합니다!!'); history.go(-1);</script>");
	}
	if(action.equals("login")){
		if(memberDao.login(member.getUid(), member.getPasswd())){
			session.setAttribute("uid", member.getUid());
			response.sendRedirect("sns_control.jsp?action=getAll");
		}
		else{
			out.println("<script>alert('아이디나 비밀번호가 틀렸습니다!!'); history.go(-1);</script>");
		}
	}
	if(action.equals("logout")){
		session.removeAttribute("uid");
		response.sendRedirect("sns_control.jsp?action=getAll");
	}
%>
</body>
</html>