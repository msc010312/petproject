<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <!-- css -->
     <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
     <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
     <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css" />
     <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/sign.css" />
</head>
<body>
<!--헤더-->
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>
<!-- 메인영역 -->
 <div class="main">
            <!-- pagew 1260px -->
            <div class="pagew">
                <!-- 작업 영역 -->
                <div class="content">
                    <!-- login -->
                    <div class="login_wrap">
                        <div class="login_form">
                            <span>login</span>
                            <form id="form" action="${pageContext.request.contextPath}/login" method="post">
                                <div class="txt_field">
                                    <input type="text" name="email" required>
                                    <label>ID (e-mail)</label>
                                </div>
                                <div class="txt_field">
                                    <input type="password" name="password" required>
                                    <label>Password</label>
                                </div>
                                <input type="submit" name="signIn" value="login" id="loginBtn" class="btn">
                                <div class="fg_pw">
                                    <a href="/findpw">Forgot your password?</a>
                                </div>
                                <div class="signup_link">
                                    Not a member?
                                    <a href="/signup">Sign Up</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
 <!--푸터-->
<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</body>
</html>