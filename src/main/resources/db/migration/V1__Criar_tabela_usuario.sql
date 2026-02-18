create table empresa (
    empresa_id bigserial primary key,
    nome_empresa varchar (100) not null,
    cnpj varchar(14) not null,
    create_at timestamp not null default now(),
    update_at timestamp not null default now(),

    constraint ck_cnpj_formato check ( cnpj ~ '^[0-9]{14}$' )
);
create table usuario(
    usuario_id bigserial primary key,
    empresa_id bigint not null references empresa(empresa_id),
    nome_do_usuario varchar(100) not null,
    email varchar(100) not null,
    senha_hash varchar(255) not null,
    role varchar (20) not null default 'ADMIN',
    ativo boolean not null default true,
    create_at timestamp not null default now(),
    update_at timestamp not null default now(),

    /**
      AQUI DIZ QUE TODA EMPRESA SÓ PODE TER 1 EMAIL
     */
    constraint uk_usuario_empresa_email unique (empresa_id, email)
);

create type tipodeentrada as enum ('ENTRADA', 'SAIDA', 'BAIXA', 'AJUSTE');

create table produto (
    produto_id bigserial primary key,
    empresa_id bigint not null references empresa(empresa_id),
    codigo_do_produto varchar(80) not null,
    nome_do_produto varchar(150) not null,
    descricao_do_produto varchar (255) not null,
    tipo_de_entrada tipodeentrada not null,
    quantidade_do_produto integer not null,
    quantidade_min integer not null default 0,
    preco_unitatio numeric(10,2) not null default 0,
    ativo boolean not null default true,
    create_at timestamp not null default now(),
    update_at timestamp not null default now(),
    CONSTRAINT  uk_produto_empresa_sku unique (empresa_id, codigo_do_produto),
    CONSTRAINT  ck_produto_min check (quantidade_min >= 0)
);

create index idx_produto_empresa on produto(empresa_id);
create index idx_produto_alerta on produto(empresa_id, quantidade_min);


create type tipodemovimentacao as enum ('ENTRADA', 'SAIDA', 'BAIXA', 'AJUSTE');

create table movimentacao_estoque
(
    movimentacao_id      bigserial primary key,
    empresa_id           bigint references empresa (empresa_id),
    produto_id           bigint references produto (produto_id),
    usuario_id           bigint references usuario (usuario_id),
    tipo_de_movimentacao tipodemovimentacao not null,
    observacao           varchar(200)       not null,
    quantidade           integer            not null,
    preco_unitario       numeric(10, 2),
    create_at            timestamp default now(),
    update_at            timestamp default now(),
    constraint ck_mov_qtd check ( quantidade > 0 ),
    constraint ck_preco_por_tipo check ( (
                                             tipo_de_movimentacao in ('SAIDA', 'ENTRADA') and
                                             preco_unitario is not null)
        or
                                         (tipo_de_movimentacao in ('BAIXA', 'AJUSTE') and preco_unitario is not null))
);
create index idx_mov_empresa on movimentacao_estoque(empresa_id);
create index idx_mov_produto on movimentacao_estoque(produto_id);
create index idx_mov_usuario on movimentacao_estoque(usuario_id);