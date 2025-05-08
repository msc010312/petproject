<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<body>
    <div class="header" id="headerContainer">
        <div class="logo">
            <span><a href="${pageContext.request.contextPath}/main">LOGO</a></span>
        </div>
        <div class="menu">
            <ul>
                <li class="menu-service">
                    <a href="#">Service</a>
                    <ul class="sub-menu">
                        <li class="sub-item"><a href="#">Day Care</a></li>
                        <li class="sub-item"><a href="#">Hotelling</a></li>
                        <li class="sub-item"><a href="#">work with pet</a></li>
                    </ul>
                </li>
                <li><a href="#">place</a></li>
                <li><a href="#">board</a></li>
            </ul>
        </div>
        <div class="sign">
            <c:choose>
            <c:when test="${not empty sessionScope.loggedInUser}">
                <span>${sessionScope.loggedInUser.name}님 환영합니다</span>
                <button class="sign-out btn">
                    <a href="${pageContext.request.contextPath}/logout">로그아웃</a>
                </button>
                <button class="resign btn">
                    <a href="${pageContext.request.contextPath}/resign">회원탈퇴</a>
                </button>
            </c:when>
            <c:otherwise>
                    <button class="sign-in btn">
                        <a href="${pageContext.request.contextPath}/login">로그인</a>
                    </button>
                    <button class="sign-up btn">
                        <a href="${pageContext.request.contextPath}/signup">회원가입</a>
                    </button>
            </c:otherwise>
           </c:choose>
        </div>
    </div>
</body>