Spring-docker-cassandra. Cassandra cluster with 3 nodes and replication factor = 2.
If want more nodes -> add new

Start application: docker-compose up --build --remove-orphans --abort-on-container-exit
(-d if background) (--build if need to rebuild image)
Enter main node bash: docker exec -it cassandra-seed bash
Nodes status: nodetool status
Enter query language shell: cqlsh
Docker container statistics: docker stats
Cassandra run test: cassandra-stress write n=1000000 (tool need to be installed cassandra-stress tool)
Percentile statistics: docker exec cassandra-seed nodetool tablehistograms vvopaa tutorial
