document.addEventListener("DOMContentLoaded", function () {
  // 모달 처리
  const modal = document.getElementById("myModal");
  const closeBtn = document.getElementById("closeBtn");
  const modalBody = document.getElementById("modalContentBody");
  const modalBtns = document.querySelectorAll(".modal-btn");

  // 모달 버튼에 이벤트 리스너 추가
  if (modalBtns.length > 0) {
    modalBtns.forEach((btn) => {
      btn.addEventListener("click", function () {
        const type = btn.getAttribute("data-type");
        let content = "";

        // content 업데이트 부분 (switch문 생략)
        switch (type) {
          case "pay":
            content = "<p1>총 누적액</p1><br><p2>누적 금액 : </p2><p3>150,000원</p3><p>산책 : 30,000원(24.08.17)</p><p>호텔링 : 70,000원(25.02.10-11)</p><p>산책 : </p><p>호텔링 : </p><p>데이케어 : </p><p>데이케어 : </p>";
            break;
          case "view1":
            content = "<p1>호텔링 예약</p1><br><p2>예약 확정</p2><p>pet 이름 : 동동<p><p>주인 이름 : 하몽<p>날짜 : 2025-05-12 ~ 2025-05-15</p><p>가격 : 120,000원</p><p>시간 : 직접 드롭인 오전 10시 / 주인이 픽업 오실 예정(시간 미지정 - 오시기 1시간 전에 연락주시기로 함)</p><p>주의사항 : 생각보다 분리불안이 심하여, 잔짖음이 있음. 또 경미한 배변실수 있음</p>";
            break;
          // 다른 케이스들 생략...
          default:
            content = "<p>알 수 없는 요청입니다.</p>";
        }

        modalBody.innerHTML = content;
        modal.classList.add("show");
      });
    });
  } else {
    console.error("모달 버튼을 찾을 수 없습니다.");
  }

  // 모달 닫기 버튼
  if (closeBtn) {
    closeBtn.addEventListener("click", () => {
      modal.classList.remove("show");
    });
  } else {
    console.error("모달 닫기 버튼을 찾을 수 없습니다.");
  }

  window.addEventListener("click", function (event) {
    if (event.target === modal) {
      modal.classList.remove("show");
    }
  });

  // ------------------------ 채팅 모달 ------------------------
  const openBtns = document.querySelectorAll(".openChatBtn");
  const closeChatBtn = document.getElementById("closeChatBtn");
  const chatModal = document.getElementById("chatModal");
  const chatWith = document.getElementById("chatWith");
  const sendBtn = document.getElementById("sendBtn");
  const chatInput = document.getElementById("chatInput");
  const chatMessages = document.getElementById("chatMessages");

  let currentOwner = null;

  if (openBtns.length > 0) {
    openBtns.forEach((btn) => {
      btn.addEventListener("click", () => {
        currentOwner = btn.dataset.owner;
        chatWith.textContent = `${currentOwner}님과의 채팅`;
        chatMessages.innerHTML = ""; // 새로운 오너 채팅 메시지 초기화
        chatModal.classList.add("show");
      });
    });
  } else {
    console.error("채팅 버튼을 찾을 수 없습니다.");
  }

  if (closeChatBtn) {
    closeChatBtn.addEventListener("click", () => {
      chatModal.classList.remove("show");
    });
  } else {
    console.error("채팅 닫기 버튼을 찾을 수 없습니다.");
  }

  sendBtn.addEventListener("click", sendMessage);
  chatInput.addEventListener("keydown", function (e) {
    if (e.key === "Enter") sendMessage();
  });

  function sendMessage() {
    const message = chatInput.value.trim();
    if (message === "" || !currentOwner) return;

    const msg = document.createElement("div");
    msg.textContent = `나: ${message}`;
    chatMessages.appendChild(msg);

    chatInput.value = "";
    chatMessages.scrollTop = chatMessages.scrollHeight;
  }
});
