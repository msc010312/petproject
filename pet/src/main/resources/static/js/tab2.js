document.addEventListener("DOMContentLoaded", function () {
   document.querySelectorAll('.social-login-btn').forEach(btn => {
       btn.addEventListener('click', function (e) {
         e.preventDefault();

         const provider = this.dataset.provider;
         const selectedRole = document.querySelector('input[name="role"]:checked').value;

         fetch('/set-role', {
           method: 'POST',
           headers: {
             'Content-Type': 'application/x-www-form-urlencoded'
           },
           body: `role=${selectedRole}`
         }).then(() => {
           window.location.href = `/oauth2/authorization/${provider}`;
         });
       });
     });
});
