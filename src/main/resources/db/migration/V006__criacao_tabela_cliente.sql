create table cliente
(
   id bigint not null auto_increment,
   nome varchar (80) not null,
   cpfCnpj varchar (30),
   telefone varchar (20),
   tipo_pessoa varchar (15) not null,
   email varchar (80) not null,
   endereco_cidade_id bigint,
   endereco_cep varchar (9),
   endereco_logradouro varchar (100),
   endereco_numero varchar (20),
   endereco_complemento varchar (60),
   primary key (id)
)
engine = InnoDB default charset= utf8;
alter table cliente add constraint fk_cliente_cidade foreign key (endereco_cidade_id) references cidade (id);