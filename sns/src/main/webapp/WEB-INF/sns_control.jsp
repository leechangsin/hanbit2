<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="sns.message.MessageSet, sns.member.MemberDao, java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<jsp:useBean id="msg" class="sns.message.Message"/>
	<jsp:setProperty name="msg" property="*"/>
<jsp:useBean id="msgDao" class="sns.message.MessageDao"/>
<jsp:useBean id="reply" class="sns.message.Reply"/>
	<jsp:setProperty name="reply" property="*"/>

<%
	out.println("2");
	String action = request.getParameter("action");
	String cnt = request.getParameter("cnt");
	String suid = request.getParameter("suid");
	
	request.setAttribute("curmsg", request.getParameter("curmsg"));
	
	String home;
	int mcnt;
	
	if((cnt != null) && (suid != null)){
		home="sns_control.jsp?action=getAll&cnt="+cnt+"&suid="+suid;
		mcnt = Integer.parseInt(request.getParameter("cnt"));
	}
	else {
		home = "sns_control.jsp?action=getAll";
		mcnt = 5;
	}
	
	if(action.equals("newMsg")){
		if(msgDao.newMsg(msg))
			response.sendRedirect(home);
		else
			throw new Exception("메세지 등록 오류!!");
	}
	else if(action.equals("delMsg")){
		if(msgDao.delMsg(msg.getMid()))
			response.sendRedirect(home);
		else
			throw new Exception("메세지 삭제 오류!!");
	}
	else if(action.equals("newReply")){
		if(msgDao.newReply(reply))
			pageContext.forward(home);
		else
			throw new Exception("덧글 등록 오류!!");
	}
	else if(action.equals("delReply")){
		if(msgDao.delReply(reply.getRid()))
			pageContext.forward(home);
		else
			throw new Exception("덧글 삭제 오류!!");
	}
	else if(action.equals("favorite")){
		msgDao.favorite(msg.getMid());
		pageContext.forward(home);
	}
	else if(action.equals("getAll")){
		ArrayList<MessageSet> datas = msgDao.getAll(mcnt, suid);
		ArrayList<String> nusers = new MemberDao().getNewMembers();
		
		request.setAttribute("datas", datas);
		request.setAttribute("nusers", nusers);
		
		request.setAttribute("suid", suid);
		request.setAttribute("cnt", cnt);
		pageContext.forward("sns_main_ui.jsp");
	}
%>