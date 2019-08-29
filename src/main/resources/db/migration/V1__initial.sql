create table recipes (
  id serial primary key,
  name varchar(255) not null,
  servings integer not null,

  created_at datetime not null default CURRENT_TIMESTAMP,
  updated_at datetime not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP
);

create table ingredients (
  id serial primary key,
  name varchar(255) not null,
  quantity integer not null,
  unit varchar(8) not null,

  created_at datetime not null default CURRENT_TIMESTAMP,
  updated_at datetime not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,

  recipe_id bigint unsigned,
  foreign key (recipe_id) references  recipes (id)
);

create table steps (
  id serial primary key,
  description text not null,
  duration integer not null,

  created_at datetime not null default CURRENT_TIMESTAMP,
  updated_at datetime not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,

  recipe_id bigint unsigned,
  foreign key (recipe_id) references  recipes (id)
);
