
create table recipes (
  id serial primary key,
  name varchar(255) not null,
  servings integer not null,

  created_at timestamp not null default CURRENT_TIMESTAMP,
  updated_at timestamp not null default CURRENT_TIMESTAMP
);

create table ingredients (
  id serial primary key,
  name varchar(255) not null,
  quantity integer not null,
  unit varchar(8) not null,

  created_at timestamp not null default CURRENT_TIMESTAMP,
  updated_at timestamp not null default CURRENT_TIMESTAMP,

  recipe_id bigint,
  foreign key (recipe_id) references  recipes (id)
);

create table steps (
  id serial primary key,
  description text not null,
  duration integer not null,

  created_at timestamp not null default CURRENT_TIMESTAMP,
  updated_at timestamp not null default CURRENT_TIMESTAMP,

  recipe_id bigint,
  foreign key (recipe_id) references  recipes (id)
);
