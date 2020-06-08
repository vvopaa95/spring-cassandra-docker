FROM cassandra:latest
COPY wait-for-it.sh /usr/bin/wait-for-it.sh
RUN chmod +x /usr/bin/wait-for-it.sh
ENTRYPOINT wait-for-it.sh "cassandra-node-$NODE_ID:9042" -t 300 -- /docker-entrypoint.sh cassandra -f