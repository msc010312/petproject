// reserve.js (날짜 선택 시 가격 계산 제거됨)

let selectedSitterPrice = 30000;
window.priceConfirmed = false;

const reservationHeader = document.getElementById('reservationHeader');
const tabs = document.querySelectorAll('.tab');

const serviceTemplates = {
  short: `
    <h2>데이케어 예약</h2>
    <div class="form-group">
      <label>데이케어 날짜와 시간을 입력하세요</label>
      <div class="date-time">
        <div class="input-icon">
          <input type="text" class="date-picker" placeholder="📅 날짜를 선택해주세요">
        </div>
        <div class="input-icon">
          <input type="text" class="time-picker" placeholder="⏰ 시간을 선택해주세요">
        </div>
      </div>
    </div>
    <div class="form-group">
      <label>위치</label>
      <input type="text" id="addressInput" placeholder="예: 서울특별시 강남구">
      <div id="map" style="width: 100%; height: 400px;"></div>
    </div>
  `,
  hotel: `
    <h2>호텔링 예약</h2>
    <div class="form-group">
      <label>체크인/체크아웃 날짜</label>
      <div class="date-time">
        <div class="input-icon">
          <input type="text" class="date-range-picker" placeholder="📅 체크인/체크아웃 날짜를 선택해주세요">
        </div>
      </div>
    </div>
    <div class="form-group">
      <label>호텔링 위치</label>
      <input type="text" id="addressInput" placeholder="예: 부산 해운대구">
      <div id="map" style="width: 100%; height: 400px;"></div>
    </div>
  `,
  walk: `
    <h2>산책 예약</h2>
    <div class="form-group">
      <label>산책 날짜와 시간</label>
      <div class="date-time">
        <div class="input-icon">
          <input type="text" class="date-picker" placeholder="📅 날짜를 선택해주세요">
        </div>
        <div class="input-icon">
          <input type="text" class="time-picker" placeholder="⏰ 시간을 선택해주세요">
        </div>
      </div>
    </div>
    <div class="form-group">
      <label>출발 위치</label>
      <input type="text" id="addressInput" placeholder="예: 인천 연수구">
      <div id="map" style="width: 100%; height: 400px;"></div>
    </div>
  `
};

function initializePickers() {
  flatpickr('.date-picker', {
    dateFormat: 'Y-m-d',
    minDate: 'today',
    onChange: updateReservationTime
  });
  flatpickr('.time-picker', {
    enableTime: true,
    noCalendar: true,
    dateFormat: 'H:i',
    time_24hr: true,
    onChange: updateReservationTime
  });
  flatpickr('.date-range-picker', {
    mode: 'range',
    dateFormat: 'Y-m-d',
    minDate: 'today',
    onClose: function (selectedDates) {
      if (selectedDates.length === 2) {
        const checkIn = selectedDates[0];
        const checkOut = selectedDates[1];

        document.getElementById("reservation-time").textContent =
          `${checkIn.toLocaleDateString()} ~ ${checkOut.toLocaleDateString()}`;
      }
    }
  });
}

function updateReservationTime() {
  const date = document.querySelector(".date-picker")?.value;
  const time = document.querySelector(".time-picker")?.value;
  if (date && time) {
    document.getElementById("reservation-time").textContent = `${date} ${time}`;
  }
}

function loadMap() {
  const mapContainer = document.getElementById("map");
  if (!mapContainer) return;

  const defaultLoc = { lat: 37.5665, lng: 126.9780 };
  const map = new google.maps.Map(mapContainer, {
    center: defaultLoc,
    zoom: 13
  });

  const marker = new google.maps.Marker({
    map: map,
    position: defaultLoc
  });

  const geocoder = new google.maps.Geocoder();
  map.addListener("click", (e) => {
    marker.setPosition(e.latLng);
    geocoder.geocode({ location: e.latLng }, (results, status) => {
      if (status === "OK" && results[0]) {
        document.getElementById("addressInput").value = results[0].formatted_address;
        document.getElementById("reservation-location").textContent = results[0].formatted_address;
      }
    });
  });
}

window.addEventListener("DOMContentLoaded", () => {
  if (reservationHeader && serviceTemplates.short) {
    reservationHeader.innerHTML = serviceTemplates.short;
  }
  initializePickers();

  const googleInterval = setInterval(() => {
    if (window.google && window.google.maps) {
      loadMap();
      clearInterval(googleInterval);
    }
  }, 100);

  tabs.forEach(tab => {
    tab.addEventListener("click", () => {
      tabs.forEach(t => t.classList.remove("active"));
      tab.classList.add("active");

      const type = tab.dataset.type;
      reservationHeader.innerHTML = serviceTemplates[type];
      initializePickers();
      setTimeout(loadMap, 100);

      document.getElementById("reservation-service").textContent = tab.textContent + " 서비스";
      document.getElementById("reservation-location").textContent = "";
      document.getElementById("reservation-time").textContent = "";
      document.getElementById("reservation-price").textContent = "0원";
      document.getElementById("reservation-total").textContent = "0원";

      selectedSitterPrice = 30000;
      priceConfirmed = false;
    });
  });
});
