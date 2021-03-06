    create table category (
       id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB
GO
    create table product_categories (
       product_id bigint not null,
        categoty_id bigint not null
    ) engine=InnoDB
GO
    create table products (
       id bigint not null auto_increment,
        cost decimal(19,2),
        description varchar(255) not null,
        title varchar(255) not null,
        primary key (id)
    ) engine=InnoDB
GO
    create table roles (
       id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB
GO
    create table users (
       id bigint not null auto_increment,
        age integer,
        email varchar(255),
        name varchar(32) not null,
        password varchar(128) not null,
        primary key (id)
    ) engine=InnoDB
GO
    create table users_roles (
       user_id bigint not null,
        role_id bigint not null,
        primary key (user_id, role_id)
    ) engine=InnoDB
GO
    alter table roles
       add constraint UK_ofx66keruapi6vyqpv6f2or37 unique (name)
GO
    alter table product_categories
       add constraint FK26v0rnjfnu7lkg47txe1rcem9
       foreign key (categoty_id)
       references category (id)
GO
    alter table product_categories
       add constraint FKlda9rad6s180ha3dl1ncsp8n7
       foreign key (product_id)
       references products (id)
GO
    alter table users_roles
       add constraint FKj6m8fwv7oqv74fcehir1a9ffy
       foreign key (role_id)
       references roles (id)
GO
    alter table users_roles
       add constraint FK2o0jvgh89lemvvo17cbqvdxaa
       foreign key (user_id)
       references users (id)
GO