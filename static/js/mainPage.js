document.addEventListener("DOMContentLoaded", () => {
	// swiper 설정
	const swiper = new Swiper('.swiper', {
		loop: true,
		autoplay: {
			delay: 15000,
			disableOnInteraction: false,
		},
		navigation: {
			nextEl: '.swiper-button-next',
			prevEl: '.swiper-button-prev',
		},
	});

	// 서비스 카드 애니메이션
	const serviceSection = document.querySelector(".services");

	setTimeout(() => {
		const cards = document.querySelectorAll(".service-cards .card");
		console.log("카드 개수:", cards.length);

		function revealCardsOnScroll() {
			const triggerPoint = serviceSection.offsetTop - window.innerHeight / 2;
			console.log("스크롤Y:", window.scrollY, "트리거포인트:", triggerPoint);

			if (window.scrollY > triggerPoint) {
				serviceSection.classList.add("show");
				cards.forEach((card, index) => {
					setTimeout(() => {
						card.classList.add("show");
					}, index * 200);
				});

				window.removeEventListener("scroll", revealCardsOnScroll);
			}
		}

		window.addEventListener("scroll", revealCardsOnScroll);
		revealCardsOnScroll();
	}, 100);
});
