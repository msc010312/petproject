IMP.init("imp47302140");

let selectedSitterId = 1;

function pay() {
  const totalText = document.getElementById("reservation-total").innerText;
  const amount = Number(totalText.replace(/[^0-9]/g, ""));
  const location = document.getElementById("reservation-location").innerText;
  const dateTime = document.getElementById("reservation-time").innerText;
  const requestText = document.querySelector("textarea")?.value || "없음";
  const paymentMethod = document.querySelector('input[name="payment"]:checked')?.value;
  if (!paymentMethod) {
    alert("결제 수단을 선택해주세요.");
    return;
  }

  if (!selectedSitterId) {
    alert("시터를 선택해주세요.");
    return;
  }

  const selectedTab = document.querySelector(".tab.active");
  const serviceType = selectedTab.dataset.type;
  const serviceText = selectedTab.textContent + " 서비스";

  // 날짜 처리
  const { startDateTime, endDateTime } = convertToISOString(dateTime);

  IMP.request_pay({
    channelKey: "channel-key-8c4b6869-ef18-4d04-a650-a6f93622e59c",
    pay_method: "card",
    merchant_uid: "merchant_" + crypto.randomUUID(),
    name: serviceText,
    amount: 100, // amount
    buyer_email: ownerEmail,
    buyer_name: ownerName
  }, function (rsp) {
    if (rsp.success) {
      fetch("/api/reserve", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          serviceType: serviceType,
          location: location,
          startDateTime: startDateTime,
          endDateTime: endDateTime,
          request: requestText,
          paymentMethod: rsp.pay_method,
          transactionId: rsp.imp_uid,
          ownerId: ownerId,
          sitterId: selectedSitterId
        })
      }).then(res => {
        if (res.ok) {
          window.location.href = "/reserve/confirm";
        } else {
          alert("예약 저장 실패");
        }
      });
    } else {
      alert("결제에 실패했습니다: " + rsp.error_msg);
    }
  });
}
function convertToISOString(dateTimeStr) {
  if (!dateTimeStr || dateTimeStr.trim() === "") {
    console.warn("빈 날짜입니다. 현재 날짜로 대체");
    return { startDateTime: new Date().toISOString(), endDateTime: null };
  }

  // 호텔링 형식: "2025. 6. 12. ~ 2025. 6. 14."
  if (dateTimeStr.includes("~")) {
    const [startRaw, endRaw] = dateTimeStr.split(" ~ ");
    const start = parseKoreanDate(startRaw);
    const end = parseKoreanDate(endRaw);
    return {
      startDateTime: start.toISOString(),
      endDateTime: end.toISOString()
    };
  }

  // 단일 날짜+시간 형식: "2025-06-12 15:00"
  const [date, time] = dateTimeStr.split(" ");
  const full = `${date}T${time || "00:00"}:00`;
  const dt = new Date(full);
  if (isNaN(dt)) {
    console.error("Invalid datetime:", dateTimeStr);
    return { startDateTime: new Date().toISOString(), endDateTime: null };
  }
  return { startDateTime: dt.toISOString(), endDateTime: null };
}

// "2025. 6. 12." 같은 형식을 Date 객체로 변환
function parseKoreanDate(raw) {
  const parts = raw.trim().replace(/\.$/, "").split(". ");
  const [y, m, d] = parts.map(Number);
  return new Date(y, m - 1, d);
}
