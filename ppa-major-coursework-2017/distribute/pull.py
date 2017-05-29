import os, shutil, sh, re, random, string, subprocess, errno, mmap

from encrypt import encrypt

from send import clone_all, pull_all, fetch_all, switch_all

if __name__ == '__main__':
    
    repos_list = []
    
    with open("not_pushed.txt") as repo_owners:
        
        for owner in repo_owners:
            
            if ( owner != "" ):
                
                repos_list.append(owner.replace("\n", ""))  
            
    clone_all(repos_list)
    switch_all(repos_list)
    fetch_all(repos_list)
    pull_all(repos_list)