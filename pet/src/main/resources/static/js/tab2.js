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
});
