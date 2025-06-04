document.addEventListener("DOMContentLoaded", function () {
  const modal = document.getElementById("myModal");
  const closeBtn = document.getElementById("closeBtn");
  const modalBody = document.getElementById("modalContentBody");
  const modalBtns = document.querySelectorAll(".modal-btn");

  // 버튼들에 이벤트 리스너 추가
  modalBtns.forEach((btn) => {
    btn.addEventListener("click", function () {
      const type = btn.getAttribute("data-type");
      let content = "";

      switch (type) {
        // 결제 내역
        case "pay":
          content = "<p1>총 누적액</p1><br><p2>누적 금액 : </p2><p3>150,000원</p3><p>산책 : 30,000원(24.08.17)</p><p>호텔링 : 70,000원(25.02.10-11)</p><p>산책 : </p><p>호텔링 : </p><p>데이케어 : </p><p>데이케어 : </p>";
          break;
        // 상세보기
        case "view1":
          content = "<p1>호텔링 예약</p1><br><p2>예약 확정</p2><p>pet 이름 : 동동<p><p>주인 이름 : 하몽<p>날짜 : 2025-05-12 ~ 2025-05-15</p><p>가격 : 120,000원</p><p>시간 : 직접 드롭인 오전 10시 / 주인이 픽업 오실 예정(시간 미지정 - 오시기 1시간 전에 연락주시기로 함)</p><p>주의사항 : 생각보다 분리불안이 심하여, 잔짖음이 있음. 또 경미한 배변실수 있음</p>";
          break;
        case "view2":
          content = "<p1>호텔링 예약</p1><br><p2>예약 확정</p2><p>pet 이름 : 동동<p><p>주인 이름 : 하몽<p>날짜 : 2025-05-12 ~ 2025-05-15</p><p>가격 : 120,000원</p><p>시간 : 직접 드롭인 오전 10시 / 주인이 픽업 오실 예정(시간 미지정 - 오시기 1시간 전에 연락주시기로 함)</p><p>주의사항 : 생각보다 분리불안이 심하여, 잔짖음이 있음. 또 경미한 배변실수 있음</p>";
          break;
        case "view3":
          content = "<p1>호텔링 예약</p1><br><p2>예약 확정</p2><p>pet 이름 : 동동<p><p>주인 이름 : 하몽<p>날짜 : 2025-05-12 ~ 2025-05-15</p><p>가격 : 120,000원</p><p>시간 : 직접 드롭인 오전 10시 / 주인이 픽업 오실 예정(시간 미지정 - 오시기 1시간 전에 연락주시기로 함)</p><p>주의사항 : 생각보다 분리불안이 심하여, 잔짖음이 있음. 또 경미한 배변실수 있음</p>";
          break;
        case "view4":
          content = "<p1>호텔링 예약</p1><br><p2>예약 확정</p2><p>pet 이름 : 동동<p><p>주인 이름 : 하몽<p>날짜 : 2025-05-12 ~ 2025-05-15</p><p>가격 : 120,000원</p><p>시간 : 직접 드롭인 오전 10시 / 주인이 픽업 오실 예정(시간 미지정 - 오시기 1시간 전에 연락주시기로 함)</p><p>주의사항 : 생각보다 분리불안이 심하여, 잔짖음이 있음. 또 경미한 배변실수 있음</p>";
          break;
        //   후기 부분
        case "view5":
          content = "<p1>산책</p1><p>날짜 : 2025-06-01</p><p>가격 : 30,000원</p><p>너무 재미있게 산책을 해주셔서 저희 아이가 너무 즐거워 하는게 느껴졌답니다. 집에와서 한동안 잠만 잤네요.</p>";
          break;
        case "view6":
          content = "<p1>산책</p1><p>날짜 : 2025-06-01</p><p>가격 : 30,000원</p><p>너무 재미있게 산책을 해주셔서 저희 아이가 너무 즐거워 하는게 느껴졌답니다. 집에와서 한동안 잠만 잤네요.</p>";
          break;
        case "view7":
          content = "<p1>산책</p1><p>날짜 : 2025-06-01</p><p>가격 : 30,000원</p><p>너무 재미있게 산책을 해주셔서 저희 아이가 너무 즐거워 하는게 느껴졌답니다. 집에와서 한동안 잠만 잤네요.</p>";;
          break;
        case "view8":
          content = "<p1>산책</p1><p>날짜 : 2025-06-01</p><p>가격 : 30,000원</p><p>너무 재미있게 산책을 해주셔서 저희 아이가 너무 즐거워 하는게 느껴졌답니다. 집에와서 한동안 잠만 잤네요.</p>";
          break;
        case "view9":
          content = "<p1>산책</p1><p>날짜 : 2025-06-01</p><p>가격 : 30,000원</p><p>너무 재미있게 산책을 해주셔서 저희 아이가 너무 즐거워 하는게 느껴졌답니다. 집에와서 한동안 잠만 잤네요.</p>";
          break;
        case "view10":
          content = "<p1>산책</p1><p>날짜 : 2025-06-01</p><p>가격 : 30,000원</p><p>너무 재미있게 산책을 해주셔서 저희 아이가 너무 즐거워 하는게 느껴졌답니다. 집에와서 한동안 잠만 잤네요.</p>";
          break;
        default:
          content = "<p>알 수 없는 요청입니다.</p>";
      }

      modalBody.innerHTML = content;
      modal.classList.add("show");
    });
  });

  // 닫기
  closeBtn.addEventListener("click", () => {
    modal.classList.remove("show");
  });

  window.addEventListener("click", function (event) {
    if (event.target === modal) {
      modal.classList.remove("show");
    }
  });
});
//-----------------------------------------------------------------------------------
// 채팅 모달
document.addEventListener("DOMContentLoaded", function () {
  const openBtns = document.querySelectorAll(".openChatBtn");
  const closeBtn = document.getElementById("closeChatBtn");
  const chatModal = document.getElementById("chatModal");
  const chatWith = document.getElementById("chatWith");
  const sendBtn = document.getElementById("sendBtn");
  const chatInput = document.getElementById("chatInput");
  const chatMessages = document.getElementById("chatMessages");

  let currentSitter = null;

  openBtns.forEach((btn) => {
    btn.addEventListener("click", () => {
      currentOwner = btn.dataset.owner;
      chatWith.textContent = `${currentOwner}님과의 채팅`;
      chatMessages.innerHTML = ""; // 새로운 오너(주인) 채팅 시 메시지 초기화
      chatModal.classList.add("show");
    });
  });

  closeBtn.addEventListener("click", () => {
    chatModal.classList.remove("show");
    currentSitter = null;
  });

  sendBtn.addEventListener("click", sendMessage);
  chatInput.addEventListener("keydown", function (e) {
    if (e.key === "Enter") sendMessage();
  });

  function sendMessage() {
    const message = chatInput.value.trim();
    if (message === "" || !currentSitter) return;

    const msg = document.createElement("div");
    msg.textContent = `나: ${message}`;
    chatMessages.appendChild(msg);

    chatInput.value = "";
    chatMessages.scrollTop = chatMessages.scrollHeight;
  }
});

// pagenation sec

document.addEventListener("DOMContentLoaded", () => {
  const endPages = document.querySelectorAll(".end-page");
  const endPageBtns = document.querySelectorAll(".allres .page-btn");
  const endLeftBtn = document.querySelector(".allres .left");
  const endRightBtn = document.querySelector(".allres .right");
  let currentEndPage = 1;
  const totalEndPages = endPages.length;

  function showEndPage(page) {
    endPages.forEach((p, idx) => {
      p.style.display = idx === page - 1 ? "block" : "none";
    });
    endPageBtns.forEach((btn, idx) => {
      btn.classList.toggle("active", idx === page - 1);
    });
    currentEndPage = page;
  }

  endPageBtns.forEach((btn, idx) => {
    btn.addEventListener("click", () => showEndPage(idx + 1));
  });

  endLeftBtn.addEventListener("click", () => {
    if (currentEndPage > 1) showEndPage(currentEndPage - 1);
  });

  endRightBtn.addEventListener("click", () => {
    if (currentEndPage < totalEndPages) showEndPage(currentEndPage + 1);
  });

  showEndPage(1); // 초기 첫 페이지
});

document.addEventListener("DOMContentLoaded", () => {
  const resPages = document.querySelectorAll(".res-page");
  const resPageBtns = document.querySelectorAll(".res .page-btn");
  const resLeftBtn = document.querySelector(".res .left");
  const resRightBtn = document.querySelector(".res .right");
  let currentResPage = 1;
  const totalResPages = resPages.length;

  function showResPage(page) {
    resPages.forEach((p, idx) => {
      p.style.display = idx === page - 1 ? "flex" : "none";
    });
    resPageBtns.forEach((btn, idx) => {
      btn.classList.toggle("active", idx === page - 1);
    });
    currentResPage = page;
  }

  resPageBtns.forEach((btn, idx) => {
    btn.addEventListener("click", () => showResPage(idx + 1));
  });

  resLeftBtn.addEventListener("click", () => {
    if (currentResPage > 1) showResPage(currentResPage - 1);
  });

  resRightBtn.addEventListener("click", () => {
    if (currentResPage < totalResPages) showResPage(currentResPage + 1);
  });

  showResPage(1); // 초기 첫 페이지
});
