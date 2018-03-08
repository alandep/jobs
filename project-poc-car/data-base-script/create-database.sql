create database IF NOT EXISTS db_poc_car;

use db_poc_car;

CREATE TABLE IF NOT EXISTS tb_car (
	id                int(11)       not null auto_increment, /*Atributo de Identificação */
	brand             varchar(40)   not null , /* Marca, texto, não nulo, 40 caracteres  */ 
	model             varchar(50)   not null , /* Modelo, texto, não nulo, 50 caracteres */
	color             varchar(30)   not null , /* Cor, texto, não nulo, 30 caracteres    */
	year              int           not null , /* Ano, inteiro positivo, não nulo        */
	price             decimal(18,3) not null,  /* Preço, decimal positivo, não nulo      */
	description       text          not null,  /* Descrição, texto                       */
	is_new            bit(1)        not null,  /* É novo?, boleano, não nulo             */
	registration_date timestamp     not null,  /* Data cadastro, data e hora, não nulo   */
	update_date       timestamp     not null,  /* Data atualização, data e hora, nulo    */
	 
	PRIMARY KEY (id) 
);

CREATE TABLE IF NOT EXISTS user (
	id        int(11) not null auto_increment, /*Atributo de Identificação */
	user      varchar(50)                    , /*Usuário a ser autenticado */
    password  varchar(50)                    , /*Senha a ser autenticada */
    is_active bit(1)                         , /*Atributo booleano que indica se o usuário esta ativo ou não */
	 
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS role (
	id      int(11)     not null auto_increment, /*Atributo de Identificação */
	role    varchar(40) not null ,               /* Descrição da regra que permitira ou não o acesso*/
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_role (
	id      int(11) not null auto_increment, /*Atributo de Identificação */
    user_id int(11) not null , /*Atributo chave estrangeira da tabela user */
    role_id int(11) not null , /*Atributo chave estrangeira de referencia a tabela role */
    
	PRIMARY KEY (id) ,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (role_id) REFERENCES role (id)
);

INSERT INTO role(role) VALUES ('ADMIN');