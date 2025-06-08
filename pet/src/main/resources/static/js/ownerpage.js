
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
        case "pay":
          content = "<p1>총 결제 내역</p1><br><p2>누적금액 : </p2><p3>150,000원</p3><p>산책 : 30,000원(24.08.17)</p><p>호텔링 : 70,000원(25.02.10-11)</p><p>산책 : </p><p>호텔링 : </p><p>데이케어 : </p><p>데이케어 : </p>";
          break;
        case "view1":
          content = "<p1>호텔링 예약</p1><br><p2>예약 확정</p2><p>시터 이름 : LEE<p>날짜 : 2025-05-12 ~ 2025-05-15</p><p>가격 : 120,000원</p><p>시간 : 직접 드롭인 오전 10시 / 주인이 픽업 오실 예정(시간 미지정 - 오시기 1시간 전에 연락주시기로 함)</p><p>주의사항 : 생각보다 분리불안이 심하여, 잔짖음이 있음. 또 경미한 배변실수 있음</p>";
          break;
        case "view2":
          content = "<p1>산책 예약</p1><br><p2>예약 확정</p2><p>시터 이름 : 몽실</p><p>날짜 : 2025-06-01</p><p>가격 : 30,000원</p><p>시간 : 오전 10시 (1시간)</p><p>주의사항 : 궁금증이 많아 산책할 때 집중하지 못함. 식탐이 많아 아무거나 주워먹음(자세히 봐주세요)</p>";
          break;
         case "petInfo":
           content = `
             <h3>반려동물 정보 등록</h3>
             <div class="pet-form">
               <label>이름: <input type="text" id="petName" /></label><br>
               <label>품종: <input type="text" id="petKind" /></label><br>
               <label>나이(세): <input type="number" id="petAge" /></label><br>
               <label>성격: <textarea id="petChar"></textarea></label><br>
               <label>주의사항: <textarea id="caution"></textarea></label><br><br>
               <button id="addPetBtn">등록</button>
             </div>
           `;
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
      currentSitter = btn.dataset.sitter;
      chatWith.textContent = `${currentSitter}님과의 채팅`;
      chatMessages.innerHTML = ""; // 새로운 시터 채팅 시 메시지 초기화
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

    // 이 자리에서 WebSocket 등으로 currentSitter에게 메시지 전송 가능
  }
});

// pagenation sec
document.addEventListener('DOMContentLoaded', () => {
  const pages = document.querySelectorAll('.resev-page');
  const pageButtons = document.querySelectorAll('.page-btn');
  const leftBtn = document.querySelector('.allres .left');
  const rightBtn = document.querySelector('.allres .right');
  let currentPage = 1;
  const totalPages = pages.length;

  function showPage(page) {
    pages.forEach((p, idx) => {
      p.classList.toggle('active', idx === page - 1);
    });

    pageButtons.forEach((btn, idx) => {
      btn.classList.toggle('active', idx === page - 1);
    });

    currentPage = page;
  }

  pageButtons.forEach((btn, idx) => {
    btn.addEventListener('click', () => {
      showPage(idx + 1);
    });
  });

  leftBtn.addEventListener('click', () => {
    if (currentPage > 1) {
      showPage(currentPage - 1);
    }
  });

  rightBtn.addEventListener('click', () => {
    if (currentPage < totalPages) {
      showPage(currentPage + 1);
    }
  });

  showPage(1); // 초기 첫 페이지 표시
});

document.body.addEventListener("click", async (event) => {
  if (event.target.id === "addPetBtn") {
    const name = document.getElementById("petName").value.trim();
    const kind = document.getElementById("petKind").value.trim();
    const age = document.getElementById("petAge").value;
    const petChar = document.getElementById("petChar").value.trim();
    const caution = document.getElementById("caution").value.trim();

    if (!name || !kind || !age) {
      alert("이름, 품종, 나이는 필수 입력입니다.");
      return;
    }

    const response = await fetch("/pet", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        petName: name,
        petKind: kind,
        petAge: age,
        petChar: petChar,
        caution: caution,
        ownerId: ownerId
      })
    });

    if (response.ok) {
      const savedPet = await response.json();

      const petCard = document.createElement("div");
      petCard.className = "pro-card";
      petCard.dataset.petId = savedPet.petId;
      petCard.innerHTML = `
        <img src="/asset/dog1.jpg" alt="">
        <ul>
          <li>${savedPet.petName}</li>
          <li>${savedPet.petKind} / ${savedPet.petAge}세</li>
          <li>${savedPet.petChar}</li>
          <li>${savedPet.caution}</li>
        </ul>
        <button class="deletePetBtn">삭제</button>
      `;

      document.querySelector(".pet-prof").appendChild(petCard);
      modal.classList.remove("show");
    } else {
      alert("등록 실패");
    }
  }

  if (event.target.classList.contains("deletePetBtn")) {
    const petCard = event.target.closest(".pro-card");
    const petId = petCard.dataset.petId;

    const confirmed = confirm("삭제하시겠습니까?");
    if (!confirmed) return;

    const res = await fetch(`/pet/${petId}`, { method: "DELETE" });

    if (res.ok) {
      petCard.remove();
    } else {
      alert("삭제 실패");
    }
  }
});

document.addEventListener("DOMContentLoaded", async function () {
  if (typeof ownerId !== "undefined") {
    const res = await fetch(`/pet/list/${ownerId}`);
    if (res.ok) {
      const pets = await res.json();

      const petContainer = document.querySelector(".pet-prof");

      pets.forEach(pet => {
        const card = document.createElement("div");
        card.className = "pro-card";
        card.dataset.petId = pet.petId;

        card.innerHTML = `
          <img src="/asset/dog1.jpg" alt="">
          <ul>
            <li>${pet.petName}</li>
            <li>${pet.petKind} / ${pet.petAge}세</li>
            <li>${pet.petChar}</li>
            <li>${pet.caution}</li>
          </ul>
          <button class="deletePetBtn">삭제</button>
        `;
        petContainer.appendChild(card);
      });
    } else {
      console.error("반려동물 불러오기 실패");
    }
  }
});