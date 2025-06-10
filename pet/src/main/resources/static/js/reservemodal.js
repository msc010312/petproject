window.addEventListener("DOMContentLoaded", () => {
  const detailButtons = document.querySelectorAll(".details-btn");
  const modal = document.getElementById("reserve-modal");
  const closeBtn = document.querySelector(".close-btn");
  const sitterCards = document.querySelectorAll(".sitter-card .sitter-content");

  // 상세보기 클릭 시 모달 열기 및 가격 저장
  detailButtons.forEach((btn, index) => {
    btn.addEventListener("click", () => {
      const selected = sitterCards[index];
      const priceEl = selected.querySelector(".sitter-price");
      const priceText = priceEl?.textContent?.replace(/[^0-9]/g, "") || "30000";
      const parsedPrice = parseInt(priceText);

      window.selectedSitterPrice = parsedPrice;
      window.priceConfirmed = true;

    // 프로필 이미지 삽입
    const sitterImageUrl = btn.dataset.profileimage || "";  // profileImage URL 가져오기
    const modalSitterImage = document.getElementById("modal-sitter-img");
    if (modalSitterImage && sitterImageUrl) {
      modalSitterImage.src = sitterImageUrl;  // 모달 이미지 업데이트
    }

    // presentation 텍스트 삽입
    const presentation = btn.dataset.presentation || "소개 정보가 없습니다.";
    const introEl = document.querySelector(".sitter-presentation");
    if (introEl) introEl.textContent = presentation;

      if (modal) modal.style.display = "flex";
    });
  });

  // 모달 닫기
  if (closeBtn) {
    closeBtn.addEventListener("click", () => {
      modal.style.display = "none";
    });
  }

  // 바깥 영역 클릭 시 모달 닫기
  window.addEventListener("click", (e) => {
    if (e.target === modal) {
      modal.style.display = "none";
    }
  });

  // 예약 버튼 마우스오버 시 가격 계산
  const reserveBtn = document.querySelector(".reserve-btn2");
  if (reserveBtn) {
    reserveBtn.addEventListener("mouseenter", () => {
//      const price = window.selectedSitterPrice || 30000;
//      const timeText = document.getElementById("reservation-time")?.textContent;
//
//      let total = price;
//
//      if (timeText && timeText.includes("~")) {
//        const [startStr, endStr] = timeText.split("~").map(s => s.trim());
//        const startDate = new Date(startStr);
//        const endDate = new Date(endStr);
//        const diffDays = Math.round((endDate - startDate) / (1000 * 60 * 60 * 24));
//        total = price * diffDays;
//      }
//
//      document.getElementById("reservation-price").textContent = `${price.toLocaleString()}원`;
//      document.getElementById("reservation-total").textContent = `${total.toLocaleString()}원`;
    });

    // 예약하기 클릭 시 모달 닫기
    reserveBtn.addEventListener("click", () => {
      modal.style.display = "none";
    });
  }

  // 후기 이미지 슬라이더
  const track = document.getElementById("slider-track");
  const prevBtn = document.querySelector(".slider__btn.prev");
  const nextBtn = document.querySelector(".slider__btn.next");

  let currentIndex = 0;
  const itemWidth = 115; // 이미지 100px + 간격 15px
  const maxIndex = track ? track.children.length - 4 : 0;

  function updateSliderPosition() {
    if (track) {
      track.style.transform = `translateX(-${currentIndex * itemWidth}px)`;
    }
  }

  if (prevBtn && nextBtn && track) {
    prevBtn.addEventListener("click", () => {
      if (currentIndex > 0) {
        currentIndex--;
        updateSliderPosition();
      }
    });

    nextBtn.addEventListener("click", () => {
      if (currentIndex < maxIndex) {
        currentIndex++;
        updateSliderPosition();
      }
    });
  }
});

