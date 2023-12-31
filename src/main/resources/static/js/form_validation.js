//Взято с https://bootstrap-4.ru/docs/5.0/forms/validation/
function validateForm() {
    'use strict'
    // Получите все формы, к которым мы хотим применить пользовательские стили проверки Bootstrap
    const forms = document.querySelectorAll('.needs-validation');
    // const genre = document.getElementById("genre");
    // Зацикливайтесь на них и предотвращайте отправку
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }
                form.classList.add('was-validated')
            }, false)
        })
}


