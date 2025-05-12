<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <script>
        alert('${success}');
        window.location.href = '${pageContext.request.contextPath}/login';
    </script>
</head>
<body>
</body>
</html>
