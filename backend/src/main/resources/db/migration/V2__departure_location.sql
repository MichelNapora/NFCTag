alter table presence add column departure_location_status varchar(255);
alter table presence add column departure_distance_meters float(53);

alter table presence add constraint ck_presence_departure_location
    check (departure_location_status in
           ('VERIFIED', 'TOO_FAR', 'NO_GPS', 'IMPRECISE', 'TAG_NOT_CALIBRATED'));