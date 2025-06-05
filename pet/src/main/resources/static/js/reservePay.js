    IMP.init("imp47302140");

    function pay(){

        const totalText = document.getElementById("reservation-total").innerText;
        const amount = Number(totalText.replace(/[^0-9]/g, ""));
        const service = document.getElementById("reservation-service").innerText;
        const paymentMethod = document.querySelector('input[name="payment"]:checked')?.value;

        const buyerEmail = document.getElementById("buyerEmail").value;
        const buyerTel = document.getElementById("buyerTel").value;
        const buyerName = document.getElementById("buyerName").value;

        if (!paymentMethod) {
            alert("결제 수단을 선택해주세요.");
            return;
        }

        const date = new Date();
        IMP.request_pay({
          channelKey: "channel-key-8c4b6869-ef18-4d04-a650-a6f93622e59c",
          pay_method: "card",
          merchant_uid: "merchant_"+crypto.randomUUID(),
          name: service,
          amount: 100,
          buyer_tel: buyerTel,
          buyer_email: buyerEmail,
          buyer_name: buyerName
        });
    }