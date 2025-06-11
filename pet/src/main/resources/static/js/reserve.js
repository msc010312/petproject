// reserve.js (날짜 선택 시 가격 계산 제거됨)

let selectedSitterPrice = 30000;
window.priceConfirmed = false;
let circle;               // 2km 반경 원
let sitterMarkers = [];
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
    onClose: dates => {
      if (dates.length === 2) {
        const [inD, outD] = dates;
        document.getElementById('reservation-time').textContent =
          `${inD.toLocaleDateString()} ~ ${outD.toLocaleDateString()}`;
      }
    }
  });
}

function updateReservationTime() {
  const date = document.querySelector('.date-picker')?.value;
  const time = document.querySelector('.time-picker')?.value;
  if (date && time) {
    document.getElementById('reservation-time').textContent = `${date} ${time}`;
  }
}

let map, marker, geocoder;

function loadMap() {
  const mapContainer = document.getElementById('map');
  if (!mapContainer) return;

  geocoder = new google.maps.Geocoder();
  // 1. owner 주소 변환
  geocoder.geocode({ address: ownerAddress }, (results, status) => {
    if (status !== 'OK' || !results[0]) {
      alert('owner 주소를 찾을 수 없습니다.');
      return;
    }
    const ownerLoc = results[0].geometry.location;
    map = new google.maps.Map(mapContainer, { center: ownerLoc, zoom: 14 });
    marker = new google.maps.Marker({ map, position: ownerLoc, label: '나' });

    // **주소 input과 사이드바 동기화**
    document.getElementById('addressInput').value = ownerAddress;
    document.getElementById('reservation-location').textContent = ownerAddress;

    // 2km 원 그리기
    circle = new google.maps.Circle({
      map,
      center: ownerLoc,
      radius: 2000,
      fillColor: '#cceeff',
      strokeColor: '#3399ff',
      strokeOpacity: 0.8,
      fillOpacity: 0.2
    });

    // 기존 시터 마커 생성 로직...
    sitterList.forEach(sitter => {
      geocoder.geocode({ address: sitter.address }, (res, stat) => {
        if (stat === 'OK' && res[0]) {
          const sitterLoc = res[0].geometry.location;
          const distance = google.maps.geometry.spherical.computeDistanceBetween(ownerLoc, sitterLoc);
          if (distance <= 2000) {
            const sitterMarker = new google.maps.Marker({
              map,
              position: sitterLoc,
              label: sitter.name,
              title: sitter.name
            });
            sitterMarkers.push(sitterMarker);
            google.maps.event.addListener(sitterMarker, 'click', function() {
              selectedSitterId = sitter.id;
              document.getElementById('reserve-modal').style.display = 'block';
            });
          }
        } else {
          console.warn('시터 주소 지오코딩 실패:', sitter.address);
        }
      });
    });

    // 맵 클릭 시 위치 변경 & 사이드바 업데이트
    map.addListener('click', e => {
      marker.setPosition(e.latLng);
      geocoder.geocode({ location: e.latLng }, (results, status) => {
        if (status === 'OK' && results[0]) {
          const addr = results[0].formatted_address;
          document.getElementById('addressInput').value = addr;
          document.getElementById('reservation-location').textContent = addr;
        }
      });
    });
  });
}

function searchAddress(address) {
  if (!address.trim() || !geocoder) return;

  geocoder.geocode({ address }, (results, status) => {
    if (status === 'OK' && results[0]) {
      const loc = results[0].geometry.location;
      if (map && marker) {
        map.setCenter(loc);
        marker.setPosition(loc);
      }
      const formatted = results[0].formatted_address;
      document.getElementById('addressInput').value = formatted;
      document.getElementById('reservation-location').textContent = formatted;

      // 기존 시터 마커 제거
      sitterMarkers.forEach(m => m.setMap(null));
      sitterMarkers = [];

      // 기존 원 제거
      if (circle) {
        circle.setMap(null);
        circle = null;
      }

      // 새 2km 원 그리기
      circle = new google.maps.Circle({
        map,
        center: loc,
        radius: 2000,
        fillColor: '#cceeff',
        strokeColor: '#3399ff',
        strokeOpacity: 0.8,
        fillOpacity: 0.2
      });

      // 2km 이내 시터만 생성
      sitterList.forEach(sitter => {
        geocoder.geocode({ address: sitter.address }, (res, stat) => {
          if (stat === 'OK' && res[0]) {
            const sitterLoc = res[0].geometry.location;
            const distance = google.maps.geometry.spherical.computeDistanceBetween(loc, sitterLoc);
            if (distance <= 2000) {
              const sitterMarker = new google.maps.Marker({
                map,
                position: sitterLoc,
                label: sitter.name,
                title: sitter.name
              });
              sitterMarkers.push(sitterMarker);
              google.maps.event.addListener(sitterMarker, 'click', function () {
                selectedSitterId = sitter.id;
                document.getElementById('reserve-modal').style.display = 'block';
              });
            }
          } else {
            console.warn('시터 주소 지오코딩 실패:', sitter.address);
          }
        });
      });

    } else {
      alert('주소를 찾을 수 없습니다.');
    }
  });
}

function enableAddressAutocomplete() {
  const input = document.getElementById('addressInput');
  if (!input || !google?.maps?.places) return;
  const autocomplete = new google.maps.places.Autocomplete(input, {
    types: ['geocode'],
    componentRestrictions: { country: 'kr' }
  });
  autocomplete.addListener('place_changed', () => {
    const place = autocomplete.getPlace();
    if (place.formatted_address) input.value = place.formatted_address;
  });
}

window.addEventListener('DOMContentLoaded', () => {
  if (reservationHeader) {
    reservationHeader.innerHTML = serviceTemplates.daycare;
    initializePickers();
    const iv = setInterval(() => {
      if (google?.maps) {
        loadMap();
        clearInterval(iv);
      }
    }, 100);
  }

  // 초기 사이드바 위치 동기화
  document.getElementById('reservation-location').textContent = ownerAddress;

  tabs.forEach(tab => tab.addEventListener('click', () => {
    tabs.forEach(t => t.classList.remove('active'));
    tab.classList.add('active');
    const type = tab.dataset.type;
    reservationHeader.innerHTML = serviceTemplates[type];
    initializePickers();
    setTimeout(loadMap, 100);
    ['reservation-service', 'reservation-location', 'reservation-time'].forEach(id => {
      document.getElementById(id).textContent = '';
    });
    document.getElementById('reservation-service').textContent = `${tab.textContent} 서비스`;
    selectedSitterPrice = 30000;
    window.priceConfirmed = false;
  }));

  document.querySelectorAll('.details-btn')
    .forEach((btn, i) => btn.addEventListener('click', () => selectedSitterId = i + 1));

  document.addEventListener('keydown', e => {
    if (e.key === 'Enter' && document.activeElement.id === 'addressInput') {
      searchAddress(e.target.value);
    }
  });

  const ac = setInterval(() => {
    if (google?.maps?.places) {
      enableAddressAutocomplete();
      clearInterval(ac);
    }
  }, 100);
});
document.querySelectorAll(".details-btn").forEach((btn, index) => {
  btn.addEventListener("click", () => {
    selectedSitterId = btn.dataset.id;

    const dayPrice = parseInt(btn.dataset.dayprice || "0");
    const hotelPrice = parseInt(btn.dataset.hotelprice || "0");
    const walkPrice = parseInt(btn.dataset.walkprice || "0");

    let price = 0;
    let total = 0;
    const activeTab = document.querySelector(".tab.active");
    const serviceType = activeTab?.dataset.type;
    const timeText = document.getElementById("reservation-time")?.textContent || "";

    if (serviceType === "daycare") {
      price = dayPrice;
      total = price; // 데이케어는 날짜 차이 계산이 필요 없으므로 그대로 설정
    } else if (serviceType === "hotel") {
      price = hotelPrice;

      // 호텔링 예약 시, 날짜 차이 계산
      if (timeText.includes("~")) {
        const [startStr, endStr] = timeText.split("~").map(s => s.trim());
        const startDate = new Date(startStr);
        const endDate = new Date(endStr);
        const diffDays = Math.max(1, Math.round((endDate - startDate) / (1000 * 60 * 60 * 24)));
        total = hotelPrice * diffDays; // 호텔링만 날짜 차이를 곱해서 total 계산
      }
    } else if (serviceType === "walk") {
      price = walkPrice;
      total = price; // 산책도 날짜 차이 없이 가격 그대로 적용
    }

    // 금액 출력
    document.getElementById("reservation-price").textContent = `${price.toLocaleString()}원`;
    document.getElementById("reservation-total").textContent = `${total.toLocaleString()}원`;

    selectedSitterPrice = price;
    priceConfirmed = true;

    console.log(`선택된 시터 ID: ${selectedSitterId}, 가격: ${price.toLocaleString()}원, 총 결제 금액: ${total.toLocaleString()}원`);
  });
});
