-- Produto
insert into produto (id, nome, preco, quantidade, data_validade)
values (203, 'Pão', 1.00, 10, current_date + 1);

insert into produto (id, nome, preco, quantidade, data_validade)
values (204, 'Refrigerante', 10.00, 3, current_date + 1);

insert into produto (id, nome, preco, quantidade, data_validade)
values (205, 'Pão de Queijo', 2.00, 1, current_date - 1);

insert into produto (id, nome, preco, quantidade, data_validade)
values (206, 'Bolo de aniversário', 50.00, 4, current_date + 2);


-- Venda
insert into venda (id, produto_id, quantidade, data_venda, total)
values (403, 203, 1, current_date, 1);

insert into venda (id, produto_id, quantidade, data_venda, total)
values (404, 204, 2, current_date, 20);

insert into venda (id, produto_id, quantidade, data_venda, total)
values (405, 203, 1, current_date, 2);

insert into venda (id, produto_id, quantidade, data_venda, total)
values (406, 206, 2, current_date, 100);
