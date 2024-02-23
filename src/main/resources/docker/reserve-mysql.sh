docker run -it \
--name zb-reserve-mysql \
-e MYSQL_ROOT_PASSWORD="reserve" \
-e MYSQL_USER="reserve" \
-e MYSQL_PASSWORD="reserve" \
-e MYSQL_DATABASE="reserve" \
-p 3306:3306 \
-d mysql