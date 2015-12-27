#!/bin/bash
#This script is an alternative version of backUp.sh using Zenity

BACKUPDIR=$HOME/archives

FILE=`zenity --file-selection --directory --filename="$HOME/digitaldruid" --title="Select the file you want to archive"`

case $? in
	0)
	BACKUPDF="$FILE"
	echo "$BACKUPDF";;
	1)
	echo "No file selected";;
	-1)
	echo "Unexpected error";;
esac

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


(
#initialisation
echo "10"; sleep 0.5
echo "# Initialisation"; sleep 0.5
cd $BACKUPDIR
TARNAME="`basename $BACKUPDF`"
TARFILE="$BACKUPDIR/$TARNAME.tar"
echo "40"; sleep 0.5
echo "# Creation of the tar file"; sleep 0.5
#We create the archive
tar cf $TARFILE $BACKUPDF 

echo "70"; sleep 0.5
echo "# Compression..."; sleep 0.5
#We zip it 
GZIPFILE="$TARFILE.gz"
if [ -f $GZIPFILE ];then
	rm $GZIPFILE
fi
gzip $TARFILE

echo "99"; sleep 0.5
echo "# Compression successfull"; sleep 0.5
) |
zenity --progress --title="BackUp of your directory" --text="Demarrage..." --percentage=0 --auto-close

if [ "$?" = -1 ]; then
	zenity --error --text="Compression canceled"
fi

#In the exercise it's asked to transfer a secure copy (scp) to the server and delete the tar.gz file but I can't do it right now because i don't have access to the server

#scp $GZIPFILE user@server:remoteDir
#rm $GZIPFILE

#This one can't be use with cron because, it require intervention of the user
