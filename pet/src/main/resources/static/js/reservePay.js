IMP.init("imp47302140");

let selectedSitterId = 1; // 임시 시터값

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
  const selectedServiceType = document.querySelector(".tab.active").dataset.type;

  IMP.request_pay({
    channelKey: "channel-key-8c4b6869-ef18-4d04-a650-a6f93622e59c",
    pay_method: "card",
    merchant_uid: "merchant_" + crypto.randomUUID(),
    name: serviceText,
    amount: 100, // 실제 결제금액 적용 amount
    buyer_email: ownerEmail,
    buyer_name: ownerName
  }, function (rsp) {
    if (rsp.success) {
      fetch("/api/reserve", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          serviceType: selectedServiceType,
          location: location,
          dateTime: convertToISOString(dateTime),
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
  if (!dateTimeStr) return new Date().toISOString();
  const [date, time] = dateTimeStr.split(" ");
  return new Date(`${date}T${time}:00`).toISOString();
}
