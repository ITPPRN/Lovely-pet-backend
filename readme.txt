
//ให้อนุญาติสิทธิ postgress  ให้เครื่องเซิร์ฟเข้าด้วย ip
host    all             all             192.168.1.102/32     scram-sha-256



//build  image
docker build -t app/lovelypet .


//run container
docker run -d --name lovelypetlocal -p 8080:8080 -e SECRET_DB_HOST=192.168.33.124 -e SECRET_DB_PORT=5432 -e DB_USERNAME=postgres -e SECRET_DB_PASS=Tee192414+ -e SECRET_REDIS_HOST=192.168.33.124 -e SECRET_REDIS_PORT=6379 app/lovelypet:latest

docker run -d --name lovely-pet -p 8080:8080 -e SECRET_DB_HOST=192.168.1.32 -e SECRET_DB_PORT=5432 -e DB_USERNAME=postgres -e SECRET_DB_PASS=Tee192414+ -e SECRET_REDIS_HOST=192.168.1.32 -e SECRET_REDIS_PORT=6379 --restart=unless-stopped app:latest

rm data on postgres
docker exec -it postgres psql -U postgres
\c lovely_pet;
DELETE FROM photo_hotel;

