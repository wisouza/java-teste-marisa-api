insert into produto (idproduto, sku, name, stock, is_in_sotck, price) values (1, 'S2390002', 'Blusa Plus Size Feminina Floral Manga Longa Marisa', 10, 1, 55.10);
insert into produto (idproduto, sku, name, stock, is_in_sotck, price) values (2, 'S3904826', 'Blusa Feminina Laise Manga Curta Marisa', 10, 0, 20.30);
insert into produto (idproduto, sku, name, stock, is_in_sotck, price) values (3, 'S6414902', 'Blusa Regata Feminina Estampa Folhas Alças Finas Marisa', 10, 1, 30.40);
insert into produto (idproduto, sku, name, stock, is_in_sotck, price) values (4, 'S9763423', 'Blusa Feminina Botões Manga Curta Marisa', 10, 0, 45.50);

insert into pedido (idpedido, finalizado) values (99, 0);
insert into pedido (idpedido, finalizado) values (100, 1);
insert into item_pedido (idproduto, idpedido, quantidade) values (1, 100, 5);
insert into item_pedido (idproduto, idpedido, quantidade) values (2, 100, 5);