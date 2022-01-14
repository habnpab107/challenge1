create database actor_movie_catalog;

use actor_movie_catalog;

create table actor (
actorId int unsigned auto_increment PRIMARY KEY,
actorName varchar(255) not null);

create table movie (
movieId int unsigned auto_increment PRIMARY KEY,
movieName varchar(255) not null);

create table catalog (
actorId int not null,
movieId int not null);

insert into actor (actorName) values ("Sean Connery");
insert into actor (actorName) values ("George Lazenby");
insert into actor (actorName) values ("Roger Moore");
insert into actor (actorName) values ("Timothy Dalton");
insert into actor (actorName) values ("Pierce Brosnan");
insert into actor (actorName) values ("Daniel Craig");


insert into movie (movieName) values ("Dr. No");
insert into movie (movieName) values ("From Russia with Love");
insert into movie (movieName) values ("Goldfinger");
insert into movie (movieName) values ("Thunderball");
insert into movie (movieName) values ("You Only Live Twice");
insert into movie (movieName) values ("Diamonds are Forever");
insert into movie (movieName) values ("On Her Majesty's Secret Service");
insert into movie (movieName) values ("Live and Let Die");
insert into movie (movieName) values ("The Man with the Golden Gun");
insert into movie (movieName) values ("The Spy Who Loved Me");
insert into movie (movieName) values ("Moonraker");
insert into movie (movieName) values ("For Your Eyes Only");
insert into movie (movieName) values ("Never Say Never Again");
insert into movie (movieName) values ("Octopussy");
insert into movie (movieName) values ("A View to a Kill");
insert into movie (movieName) values ("The Living Daylights");
insert into movie (movieName) values ("License to Kill");
insert into movie (movieName) values ("GoldenEye");
insert into movie (movieName) values ("Tomorrow Never Dies");
insert into movie (movieName) values ("The World is Not Enough");
insert into movie (movieName) values ("Die Another Day");
insert into movie (movieName) values ("Casino Royale");
insert into movie (movieName) values ("Quantum of Solace");
insert into movie (movieName) values ("Skyfall");
insert into movie (movieName) values ("Spectre");
insert into movie (movieName) values ("No Time to Die");

insert into catalog (actorId, movieId) values (1,1);
insert into catalog (actorId, movieId) values (1,2);
insert into catalog (actorId, movieId) values (1,3);
insert into catalog (actorId, movieId) values (1,4);
insert into catalog (actorId, movieId) values (1,5);
insert into catalog (actorId, movieId) values (1,6);
insert into catalog (actorId, movieId) values (2,7);
insert into catalog (actorId, movieId) values (3,8);
insert into catalog (actorId, movieId) values (3,9);
insert into catalog (actorId, movieId) values (3,10);
insert into catalog (actorId, movieId) values (3,11);
insert into catalog (actorId, movieId) values (3,12);
insert into catalog (actorId, movieId) values (1,13);
insert into catalog (actorId, movieId) values (3,14);
insert into catalog (actorId, movieId) values (3,15);
insert into catalog (actorId, movieId) values (4,16);
insert into catalog (actorId, movieId) values (4,17);
insert into catalog (actorId, movieId) values (5,18);
insert into catalog (actorId, movieId) values (5,19);
insert into catalog (actorId, movieId) values (5,20);
insert into catalog (actorId, movieId) values (5,21);
insert into catalog (actorId, movieId) values (6,22);
insert into catalog (actorId, movieId) values (6,23);
insert into catalog (actorId, movieId) values (6,24);
insert into catalog (actorId, movieId) values (6,25);
insert into catalog (actorId, movieId) values (6,26);
