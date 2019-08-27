create table recipes (
  id serial primary key,
  name varchar(255) not null,
  servings number not null,
  created_at date not null default now(),
  updated_at date not null default now() on update now()
);

create table ingredients (
  id serial primary key,
  name varchar(255) not null,
  quantity number not null,
  unit varchar(8) not null,

  created_at date not null default now(),
  updated_at date not null default now() on update now(),

  recipe_id int,
  foreign key (recipe_id) references  recipes (id)
);

create table steps (
  id serial primary key,
  description text not null,
  duration number not null,

  created_at date not null default now(),
  updated_at date not null default now() on update now(),

  recipe_id int,
  foreign key (recipe_id) references  recipes (id)
);
