let selectedSitterPrice = 30000;
window.priceConfirmed = false;

const reservationHeader = document.getElementById('reservationHeader');
const tabs = document.querySelectorAll('.tab');

const serviceTemplates = {
  daycare: `
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
      <div id="map" style="width: 100%; height: 400px; margin-top: 10px;"></div>
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
      <div id="map" style="width: 100%; height: 400px; margin-top: 10px;"></div>
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
      <div id="map" style="width: 100%; height: 400px; margin-top: 10px;"></div>
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
        const checkInFormatted = checkIn.toLocaleDateString();
        const checkOutFormatted = checkOut.toLocaleDateString();
        document.getElementById("reservation-time").textContent =
          `${checkInFormatted} ~ ${checkOutFormatted}`;
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

let map, marker, geocoder;

function loadMap() {
  const mapContainer = document.getElementById("map");
  if (!mapContainer) return;

  const defaultLoc = { lat: 37.5665, lng: 126.9780 };
  map = new google.maps.Map(mapContainer, {
    center: defaultLoc,
    zoom: 13
  });

  marker = new google.maps.Marker({
    map: map,
    position: defaultLoc
  });

  geocoder = new google.maps.Geocoder();

  map.addListener("click", (e) => {
    marker.setPosition(e.latLng);
    geocoder.geocode({ location: e.latLng }, (results, status) => {
      if (status === "OK" && results[0]) {
        document.getElementById("addressInput").value = results[0].formatted_address;
        document.getElementById("reservation-location").textContent = results[0].formatted_address;
      }
    });
  });

  enableAddressAutocomplete();
}

function searchAddress(address) {
  if (!address.trim()) return;
  const geo = new google.maps.Geocoder();
  geo.geocode({ address: address }, function (results, status) {
    if (status === "OK") {
      const location = results[0].geometry.location;
      map.setCenter(location);
      marker.setPosition(location);
      document.getElementById("reservation-location").textContent = results[0].formatted_address;
    } else {
      alert("주소를 찾을 수 없습니다: " + status);
    }
  });
}

function enableAddressAutocomplete() {
  const input = document.getElementById("addressInput");
  if (!input || !window.google || !google.maps.places) return;

  const autocomplete = new google.maps.places.Autocomplete(input, {
    types: ['geocode'],
    componentRestrictions: { country: "kr" }
  });

  autocomplete.addListener("place_changed", () => {
    const place = autocomplete.getPlace();
    if (!place.geometry) {
      alert("선택한 주소에 위치 정보가 없습니다.");
      return;
    }
    const location = place.geometry.location;
    map.setCenter(location);
    marker.setPosition(location);
    document.getElementById("reservation-location").textContent = place.formatted_address;
  });
}

window.addEventListener("DOMContentLoaded", () => {
  if (reservationHeader) {
    reservationHeader.innerHTML = serviceTemplates["daycare"];
    initializePickers();

    const googleInterval = setInterval(() => {
      if (window.google && window.google.maps) {
        loadMap();
        clearInterval(googleInterval);
      }
    }, 100);
  }

  tabs.forEach(tab => {
    tab.addEventListener("click", () => {
      tabs.forEach(t => t.classList.remove("active"));
      tab.classList.add("active");

      const type = tab.dataset.type;
      reservationHeader.innerHTML = serviceTemplates[type];
      initializePickers();
      setTimeout(() => {
        loadMap();
      }, 100);

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

// 시터 상세 버튼
document.querySelectorAll(".details-btn").forEach((btn, index) => {
  btn.addEventListener("click", () => {
    selectedSitterId = index + 1;
    console.log("임시 선택된 시터 ID:", selectedSitterId);
  });
});

// 엔터키로 주소 검색
document.addEventListener("keydown", function (e) {
  if (e.key === "Enter" && document.activeElement.id === "addressInput") {
    const address = document.getElementById("addressInput").value;
    searchAddress(address);
  }
});
