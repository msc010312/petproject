<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <!--js-->
    <script src="${pageContext.request.contextPath}/js/tab.js" defer></script>
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
                       <div class="signup_wrap">
                           <div class="signup_form">
                               <span>sign up</span>

                               <div class="convert-tab">
                                   <input id="owner" type="radio" name="tabName" checked>
                                   <label class="tab-name" for="owner">Owner</label>
                                   <input id="sitter" type="radio" name="tabName">
                                   <label class="tab-name" for="sitter">Sitter</label>
                               </div>
                               <form id="ownerSign" class="tab-content">
                                   <div class="txt_field">
                                       <input type="text" name="userEmail" required>
                                       <label>e-mail</label>
                                   </div>
                                   <div class="txt_field">
                                       <input type="password" name="userPw" required>
                                       <label>Password</label>
                                   </div>
                                   <div class="txt_field">
                                       <input type="password" name="userPwRe" required>
                                       <label>confirm Password</label>
                                   </div>
                                   <div class="txt_field">
                                       <input type="text" name="ownerName" required>
                                       <label>Owner name</label>
                                   </div>
                                   <div class="txt_field">
                                       <input type="text" name="addr" required>
                                       <label>address</label>
                                   </div>
                                   <div class="txt_field">
                                       <input type="text" name="phoneNum" required>
                                       <label>Phone Number</label>
                                   </div>
                               </form>
                               <form class="tab-content" id="sitterSign">
                                   <div class="txt_field">
                                       <input type="text" name="userEmail"required>
                                       <label>e-mail</label>
                                   </div>
                                   <div class="txt_field">
                                       <input type="password" name="userPw" required>
                                       <label>Password</label>
                                   </div>
                                   <div class="txt_field">
                                       <input type="password" name="userPwRe" required>
                                       <label>confirm Password</label>
                                   </div>
                                   <div class="txt_field">
                                       <input type="text" name="sitterName" required>
                                       <label>Sitter name</label>
                                   </div>
                                   <div class="txt_field">
                                       <input type="text" name="addr" required>
                                       <label>address</label>
                                   </div>
                                   <div class="txt_field">
                                       <input type="text" name="phoneNum" required>
                                       <label>Phone Number</label>
                                   </div>
                               </form>
                               <input type="button" name="signUp" value="Sign Up" id="joinButton" class="btn">
                           </div>
                       </div>
                   </div>
               </div>
           </div>
           <!--푸터-->
           <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</body>
</html>