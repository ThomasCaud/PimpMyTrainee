function processQuizNext() {
	var possibleAnswerSelected = $('.runQuiz-possibleAnswer-selected');
	
	if(possibleAnswerSelected.length == 0) {
		$('#modalNoAnswerSelected').modal()
		return;
	}
	
	var indexOfAnswer = possibleAnswerSelected.data("index");
	
	if(indexOfAnswer == null) {
		alert("Something went wrong.");
		return;
	}
	
	$("input[name='quizAnswerIndex']").val(indexOfAnswer);
	$("form#formAnswerQuestion").submit();
}

function getSearchParameters() {
      var prmstr = window.location.search.substr(1);
      return prmstr != null && prmstr != "" ? transformToAssocArray(prmstr) : {};
}

function transformToAssocArray( prmstr ) {
    var params = {};
    var prmarr = prmstr.split("&");
    for ( var i = 0; i < prmarr.length; i++) {
        var tmparr = prmarr[i].split("=");
        params[tmparr[0]] = tmparr[1];
    }
    return params;
}

function colorPossibleAnswers() {
	$("input[type='radio']:checked").closest(".possibleAnswerRow").addClass('possibleAnswerRow-correct');
	$("input[type='radio']:checked").closest(".possibleAnswerRow").removeClass('possibleAnswerRow-incorrect');
	$("input[type='radio']:not(:checked)").closest(".possibleAnswerRow").addClass('possibleAnswerRow-incorrect');
	$("input[type='radio']:not(:checked)").closest(".possibleAnswerRow").removeClass('possibleAnswerRow-correct');
}

function initAndLaunchTimer() {
	// Set the date we're counting down to
	var startDate = new Date($("input[name='quizBeginningTimestamp']").val()).getTime();

	// Update the count down every 1 second
	var x = setInterval(function() {

	    // Get todays date and time
	    var now = new Date().getTime();
	    
	    // Find the distance between now and the count down date
	    var distance = now - startDate;
	    
	    // Time calculations for days, hours, minutes and seconds
	    var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
	    var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
	    var seconds = Math.floor((distance % (1000 * 60)) / 1000);
	    
	    // Output the result in an element with id="demo"
	    document.getElementById("quizTimer").innerHTML = hours + "h "
	    + minutes + "m " + seconds + "s ";
	    
	}, 1000);
}

$(document).ready(function(){
	colorPossibleAnswers();
	
	if( $("input[name='quizBeginningTimestamp']").length != 0 )
		initAndLaunchTimer();
	
	$(".runQuiz-possibleAnswer").click(function(){
		$(this).toggleClass('runQuiz-possibleAnswer-selected');
		$(this).siblings().removeClass('runQuiz-possibleAnswer-selected');
	})
	
	$("input[type='radio']").click(function(){
		colorPossibleAnswers();
	})
	
	$("#btnNewQuestion").click(function(e){
		e.preventDefault();
	});
	
	$('#btnQuizNext').click(function(e){
		e.preventDefault();
		processQuizNext();
	});
	
	$('[data-toggle="tooltip"]').tooltip()
	
	$('select#nbItemsPerPage').on("change",function(){
		var value = $('select#nbItemsPerPage option:selected').text();
		var url = window.location.href;
		var search = window.location.search.substr(1);
		url = url.replace(search,"")
		
		if(search == "")
			url += "?"

		url += "n="+value
			
		window.location.href = url;
	});
});