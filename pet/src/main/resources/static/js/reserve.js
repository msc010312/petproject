// 탭 콘텐츠 및 지도, 주소 기능 포함 JS 전체 정리

const reservationHeader = document.getElementById('reservationHeader');
const tabs = document.querySelectorAll('.tab');

// 탭별 템플릿 정의
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
      <label>반려동물 정보나 요청사항</label>
      <textarea></textarea>
    </div>
    <div class="form-group">
      <label>위치</label>
      <input type="text" id="addressInput" placeholder="예: 서울특별시 강남구">
      <p>주소를 입력하거나 지도에서 직접 위치를 선택해주세요</p>
      <div id="map" style="width: 100%; height: 400px;"></div>
    </div>
  `,
  hotel: `
    <h2>호텔링 예약</h2>
    <div class="form-group">
      <label>체크인/체크아웃 날짜</label>
      <div class="date-time">
        <div class="input-icon">
          <input type="text" class="date-picker" placeholder="📅 체크인 날짜를 선택해주세요">
        </div>
        <div class="input-icon">
          <input type="text" class="date-picker" placeholder="📅 체크아웃 날짜를 선택해주세요">
        </div>
      </div>
    </div>
    <div class="form-group">
      <label>기타 요청사항</label>
      <textarea></textarea>
    </div>
    <div class="form-group">
      <label>호텔링 위치</label>
      <input type="text" id="addressInput" placeholder="예: 부산광역시 해운대구">
      <p>주소를 입력하거나 지도에서 직접 위치를 선택해주세요</p>
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
      <label>산책 거리나 반려동물 성향</label>
      <textarea></textarea>
    </div>
    <div class="form-group">
      <label>출발 위치</label>
      <input type="text" id="addressInput" placeholder="예: 인천광역시 연수구">
      <p>주소를 입력하거나 지도에서 직접 위치를 선택해주세요</p>
      <div id="map" style="width: 100%; height: 400px;"></div>
    </div>
  `
};

function initializePickers() {
  flatpickr('.date-picker', {
    dateFormat: "Y-m-d",
    minDate: "today"
  });

  flatpickr('.time-picker', {
    enableTime: true,
    noCalendar: true,
    dateFormat: "H:i",
    time_24hr: true
  });
}

let map;
let marker;
let geocoder;
let autocomplete;

function initMap() {
  const mapContainer = document.getElementById("map");
  if (!mapContainer) return;

  const defaultLoc = { lat: 37.4979, lng: 127.0276 };
  map = new google.maps.Map(mapContainer, {
    center: defaultLoc,
    zoom: 14
  });

  marker = new google.maps.Marker({
    position: defaultLoc,
    map: map,
    title: "기본 위치"
  });

  geocoder = new google.maps.Geocoder();
}

function searchAddress() {
  const addressInput = document.getElementById("addressInput");
  if (!addressInput) return;

  const address = addressInput.value;
  if (!address) return alert("주소를 입력해주세요.");

  geocoder.geocode({ address: address }, (results, status) => {
    if (status === "OK") {
      const location = results[0].geometry.location;
      map.setCenter(location);
      marker.setPosition(location);
    } else {
      alert("주소를 찾을 수 없습니다: " + status);
    }
  });
}

function initAutocomplete() {
  const input = document.getElementById("addressInput");
  if (!input) return;

  if (autocomplete && autocomplete.unbindAll) {
    autocomplete.unbindAll();
    autocomplete = null;
  }

  autocomplete = new google.maps.places.Autocomplete(input);
  autocomplete.setFields(["geometry", "formatted_address"]);

  autocomplete.addListener("place_changed", () => {
    const place = autocomplete.getPlace();
    if (!place.geometry) {
      alert("선택한 장소에 위치 정보가 없습니다.");
      return;
    }

    const location = place.geometry.location;
    map.setCenter(location);
    marker.setPosition(location);
    input.value = place.formatted_address;
    searchAddress();
  });
}

window.addEventListener("DOMContentLoaded", () => {
  initializePickers();

  const waitForGoogle = setInterval(() => {
    if (window.google && window.google.maps && window.google.maps.places) {
      initMap();
      initAutocomplete();
      clearInterval(waitForGoogle);
    }
  }, 100);

  tabs.forEach(tab => {
    tab.addEventListener('click', () => {
      tabs.forEach(t => t.classList.remove('active'));
      tab.classList.add('active');

      const type = tab.dataset.type;
      reservationHeader.innerHTML = serviceTemplates[type];
      initializePickers();

      setTimeout(() => {
        if (window.google && window.google.maps && window.google.maps.places) {
          initMap();
          initAutocomplete();
        }
      }, 0);
    });
  });

  document.addEventListener("keydown", function (e) {
    if (e.key === "Enter") {
      const addressInput = document.getElementById("addressInput");
      if (addressInput === document.activeElement) {
        e.preventDefault();
        searchAddress();
      }
    }
  });
});
