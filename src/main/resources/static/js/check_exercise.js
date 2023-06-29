
    function check_exercise1() {
    var score = 0;
    var questions = document.querySelectorAll(".question1");
    for (var i = 0; i < questions.length; i++) {
    var answer = questions[i].querySelector("input[type='radio']:checked");
    if (answer)
    score += +answer.value;
}

        if (score == 3) {
            document.getElementById('exercise1_done').value = 1;
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
            document.getElementById('exercise2_done').value = 1;
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
            document.getElementById('exercise3_done').value = 1;
            document.getElementById("comment3").textContent = "Отлично! Все правильно!";
        } else {
            document.getElementById("comment3").textContent = "Перечитайте теорию и попробуйте еще раз";
        }

    }


    function check_topic() {
        // var submit = document.getElementsByName('submit')[0];
        let exercisesDone = document.getElementById('exercise1_done').value;
        exercisesDone += document.getElementById('exercise2_done').value;
        exercisesDone += document.getElementById('exercise3_done').value;
    if (exercisesDone == 3) {
        document.getElementById("result").textContent = 'Превосходно! Можно идти дальше!';
    } else {
        document.getElementById("result").textContent = 'ghgjfk';
        // new bootstrap.Modal(document.getElementById('topicDoneError'), {keyboard: false}).show();
    }
    }



