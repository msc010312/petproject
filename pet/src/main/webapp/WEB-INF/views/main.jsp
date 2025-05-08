<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
      <link rel="stylesheet"
        href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
      <!-- Swiper CSS -->
      <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css" />
      <!-- Swiper JS -->
      <script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/mainPage.js" defer></script>
    <title>Document</title>
    <!-- css -->
     <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
     <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
     <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css" />
     <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mainPage.css" />

</head>
<body>
<!--헤더-->
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>
<!-- 메인영역 -->
<div class="main">
      <!-- 작업 영역 -->
      <div class="content">
        <!-- 슬라이드 영역 -->
        <div class="swiper slide">
          <div class="swiper-wrapper">
            <div class="swiper-slide">
              <img src="../../asset/강아지2.jpg" alt="슬라이드1">
              <div class="slide-text">
                <h3>우리 강아지를 위한 최고의 공간</h3>
                <p>사랑과 안전이 가득한 데이케어 서비스를 제공합니다.</p>
              </div>
            </div>
            <div class="swiper-slide">
              <img src="../../asset/산책.jpg" alt="슬라이드2">
              <div class="slide-text">
                <h3>우리 강아지를 위한 최고의 공간</h3>
                <p>사랑과 안전이 가득한 데이케어 서비스를 제공합니다.</p>
              </div>
            </div>
            <div class="swiper-slide">
              <img src="../../asset/산책2.jpg" alt="슬라이드3">
              <div class="slide-text">
                <h3>우리 강아지를 위한 최고의 공간</h3>
                <p>사랑과 안전이 가득한 데이케어 서비스를 제공합니다.</p>
              </div>
            </div>
          </div>
          <!-- 네비게이션 버튼 -->
          <div class="swiper-button-prev"></div>
          <div class="swiper-button-next"></div>
        </div>
        <div class="services hidden">
          <h2>서비스 소개</h2>
          <p>원하시는 정보를 찾기 위한 서비스를 확인해보세요.</p>
          <div class="service-cards">
            <div class="card">
              <span class="material-symbols-outlined">
                pets
              </span>
              <p>데이 케어 서비스</p>
              <div class="image">
                <img src="../../asset/강아지.jpg" alt="">
              </div>
              <a href="javascript:void(0)">신청하기</a>
            </div>
            <div class="card">
              <span class="material-symbols-outlined">
                location_city
              </span>
              <p>호텔링 서비스</p>
              <div class="image">
                <img src="../../asset/강아지.jpg" alt="">
              </div>
              <a href="javascript:void(0)">신청하기</a>
            </div>
            <div class="card">
              <span class="material-symbols-outlined">
                location_on
              </span>
              <p>산책 서비스</p>
              <div class="image">
                <img src="../../asset/강아지.jpg" alt="">
              </div>
              <a href="javascript:void(0)">신청하기</a>
            </div>
          </div>
        </div>
        <div class="rank">
          <h2>이달의 베스트 시터</h2>
        </div>
      </div>
    </div>
 <!--푸터-->
<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</body>
</html>