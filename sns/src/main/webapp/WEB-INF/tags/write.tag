<%@ tag language="java" pageEncoding="UTF-8" body-content="scriptless"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="type"%>

<c:if test="${uid != null}">
	<c:choose>
		<c:when test="${type == 'msg' }">
			<input type="text" name="msg" maxlength="100" placeholder="내 소식을 알리세요!!">
		</c:when>
		<c:when test="${type == 'rmsg'}">
			덧글달기<input type="text" name="rmsg" maxlength="50" size="60" placeholder="소식에 덧글을 달아보세요!!">
		</c:when>
	</c:choose>
	<button class="submit" type="submit">등록</button>
</c:if>

<c:if test="${uid == null }">
	<c:choose>
		<c:when test="${type == 'msg' }">
			<input type="text" name="msg" maxlength="100" disabled value="작성하려면 로그인 하세요!!!">
		</c:when>
		<c:when test="${type == 'rmsg' }">
			덧글달기<input type="text" name="rmsg" maxlength="50" size="60" disabled value="작성하려면 로그인 하세요!!">
		</c:when>
	</c:choose>
	<button class="submit" type="submit" disabled>등록</button>
</c:if>