function check_answers() {
    var score = 0;
    var questions = document.querySelectorAll(".question");
    for (var i = 0; i < questions.length; i++) {
        var answer = questions[i].querySelector("input[type='radio']:checked");
        if (answer)
            score += +answer.value;
    }
    document.getElementById('score').value = score;
}

