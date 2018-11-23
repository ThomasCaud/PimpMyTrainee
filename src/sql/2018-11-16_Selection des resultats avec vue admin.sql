SELECT * FROM records
JOIN quizzes ON records.quiz = quizzes.id
JOIN (
	-- get number of respondent
	select quiz, count(distinct trainee) as nbRespondents from records
	join recordanswer on records.id = recordanswer.record
	group by quiz
) nbRespondentByQuiz on nbRespondentByQuiz.quiz = records.quiz
JOIN (
	-- get rank
	select trainee, score,
	RANK() OVER(ORDER BY score desc) scoreRank
	from records
) scoreRank on scoreRank.trainee = records.trainee
JOIN (
	-- get top score and its duration
	select quiz, score as bestscore, duration as durationofbestscore
    from records
    order by score desc
    limit 1
) bestResponses on bestResponses.quiz = records.quiz
WHERE records.trainee like 21 AND title like '%%' AND isActive like 1;