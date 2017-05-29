import sys, os, math, re, zipfile, time, shutil, subprocess, signal, random, glob, errno; 
from pyunpack import Archive
from random import shuffle
from os import listdir
from os.path import isfile, join

repo_path = "student_data/submitted_repos/"

try:
    os.makedirs(repo_path + "groups/")
except:
    pass

groups = {}
   
print "Sorting into groups..."
  
with open("student_data/student-teams.csv") as opened_groups: 
    
    groupLines = opened_groups.readlines()
    
    for line in groupLines:
        
        line = line.strip()
        
        if ( line == "" or "Last name" in line ):
            
            continue;
        
        firstname = line.split(",")[1]
        
        surname = line.split(",")[0]
            
        groups[surname + ", " + firstname] = line.split(",")[2]

print groups;

for moveFile in os.listdir(repo_path):
    
    if ( moveFile == ".DS_Store" or moveFile == "groups" ):
        
        continue
    
    #print repo_path + "groups/" + groups[moveFile] + "/" + moveFile
    shutil.move(repo_path + moveFile, repo_path + "groups/" + groups[moveFile] + "/" + moveFile)