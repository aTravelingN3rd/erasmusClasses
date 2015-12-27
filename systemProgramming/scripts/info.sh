#!/bin/bash
#This script displays simple informations

echo "Hello $USER,"
echo "The date is `date`"
echo "Your home directory is $HOME"
echo "The current directory is $PWD"
echo "Your OS is `uname -o`"
echo 
echo "Your CPU model is :"
lscpu | grep "Model"
echo "You can display using the command 'lscpu'"
echo
echo "Memory infos in Mb:"
free -m 
echo
echo "Users currently connected:"
who 
