<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="sns"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>창신's SNS</title>
<link rel="stylesheet" href="css/styles.css" type="text/css" media="screen" />
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script>
	$(function() {
		$("#accordion").accordion({
			heightStyle : "content",
		});
	});

	function newuser() {
		window.open("new_user.jsp", "newuser", "titlebar=no, location=no, scrollbars=no, resizeable=no, menubar=no, toolbar=no, width=300, height=240");
	}
	
	function modify(){
		alert("아직 구현되지 않은 기능입니다. 조금만 기다려주세요.");
	}
</script>

</head>

<body>
	<header>
		<div class="container1">
			<h1 class="fontface" id="title">창신's SNS</h1>
		</div>
	</header>

	<nav>
		<div class="menu">
			<ul>
				<li><a href="/sns/">Home</a></li>
				<li><a href="sns_control.jsp?action=getAll">전체글보기</a>
				<li><sns:login /></li>
			</ul>
		</div>
	</nav>

	<div id="wrapper">
		<section id="main">
			<section id="content">
				<b>내소식 업데이트</b>
				<form class="m_form" method="post" action="sns_control.jsp?action=newMsg">
					<input type="hidden" name="uid" value="${uid}">
					<sns:write type="msg" />
				</form>
				<br> <br>
				<h3>친구들의 최신 소식</h3>
				<div id="accordion">
					<c:forEach varStatus="mcnt" var="msgs" items="${datas}">
						<c:set var="m" value="${msgs.message}"/>
						<h3> [${m.uid}]${m.msg} :: [좋아요 ${m.favcount} | 댓글 ${m.replycount}] </h3>
						<div>
							<p></p>
							<p><sns:smenu mid="${m.mid}" auid="${m.uid}" curmsg="${mcnt.index}"/>
							/ ${m.date}에 작성된 글입니다.></p>
							<ul class="reply">
								<c:forEach var="reply" items="${msgs.rlist}">
									<li>${reply.uid} :: ${reply.rmsg} - ${reply.date} <sns:rmenu curmsg="${mcnt.index}" rid="${reply.rid}" ruid="${reply.uid}"/></li>
								</c:forEach>
							</ul>
							<form action="sns_control.jsp?action=newReply&cnt=${cnt}" method="post">
								<input type="hidden" name="mid" value="${m.mid}">
								<input type="hidden" name="uid" value="${uid}">
								<input type="hidden" name="suid" value="${suid}">
								<input type="hidden" name="curmsg" value="${mcnt.index}">
								<sns:write type="rmsg"/>
							</form>
						</div>
					</c:forEach>
				</div>
				<div align="center">
					<a href="sns_control.jsp?action=getAll&cnt=${cnt+5}&suid=${suid}">더보기&gt;&gt;</a>
				</div>
			</section>

			<aside id="sidebar2">
				<h2>새로운 친구들.!!</h2>
				<ul>
					<c:forEach items="${newMembers}" var="members">
						<li><a href = "sns_control.jsp?action=getAll&suid=${members}"> ${members} </a></li>
					</c:forEach>
				</ul>
				<br> <br>
				<h3>We're Social Too!!</h3>
				<a href="www.facebook.com"><img src="img/facebook_32.png"></a>
				<a href="www.twitter.com/"><img src="img/twitter_32.png"></a>
				<a href="www.youtube.com"><img src="img/youtube_32.png"></a> <br> <br> <br> <br>

				<h3>Links</h3>
				<ul>
					<li><a href="http://www.naver.com">네이버</a></li>
					<li><a href="http://www.google.com">구글</a></li>
					<li><a href="http://www.daum.net">다음</a></li>
				</ul>

			</aside>
		</section>
	</div>

	<footer>
		<div class="container1">
			<section id="footer-area">

				<section id="footer-outer-block">
					<aside class="footer-segment">
						<h4>About</h4>
						<ul>
							<li><a href="#">About My Simple SNS</a></li>
							<li><a href="#">Copyright</a></li>
							<li><a href="#">Author</a></li>
						</ul>
					</aside>
					<!-- end of #first footer segment -->

					<aside class="footer-segment">
						<h4>Java Web Programming</h4>
						<ul>
							<li><a href="#">Book Information</a></li>
							<li><a href="#">Table of contents</a></li>
							<li><a href="#">Book History</a></li>
						</ul>
					</aside>
					<!-- end of #second footer segment -->

					<aside class="footer-segment">
						<h4>Contact Us</h4>
						<ul>
							<li><a href="#">Book Support</a></li>
							<li><a href="#">Publication</a></li>
							<li><a href="#">Investor Relations</a></li>
						</ul>
					</aside>
					<!-- end of #third footer segment -->

					<aside class="footer-segment">
						<h4>이창신</h4>
						<p>
							&copy; 2016 <a href="#">ycs318@naver.com</a>
						</p>
					</aside>
					<!-- end of #fourth footer segment -->

				</section>
				<!-- end of footer-outer-block -->

			</section>
			<!-- end of footer-area -->
		</div>
	</footer>
</body>
</html>