#!/bin/bash
#This script create new users in Linux system with a text file

if [ $# -eq 0 ];then
	echo "Welcome to the addUsers script"
	echo "Please, enter the path of the file you want to use to add your users (the format of the textfile must be: id:firstName:surname)"
	read userFile
else
	userFile=$1
fi

if [ ! -f $userFile ];then
	echo "This file doesn't exist"
	exit;
fi 

#We modify the file format to use the command newusers
k=1000; while IFS=: read userID firstname surname; do
	echo "$userID:$surname:$k:1000:$userID:/home/$userID:/bin/bash";let k++;
	done < $userFile > newUsers.txt

#We use our file freshly created
sudo newusers newUsers.txt

	#We want to create a welcome page for all the new users
k=1000; while IFS=: read userID firstname surname; do

	#We create public_html directory
	sudo mkdir /home/$userID/public_html

	#We create html page using the makeHTML script, we give name and surname in argument
	./makeHTML.sh $firstname $surname | sudo tee /home/$userID/public_html/index.html
	
	#give rights to the directory
	sudo chmod 755 /home/$userID

done < $userFile

