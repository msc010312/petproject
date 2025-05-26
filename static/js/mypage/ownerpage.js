// const openModal = document.getElementById('openModal');
// const closeModal = document.getElementById('closeModal');
// const modal = document.getElementById('modalContainer');
// const 

// modalOpenButton.addEventListener('click', () => {
//   modal.classList.remove('hidden');
// });

// modalCloseButton.addEventListener('click', () => {
//   modal.classList.add('hidden');
// });

// window.addEventListener('click', function(event) {
//   if (event.target === modal) {
//        modal.classList.add('hidden');
//     }
// });



// HTML 문서의 로딩이 완료되었을 때, 해당 함수를 실행
document.addEventListener("DOMContentLoaded", function () {
  // elements
  var modalBtn = document.getElementById("modalBtn");
  var modal = document.getElementById("myModal");
  var closeBtn = document.getElementById("closeBtn");

  // functions
  function toggleModal() {
    modal.classList.toggle("show");
  }

  // events
  modalBtn.addEventListener("click", toggleModal);
  closeBtn.addEventListener("click", toggleModal);

  window.addEventListener("click", function (event) {
    // 모달의 검은색 배경 부분이 클릭된 경우 닫히도록 하는 코드
    if (event.target === modal) {
      toggleModal();
    }
  });
});