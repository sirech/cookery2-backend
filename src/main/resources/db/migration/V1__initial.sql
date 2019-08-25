create table recipes (
  id serial primary key,
  name varchar(255) unique,
  servings number not null,
  created_at date not null,
  updated_at date not null
);