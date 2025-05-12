<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <script>
        alert('${successLogin}');
        window.location.href = '${pageContext.request.contextPath}/main';
    </script>
</head>
<body>
</body>
</html>
