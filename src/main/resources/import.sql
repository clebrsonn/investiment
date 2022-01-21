-- Roles
Insert into Role(Name) values('ROLE_USER');
Insert into Role(Name) values('ROLE_ADMIN');

--user
Insert into Users(username, document, enabled, lastsync, name, password) values('clebrsonn@hotmail.com','06824530484',true,null,'Cleberson Barbosa','{bcrypt}$2a$10$MsyS1mGEro2wwYXTXiJw5.XVfRvbqcRgy2nlNuOy6vC0tBBcMhPIG')

-- Wallets
Insert into Wallet(Id, Name) values ('0x288FD12B2a248C2C8CfF67310Cb9221aE2EE8ff0', 'Metamask');
Insert into Wallet(Id, Name) values ('0x288FD12B2a248C2C8CfF67310Cb9221aE2EE8ff1', 'Trust');
