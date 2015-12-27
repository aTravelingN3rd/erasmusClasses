#!/bin/bash
#This script makes a backUp of a choosen directory 

BACKUPDIR=$HOME/archives

#If the path isn't write as argument, we ask for it
if [ $# -eq 0 ];then
	echo "Welcome to the backUp script"
	echo "Please, enter the path of the file/directory you want to archive..."
	read BACKUPDF
else
	BACKUPDF=$1
fi

#If the file/directory exist we create directory for archive 
if [ -f $BACKUPDF ] || [ -d $BACKUPDF ];then 
	if [ ! -d $BACKUPDIR ];then
		mkdir $BACKUPDIR
	fi
	echo "You will find your backUP here :$BACKUPDIR" 
else
	echo "This file/directory doesn't exist"
	exit;
fi 

#initialisation
cd $BACKUPDIR
TARNAME="`basename $BACKUPDF`"
TARFILE="$BACKUPDIR/$TARNAME.tar"


#We create the archive
tar cf $TARFILE $BACKUPDF 

#We zip it 
GZIPFILE="$TARFILE.gz"
if [ -f $GZIPFILE ];then
	rm $GZIPFILE
fi
gzip $TARFILE

#In the exercise it's asked to transfer a secure copy (scp) to the server and delete the tar.gz file but I can't do it right now because i don't have access to the server

#scp $GZIPFILE user@server:remoteDir
#rm $GZIPFILE

#We also add to use crontable to execute the script periodically, in the exemple every friday at 19:00. This is what I put in the crontable
# m h dom mon dow command
# 0 19 * * 5 $HOME/scripts/backUp.sh $HOME/backUpFile
