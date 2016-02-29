# coursera-cloudapp-mp3-storm
## Steps to install Storm on my Windows 8.1 x64

* Install Java
* Install Python
* Download archive with Zookeeper and extract it. Than configure:
```
cd zookeeper-3.3.6
copy conf\zoo_sample.cfg conf\zoo.cfg
```
* Install Storm
For my environment (Windows 8.1 x64) it was OK to use a pre-build version of Storm (in task folder). Extract this archive.
* Set Environment Variables
```
JAVA_HOME: C:\Java\jdk1.7.0_45\
STORM_HOME: C:\storm-0.9.1-incubating-SNAPSHOT-12182013\
PATH: %STORM_HOME%\bin;%JAVA_HOME%\bin;C:\Python27;C:\Python27\Lib\site-packages\;C:\Python27\Scripts\;
PATHEXT: .PY
```

## Steps to run Storm
* Start Zookeeper
```
C:\Hadoop\zookeeper-3.3.6
.\bin\zkServer.cmd
```

* Start Nimbus Daemon
```
cd %STORM_HOME%
storm nimbus
```

* Start Supervisor Daemon
```
cd %STORM_HOME%
storm supervisor
```

* Start Storm UI Daemon
```
cd %STORM_HOME%
storm ui
```

* Check that services are running at 
```
http://localhost:8080/ 
```
