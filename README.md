On Windows OS 10 had to increase docker memory limit to 7 Gb + 1Gb SWAP for 1 local data-center with 4 nodes.

Start application: docker-compose up --build --remove-orphans --abort-on-container-exit
(-d if background) (--build if need to rebuild image)
Enter main node bash: docker exec -it cassandra-seed bash
Nodes status: nodetool status
Enter query language shell: cqlsh
Docker container statistics: docker stats
Cassandra run test: cassandra-stress write n=1000000 (tool need to be installed cassandra-stress tool)
Percentile statistics: docker exec cassandra-seen nodetool tablehistograms vvopaa tutorial
