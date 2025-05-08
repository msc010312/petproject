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
     <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css?after" />
     <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css?after" />
     <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css?after" />
     <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/sign.css?after" />
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
                               <form action="${pageContext.request.contextPath}/signup" id="ownerSign" class="tab-content" method="post">
                                   <div class="txt_field">
                                       <input type="text" name="email" required>
                                       <label>e-mail</label>
                                   </div>
                                   <div class="txt_field">
                                       <input type="password" name="password" required>
                                       <label>Password</label>
                                   </div>
                                   <div class="txt_field">
                                       <input type="password" name="userPwRe" required>
                                       <label>confirm Password</label>
                                   </div>
                                   <div class="txt_field">
                                       <input type="text" name="name" required>
                                       <label>Owner name</label>
                                   </div>
                                   <div class="txt_field">
                                       <input type="text" name="address" required>
                                       <label>address</label>
                                   </div>
                                   <div class="txt_field">
                                       <input type="text" name="phone" required>
                                       <label>Phone Number</label>
                                   </div>
                                   <input type="hidden" name="userType" value="Owner" required>
                                   <input type="submit" name="signUp" value="Sign Up" id="OwnerBtn" class="btn">
                               </form>
                               <form action="${pageContext.request.contextPath}/signup" class="tab-content" id="sitterSign" method="post">
                                   <div class="txt_field">
                                       <input type="text" name="email"required>
                                       <label>e-mail</label>
                                   </div>
                                   <div class="txt_field">
                                       <input type="password" name="password" required>
                                       <label>Password</label>
                                   </div>
                                   <div class="txt_field">
                                       <input type="password" name="userPwRe" required>
                                       <label>confirm Password</label>
                                   </div>
                                   <div class="txt_field">
                                       <input type="text" name="name" required>
                                       <label>Sitter name</label>
                                   </div>
                                   <div class="txt_field">
                                       <input type="text" name="address" required>
                                       <label>address</label>
                                   </div>
                                   <div class="txt_field">
                                       <input type="text" name="phone" required>
                                       <label>Phone Number</label>
                                   </div>
                                   <input type="hidden" name="userType" value="Sitter" required>
                                   <input type="submit" name="signUp" value="Sign Up" id="SitterBtn" class="btn">
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