document.addEventListener("DOMContentLoaded", function () {
  // 1. header.html 가져오기
  fetch("/static/html/common/header.html")
    .then(response => response.text())
    .then(data => {
      document.getElementById("headerContainer").innerHTML = data;

      // header.css 동적으로 추가
      let link = document.createElement("link");
      link.rel = "stylesheet";
      link.href = "/static/css/header.css";
      document.head.appendChild(link);
    })
    .catch(error => console.error("헤더 로딩 실패:", error));

  // 2. footer.html 가져오기
  fetch("/static/html/common/footer.html")
    .then(response => response.text())
    .then(data => {
      document.getElementById("footerContainer").innerHTML = data;

      // footer.css 동적으로 추가
      let link = document.createElement("link");
      link.rel = "stylesheet";
      link.href = "/static/css/footer.css";
      document.head.appendChild(link);
    })
    .catch(error => console.error("footer 로딩 실패:", error));
});
