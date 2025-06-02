document.addEventListener("DOMContentLoaded", () => {
  const detailBtn = document.querySelectorAll(".details-btn");
  const modal = document.getElementById("reserve-modal");
  const closeBtn = document.querySelector(".close-btn");

 detailBtn.forEach((btn) => {
   btn.addEventListener("click", () => {
     modal.style.display = "flex";
   });
 })

  closeBtn.addEventListener("click", ()=>{
    modal.style.display = "none"
  })
});
