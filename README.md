Este projeto é uma aplicação desenvolvida com Spring Boot, conectada a um banco de dados MySQL utilizando Docker.

Iniciando o Projeto com Docker

Abra o terminal e navegue até a pasta onde se encontra o arquivo docker-compose.yml
cd caminho/para/o/projeto

Execute o comando para iniciar o container em segundo plano
docker-compose up -d

Verifique se o container está ativo
docker ps

Acessando o Banco de Dados MySQL

Para acessar o MySQL dentro do container, execute o seguinte comando
docker exec -it mysql_container mysql -u root -p

A senha padrão definida no docker-compose.yml é "senha", podendo ser alterada conforme necessário

Após acessar o MySQL, confirme a criação do banco com
SHOW DATABASES;

Iniciando a Aplicação Spring Boot

Abra o projeto no IntelliJ e execute a classe principal localizada em
src/main/java/[seu_pacote]/[ArquivoPrincipal].java

Testando os Endpoints

A aplicação pode ser testada utilizando ferramentas como Postman ou cURL

-> EndPoints para Gerenciamento de Instrutores
Base: http://localhost:8080/instrutor/

-> Cadastrar um novo instrutor
POST http://localhost:8080/instrutor/
Body (JSON):
{
"nome": "nome",
"email": "email@exemplo"
}

-> Buscar todos os instrutores
GET http://localhost:8080/instrutor/

-> Buscar instrutor por ID
GET http://localhost:8080/instrutor/{id}

-> Atualizar informações de um instrutor
PUT http://localhost:8080/instrutor/{id}
Body (JSON):
{
"nome": "novo nome",
"email": "novoemail@exemplo"
}

-> Deletar um instrutor
DELETE http://localhost:8080/instrutor/{id}

-> EndPoints para Gerenciamento de Cursos
Base: http://localhost:8080/cursos/

-> Criar um novo curso
POST http://localhost:8080/cursos/
Body (JSON):
{
"titulo": "Curso A",
"duracaoHoras": 10,
"instrutorId": 1
}

-> Cadastro em lote (batch)
POST http://localhost:8080/cursos/batch
Body (JSON):
[
{
"titulo": "Curso A",
"duracaoHoras": 10,
"instrutorId": 1
},
{
"titulo": "Curso B",
"duracaoHoras": 15,
"instrutorId": 1
}
]

-> Buscar todos os cursos
GET http://localhost:8080/cursos

-> Buscar curso por ID
GET http://localhost:8080/cursos/{id}

-> Buscar curso por título
GET http://localhost:8080/cursos/titulo/{titulo}

-> Buscar cursos por ID do instrutor
GET http://localhost:8080/cursos/instrutorId/{id}

-> Atualizar curso
PUT http://localhost:8080/cursos/{id}
Body (JSON):
{
"titulo": "Curso A",
"duracaoHoras": 10,
"instrutorId": 1
}
Caso deseje alterar apenas uma informação, mantenha os demais campos com os valores atuais

-> Deletar um curso
DELETE http://localhost:8080/cursos/{id}
