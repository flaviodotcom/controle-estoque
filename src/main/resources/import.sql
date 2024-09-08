-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
insert into produto (id, nome, preco, quantidade, data_validade)
values (203, 'Pão', 1.00, 1, current_date + 1);