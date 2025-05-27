document.addEventListener("DOMContentLoaded", () => {
  const buttons = document.querySelectorAll(".details-btn");
  const modal = document.getElementById("reserve-modal");
  const modalInner = document.getElementById("modal-inner");

  buttons.forEach((btn) => {
    btn.addEventListener("click", () => {
      fetch('/modal/sitter-detail')
        .then(res => res.text())
        .then(html => {
          modalInner.innerHTML = html;
          modal.style.display = "flex";

          // 동적으로 삽입된 닫기버튼 이벤트
          const closeBtn = modalInner.querySelector(".close-btn, #modal-x-btn");
          if (closeBtn) {
            closeBtn.addEventListener("click", () => {
              modal.style.display = "none";
              modalInner.innerHTML = "";
            });
          }
        });
    });
  });
});
