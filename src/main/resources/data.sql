insert into topic (id, value) values(1, 'temat 1');
insert into topic (id, value) values(2, 'temat 2');
insert into topic (id, value) values(3, 'temat 3');
insert into topic (id, value) values(4, 'temat 4');
insert into topic (id, value) values(5, 'temat 5');

insert into team (id, name) values(1000, 'Test drużyna');

insert into battle(id, topic_id) values(1000, 1);
insert into battle(id, topic_id) values(1001, 2);

-- insert into teams_battles(battle_id, team_id) values(1000, 1000);