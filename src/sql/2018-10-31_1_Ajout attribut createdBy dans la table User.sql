ALTER TABLE Users ADD COLUMN
	`createdBy` bigint(20) unsigned;
    
ALTER TABLE Users ADD CONSTRAINT
	FOREIGN KEY (createdBy) REFERENCES Users(id) ON DELETE CASCADE;

-- Needed if you want to update values without using key in where clause
SET SQL_SAFE_UPDATES = 0;
update Users set createdBy = (
	-- Needed beacause InnoDB can't manage two instance of a same table in update clause
	select id from (select id from Users where role = "admin" limit 1) creator
) where role = "trainee";