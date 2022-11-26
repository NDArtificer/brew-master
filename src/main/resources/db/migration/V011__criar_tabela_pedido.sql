create table pedido (
	id bigint auto_increment,
	data_criacao datetime not null,
	valor_frete decimal(10,2), 
	valor_desconto decimal(10,2),
	valor_total decimal(10,2) not null,
	status varchar(30) not null,
	observacao varchar(200),
	data_entrega datetime,
	cliente_id bigint not null,
	usuario_id bigint not null ,
	primary key (id)
)
engine = InnoDB default charset= utf8;


alter table pedido add constraint fk_pedido_cliente
foreign key (cliente_id) references cliente (id);

alter table pedido add constraint fk_pedido_usuario
foreign key (usuario_id) references usuario (id);