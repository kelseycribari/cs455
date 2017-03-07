test_home=~/workspace/cs455/scalable-server/src

for i in `cat machines_list` 
do
	echo ' logging into '${i}
	terminator "ssh -t ${i} 'echo 'helloworld!'; cd ${test_home}; java cs455.scaling.client.Client peanut 7077 5; bash '" & 
done