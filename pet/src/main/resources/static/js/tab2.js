document.addEventListener("DOMContentLoaded", function () {
   document.querySelectorAll('.social-login-btn').forEach(btn => {
       btn.addEventListener('click', function (e) {
         e.preventDefault();

         const provider = this.dataset.provider;
         const selectedRole = document.querySelector('input[name="role"]:checked').value;

         document.cookie = `oauth2_role=${selectedRole}; path=/; max-age=600`;

         window.location.href = `/oauth2/authorization/${provider}`;
       });
     });

         document.querySelectorAll('input[name="role"]').forEach(radio => {
             radio.addEventListener('change', function () {
                 document.getElementById('roleInput').value = this.value;
             });
         });

         const checkedRadio = document.querySelector('input[name="role"]:checked');
         if (checkedRadio) {
             document.getElementById('roleInput').value = checkedRadio.value;
         }
});
