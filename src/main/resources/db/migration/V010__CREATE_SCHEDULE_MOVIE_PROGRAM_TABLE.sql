CREATE TABLE schedule_movie_program
(
    schedule_id BIGINT AUTO_INCREMENT NOT NULL,
    movie_program_id BIGINT AUTO_INCREMENT NOT NULL,
    primary key (schedule_id, movie_program_id),
    foreign key (schedule_id) references schedules(id),
    foreign key (movie_program_id) references movie_programs(schedule_id)
);