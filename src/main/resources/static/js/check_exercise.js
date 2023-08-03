var checkedQuestions = 0;

function check_exercise1() {
    var score = 0;
    var questions = document.querySelectorAll(".question1");
    for (var i = 0; i < questions.length; i++) {
        var answer = questions[i].querySelector("input[type='radio']:checked");
        if (answer)
            score += +answer.value;
    }

    if (score == 3) {
        checkedQuestions++;
        document.getElementById("comment1").textContent = "Отлично! Все правильно!";
    } else {
        document.getElementById("comment1").textContent = "Перечитайте теорию и попробуйте еще раз";
    }

}

function check_exercise2() {
    var score = 0;
    var questions = document.querySelectorAll(".question2");
    for (var i = 0; i < questions.length; i++) {
        var answer = questions[i].querySelector("input[type='radio']:checked");
        if (answer)
            score += +answer.value;
    }


    if (score == 3) {
        checkedQuestions++;
        document.getElementById("comment2").textContent = "Отлично! Все правильно!";
    } else {
        document.getElementById("comment2").textContent = "Перечитайте теорию и попробуйте еще раз";
    }

}

function check_exercise3() {
    var score = 0;
    var questions = document.querySelectorAll(".question3");
    for (var i = 0; i < questions.length; i++) {
        var answer = questions[i].querySelector("input[type='radio']:checked");
        if (answer)
            score += +answer.value;
    }

    if (score == 3) {
        checkedQuestions++;
        document.getElementById("comment3").textContent = "Отлично! Все правильно!";
    } else {
        document.getElementById("comment3").textContent = "Перечитайте теорию и попробуйте еще раз";
    }

}


function check_topic() {
    if (checkedQuestions == 3) {
        document.getElementById("result").textContent = "Тема пройдена ✅ Так держать!";
        document.getElementById("submit").disabled = false;
    } else {
        new bootstrap.Modal(document.getElementById('topicDoneError'), {keyboard: false}).show();
    }
}



