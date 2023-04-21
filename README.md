# dockerization-withRestApi-Postgres-rabbitMQ-pgAdmin4


--------------------       How to build ----------------------

how to dockerize this application:

	download project as zip file or clone it to your pc.
	
		open in IDE, IntelliJ IDEA 2021.3.3 (Community Edition) was used to build this project
		
		commands to run in terminal:
		clean the project , gradle clean
		build jar , gradle build -x test (skip tests as they will fail as rabbitmq is not up and running as of now)
		this jar file will be located in build/libs  *-SHAPSHOT.jar
		copy this jar file and paste it to src/main/docker
		open docker-config.yml and click on green play icon next to service, it will pull imagies(if required) and start all applications in a container named as docker , 
		or use command docker compose up in directory src/main/docer to pull imagies and start container.


	pgadmin: http://127.0.0.1/login
		login:guest@ymail.com
		password: password
	in pgadmin
		click on server, new server (any name), click on connection, set username,maintenance database,password to compose-postgres, save password
		inorder to set hostname , open terminal use command docker ps, locate postgres, find its id and now use command docker inspect ID_OF_POSTGRES.
		copy ip address avaiable at very end of result and give it at hostname tab. 

	rabbitMQ: http://localhost:15672
		login: guest
		password: guest
	one can see all queues, messages, exchanges by visiting provided link and credentails
	
	
	
	
	--------------------       API ENDPOINTS -----------------------------
	
	Create account : POST -> http://localhost:8081/api/create_account

request
{
    "customerId":"Omar",
    "country":"Estonia",
    "balances":
    [
        {
            "currency":"GBP",
            "availableBalance":"112"
        },
        {
            "currency":"USD",
            "availableBalance":"15"
        }
    ]
}


Get account : GET -> http://localhost:8081/api/account/1

create transaction : POST -> http://localhost:8081/api/create_transaction

request : 
{
    "account_id":1,
    "currency": "GBP",
    "amount":34,
    "direction":"IN",
    "description":"transaction in"
}

Get Transaction : GET -> http://localhost:8081/api/transaction/account/1



