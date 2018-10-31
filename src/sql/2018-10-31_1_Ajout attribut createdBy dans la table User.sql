ALTER TABLE Users ADD COLUMN
	`managerId` bigint(20) unsigned;
    
ALTER TABLE Users ADD CONSTRAINT
	FOREIGN KEY (managerId) REFERENCES Users(id) ON DELETE CASCADE;

-- Needed if you want to update values without using key in where clause
SET SQL_SAFE_UPDATES = 0;
update Users set managerId = (
	-- Needed beacause InnoDB can't manage two instance of a same table in update clause
	select id from (select id from Users where role = "admin" limit 1) creator
) where role = "trainee";