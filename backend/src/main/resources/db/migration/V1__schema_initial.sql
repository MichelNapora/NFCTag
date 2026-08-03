-- Schéma initial de NFCTag.
-- Généré depuis les entités JPA, puis nommé lisiblement.
-- Les tables sont créées dans l'ordre de leurs dépendances.

create table address (
                         id          uuid         not null,
                         street      varchar(255) not null,
                         number      integer      not null,
                         box         varchar(255),
                         postal_code integer      not null,
                         city        varchar(255) not null,
                         constraint pk_address        primary key (id),
                         constraint ck_address_postal check (postal_code between 4000 and 4999)
);

create table business (
                          id   uuid         not null,
                          name varchar(255) not null,
                          bce  varchar(255) not null,
                          constraint pk_business     primary key (id),
                          constraint uk_business_bce unique (bce)
);

create table employee (
                          id              uuid         not null,
                          firstname       varchar(255) not null,
                          lastname        varchar(255) not null,
                          email           varchar(255) not null,
                          password_hash   varchar(255) not null,
                          role            varchar(255) not null,
                          failed_attempts integer      not null,
                          locked          boolean      not null,
                          constraint pk_employee       primary key (id),
                          constraint uk_employee_email unique (email),
                          constraint ck_employee_role  check (role in ('ADMIN', 'EMPLOYEE'))
);

create table building (
                          id           uuid         not null,
                          name         varchar(255) not null,
                          project_code varchar(8)   not null,
                          address_id   uuid         not null,
                          constraint pk_building         primary key (id),
                          constraint uk_building_code    unique (project_code),
                          constraint uk_building_address unique (address_id),
                          constraint fk_building_address foreign key (address_id) references address (id)
);

create table wing (
                      id          uuid         not null,
                      name        varchar(255) not null,
                      building_id uuid         not null,
                      constraint pk_wing                 primary key (id),
                      constraint uk_wing_name_per_building unique (name, building_id),
                      constraint fk_wing_building        foreign key (building_id) references building (id)
);

create table tag (
                     id            uuid not null,
                     scan_token    uuid not null,
                     latitude      float(53),
                     longitude     float(53),
                     calibrated_at timestamp(6) with time zone,
                     wing_id       uuid not null,
                     constraint pk_tag       primary key (id),
                     constraint uk_tag_token unique (scan_token),
                     constraint uk_tag_wing  unique (wing_id),
                     constraint fk_tag_wing  foreign key (wing_id) references wing (id)
);

create table technician (
                            id           uuid         not null,
                            firstname    varchar(255) not null,
                            lastname     varchar(255) not null,
                            mobile       varchar(255) not null,
                            device_token uuid         not null,
                            business_id  uuid         not null,
                            constraint pk_technician          primary key (id),
                            constraint uk_technician_mobile   unique (mobile),
                            constraint uk_technician_device   unique (device_token),
                            constraint fk_technician_business foreign key (business_id) references business (id)
);

create table presence (
                          id                uuid    not null,
                          technician_id     uuid    not null,
                          tag_id            uuid    not null,
                          arrived_at        timestamp(6) with time zone not null,
                          departed_at       timestamp(6) with time zone,
                          estimated         boolean not null,
                          location_verified boolean not null,
                          location_status   varchar(255),
                          distance_meters   float(53),
                          constraint pk_presence            primary key (id),
                          constraint ck_presence_location   check (location_status in
                                                                   ('VERIFIED', 'TOO_FAR', 'NO_GPS', 'IMPRECISE', 'TAG_NOT_CALIBRATED')),
                          constraint fk_presence_technician foreign key (technician_id) references technician (id),
                          constraint fk_presence_tag        foreign key (tag_id)        references tag (id)
);