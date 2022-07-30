# Projeto Agro

## Desafio
O desafio proposto diz respeito à construção de uma solução para o agronegócio.
A solução deverá contemplar a leitura de sensores alocados em drones, que enviam uma nova leitura a cada 10 segundos.
Caso a solução detecte uma anormalidade, que pode ser relativa à temperatura (acima ou igual à 35ºC ou abaixo ou igual a 0ºC) ou Umidade (menor ou igual a 15%), deverá ser enviado um alerta por e-mail com os dados da leitura.

## RabbitMQ <img align="left" alt="RabbitMQ" width="30px" src="https://jpadilla.github.io/rabbitmqapp/assets/img/icon.png"/>
- É necessário subir o RabbitMQ que está configurado para rodar via Docker, na pasta raíz já existe o arquivo docker-compose.yml, rodar o comando "docker-compose up". As configuraçõs default de portas do RabbitMQ, usuárioe e senha se encontram dentro desse arquivo.
Com o broker da fila rodando, rodar o projeto do produtor e consumidor da mensageria, que são os projetos agro-producer e agro-consumer.


## Agro Producer <img align="left" alt="Java" width="30px" src="https://devkico.itexto.com.br/wp-content/uploads/2014/08/spring-boot-project-logo.png" />
- O produtor tem a responsabilidade de receber os dados do drone pela camada Rest da API e enviar para o broker em uma fila. As configurações desse projeto estão no arquivo "application.properties", basicamente tem apenas as configurações de como se comunicar com o broker (usuário, senha, porta e etc).
Além de receber os dados do drone pela camada Rest, foi criado um job que é executado a cada 10 segundos que irá enviar dados randomicos para o broker, simulando automaticamente o recebimento de dados de 10 drones com a leitura feita. Esses dados estão nos formatos da biblioteca "agro-lib" que será explicado no próximo item.

## Agro Lib <img align="left" alt="Java" width="30px" src="https://iconarchive.com/download/i98325/dakirby309/simply-styled/Java.ico" />
- Para padronizar os dados de entrada e saída da fila, foi criado esse projeto que representa o "objeto" ou a estrutura dos dados que será enviado pelo drone recebendo no projeto "Agro Producer" e depois enviado para o broker Rabbit MQ e finalmente, que será consumido pelo projeto "Agro Consumer" que será explicado no próximo item.
Classe que representa esses dados: LeituraDto.java.

## Agro Consumer <img align="left" alt="Java" width="30px" src="https://devkico.itexto.com.br/wp-content/uploads/2014/08/spring-boot-project-logo.png" />
- Essa aplicação tem o objetivo de consumir do broker RabbitMQ através de uma fila. Para rodar o projeto, deve-se configurar os seguintes parâmetros no arquivo "application.properties":
<div>
spring.mail.host=smtp.office365.com #VERIFICAR I SMTP DO PROVEDOR E E-MAIL ex: GMAIL: smtp.gmail.com, OUTLOOK: smtp.office365.com
spring.mail.port=587 #VERIFICAR PORTA DISPONIVEL DO PROVEDOR DE E-MAIL
spring.mail.password=senhaDoEmail #SENHA
spring.mail.username=emailQueVaiEnviar@email.com #EMAIL
</div>

Esse proijeto ao fazer a leitura do broker, vai processar os dados e idenfiticar conforme o desafio quando terá que enviar um e-mail de alerta ao e-mail cadastrado.
