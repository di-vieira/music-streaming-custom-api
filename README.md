# music-streaming-custom-api

## API Customizada com funções adicionais para serviços de streaming

Esta é uma API REST que tem por objetivo consumir outras APIs de serviços de streaming e através deles realizar algumas tarefas customizadas. No momento ela está consumindo apenas com a API do Spotify, mas a idéia é que futuramente possa ser evoluído para consumir APIs de outros serviços também, como o Deezer e Apple Music, por exemplo.

Atualmente há quatro funcionalidades principais relacionadas ao serviço de streaming expostas:
- Criação de uma playlist personalizada, com base nas músicas e artistas que o usuário mais ouve no momento
- Busca de artistas favoritas
- Busca de músicas favoritas
- Avalia o quão ruim é o seu gosto musical ;)

Também estão expostas algumas funcionalidades de cadastro de gêneros, que é basicamente o cadastro no qual a API se baseia para avaliar o gosto musical da pessoa.

## Stack utilizada

Para a criação desta API foi utilizada a stack abaixo:

- Java 11 (Linguagem backend)
- Spring Boot 2.3.3 (Ferramenta para configuração do microsserviço)
- Apache Maven 3.6.3 (Gerenciador de dependências)
- Swagger (Documentação da API e testes)
- H2 (Banco de Dados em memória)
- JUnit e Mockito (Testes unitários)
- AWS EC2 (Serviço de nuvem para publicação do serviço)

## Como Executar?

Para realizar o build do microsserviço, basta que na sua pasta raiz seja executado o comando:

```shell
mvn clean install
```

Uma vez que o build tenha sido realizado com sucesso, a subida do serviço pode ser feita através do comando:

```shell
java -jar target/music-streaming-custom-api.jar
```

O microsserviço também está preparado para rodar em um container Docker, possuindo um Dockerfile configurado. 
Para criação do container docker, execute o comando:

```shell
docker build -t target/music-streaming-custom-api.jar .
```

Por fim, para subir o container docker

```shell
docker run -p [PORTA_DESEJADA]:8080 music-streaming-custom-api.jar
```
Desta forma o microsserviço estará no ar e é possivel verificar e testar localmente através da URL:
http://localhost:8080/swagger-ui.html ou usando uma ferramenta para testes de API Rest, como o Postman


## Como posso testar a API?

O microsserviço se encontra rodando em uma instância na AWS, sendo possível acessá-lo em:
[http://ec2-18-222-154-235.us-east-2.compute.amazonaws.com:8080/swagger-ui.html]

<img src="https://github.com/di-vieira/my-images/blob/master/Tela%20principal%20Swagger.png"/>

O Swagger além de documentar a API, permite que a mesma possa ser facilmente testada.

Os endpoints dentro de <b>music-streaming-controller</b> necessitam de autenticação para serem executados, visto que acessam a API do Spotify. Para testar com o Swagger, basta seguir os seguintes passos:


<b><i>Na UI do Swagger, clicar no cadeado verde "Authorize"</i><b>

<img src="https://github.com/di-vieira/my-images/blob/master/Authorize.png"/>


<b><i>Selecionar os escopos que permitem acesso às funções da API</i><b>

<img src="https://github.com/di-vieira/my-images/blob/master/Authorize2.png"/>


<b><i>Clicar em Authorize para que seja feito o login no Spotify</i><b>

<img src="https://github.com/di-vieira/my-images/blob/master/Authorize3.png"/>


<b><i>Feito o login e de volta ao swagger, basta chamar o serviço. O parâmetro authorization não deve ser preenchido pois o mesmo será preenchido com o token de autenticação, através do header da requisição</i><b>

<img src="https://github.com/di-vieira/my-images/blob/master/create_playlist.png"/>



## Histórico de Release

* 1.0.0
    * Versão Inicial

## Info

Diego da Silva Vieira – [@dieguito_vieira](https://twitter.com/dieguito_vieira) – diegodasilvavieira@gmail.com
Mobile/Whatsapp: 11 98242-4627

[https://github.com/di-vieira/music-streaming-custom-api]
[https://www.linkedin.com/in/diego-silva-vieira/]
