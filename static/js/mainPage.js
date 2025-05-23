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

const ctx = document.getElementById('myChart').getContext('2d');

const myChart = new Chart(ctx, {
  type: 'bar',
  data: {
    datasets: [{
      label: 'Best Sitter',
      data: [
        { x: 4.7, y: 'DayCare', name: '이현정', rating: 4.7 },
        { x: 4.2, y: 'Hoteling', name: '김준희', rating: 4.2 },
        { x: 2.2, y: 'Walking', name: '윤성환', rating: 2.2 }
      ],
      parsing: {
        xAxisKey: 'x',
        yAxisKey: 'y'
      },
      backgroundColor: [
        'rgba(255, 99, 132, 0.2)',
        'rgba(54, 162, 235, 0.2)',
        'rgba(255, 206, 86, 0.2)'
      ],
      borderColor: [
        'rgba(255, 99, 132, 1)',
        'rgba(54, 162, 235, 1)',
        'rgba(255, 206, 86, 1)'
      ],
      borderWidth: 1
    }]
  },
  options: {
    indexAxis: 'y',
    plugins: {
      tooltip: {
        callbacks: {
          title: (context) => `서비스: ${context[0].raw.y}`,
          label: (context) => {
            const data = context.raw;
            return `시터: ${data.name}, 평점: ${data.rating}`;
          }
        }
      }
    },
    scales: {
      x: {
        beginAtZero: true,
        max: 5,
        title: {
          display: true
        }
      },
      y: {
        title: {
          display: true
        }
      }
    }
  }
});

