create table item_pedido
(
   id bigint auto_increment, 
   quantidade integer not null,
   valor_unitario decimal(10,2),
   cerveja_id bigint not null, 
   pedido_id bigint not null,
   primary key (id)
)
engine = InnoDB default charset= utf8;

alter table item_pedido add constraint fk_item_pedido_cerveja
foreign key (cerveja_id) references cerveja (id);

alter table item_pedido add constraint fk_item_pedido_pedido
foreign key (pedido_id) references pedido (id);