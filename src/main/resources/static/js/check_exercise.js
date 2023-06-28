
    function check_exercise() {
    var score = 0;
    var questions = document.querySelectorAll(".question");
    for (var i = 0; i < questions.length; i++) {
    var answer = questions[i].querySelector("input[type='radio']:checked");
    if (answer)
    score += +answer.value;
}
    if (score == 3) {
    document.getElementById('comment').textContent = "Отлично! Все правильно!";
} else {
    document.getElementById('comment').textContent = "Перечитайте теорию и попробуйте еще раз";
}
}


    function check() {
        var submit = document.getElementsByName('submit')[0];
        var notChecked = false;
        if (!document.getElementById('exercise').checked) notChecked = true;
        if(!notChecked) submit.disabled = '';
        else submit.disabled = 'disabled';
    }
