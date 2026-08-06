create index ix_presence_tag        on presence (tag_id);
create index ix_presence_technician on presence (technician_id);
create index ix_technician_business on technician (business_id);
create index ix_wing_building       on wing (building_id);