function checkEntranceTest(){

    //store number of correct answer for each question
    var solutions = [2, 2, 3, 2, 4, 4, 2, 1, 4, 3];

    var numberOfQuestions = 10;
    var correctedAnswers = 0;

    for(var i=1; i<=numberOfQuestions; i++){

        //find out, which radio-button was checked for question i
        var result = document.querySelector('input[name=q'+i+']:checked');

        //number of correct answer for question i
        var solution = solutions[i-1];

        //r.value contains the number of the selected answer
        if(result && result.value == 'r'+solution){

            //increase number of correct answers
            correctedAnswers++;

            //highlight the correct answer
            document.getElementById('q'+i+'_r'+solution).parentNode.style.color='#5cb85c';
            document.getElementById('q'+i+'_r'+solution).parentNode.style.fontWeight='bold';
        }
    }

    //update the progress bar


    var correctAnswersNumber = correctedAnswers;

    document.getElementById("correctAnswersNumber").setAttribute("aria-valuenow", correctAnswersNumber);

    document.getElementById("result_message").innerHTML = "You have answered " + correctedAnswers + " questions correctly!";

    document.getElementById("result_progress").setAttribute("aria-valuenow", p);

    document.getElementById("result_progress").className = "progress-bar bg-success";

    if(p<=60){

        document.getElementById("result_progress").className = "progress-bar bg-warning";
    }

    if(p<=30){

        document.getElementById("result_progress").className = "progress-bar bg-danger";
    }


    return correctAnswersNumber;
}


function check_answers() {
    var score = 0;
    var questions = document.querySelectorAll(".question");
    for (var i = 0; i < questions.length; i++) {
        var answer = questions[i].querySelector("input[type='radio']:checked");
        if (answer)
            score += +answer.value;
    }

    document.getElementById('score').textContent =
        "Тест пройден! Правильных ответов " + score + " из " + questions.length + ".";
}
