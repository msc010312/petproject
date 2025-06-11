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
  flatpickr('.date-picker', { dateFormat: 'Y-m-d', minDate: 'today', onChange: updateReservationTime });
  flatpickr('.time-picker', { enableTime: true, noCalendar: true, dateFormat: 'H:i', time_24hr: true, onChange: updateReservationTime });
  flatpickr('.date-range-picker', { mode: 'range', dateFormat: 'Y-m-d', minDate: 'today', onClose: dates => {
      if (dates.length === 2) {
        const [inD, outD] = dates;
        document.getElementById('reservation-time').textContent = `${inD.toLocaleDateString()} ~ ${outD.toLocaleDateString()}`;
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
     document.getElementById('addressInput').value = ownerAddress;

    new google.maps.Circle({ map, center: ownerLoc, radius: 2000, fillColor: '#cceeff', strokeColor: '#3399ff', strokeOpacity: 0.8, fillOpacity: 0.2 });

    sitterList.forEach(sitter => {
      geocoder.geocode({ address: sitter.address }, (res, stat) => {
        if (stat === 'OK' && res[0]) {
          const sitterLoc = res[0].geometry.location;
          const distance = google.maps.geometry.spherical.computeDistanceBetween(ownerLoc, sitterLoc);
          if (distance <= 2000) {
            const sitterMarker = new google.maps.Marker({ map, position: sitterLoc, label: sitter.name, title: sitter.name });
            // 클릭 시 시터 상세 모달 열기 using Bootstrap Modal API
google.maps.event.addListener(sitterMarker, 'click', function() {
  selectedSitterId = sitter.id;
  document.getElementById('reserve-modal').style.display = 'block';
});
          }
        } else console.warn('시터 주소 지오코딩 실패:', sitter.address);
      });
    });

    map.addListener('click', e => {
      marker.setPosition(e.latLng);
      geocoder.geocode({ location: e.latLng }, (results, status) => {
        if (status === 'OK' && results[0]) {
          document.getElementById('addressInput').value = results[0].formatted_address;
          document.getElementById('reservation-location').textContent = results[0].formatted_address;
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
      const input = document.getElementById('addressInput');
      if (input) input.value = formatted;
      document.getElementById('reservation-location').textContent = formatted;

      // ✅ 기존 시터 마커 전부 제거
      sitterMarkers.forEach(marker => {
        marker.setMap(null);  // 지도에서 제거
      });
      sitterMarkers = []; // 배열 비우기

      // ✅ 기존 원 제거
      if (circle) {
        circle.setMap(null);  // 지도에서 제거
        circle = null;
      }

      // ✅ 새 2km 원 그리기
      circle = new google.maps.Circle({
        map,
        center: loc,
        radius: 2000,
        fillColor: '#cceeff',
        strokeColor: '#3399ff',
        strokeOpacity: 0.8,
        fillOpacity: 0.2
      });

      // ✅ 2km 이내 시터만 마커 생성
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

              sitterMarkers.push(sitterMarker); // 배열에 저장

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
  const autocomplete = new google.maps.places.Autocomplete(input, { types: ['geocode'], componentRestrictions: { country: 'kr' } });
  autocomplete.addListener('place_changed', () => {
    const place = autocomplete.getPlace();
    if (place.formatted_address) input.value = place.formatted_address;
  });
}

window.addEventListener('DOMContentLoaded', () => {
  if (reservationHeader) {
    reservationHeader.innerHTML = serviceTemplates.daycare;
    initializePickers();
    const iv = setInterval(() => { if (google?.maps) { loadMap(); clearInterval(iv); } }, 100);
  }

  tabs.forEach(tab => tab.addEventListener('click', () => {
    tabs.forEach(t => t.classList.remove('active'));
    tab.classList.add('active');
    const type = tab.dataset.type;
    reservationHeader.innerHTML = serviceTemplates[type];
    initializePickers();
    setTimeout(loadMap, 100);
    ['reservation-service','reservation-location','reservation-time','reservation-price','reservation-total'].forEach(id => document.getElementById(id).textContent = '');
    document.getElementById('reservation-service').textContent = `${tab.textContent} 서비스`;
    selectedSitterPrice = 30000; window.priceConfirmed = false;
  }));

  document.querySelectorAll('.details-btn').forEach((btn, i) => btn.addEventListener('click', () => selectedSitterId = i+1));

  document.addEventListener('keydown', e => {
    if (e.key === 'Enter' && document.activeElement.id === 'addressInput') searchAddress(e.target.value);
  });

  const ac = setInterval(() => { if (google?.maps?.places) { enableAddressAutocomplete(); clearInterval(ac); } }, 100);
});
