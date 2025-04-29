const menuService = document.querySelector('.menu-service');
const submenu = document.querySelector('.sub-menu');

let timeout;

menuService.addEventListener('mouseenter', () => {
    clearTimeout(timeout);
    submenu.classList.remove('hide');
    submenu.classList.add('show');
});

menuService.addEventListener('mouseleave', () => {
    submenu.classList.remove('show');
    submenu.classList.add('hide');

    // 애니메이션 후 숨김 처리
    timeout = setTimeout(() => {
        submenu.classList.remove('hide');
    }, 500); // 사라지는 딜레이 시간
});