drop table if exists crew;

create table crew (
  idx bigint NOT NULL AUTO_INCREMENT comment '지원 순서',
  id varchar(15) not null comment '계정(ID)',
  join_date TIMESTAMP not null comment '가입 일시',
  name varchar(50) not null  comment '이름',
  email varchar(255) comment 'Email 주소',
  primary key (idx)
);