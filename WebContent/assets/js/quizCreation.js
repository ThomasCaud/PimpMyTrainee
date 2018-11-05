function colorPossibleAnswers() {
	$("input[type='radio']:checked").closest(".possibleAnswerRow").addClass('possibleAnswerRow-correct');
	$("input[type='radio']:checked").closest(".possibleAnswerRow").removeClass('possibleAnswerRow-incorrect');
	$("input[type='radio']:not(:checked)").closest(".possibleAnswerRow").addClass('possibleAnswerRow-incorrect');
	$("input[type='radio']:not(:checked)").closest(".possibleAnswerRow").removeClass('possibleAnswerRow-correct');
}

$(document).ready(function(){
	colorPossibleAnswers();
	
	$("input[type='radio']").click(function(){
		colorPossibleAnswers();
	})
	
	$("#btnNewQuestion").click(function(e){
		e.preventDefault();
	});
	
	$('[data-toggle="tooltip"]').tooltip()
});