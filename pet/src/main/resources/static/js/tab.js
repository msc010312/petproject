document.addEventListener("DOMContentLoaded", function () {
    const ownerTab = document.getElementById("owner");
    const sitterTab = document.getElementById("sitter");

    function toggleTab() {
        if (ownerTab.checked) {
            ownerSign.style.display = "block";
            sitterSign.style.display = "none";
        } else if (sitterTab.checked) {
            ownerSign.style.display = "none";
            sitterSign.style.display = "block";
        }
    }

    // 초기 로드 시 실행
    toggleTab();

    // 탭 변경 시 실행
    ownerTab.addEventListener("change", toggleTab);
    sitterTab.addEventListener("change", toggleTab);
});