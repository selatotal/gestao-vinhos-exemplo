# Gestão de Vinhos

Aplicação web de exemplo, em Spring Boot, para cadastro e avaliação de vinhos. Usuários autenticados navegam pelo catálogo e registram avaliações (positivas ou negativas) com um comentário; administradores mantêm o catálogo de vinhos e a base de usuários.

Projeto didático desenvolvido como exemplo para a disciplina de Programação Web (ULBRA).

## Funcionalidades

- **Autenticação e autorização** com Spring Security, com dois papéis: `ROLE_USER` e `ROLE_ADMIN`
- **Cadastro público de usuário** em `/usuario/novo`
- **Catálogo de vinhos** com nome, vinícola, tipo (Seco/Suave) e imagem
- **Avaliação de vinhos**: cada usuário registra um voto positivo ou negativo com descrição
- **Minha lista**: vinhos já avaliados pelo usuário logado
- **Área administrativa** (`/admin`), restrita a administradores:
  - CRUD de vinhos, incluindo upload da imagem
  - CRUD de usuários, com reset de senha
  - Exclusão de avaliações
- **Documentação da API** gerada por Springfox/OpenAPI 3
- **Endpoints de monitoramento** via Spring Boot Actuator

## Stack

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 8 |
| Framework | Spring Boot 2.0.2 (Web, Data JPA, Security, Actuator) |
| Templates | Thymeleaf + Semantic UI |
| Banco de dados | H2 (arquivo local) |
| Documentação | Springfox 3.0.0 (OpenAPI 3) |
| Mapeamento DTO | ModelMapper |
| Build | Maven (via Maven Wrapper) |

## Como executar

Pré-requisito: JDK 8.

```bash
./mvnw spring-boot:run
```

Ou, gerando o JAR executável:

```bash
./mvnw clean package
java -jar target/gestao-vinhos-0.0.3.jar
```

A aplicação sobe em `http://localhost:8080`.

### Usuário administrador

Criado automaticamente pelo `src/main/resources/data.sql`:

- **Usuário:** admin@admin.com
- **Senha:** 123456

## Endpoints úteis

| URL | Descrição |
|---|---|
| `/` → `/inicio` | Página inicial com o catálogo de vinhos |
| `/vinhos/minhalista` | Vinhos avaliados pelo usuário logado |
| `/admin` | Área administrativa (requer `ROLE_ADMIN`) |
| `/swagger-ui/index.html` | Interface do Swagger |
| `/v3/api-docs` | Documento OpenAPI 3 em JSON |
| `/h2` | Console do banco H2 |
| `/actuator/health` | Health check da aplicação |

Todos os endpoints exigem autenticação, exceto `/login`, `/usuario/novo` e os recursos estáticos.

## Configuração

Parâmetros ficam em `src/main/resources/application.yml`:

- `spring.datasource.url`: banco H2 em arquivo, por padrão `~/db/submissoes`
- `spring.jpa.hibernate.ddl-auto`: `create-drop` — o schema é recriado a cada inicialização e os dados de `data.sql` são recarregados
- `gestao-vinhos.uploadFilePath`: diretório onde as imagens dos vinhos são gravadas (padrão `/tmp/gestao-vinhos/files`)

## Aviso

Este é um projeto de exemplo, com credenciais padrão no código, banco em memória/arquivo local e CSRF desabilitado. Não use em produção sem revisar essas configurações.

## Licença

Distribuído sob a licença MIT. Veja [LICENSE](LICENSE) para o texto completo.
