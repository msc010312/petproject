document.addEventListener("DOMContentLoaded", () => {
    const addBtn = document.getElementById("addBtn");
    const delBtn = document.getElementById("delBtn");
    const isLoggedInElem = document.getElementById("isLoggedIn");
    const delSuccessElem = document.getElementById("delSuccessMessage");

    // 글 작성 버튼 클릭 시
    if (addBtn && isLoggedInElem) {
        const isLoggedIn = isLoggedInElem.value === "true";

        addBtn.addEventListener("click", () => {
            if (!isLoggedIn) {
                alert("로그인이 필요합니다.");
                window.location.href = "/login";
            } else {
                window.location.href = "/board/add";
            }
        });
    }



    if (delSuccessElem) {
        const msg = delSuccessElem.dataset.message;
        if (msg && msg.trim() !== "") {
            alert(msg);
        }
    }
});

// 글 삭제 확인 함수
function confirmDelete() {
    return confirm("정말로 이 글을 삭제하시겠습니까?");
}
