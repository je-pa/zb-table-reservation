docker run --name reserve-redis \
             -p 6379:6379 \
             --network docker_reserve \
             -d redis:latest
