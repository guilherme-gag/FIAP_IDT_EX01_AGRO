# Projeto Agro
## Desafio
O desafio proposto diz respeito à construção de uma solução para o agronegócio.
A solução deverá contemplar a leitura de sensores alocados em drones, que enviam uma nova leitura a cada 10 segundos.
Caso a solução detecte uma anormalidade, que pode ser relativa à temperatura (acima ou igual à 35ºC ou abaixo ou igual a 0ºC) ou Umidade (menor ou igual a 15%), deverá ser enviado um alerta por e-mail com os dados da leitura.

## RabbitMQ <img align="left" alt="RabbitMQ" width="30px" src="https://jpadilla.github.io/rabbitmqapp/assets/img/icon.png"/>
- É necessário subir o RabbitMQ que está configurado para rodar via Docker, na pasta raíz já existe o arquivo docker-compose.yml, rodar o comando "docker-compose up". As configuraçõs default de portas do RabbitMQ, usuário e e senha se encontram dentro desse arquivo.
Com o broker da fila rodando, rodar o projeto do produtor e consumidor da mensageria, que são os projetos agro-producer e agro-consumer.


## Agro Producer <img align="left" alt="Spring" width="30px" src="https://devkico.itexto.com.br/wp-content/uploads/2014/08/spring-boot-project-logo.png" /> <img align="left" alt="Rest" width="30px" src="https://icon-library.com/images/rest-api-icon/rest-api-icon-1.jpg" />
- O produtor tem a responsabilidade de receber os dados do drone pela camada Rest da API e enviar para o broker em uma fila. As configurações desse projeto estão no arquivo "application.properties", basicamente tem apenas as configurações de como se comunicar com o broker (usuário, senha, porta e etc).
Além de receber os dados do drone pela camada Rest, foi criado um job que é executado a cada 10 segundos que irá simular o recebimento dados randomicos de dez drones e enviar para o broker. Esses dados estão nos formatos da biblioteca "agro-lib" que será explicado no próximo item.

## Agro Lib <img align="left" alt="Java" width="30px" src="https://iconarchive.com/download/i98325/dakirby309/simply-styled/Java.ico" />
- Para padronizar os dados de entrada e saída da fila, foi criado esse projeto que representa o "objeto" ou a estrutura dos dados que será enviado pelo drone, recebido  no projeto "Agro Producer" e depois enviado para o broker Rabbit MQ e finalmente, que será consumido pelo projeto "Agro Consumer" que será explicado no próximo item.
Classe que representa esses dados: LeituraDto.java.

## Agro Consumer <img align="left" alt="Spring" width="30px" src="https://devkico.itexto.com.br/wp-content/uploads/2014/08/spring-boot-project-logo.png" />
- Essa aplicação tem o objetivo de consumir do broker RabbitMQ através de uma fila. Para rodar o projeto, deve-se configurar os seguintes parâmetros no arquivo "application.properties":

````
spring.mail.host=smtp.office365.com #VERIFICAR O SMTP DO PROVEDOR DE E-MAIL ex: GMAIL: smtp.gmail.com, OUTLOOK: smtp.office365.com
spring.mail.port=587 #VERIFICAR PORTA DISPONIVEL DO PROVEDOR DE E-MAIL
spring.mail.password=senhaDoEmail #SENHA
spring.mail.username=emailQueVaiEnviar@email.com #EMAIL
````

- Esse projeto ao fazer a leitura do broker, vai processar os dados e idenfiticar conforme o desafio quando terá que enviar um e-mail de alerta ao e-mail cadastrado.

# Docker Compose <img align="left" alt="DockerCompose" width="30px" src="https://stack.desenvolvedor.expert/appendix/docker/images/compose.png"/> <img align="left" alt="DockerCompose" width="30px" src="https://www.docker.com/wp-content/uploads/2022/03/Moby-logo.png"/>

A branch Develop está preparada para rodar o projeto como um todo no docker compose. Existem algumas configurações que devem ser feitas, segue:

Pela demora de se fazer o build de cada projeto Maven dentro do container, não foi incluído o build automatico do maven. Então você deve ir em cada um dos três projetos Java/Spring e executar o comando: "maven clean install" para gerar o artefato .jar. Os projetos são: Agro Lib, Agro Producer e Agro Consumer nessa ordem.

Após gerar os três artefatos, abrir o arquivo docker-compose.yml da pasta raíz, "service" agro-consumer e preencher as variaveis EMAIL_PASSWORD e EMAIL_USER com o usuário e senha correspondente.
Obs: Devido a particulariade de cada provedor de e-mail, foi escolhido o Gmail por ter uma grande facilidade. O google tem um gerador de senhas para apps menos seguros (para testes), o que é ideal já que é uma senha aleatória que você cadastra na sua conta do Gmail e que pode ser excluída a qualquer momento e  você não tem necessidade de colocar sua senha real dentro da aplicação.

Mais detalhes em: https://support.google.com/accounts/answer/185833?hl=pt-BR

Após preencher as duas variáveis, na pasta raiz rodar o comando: docker-compose up --build. E a aplicação irá rodar e mandar os e-mail caso necessário.
As aplicações Spring/Java pode dar algum erro no começo por ter que esperar o RabbiMQ subir, mas eles estão sempre restartando. 
Fique de olho nos logs agro-producer que irá gerar 10 leituras de drones a cada 30 segundos e enviar parao broker. E o agro-consumer estará sempre escutando a fila para recuparar a leitura dos drones e se tiver necessidade tentará envial um e-mail para o usuário do e-mail cadastrado na variável de ambiente.

Template no log caso haja uma tentativa de e-mail:
````
O drone de ID x apresentou temperatura xx.xºC, umidade: xxx.x%, latitude: xx.x. Rastreamento Ativado: xxx.
````


