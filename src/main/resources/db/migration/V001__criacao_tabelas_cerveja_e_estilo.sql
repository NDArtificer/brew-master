create table estilo
(
   id bigint auto_increment,
   nome varchar (50) not null,
   primary key (id)
)
engine = InnoDB default charset= utf8;

create table cerveja
(
   id bigint auto_increment,
   sku varchar (50) not null,
   nome varchar (80) not null,
   descricao text not null,
   valor decimal(10,2) not null,
   teor_alcoolico decimal(10,2) not null,
   comissao decimal(10,2) not null,
   sabor varchar (50) not null,
   origem varchar (50) not null,
   estilo_id bigint not null,
   primary key (id)
)
engine = InnoDB default charset= utf8;


alter table cerveja add constraint fk_cerveja_estilo
foreign key (estilo_id) references estilo (id);