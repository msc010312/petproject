document.addEventListener("DOMContentLoaded", () => {
    const addBtn = document.getElementById("addBtn");
    const isLoggedIn = document.getElementById("isLoggedIn").value === "true";

    addBtn.addEventListener("click", () => {
        if (!isLoggedIn) {
            alert("로그인이 필요합니다.");
            window.location.href = "/login";
        } else {
            window.location.href = "/board/add";
        }
    });
});