-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
insert into produto (id, nome, preco, quantidade, data_validade)
values (203, 'Pão', 1.00, 1, current_date + 1);

insert into produto (id, nome, preco, quantidade, data_validade)
values (204, 'Refrigerante', 10.00, 1, current_date + 1);

insert into produto (id, nome, preco, quantidade, data_validade)
values (205, 'Pão de Queijo', 2.00, 1, current_date - 1);

insert into venda (id, produto_id, quantidade, data_venda)
values (403, 203, 1, current_date);