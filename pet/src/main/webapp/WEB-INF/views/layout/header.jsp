<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<body>
    <div class="header" id="headerContainer">
        <div class="logo">
            <span><a href="#">LOGO</a></span>
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
            <button class="sign-in btn">
                <a href="${pageContext.request.contextPath}/login">로그인</a>
            </button>
            <button class="sign-up btn">
                <a href="${pageContext.request.contextPath}/signup">회원가입</a>
            </button>
        </div>
    </div>
</body>