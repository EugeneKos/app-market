create table persons (
    id bigint not null unique,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    middle_name varchar(255) not null,
    constraint persons_pkey primary key (id)
);

create sequence person_id_seq minvalue 1 start with 1 increment by 1;

create table users (
    id bigint not null unique,
    username varchar(255) not null,
    password varchar(255) not null,
    password_attempt_count integer not null,
    status varchar(255) not null,
    timestamp_status timestamp not null,
    person_id bigint not null,
    constraint users_pkey primary key (id),
    constraint username_person_uq unique (username, person_id),
    constraint user_person_fk foreign key (person_id) references persons(id)
);

create sequence user_id_seq minvalue 1 start with 1 increment by 1;

create table money_account (
    id bigint not null unique,
    balance numeric(19,2) not null,
    name varchar(255) not null,
    description varchar(255),
    date_created date not null,
    person_id bigint not null,
    constraint money_account_pkey primary key (id),
    constraint money_account_name_uq unique (name, person_id),
    constraint money_account_person_fk foreign key (person_id) references persons(id)
);

create sequence money_account_id_seq minvalue 1 start with 1 increment by 1;

create table operation (
    id bigint not null unique,
    amount numeric(19,2) not null,
    description varchar(255),
    operation_type varchar(255),
    date date not null,
    time time not null,
    old_balance numeric(19,2) not null,
    new_balance numeric(19,2) not null,
    money_account_id bigint not null,
    constraint operation_pkey primary key (id),
    constraint operation_money_account_fk foreign key (money_account_id) references money_account(id)
);

create sequence operation_id_seq minvalue 1 start with 1 increment by 1;

create table cost_limit (
    id bigint not null unique,
    description varchar(255) not null,
    sum numeric(19,2) not null,
    begin_date date not null,
    end_date date not null,
    person_id bigint not null,
    constraint cost_limit_pkey primary key (id),
    constraint cost_limit_uq unique (sum, begin_date, end_date),
    constraint cost_limit_person_fk foreign key (person_id) references persons(id)
);

create sequence cost_limit_id_seq minvalue 1 start with 1 increment by 1;

create table cost (
    id bigint not null unique,
    category varchar(255) not null,
    description varchar(255) not null,
    sum numeric(19,2) not null,
    date date not null,
    time time not null,
    cost_limit_id bigint not null,
    operation_id bigint not null,
    constraint cost_pkey primary key (id),
    constraint cost_operation_uq unique (id, operation_id),
    constraint cost_operation_fk foreign key (operation_id) references operation(id),
    constraint cost_cost_limit_fk foreign key (cost_limit_id) references cost_limit(id)
);

create sequence cost_id_seq minvalue 1 start with 1 increment by 1;