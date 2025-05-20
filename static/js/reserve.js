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
      <label>반려동물 정보나 요청사항</label>
      <textarea></textarea>
    </div>
    <div class="form-group">
      <label>위치</label>
      <input type="text" placeholder="예: 서울특별시 강남구">
      <p>주소를 입력하거나 지도에서 직접 위치를 선택해주세요</p>
      <div class="map">지도</div>
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
      <input type="text" placeholder="예: 부산광역시 해운대구">
      <p>주소를 입력하거나 지도에서 직접 위치를 선택해주세요</p>
      <div class="map">지도</div>
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
      <input type="text" placeholder="예: 인천광역시 연수구">
      <p>주소를 입력하거나 지도에서 직접 위치를 선택해주세요</p>
      <div class="map">지도</div>
    </div>
  `
};

tabs.forEach(tab => {
  tab.addEventListener('click', () => {

    tabs.forEach(t => t.classList.remove('active'));

    tab.classList.add('active');

    const type = tab.dataset.type;
    reservationHeader.innerHTML = serviceTemplates[type];
  });
});
document.addEventListener('DOMContentLoaded', () => {
  initializePickers();

  tabs.forEach(tab => {
    tab.addEventListener('click', () => {
      tabs.forEach(t => t.classList.remove('active'));
      tab.classList.add('active');

      const type = tab.dataset.type;
      reservationHeader.innerHTML = serviceTemplates[type];
      initializePickers();
    });
  });
});

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
// document.addEventListener('DOMContentLoaded', () => {
//   fetch('reserve-modal.html')
//     .then(response => response.text())
//     .then(data => {
//       document.getElementById('modalContainer').innerHTML = data;

//       const modal = document.getElementById('sitterModal');
//       const closeModal = modal.querySelector('.close-btn');

//       document.addEventListener('click', function (e) {
//         if (e.target.classList.contains('details-btn')) {
//           modal.classList.remove('hidden');
//         }
//       });


//       closeModal.addEventListener('click', () => {
//         modal.classList.add('hidden');
//       });


      // window.addEventListener('click', (e) => {
      //   if (e.target === modal) {
      //     modal.classList.add('hidden');
      //   }
      // });
//     });
// });

document.addEventListener("DOMContentLoaded", function () {
  const track = document.querySelector(".slider-track");
  const items = document.querySelectorAll(".sitter-review-img");
  const prevBtn = document.querySelector(".prev");
  const nextBtn = document.querySelector(".next");

  const itemWidth = 220; 
  const visibleCount = 4;
  let currentIndex = 0;

  function updateSlider() {
    const offset = currentIndex * itemWidth;
    track.style.transform = `translateX(-${offset}px)`;
  }

  prevBtn.addEventListener("click", function (e) {
    e.preventDefault();
    if (currentIndex > 0) {
      currentIndex--;
      updateSlider();
    }
  });

  nextBtn.addEventListener("click", function (e) {
    e.preventDefault();
    if (currentIndex < items.length - visibleCount) {
      currentIndex++;
      updateSlider();
    }
  });

  updateSlider();
});





