
//ให้อนุญาติสิทธิ postgress  ให้เครื่องเซิร์ฟเข้าด้วย ip
host    all             all             192.168.1.102/32     scram-sha-256



//build  image
docker build -t app .


//run container
docker run -d --name lovelypetlocal -p 8080:8080 -e SECRET_DB_HOST=192.168.33.124 -e SECRET_DB_PORT=5432 -e DB_USERNAME=postgres -e SECRET_DB_PASS=Tee192414+ -e SECRET_REDIS_HOST=192.168.33.124 -e SECRET_REDIS_PORT=6379 app/lovelypet:latest

docker run -d --name lovely-pet -p 8080:8080 -e SECRET_DB_HOST=192.168.105.124 -e SECRET_DB_PORT=5432 -e DB_USERNAME=postgres -e SECRET_DB_PASS=Tee192414+ -e SECRET_REDIS_HOST=192.168.105.124 -e SECRET_REDIS_PORT=6379 --restart=unless-stopped app:latest

//รันเมลล์ด้วย
docker run -d --name lovely-pet -p 8089:8080 -e SECRET_MAIL_HOST=smtp.gmail.com -e SECRET_MAIL_PORT=587    -e SECRET_DB_HOST=10.101.106.156 -e SECRET_DB_PORT=5432 -e DB_USERNAME=postgres -e SECRET_DB_PASS=Tee192414+ -e SECRET_REDIS_HOST=10.101.106.156 -e SECRET_REDIS_PORT=6379 --restart=unless-stopped app:latest

docker run -d --name lovely-pet -p 8089:8080 -e SECRET_MAIL_HOST=smtp.gmail.com -e SECRET_MAIL_PORT=587    -e SECRET_DB_HOST=192.168.159.124 -e SECRET_DB_PORT=5432 -e DB_USERNAME=postgres -e SECRET_DB_PASS=Tee192414+ -e SECRET_REDIS_HOST=192.168.159.124 -e SECRET_REDIS_PORT=6379 --restart=unless-stopped app:latest

//view logs

docker logs -f lovely-pet



//run postgres

docker run --name postgres -e POSTGRES_PASSWORD=Tee192414+ -d -p 5432:5432 --restart=unless-stopped  postgres

CREATE DATABASE lovely_pet;



rm data on postgres
docker exec -it postgres psql -U postgres
\c lovely_pet;
DELETE FROM photo_hotel;


//syntax database
UPDATE userprofile SET activated = true WHERE id <= 1;

UPDATE hotel
SET open_state = 'OPEN',
    verify = 'approve',
    activated = true
WHERE id <= 1;

//delete table
DROP TABLE name;

//if dependent 
DROP TABLE booking CASCADE;



// maven
 //install on ubuntu server
 sudo apt update
 sudo apt install maven
//build ja file
mvn clean install

UPDATE hotel
SET activated = true
WHERE id > 0;