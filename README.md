# CSCE548 N-Tier-Application

## Software Required
1) Have MariaDB Server installed (I just used my package manager, but you may need to go to https://mariadb.org/download/?t=repo-config, click on MariaDB Server, and download whichever version matches your operating system)
Note: Depending on your OS, you may need to install a command-line interface/client as well (ex: Ubuntu uses ```sudo apt install mariadb-server mariadb-client```) 
Note: MariaDB may conflict with MySQl, so you may need to change the configuration in some places (see https://mariadb.com/docs/server/server-management/install-and-upgrade-mariadb/installing-mariadb/binary-packages/installing-mariadb-alongside-mysql for more details)

## Setup
0) Ensure MariaDB is running (```sudo systemctl status mariadb```, replace status with start if it's not already started)
1) Log in to MariaDB as an admin (either with ```sudo mariadb``` or ```mariadb -u root -p``` if you've set a root password during installation)
Note: tools like MySQL Workbench may not be combatible with MariaDB
2) Run schema.sql or copy-paste its contents directly into MariaDB's console
3) Run testdata.sql
4) (Optional) Exit MariaDB by typing exit;
5) Add the jar as a java referenced library (not necessary if your IDE is VS Code and the settings are imported)

## Running
0) Ensure MariaDB is running (```sudo systemctl status mariadb```, replace status with start if it's not already started)
1) Run DataLayerFrontEnd's main method