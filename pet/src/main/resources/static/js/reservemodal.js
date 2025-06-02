// reservemodal.js (재구성본 전체 복붙용)

// 시터 상세 정보 모달 로직
window.addEventListener("DOMContentLoaded", () => {
  const detailButtons = document.querySelectorAll(".details-btn");
  const modal = document.getElementById("reserve-modal");
  const closeBtn = document.querySelector(".close-btn");
  const modalInner = document.getElementById("modal-inner");
  const sitterCards = document.querySelectorAll(".sitter-card .sitter-content");

  detailButtons.forEach((btn, index) => {
    btn.addEventListener("click", () => {
      const selected = sitterCards[index];
      const priceEl = selected.querySelector(".sitter-price");
      const priceText = priceEl?.textContent?.replace(/[^0-9]/g, "") || "30000";
      const parsedPrice = parseInt(priceText);
      window.selectedSitterPrice = parsedPrice;
      window.priceConfirmed = true;

      // 예약 정보에 가격과 총 금액 반영
      const priceTarget = document.getElementById("reservation-price");
      const totalTarget = document.getElementById("reservation-total");
      if (priceTarget) priceTarget.textContent = parsedPrice.toLocaleString() + "원";
      if (totalTarget) totalTarget.textContent = parsedPrice.toLocaleString() + "원";

      if (modal) modal.style.display = "flex";
    });
  });

  if (closeBtn) {
    closeBtn.addEventListener("click", () => {
      modal.style.display = "none";
    });
  }

  // 외부 클릭 시 닫기
  window.addEventListener("click", (e) => {
    if (e.target === modal) {
      modal.style.display = "none";
    }
  });
});
